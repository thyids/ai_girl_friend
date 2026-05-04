package com.thyids.ai_girl_friend.gameplay;

import com.thyids.ai_girl_friend.web.DeepSeekAPI;
import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

/**
 * 增强的真实互动行为：
 * - 给女友物品触发不同反应（鲜花、钻石、食物等）
 * - 女友自主做家务（补种农田）
 * - 女友随机撒娇/哼歌/主动拥抱
 * - 女友在附近种花装饰
 */
public class GirlfriendActionHandler {
    private static final Random RANDOM = new Random();

    /**
     * 玩家给女友递物品时的反应系统
     */
    public static void onGiveItem(Player player, CreeperMother girl, ItemStack stack) {
        if (player.level().isClientSide) return;
        boolean isHusband = player.getUUID().equals(girl.getHusbandUUID());
        if (!isHusband) return;

        String itemName = stack.getHoverName().getString();

        // 鲜花类 → 开心+送礼
        if (stack.is(Items.POPPY) || stack.is(Items.DANDELION) || stack.is(Items.ALLIUM) ||
                stack.is(Items.AZURE_BLUET) || stack.is(Items.OXEYE_DAISY) || stack.is(Items.LILY_OF_THE_VALLEY) ||
                stack.is(Items.SUNFLOWER) || stack.is(Items.ROSE_BUSH) || stack.is(Items.PEONY) ||
                stack.is(Items.LILAC) || stack.is(Items.CORNFLOWER) || stack.is(Items.WITHER_ROSE)) {
            girl.adjustMood(15);
            girl.triggerAnimation("happy");
            girl.addMessageToMemory("system", "（老公送了一朵" + itemName + "，你开心极了）");
            DeepSeekAPI.askAI(player, girl, "（老公送了你一朵" + itemName + "，你非常开心，甜甜地跟老公说话）");
            stack.shrink(1);
            return;
        }

        // 钻石、金锭、绿宝石 → 惊喜
        if (stack.is(Items.DIAMOND) || stack.is(Items.EMERALD) || stack.is(Items.GOLD_INGOT) || stack.is(Items.NETHERITE_SCRAP)) {
            girl.adjustMood(20);
            girl.triggerAnimation("heart");
            girl.addMessageToMemory("system", "（老公送了" + itemName + "，你感到惊喜又珍贵）");
            DeepSeekAPI.askAI(player, girl, "（老公送了你" + itemName + "，你睁大眼睛又惊又喜，开心地抱住老公）");
            stack.shrink(1);
            return;
        }

        // 食物类 → 按喜好分等级
        if (stack.isEdible()) {
            // 甜食/水果 → 超开心
            if (stack.is(Items.COOKIE) || stack.is(Items.CAKE) || stack.is(Items.SWEET_BERRIES) ||
                    stack.is(Items.HONEY_BOTTLE) || stack.is(Items.GLOW_BERRIES) || stack.is(Items.PUMPKIN_PIE) ||
                    stack.is(Items.COOKED_BEEF) || stack.is(Items.COOKED_PORKCHOP)) {
                girl.adjustMood(12);
                girl.triggerAnimation("coquetry");
                girl.addMessageToMemory("system", "（老公给了" + itemName + "，你超喜欢的！）");
                DeepSeekAPI.askAI(player, girl, "（老公递给你" + itemName + "，这是你最爱吃的，开心地接过，撒娇地跟老公说话）");
            } else {
                girl.adjustMood(5);
                girl.triggerAnimation("happy");
                DeepSeekAPI.askAI(player, girl, "（老公给了你一些" + itemName + "，你温柔地接过）");
            }
            stack.shrink(1);
            return;
        }

        // 武器 → 觉得有趣
        if (stack.is(Items.WOODEN_SWORD) || stack.is(Items.STONE_SWORD) || stack.is(Items.IRON_SWORD) ||
                stack.is(Items.DIAMOND_SWORD) || stack.is(Items.NETHERITE_SWORD)) {
            girl.adjustMood(10);
            girl.triggerAnimation("spin");
            girl.addMessageToMemory("system", "（老公给了" + itemName + "，你拿着把玩，觉得很有趣）");
            DeepSeekAPI.askAI(player, girl, "（老公给了你一把" + itemName + "，你好奇地打量，调皮地挥了挥）");
            return;
        }

        // 盔甲 → 温柔
        if (stack.is(Items.LEATHER_CHESTPLATE) || stack.is(Items.CHAINMAIL_CHESTPLATE) ||
                stack.is(Items.IRON_CHESTPLATE) || stack.is(Items.DIAMOND_CHESTPLATE) ||
                stack.is(Items.NETHERITE_CHESTPLATE)) {
            girl.adjustMood(8);
            girl.triggerAnimation("shy");
            girl.addMessageToMemory("system", "（老公给了你一件" + itemName + "，你心里暖暖的）");
            DeepSeekAPI.askAI(player, girl, "（老公送了你一件" + itemName + "，你感觉很温暖很幸福，害羞地道谢）");
            return;
        }

        // 默认反应
        girl.adjustMood(3);
        girl.triggerAnimation("shy");
        DeepSeekAPI.askAI(player, girl, "（老公给了你" + itemName + "，你接过来，温柔地收好了）");
    }

    /**
     * 女友做家务：补种农田
     */
    public static void doFarmChores(CreeperMother girl) {
        if (girl.level().isClientSide || girl.isSleeping()) return;
        Level level = girl.level();
        if (level.isNight()) return;
        if (girl.tickCount % 4000 != 0) return; // 约每3分钟检查一次
        if (RANDOM.nextFloat() > 0.25f) return;

        BlockPos.betweenClosedStream(
                        girl.blockPosition().offset(-10, -3, -10),
                        girl.blockPosition().offset(10, 3, 10)
                ).filter(pos -> level.getBlockState(pos).is(Blocks.FARMLAND))
                .filter(pos -> level.isEmptyBlock(pos.above()))
                .findFirst().ifPresent(pos -> {
                    BlockPos above = pos.above();
                    girl.getNavigation().moveTo(above.getX() + 0.5, above.getY(), above.getZ() + 0.5, 1.0);
                    // 小手一抖，种下一颗种子（用粒子效果暗示）
                    if (girl.blockPosition().distSqr(above) < 4.0) {
                        girl.triggerAnimation("happy");
                        girl.addMessageToMemory("system", "（你帮老公在田里种了东西）");
                    }
                });
    }

    /**
     * 女友在房子周围种花装饰
     */
    public static void doPlantFlowers(CreeperMother girl) {
        if (girl.level().isClientSide || girl.isSleeping()) return;
        Level level = girl.level();
        if (girl.tickCount % 8000 != 0) return; // 约6分钟
        if (RANDOM.nextFloat() > 0.15f) return;

        // 在附近的草方块/泥土上随机放一朵花
        BlockPos.betweenClosedStream(
                girl.blockPosition().offset(-5, -2, -5),
                girl.blockPosition().offset(5, 2, 5)
        ).filter(pos -> {
            BlockState state = level.getBlockState(pos);
            return (state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT)) && level.isEmptyBlock(pos.above());
        }).findAny().ifPresent(pos -> {
            BlockPos above = pos.above();
            // 随机选一种花
            var flowers = new BlockState[] {
                    Blocks.POPPY.defaultBlockState(),
                    Blocks.DANDELION.defaultBlockState(),
                    Blocks.AZURE_BLUET.defaultBlockState(),
                    Blocks.OXEYE_DAISY.defaultBlockState(),
                    Blocks.CORNFLOWER.defaultBlockState()
            };
            level.setBlock(above, flowers[RANDOM.nextInt(flowers.length)], 3);
            girl.triggerAnimation("happy");
        });
    }

    /**
     * 女友自主行为：随机做可爱的事
     * 在 CreeperMother.tick() 中定期调用
     */
    public static void doRandomAffection(CreeperMother girl, Player husband) {
        if (girl.level().isClientSide || husband == null || girl.isSleeping()) return;

        // 每5~10分钟左右触发一次（6000~12000 ticks）
        if (girl.tickCount % 8000 != 0) return;
        if (RANDOM.nextFloat() > 0.3f) return;

        float roll = RANDOM.nextFloat();
        if (roll < 0.15f && girl.distanceToSqr(husband) < 9) {
            // 撒娇
            girl.triggerAnimation("coquetry");
            DeepSeekAPI.askAI(husband, girl, "（你正在撒娇，想让老公哄哄你）");
        } else if (roll < 0.3f && girl.distanceToSqr(husband) < 9) {
            // 主动抱抱
            girl.triggerAnimation("embrace");
            girl.adjustMood(3);
            DeepSeekAPI.askAI(husband, girl, "（你突然走过去抱住老公，温柔地贴着他）");
        } else if (roll < 0.4f) {
            // 哼歌（用粒子效果表现）
            girl.triggerAnimation("happy");
            if (RANDOM.nextFloat() < 0.5f) {
                girl.addMessageToMemory("system", "（你开心地哼起了歌）");
                husband.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                        "§d[" + girl.getDisplayName().getString() + "] §7♪ ♪ ♪ （轻轻哼着不知名的小曲）"));
            }
        } else if (roll < 0.5f && girl.distanceToSqr(husband) < 16) {
            // 跑过来找老公
            girl.getNavigation().moveTo(husband, 1.3);
            girl.triggerAnimation("jump");
            DeepSeekAPI.askAI(husband, girl, "（你蹦蹦跳跳跑到老公身边，甜甜地叫他老公）");
        }
    }

}