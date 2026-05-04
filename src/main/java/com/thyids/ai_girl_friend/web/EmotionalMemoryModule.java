package com.thyids.ai_girl_friend.web;

import com.google.gson.JsonObject;
import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;

import java.util.HashMap;
import java.util.Map;

/**
 * 情感记忆模块 - 实现Python look项目中的情感记忆功能
 */
public class EmotionalMemoryModule {

    // 存储每个女友的情感记忆
    private static final Map<String, EmotionMemory> emotionalMemories = new HashMap<>();

    /**
     * 情感记忆数据结构
     * 修复：改为 public，允许外部访问
     */
    public static class EmotionMemory {
        public int happiness = 50;  // 幸福度
        public int trust = 50;      // 信任度
        public int affection = 50;  // 亲密度
        public int jealousy = 0;    // 嫉妒度
        public String firstMeetDate = "";         // 第一次见面日期
        public int daysTogether = 0;              // 在一起的天数
        public String specialEvents = "";         // 特殊事件记录
        public Map<String, Integer> memoryTags = new HashMap<>(); // 记忆标签
        public String lastInteraction = ""; // 最后互动内容
        public long lastInteractionTime = System.currentTimeMillis(); // 最后互动时间

        /**
         * 添加特殊事件记忆
         */
        public void addSpecialEvent(String event) {
            if (specialEvents.isEmpty()) {
                specialEvents = event;
            } else {
                specialEvents += " | " + event;
            }
            memoryTags.merge(event, 1, Integer::sum);
        }

        /**
         * 获取关系记忆描述
         */
        public String getRelationshipMemory() {
            StringBuilder memory = new StringBuilder();
            memory.append("【我们的故事】\n");
            if (!firstMeetDate.isEmpty()) {
                memory.append("我们在").append(firstMeetDate).append("相遇\n");
            }
            memory.append("已经在一起").append(daysTogether).append("天了\n");
            if (!specialEvents.isEmpty()) {
                memory.append("特别的回忆: ").append(specialEvents).append("\n");
            }
            return memory.toString();
        }

        public void updateEmotions(int moodChange) {
            // 根据心情变化更新情感值
            if (moodChange > 0) {
                happiness = Math.min(100, happiness + moodChange * 2);
                affection = Math.min(100, affection + moodChange);
                trust = Math.min(100, trust + moodChange / 2);
            } else {
                happiness = Math.max(0, happiness + moodChange * 2);
                affection = Math.max(0, affection + moodChange);
                trust = Math.max(0, trust + moodChange / 3);
            }

            // 确保嫉妒度不会过高
            jealousy = Math.max(0, jealousy - Math.abs(moodChange) / 5);
        }
    }

    /**
     * 获取或创建女友的情感记忆
     */
    public static EmotionMemory getOrCreateEmotionMemory(CreeperMother girl) {
        String key = girl.getUUID().toString();
        emotionalMemories.computeIfAbsent(key, k -> new EmotionMemory());
        return emotionalMemories.get(key);
    }

    /**
     * 更新情感记忆
     */
    public static void updateEmotionalMemory(CreeperMother girl, String interaction, int moodChange) {
        EmotionMemory memory = getOrCreateEmotionMemory(girl);

        memory.lastInteraction = interaction;
        memory.lastInteractionTime = System.currentTimeMillis();

        // 更新情感值
        memory.updateEmotions(moodChange);
    }

    /**
     * 获取情感记忆上下文
     */
    public static String getEmotionalContext(CreeperMother girl) {
        EmotionMemory memory = getOrCreateEmotionMemory(girl);

        StringBuilder context = new StringBuilder();
        context.append("【情感状态】\n");
        context.append("幸福度: ").append(memory.happiness).append("/100\n");
        context.append("信任度: ").append(memory.trust).append("/100\n");
        context.append("亲密度: ").append(memory.affection).append("/100\n");
        context.append("嫉妒度: ").append(memory.jealousy).append("/100\n");
        context.append("上次互动: ").append(memory.lastInteraction).append("\n");

        // 根据情感状态添加相应描述
        if (memory.happiness > 80) {
            context.append("当前心情: 非常愉快，对玩家充满好感\n");
        } else if (memory.happiness > 60) {
            context.append("当前心情: 心情不错，愿意与玩家互动\n");
        } else if (memory.happiness > 40) {
            context.append("当前心情: 心情一般，需要玩家关怀\n");
        } else {
            context.append("当前心情: 心情较差，可能不太愿意互动\n");
        }

        return context.toString();
    }

    /**
     * 获取情感记忆摘要
     */
    public static JsonObject getEmotionalSummary(CreeperMother girl) {
        EmotionMemory memory = getOrCreateEmotionMemory(girl);

        JsonObject summary = new JsonObject();
        summary.addProperty("happiness", memory.happiness);
        summary.addProperty("trust", memory.trust);
        summary.addProperty("affection", memory.affection);
        summary.addProperty("jealousy", memory.jealousy);
        summary.addProperty("last_interaction", memory.lastInteraction);

        return summary;
    }

    /**
     * 重置情感记忆
     */
    public static void resetEmotionalMemory(CreeperMother girl) {
        String key = girl.getUUID().toString();
        emotionalMemories.put(key, new EmotionMemory());
    }
}