package com.thyids.ai_girl_friend.gui;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.thyids.ai_girl_friend.Ai_girl_friend;
import com.thyids.ai_girl_friend.web.AIConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Ai_girl_friend.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AIConfigCommand {

    @SubscribeEvent
    public static void registerCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("aiconfig")
                .then(Commands.literal("gui")
                        .executes(ctx -> {
                            // 在主线程打开GUI
                            Minecraft.getInstance().execute(() -> {
                                Minecraft.getInstance().setScreen(new AIConfigScreen());
                            });
                            return 1;
                        })
                )
                .then(Commands.literal("get")
                        .executes(ctx -> {
                            String key = AIConfig.getApiKey();
                            String maskedKey = key.length() > 8 ? key.substring(0, 4) + "****" + key.substring(key.length() - 4) : key;
                            String base = AIConfig.getApiBase();
                            ctx.getSource().sendSuccess(() -> Component.literal(
                                    "§dAI配置信息:\n" +
                                            "§fAPI Key: §a" + maskedKey + "\n" +
                                            "§fAPI Base: §a" + base
                            ), false);
                            return 1;
                        })
                )
                .then(Commands.literal("set")
                        .then(Commands.literal("key")
                                .then(Commands.argument("apikey", StringArgumentType.string())
                                        .executes(ctx -> {
                                            String apiKey = StringArgumentType.getString(ctx, "apikey");
                                            AIConfig.setApiKey(apiKey);
                                            ctx.getSource().sendSuccess(() -> Component.literal("§dAPI密钥已更新！"), false);
                                            return 1;
                                        })
                                )
                        )
                        .then(Commands.literal("base")
                                .then(Commands.argument("apibase", StringArgumentType.string())
                                        .executes(ctx -> {
                                            String apiBase = StringArgumentType.getString(ctx, "apibase");
                                            AIConfig.setApiBase(apiBase);
                                            ctx.getSource().sendSuccess(() -> Component.literal("§dAPI地址已更新！"), false);
                                            return 1;
                                        })
                                )
                        )
                )
        );
    }
}