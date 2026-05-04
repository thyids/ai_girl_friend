package com.thyids.ai_girl_friend.world.entity;

import com.thyids.ai_girl_friend.entities.client.model.CreeperMotherModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.thyids.ai_girl_friend.Ai_girl_friend;

public class EntityEventBus {
    @Mod.EventBusSubscriber(modid = Ai_girl_friend.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public class ClientModEvents {
        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(CreeperMotherModel.CREEPER_MOTHER_LAYER, CreeperMotherModel::createBodyLayer);
        }
    }
}
