package com.thyids.ai_girl_friend.entities.client;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.AnimationState;

public class PlayerAnimationHolder {
    // 全部和女友对齐的动画状态
    public final AnimationState jumpAnim = new AnimationState();
    public final AnimationState middleRightAnim = new AnimationState();
    public final AnimationState kissAnim = new AnimationState();
    public final AnimationState happyAnim = new AnimationState();
    public final AnimationState embraceAnim = new AnimationState();
    public final AnimationState shyAnim = new AnimationState();
    public final AnimationState heartAnim = new AnimationState();
    public final AnimationState leanAnim = new AnimationState();
    public final AnimationState holdHandAnim = new AnimationState();
    public final AnimationState coquetryAnim = new AnimationState();
    public final AnimationState spinAnim = new AnimationState();
    public final AnimationState headPatAnim = new AnimationState();
    public final AnimationState angryAnim = new AnimationState();
    public final AnimationState lieDownAnim = new AnimationState();
    public final AnimationState sideLieAnim = new AnimationState();

    private static PlayerAnimationHolder INSTANCE;

    public static PlayerAnimationHolder get() {
        if (INSTANCE == null) INSTANCE = new PlayerAnimationHolder();
        return INSTANCE;
    }

    // 重置所有动画
    public void resetAll() {
        jumpAnim.stop();
        middleRightAnim.stop();
        kissAnim.stop();
        happyAnim.stop();
        embraceAnim.stop();
        shyAnim.stop();
        heartAnim.stop();
        leanAnim.stop();
        holdHandAnim.stop();
        coquetryAnim.stop();
        spinAnim.stop();
        headPatAnim.stop();
        angryAnim.stop();
        lieDownAnim.stop();
        sideLieAnim.stop();
    }

    // 播放指定玩家动画
    public void playAnim(String name, int tick) {
        resetAll();
        switch (name.toLowerCase()) {
            case "jump" -> jumpAnim.start(tick);
            case "middle_right" -> middleRightAnim.start(tick);
            case "kiss" -> kissAnim.start(tick);
            case "happy" -> happyAnim.start(tick);
            case "embrace" -> embraceAnim.start(tick);
            case "shy" -> shyAnim.start(tick);
            case "heart" -> heartAnim.start(tick);
            case "lean" -> leanAnim.start(tick);
            case "hold_hand" -> holdHandAnim.start(tick);
            case "coquetry" -> coquetryAnim.start(tick);
            case "spin" -> spinAnim.start(tick);
            case "head_pat" -> headPatAnim.start(tick);
            case "angry" -> angryAnim.start(tick);
            case "liedown" -> lieDownAnim.start(tick);
            case "sidelie" -> sideLieAnim.start(tick);
        }
    }
}