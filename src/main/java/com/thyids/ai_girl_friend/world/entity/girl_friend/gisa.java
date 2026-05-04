package com.thyids.ai_girl_friend.world.entity.girl_friend;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class gisa extends CreeperMother {
    public gisa(EntityType<? extends gisa> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariant() {
        return 127;
    }
}
