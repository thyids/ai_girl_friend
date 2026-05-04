package com.thyids.ai_girl_friend.world.entity.girl_friend;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class neve extends CreeperMother {
    public neve(EntityType<? extends neve> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariant() {
        return 166;
    }
}
