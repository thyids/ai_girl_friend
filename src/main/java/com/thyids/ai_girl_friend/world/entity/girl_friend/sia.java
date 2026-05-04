package com.thyids.ai_girl_friend.world.entity.girl_friend;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class sia extends CreeperMother {
    public sia(EntityType<? extends sia> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariant() {
        return 45;
    }
}
