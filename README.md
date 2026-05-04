# AI Girl Friend - Minecraft Forge Mod

一款为 Minecraft 1.20.1 设计的 AI 女友模组，让你在游戏中拥有一个智能互动的虚拟女友！

## 功能特性

### 🤖 智能对话
- 与 AI 女友进行自然语言对话
- 支持多种 AI 模型（DeepSeek、GLM 等）
- 上下文感知的对话系统
- 情绪系统，女友会根据对话改变心情

### 💝 互动系统
- **亲亲** - 输入"亲亲"或"亲我"触发亲吻动画
- **摸头** - 输入"摸摸头"或"摸头"触发摸头动画
- **睡觉** - 输入"躺下"或"睡觉"一起入睡
- **抱抱** - 输入"抱"或"抱抱"触发拥抱动作

### 🧠 记忆系统
- 自动记录对话历史
- 记忆压缩功能，将长对话总结为背景信息
- 长期记忆保存

### ⚙️ 配置界面
- 游戏内 GUI 配置 AI 参数
- 支持修改 API Key、API 地址和模型名称
- 配置自动保存到本地文件

## 快速开始

### 前置要求
- Minecraft 1.20.1
- Forge 47.2.0 或更高版本
- 有效的 AI API Key（如 DeepSeek、GLM）

### 安装步骤

1. **下载模组**
   - 从 Release 页面下载最新版本的 `.jar` 文件
   - 将文件放入 `.minecraft/mods/` 目录

2. **配置 API**
   - 启动游戏并创建世界
   - 使用命令 `/aiconfig` 打开配置界面
   - 输入你的 API Key 和其他配置

3. **生成女友**
   - 游戏中使用 AI 女友生成物品右键放置
   - 你的 AI 女友就会出现在面前！

### 配置说明

#### API 配置
| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| API 密钥 | `d7085a54f57e44689e9e9b81a2984301.jkigR8P8CKLo7sXq` | 你的 AI 服务 API Key |
| API 地址 | `https://open.bigmodel.cn/api/paas/v4` | AI 服务的基础 URL |
| 模型名称 | `glm-4.5-flash` | 使用的 AI 模型名称 |

#### 支持的模型

**DeepSeek:**
- `deepseek-v4-pro` - 高级推理模型
- `deepseek-v4-flash` - 快速推理模型

**GLM (智谱):**
- `glm-4.5-flash` - 快速版 GLM-4.5
- `glm-4.5` - 标准版 GLM-4.5
...

...

### 命令说明

| 命令 | 功能 |
|------|------|
| `/aiconfig` | 打开 AI 配置 GUI |
| `/aiconfig set key <apikey>` | 设置 API 密钥 |
| `/aiconfig set base <apibase>` | 设置 API 地址 |

## 项目结构

```
TI_ai_girl_friend/
├── src/main/java/com/thyids/ai_girl_friend/
│   ├── gui/              # GUI 界面相关
│   │   ├── AIConfigScreen.java    # 配置界面
│   │   └── AIConfigCommand.java   # 配置命令
│   ├── web/              # 网络通信相关
│   │   ├── AIConfig.java          # 配置管理
│   │   ├── DeepSeekAPI.java       # 主要 API 调用
│   │   ├── AdvancedAIService.java # 高级 AI 服务
│   │   └── ChatProcessor.java     # 聊天处理
│   ├── world/entity/     # 实体相关
│   │   └── girl_friend/           # 女友实体
│   └── event/            # 事件处理
├── look/                 # Python 后端服务
│   ├── src/              # Python 源代码
│   └── modules/          # 功能模块
└── resources/            # 资源文件
```

## 开发指南

### 环境要求
- JDK 17 或更高版本
- Gradle 8.x
- Minecraft Forge MDK 1.20.1

### 构建项目

```bash
# 克隆项目
git clone <repository-url>
cd TI_ai_girl_friend

# 构建模组
./gradlew build

# 构建产物位于 build/libs/ 目录
```

### 运行开发环境

```bash
# 运行客户端
./gradlew runClient

# 运行服务端
./gradlew runServer
```

## API 兼容性

本模组支持以下 AI 服务提供商：

| 提供商 | API 地址示例 | 模型示例 |
|--------|-------------|----------|
| DeepSeek | `https://api.deepseek.com` | `deepseek-chat` |
| GLM (智谱) | `https://open.bigmodel.cn/api/paas/v4/` | `glm-4-flash` |
| 自定义 | 任意兼容 OpenAI 格式的 API | 对应模型名称 |

## 配置文件位置

配置文件保存在用户目录下：
```
Windows: %USERPROFILE%/.mc_ai_girlfriend/config.properties
Linux/Mac: ~/.mc_ai_girlfriend/config.properties
```

配置文件格式：
```properties
api_key=your-api-key-here
api_base=https://api.deepseek.com
model=deepseek-chat
```

## 常见问题

### Q: 为什么配置了 GLM 模型却无法使用？
A: 请确保：
1. API 地址正确（GLM 需要使用 `/api/paas/v4/chat/completions` 格式）
2. 模型名称正确（如 `glm-4-flash`）
3. API Key 有效且有足够余额

### Q: 女友为什么不说话？
A: 检查以下几点：
1. 网络连接正常
2. API Key 和地址配置正确
3. 输入消息包含女友名字或"老婆"关键词

### Q: 如何修改女友的名字？
A: 使用铁砧给女友命名，或使用游戏内的命名功能。

## 许可证

本项目采用 MIT 许可证，详见 LICENSE 文件。

## 贡献

欢迎提交 Issue 和 Pull Request！

## 致谢

- [DeepSeek](https://www.deepseek.com/) - AI 服务支持
- [Minecraft Forge](https://files.minecraftforge.net/) - 模组框架
- [KouriChat 项目](https://github.com/KouriChat/KouriChat) - AI 逻辑参考
- [Trae](https://www.trae.cn/) - AI 辅助编写
- [Google Gemini](https://gemini.google.com/) - AI 辅助编写
- [豆包](https://www.doubao.com/) - AI 辅助编写

---

⭐ 如果你喜欢这个项目，请给它一个 Star！