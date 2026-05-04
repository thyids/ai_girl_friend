package com.thyids.ai_girl_friend.gameplay;

import com.thyids.ai_girl_friend.Ai_girl_friend;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 数据持久化系统：
 * - 保存每个玩家的互动数据（礼物次数、亲密度、成就等）
 * - 数据写入玩家的 persisted NBT
 */
@Mod.EventBusSubscriber(modid = Ai_girl_friend.MOD_ID)
public class PlayerDataManager {

    private static final String GIRLFRIEND_TAG = "ai_girlfriend_data";

    /**
     * 获取玩家的女友数据
     */
    public static CompoundTag getData(ServerPlayer player) {
        CompoundTag persistent = player.getPersistentData();
        if (!persistent.contains(GIRLFRIEND_TAG)) {
            persistent.put(GIRLFRIEND_TAG, new CompoundTag());
        }
        return persistent.getCompound(GIRLFRIEND_TAG);
    }

    /**
     * 记录玩家送花的数量
     */
    public static void addFlowerGiven(ServerPlayer player) {
        CompoundTag data = getData(player);
        int count = data.getInt("flowers_given") + 1;
        data.putInt("flowers_given", count);

        // 成就检测
        if (count == 10) {
            player.displayClientMessage(net.minecraft.network.chat.Component.literal(
                "§6🏆 成就解锁：『第一束花』（送花10次）"), true);
        } else if (count == 50) {
            player.displayClientMessage(net.minecraft.network.chat.Component.literal(
                "§6🏆 成就解锁：『花店老板』（送花50次）"), true);
        } else if (count == 100) {
            player.displayClientMessage(net.minecraft.network.chat.Component.literal(
                "§6🏆 成就解锁：『满园花香』（送花100次）"), true);
        }
    }

    /**
     * 记录召唤女友次数
     */
    public static void addSummonCount(ServerPlayer player) {
        CompoundTag data = getData(player);
        int count = data.getInt("summon_count") + 1;
        data.putInt("summon_count", count);
    }

    /**
     * 获取送花次数
     */
    public static int getFlowerGiven(ServerPlayer player) {
        return getData(player).getInt("flowers_given");
    }

    /**
     * 获取召唤次数
     */
    public static int getSummonCount(ServerPlayer player) {
        return getData(player).getInt("summon_count");
    }
}
