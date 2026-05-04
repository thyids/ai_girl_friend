package com.thyids.ai_girl_friend.event;

import com.thyids.ai_girl_friend.Ai_girl_friend;
import com.thyids.ai_girl_friend.web.DeepSeekAPI;
import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Ai_girl_friend.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ChatEventHandler {

    // 搜索范围：10格直径（前后左右各5格，共100格面积）
    private static final double CALL_RANGE = 10.0;

    @SubscribeEvent
    public static void onPlayerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        String message = event.getRawText();
        boolean anyGirlResponded = false;

        // 搜索周围 64 格内的苦力怕娘
        List<CreeperMother> entities = player.level().getEntitiesOfClass(CreeperMother.class,
                player.getBoundingBox().inflate(64));

        // 标记是否是喊"老婆"
        boolean isCallingWife = message.contains("老婆") || message.contains("老婆~") || 
                               message.contains("老婆！") || message.contains("老婆。");
        
        // 标记是否是邀请上床
        boolean isInvitingToBed = message.contains("上床") || message.contains("睡觉") ||
                                  message.contains("躺") || message.contains("陪我");

        // 存储最近的女友
        CreeperMother nearestGirl = null;
        double nearestDistance = Double.MAX_VALUE;

        for (CreeperMother girl : entities) {
            // 获取名字，如果没有自定义名字则使用默认名
            String name = girl.getCustomName() != null ? girl.getCustomName().getString() : "老婆";

            // 检测消息中是否包含当前这只女孩的名字
            if (message.contains(name)) {
                // 如果是邀请上床，设置邀请标志
                if (isInvitingToBed) {
                    girl.setInvitedToBed(true);
                    girl.trySleep();
                }
                // 调用异步 API
                DeepSeekAPI.askAI(player, girl, message);
                anyGirlResponded = true;
                // 注意：这里不要 return，继续循环检查下一个女孩
            }

            // 如果是喊"老婆"，寻找10格范围内最近的绑定女友
            if (isCallingWife) {
                // 检查是否绑定了当前玩家作为老公
                if (girl.getHusbandUUID() != null && 
                    girl.getHusbandUUID().equals(player.getUUID())) {
                    
                    double distance = girl.distanceTo(player);
                    // 只在10格范围内寻找
                    if (distance <= CALL_RANGE && distance < nearestDistance) {
                        nearestDistance = distance;
                        nearestGirl = girl;
                    }
                }
            }
        }

        // 如果喊"老婆"且找到了最近的女友
        if (isCallingWife && nearestGirl != null) {
            // 如果在床上，让AI决定动作
            if (nearestGirl.isSleeping()) {
                // 在床上时，让AI分析对话内容决定动作
                DeepSeekAPI.askAI(player, nearestGirl, "（在床上，听到老公说：" + message + "）");
                anyGirlResponded = true;
            } else {
                // 让女友走向玩家
                nearestGirl.getNavigation().moveTo(player, 1.3D);
                
                // 添加记忆：老公叫我了
                nearestGirl.addMessageToMemory("system", "（老公在叫我，我要过去找他）");
                
                // 发送回应
                DeepSeekAPI.askAI(player, nearestGirl, "（听到老公在叫我，开心地跑过去）老公，我来了！");
                anyGirlResponded = true;
            }
        }
        
        // 检查所有女友中是否有在床上的，让AI决定动作
        for (CreeperMother girl : entities) {
            if (girl.isSleeping() && girl.getHusbandUUID() != null && 
                girl.getHusbandUUID().equals(player.getUUID()) && girl != nearestGirl) {
                // 让AI分析对话内容决定动作
                DeepSeekAPI.askAI(player, girl, "（在床上，听到老公说：" + message + "）");
                anyGirlResponded = true;
            }
        }

        // 如果至少有一个女孩被点名并准备回复，我们可以取消原始聊天消息的显示
        if (anyGirlResponded) {
            event.setCanceled(true); // 如果想让"点名"的消息不显示在公屏，取消注释
        }
    }
}