package com.thyids.ai_girl_friend.world.entity;

import com.thyids.ai_girl_friend.Ai_girl_friend;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Ai_girl_friend.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyBindRegister {
    public static KeyMapping KEY_HEAD_PAT;
    public static KeyMapping KEY_KISS;
    public static KeyMapping KEY_EMBRACE;
    public static KeyMapping KEY_LIE_DOWN;

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        KEY_HEAD_PAT = new KeyMapping("key.ai_girl_friend.head_pat", GLFW.GLFW_KEY_H, "category.ai_girl_friend");
        KEY_KISS = new KeyMapping("key.ai_girl_friend.kiss", GLFW.GLFW_KEY_J, "category.ai_girl_friend");
        KEY_EMBRACE = new KeyMapping("key.ai_girl_friend.embrace", GLFW.GLFW_KEY_K, "category.ai_girl_friend");
        KEY_LIE_DOWN = new KeyMapping("key.ai_girl_friend.lie_down", GLFW.GLFW_KEY_L, "category.ai_girl_friend");

        event.register(KEY_HEAD_PAT);
        event.register(KEY_KISS);
        event.register(KEY_EMBRACE);
        event.register(KEY_LIE_DOWN);
    }
}