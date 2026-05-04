package com.thyids.ai_girl_friend.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * 高级AI管理器 - 整合Python look项目中的内存管理和上下文功能
 */
public class AdvancedAIManager {

    // 存储每个女友的记忆
    private static final Map<String, JsonArray> girlfriendMemories = new HashMap<>();

    /**
     * 获取或创建女友的记忆
     */
    public static JsonArray getOrCreateMemory(CreeperMother girl) {
        String key = girl.getUUID().toString();
        if (!girlfriendMemories.containsKey(key)) {
            girlfriendMemories.put(key, new JsonArray());
        }
        return girlfriendMemories.get(key);
    }

    /**
     * 更新女友的记忆
     */
    public static void updateMemory(CreeperMother girl, JsonArray newMemory) {
        String key = girl.getUUID().toString();
        girlfriendMemories.put(key, newMemory);
    }

    /**
     * 添加消息到记忆
     */
    public static void addMessageToMemory(CreeperMother girl, String role, String content) {
        JsonArray memory = getOrCreateMemory(girl);
        JsonObject message = new JsonObject();
        message.addProperty("role", role);
        message.addProperty("content", content);
        message.addProperty("timestamp", System.currentTimeMillis());

        memory.add(message);

        // 限制记忆长度，超过限制时进行压缩
        if (memory.size() > 20) { // 假设最大记忆长度为20
            compressMemory(girl);
        }
    }

    /**
     * 压缩记忆 - 将旧对话总结成背景信息
     */
    public static void compressMemory(CreeperMother girl) {
        JsonArray fullMemory = getOrCreateMemory(girl);
        if (fullMemory.size() <= 5) { // 如果记忆不够多，不需要压缩
            return;
        }

        // 创建新的压缩记忆
        JsonArray compressedMemory = new JsonArray();

        // ✅ 修复：先添加总结消息到最前面
        JsonObject summary = new JsonObject();
        summary.addProperty("role", "system");
        summary.addProperty("content", "这是之前的对话摘要：玩家和AI女友进行了多次交流，建立了良好的关系。");
        compressedMemory.add(summary);

        // 保留最后3条消息
        int keepCount = Math.min(3, fullMemory.size());
        for (int i = fullMemory.size() - keepCount; i < fullMemory.size(); i++) {
            compressedMemory.add(fullMemory.get(i));
        }

        // 更新记忆
        updateMemory(girl, compressedMemory);
    }

    /**
     * 获取当前记忆上下文
     */
    public static JsonArray getCurrentContext(CreeperMother girl) {
        return getOrCreateMemory(girl); // 直接返回即可，简化代码
    }

    /**
     * 重置女友记忆
     */
    public static void resetMemory(CreeperMother girl) {
        String key = girl.getUUID().toString();
        girlfriendMemories.put(key, new JsonArray());
    }

    /**
     * 获取记忆统计信息
     */
    public static String getMemoryStats(CreeperMother girl) {
        JsonArray memory = getOrCreateMemory(girl);
        return String.format("记忆条数: %d 条", memory.size());
    }
}