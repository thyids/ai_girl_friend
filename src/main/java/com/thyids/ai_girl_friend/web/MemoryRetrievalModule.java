package com.thyids.ai_girl_friend.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;

import java.util.ArrayList;
import java.util.List;

/**
 * 记忆检索模块 - 实现Python look项目的记忆检索和相似性匹配功能
 */
public class MemoryRetrievalModule {

    /**
     * 相似度阈值 - 用于判断记忆是否相关
     */
    private static final double SIMILARITY_THRESHOLD = 0.6;

    /**
     * 计算两个字符串的相似度（简化版Jaccard相似度）
     */
    public static double calculateSimilarity(String str1, String str2) {
        if (str1 == null || str2 == null) return 0.0;
        if (str1.equals(str2)) return 1.0;

        // 分割字符串为词汇集合
        String[] words1 = str1.toLowerCase().split("\\s+");
        String[] words2 = str2.toLowerCase().split("\\s+");

        // 使用集合来计算交集和并集
        java.util.Set<String> set1 = new java.util.HashSet<>();
        java.util.Set<String> set2 = new java.util.HashSet<>();

        for (String word : words1) set1.add(word.replaceAll("[^a-zA-Z0-9\u4e00-\u9fa5]", ""));
        for (String word : words2) set2.add(word.replaceAll("[^a-zA-Z0-9\u4e00-\u9fa5]", ""));

        // 计算交集
        java.util.Set<String> intersection = new java.util.HashSet<>(set1);
        intersection.retainAll(set2);

        // 计算并集
        java.util.Set<String> union = new java.util.HashSet<>(set1);
        union.addAll(set2);

        if (union.isEmpty()) return 0.0;

        return (double) intersection.size() / union.size();
    }

    /**
     * 检索相关记忆
     */
    public static JsonArray retrieveRelatedMemories(CreeperMother girl, String query, int maxResults) {
        JsonArray allMemories = AdvancedAIManager.getCurrentContext(girl);
        JsonArray relatedMemories = new JsonArray();

        // 存储记忆及其相似度
        List<MemoryWithScore> scoredMemories = new ArrayList<>();

        for (int i = 0; i < allMemories.size(); i++) {
            if (allMemories.get(i).isJsonObject()) {
                JsonObject memory = allMemories.get(i).getAsJsonObject();
                String content = memory.get("content") != null ? memory.get("content").getAsString() : "";

                double similarity = calculateSimilarity(query, content);

                if (similarity >= SIMILARITY_THRESHOLD) {
                    MemoryWithScore mws = new MemoryWithScore(memory, similarity);
                    scoredMemories.add(mws);
                }
            }
        }

        // 按相似度降序排序
        scoredMemories.sort((a, b) -> Double.compare(b.score, a.score));

        // 取前maxResults个相关记忆
        int count = Math.min(maxResults, scoredMemories.size());
        for (int i = 0; i < count; i++) {
            relatedMemories.add(scoredMemories.get(i).memory);
        }

        return relatedMemories;
    }

    /**
     * 为AI生成记忆检索上下文
     */
    public static String generateMemoryContext(CreeperMother girl, String query) {
        JsonArray relatedMemories = retrieveRelatedMemories(girl, query, 5); // 最多返回5个相关记忆

        if (relatedMemories.size() == 0) {
            return "【相关记忆】没有找到与当前话题相关的过往记忆。";
        }

        StringBuilder context = new StringBuilder();
        context.append("【相关记忆】以下是从长期记忆中检索到的相关信息：\n");

        for (int i = 0; i < relatedMemories.size(); i++) {
            if (relatedMemories.get(i).isJsonObject()) {
                JsonObject memory = relatedMemories.get(i).getAsJsonObject();
                String role = memory.get("role") != null ? memory.get("role").getAsString() : "unknown";
                String content = memory.get("content") != null ? memory.get("content").getAsString() : "";
                long timestamp = memory.get("timestamp") != null ? memory.get("timestamp").getAsLong() : 0;

                context.append(String.format("- [%s] %s (时间戳: %d)\n", role, content, timestamp));
            }
        }

        return context.toString();
    }

    /**
     * 内部类：带分数的记忆
     */
    private static class MemoryWithScore {
        public JsonObject memory;
        public double score;

        public MemoryWithScore(JsonObject memory, double score) {
            this.memory = memory;
            this.score = score;
        }
    }
}