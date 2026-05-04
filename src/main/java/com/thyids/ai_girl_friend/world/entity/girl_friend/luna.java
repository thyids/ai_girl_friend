package com.thyids.ai_girl_friend.world.entity.girl_friend;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class luna extends CreeperMother {
    public luna(EntityType<? extends luna> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariant() {
        return 11;
    }
}
