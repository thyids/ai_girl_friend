package com.thyids.ai_girl_friend.item;

import com.thyids.ai_girl_friend.Ai_girl_friend;
import com.thyids.ai_girl_friend.world.entity.EntityTypeRegister;
import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Ai_girl_friend.MOD_ID);

    public static final RegistryObject<Item> GIRLFRIEND_CORE = ITEMS.register("girlfriend_core",
            () -> new GirlfriendCoreItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));


    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}