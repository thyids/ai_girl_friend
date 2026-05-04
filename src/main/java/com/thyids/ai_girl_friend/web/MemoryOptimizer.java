package com.thyids.ai_girl_friend.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;

import java.util.ArrayList;
import java.util.List;

/**
 * 记忆优化器 - 实现Python look项目中的高级记忆管理功能
 */
public class MemoryOptimizer {

    /**
     * 智能记忆压缩 - 将多个对话转换为精华摘要
     */
    public static JsonArray compressMemorySmart(CreeperMother girl, JsonArray fullMemory) {
        if (fullMemory.size() <= 10) { // 如果记忆不多，无需压缩
            return fullMemory;
        }

        JsonArray compressedMemory = new JsonArray();

        // 先添加摘要（修复：JsonArray不支持add(0,xx)，只能先加）
        String keyInfoSummary = extractKeyInformation(fullMemory);
        JsonObject summary = new JsonObject();
        summary.addProperty("role", "system");
        summary.addProperty("content", "重要信息摘要：" + keyInfoSummary);
        compressedMemory.add(summary);

        // 保留最近的重要对话
        int keepRecent = Math.min(5, fullMemory.size());
        for (int i = fullMemory.size() - keepRecent; i < fullMemory.size(); i++) {
            compressedMemory.add(fullMemory.get(i));
        }

        return compressedMemory;
    }

    /**
     * 提取关键信息
     */
    private static String extractKeyInformation(JsonArray memory) {
        StringBuilder keyInfo = new StringBuilder();

        // 分析记忆中的关键内容
        for (int i = 0; i < memory.size(); i++) {
            if (memory.get(i).isJsonObject()) {
                JsonObject message = memory.get(i).getAsJsonObject();
                String content = message.get("content") != null ? message.get("content").getAsString() : "";
                String role = message.get("role") != null ? message.get("role").getAsString() : "";

                // 提取用户偏好、重要承诺等
                if (content.contains("喜欢") || content.contains("讨厌") || content.contains("想要") ||
                        content.contains("记得") || content.contains("希望")) {
                    if (keyInfo.length() > 0) keyInfo.append("; ");
                    keyInfo.append(content.length() > 100 ? content.substring(0, 100) + "..." : content);
                }
            }
        }

        if (keyInfo.length() == 0) {
            keyInfo.append("玩家与AI女友有多次交流经验，关系良好");
        }

        return keyInfo.toString();
    }

    /**
     * 记忆清理 - 移除过时或冗余的记忆
     */
    public static JsonArray cleanMemory(CreeperMother girl, JsonArray memory) {
        JsonArray cleanedMemory = new JsonArray();

        // 过滤掉重复或无意义的消息
        List<String> seenContents = new ArrayList<>();

        for (int i = 0; i < memory.size(); i++) {
            if (memory.get(i).isJsonObject()) {
                JsonObject message = memory.get(i).getAsJsonObject();
                String content = message.get("content") != null ? message.get("content").getAsString() : "";
                String role = message.get("role") != null ? message.get("role").getAsString() : "";

                // 跳过重复内容或过于简单的消息
                if (!seenContents.contains(content) && content.trim().length() > 3) {
                    cleanedMemory.add(message);
                    seenContents.add(content);
                }
            }
        }

        return cleanedMemory;
    }

    /**
     * 根据时间衰减记忆重要性
     */
    public static JsonArray decayMemoryImportance(CreeperMother girl, JsonArray memory) {
        // 在实际实现中，这可以根据时间戳降低旧记忆的重要性
        // 为了简化，我们这里返回原始记忆
        return memory;
    }

    /**
     * 记忆优先级排序
     */
    public static JsonArray prioritizeMemory(CreeperMother girl, JsonArray memory) {
        JsonArray prioritizedMemory = new JsonArray();
        JsonArray lowPriorityMemory = new JsonArray();

        // 将系统消息和重要信息放在前面
        for (int i = 0; i < memory.size(); i++) {
            if (memory.get(i).isJsonObject()) {
                JsonObject message = memory.get(i).getAsJsonObject();
                String role = message.get("role") != null ? message.get("role").getAsString() : "";
                String content = message.get("content") != null ? message.get("content").getAsString() : "";

                if (role.equals("system") || isHighPriority(content)) {
                    prioritizedMemory.add(message);
                } else {
                    lowPriorityMemory.add(message);
                }
            }
        }

        // 添加低优先级记忆
        for (int i = 0; i < lowPriorityMemory.size(); i++) {
            prioritizedMemory.add(lowPriorityMemory.get(i));
        }

        return prioritizedMemory;
    }

    /**
     * 判断是否为高优先级内容
     */
    private static boolean isHighPriority(String content) {
        // 包含重要关键词的内容被认为是高优先级
        String lowerContent = content.toLowerCase();
        return lowerContent.contains("喜欢") || lowerContent.contains("讨厌") ||
                lowerContent.contains("记住") || lowerContent.contains("重要") ||
                lowerContent.contains("承诺") || lowerContent.contains("约定") ||
                lowerContent.contains("生日") || lowerContent.contains("纪念日");
    }
}