package com.thyids.ai_girl_friend.web;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thyids.ai_girl_friend.entities.client.PlayerAnimationHolder;
import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Minecraft AI处理器 - 完整整合Python look项目的AI逻辑
 */
public class MinecraftAIProcessor {

    private static final Gson GSON = new Gson();
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    /**
     * 与AI交互的主要方法
     */
    public static void interactWithAI(Player player, CreeperMother girl, String message) {
        if (player == null || girl == null || !girl.isAlive()) return;

        // 聊天关键词自动触发动作
        String processedMessage = triggerActionsFromKeywords(girl, message);
        boolean hasTriggeredAction = !processedMessage.equals(message);

        String currentName = DeepSeekAPI.getName(girl);
        int oldMood = girl.getMoodLevel();
        String moodDesc = (oldMood < 20) ? "极度生气" : (oldMood < 40) ? "不太高兴" : (oldMood < 60) ? "心情正常" : (oldMood < 80) ? "心情不错" : "极度开心";

        player.sendSystemMessage(Component.literal("§a[" + currentName + "] §7正在思考中..."));

        String finalMessage = processedMessage;
        CompletableFuture.runAsync(() -> {
            try {
                JsonObject json = new JsonObject();
                json.addProperty("model", AIConfig.getModel());

                // 构建完整的消息数组
                JsonArray messages = new JsonArray();

                // 添加上下文感知的系统消息
                JsonObject systemMessage = new JsonObject();
                systemMessage.addProperty("role", "system");
                systemMessage.addProperty("content",
                        ContextAwareProcessor.buildContextualPrompt(girl, player, finalMessage));
                messages.add(systemMessage);

                // 添加历史记忆
                messages.addAll(AdvancedAIManager.getCurrentContext(girl));

                json.add("messages", messages);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(AIConfig.getApiUrl()))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + AIConfig.getApiKey())
                        .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(json)))
                        .build();

                HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
                JsonObject resJson = GSON.fromJson(response.body(), JsonObject.class);

                if (resJson == null || !resJson.has("choices")) {
                    throw new RuntimeException("API 响应异常");
                }

                String aiRawText = resJson.getAsJsonArray("choices")
                        .get(0).getAsJsonObject()
                        .getAsJsonObject("message")
                        .get("content").getAsString();

                // 回到主线程处理响应
                MinecraftServer server = player.getServer();
                if (server != null) {
                    server.execute(() -> {
                        if (player == null || !girl.isAlive()) return;

                        // 处理心情变化
                        Matcher moodMatcher = Pattern.compile("\\[MOOD:([+-]?\\d+)\\]").matcher(aiRawText);
                        if (moodMatcher.find()) {
                            girl.adjustMood(Integer.parseInt(moodMatcher.group(1)));
                        }

                        // 处理动作
                        handleAIActions(girl, aiRawText);

                        // 处理特殊指令
                        handleSpecialCommands(girl, aiRawText);

                        // 视觉反馈
                        int newMood = girl.getMoodLevel();
                        if (girl.level() instanceof ServerLevel serverLevel) {
                            if (newMood > oldMood) {
                                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                                        girl.getX(), girl.getEyeY() + 0.5, girl.getZ(), 5, 0.3, 0.3, 0.3, 0.1);
                            } else if (newMood < oldMood) {
                                serverLevel.sendParticles(ParticleTypes.ANGRY_VILLAGER,
                                        girl.getX(), girl.getEyeY() + 0.5, girl.getZ(), 5, 0.3, 0.3, 0.3, 0.1);
                            }
                        }

                        // 清理并发送文本
                        String cleanText = aiRawText.replaceAll("\\[MOOD:.*?\\]", "")
                                .replaceAll("\\[ACTION:.*?\\]", "")
                                .replaceAll("\\[TASK:.*?\\]", "")
                                .replaceAll("\\[DIG:.*?\\]", "")
                                .trim();

                        String formattedMsg = "§a[" + currentName + " §d❤" + newMood + "§a] §f" + cleanText;

                        SpeakerManager.queueSpeech(player, currentName, formattedMsg);

                        // 更新情感记忆
                        EmotionalMemoryModule.updateEmotionalMemory(girl, cleanText, newMood - oldMood);

                        // 添加到记忆
                        girl.addMessageToMemory("assistant", cleanText);
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
                if (player.getServer() != null) {
                    player.getServer().execute(() ->
                            player.sendSystemMessage(Component.literal("§c[错误] " + DeepSeekAPI.getName(girl) + "的思绪断开了..."))
                    );
                }
            }
        });
    }

    /**
     * 从关键词触发动作
     */
    private static String triggerActionsFromKeywords(CreeperMother girl, String message) {
        String lowerMsg = message.toLowerCase().trim();
        String originalMessage = message;

        if (lowerMsg.contains("亲亲") || lowerMsg.contains("亲我") || lowerMsg.contains("嘴")) {
            girl.triggerAnimation("kiss");
            PlayerAnimationHolder.get().playAnim("kiss", girl.tickCount);
            message += "（老公要亲亲）";
        }
        if (lowerMsg.contains("摸摸头") || lowerMsg.contains("摸头") || lowerMsg.contains("乖")) {
            girl.triggerAnimation("head_pat");
            PlayerAnimationHolder.get().playAnim("head_pat", girl.tickCount);
            message += "（老公摸你头）";
        }
        if (lowerMsg.contains("躺下") || lowerMsg.contains("睡觉") || lowerMsg.contains("陪我睡")) {
            if(girl.getMoodLevel() < 30){
                return originalMessage; // 简单处理，不修改消息
            }
            girl.trySleep();
            message += "（老公要你躺下陪睡觉）";
        }
        if (lowerMsg.contains("抱") || lowerMsg.contains("抱抱")) {
            girl.activeEmbrace(null); // 简化处理
            PlayerAnimationHolder.get().playAnim("embrace", girl.tickCount);
        }

        return message;
    }

    /**
     * 处理AI动作指令
     */
    private static void handleAIActions(CreeperMother girl, String aiResponse) {
        if (aiResponse.contains("[ACTION:KISS]")) {
            girl.triggerAnimation("kiss");
        }
        if (aiResponse.contains("[ACTION:FOLLOW]")) {
            girl.setBehaviorState(1); // 跟随状态
        }
        if (aiResponse.contains("[ACTION:STAY]")) {
            girl.setBehaviorState(0); // 停留状态
        }
        if (aiResponse.contains("[ACTION:JUMP]")) {
            girl.triggerAnimation("jump");
        }
        if (aiResponse.contains("[ACTION:OPEN_CHEST]")) {
            girl.getEntityData().set(CreeperMother.CURRENT_TASK, "OPEN_CHEST");
        }
        if (aiResponse.contains("[ACTION:SMELT]")) {
            girl.getEntityData().set(CreeperMother.CURRENT_TASK, "SMELT");
        }
        if (aiResponse.contains("[ACTION:MINE]")) {
            girl.getEntityData().set(CreeperMother.CURRENT_TASK, "MINE");
        }
        if (aiResponse.contains("[ACTION:GIVE]")) {
            // 赠送物品的逻辑
        }
        if (aiResponse.contains("[ACTION:HUG]")) {
            girl.triggerAnimation("embrace");
        }
    }

    /**
     * 处理特殊命令
     */
    private static void handleSpecialCommands(CreeperMother girl, String aiResponse) {
        // 解析 [DIG:x,y,z] - 挖掘指令
        Matcher digMatcher = Pattern.compile("\\[DIG:(-?\\d+),(-?\\d+),(-?\\d+)\\]").matcher(aiResponse);
        while (digMatcher.find()) {
            int x = Integer.parseInt(digMatcher.group(1));
            int y = Integer.parseInt(digMatcher.group(2));
            int z = Integer.parseInt(digMatcher.group(3));

            BlockPos targetPos = new BlockPos(x, y, z);
            if(girl.blockPosition().closerThan(targetPos, 5)) {
                girl.level().destroyBlock(targetPos, true, girl);
            }
        }

        // 解析 [TASK:DIG:x,y,z] - 挖掘任务
        Matcher taskDigMatcher = Pattern.compile("\\[TASK:DIG:(-?\\d+),(-?\\d+),(-?\\d+)\\]").matcher(aiResponse);
        while (taskDigMatcher.find()) {
            int x = Integer.parseInt(taskDigMatcher.group(1));
            int y = Integer.parseInt(taskDigMatcher.group(2));
            int z = Integer.parseInt(taskDigMatcher.group(3));

            girl.getEntityData().set(CreeperMother.CURRENT_TASK, "DIG:" + x + "," + y + "," + z);
            girl.getNavigation().moveTo(x, y, z, 1.2D);
        }

        // 解析 [TASK:GO_TO:x,y,z] - 移动任务
        Matcher goToMatcher = Pattern.compile("\\[TASK:GO_TO:(-?\\d+),(-?\\d+),(-?\\d+)\\]").matcher(aiResponse);
        while (goToMatcher.find()) {
            int x = Integer.parseInt(goToMatcher.group(1));
            int y = Integer.parseInt(goToMatcher.group(2));
            int z = Integer.parseInt(goToMatcher.group(3));

            girl.getNavigation().moveTo(x, y, z, 1.2D);
        }
    }
}