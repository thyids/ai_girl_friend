package com.thyids.ai_girl_friend.entities.client;

import com.thyids.ai_girl_friend.Ai_girl_friend;
import com.thyids.ai_girl_friend.entities.client.PlayerActionManager;
import com.thyids.ai_girl_friend.entities.client.PlayerActionType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Ai_girl_friend.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerRenderAnimEvent {

    private static void reset(HumanoidModel<?> m){
        m.head.resetPose();
        m.body.resetPose();
        m.leftArm.resetPose();
        m.rightArm.resetPose();
        m.leftLeg.resetPose();
        m.rightLeg.resetPose();
    }

    @SubscribeEvent
    public static void renderPlayer(RenderPlayerEvent.Pre event){
        HumanoidModel<?> m = event.getRenderer().getModel();
        PlayerActionType type = PlayerActionManager.get().getCurrent();

        reset(m);

        switch (type){
            case HUG_GIRL:
                // 玩家张开双臂抱
                m.leftArm.yRot = (float)Math.toRadians(-120);
                m.rightArm.yRot = (float)Math.toRadians(120);
                break;

            case KISS_GIRL:
                // 玩家低头亲
                m.head.xRot = (float)Math.toRadians(25);
                break;

            case GIRL_LAY_ON_BODY:
                // 玩家站直，让女友趴在胸口
                m.body.xRot = 0;
                break;

            case GIRL_LAY_ON_LEG:
                // 玩家微微坐下来
                m.body.xRot = (float)Math.toRadians(15);
                m.leftLeg.xRot = (float)Math.toRadians(20);
                m.rightLeg.xRot = (float)Math.toRadians(20);
                break;

            default:
                break;
        }
    }
}