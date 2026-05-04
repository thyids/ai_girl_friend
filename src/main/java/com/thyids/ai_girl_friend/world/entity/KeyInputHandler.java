package com.thyids.ai_girl_friend.world.entity;

import com.thyids.ai_girl_friend.entities.client.PlayerActionManager;
import com.thyids.ai_girl_friend.entities.client.PlayerActionType;
import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class KeyInputHandler {

    private static CreeperMother getNearWife(Player p){
        List<CreeperMother> list = p.level().getEntitiesOfClass(
                CreeperMother.class,
                p.getBoundingBox().inflate(2.5D)
        );
        for(CreeperMother girl : list){
            if(girl.getHusbandUUID() != null
                    && girl.getHusbandUUID().equals(p.getUUID())){
                return girl;
            }
        }
        return null;
    }

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent e){
        if(e.phase != TickEvent.Phase.END) return;
        Player p = Minecraft.getInstance().player;
        if(p == null) return;

        PlayerActionManager.get().tickUpdate();

        // H 抱女友
        if(KeyBindRegister.KEY_HEAD_PAT.consumeClick()){
            CreeperMother girl = getNearWife(p);
            if(girl != null){
                PlayerActionManager.get().setAction(PlayerActionType.HUG_GIRL, girl);
                girl.triggerAnimation("embrace");
            }
        }

        // J 亲女友
        if(KeyBindRegister.KEY_KISS.consumeClick()){
            CreeperMother girl = getNearWife(p);
            if(girl != null){
                PlayerActionManager.get().setAction(PlayerActionType.KISS_GIRL, girl);
                girl.triggerAnimation("kiss");
            }
        }

        // K 女友躺身上
        if(KeyBindRegister.KEY_EMBRACE.consumeClick()){
            CreeperMother girl = getNearWife(p);
            if(girl != null){
                PlayerActionManager.get().setAction(PlayerActionType.GIRL_LAY_ON_BODY, girl);
                girl.setNoGravity(true);
                girl.absMoveTo(p.getX(), p.getY()+0.4D, p.getZ(), p.getYRot(), 0);
            }
        }

        // L 女友躺腿上
        if(KeyBindRegister.KEY_LIE_DOWN.consumeClick()){
            CreeperMother girl = getNearWife(p);
            if(girl != null){
                PlayerActionManager.get().setAction(PlayerActionType.GIRL_LAY_ON_LEG, girl);
                girl.setNoGravity(true);
                girl.absMoveTo(p.getX()+0.35D, p.getY(), p.getZ(), p.getYRot(), 0);
            }
        }
    }
}