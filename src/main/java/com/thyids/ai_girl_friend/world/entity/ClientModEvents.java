package com.thyids.ai_girl_friend.world.entity;

import com.thyids.ai_girl_friend.Ai_girl_friend;
import com.thyids.ai_girl_friend.entities.client.PlayerAnimationHolder;
import com.thyids.ai_girl_friend.entities.client.model.CreeperMotherModel;
import com.thyids.ai_girl_friend.entities.client.renderer.CreeperMotherRenderer;
import com.thyids.ai_girl_friend.web.DeepSeekAPI;
import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

// 这里的 value = Dist.CLIENT 非常重要，防止服务端加载这段代码导致崩溃
@Mod.EventBusSubscriber(modid = Ai_girl_friend.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeRegister.CREEPER_MOTHER.get(), CreeperMotherRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CreeperMotherModel.CREEPER_MOTHER_LAYER, CreeperMotherModel::createBodyLayer);
    }
}