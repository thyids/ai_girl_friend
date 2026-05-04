package com.thyids.ai_girl_friend.world.entity.girl_friend;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class isla extends CreeperMother {
    public isla(EntityType<? extends isla> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariant() {
        return 36;
    }
}
