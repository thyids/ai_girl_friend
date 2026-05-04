package com.thyids.ai_girl_friend.network;

import com.thyids.ai_girl_friend.web.DeepSeekAPI;
import com.thyids.ai_girl_friend.world.entity.ModEntityList;
import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.network.NetworkEvent;
import java.util.Random;
import java.util.function.Supplier;

public class SummonPacket {
    private final int index;
    private static final Random RAND = new Random();

    public SummonPacket(int index) { this.index = index; }
    public static void encode(SummonPacket msg, FriendlyByteBuf buf) { buf.writeInt(msg.index); }
    public static SummonPacket decode(FriendlyByteBuf buf) { return new SummonPacket(buf.readInt()); }

    public static void handle(SummonPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            // 经验等级检查
            if (player.experienceLevel < 2) {
                player.displayClientMessage(Component.literal("§c你的经验等级不足 2 级，无法召唤老婆！"), true);
                return;
            }

            int index = msg.index;
            var list = ModEntityList.GIRLFRIENDS;

            if (index >= 0 && index < list.size()) {
                ServerLevel level = player.serverLevel();
                Mob girl = (Mob) list.get(index).get().create(level);

                if (girl != null) {
                    // 1. 先计算坐标（防止挤压）
                    double offsetX = (RAND.nextDouble() - 0.5D) * 4.0D;
                    double offsetZ = (RAND.nextDouble() - 0.5D) * 4.0D;
                    double spawnX = player.getX() + offsetX;
                    double spawnY = player.getY();
                    double spawnZ = player.getZ() + offsetZ;

                    // 2. 预设位置
                    girl.moveTo(spawnX, spawnY, spawnZ, player.getYRot(), 0.0F);

                    // 3. 【核心修复】在实体 addFreshEntity 之前完成所有绑定
                    if (girl instanceof CreeperMother girlfriend) {
                        // 强制写入 UUID
                        girlfriend.husbandUUID = player.getUUID();

                        // 调用类内绑定方法处理同步和记忆
                        girlfriend.setHusband(player);

                        // 调试信息：发送给玩家确认
                        player.sendSystemMessage(Component.literal("§e[Debug] 正在为新老婆绑定 UUID..."));

                        // 验证内存绑定
                        if (girlfriend.getHusbandUUID() != null) {
                            player.sendSystemMessage(Component.literal("§a[Debug] 内存绑定成功！老公 ID: " + girlfriend.getHusbandUUID().toString().substring(0,8)));
                        }

                        // 设置名字
                        String name = DeepSeekAPI.getName(girlfriend);
                        girlfriend.setCustomName(Component.literal(name));
                        girlfriend.setCustomNameVisible(true);

                        // 设为永久存活
                        girlfriend.setPersistenceRequired();
                    }

                    // 4. 最后一步：将已经设置好数据的实体加入世界
                    // 这样 AI 在激活的第一帧就能识别到老公，不会出现“不动”的情况
                    level.addFreshEntity(girl);

                    // 扣除经验
                    player.giveExperienceLevels(-2);
                    player.displayClientMessage(Component.literal("§d召唤成功！"), true);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}