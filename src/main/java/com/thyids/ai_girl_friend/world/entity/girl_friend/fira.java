package com.thyids.ai_girl_friend.world.entity.girl_friend;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class fira extends CreeperMother {
    public fira(EntityType<? extends fira> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariant() {
        return 33;
    }
}
