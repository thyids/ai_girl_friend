package com.thyids.ai_girl_friend.world.entity;

import com.thyids.ai_girl_friend.Ai_girl_friend;
import com.thyids.ai_girl_friend.world.entity.girl_friend.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityTypeRegister {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Ai_girl_friend.MOD_ID);

    public static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }

    // ==== 全部实体完整注册（类名全小写） ====
    public static final RegistryObject<EntityType<aisha>> AISHA =
            ENTITY_TYPES.register("aisha",
                    () -> EntityType.Builder.of(aisha::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("aisha"));

    public static final RegistryObject<EntityType<aria>> ARIA =
            ENTITY_TYPES.register("aria",
                    () -> EntityType.Builder.of(aria::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("aria"));

    public static final RegistryObject<EntityType<ayla>> AYLA =
            ENTITY_TYPES.register("ayla",
                    () -> EntityType.Builder.of(ayla::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("ayla"));

    public static final RegistryObject<EntityType<bade>> BADE =
            ENTITY_TYPES.register("bade",
                    () -> EntityType.Builder.of(bade::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("bade"));

    public static final RegistryObject<EntityType<bela>> BELA =
            ENTITY_TYPES.register("bela",
                    () -> EntityType.Builder.of(bela::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("bela"));

    public static final RegistryObject<EntityType<bella>> BELLA =
            ENTITY_TYPES.register("bella",
                    () -> EntityType.Builder.of(bella::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("bella"));

    public static final RegistryObject<EntityType<bria>> BRIA =
            ENTITY_TYPES.register("bria",
                    () -> EntityType.Builder.of(bria::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("bria"));

    public static final RegistryObject<EntityType<chloe>> CHLOE =
            ENTITY_TYPES.register("chloe",
                    () -> EntityType.Builder.of(chloe::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("chloe"));

    public static final RegistryObject<EntityType<cici>> CICI =
            ENTITY_TYPES.register("cici",
                    () -> EntityType.Builder.of(cici::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("cici"));

    public static final RegistryObject<EntityType<clara>> CLARA =
            ENTITY_TYPES.register("clara",
                    () -> EntityType.Builder.of(clara::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("clara"));

    public static final RegistryObject<EntityType<coco>> COCO =
            ENTITY_TYPES.register("coco",
                    () -> EntityType.Builder.of(coco::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("coco"));

    public static final RegistryObject<EntityType<codea>> CODEA =
            ENTITY_TYPES.register("codea",
                    () -> EntityType.Builder.of(codea::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("codea"));

    public static final RegistryObject<EntityType<cora>> CORA =
            ENTITY_TYPES.register("cora",
                    () -> EntityType.Builder.of(cora::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("cora"));

    public static final RegistryObject<EntityType<creeper_mother>> CREEPER_MOTHER =
            ENTITY_TYPES.register("creeper_mother",
                    () -> EntityType.Builder.of(creeper_mother::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("creeper_mother"));

    public static final RegistryObject<EntityType<cute>> CUTE =
            ENTITY_TYPES.register("cute",
                    () -> EntityType.Builder.of(cute::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("cute"));

    public static final RegistryObject<EntityType<dara>> DARA =
            ENTITY_TYPES.register("dara",
                    () -> EntityType.Builder.of(dara::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("dara"));

    public static final RegistryObject<EntityType<dila>> DILA =
            ENTITY_TYPES.register("dila",
                    () -> EntityType.Builder.of(dila::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("dila"));

    public static final RegistryObject<EntityType<dina>> DINA =
            ENTITY_TYPES.register("dina",
                    () -> EntityType.Builder.of(dina::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("dina"));

    public static final RegistryObject<EntityType<eira>> EIRA =
            ENTITY_TYPES.register("eira",
                    () -> EntityType.Builder.of(eira::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("eira"));

    public static final RegistryObject<EntityType<ella>> ELLA =
            ENTITY_TYPES.register("ella",
                    () -> EntityType.Builder.of(ella::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("ella"));

    public static final RegistryObject<EntityType<elva>> ELVA =
            ENTITY_TYPES.register("elva",
                    () -> EntityType.Builder.of(elva::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("elva"));

    public static final RegistryObject<EntityType<ema>> EMA =
            ENTITY_TYPES.register("ema",
                    () -> EntityType.Builder.of(ema::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("ema"));

    public static final RegistryObject<EntityType<fara>> FARA =
            ENTITY_TYPES.register("fara",
                    () -> EntityType.Builder.of(fara::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("fara"));

    public static final RegistryObject<EntityType<faye>> FAYE =
            ENTITY_TYPES.register("faye",
                    () -> EntityType.Builder.of(faye::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("faye"));

    public static final RegistryObject<EntityType<fews>> FEWS =
            ENTITY_TYPES.register("fews",
                    () -> EntityType.Builder.of(fews::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("fews"));

    public static final RegistryObject<EntityType<fira>> FIRA =
            ENTITY_TYPES.register("fira",
                    () -> EntityType.Builder.of(fira::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("fira"));

    public static final RegistryObject<EntityType<gia>> GIA =
            ENTITY_TYPES.register("gia",
                    () -> EntityType.Builder.of(gia::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("gia"));

    public static final RegistryObject<EntityType<gigi>> GIGI =
            ENTITY_TYPES.register("gigi",
                    () -> EntityType.Builder.of(gigi::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("gigi"));

    public static final RegistryObject<EntityType<hana>> HANA =
            ENTITY_TYPES.register("hana",
                    () -> EntityType.Builder.of(hana::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("hana"));

    public static final RegistryObject<EntityType<hera>> HERA =
            ENTITY_TYPES.register("hera",
                    () -> EntityType.Builder.of(hera::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("hera"));

    public static final RegistryObject<EntityType<hila>> HILA =
            ENTITY_TYPES.register("hila",
                    () -> EntityType.Builder.of(hila::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("hila"));

    public static final RegistryObject<EntityType<iris>> IRIS =
            ENTITY_TYPES.register("iris",
                    () -> EntityType.Builder.of(iris::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("iris"));

    public static final RegistryObject<EntityType<irys>> IRYS =
            ENTITY_TYPES.register("irys",
                    () -> EntityType.Builder.of(irys::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("irys"));

    public static final RegistryObject<EntityType<isla>> ISLA =
            ENTITY_TYPES.register("isla",
                    () -> EntityType.Builder.of(isla::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("isla"));

    public static final RegistryObject<EntityType<iva>> IVA =
            ENTITY_TYPES.register("iva",
                    () -> EntityType.Builder.of(iva::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("iva"));

    public static final RegistryObject<EntityType<jane>> JANE =
            ENTITY_TYPES.register("jane",
                    () -> EntityType.Builder.of(jane::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("jane"));

    public static final RegistryObject<EntityType<jara>> JARA =
            ENTITY_TYPES.register("jara",
                    () -> EntityType.Builder.of(jara::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("jara"));

    public static final RegistryObject<EntityType<jk>> JK =
            ENTITY_TYPES.register("jk",
                    () -> EntityType.Builder.of(jk::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("jk"));

    public static final RegistryObject<EntityType<joli>> JOLI =
            ENTITY_TYPES.register("joli",
                    () -> EntityType.Builder.of(joli::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("joli"));

    public static final RegistryObject<EntityType<kala>> KALA =
            ENTITY_TYPES.register("kala",
                    () -> EntityType.Builder.of(kala::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("kala"));

    public static final RegistryObject<EntityType<kiki>> KIKI =
            ENTITY_TYPES.register("kiki",
                    () -> EntityType.Builder.of(kiki::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("kiki"));

    public static final RegistryObject<EntityType<kira>> KIRA =
            ENTITY_TYPES.register("kira",
                    () -> EntityType.Builder.of(kira::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("kira"));

    public static final RegistryObject<EntityType<kyla>> KYLA =
            ENTITY_TYPES.register("kyla",
                    () -> EntityType.Builder.of(kyla::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("kyla"));

    public static final RegistryObject<EntityType<lala>> LALA =
            ENTITY_TYPES.register("lala",
                    () -> EntityType.Builder.of(lala::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("lala"));

    public static final RegistryObject<EntityType<lila>> LILA =
            ENTITY_TYPES.register("lila",
                    () -> EntityType.Builder.of(lila::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("lila"));

    public static final RegistryObject<EntityType<lily>> LILY =
            ENTITY_TYPES.register("lily",
                    () -> EntityType.Builder.of(lily::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("lily"));

    public static final RegistryObject<EntityType<lira>> LIRA =
            ENTITY_TYPES.register("lira",
                    () -> EntityType.Builder.of(lira::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("lira"));

    public static final RegistryObject<EntityType<luna>> LUNA =
            ENTITY_TYPES.register("luna",
                    () -> EntityType.Builder.of(luna::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("luna"));

    public static final RegistryObject<EntityType<lunad>> LUNAD =
            ENTITY_TYPES.register("lunad",
                    () -> EntityType.Builder.of(lunad::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("lunad"));

    public static final RegistryObject<EntityType<maid>> MAID =
            ENTITY_TYPES.register("maid",
                    () -> EntityType.Builder.of(maid::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("maid"));

    public static final RegistryObject<EntityType<mamad>> MAMAD =
            ENTITY_TYPES.register("mamad",
                    () -> EntityType.Builder.of(mamad::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("mamad"));

    public static final RegistryObject<EntityType<maya>> MAYA =
            ENTITY_TYPES.register("maya",
                    () -> EntityType.Builder.of(maya::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("maya"));

    public static final RegistryObject<EntityType<mena>> MENA =
            ENTITY_TYPES.register("mena",
                    () -> EntityType.Builder.of(mena::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("mena"));

    public static final RegistryObject<EntityType<mera>> MERA =
            ENTITY_TYPES.register("mera",
                    () -> EntityType.Builder.of(mera::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("mera"));

    public static final RegistryObject<EntityType<miki>> MIKI =
            ENTITY_TYPES.register("miki",
                    () -> EntityType.Builder.of(miki::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("miki"));

    public static final RegistryObject<EntityType<mila>> MILA =
            ENTITY_TYPES.register("mila",
                    () -> EntityType.Builder.of(mila::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("mila"));

    public static final RegistryObject<EntityType<nika>> NIKA =
            ENTITY_TYPES.register("nika",
                    () -> EntityType.Builder.of(nika::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("nika"));

    public static final RegistryObject<EntityType<nola>> NOLA =
            ENTITY_TYPES.register("nola",
                    () -> EntityType.Builder.of(nola::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("nola"));

    public static final RegistryObject<EntityType<nova>> NOVA =
            ENTITY_TYPES.register("nova",
                    () -> EntityType.Builder.of(nova::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("nova"));

    public static final RegistryObject<EntityType<nyla>> NYLA =
            ENTITY_TYPES.register("nyla",
                    () -> EntityType.Builder.of(nyla::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("nyla"));

    public static final RegistryObject<EntityType<ola>> OLA =
            ENTITY_TYPES.register("ola",
                    () -> EntityType.Builder.of(ola::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("ola"));

    public static final RegistryObject<EntityType<ona>> ONA =
            ENTITY_TYPES.register("ona",
                    () -> EntityType.Builder.of(ona::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("ona"));

    public static final RegistryObject<EntityType<pippa>> PIPPA =
            ENTITY_TYPES.register("pippa",
                    () -> EntityType.Builder.of(pippa::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("pippa"));

    public static final RegistryObject<EntityType<pola>> POLA =
            ENTITY_TYPES.register("pola",
                    () -> EntityType.Builder.of(pola::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("pola"));

    public static final RegistryObject<EntityType<qara>> QARA =
            ENTITY_TYPES.register("qara",
                    () -> EntityType.Builder.of(qara::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("qara"));

    public static final RegistryObject<EntityType<ria>> RIA =
            ENTITY_TYPES.register("ria",
                    () -> EntityType.Builder.of(ria::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("ria"));

    public static final RegistryObject<EntityType<rina>> RINA =
            ENTITY_TYPES.register("rina",
                    () -> EntityType.Builder.of(rina::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("rina"));

    public static final RegistryObject<EntityType<rola>> ROLA =
            ENTITY_TYPES.register("rola",
                    () -> EntityType.Builder.of(rola::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("rola"));

    public static final RegistryObject<EntityType<roxy>> ROXY =
            ENTITY_TYPES.register("roxy",
                    () -> EntityType.Builder.of(roxy::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("roxy"));

    public static final RegistryObject<EntityType<sia>> SIA =
            ENTITY_TYPES.register("sia",
                    () -> EntityType.Builder.of(sia::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("sia"));

    public static final RegistryObject<EntityType<skye>> SKYE =
            ENTITY_TYPES.register("skye",
                    () -> EntityType.Builder.of(skye::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("skye"));

    public static final RegistryObject<EntityType<sora>> SORA =
            ENTITY_TYPES.register("sora",
                    () -> EntityType.Builder.of(sora::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("sora"));

    public static final RegistryObject<EntityType<suki>> SUKI =
            ENTITY_TYPES.register("suki",
                    () -> EntityType.Builder.of(suki::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("suki"));

    public static final RegistryObject<EntityType<sunny>> SUNNY =
            ENTITY_TYPES.register("sunny",
                    () -> EntityType.Builder.of(sunny::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("sunny"));

    public static final RegistryObject<EntityType<tala>> TALA =
            ENTITY_TYPES.register("tala",
                    () -> EntityType.Builder.of(tala::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("tala"));

    public static final RegistryObject<EntityType<thea>> THEA =
            ENTITY_TYPES.register("thea",
                    () -> EntityType.Builder.of(thea::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("thea"));

    public static final RegistryObject<EntityType<tira>> TIRA =
            ENTITY_TYPES.register("tira",
                    () -> EntityType.Builder.of(tira::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("tira"));

    public static final RegistryObject<EntityType<ula>> ULA =
            ENTITY_TYPES.register("ula",
                    () -> EntityType.Builder.of(ula::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("ula"));

    public static final RegistryObject<EntityType<una>> UNA =
            ENTITY_TYPES.register("una",
                    () -> EntityType.Builder.of(una::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("una"));

    public static final RegistryObject<EntityType<vara>> VARA =
            ENTITY_TYPES.register("vara",
                    () -> EntityType.Builder.of(vara::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("vara"));

    public static final RegistryObject<EntityType<vivi>> VIVI =
            ENTITY_TYPES.register("vivi",
                    () -> EntityType.Builder.of(vivi::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("vivi"));

    public static final RegistryObject<EntityType<wila>> WILA =
            ENTITY_TYPES.register("wila",
                    () -> EntityType.Builder.of(wila::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("wila"));

    public static final RegistryObject<EntityType<willow>> WILLOW =
            ENTITY_TYPES.register("willow",
                    () -> EntityType.Builder.of(willow::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("willow"));

    public static final RegistryObject<EntityType<wini>> WINI =
            ENTITY_TYPES.register("wini",
                    () -> EntityType.Builder.of(wini::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("wini"));

    public static final RegistryObject<EntityType<xara>> XARA =
            ENTITY_TYPES.register("xara",
                    () -> EntityType.Builder.of(xara::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("xara"));

    public static final RegistryObject<EntityType<xyla>> XYLA =
            ENTITY_TYPES.register("xyla",
                    () -> EntityType.Builder.of(xyla::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("xyla"));

    public static final RegistryObject<EntityType<yara>> YARA =
            ENTITY_TYPES.register("yara",
                    () -> EntityType.Builder.of(yara::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("yara"));

    public static final RegistryObject<EntityType<yuli>> YULI =
            ENTITY_TYPES.register("yuli",
                    () -> EntityType.Builder.of(yuli::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("yuli"));

    public static final RegistryObject<EntityType<zara>> ZARA =
            ENTITY_TYPES.register("zara",
                    () -> EntityType.Builder.of(zara::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("zara"));

    public static final RegistryObject<EntityType<zora>> ZORA =
            ENTITY_TYPES.register("zora",
                    () -> EntityType.Builder.of(zora::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("zora"));

    public static final RegistryObject<EntityType<zuri>> ZURI =
            ENTITY_TYPES.register("zuri",
                    () -> EntityType.Builder.of(zuri::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("zuri"));

    public static final RegistryObject<EntityType<wren>> WREN =
            ENTITY_TYPES.register("wren",
                    () -> EntityType.Builder.of(wren::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("wren"));

    public static final RegistryObject<EntityType<tess>> TESS =
            ENTITY_TYPES.register("tess",
                    () -> EntityType.Builder.of(tess::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("tess"));

    public static final RegistryObject<EntityType<nix>> NIX =
            ENTITY_TYPES.register("nix",
                    () -> EntityType.Builder.of(nix::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("nix"));

    public static final RegistryObject<EntityType<vega>> VEGA =
            ENTITY_TYPES.register("vega",
                    () -> EntityType.Builder.of(vega::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("vega"));

    public static final RegistryObject<EntityType<opal>> OPAL =
            ENTITY_TYPES.register("opal",
                    () -> EntityType.Builder.of(opal::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("opal"));

    public static final RegistryObject<EntityType<ivy>> IVY =
            ENTITY_TYPES.register("ivy",
                    () -> EntityType.Builder.of(ivy::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("ivy"));

    public static final RegistryObject<EntityType<elsie>> ELSIE =
            ENTITY_TYPES.register("elsie",
                    () -> EntityType.Builder.of(elsie::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("elsie"));

    public static final RegistryObject<EntityType<maeve>> MAEVE =
            ENTITY_TYPES.register("maeve",
                    () -> EntityType.Builder.of(maeve::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("maeve"));

    public static final RegistryObject<EntityType<sage>> SAGE =
            ENTITY_TYPES.register("sage",
                    () -> EntityType.Builder.of(sage::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("sage"));

    public static final RegistryObject<EntityType<bryn>> BRYN =
            ENTITY_TYPES.register("bryn",
                    () -> EntityType.Builder.of(bryn::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("bryn"));

    public static final RegistryObject<EntityType<lark>> LARK =
            ENTITY_TYPES.register("lark",
                    () -> EntityType.Builder.of(lark::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("lark"));

    public static final RegistryObject<EntityType<wisp>> WISP =
            ENTITY_TYPES.register("wisp",
                    () -> EntityType.Builder.of(wisp::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("wisp"));

    public static final RegistryObject<EntityType<bree>> BREE =
            ENTITY_TYPES.register("bree",
                    () -> EntityType.Builder.of(bree::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("bree"));

    public static final RegistryObject<EntityType<ziva>> ZIVA =
            ENTITY_TYPES.register("ziva",
                    () -> EntityType.Builder.of(ziva::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("ziva"));

    public static final RegistryObject<EntityType<maia>> MAIA =
            ENTITY_TYPES.register("maia",
                    () -> EntityType.Builder.of(maia::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("maia"));

    public static final RegistryObject<EntityType<lina>> LINA =
            ENTITY_TYPES.register("lina",
                    () -> EntityType.Builder.of(lina::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("lina"));

    public static final RegistryObject<EntityType<pixy>> PIXY =
            ENTITY_TYPES.register("pixy",
                    () -> EntityType.Builder.of(pixy::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("pixy"));

    public static final RegistryObject<EntityType<zari>> ZARI =
            ENTITY_TYPES.register("zari",
                    () -> EntityType.Builder.of(zari::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("zari"));

    public static final RegistryObject<EntityType<olyn>> OLYN =
            ENTITY_TYPES.register("olyn",
                    () -> EntityType.Builder.of(olyn::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("olyn"));

    public static final RegistryObject<EntityType<rhia>> RHIA =
            ENTITY_TYPES.register("rhia",
                    () -> EntityType.Builder.of(rhia::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("rhia"));

    public static final RegistryObject<EntityType<tris>> TRIS =
            ENTITY_TYPES.register("tris",
                    () -> EntityType.Builder.of(tris::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("tris"));

    public static final RegistryObject<EntityType<nori>> NORI =
            ENTITY_TYPES.register("nori",
                    () -> EntityType.Builder.of(nori::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("nori"));

    public static final RegistryObject<EntityType<elyn>> ELYN =
            ENTITY_TYPES.register("elyn",
                    () -> EntityType.Builder.of(elyn::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("elyn"));

    public static final RegistryObject<EntityType<mira>> MIRA =
            ENTITY_TYPES.register("mira",
                    () -> EntityType.Builder.of(mira::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("mira"));

    public static final RegistryObject<EntityType<neve>> NEVE =
            ENTITY_TYPES.register("neve",
                    () -> EntityType.Builder.of(neve::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("neve"));

    public static final RegistryObject<EntityType<lyra>> LYRA =
            ENTITY_TYPES.register("lyra",
                    () -> EntityType.Builder.of(lyra::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("lyra"));

    public static final RegistryObject<EntityType<cary>> CARY =
            ENTITY_TYPES.register("cary",
                    () -> EntityType.Builder.of(cary::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("cary"));

    public static final RegistryObject<EntityType<demi>> DEMI =
            ENTITY_TYPES.register("demi",
                    () -> EntityType.Builder.of(demi::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("demi"));

    public static final RegistryObject<EntityType<fina>> FINA =
            ENTITY_TYPES.register("fina",
                    () -> EntityType.Builder.of(fina::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("fina"));

    public static final RegistryObject<EntityType<gala>> GALA =
            ENTITY_TYPES.register("gala",
                    () -> EntityType.Builder.of(gala::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("gala"));

    public static final RegistryObject<EntityType<hali>> HALI =
            ENTITY_TYPES.register("hali",
                    () -> EntityType.Builder.of(hali::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("hali"));

    public static final RegistryObject<EntityType<indy>> INDY =
            ENTITY_TYPES.register("indy",
                    () -> EntityType.Builder.of(indy::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("indy"));

    public static final RegistryObject<EntityType<jory>> JORY =
            ENTITY_TYPES.register("jory",
                    () -> EntityType.Builder.of(jory::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("jory"));

    public static final RegistryObject<EntityType<kora>> KORA =
            ENTITY_TYPES.register("kora",
                    () -> EntityType.Builder.of(kora::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("kora"));

    public static final RegistryObject<EntityType<lana>> LANA =
            ENTITY_TYPES.register("lana",
                    () -> EntityType.Builder.of(lana::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("lana"));

    public static final RegistryObject<EntityType<mona>> MONA =
            ENTITY_TYPES.register("mona",
                    () -> EntityType.Builder.of(mona::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("mona"));

    public static final RegistryObject<EntityType<naya>> NAYA =
            ENTITY_TYPES.register("naya",
                    () -> EntityType.Builder.of(naya::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("naya"));

    public static final RegistryObject<EntityType<olie>> OLIE =
            ENTITY_TYPES.register("olie",
                    () -> EntityType.Builder.of(olie::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("olie"));

    public static final RegistryObject<EntityType<pari>> PARI =
            ENTITY_TYPES.register("pari",
                    () -> EntityType.Builder.of(pari::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("pari"));

    public static final RegistryObject<EntityType<rani>> RANI =
            ENTITY_TYPES.register("rani",
                    () -> EntityType.Builder.of(rani::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("rani"));

    public static final RegistryObject<EntityType<sari>> SARI =
            ENTITY_TYPES.register("sari",
                    () -> EntityType.Builder.of(sari::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("sari"));

    public static final RegistryObject<EntityType<tina>> TINA =
            ENTITY_TYPES.register("tina",
                    () -> EntityType.Builder.of(tina::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("tina"));

    public static final RegistryObject<EntityType<ulie>> ULIE =
            ENTITY_TYPES.register("ulie",
                    () -> EntityType.Builder.of(ulie::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("ulie"));

    public static final RegistryObject<EntityType<vale>> VALE =
            ENTITY_TYPES.register("vale",
                    () -> EntityType.Builder.of(vale::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("vale"));

    public static final RegistryObject<EntityType<wena>> WENA =
            ENTITY_TYPES.register("wena",
                    () -> EntityType.Builder.of(wena::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("wena"));

    public static final RegistryObject<EntityType<xani>> XANI =
            ENTITY_TYPES.register("xani",
                    () -> EntityType.Builder.of(xani::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("xani"));

    public static final RegistryObject<EntityType<yana>> YANA =
            ENTITY_TYPES.register("yana",
                    () -> EntityType.Builder.of(yana::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("yana"));

    public static final RegistryObject<EntityType<zana>> ZANA =
            ENTITY_TYPES.register("zana",
                    () -> EntityType.Builder.of(zana::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("zana"));

    public static final RegistryObject<EntityType<adis>> ADIS =
            ENTITY_TYPES.register("adis",
                    () -> EntityType.Builder.of(adis::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("adis"));

    public static final RegistryObject<EntityType<beri>> BERI =
            ENTITY_TYPES.register("beri",
                    () -> EntityType.Builder.of(beri::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("beri"));

    public static final RegistryObject<EntityType<cala>> CALA =
            ENTITY_TYPES.register("cala",
                    () -> EntityType.Builder.of(cala::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("cala"));

    public static final RegistryObject<EntityType<dori>> DORI =
            ENTITY_TYPES.register("dori",
                    () -> EntityType.Builder.of(dori::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("dori"));

    public static final RegistryObject<EntityType<envy>> ENVY =
            ENTITY_TYPES.register("envy",
                    () -> EntityType.Builder.of(envy::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("envy"));

    public static final RegistryObject<EntityType<fari>> FARI =
            ENTITY_TYPES.register("fari",
                    () -> EntityType.Builder.of(fari::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("fari"));

    public static final RegistryObject<EntityType<geli>> GELI =
            ENTITY_TYPES.register("geli",
                    () -> EntityType.Builder.of(geli::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("geli"));

    public static final RegistryObject<EntityType<hani>> HANI =
            ENTITY_TYPES.register("hani",
                    () -> EntityType.Builder.of(hani::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("hani"));

    public static final RegistryObject<EntityType<inos>> INOS =
            ENTITY_TYPES.register("inos",
                    () -> EntityType.Builder.of(inos::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("inos"));

    public static final RegistryObject<EntityType<jani>> JANI =
            ENTITY_TYPES.register("jani",
                    () -> EntityType.Builder.of(jani::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("jani"));

    public static final RegistryObject<EntityType<kali>> KALI =
            ENTITY_TYPES.register("kali",
                    () -> EntityType.Builder.of(kali::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("kali"));

    public static final RegistryObject<EntityType<loni>> LONI =
            ENTITY_TYPES.register("loni",
                    () -> EntityType.Builder.of(loni::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("loni"));

    public static final RegistryObject<EntityType<mari>> MARI =
            ENTITY_TYPES.register("mari",
                    () -> EntityType.Builder.of(mari::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("mari"));

    public static final RegistryObject<EntityType<nera>> NERA =
            ENTITY_TYPES.register("nera",
                    () -> EntityType.Builder.of(nera::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("nera"));

    public static final RegistryObject<EntityType<onni>> ONNI =
            ENTITY_TYPES.register("onni",
                    () -> EntityType.Builder.of(onni::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("onni"));

    public static final RegistryObject<EntityType<pani>> PANI =
            ENTITY_TYPES.register("pani",
                    () -> EntityType.Builder.of(pani::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("pani"));

    public static final RegistryObject<EntityType<rasi>> RASI =
            ENTITY_TYPES.register("rasi",
                    () -> EntityType.Builder.of(rasi::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("rasi"));

    public static final RegistryObject<EntityType<sena>> SENA =
            ENTITY_TYPES.register("sena",
                    () -> EntityType.Builder.of(sena::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("sena"));

    public static final RegistryObject<EntityType<tali>> TALI =
            ENTITY_TYPES.register("tali",
                    () -> EntityType.Builder.of(tali::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("tali"));

    public static final RegistryObject<EntityType<uray>> URAY =
            ENTITY_TYPES.register("uray",
                    () -> EntityType.Builder.of(uray::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("uray"));

    public static final RegistryObject<EntityType<vani>> VANI =
            ENTITY_TYPES.register("vani",
                    () -> EntityType.Builder.of(vani::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("vani"));

    public static final RegistryObject<EntityType<wari>> WARI =
            ENTITY_TYPES.register("wari",
                    () -> EntityType.Builder.of(wari::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("wari"));

    public static final RegistryObject<EntityType<xena>> XENA =
            ENTITY_TYPES.register("xena",
                    () -> EntityType.Builder.of(xena::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("xena"));

    public static final RegistryObject<EntityType<yori>> YORI =
            ENTITY_TYPES.register("yori",
                    () -> EntityType.Builder.of(yori::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("yori"));

    public static final RegistryObject<EntityType<zeli>> ZELI =
            ENTITY_TYPES.register("zeli",
                    () -> EntityType.Builder.of(zeli::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("zeli"));

    public static final RegistryObject<EntityType<arni>> ARNI =
            ENTITY_TYPES.register("arni",
                    () -> EntityType.Builder.of(arni::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("arni"));

    public static final RegistryObject<EntityType<bris>> BRIS =
            ENTITY_TYPES.register("bris",
                    () -> EntityType.Builder.of(bris::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("bris"));

    public static final RegistryObject<EntityType<dana>> DANA =
            ENTITY_TYPES.register("dana",
                    () -> EntityType.Builder.of(dana::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("dana"));

    public static final RegistryObject<EntityType<elri>> ELRI =
            ENTITY_TYPES.register("elri",
                    () -> EntityType.Builder.of(elri::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("elri"));

    public static final RegistryObject<EntityType<fray>> FRAY =
            ENTITY_TYPES.register("fray",
                    () -> EntityType.Builder.of(fray::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("fray"));

    public static final RegistryObject<EntityType<gona>> GONA =
            ENTITY_TYPES.register("gona",
                    () -> EntityType.Builder.of(gona::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("gona"));

    public static final RegistryObject<EntityType<hory>> HORY =
            ENTITY_TYPES.register("hory",
                    () -> EntityType.Builder.of(hory::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("hory"));

    public static final RegistryObject<EntityType<jali>> JALI =
            ENTITY_TYPES.register("jali",
                    () -> EntityType.Builder.of(jali::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("jali"));

    public static final RegistryObject<EntityType<kery>> KERY =
            ENTITY_TYPES.register("kery",
                    () -> EntityType.Builder.of(kery::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("kery"));

    public static final RegistryObject<EntityType<loma>> LOMA =
            ENTITY_TYPES.register("loma",
                    () -> EntityType.Builder.of(loma::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("loma"));

    public static final RegistryObject<EntityType<mika>> MIKA =
            ENTITY_TYPES.register("mika",
                    () -> EntityType.Builder.of(mika::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("mika"));

    public static final RegistryObject<EntityType<olia>> OLIA =
            ENTITY_TYPES.register("olia",
                    () -> EntityType.Builder.of(olia::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("olia"));

    public static final RegistryObject<EntityType<pela>> PELA =
            ENTITY_TYPES.register("pela",
                    () -> EntityType.Builder.of(pela::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("pela"));

    public static final RegistryObject<EntityType<qani>> QANI =
            ENTITY_TYPES.register("qani",
                    () -> EntityType.Builder.of(qani::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("qani"));

    public static final RegistryObject<EntityType<ravi>> RAVI =
            ENTITY_TYPES.register("ravi",
                    () -> EntityType.Builder.of(ravi::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("ravi"));

    public static final RegistryObject<EntityType<sola>> SOLA =
            ENTITY_TYPES.register("sola",
                    () -> EntityType.Builder.of(sola::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("sola"));

    public static final RegistryObject<EntityType<ulna>> ULNA =
            ENTITY_TYPES.register("ulna",
                    () -> EntityType.Builder.of(ulna::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("ulna"));

    public static final RegistryObject<EntityType<vira>> VIRA =
            ENTITY_TYPES.register("vira",
                    () -> EntityType.Builder.of(vira::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("vira"));

    public static final RegistryObject<EntityType<wyla>> WYLA =
            ENTITY_TYPES.register("wyla",
                    () -> EntityType.Builder.of(wyla::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("wyla"));

    public static final RegistryObject<EntityType<adra>> ADRA =
            ENTITY_TYPES.register("adra",
                    () -> EntityType.Builder.of(adra::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("adra"));

    public static final RegistryObject<EntityType<cino>> CINO =
            ENTITY_TYPES.register("cino",
                    () -> EntityType.Builder.of(cino::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("cino"));

    public static final RegistryObject<EntityType<dema>> DEMA =
            ENTITY_TYPES.register("dema",
                    () -> EntityType.Builder.of(dema::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("dema"));

    public static final RegistryObject<EntityType<elka>> ELKA =
            ENTITY_TYPES.register("elka",
                    () -> EntityType.Builder.of(elka::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("elka"));

    public static final RegistryObject<EntityType<gisa>> GISA =
            ENTITY_TYPES.register("gisa",
                    () -> EntityType.Builder.of(gisa::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("gisa"));

    public static final RegistryObject<EntityType<hela>> HELA =
            ENTITY_TYPES.register("hela",
                    () -> EntityType.Builder.of(hela::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("hela"));

    public static final RegistryObject<EntityType<inka>> INKA =
            ENTITY_TYPES.register("inka",
                    () -> EntityType.Builder.of(inka::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("inka"));

    public static final RegistryObject<EntityType<jola>> JOLA =
            ENTITY_TYPES.register("jola",
                    () -> EntityType.Builder.of(jola::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("jola"));

    public static final RegistryObject<EntityType<kina>> KINA =
            ENTITY_TYPES.register("kina",
                    () -> EntityType.Builder.of(kina::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("kina"));

    public static final RegistryObject<EntityType<leva>> LEVA =
            ENTITY_TYPES.register("leva",
                    () -> EntityType.Builder.of(leva::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("leva"));

    public static final RegistryObject<EntityType<mina>> MINA =
            ENTITY_TYPES.register("mina",
                    () -> EntityType.Builder.of(mina::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("mina"));

    public static final RegistryObject<EntityType<nela>> NELA =
            ENTITY_TYPES.register("nela",
                    () -> EntityType.Builder.of(nela::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("nela"));

    public static final RegistryObject<EntityType<okra>> OKRA =
            ENTITY_TYPES.register("okra",
                    () -> EntityType.Builder.of(okra::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("okra"));

    public static final RegistryObject<EntityType<saka>> SAKA =
            ENTITY_TYPES.register("saka",
                    () -> EntityType.Builder.of(saka::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("saka"));

    public static final RegistryObject<EntityType<tova>> TOVA =
            ENTITY_TYPES.register("tova",
                    () -> EntityType.Builder.of(tova::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("tova"));

    public static final RegistryObject<EntityType<unna>> UNNA =
            ENTITY_TYPES.register("unna",
                    () -> EntityType.Builder.of(unna::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("unna"));

    public static final RegistryObject<EntityType<veta>> VETA =
            ENTITY_TYPES.register("veta",
                    () -> EntityType.Builder.of(veta::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("veta"));

    public static final RegistryObject<EntityType<wira>> WIRA =
            ENTITY_TYPES.register("wira",
                    () -> EntityType.Builder.of(wira::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("wira"));

    public static final RegistryObject<EntityType<xila>> XILA =
            ENTITY_TYPES.register("xila",
                    () -> EntityType.Builder.of(xila::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("xila"));

    public static final RegistryObject<EntityType<yuka>> YUKA =
            ENTITY_TYPES.register("yuka",
                    () -> EntityType.Builder.of(yuka::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("yuka"));

    public static final RegistryObject<EntityType<zoha>> ZOHA =
            ENTITY_TYPES.register("zoha",
                    () -> EntityType.Builder.of(zoha::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("zoha"));

    public static final RegistryObject<EntityType<anna>> ANNA =
            ENTITY_TYPES.register("anna",
                    () -> EntityType.Builder.of(anna::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("anna"));

    public static final RegistryObject<EntityType<bona>> BONA =
            ENTITY_TYPES.register("bona",
                    () -> EntityType.Builder.of(bona::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("bona"));

    public static final RegistryObject<EntityType<cana>> CANA =
            ENTITY_TYPES.register("cana",
                    () -> EntityType.Builder.of(cana::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("cana"));

    public static final RegistryObject<EntityType<dona>> DONA =
            ENTITY_TYPES.register("dona",
                    () -> EntityType.Builder.of(dona::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("dona"));

    public static final RegistryObject<EntityType<elna>> ELNA =
            ENTITY_TYPES.register("elna",
                    () -> EntityType.Builder.of(elna::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("elna"));

    public static final RegistryObject<EntityType<fana>> FANA =
            ENTITY_TYPES.register("fana",
                    () -> EntityType.Builder.of(fana::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("fana"));

    public static final RegistryObject<EntityType<gana>> GANA =
            ENTITY_TYPES.register("gana",
                    () -> EntityType.Builder.of(gana::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("gana"));

    public static final RegistryObject<EntityType<iana>> IANA =
            ENTITY_TYPES.register("iana",
                    () -> EntityType.Builder.of(iana::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("iana"));

    public static final RegistryObject<EntityType<jana>> JANA =
            ENTITY_TYPES.register("jana",
                    () -> EntityType.Builder.of(jana::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("jana"));

    public static final RegistryObject<EntityType<kana>> KANA =
            ENTITY_TYPES.register("kana",
                    () -> EntityType.Builder.of(kana::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("kana"));

    public static final RegistryObject<EntityType<mana>> MANA =
            ENTITY_TYPES.register("mana",
                    () -> EntityType.Builder.of(mana::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("mana"));

    public static final RegistryObject<EntityType<nana>> NANA =
            ENTITY_TYPES.register("nana",
                    () -> EntityType.Builder.of(nana::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("nana"));

    public static final RegistryObject<EntityType<zory>> ZORY =
            ENTITY_TYPES.register("zory",
                    () -> EntityType.Builder.of(zory::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("zory"));

    public static final RegistryObject<EntityType<bixy>> BIXY =
            ENTITY_TYPES.register("bixy",
                    () -> EntityType.Builder.of(bixy::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("bixy"));

    public static final RegistryObject<EntityType<cozy>> COZY =
            ENTITY_TYPES.register("cozy",
                    () -> EntityType.Builder.of(cozy::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("cozy"));

    public static final RegistryObject<EntityType<dazy>> DAZY =
            ENTITY_TYPES.register("dazy",
                    () -> EntityType.Builder.of(dazy::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("dazy"));

    public static final RegistryObject<EntityType<elmy>> ELMY =
            ENTITY_TYPES.register("elmy",
                    () -> EntityType.Builder.of(elmy::new, MobCategory.CREATURE).sized(0.6F, 1.8F).build("elmy"));
}