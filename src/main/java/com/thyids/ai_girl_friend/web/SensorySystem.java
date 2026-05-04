// 创建新文件：SensorySystem.java
package com.thyids.ai_girl_friend.web;

import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.world.entity.player.Player;

public class SensorySystem {

    /**
     * 获取女友的感官体验描述
     */
    public static String getSensoryExperience(CreeperMother girl, Player player) {
        StringBuilder sensory = new StringBuilder();

        // 视觉感知
        sensory.append("【视觉】");
        if (girl.level().isNight()) {
            sensory.append("夜色笼罩，星空闪烁");
        } else if (girl.level().isRaining()) {
            sensory.append("细雨绵绵，视线有些模糊");
        } else {
            sensory.append("阳光明媚，视野清晰");
        }

        // 听觉感知
        sensory.append("【听觉】");
        if (player != null && player.distanceTo(girl) < 5) {
            sensory.append("听到老公的脚步声");
        }
        if (girl.level().isThundering()) {
            sensory.append("，远处传来雷声");
        }

        // 距离感知
        if (player != null) {
            double dist = girl.distanceTo(player);
            sensory.append("【距离】");
            if (dist < 2) sensory.append("老公就在身边");
            else if (dist < 10) sensory.append("老公在不远处");
            else if (dist < 30) sensory.append("老公在视野范围内");
            else sensory.append("老公离我有点远");
        }

        return sensory.toString();
    }
}