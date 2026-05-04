package com.thyids.ai_girl_friend.world.entity.girl_friend;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class lily extends CreeperMother {
    public lily(EntityType<? extends lily> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariant() {
        return 9;
    }
}
