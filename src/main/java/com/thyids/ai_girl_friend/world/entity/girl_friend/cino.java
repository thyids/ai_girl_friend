package com.thyids.ai_girl_friend.world.entity.girl_friend;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class cino extends CreeperMother {
    public cino(EntityType<? extends cino> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariant() {
        return 105;
    }
}
