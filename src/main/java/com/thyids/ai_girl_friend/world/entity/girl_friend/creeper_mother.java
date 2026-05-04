package com.thyids.ai_girl_friend.world.entity.girl_friend;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class creeper_mother extends CreeperMother {
    public creeper_mother(EntityType<? extends creeper_mother> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariant() {
        return 4;
    }
}
