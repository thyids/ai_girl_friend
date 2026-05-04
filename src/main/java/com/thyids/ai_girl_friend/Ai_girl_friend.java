package com.thyids.ai_girl_friend;

import com.mojang.logging.LogUtils;
import com.thyids.ai_girl_friend.entities.client.renderer.CreeperMotherRenderer;
import com.thyids.ai_girl_friend.item.ModItems;
import com.thyids.ai_girl_friend.block.ModBlocks;

import com.thyids.ai_girl_friend.network.ModMessages;
import com.thyids.ai_girl_friend.gameplay.ModConfig;
import com.thyids.ai_girl_friend.world.entity.EntityTypeRegister;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Ai_girl_friend.MOD_ID)
public class Ai_girl_friend
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "ai_girl_friend";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Ai_girl_friend()
    {
        ModConfig.load();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        RegisterAll(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    public static void RegisterAll(IEventBus bus) {
        ModItems.register(bus);
        ModBlocks.register(bus);
        EntityTypeRegister.register(bus);
        ModMessages.register();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // this_insert_wpl
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.GIRLFRIEND_CORE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {}

        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityTypeRegister.AISHA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ARIA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.AYLA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.BADE.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.BELA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.BELLA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.BRIA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.CHLOE.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.CICI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.CLARA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.COCO.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.CODEA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.CORA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.CREEPER_MOTHER.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.CUTE.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.DARA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.DILA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.DINA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.EIRA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ELLA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ELVA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.EMA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.FARA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.FAYE.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.FEWS.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.FIRA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.GIA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.GIGI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.HANA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.HERA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.HILA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.IRIS.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.IRYS.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ISLA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.IVA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.JANE.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.JARA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.JK.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.JOLI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.KALA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.KIKI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.KIRA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.KYLA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.LALA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.LILA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.LILY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.LIRA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.LUNA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.LUNAD.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.MAID.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.MAMAD.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.MAYA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.MENA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.MERA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.MIKI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.MILA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.NIKA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.NOLA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.NOVA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.NYLA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.OLA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ONA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.PIPPA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.POLA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.QARA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.RIA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.RINA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ROLA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ROXY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.SIA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.SKYE.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.SORA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.SUKI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.SUNNY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.TALA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.THEA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.TIRA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ULA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.UNA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.VARA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.VIVI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.WILA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.WILLOW.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.WINI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.XARA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.XYLA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.YARA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.YULI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ZARA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ZORA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ZURI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.WREN.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.TESS.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.NIX.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.VEGA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.OPAL.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.IVY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ELSIE.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.MAEVE.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.SAGE.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.BRYN.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.LARK.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.WISP.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.BREE.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ZIVA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.MAIA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.LINA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.PIXY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ZARI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.OLYN.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.RHIA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.TRIS.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.NORI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ELYN.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.MIRA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.NEVE.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.LYRA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.CARY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.DEMI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.FINA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.GALA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.HALI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.INDY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.JORY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.KORA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.LANA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.MONA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.NAYA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.OLIE.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.PARI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.RANI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.SARI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.TINA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ULIE.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.VALE.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.WENA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.XANI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.YANA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ZANA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ADIS.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.BERI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.CALA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.DORI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ENVY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.FARI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.GELI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.HANI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.INOS.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.JANI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.KALI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.LONI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.MARI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.NERA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ONNI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.PANI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.RASI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.SENA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.TALI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.URAY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.VANI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.WARI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.XENA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.YORI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ZELI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ARNI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.BRIS.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.DANA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ELRI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.FRAY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.GONA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.HORY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.JALI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.KERY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.LOMA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.MIKA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.OLIA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.PELA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.QANI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.RAVI.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.SOLA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ULNA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.VIRA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.WYLA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ADRA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.CINO.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.DEMA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ELKA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.GISA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.HELA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.INKA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.JOLA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.KINA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.LEVA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.MINA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.NELA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.OKRA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.SAKA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.TOVA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.UNNA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.VETA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.WIRA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.XILA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.YUKA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ZOHA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ANNA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.BONA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.CANA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.DONA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ELNA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.FANA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.GANA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.IANA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.JANA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.KANA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.MANA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.NANA.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ZORY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.BIXY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.COZY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.DAZY.get(), CreeperMotherRenderer::new);
            event.registerEntityRenderer(EntityTypeRegister.ELMY.get(), CreeperMotherRenderer::new);
        }
    }
}
