package com.thyids.ai_girl_friend.world.entity.girl_friend;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class pani extends CreeperMother {
    public pani(EntityType<? extends pani> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariant() {
        return 175;
    }
}
