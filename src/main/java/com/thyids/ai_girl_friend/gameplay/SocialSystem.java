package com.thyids.ai_girl_friend.gameplay;

import com.thyids.ai_girl_friend.Ai_girl_friend;
import com.thyids.ai_girl_friend.web.DeepSeekAPI;
import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

/**
 * 更真实的社交行为系统：
 * - 女友之间会互相"吃醋"（当老公和另一个女友贴贴时）
 * - 女友心情太差会离家出走一段时间
 * - 女友之间会有小互动（对话、一起玩）
 */
@Mod.EventBusSubscriber(modid = Ai_girl_friend.MOD_ID)
public class SocialSystem {
    private static final Random RANDOM = new Random();

    // 记录每个女友最近一次互动/吃醋的时间
    private static final Map<Integer, Long> lastJealousTick = new HashMap<>();
    private static final Map<Integer, Long> runawayUntil = new HashMap<>();

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.level instanceof ServerLevel level)) return;
        if (level.getGameTime() % 100 != 0) return; // 每5秒处理一次

        List<CreeperMother> allGirls = level.getEntitiesOfClass(CreeperMother.class,
                new AABB(-30000000, -64, -30000000, 30000000, 320, 30000000));

        for (CreeperMother girl : allGirls) {
            if (girl.isSleeping() || !girl.isAlive()) continue;
            int girlId = girl.getId();

            // 检查离家出走状态
            if (runawayUntil.containsKey(girlId)) {
                if (level.getGameTime() >= runawayUntil.get(girlId)) {
                    runawayUntil.remove(girlId);
                    // 回到家
                    if (girl.getHusbandUUID() != null) {
                        ServerPlayer husband = level.getServer().getPlayerList().getPlayer(girl.getHusbandUUID());
                        if (husband != null && girl.distanceToSqr(husband) > 400) {
                            girl.teleportTo(husband.getX() + 2, husband.getY(), husband.getZ() + 2);
                            girl.adjustMood(20);
                            DeepSeekAPI.askAI(husband, girl,
                                "（你气消了回到家，看到老公你又心软了，委屈又不好意思地抱住他）");
                        }
                    }
                }
                continue;
            }

            // ⚠️ 吃醋系统已禁用
            // 女友之间互动：如果两个非睡眠的女友距离很近
            if (girl.tickCount % 1200 == 0) {
                for (CreeperMother other : allGirls) {
                    if (other == girl || other.isSleeping() || !other.isAlive()) continue;
                    if (girl.distanceToSqr(other) < 9.0 && girl.getHusbandUUID() != null) {
                        // 两个老婆在聊天
                        if (RANDOM.nextFloat() < 0.2f) {
                            // 随机分配一个互动事件
                            int eventType = RANDOM.nextInt(3);
                            switch (eventType) {
                                case 0:
                                    // 一起跳舞/旋转
                                    girl.triggerAnimation("spin");
                                    other.triggerAnimation("happy");
                                    break;
                                case 1:
                                    // 聊天（用粒子效果表示）
                                    girl.triggerAnimation("shy");
                                    break;
                                case 2:
                                    // 一起去找老公
                                    if (girl.getHusbandUUID() != null) {
                                        Player husband = level.getPlayerByUUID(girl.getHusbandUUID());
                                        if (husband != null) {
                                            girl.getNavigation().moveTo(husband, 1.0);
                                        }
                                    }
                                    break;
                            }
                        }
                        break;
                    }
                }
            }

            // 心情太差 → 离家出走
            if (girl.getMoodLevel() < 15 && girl.getHusbandUUID() != null && level.getGameTime() % 1200 == 0) {
                if (!runawayUntil.containsKey(girlId) && RANDOM.nextFloat() < 0.3f) {
                    runawayUntil.put(girlId, level.getGameTime() + 6000 + RANDOM.nextInt(12000)); // 5~15分钟
                    girl.triggerAnimation("shy");
                    girl.addMessageToMemory("system", "（你心情太差，决定出去走走冷静一下）");
                    // 随机瞬移走
                    double angle = RANDOM.nextDouble() * 2 * Math.PI;
                    double dist = 50 + RANDOM.nextDouble() * 100;
                    girl.teleportTo(
                        girl.getX() + Math.cos(angle) * dist,
                        girl.getY(),
                        girl.getZ() + Math.sin(angle) * dist
                    );
                    if (girl.getHusbandUUID() != null) {
                        Player husband = level.getPlayerByUUID(girl.getHusbandUUID());
                        if (husband != null) {
                            husband.sendSystemMessage(
                                net.minecraft.network.chat.Component.literal(
                                    "§c[" + girl.getDisplayName().getString() + "] §f哼，我出去走走，不要找我！"));
                        }
                    }
                }
            }
        }
    }
}
