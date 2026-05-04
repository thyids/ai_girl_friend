package com.thyids.ai_girl_friend.web;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class AIConfig {
    private static final String CONFIG_FILE = System.getProperty("user.home") + "/.mc_ai_girlfriend/config.properties";
    private static Properties properties = new Properties();

    // 默认值 - api_base 只包含基础地址
    private static final String DEFAULT_API_KEY = "d7085a54f57e44689e9e9b81a2984301.jkigR8P8CKLo7sXq";
    private static final String DEFAULT_API_BASE = "https://open.bigmodel.cn/api/paas/v4";
    private static final String DEFAULT_MODEL = "glm-4.5-flash";
    private static final String API_ENDPOINT = "/chat/completions";

    private static String apiKey;
    private static String apiBase;
    private static String model;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        // 优先从环境变量读取
        apiKey = System.getenv("DEEPSEEK_API_KEY");
        apiBase = System.getenv("DEEPSEEK_API_BASE");
        model = System.getenv("DEEPSEEK_MODEL");

        // 尝试从配置文件读取（即使环境变量已设置，也可以覆盖）
        File configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                properties.load(reader);
                if (apiKey == null) apiKey = properties.getProperty("api_key");
                if (apiBase == null) apiBase = properties.getProperty("api_base");
                if (model == null) model = properties.getProperty("model");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 如果都没有设置，使用默认值
        if (apiKey == null) apiKey = DEFAULT_API_KEY;
        if (apiBase == null) apiBase = DEFAULT_API_BASE;
        if (model == null) model = DEFAULT_MODEL;
    }

    /**
     * 获取 API 密钥
     */
    public static String getApiKey() { return apiKey; }

    /**
     * 获取 API 基础地址（不含 endpoint）
     */
    public static String getApiBase() { return apiBase; }

    /**
     * 获取模型名称
     */
    public static String getModel() { return model; }

    /**
     * 获取完整的 API URL（基础地址 + endpoint）
     */
    public static String getApiUrl() { return apiBase + API_ENDPOINT; }

    /**
     * 设置 API 密钥
     */
    public static void setApiKey(String key) { apiKey = key; saveConfig(); }

    /**
     * 设置 API 基础地址
     */
    public static void setApiBase(String base) { apiBase = base; saveConfig(); }

    /**
     * 设置模型名称
     */
    public static void setModel(String modelName) { model = modelName; saveConfig(); }

    private static void saveConfig() {
        try {
            File configDir = new File(CONFIG_FILE).getParentFile();
            if (!configDir.exists()) configDir.mkdirs();

            properties.setProperty("api_key", apiKey);
            properties.setProperty("api_base", apiBase);
            properties.setProperty("model", model);

            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                properties.store(writer, "AI Girlfriend Configuration");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadConfig() { loadConfig(); }
}