package com.thyids.ai_girl_friend.world.entity.girl_friend;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class geli extends CreeperMother {
    public geli(EntityType<? extends geli> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariant() {
        return 126;
    }
}
