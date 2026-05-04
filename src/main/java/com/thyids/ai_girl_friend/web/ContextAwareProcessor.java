package com.thyids.ai_girl_friend.web;
import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 上下文感知处理器 - 增强AI对游戏环境的理解
 */
public class ContextAwareProcessor {

    /**
     * 获取【增强】游戏环境上下文（修复缺失方法）
     */
    public static String getEnhancedGameContext(CreeperMother girl, Player player) {
        return getGameContext(girl, player);
    }

    /**
     * 获取当前游戏环境上下文
     */
    public static String getGameContext(CreeperMother girl, Player player) {
        StringBuilder context = new StringBuilder();

        // 时间上下文
        LocalTime currentTime = LocalTime.now();
        String timeOfDay = getTimeOfDay(currentTime);
        context.append("【当前时间】现在是").append(currentTime.format(DateTimeFormatter.ofPattern("HH:mm"))).append("，属于").append(timeOfDay).append("\n");

        // 环境上下文
        Level world = girl.level();
        if (world != null) {
            int x = (int) girl.getX();
            int y = (int) girl.getY();
            int z = (int) girl.getZ();

            context.append("【当前位置】坐标: X=").append(x).append(", Y=").append(y).append(", Z=").append(z).append("\n");

            // 获取生物群系信息
            String biome = getBiomeInfo(world, x, y, z);
            context.append("【环境信息】当前处于").append(biome).append("生物群系\n");

            // 获取天气信息
            String weather = getWeatherInfo(world);
            context.append("【天气状况】").append(weather).append("\n");
        }

        // 玩家状态上下文
        if (player != null) {
            context.append("【玩家状态】").append(player.getName().getString()).append("的健康值: ").append((int) player.getHealth()).append("/20\n");
            context.append("【玩家状态】").append(player.getName().getString()).append("的饥饿值: ").append(player.getFoodData().getFoodLevel()).append("/20\n");
        }

        // 女友状态上下文
        context.append("【女友状态】").append("心情值: ").append(girl.getMoodLevel()).append("/100\n");
        if(player != null){
            context.append("【女友状态】").append("与玩家的距离: ").append((int) girl.distanceTo(player)).append("格\n");
        }

        // 世界状态
        boolean isDay = world != null && world.isDay();
        context.append("【世界状态】").append(isDay ? "白天" : "夜晚").append("\n");

        context.append("【附近实体】\n");
        List<Entity> nearbyEntities = girl.level().getEntities(girl,
                new AABB(girl.blockPosition()).inflate(16));
        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player) continue;
            context.append("- ").append(entity.getType().getDescription().getString()).append("\n");
        }

        // 玩家装备感知
        if (player != null) {
            context.append("【玩家装备】\n");
            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();
            context.append("- 主手: ").append(mainHand.isEmpty() ? "空" : mainHand.getDisplayName().getString()).append("\n");
            context.append("- 副手: ").append(offHand.isEmpty() ? "空" : offHand.getDisplayName().getString()).append("\n");
        }

        // 建筑/结构感知（修复缺失方法）
        context.append("【附近建筑】\n");
        detectNearbyStructures(girl, context);

        return context.toString();
    }

    /**
     * 检测附近建筑（补全缺失方法）
     */
    /**
     * 检测附近建筑（补全缺失方法 - 已修复床的判断）
     */
    private static void detectNearbyStructures(CreeperMother girl, StringBuilder context) {
        Level level = girl.level();
        BlockPos pos = girl.blockPosition();

        boolean hasHouse = false;
        boolean hasBed = false;
        boolean hasFurnace = false;

        // 简单扫描附近方块
        for (int dx = -8; dx <= 8; dx++) {
            for (int dz = -8; dz <= 8; dz++) {
                for (int dy = -4; dy <= 4; dy++) {
                    BlockPos checkPos = pos.offset(dx, dy, dz);
                    var block = level.getBlockState(checkPos).getBlock();

                    // ========== 修复这里 ==========
                    // 检测任意颜色的床
                    if (block == Blocks.RED_BED
                            || block == Blocks.BLUE_BED
                            || block == Blocks.CYAN_BED
                            || block == Blocks.GRAY_BED
                            || block == Blocks.LIGHT_BLUE_BED
                            || block == Blocks.LIGHT_GRAY_BED
                            || block == Blocks.LIME_BED
                            || block == Blocks.GREEN_BED
                            || block == Blocks.PINK_BED
                            || block == Blocks.MAGENTA_BED
                            || block == Blocks.ORANGE_BED
                            || block == Blocks.PURPLE_BED
                            || block == Blocks.WHITE_BED
                            || block == Blocks.YELLOW_BED
                            || block == Blocks.BLACK_BED) {
                        hasBed = true;
                    }

                    if (block == Blocks.FURNACE) hasFurnace = true;
                    if (block == Blocks.BRICKS || block == Blocks.OAK_PLANKS || block == Blocks.STONE_BRICKS) hasHouse = true;
                }
            }
        }

        if (hasHouse) context.append("- 发现房屋/建筑\n");
        if (hasBed) context.append("- 发现床\n");
        if (hasFurnace) context.append("- 发现熔炉\n");
        if (!hasHouse && !hasBed && !hasFurnace) context.append("- 野外/无建筑\n");
    }

    /**
     * 获取一天中的时段
     */
    private static String getTimeOfDay(LocalTime time) {
        int hour = time.getHour();
        if (hour >= 5 && hour < 9) return "清晨";
        else if (hour >= 9 && hour < 12) return "上午";
        else if (hour >= 12 && hour < 15) return "中午";
        else if (hour >= 15 && hour < 18) return "下午";
        else if (hour >= 18 && hour < 21) return "傍晚";
        else if (hour >= 21 || hour < 5) return "夜间";
        else return "时间";
    }

    /**
     * 获取生物群系信息
     */
    private static String getBiomeInfo(Level world, int x, int y, int z) {
        try {
            return world.getBiome(new BlockPos(x, y, z)).toString();
        } catch (Exception e) {
            return "森林";
        }
    }

    /**
     * 获取天气信息
     */
    private static String getWeatherInfo(Level world) {
        if (world == null) return "未知";

        if (world.isRaining() && world.isThundering()) {
            return "雷雨天气";
        } else if (world.isRaining()) {
            return "下雨";
        } else {
            return "晴朗";
        }
    }

    /**
     * 构建带有环境上下文的完整提示词
     */
    public static String buildContextualPrompt(CreeperMother girl, Player player, String userMessage) {
        StringBuilder prompt = new StringBuilder();

        // 环境上下文
        prompt.append("【环境上下文】\n");
        prompt.append(getEnhancedGameContext(girl, player));
        prompt.append("\n");

        // 感官体验
        prompt.append("【感官体验】\n");
        prompt.append(SensorySystem.getSensoryExperience(girl, player));
        prompt.append("\n");

        // 玩家档案
        prompt.append("【玩家档案】\n");
        prompt.append(PlayerBehaviorTracker.getPlayerProfile(player));
        prompt.append("\n");

        // 情感记忆
        prompt.append("【情感记忆】\n");
        prompt.append(EmotionalMemoryModule.getEmotionalContext(girl));
        prompt.append("\n");

        // 意图识别上下文
        prompt.append("【意图识别】\n");
        prompt.append(IntentRecognitionModule.generateIntentAwarePrompt(userMessage, girl, player));
        prompt.append("\n\n");

        // 基础角色设定
        prompt.append("【角色设定】\n");
        prompt.append("你是").append(player != null ? player.getName().getString() : "主人").append("的女朋友，名叫").append(girl.getCustomName() != null ? girl.getCustomName().getString() : "宝贝").append("。\n");
        prompt.append("你们正在Minecraft世界中一起冒险和生活。\n\n");

        // 行为指导
        prompt.append("【交互指导】\n");
        prompt.append("- 根据当前环境和时间调整对话和行为\n");
        prompt.append("- 考虑天气、时间、地点对互动的影响\n");
        prompt.append("- 根据状态提供自然的反应\n");
        prompt.append("- 保持可爱、温柔、粘人、专一的性格\n\n");

        // 人格
        prompt.append("【人格特征】\n");
        prompt.append(ChatProcessor.getPersonalityTraits(girl.getVariant())).append("\n\n");

        // 用户消息
        prompt.append("【用户消息】\n");
        prompt.append(userMessage);

        return prompt.toString();
    }
}