package com.thyids.ai_girl_friend.world.entity.girl_friend;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class fina extends CreeperMother {
    public fina(EntityType<? extends fina> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariant() {
        return 122;
    }
}
