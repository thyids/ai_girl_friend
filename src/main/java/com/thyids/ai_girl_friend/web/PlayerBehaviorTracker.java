// 创建新文件：PlayerBehaviorTracker.java
package com.thyids.ai_girl_friend.web;

import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PlayerBehaviorTracker {

    // 存储每个玩家的行为数据
    private static final Map<String, PlayerBehaviorData> behaviorData = new HashMap<>();

    public static class PlayerBehaviorData {
        public int totalInteractions = 0;      // 总互动次数
        public int giftsGiven = 0;             // 送礼物次数
        public int hugsGiven = 0;              // 拥抱次数
        public int kissesGiven = 0;            // 亲吻次数
        public int headPatsGiven = 0;          // 摸头次数
        public long lastOnlineTime = 0;        // 上次在线时间
        public String favoriteItem = "";       // 玩家常用物品
        public int playTimeMinutes = 0;        // 累计游玩时间

        // 记录玩家偏好
        public Map<String, Integer> itemUsage = new HashMap<>();
        public Map<String, Integer> activityCount = new HashMap<>();
    }

    /**
     * 记录玩家送礼物行为
     */
    public static void recordGift(Player player, CreeperMother girl, ItemStack item) {
        PlayerBehaviorData data = getOrCreateData(player);
        data.giftsGiven++;
        data.totalInteractions++;

        // 统计物品偏好
        String itemName = item.getDisplayName().getString();
        data.itemUsage.merge(itemName, 1, Integer::sum);

        // 更新最喜欢的物品
        updateFavoriteItem(data);
    }

    /**
     * 记录互动行为
     */
    public static void recordInteraction(Player player, String interactionType) {
        PlayerBehaviorData data = getOrCreateData(player);
        data.totalInteractions++;
        data.activityCount.merge(interactionType, 1, Integer::sum);

        switch (interactionType) {
            case "hug" -> data.hugsGiven++;
            case "kiss" -> data.kissesGiven++;
            case "head_pat" -> data.headPatsGiven++;
        }
    }

    /**
     * 获取玩家行为描述
     */
    public static String getPlayerProfile(Player player) {
        PlayerBehaviorData data = getOrCreateData(player);

        StringBuilder profile = new StringBuilder();
        profile.append("【玩家档案】\n");
        profile.append("总互动次数: ").append(data.totalInteractions).append("次\n");
        profile.append("送礼物: ").append(data.giftsGiven).append("次\n");
        profile.append("拥抱: ").append(data.hugsGiven).append("次\n");
        profile.append("亲吻: ").append(data.kissesGiven).append("次\n");
        profile.append("摸头: ").append(data.headPatsGiven).append("次\n");
        profile.append("最喜欢的物品: ").append(data.favoriteItem.isEmpty() ? "未记录" : data.favoriteItem).append("\n");

        // 分析互动偏好
        if (data.hugsGiven > data.kissesGiven * 2) {
            profile.append("互动偏好: 喜欢拥抱，比较温柔\n");
        } else if (data.kissesGiven > data.hugsGiven * 2) {
            profile.append("互动偏好: 喜欢亲吻，比较热情\n");
        } else if (data.headPatsGiven > data.hugsGiven) {
            profile.append("互动偏好: 喜欢摸头，喜欢被宠爱\n");
        }

        return profile.toString();
    }

    private static PlayerBehaviorData getOrCreateData(Player player) {
        String key = player.getUUID().toString();
        behaviorData.computeIfAbsent(key, k -> new PlayerBehaviorData());
        return behaviorData.get(key);
    }

    private static void updateFavoriteItem(PlayerBehaviorData data) {
        String favorite = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : data.itemUsage.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                favorite = entry.getKey();
            }
        }
        data.favoriteItem = favorite;
    }
}