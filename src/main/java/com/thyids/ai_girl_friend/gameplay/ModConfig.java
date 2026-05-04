package com.thyids.ai_girl_friend.gameplay;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thyids.ai_girl_friend.Ai_girl_friend;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

/**
 * 配置文件系统 - 允许玩家在不改代码的情况下调整参数
 */
public class ModConfig {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static JsonObject config;

    // 默认值
    private static final JsonObject DEFAULTS = new JsonObject();

    static {
        DEFAULTS.addProperty("summon_cost_level", 2);
        DEFAULTS.addProperty("max_girlfriend_per_player", 10);
        DEFAULTS.addProperty("chat_range", 64);
        DEFAULTS.addProperty("tts_enabled", false);
        DEFAULTS.addProperty("flower_mood_boost", 15);
        DEFAULTS.addProperty("diamond_mood_boost", 20);
        DEFAULTS.addProperty("jealousy_rate", 0.15);
        DEFAULTS.addProperty("flower_give_interval_ticks", 100);
        DEFAULTS.addProperty("max_memory_size", 15);

        // AI 行为选项
        DEFAULTS.addProperty("autonomous_chores", true);
        DEFAULTS.addProperty("autonomous_plant_flowers", true);
        DEFAULTS.addProperty("social_jealousy", true);
        DEFAULTS.addProperty("runaway_enabled", true);
    }

    public static void load() {
        Path configPath = FMLPaths.CONFIGDIR.get();
        File configFile = configPath.resolve("ai_girl_friend.json").toFile();

        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                config = JsonParser.parseReader(reader).getAsJsonObject();
                // 补全缺失的默认值
                for (String key : DEFAULTS.keySet()) {
                    if (!config.has(key)) {
                        config.add(key, DEFAULTS.get(key));
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Failed to load config, using defaults", e);
                config = DEFAULTS.deepCopy();
            }
        } else {
            config = DEFAULTS.deepCopy();
            save();
        }
    }

    public static void save() {
        Path configPath = FMLPaths.CONFIGDIR.get();
        File configFile = configPath.resolve("ai_girl_friend.json").toFile();
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(config, writer);
        } catch (Exception e) {
            LOGGER.error("Failed to save config", e);
        }
    }

    public static int getInt(String key) {
        if (config == null) load();
        return config.has(key) ? config.get(key).getAsInt() : DEFAULTS.get(key).getAsInt();
    }

    public static double getDouble(String key) {
        if (config == null) load();
        return config.has(key) ? config.get(key).getAsDouble() : DEFAULTS.get(key).getAsDouble();
    }

    public static boolean getBoolean(String key) {
        if (config == null) load();
        return config.has(key) ? config.get(key).getAsBoolean() : DEFAULTS.get(key).getAsBoolean();
    }

    public static String getString(String key) {
        if (config == null) load();
        return config.has(key) ? config.get(key).getAsString() : DEFAULTS.get(key).getAsString();
    }
}
