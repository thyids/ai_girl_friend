package com.thyids.ai_girl_friend.world.entity.girl_friend;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class zora extends CreeperMother {
    public zora(EntityType<? extends zora> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariant() {
        return 19;
    }
}
