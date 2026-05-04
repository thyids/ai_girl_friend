package com.thyids.ai_girl_friend.gui;

import com.thyids.ai_girl_friend.web.AIConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AIConfigScreen extends Screen {
    private EditBox apiKeyInput;
    private EditBox apiBaseInput;
    private EditBox modelInput;
    private boolean saved = false;

    public AIConfigScreen() {
        super(Component.literal("AI 配置"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // API Key 标签
        this.addRenderableWidget(Button.builder(Component.literal("§dAPI 密钥:"), (b) -> {}).bounds(centerX - 150, centerY - 60, 80, 20).build());

        // API Key 输入框 - 使用 addRenderableWidget
        apiKeyInput = new EditBox(this.font, centerX - 60, centerY - 60, 220, 20, Component.literal("API Key"));
        apiKeyInput.setValue(AIConfig.getApiKey());
        apiKeyInput.setMaxLength(100);
        this.addRenderableWidget(apiKeyInput);

        // API Base 标签
        this.addRenderableWidget(Button.builder(Component.literal("§dAPI 地址:"), (b) -> {}).bounds(centerX - 150, centerY - 25, 80, 20).build());

        // API Base 输入框 - 使用 addRenderableWidget
        apiBaseInput = new EditBox(this.font, centerX - 60, centerY - 25, 220, 20, Component.literal("API Base"));
        apiBaseInput.setValue(AIConfig.getApiBase());
        apiBaseInput.setMaxLength(200);
        this.addRenderableWidget(apiBaseInput);

        // 模型标签
        this.addRenderableWidget(Button.builder(Component.literal("§d模型名称:"), (b) -> {}).bounds(centerX - 150, centerY + 10, 80, 20).build());

        // 模型输入框 - 使用 addRenderableWidget
        modelInput = new EditBox(this.font, centerX - 60, centerY + 10, 220, 20, Component.literal("Model"));
        modelInput.setValue(AIConfig.getModel());
        modelInput.setMaxLength(100);
        this.addRenderableWidget(modelInput);

        // 保存按钮
        this.addRenderableWidget(Button.builder(Component.literal("§a保存配置"), (b) -> {
            AIConfig.setApiKey(apiKeyInput.getValue());
            AIConfig.setApiBase(apiBaseInput.getValue());
            AIConfig.setModel(modelInput.getValue());
            saved = true;
        }).bounds(centerX - 60, centerY + 45, 120, 20).build());

        // 返回按钮
        this.addRenderableWidget(Button.builder(Component.literal("§c返回"), (b) -> {
            this.onClose();
        }).bounds(centerX - 60, centerY + 75, 120, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 30, 0xFFFFFF);

        // 显示保存成功提示
        if (saved) {
            graphics.drawCenteredString(this.font, "§a配置已保存！", this.width / 2, 60, 0x00FF00);
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        super.onClose();
    }
}