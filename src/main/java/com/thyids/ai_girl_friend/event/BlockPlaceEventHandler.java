package com.thyids.ai_girl_friend.event;

import com.thyids.ai_girl_friend.Ai_girl_friend;
import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.BedBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Ai_girl_friend.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockPlaceEventHandler {

    // 检测范围：20格内的女友
    private static final double DETECT_RANGE = 20.0;

    @SubscribeEvent
    public static void onBlockPlace(PlayerInteractEvent.RightClickBlock event) {
        // 只处理服务器端事件
        if (event.getLevel().isClientSide) {
            return;
        }

        // 检查是否放置的是床
        if (!(event.getLevel().getBlockState(event.getPos()).getBlock() instanceof BedBlock)) {
            return;
        }

        Player player = event.getEntity();
        if (player == null) {
            return;
        }

        BlockPos placedPos = event.getPos();

        // 搜索周围20格内的女友
        List<CreeperMother> girlfriends = player.level().getEntitiesOfClass(
                CreeperMother.class,
                player.getBoundingBox().inflate(DETECT_RANGE)
        );

        for (CreeperMother girl : girlfriends) {
            // 检查是否是当前玩家的女友
            if (girl.getHusbandUUID() != null && girl.getHusbandUUID().equals(player.getUUID())) {
                // 设置床位置并让女友去睡觉
                girl.setBedPos(placedPos);
                
                // 让女友走向床
                girl.getNavigation().moveTo(
                        placedPos.getX() + 0.5,
                        placedPos.getY(),
                        placedPos.getZ() + 0.5,
                        1.3D
                );

                // 添加记忆并发送消息
                girl.addMessageToMemory("system", "（老公放了一张床，我要去睡觉了）");
            }
        }
    }
}
