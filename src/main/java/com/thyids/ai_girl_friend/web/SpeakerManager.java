package com.thyids.ai_girl_friend.web;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;

public class SpeakerManager {
    private static final Queue<SpeechTask> SPEECH_QUEUE = new LinkedList<>();
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    private static boolean isProcessing = false;
    private static int waitingTicks = 0;

    public static void queueSpeech(Player player, String name, String message) {
        SPEECH_QUEUE.add(new SpeechTask(player, name, message));
    }

    public static void tick() {
        if (waitingTicks > 0) {
            waitingTicks--;
            return;
        }
        if (SPEECH_QUEUE.isEmpty() || isProcessing) return;

        SpeechTask task = SPEECH_QUEUE.poll();
        if (task != null) {
            processSpeech(task);
        }
    }

    private static void processSpeech(SpeechTask task) {
        isProcessing = true;
        task.player.sendSystemMessage(Component.literal("§d[" + task.name + "] §f" + task.message));

        CompletableFuture.runAsync(() -> {
            synchronized (SpeakerManager.class) {
                waitingTicks = 40;
                isProcessing = false;
            }
        });
    }

    private record SpeechTask(Player player, String name, String message) {}
}