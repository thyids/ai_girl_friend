package com.thyids.ai_girl_friend.world.entity.girl_friend;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class xyla extends CreeperMother {
    public xyla(EntityType<? extends xyla> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariant() {
        return 50;
    }
}
