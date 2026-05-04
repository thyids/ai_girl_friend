package com.thyids.ai_girl_friend.web;

import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * 意图识别模块 - 实现Python look项目的意图识别功能
 */
public class IntentRecognitionModule {

    public enum IntentType {
        CHAT,           // 闲聊
        REQUEST_HELP,   // 请求帮助
        EXPLORE,        // 探索建议
        CRAFT,          // 合成制作
        BUILD,          // 建造
        FARM,           // 农业
        COMBAT,         // 战斗
        SOCIAL,         // 社交互动
        TASK_ASSIGNMENT,// 任务分配
        EMOTIONAL       // 情感表达
    }

    /**
     * 识别用户意图
     */
    public static IntentType recognizeIntent(String message, CreeperMother girl, Player player) {
        String lowerMessage = message.toLowerCase();

        // 检查是否为请求帮助
        if (containsKeywords(lowerMessage, "帮助", "怎么", "如何", "怎么做", "不知道", "不会", "教我", "告诉我")) {
            return IntentType.REQUEST_HELP;
        }

        // 检查是否为探索相关
        if (containsKeywords(lowerMessage, "探索", "冒险", "去", "走", "周围", "看看", "游览", "发现")) {
            return IntentType.EXPLORE;
        }

        // 检查是否为合成制作
        if (containsKeywords(lowerMessage, "制作", "合成", "做", "怎么做", "配方", "材料", "工具", "武器", "盔甲")) {
            return IntentType.CRAFT;
        }

        // 检查是否为建造
        if (containsKeywords(lowerMessage, "建造", "建筑", "房子", "建", "盖", "设计", "装饰", "装修")) {
            return IntentType.BUILD;
        }

        // 检查是否为农业
        if (containsKeywords(lowerMessage, "种植", " farming", "农", "田", "种子", "作物", "食物", "养殖")) {
            return IntentType.FARM;
        }

        // 检查是否为战斗
        if (containsKeywords(lowerMessage, "战斗", "打", "怪物", "敌人", "保护", "防御", "攻击", "杀")) {
            return IntentType.COMBAT;
        }

        // 检查是否为社交互动
        if (containsKeywords(lowerMessage, "聊天", "说话", "谈谈", "聊聊", "故事", "一起", "和你", "陪你")) {
            return IntentType.SOCIAL;
        }

        // 检查是否为任务分配
        if (containsKeywords(lowerMessage, "任务", "工作", "帮", "帮我", "让", "去", "fetch", "get", "bring")) {
            return IntentType.TASK_ASSIGNMENT;
        }

        // 检查是否为情感表达
        if (containsKeywords(lowerMessage, "爱", "喜欢", "想你", "想念", "想", "亲", "抱", "开心", "快乐", "难过", "伤心")) {
            return IntentType.EMOTIONAL;
        }

        // 默认为闲聊
        return IntentType.CHAT;
    }

    /**
     * 检查消息是否包含特定关键词
     */
    private static boolean containsKeywords(String message, String... keywords) {
        for (String keyword : keywords) {
            if (message.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据意图类型生成相应的AI提示词补充
     */
    public static String getIntentContext(IntentType intent, CreeperMother girl, Player player) {
        switch (intent) {
            case REQUEST_HELP:
                return "用户正在寻求帮助，你应该提供详细、有用的指导信息，结合Minecraft游戏机制给出具体建议。";
            case EXPLORE:
                return "用户想要探索，你应该推荐有趣的地方，提醒安全事项，可以提议一起去冒险。";
            case CRAFT:
                return "用户询问制作合成，你应该提供详细的合成配方和制作步骤，推荐有用的物品。";
            case BUILD:
                return "用户想要建造，你应该提供建筑创意、设计建议和建筑材料选择。";
            case FARM:
                return "用户关注农业，你应该提供种植技巧、作物生长周期和高效农场设计。";
            case COMBAT:
                return "用户关注战斗，你应该提供装备建议、战斗策略和安全防护措施。";
            case SOCIAL:
                return "用户想要社交互动，你应该进行自然的对话，分享想法和感受。";
            case TASK_ASSIGNMENT:
                return "用户分配任务，你应该积极回应，确认理解任务要求。";
            case EMOTIONAL:
                return "用户表达情感，你应该做出适当的感情回应，体现关心和理解。";
            default:
                return "用户在进行普通聊天，你可以自由地进行对话。";
        }
    }

    /**
     * 为AI生成意图感知的提示词
     */
    public static String generateIntentAwarePrompt(String originalMessage, CreeperMother girl, Player player) {
        IntentType intent = recognizeIntent(originalMessage, girl, player);
        String intentContext = getIntentContext(intent, girl, player);

        return String.format(
                "用户输入：'%s'\n意图分析：%s\n建议回应方式：%s",
                originalMessage,
                intent.name(),
                intentContext
        );
    }
}