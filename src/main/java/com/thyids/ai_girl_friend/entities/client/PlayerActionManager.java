package com.thyids.ai_girl_friend.entities.client;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class PlayerActionManager {
    private static final PlayerActionManager INSTANCE = new PlayerActionManager();
    private PlayerActionType current = PlayerActionType.NONE;
    private int tick;
    private Entity targetGirl;

    private PlayerActionManager(){}

    public static PlayerActionManager get(){
        return INSTANCE;
    }

    public void setAction(PlayerActionType type, Entity girl){
        this.current = type;
        this.tick = 0;
        this.targetGirl = girl;
    }

    public void clear(){
        current = PlayerActionType.NONE;
        tick = 0;
        targetGirl = null;
    }

    public void tickUpdate(){
        if(current != PlayerActionType.NONE){
            tick++;
            // 动作持续 35 帧自动恢复
            if(tick > 35){
                clear();
            }
        }
    }

    public PlayerActionType getCurrent(){
        return current;
    }

    public Entity getTargetGirl(){
        return targetGirl;
    }
}