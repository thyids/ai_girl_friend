// 创建新文件：PersonalitySystem.java
package com.thyids.ai_girl_friend.gameplay;

import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;

import java.util.Random;

public class PersonalitySystem {

    // 性格类型
    public enum PersonalityType {
        TSUNDERE,    // 傲娇
        YANDERE,     // 病娇
        DANDELION,   // 温柔
        KUUDERE,     // 高冷
        GENKI,       // 元气
        SHY          // 害羞
    }

    // 性格特征
    public static class Personality {
        public PersonalityType type;
        public int affectionBonus;      // 亲密度加成
        public int moodVolatility;      // 心情波动幅度
        public int clinginess;          // 粘人程度 (0-10)
        public int jealousyLevel;       // 嫉妒心程度 (0-10)

        public String getPersonalityDescription() {
            return switch (type) {
                case TSUNDERE -> "傲娇型：口是心非，嘴上说着不要但心里很在意";
                case YANDERE -> "病娇型：非常依赖，占有欲很强";
                case DANDELION -> "温柔型：善解人意，总是体贴关心";
                case KUUDERE -> "高冷型：外表冷淡，内心温暖";
                case GENKI -> "元气型：充满活力，总是很开心";
                case SHY -> "害羞型：容易害羞脸红，说话小声";
            };
        }
    }

    private static final Random RANDOM = new Random();

    public static Personality generatePersonality(CreeperMother girl) {
        Personality personality = new Personality();

        // 根据变体决定性格
        int variant = girl.getVariant();
        personality.type = PersonalityType.values()[variant % PersonalityType.values().length];

        // 设置性格参数
        switch (personality.type) {
            case TSUNDERE -> {
                personality.affectionBonus = 5;
                personality.moodVolatility = 8;
                personality.clinginess = 6;
                personality.jealousyLevel = 7;
            }
            case YANDERE -> {
                personality.affectionBonus = 10;
                personality.moodVolatility = 10;
                personality.clinginess = 10;
                personality.jealousyLevel = 10;
            }
            case DANDELION -> {
                personality.affectionBonus = 8;
                personality.moodVolatility = 3;
                personality.clinginess = 5;
                personality.jealousyLevel = 3;
            }
            case KUUDERE -> {
                personality.affectionBonus = 3;
                personality.moodVolatility = 2;
                personality.clinginess = 2;
                personality.jealousyLevel = 4;
            }
            case GENKI -> {
                personality.affectionBonus = 6;
                personality.moodVolatility = 5;
                personality.clinginess = 7;
                personality.jealousyLevel = 5;
            }
            case SHY -> {
                personality.affectionBonus = 4;
                personality.moodVolatility = 6;
                personality.clinginess = 4;
                personality.jealousyLevel = 4;
            }
        }

        return personality;
    }

    // 根据性格调整对话风格
    public static String adjustResponseByPersonality(String response, Personality personality) {
        switch (personality.type) {
            case TSUNDERE -> {
                // 傲娇：在结尾加傲娇语气
                if (!response.contains("哼") && !response.contains("才不是")) {
                    response += " 哼！";
                }
            }
            case YANDERE -> {
                // 病娇：增加占有欲的表达
                response = response.replace("喜欢", "最喜欢")
                        .replace("想你", "好想好想你");
            }
            case DANDELION -> {
                // 温柔：增加关心的话语
                response += " 要照顾好自己哦~";
            }
            case KUUDERE -> {
                // 高冷：缩短回复，更简洁
                if (response.length() > 20) {
                    response = response.substring(0, 20) + "...";
                }
            }
            case GENKI -> {
                // 元气：添加表情符号
                response += " (≧∀≦)ﾉ";
            }
            case SHY -> {
                // 害羞：添加害羞表情
                response = "(害羞)" + response;
            }
        }
        return response;
    }
}
