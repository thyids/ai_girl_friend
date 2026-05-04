package com.thyids.ai_girl_friend.world.entity;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.thyids.ai_girl_friend.Ai_girl_friend;
import com.thyids.ai_girl_friend.gui.AIConfigScreen;
import com.thyids.ai_girl_friend.web.AIConfig;
import com.thyids.ai_girl_friend.web.DeepSeekAPI;
import com.thyids.ai_girl_friend.web.SpeakerManager;
import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Ai_girl_friend.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModServerEvents {

    private static final double LOOK_RANGE = 8.0D;

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("girlanim")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("anim", StringArgumentType.string())
                                .executes(ctx -> {
                                    String animName = StringArgumentType.getString(ctx, "anim");
                                    Player player = ctx.getSource().getPlayer();
                                    if (player == null) return 0;

                                    for (CreeperMother girl : player.level().getEntitiesOfClass(CreeperMother.class, player.getBoundingBox().inflate(10))) {
                                        girl.triggerAnimation(animName);
                                    }

                                    ctx.getSource().sendSuccess(() -> Component.literal("§d已播放动画：" + animName), false);
                                    return 1;
                                })
                        )
        );

        // AI配置命令
        event.getDispatcher().register(
                Commands.literal("aiconfig")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("get")
                                .executes(ctx -> {
                                    String key = AIConfig.getApiKey();
                                    // 隐藏API密钥的中间部分
                                    String maskedKey = key.length() > 8 ? key.substring(0, 4) + "****" + key.substring(key.length() - 4) : key;
                                    String base = AIConfig.getApiBase();
                                    ctx.getSource().sendSuccess(() -> Component.literal(
                                            "§dAI配置信息:\n"
                                            + "§fAPI Key: §a" + maskedKey + "\n"
                                            + "§fAPI Base: §a" + base
                                    ), false);
                                    return 1;
                                })
                        )
                        .then(Commands.literal("set")
                                .then(Commands.literal("key")
                                        .then(Commands.argument("apikey", StringArgumentType.string())
                                                .executes(ctx -> {
                                                    String apiKey = StringArgumentType.getString(ctx, "apikey");
                                                    AIConfig.setApiKey(apiKey);
                                                    ctx.getSource().sendSuccess(() -> Component.literal("§dAPI密钥已更新！"), false);
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("base")
                                        .then(Commands.argument("apibase", StringArgumentType.string())
                                                .executes(ctx -> {
                                                    String apiBase = StringArgumentType.getString(ctx, "apibase");
                                                    AIConfig.setApiBase(apiBase);
                                                    ctx.getSource().sendSuccess(() -> Component.literal("§dAPI地址已更新！"), false);
                                                    return 1;
                                                })
                                        )
                                )
                        )
                        .executes(ctx -> {
                            // 不带参数时打开GUI
                            Player player = ctx.getSource().getPlayer();
                            if (player != null) {
                                player.sendSystemMessage(Component.literal("§d正在打开AI配置界面..."));
                            }
                            return 1;
                        })
        );
    }

    @SubscribeEvent
    public static void onBedInteract(PlayerInteractEvent.RightClickBlock event) {
        BlockPos pos = event.getPos();
        Level level = event.getLevel();
        Player player = event.getEntity();
        BlockState state = level.getBlockState(pos);

        if (!(state.getBlock() instanceof BedBlock)) return;

        // 检查床上是否有其他玩家的女友
        List<CreeperMother> sleepingGirls = level.getEntitiesOfClass(CreeperMother.class, new AABB(pos).inflate(1.5D));
        for (CreeperMother sg : sleepingGirls) {
            UUID husband = sg.getHusbandUUID();
            if (husband != null && !husband.equals(player.getUUID())) {
                player.displayClientMessage(Component.literal("§c[" + sg.getName().getString() + "] §f不许上来！这是我和我老公的床！"), true);
                event.setCanceled(true);
                return;
            }
        }

        // 强制允许玩家白天上床
        if (player instanceof ServerPlayer serverPlayer) {
            Direction facing = state.getValue(BedBlock.FACING);
            BlockPos headPos = state.getValue(BedBlock.PART) == net.minecraft.world.level.block.state.properties.BedPart.HEAD ? pos : pos.relative(facing);
            
            // 不管白天晚上都允许上床
            serverPlayer.startSleeping(headPos);
            event.setCanceled(true);
            
            // 显示女友状态提示
            List<CreeperMother> girls = level.getEntitiesOfClass(CreeperMother.class, new AABB(pos).inflate(8D));
            for (CreeperMother girl : girls) {
                UUID husband = girl.getHusbandUUID();
                if (husband != null && husband.equals(player.getUUID())) {
                    player.displayClientMessage(Component.literal("§d[" + girl.getName().getString() + "] §f心情: " + girl.getMoodLevel()), true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
        Player player = event.getEntity();
        Level level = player.level();
        
        // 取消默认事件处理，避免游戏拒绝白天睡觉
        event.setCanceled(true);
        
        // 使用 startSleeping 方法强制让玩家睡觉
        if (player instanceof ServerPlayer serverPlayer) {
            BlockPos bedPos = event.getPos();
            serverPlayer.startSleeping(bedPos);
        }
        
        // 玩家上床时，更新女友的状态（但不自动上床）
        List<CreeperMother> girls = level.getEntitiesOfClass(CreeperMother.class, player.getBoundingBox().inflate(8D));
        for (CreeperMother girl : girls) {
            UUID husband = girl.getHusbandUUID();
            if (husband != null && husband.equals(player.getUUID())) {
                girl.getEntityData().set(CreeperMother.IS_SIDE_LYING, true);
                // 记录老公上床了，但需要邀请才会跟随
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof Player husband)) return;
        LivingEntity victim = event.getEntity();

        List<CreeperMother> girls = husband.level().getEntitiesOfClass(CreeperMother.class, husband.getBoundingBox().inflate(10));

        for (CreeperMother girl : girls) {
            if (husband.getUUID().equals(girl.getHusbandUUID()) && victim instanceof Mob mobVictim) {
                if (mobVictim.getTarget() == girl) {
                    girl.onHusbandDefendedMe(husband);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            SpeakerManager.tick();
        }
    }

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        Player player = event.getPlayer();
        String msg = event.getRawText();
        Level level = player.level();

        List<CreeperMother> girls = level.getEntitiesOfClass(CreeperMother.class, player.getBoundingBox().inflate(15));

        if (msg.contains("所有老婆") || msg.contains("老婆们") || msg.contains("女朋友们")) {
            for (CreeperMother girl : girls) {
                if (player.getUUID().equals(girl.getHusbandUUID())) {
                    DeepSeekAPI.askAI(player, girl, msg);
                }
            }
            return;
        }

        for (CreeperMother girl : girls) {
            String name = girl.getName().getString();
            if (msg.contains(name) || msg.contains("亲爱的")) {
                if (player.getUUID().equals(girl.getHusbandUUID())) {
                    DeepSeekAPI.askAI(player, girl, msg);
                }
                return;
            }
        }

        HitResult hit = player.pick(LOOK_RANGE, 1F, false);
        if (hit instanceof EntityHitResult entityHit && entityHit.getEntity() instanceof CreeperMother girl) {
            if (player.getUUID().equals(girl.getHusbandUUID())) {
                DeepSeekAPI.askAI(player, girl, msg);
            }
        }
    }
}