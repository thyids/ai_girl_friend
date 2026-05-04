package com.thyids.ai_girl_friend.network;

import com.thyids.ai_girl_friend.Ai_girl_friend; // 换成你的主类包名
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    // 每一个数据包都需要一个唯一的 ID
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation("ai_girl_friend", "messages")) // 这里的ID必须和你的mod id一致
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        // 注册召唤老婆的数据包
        net.messageBuilder(SummonPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SummonPacket::decode)
                .encoder(SummonPacket::encode)
                .consumerMainThread(SummonPacket::handle)
                .add();
    }

    // 发送给服务器的方法
    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    // 发送给特定玩家的方法（以后可能会用到）
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}