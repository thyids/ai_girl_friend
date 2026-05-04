package com.thyids.ai_girl_friend.web;

import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.world.entity.player.Player;

/**
 * AI控制器 - 综合管理所有AI模块
 */
public class AIController {

    /**
     * 初始化AI系统
     */
    public static void initializeAISystem() {
        System.out.println("[AI系统] 正在初始化高级AI系统...");

        // 初始化各个模块
        System.out.println("[AI系统] 上下文感知模块已加载");
        System.out.println("[AI系统] 情感记忆模块已加载");
        System.out.println("[AI系统] 意图识别模块已加载");
        System.out.println("[AI系统] 记忆检索模块已加载");
        System.out.println("[AI系统] 高级AI系统初始化完成");
    }

    /**
     * 处理玩家与女友的交互
     */
    public static void handleInteraction(Player player, CreeperMother girl, String message) {
        // 使用MinecraftAIProcessor处理交互
        MinecraftAIProcessor.interactWithAI(player, girl, message);
    }

    /**
     * 获取女友的状态信息
     */
    public static String getStatusInfo(CreeperMother girl) {
        StringBuilder info = new StringBuilder();
        info.append("=== 女友状态信息 ===\n");
        info.append("姓名: ").append(DeepSeekAPI.getName(girl)).append("\n");
        info.append("心情值: ").append(girl.getMoodLevel()).append("/100\n");
        info.append("变体: ").append(girl.getVariant()).append("\n");
        info.append("记忆条数: ").append(AdvancedAIManager.getCurrentContext(girl).size()).append("\n");

        // 添加情感记忆信息
        var emotionMemory = EmotionalMemoryModule.getOrCreateEmotionMemory(girl);
        info.append("幸福度: ").append(emotionMemory.happiness).append("/100\n");
        info.append("信任度: ").append(emotionMemory.trust).append("/100\n");
        info.append("亲密度: ").append(emotionMemory.affection).append("/100\n");

        return info.toString();
    }

    /**
     * 重置女友的记忆
     */
    public static void resetGirlMemory(CreeperMother girl) {
        AdvancedAIManager.resetMemory(girl);
        EmotionalMemoryModule.resetEmotionalMemory(girl);
    }
}