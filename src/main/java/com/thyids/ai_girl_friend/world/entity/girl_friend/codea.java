package com.thyids.ai_girl_friend.world.entity.girl_friend;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class codea extends CreeperMother {
    public codea(EntityType<? extends codea> type, Level level) {
        super(type, level);
    }

    @Override
    public int getVariant() {
        return 89;
    }
}
