// 创建新文件：LongTermMemory.java
package com.thyids.ai_girl_friend.web;

import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.world.entity.player.Player;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LongTermMemory {

    private static final Map<String, RelationshipMemory> memories = new HashMap<>();

    public static class RelationshipMemory {
        public String firstMeetDate;           // 相遇日期
        public int daysTogether;               // 在一起天数
        public List<String> specialEvents;     // 特殊事件列表
        public Map<String, Integer> habits;    // 玩家习惯记录
        public String favoriteActivity;        // 最喜欢的活动
        public String lastGift;                // 最后收到的礼物

        public RelationshipMemory() {
            this.firstMeetDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
            this.daysTogether = 1;
            this.specialEvents = new ArrayList<>();
            this.habits = new HashMap<>();
        }

        public void addSpecialEvent(String event) {
            String timestamp = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
            specialEvents.add(timestamp + ": " + event);
        }

        public void recordHabit(String habit) {
            habits.merge(habit, 1, Integer::sum);
        }

        public String getMemorySummary() {
            StringBuilder sb = new StringBuilder();
            sb.append("我们在一起 ").append(daysTogether).append(" 天了\n");
            sb.append("最喜欢的活动: ").append(favoriteActivity != null ? favoriteActivity : "还在探索中\n");
            if (!specialEvents.isEmpty()) {
                sb.append("美好的回忆: ").append(String.join(" | ", specialEvents));
            }
            return sb.toString();
        }
    }

    public static void initializeMemory(CreeperMother girl) {
        String key = girl.getUUID().toString();
        memories.computeIfAbsent(key, k -> new RelationshipMemory());
    }

    public static void updateDaysTogether(CreeperMother girl) {
        RelationshipMemory memory = memories.get(girl.getUUID().toString());
        if (memory != null) {
            memory.daysTogether++;
        }
    }

    public static String getRelationshipContext(CreeperMother girl, Player player) {
        RelationshipMemory memory = memories.get(girl.getUUID().toString());
        if (memory == null) return "";
        return memory.getMemorySummary();
    }
}