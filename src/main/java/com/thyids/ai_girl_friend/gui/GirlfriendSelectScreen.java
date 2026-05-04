package com.thyids.ai_girl_friend.gui;

import com.thyids.ai_girl_friend.network.SummonPacket;
import com.thyids.ai_girl_friend.network.ModMessages;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GirlfriendSelectScreen extends Screen {
    private final List<String> names = new ArrayList<>();
    private final Set<Integer> selectedIndices = new HashSet<>();
    private int currentPage = 0;
    private static final int PER_PAGE = 20;

    public GirlfriendSelectScreen() {
        super(Component.literal("女友核心 - 多选模式"));
        String[] baseNames = {"婉宝", "语儿", "小岑", "妩儿", "清禾", "小柠", "阿娴", "玥宝", "芷儿", "晚夕", "小汐", "甜栗", "岚宝", "妤儿", "棠夕", "冷月", "瑶夕", "小柚", "茉儿", "晚吟", "暖兮", "清柠", "安禾", "萱儿", "鹿宝", "辞兮", "芮宝", "涵儿", "绵夕", "瑶禾", "汐月", "乐兮", "雪禾", "冉儿", "晚棠", "寒夕", "舒禾", "宁夕", "棉宝", "芊儿", "苒夕", "薇儿", "柔兮", "瑶苒", "甜禾", "梵兮", "禾夕", "晴宝", "予儿", "娴夕", "桃兮", "晴禾", "绵苒", "溪儿", "柠夕", "绯兮", "宁苒", "姝儿", "玥兮", "糯宝", "澜儿", "茉苒", "瑶芊", "晚茉", "清苒", "糯夕", "棠儿", "娴苒", "汐宝", "清寻", "绯禾", "眠兮", "寻儿", "妍夕", "柠禾", "妤苒", "思禾", "冉芊", "夜夕", "星禾", "汐苒", "夕宝", "溪禾", "桐儿", "白兮", "婉禾", "软儿", "念兮", "枳宝", "棠芊", "青瑶", "星瑶", "月汐", "雪见", "铃兰", "花音", "莲依", "若薇", "思雨", "梦蝶", "绮罗", "紫嫣", "碧荷", "翠微", "丹彤", "芳华", "海棠", "佳黛", "可盈", "兰萱", "曼云", "沐曦", "念雪", "芊羽", "晴岚", "茹薇", "苏念", "听荷", "宛然", "夕颜", "晓霜", "雅琴", "映月", "芷若", "竹清", "采薇", "春熙", "清荷", "菲儿", "歌澜", "涵双", "静蕾", "琬琰", "馨予", "瑶姬", "芷兰", "白露", "初雪", "黛眉", "绯樱", "孤月", "红鸾", "惠心", "寄晴", "锦瑟", "君悦", "兰亭", "灵素", "洛神", "眉妩", "明曦", "墨染", "弄玉", "飘絮", "齐眉", "清漪", "琼华", "秋菱", "如霜", "若水", "诗涵", "水瑶", "素心", "澹台", "天香", "婉清", "问柳", "惜春", "萧湘", "秀娥", "煦煦", "璇珠", "嫣然", "瑶琴", "依云", "盈袖", "幽兰", "语嫣", "毓秀", "云裳", "昭君", "织女", "子衿", "醉月", "傲霜", "冰蝶", "彩萱", "沉香", "楚颜", "初心", "翠岚", "丁香", "端丽", "飞琼", "芙蓉", "谷雨", "寒梅", "惠然", "霁月", "锦心", "静仪", "卷耳", "兰芝", "莲心", "凌霄", "柳絮", "绿萼", "梅妆", "明珠", "慕青", "凝露", "佩兰", "品若", "琪花", "千雪", "茜素", "轻罗", "清露", "秋容", "如意", "蕊珠"};
        for (int i = 1; i <= 221; i++) {
            names.add(i <= baseNames.length ? baseNames[i - 1] : "老婆 " + i + " 号");
        }
    }

    @Override
    protected void init() {
        refreshButtons();
    }

    private void refreshButtons() {
        this.clearWidgets();
        int startX = this.width / 2 - 160;
        int startY = 50;

        int startIndex = currentPage * PER_PAGE;
        int endIndex = Math.min(startIndex + PER_PAGE, names.size());

        for (int i = startIndex; i < endIndex; i++) {
            int idx = i - startIndex;
            int currentIdx = i;

            String prefix = selectedIndices.contains(currentIdx) ? "§a✔ " : "§7○ ";

            this.addRenderableWidget(Button.builder(Component.literal(prefix + names.get(i)), (b) -> {
                if (selectedIndices.contains(currentIdx)) {
                    selectedIndices.remove(currentIdx);
                } else {
                    selectedIndices.add(currentIdx);
                }
                refreshButtons();
            }).bounds(startX + (idx % 4) * 80, startY + (idx / 4) * 25, 75, 20).build());
        }

        // ===================== 【新增：全选 / 取消全选 按钮】 =====================
        this.addRenderableWidget(Button.builder(Component.literal("§a全选"), (b) -> {
            // 选中当前页所有女友
            for (int i = startIndex; i < endIndex; i++) {
                selectedIndices.add(i);
            }
            refreshButtons();
        }).bounds(this.width / 2 - 90, 180, 60, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("§c取消全选"), (b) -> {
            // 取消当前页所有选中
            for (int i = startIndex; i < endIndex; i++) {
                selectedIndices.remove(i);
            }
            refreshButtons();
        }).bounds(this.width / 2 - 25, 180, 70, 20).build());
        // ======================================================================

        // 一键召唤按钮
        if (!selectedIndices.isEmpty()) {
            this.addRenderableWidget(Button.builder(Component.literal("§6确认召唤 (" + selectedIndices.size() + ")"), (b) -> {
                for (Integer index : selectedIndices) {
                    ModMessages.sendToServer(new SummonPacket(index));
                }
                this.onClose();
            }).bounds(this.width / 2 - 40, 210, 80, 20).build());
        }

        // 翻页
        if (currentPage > 0) {
            this.addRenderableWidget(Button.builder(Component.literal("◀"), (b) -> { currentPage--; refreshButtons(); })
                    .bounds(this.width / 2 + 60, 180, 20, 20).build());
        }
        if (endIndex < names.size()) {
            this.addRenderableWidget(Button.builder(Component.literal("▶"), (b) -> { currentPage++; refreshButtons(); })
                    .bounds(this.width / 2 + 85, 180, 20, 20).build());
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        graphics.drawCenteredString(this.font, "已选择: " + selectedIndices.size() + " 位 (每位消耗 2 级经验)", this.width / 2, 35, 0xAAAAAA);
        super.render(graphics, mouseX, mouseY, partialTick);
    }
}