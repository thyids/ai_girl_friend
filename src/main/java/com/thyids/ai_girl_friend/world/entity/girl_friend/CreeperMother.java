package com.thyids.ai_girl_friend.world.entity.girl_friend;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thyids.ai_girl_friend.entities.client.PlayerAnimationHolder;
import com.thyids.ai_girl_friend.gameplay.GirlfriendActionHandler;
import com.thyids.ai_girl_friend.gameplay.ModConfig;
import com.thyids.ai_girl_friend.web.AdvancedAIManager;
import com.thyids.ai_girl_friend.web.ContextAwareProcessor;
import com.thyids.ai_girl_friend.web.DeepSeekAPI;
import com.thyids.ai_girl_friend.web.EmotionalMemoryModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class CreeperMother extends PathfinderMob {
    private final SimpleContainer inventory = new SimpleContainer(27);
    private int hurtCooldown = 0;
    private boolean isEmbracingNav = false;
    public java.util.UUID husbandUUID = null;

    private String chatHistory = "";
    private JsonArray chatMessages = new JsonArray();
    private static final int MAX_MEMORY_SIZE = 15;
    private BlockPos autonomousTargetPos = BlockPos.ZERO;

    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(CreeperMother.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> TRIGGER_ANIM = SynchedEntityData.defineId(CreeperMother.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> DATA_BEHAVIOR_STATE = SynchedEntityData.defineId(CreeperMother.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MOOD_LEVEL = SynchedEntityData.defineId(CreeperMother.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> IS_SIDE_LYING = SynchedEntityData.defineId(CreeperMother.class, EntityDataSerializers.BOOLEAN);

    // ==========================================
    // 原版动画
    // ==========================================
    public final AnimationState jumpAnimationState = new AnimationState();
    public final AnimationState middleRightAnimationState = new AnimationState();
    public final AnimationState kissAnimationState = new AnimationState();
    public final AnimationState happyAnimationState = new AnimationState();
    public final AnimationState embraceAnimationState = new AnimationState();

    // ==========================================
    // 【新增】全部恋爱动画（补齐！）
    // ==========================================
    public final AnimationState shyAnimationState = new AnimationState();
    public final AnimationState heartAnimationState = new AnimationState();
    public final AnimationState leanAnimationState = new AnimationState();
    public final AnimationState holdHandAnimationState = new AnimationState();
    public final AnimationState coquetryAnimationState = new AnimationState();
    public final AnimationState spinAnimationState = new AnimationState();
    public final AnimationState headPatAnimationState = new AnimationState();
    public final AnimationState angryAnimationState = new AnimationState();
    public final AnimationState teaseAnimationState = new AnimationState();

    private int pendingResponseDelay = -1;
    private Player pendingPlayer;
    private String pendingMessage;
    private int proactiveTick = 0;
    private boolean isPerformingKiss = false;
    private Player kissTargetPlayer = null;
    private int ticksSinceLastSeenHusband = 0;
    private boolean isWaitingForHomecoming = false;

    private boolean isSleeping = false;
    private BlockPos bedPos = null;
    private boolean spawnedBed = false;
    private boolean isInvitedToBed = false;
    private static final int BED_SEARCH_RADIUS = 48;
    public static final EntityDataAccessor<String> CURRENT_TASK = SynchedEntityData.defineId(CreeperMother.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> BEHAVIOR_STATE = SynchedEntityData.defineId(CreeperMother.class, EntityDataSerializers.INT);
    
    // 保护老公相关变量
    private LivingEntity currentAttackTarget = null;
    private int protectCooldown = 0;
    private int husbandHurtTime = 0;
    private static final int PROTECT_RANGE = 16;
    private static final int MAX_PROTECT_DISTANCE = 24;

    public CreeperMother(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        if (this.getNavigation() instanceof net.minecraft.world.entity.ai.navigation.GroundPathNavigation nav) {
            nav.setCanOpenDoors(true);
        }
        
        // 初始化AI系统
        if (!pLevel.isClientSide) {
            com.thyids.ai_girl_friend.web.AIController.initializeAISystem();
        }
    }

    // ==========================================
    // 【关键】动画自动更新（必须加）
    // ==========================================
    @Override
    public void handleEntityEvent(byte id) {
        if (id == 4) { // 动画同步
            String anim = this.entityData.get(TRIGGER_ANIM).replaceAll("\\d", "");

            this.jumpAnimationState.stop();
            this.middleRightAnimationState.stop();
            this.kissAnimationState.stop();
            this.happyAnimationState.stop();
            this.embraceAnimationState.stop();

            this.shyAnimationState.stop();
            this.heartAnimationState.stop();
            this.leanAnimationState.stop();
            this.holdHandAnimationState.stop();
            this.coquetryAnimationState.stop();
            this.spinAnimationState.stop();
            this.headPatAnimationState.stop();
            this.angryAnimationState.stop();
            this.teaseAnimationState.stop();

            switch (anim) {
                case "jump" -> this.jumpAnimationState.start(this.tickCount);
                case "middle_right" -> this.middleRightAnimationState.start(this.tickCount);
                case "kiss" -> this.kissAnimationState.start(this.tickCount);
                case "happy" -> this.happyAnimationState.start(this.tickCount);
                case "embrace" -> this.embraceAnimationState.start(this.tickCount);

                case "shy" -> this.shyAnimationState.start(this.tickCount);
                case "heart" -> this.heartAnimationState.start(this.tickCount);
                case "lean" -> this.leanAnimationState.start(this.tickCount);
                case "hold_hand" -> this.holdHandAnimationState.start(this.tickCount);
                case "coquetry" -> this.coquetryAnimationState.start(this.tickCount);
                case "spin" -> this.spinAnimationState.start(this.tickCount);
                case "head_pat" -> this.headPatAnimationState.start(this.tickCount);
                case "angry" -> this.angryAnimationState.start(this.tickCount);
                case "tease" -> this.teaseAnimationState.start(this.tickCount);
            }
        }
        super.handleEntityEvent(id);
    }

    private void handleLongAbsenceReunion() {
        if (this.level().isClientSide || this.husbandUUID == null) return;

        Player husband = this.level().getPlayerByUUID(this.husbandUUID);

        if (husband != null) {
            double dist = this.distanceTo(husband);

            if (dist > 50.0D) {
                ticksSinceLastSeenHusband++;
                if (ticksSinceLastSeenHusband > 12000) {
                    isWaitingForHomecoming = true;
                }
            } else {
                if (isWaitingForHomecoming && dist < 15.0D) {
                    triggerReunion(husband);
                }
                ticksSinceLastSeenHusband = 0;
            }
        }
    }

    private void triggerReunion(Player husband) {
        isWaitingForHomecoming = false;
        this.getNavigation().moveTo(husband, 1.5D);
        this.triggerAnimation("embrace");
        this.adjustMood(20);
        this.addMessageToMemory("system", "（老公离开家很久终于回来了，你非常激动，飞奔过去迎接他）");
        this.scheduleResponse(husband, "（你看到出远门的老公终于回来了，激动地跑过去扑进他怀里，告诉他你等了多久，有多想他）", 30);
    }

    public SimpleContainer getInventory() { return this.inventory; }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean hasHurt = super.hurt(source, amount);
        if (!hasHurt || this.level().isClientSide || hurtCooldown > 0) return hasHurt;

        Entity attacker = source.getEntity();
        if (attacker instanceof Player p) {
            if (p.getUUID().equals(this.husbandUUID)) {
                // 如果在床上被打，先醒来
                if (this.isSleeping) {
                    wakeUp();
                    addMessageToMemory("system", "（在床上被老公打了，生气地醒了过来）");
                }
                
                adjustMood(-10);
                addMessageToMemory("system", "（老公动手打了你）");
                float finalChance = 0.15F + ((100.0F - getMoodLevel()) / 100.0F) * 0.65F;
                if (random.nextFloat() < finalChance) {
                    retaliateToPlayer(p);
                } else {
                    triggerAnimation("shy");
                    DeepSeekAPI.askAI(p, this, "（你被老公打了，非常委屈，问他为什么要这样对待你）");
                }
            } else {
                addMessageToMemory("system", "（陌生人打了你，你好害怕）");
                if (this.husbandUUID != null) {
                    Player husband = this.level().getPlayerByUUID(this.husbandUUID);
                    if (husband != null) {
                        triggerAnimation("shy");
                        DeepSeekAPI.askAI(husband, this, "（你被陌生人打了，非常委屈，向你老公寻求保护）");
                    }
                }
            }
        }
        hurtCooldown = 40;
        return hasHurt;
    }

    private void retaliateToPlayer(Player target) {
        if (hurtCooldown > 0) return;
        this.triggerAnimation("middle_right");
        this.getLookControl().setLookAt(target, 90F, 90F);
        target.hurt(this.damageSources().mobAttack(this), 2.0F);
        addMessageToMemory("assistant", "（生气地回击了老公）");
        DeepSeekAPI.askAI(target, this, "（你很生气，抬手制止/回击老公，带点小脾气说话）");
        hurtCooldown = 20;
    }

    public void appendChatHistory(String text) {
        this.chatHistory += text;
        if (this.chatHistory.length() > 1000) {
            this.chatHistory = this.chatHistory.substring(this.chatHistory.length() - 1000);
        }
        addMessageToMemory("user", text);
    }

    public String getChatHistory() { return this.chatHistory; }
    public JsonArray getChatMessages() { 
        // 使用高级AI管理系统来管理记忆
        return AdvancedAIManager.getCurrentContext(this);
    }
    public void updateMemory(JsonArray newMemory) { 
        AdvancedAIManager.updateMemory(this, newMemory);
    }

    public void onHusbandDefendedMe(Player husband) {
        if (this.level().isClientSide) return;
        this.adjustMood(15);
        this.triggerAnimation("happy");
        this.getLookControl().setLookAt(husband, 180F, 180F);
        this.addMessageToMemory("user", "（老公刚刚帮你教训了欺负我的人，你对老公好有安全感）");
        DeepSeekAPI.askAI(husband, this, "（老公刚才保护了你，打跑了欺负你的人，你现在满眼都是他，非常感动和崇拜，对他撒个撒娇并表达谢意）");
    }

    public void addMessageToMemory(String role, String content) {
        // 使用高级AI管理系统来添加消息
        AdvancedAIManager.addMessageToMemory(this, role, content);
    }

    public void scheduleResponse(Player player, String msg, int delayTicks) {
        this.pendingPlayer = player;
        this.pendingMessage = msg;
        this.pendingResponseDelay = delayTicks;
    }

    public java.util.UUID getHusbandUUID() { return husbandUUID; }

    public void setAutonomousTargetPos(BlockPos pos) { this.autonomousTargetPos = pos; }

    public void setBedPos(BlockPos pos) { this.bedPos = pos; }

    public void inviteToBed() {
        this.isInvitedToBed = true;
    }
    
    public void setInvitedToBed(boolean invited) {
        this.isInvitedToBed = invited;
    }

    // ==========================================
    // 床上互动动作
    // ==========================================
    
    public void performBedAction(Player target, String action) {
        if (!isSleeping || target == null) return;
        
        switch (action.toLowerCase()) {
            case "kiss", "亲我", "亲亲" -> performBedKiss(target);
            case "hug", "embrace", "搂我", "抱我" -> performBedHug(target);
            case "hit", "打我", "揍我" -> performBedHit(target);
            case "tease", "show", "勾引", "展示", "身材" -> performBedTease(target);
            case "lean", "靠我" -> performBedLean(target);
            case "coquetry", "撒娇" -> performBedCoquetry(target);
        }
    }

    private void performBedKiss(Player target) {
        triggerAnimation("kiss");
        addMessageToMemory("user", "（在床上亲了老公一下）");
        DeepSeekAPI.askAI(target, this, "（你侧过脸，轻轻亲了老公一下，然后红着脸钻进被子里）");
    }

    private void performBedHug(Player target) {
        triggerAnimation("embrace");
        addMessageToMemory("user", "（从背后抱住了老公）");
        DeepSeekAPI.askAI(target, this, "（你从背后轻轻抱住老公，把脸贴在他背上，小声说着甜蜜的话）");
    }

    private void performBedHit(Player target) {
        triggerAnimation("middle_right");
        target.hurt(this.damageSources().mobAttack(this), 1.0F);
        adjustMood(-5);
        addMessageToMemory("user", "（轻轻打了老公一下）");
        DeepSeekAPI.askAI(target, this, "（你假装生气地捶了老公一下，带着娇嗔说不要啦~）");
    }

    private void performBedTease(Player target) {
        triggerAnimation("tease");
        adjustMood(10);
        addMessageToMemory("user", "（在床上展示身材勾引老公）");
        DeepSeekAPI.askAI(target, this, "（你故意掀开一点被子，露出白皙的肩膀，眼神妩媚地看着老公）");
    }

    private void performBedLean(Player target) {
        triggerAnimation("lean");
        addMessageToMemory("user", "（把头靠在老公肩上）");
        DeepSeekAPI.askAI(target, this, "（你把头轻轻靠在老公肩上，享受着这份宁静）");
    }

    private void performBedCoquetry(Player target) {
        triggerAnimation("coquetry");
        addMessageToMemory("user", "（在床上撒娇）");
        DeepSeekAPI.askAI(target, this, "（你抱着老公的胳膊蹭了蹭，撒娇地说着话）");
    }

    private void executeWorkLogic() {
        String task = entityData.get(CURRENT_TASK);
        if (task.equals("SMELT")) triggerAnimation("happy");
        entityData.set(CURRENT_TASK, "IDLE");
    }

    private void performAutonomousTask() {
        String task = entityData.get(CURRENT_TASK);
        BlockPos target = this.autonomousTargetPos;
        if (target == null || target.equals(BlockPos.ZERO)) return;

        if (task.startsWith("DIG")) {
            this.level().destroyBlock(target, true, this);
        } else if (task.startsWith("USE")) {
            BlockState state = this.level().getBlockState(target);
            Player nearest = this.level().getNearestPlayer(this, 10);
            if (nearest != null) {
                state.use(this.level(), nearest, InteractionHand.MAIN_HAND,
                        new net.minecraft.world.phys.BlockHitResult(
                                Vec3.atCenterOf(target), Direction.UP, target, false));
            }
        }
        entityData.set(CURRENT_TASK, "IDLE");
    }

    /**
     * 睡觉时持续锁定位置和朝向
     * 核心原则：女友和老公不能同时占用同一个方块格
     * - 老公在床上 → 女友躺在 FOOT 格（靠向 HEAD 格一侧，看起来依偎在一起）
     * - 一人睡 → 躺在 HEAD 格中心
     */
    private void maintainSleepPosition() {
        if (bedPos == null) return;
        BlockState bedState = level().getBlockState(bedPos);
        if (!(bedState.getBlock() instanceof BedBlock)) {
            wakeUp();
            return;
        }

        Direction bedFacing = bedState.getValue(BedBlock.FACING);
        boolean isHead = bedState.getValue(BedBlock.PART) == BedPart.HEAD;
        BlockPos headPos = isHead ? bedPos : bedPos.relative(bedFacing.getOpposite());
        float yaw = bedFacing.toYRot();

        // 检查老公是否在同一张床的 HEAD 格
        Player husband = husbandUUID != null ? level().getPlayerByUUID(husbandUUID) : null;
        boolean husbandInBed = husband != null && husband.isSleeping()
                && husband.getSleepingPos().isPresent()
                && husband.getSleepingPos().get().equals(headPos);

        // 获取同床的其他女友
        List<CreeperMother> girlfriendsInBed = getGirlfriendsInBed(headPos);

        double fixX, fixY, fixZ;

        if (husbandInBed) {
            // 老公在床上，女友躺在老公身边（左侧或右侧）
            boolean sleepOnLeft = shouldSleepOnLeft(girlfriendsInBed, bedFacing);
            double offset = 0.85D;
            if (sleepOnLeft) {
                // 躺在老公左侧
                fixX = headPos.getX() + 0.5D + bedFacing.getStepZ() * offset;
                fixY = headPos.getY() + 0.5D;
                fixZ = headPos.getZ() + 0.5D - bedFacing.getStepX() * offset;
            } else {
                // 躺在老公右侧
                fixX = headPos.getX() + 0.5D - bedFacing.getStepZ() * offset;
                fixY = headPos.getY() + 0.5D;
                fixZ = headPos.getZ() + 0.5D + bedFacing.getStepX() * offset;
            }
            entityData.set(IS_SIDE_LYING, true);
        } else if (!girlfriendsInBed.isEmpty()) {
            // 已有女友在床上但老公不在，女友躺在另一侧
            boolean sleepOnLeft = shouldSleepOnLeft(girlfriendsInBed, bedFacing);
            double offset = 0.85D;
            if (sleepOnLeft) {
                fixX = headPos.getX() + 0.5D + bedFacing.getStepZ() * offset;
                fixY = headPos.getY() + 0.5D;
                fixZ = headPos.getZ() + 0.5D - bedFacing.getStepX() * offset;
            } else {
                fixX = headPos.getX() + 0.5D - bedFacing.getStepZ() * offset;
                fixY = headPos.getY() + 0.5D;
                fixZ = headPos.getZ() + 0.5D + bedFacing.getStepX() * offset;
            }
            entityData.set(IS_SIDE_LYING, true);
        } else {
            // 一人睡 → 躺在 HEAD 格中心，稍向枕头侧偏移
            fixX = headPos.getX() + 0.5D - bedFacing.getStepX() * 0.15D;
            fixY = headPos.getY() + 0.5D;
            fixZ = headPos.getZ() + 0.5D - bedFacing.getStepZ() * 0.15D;
            entityData.set(IS_SIDE_LYING, false);
        }

        this.absMoveTo(fixX, fixY, fixZ, yaw, 0);
        this.setDeltaMovement(0, 0, 0);
        this.noPhysics = true;
        this.setPose(Pose.SLEEPING);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) return;

        if (this.tickCount % 100 == 0 && husbandUUID != null && !isSleeping) {
            Player husband = level().getPlayerByUUID(husbandUUID);
            if (husband != null && this.distanceTo(husband) < 1.8D && this.getMoodLevel() > 50) {
                if (random.nextFloat() < 0.12F) {
                    this.triggerAnimation("head_pat");
                    PlayerAnimationHolder.get().playAnim("head_pat", husband.tickCount);
                    DeepSeekAPI.askAI(husband, this, "（温柔摸了摸老公的头）");
                }
            }
        }

        if (this.tickCount % 600 == 0) { // 每30秒观察一次
            Player husband = this.husbandUUID != null ? this.level().getPlayerByUUID(this.husbandUUID) : null;
            String observation = ContextAwareProcessor.getEnhancedGameContext(this, husband);
            addMessageToMemory("system", "（观察到：" + observation + "）");
        }

        // 睡觉状态维护
        if (this.isSleeping) {
            // 每10tick检查床是否还在
            if (this.tickCount % 10 == 0) {
                if (bedPos == null || !(level().getBlockState(bedPos).getBlock() instanceof BedBlock)) {
                    wakeUp();
                    return;
                }
            }
            // 持续锁定位置和姿态（统一调用新方法）
            maintainSleepPosition();
        } else {
            this.noPhysics = false;
        }

        if (pendingResponseDelay >= 0) {
            if (pendingResponseDelay == 0) {
                DeepSeekAPI.askAI(pendingPlayer, this, pendingMessage);
                pendingResponseDelay = -1;
            } else {
                pendingResponseDelay--;
            }
        }

        if (!this.isSleeping) {
            handleMoodEffects();
            handleLongAbsenceReunion();
            handleSweetJealousy();

            // ===== 新增强的真实行为 =====
            if (ModConfig.getBoolean("autonomous_chores")) {
                GirlfriendActionHandler.doFarmChores(this);
                GirlfriendActionHandler.doPlantFlowers(this);
            }
            GirlfriendActionHandler.doRandomAffection(this,
                    this.husbandUUID != null ? this.level().getPlayerByUUID(this.husbandUUID) : null);
        }

        if (hurtCooldown > 0) hurtCooldown--;

        if (isEmbracingNav && this.getNavigation().isInProgress() && this.getNavigation().getTargetPos() != null) {
            if (this.distanceToSqr(this.getNavigation().getTargetPos().getX(),
                    this.getNavigation().getTargetPos().getY(),
                    this.getNavigation().getTargetPos().getZ()) <= 2.5D) {
                this.getNavigation().stop();
                isEmbracingNav = false;
                Player nearPlayer = level().getNearestPlayer(this, 3D);
                if (nearPlayer != null && nearPlayer.getUUID().equals(husbandUUID)) {
                    triggerAnimation("embrace");
                    adjustMood(6);
                    this.getLookControl().setLookAt(nearPlayer, 180F, 180F);
                    addMessageToMemory("user", "（你走到老公身边，主动抱住了他）");
                    DeepSeekAPI.askAI(nearPlayer, this, "（快步走到老公怀里，紧紧抱住，软软地说话）");
                }
            }
        }

        refreshDefaultName();

        // === 任何时间都能上床睡觉 ===
        if (!this.isSleeping) {
            // 如果已经设置了床位置，频繁检查是否到达床边
            if (this.bedPos != null) {
                checkBedAndSleep();
            } else if (this.tickCount % 40 == 0) {
                // 没有设置床位置时，定期检查找床
                trySleep();
            }
        }

        // === 睡着时亲密互动（位置锁定在 maintainSleepPosition 中处理） ===
        if (this.isSleeping && bedPos != null) {
            // 老公靠近时触发床上亲密互动
            Player nearest = this.level().getNearestPlayer(this, 3.0D);
            if (nearest != null && nearest.getUUID().equals(this.husbandUUID)
                    && (nearest.isSleeping() || nearest.distanceTo(this) < 1.5D)) {
                if (!entityData.get(IS_SIDE_LYING)) {
                    entityData.set(IS_SIDE_LYING, true);
                }
                // 随机亲密小动作（不要太频繁）
                if (this.tickCount % 200 == 0 && random.nextFloat() < 0.3f) {
                    float r = random.nextFloat();
                    if (r < 0.25f) {
                        triggerAnimation("lean");
                        DeepSeekAPI.askAI(nearest, this, "（你侧过身子，把脸贴在老公胸口，软软地蹭了蹭）");
                    } else if (r < 0.5f) {
                        triggerAnimation("kiss");
                        DeepSeekAPI.askAI(nearest, this, "（你凑近老公，轻轻亲了他一下，然后害羞地躲开）");
                    } else if (r < 0.75f) {
                        triggerAnimation("coquetry");
                        DeepSeekAPI.askAI(nearest, this, "（你抱着老公的胳膊撒娇，小声说睡不着）");
                    } else {
                        triggerAnimation("embrace");
                        DeepSeekAPI.askAI(nearest, this, "（你钻进老公怀里，紧紧抱着他，觉得特别安心）");
                    }
                }
            } else {
                entityData.set(IS_SIDE_LYING, false);
            }
        } else if (!this.isSleeping) {
            // 没睡觉时的正常行为
            handleProactiveBehavior();
            handleKissBehavior();
        }

        if (this.tickCount % 2000 == 0) {
            adjustMood(getMoodLevel() > 50 ? -1 : 1);
        }

        if (isWatchingHusband()) {
            Player husband = this.level().getPlayerByUUID(husbandUUID);
            if (husband != null) {
                this.getLookControl().setLookAt(husband, 90F, 90F);
                this.getNavigation().stop();
                this.setDeltaMovement(0, 0, 0);
            }
        }
        updateDailyRoutine();
        
        // 保护老公和协同攻击逻辑
        if (!isSleeping && husbandUUID != null) {
            handleProtectHusband();
            handleAssistAttack();
        }
        
        // 保护冷却递减
        if (protectCooldown > 0) {
            protectCooldown--;
        }
        
        // 更新攻击目标
        if (currentAttackTarget != null) {
            updateAttackTarget();
        }
    }

    // 在 CreeperMother.java 中添加表情状态
    public enum FacialExpression {
        NEUTRAL,    // 中性
        HAPPY,      // 开心
        SHY,        // 害羞
        SAD,        // 难过
        ANGRY,      // 生气
        SURPRISED   // 惊讶
    }

    private FacialExpression currentExpression = FacialExpression.NEUTRAL;

    public void updateExpression() {
        int mood = getMoodLevel();

        if (mood >= 80) {
            currentExpression = FacialExpression.HAPPY;
        } else if (mood >= 60) {
            currentExpression = FacialExpression.NEUTRAL;
        } else if (mood >= 40) {
            currentExpression = FacialExpression.SHY;
        } else if (mood >= 20) {
            currentExpression = FacialExpression.SAD;
        } else {
            currentExpression = FacialExpression.ANGRY;
        }

        // 根据表情播放粒子效果
        if (level() instanceof ServerLevel serverLevel) {
            switch (currentExpression) {
                case HAPPY -> {
                    serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                            getX(), getY() + 1.5, getZ(), 3, 0.2, 0.2, 0.2, 0.1);
                }
                case SHY -> {
                    serverLevel.sendParticles(ParticleTypes.HEART,
                            getX(), getY() + 1.5, getZ(), 2, 0.1, 0.1, 0.1, 0.05);
                }
                case SAD -> {
                    serverLevel.sendParticles(ParticleTypes.DRIPPING_DRIPSTONE_WATER,
                            getX(), getY() + 1.5, getZ(), 3, 0.1, 0.3, 0.1, 0.1);
                }
                case ANGRY -> {
                    serverLevel.sendParticles(ParticleTypes.ANGRY_VILLAGER,
                            getX(), getY() + 1.5, getZ(), 3, 0.2, 0.2, 0.2, 0.1);
                }
            }
        }
    }

    // 在 CreeperMother.tick() 中添加
    private void updateDailyRoutine() {
        int hour = (int) (level().getDayTime() / 1000 + 6) % 24;

        // 早晨 (6-9点): 起床伸懒腰
        if (hour >= 6 && hour < 9 && isSleeping) {
            if (tickCount % 200 == 0 && random.nextFloat() < 0.3f) {
                wakeUp();
                triggerAnimation("happy");
                addMessageToMemory("system", "（早晨起床，伸了个懒腰）");
            }
        }

        // 上午 (9-12点): 主动找事情做
        if (hour >= 9 && hour < 12 && !isSleeping) {
            if (tickCount % 400 == 0) {
                doMorningActivity();
            }
        }

        // 中午 (12-14点): 休息聊天
        if (hour >= 12 && hour < 14 && !isSleeping && husbandUUID != null) {
            if (tickCount % 600 == 0 && random.nextFloat() < 0.2f) {
                Player husband = level().getPlayerByUUID(husbandUUID);
                if (husband != null) {
                    DeepSeekAPI.askAI(husband, this, "（依偎在你身边）老公，中午要不要休息一下呀~");
                }
            }
        }

        // 傍晚 (18-21点): 等待老公回家，提醒放床
        if (hour >= 18 && hour < 21 && husbandUUID != null && !isSleeping) {
            Player husband = level().getPlayerByUUID(husbandUUID);
            if (husband != null) {
                if (distanceTo(husband) > 5) {
                    getLookControl().setLookAt(husband, 90F, 90F);
                    if (tickCount % 400 == 0) {
                        addMessageToMemory("system", "（傍晚了，老公还没回来，有点想念）");
                    }
                }
                // 如果还没有床，提醒老公放床
                if (bedPos == null && tickCount % 600 == 0) {
                    DeepSeekAPI.askAI(husband, this, "（天色渐渐暗了）老公，晚上了，咱们该准备睡觉了~ 能放张床吗？");
                }
            }
        }

        // 夜晚 (21点后): 提醒睡觉
        if (hour >= 21 && !isSleeping) {
            if (tickCount % 800 == 0 && husbandUUID != null) {
                Player husband = level().getPlayerByUUID(husbandUUID);
                if (husband != null) {
                    DeepSeekAPI.askAI(husband, this, "（看看天色已晚）老公，该休息了吧~");
                }
            }
        }
    }

    private void doMorningActivity() {
        float r = random.nextFloat();
        if (r < 0.3f) {
            // 种花
            GirlfriendActionHandler.doPlantFlowers(this);
        } else if (r < 0.6f) {
            // 整理物品
            triggerAnimation("happy");
            addMessageToMemory("system", "（整理背包，把东西摆整齐）");
        } else {
            // 四处看看
            BlockPos randPos = randomPosition();
            getNavigation().moveTo(randPos.getX(), randPos.getY(), randPos.getZ(), 1.0D);
            addMessageToMemory("system", "（早晨散散步，看看周围的风景）");
        }
    }
    // ==========================================
// 随机安全位置（用于早晨散步、自主闲逛）
// ==========================================
    private BlockPos randomPosition() {
        // 以女友当前位置为中心
        BlockPos center = this.blockPosition();

        // 随机范围：X/Z ±10 ~ ±25 格，Y 上下浮动 3 格
        int rangeMin = 10;
        int rangeMax = 25;
        int x = center.getX() + this.random.nextInt(rangeMax - rangeMin) + rangeMin * (this.random.nextBoolean() ? 1 : -1);
        int z = center.getZ() + this.random.nextInt(rangeMax - rangeMin) + rangeMin * (this.random.nextBoolean() ? 1 : -1);
        int y = center.getY() + this.random.nextInt(7) - 3; // 上下浮动

        // 找到这个 XZ 位置最高的安全地面（防止掉虚空、摔死）
        BlockPos randomPos = new BlockPos(x, y, z);
        return findValidGroundPos(randomPos);
    }

    // ==========================================
    // 安全地面检测工具（确保站得稳、不摔落）
    // ==========================================
    private BlockPos findValidGroundPos(BlockPos targetPos) {
        Level level = this.level();

        // 向上寻找 10 格内的安全地面
        for (int dy = 10; dy > -10; dy--) {
            BlockPos checkPos = targetPos.above(dy);
            BlockPos downPos = checkPos.below();

            // 条件：脚下是方块，头顶和自身位置是空的，不是液体
            boolean groundSolid = !level.getBlockState(downPos).isAir()
                    && !level.getBlockState(downPos).liquid();
            boolean bodyEmpty = level.getBlockState(checkPos).isAir();
            boolean headEmpty = level.getBlockState(checkPos.above()).isAir();

            if (groundSolid && bodyEmpty && headEmpty) {
                return checkPos;
            }
        }

        // 找不到就返回原地（保底）
        return this.blockPosition();
    }

    private boolean isWatchingHusband() {
        if (husbandUUID == null || isSleeping || isPerformingKiss || isEmbracingNav) {
            return false;
        }
        Player husband = this.level().getPlayerByUUID(husbandUUID);
        if (husband == null) return false;
        return this.distanceTo(husband) < 12.0D && this.getSensing().hasLineOfSight(husband);
    }

    public void trySleep() {
        int mood = this.getMoodLevel();
        
        // 找老公：优先找绑定老公，其次找最近玩家
        Player referencePlayer = null;
        if (this.husbandUUID != null) {
            referencePlayer = this.level().getPlayerByUUID(this.husbandUUID);
        }
        if (referencePlayer == null) {
            referencePlayer = this.level().getNearestPlayer(this, 10.0);
        }
        if (referencePlayer == null) return;

        // 老公在床上时的逻辑
        boolean husbandInBed = referencePlayer.isSleeping();
        
        if (husbandInBed) {
            // 检查心情
            if (mood < 30) {
                // 非常生气，拒绝上床并怼玩家
                if (husbandUUID != null) {
                    Player husband = level().getPlayerByUUID(husbandUUID);
                    if (husband != null) {
                        husband.displayClientMessage(Component.literal("§c[" + getName().getString() + "] §f哼！谁要跟你一起睡！"), true);
                    }
                }
                return;
            } else if (mood < 60 && !isInvitedToBed) {
                // 心情一般，需要邀请才上床
                if (husbandUUID != null) {
                    Player husband = level().getPlayerByUUID(husbandUUID);
                    if (husband != null) {
                        husband.displayClientMessage(Component.literal("§d[" + getName().getString() + "] §f想让我陪你？求我呀~"), true);
                    }
                }
                return;
            }
            // 心情好或被邀请，上床
            bedPos = referencePlayer.getSleepingPos().orElse(null);
            if (bedPos != null) {
                BlockState bState = level().getBlockState(bedPos);
                if (bState.getBlock() instanceof BedBlock) {
                    Direction bDir = bState.getValue(BedBlock.FACING);
                    BlockPos footPos = bedPos.relative(bDir.getOpposite());
                    double targetX = footPos.getX() + 0.5D + bDir.getStepX() * 0.65D;
                    double targetZ = footPos.getZ() + 0.5D + bDir.getStepZ() * 0.65D;
                    double dist = this.distanceToSqr(targetX, this.getY(), targetZ);
                    if (dist > 3.0D) {
                        this.getNavigation().moveTo(targetX, footPos.getY(), targetZ, 1.3D);
                    } else {
                        performSleepOnBed(bedPos, referencePlayer);
                    }
                } else {
                    performSleepOnBed(bedPos, referencePlayer);
                }
                isInvitedToBed = false; // 重置邀请状态
                return;
            }
        }

        // 老公不在床上时的自主上床逻辑
        int hour = (int) (level().getDayTime() / 1000 + 6) % 24;
        boolean isNight = hour >= 21 || hour < 6;
        
        // 只有晚上或被邀请时才自主找床上床
        if (!isNight && !isInvitedToBed) {
            return;
        }
        
        // 自主上床也需要心情
        if (mood < 30) {
            return; // 太生气了，不想上床
        } else if (mood < 60 && !isInvitedToBed) {
            return; // 心情一般，没被邀请就不上床
        }

        // 找现有的床
        if (bedPos == null) {
            bedPos = findExistingBedInRange(referencePlayer, BED_SEARCH_RADIUS);
        }

        // 如果没有找到床，让AI请求玩家放床
        if (bedPos == null && husbandUUID != null) {
            Player husband = level().getPlayerByUUID(husbandUUID);
            if (husband != null && tickCount % 1000 == 0) {
                DeepSeekAPI.askAI(husband, this, "（揉了揉眼睛，有些困了）老公~ 我想睡觉了，能帮我放张床吗？");
            }
            return;
        }

        // 重置邀请状态
        isInvitedToBed = false;

        if (bedPos != null) {
            double dist = this.distanceToSqr(bedPos.getX() + 0.5, this.getY(), bedPos.getZ() + 0.5);
            if (dist > 3.0D) {
                // 距离太远，移动到床边
                this.getNavigation().moveTo(bedPos.getX() + 0.5, bedPos.getY(), bedPos.getZ() + 0.5, 1.3D);
            } else {
                // 距离足够近，上床睡觉
                BlockState state = level().getBlockState(bedPos);
                if (state.getBlock() instanceof BedBlock) {
                    performSleepOnBed(bedPos, referencePlayer);
                }
            }
        }
    }

    private void checkBedAndSleep() {
        if (bedPos == null) return;
        
        // 检查床是否还存在
        BlockState state = level().getBlockState(bedPos);
        if (!(state.getBlock() instanceof BedBlock)) {
            this.bedPos = null;
            return;
        }
        
        Player referencePlayer = null;
        if (this.husbandUUID != null) {
            referencePlayer = this.level().getPlayerByUUID(this.husbandUUID);
        }
        
        // 计算距离床边的距离
        double dist = this.distanceToSqr(bedPos.getX() + 0.5, this.getY(), bedPos.getZ() + 0.5);
        if (dist > 3.0D) {
            // 距离太远，移动到床边
            if (!this.getNavigation().isInProgress()) {
                this.getNavigation().moveTo(bedPos.getX() + 0.5, bedPos.getY(), bedPos.getZ() + 0.5, 1.3D);
            }
        } else {
            // 距离足够近，上床睡觉
            performSleepOnBed(bedPos, referencePlayer);
        }
    }

    private void performSleepOnBed(BlockPos bedPos, @Nullable Player husband) {
        BlockState state = level().getBlockState(bedPos);
        if (!(state.getBlock() instanceof BedBlock)) return;

        Direction bedFacing = state.getValue(BedBlock.FACING);
        boolean isHeadPart = state.getValue(BedBlock.PART) == BedPart.HEAD;
        BlockPos headPos = isHeadPart ? bedPos : bedPos.relative(bedFacing.getOpposite());
        BlockPos footPos = headPos.relative(bedFacing.getOpposite());

        // 检查床上已有多少女友
        List<CreeperMother> girlfriendsInBed = getGirlfriendsInBed(headPos);
        
        // 一张床最多2个女友
        if (girlfriendsInBed.size() >= 2) {
            // 床上已满，尝试找旁边的床
            BlockPos nearbyBed = findNearbyBed(headPos);
            if (nearbyBed != null) {
                this.bedPos = nearbyBed;
                checkBedAndSleep();
            }
            return;
        }

        // 检查老公是否在床上
        boolean husbandInBed = husband != null && husband.isSleeping()
                && husband.getSleepingPos().isPresent()
                && husband.getSleepingPos().get().equals(headPos);

        float yaw = bedFacing.toYRot();
        double fixX, fixY, fixZ;
        
        // 计算女友应该躺的位置（左侧或右侧）
        boolean sleepOnLeft = shouldSleepOnLeft(girlfriendsInBed, bedFacing);
        double offset = 0.85D; // 增大偏移量避免重叠
        
        if (husbandInBed) {
            // 老公在床上，女友躺在老公身边（左侧或右侧）
            if (sleepOnLeft) {
                // 躺在老公左侧
                fixX = headPos.getX() + 0.5D + bedFacing.getStepZ() * offset;
                fixY = headPos.getY() + 0.5D;
                fixZ = headPos.getZ() + 0.5D - bedFacing.getStepX() * offset;
            } else {
                // 躺在老公右侧
                fixX = headPos.getX() + 0.5D - bedFacing.getStepZ() * offset;
                fixY = headPos.getY() + 0.5D;
                fixZ = headPos.getZ() + 0.5D + bedFacing.getStepX() * offset;
            }
            entityData.set(IS_SIDE_LYING, true);
        } else if (!girlfriendsInBed.isEmpty()) {
            // 已有女友在床上但老公不在，新女友躺在另一侧
            if (sleepOnLeft) {
                fixX = headPos.getX() + 0.5D + bedFacing.getStepZ() * offset;
                fixY = headPos.getY() + 0.5D;
                fixZ = headPos.getZ() + 0.5D - bedFacing.getStepX() * offset;
            } else {
                fixX = headPos.getX() + 0.5D - bedFacing.getStepZ() * offset;
                fixY = headPos.getY() + 0.5D;
                fixZ = headPos.getZ() + 0.5D + bedFacing.getStepX() * offset;
            }
            entityData.set(IS_SIDE_LYING, true);
        } else {
            // 一人睡 → HEAD 格中心
            fixX = headPos.getX() + 0.5D - bedFacing.getStepX() * 0.15D;
            fixY = headPos.getY() + 0.5D;
            fixZ = headPos.getZ() + 0.5D - bedFacing.getStepZ() * 0.15D;
            entityData.set(IS_SIDE_LYING, false);
        }

        this.setYRot(yaw);
        this.yBodyRot = yaw;
        this.yHeadRot = yaw;
        this.absMoveTo(fixX, fixY, fixZ, yaw, 0);
        this.setDeltaMovement(0, 0, 0);
        this.setPose(Pose.SLEEPING);
        this.isSleeping = true;
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    private List<CreeperMother> getGirlfriendsInBed(BlockPos headPos) {
        return level().getEntitiesOfClass(CreeperMother.class, 
                new AABB(headPos).inflate(2.0D),
                girl -> girl.isSleeping() && girl.bedPos != null && 
                        girl.bedPos.closerThan(headPos, 2.0D));
    }

    private boolean shouldSleepOnLeft(List<CreeperMother> girlfriendsInBed, Direction bedFacing) {
        if (girlfriendsInBed.isEmpty()) {
            return random.nextBoolean();
        }
        
        // 检查已有女友的位置
        for (CreeperMother girl : girlfriendsInBed) {
            if (girl == this) continue;
            double girlX = girl.getX();
            double girlZ = girl.getZ();
            double headX = girl.bedPos.getX() + 0.5D;
            double headZ = girl.bedPos.getZ() + 0.5D;
            
            // 判断女友在左侧还是右侧
            double cross = (girlX - headX) * bedFacing.getStepZ() - (girlZ - headZ) * bedFacing.getStepX();
            if (cross > 0) {
                // 已有女友在左侧，新女友睡右侧
                return false;
            }
        }
        // 没有女友在左侧，新女友睡左侧
        return true;
    }

    private BlockPos findNearbyBed(BlockPos headPos) {
        // 在周围搜索空床
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                if (dx == 0 && dz == 0) continue;
                BlockPos checkPos = headPos.offset(dx, 0, dz);
                BlockState state = level().getBlockState(checkPos);
                if (state.getBlock() instanceof BedBlock && 
                        state.getValue(BedBlock.PART) == BedPart.HEAD) {
                    // 检查这张床是否已满
                    List<CreeperMother> girls = getGirlfriendsInBed(checkPos);
                    if (girls.size() < 2) {
                        return checkPos;
                    }
                }
            }
        }
        return null;
    }

    private void wakeUp() {
        this.setPose(Pose.STANDING);
        this.isSleeping = false;
        this.setNoGravity(false);
        this.noPhysics = false;
        entityData.set(IS_SIDE_LYING, false);

        if (this.bedPos != null) {
            if (spawnedBed) {
                BlockState headState = level().getBlockState(bedPos);
                if (headState.getBlock() instanceof BedBlock) {
                    Direction facing = headState.getValue(BedBlock.FACING);
                    BlockPos footPos = bedPos.relative(facing.getOpposite());
                    level().destroyBlock(footPos, false);
                    level().destroyBlock(bedPos, false);
                }
                spawnedBed = false;
            }
        }

        this.bedPos = null;
        this.triggerAnimation("happy");
    }

    private BlockPos findExistingBedInRange(Player player, int radius) {
        return BlockPos.betweenClosedStream(
                        player.blockPosition().offset(-radius, -8, -radius),
                        player.blockPosition().offset(radius, 8, radius)
                )
                .filter(pos -> {
                    BlockState s = level().getBlockState(pos);
                    return s.getBlock() instanceof BedBlock
                            && s.getValue(BedBlock.PART) == BedPart.HEAD;
                })
                .map(BlockPos::immutable)
                .min(java.util.Comparator.comparingDouble(p -> p.distSqr(this.blockPosition())))
                .orElse(null);
    }

    private BlockPos placeBedNextToPlayer(Player player) {
        Direction dir = player.getDirection().getOpposite();
        BlockPos head = player.blockPosition().below();
        BlockPos foot = head.relative(dir);
        if ((level().isEmptyBlock(head) || level().getBlockState(head).canBeReplaced()) &&
                (level().isEmptyBlock(foot) || level().getBlockState(foot).canBeReplaced())) {
            level().setBlock(head, Blocks.RED_BED.defaultBlockState()
                    .setValue(BedBlock.FACING, dir.getOpposite())
                    .setValue(BedBlock.PART, BedPart.HEAD), 3);
            level().setBlock(foot, Blocks.RED_BED.defaultBlockState()
                    .setValue(BedBlock.FACING, dir.getOpposite())
                    .setValue(BedBlock.PART, BedPart.FOOT), 3);
            return head;
        }
        return null;
    }

    private void handleKissBehavior() {
        if (!isPerformingKiss || kissTargetPlayer == null) return;
        if (this.distanceTo(kissTargetPlayer) > 1.5D) {
            this.getNavigation().moveTo(kissTargetPlayer, 1.3D);
        } else {
            this.getNavigation().stop();
            this.getLookControl().setLookAt(kissTargetPlayer, 180F, 180F);
            triggerAnimation("kiss");
            DeepSeekAPI.askAI(kissTargetPlayer, this, "（你刚刚亲完老公，害羞地说一句话）");
            resetKiss();
        }
    }

    private void resetKiss() {
        this.isPerformingKiss = false;
        this.kissTargetPlayer = null;
        this.getNavigation().stop();
    }

    private void handleProactiveBehavior() {
        if (++proactiveTick > 1200 && !isSleeping && !isPerformingKiss) {
            proactiveTick = 0;
            if (husbandUUID == null) return;
            Player p = this.level().getPlayerByUUID(husbandUUID);
            if (p != null && this.getSensing().hasLineOfSight(p) && p.distanceTo(this) < 10D) {
                float r = random.nextFloat();
                if (r < 0.2F) {
                    startKiss(p);
                } else if (r < 0.25F) {
                    triggerAnimation("heart");
                } else if (r < 0.3F) {
                    triggerAnimation("shy");
                } else if (r < 0.35F) {
                    triggerAnimation("lean");
                } else if (r < 0.4F) {
                    DeepSeekAPI.askAI(p, this, "（你正注视着老公，主动温柔地打个招呼）");
                }
            }
        }
        if (this.tickCount % 2400 == 0 && this.getMoodLevel() > 70) {
            Player husband = this.level().getPlayerByUUID(this.husbandUUID);
            if (husband != null && this.distanceTo(husband) < 3) {
                this.spawnAtLocation(Blocks.POPPY);
                this.triggerAnimation("happy");
                DeepSeekAPI.askAI(husband, this, "（你觉得给老公的爱还不够，主动送了他一朵花，并告诉他你会永远陪着他）");
            }
        }
        if (++proactiveTick > 1200) {
            proactiveTick = 0;
            // 根据时间问候
            Player p = this.level().getPlayerByUUID(husbandUUID);
            String greeting = getTimeBasedGreeting();
            DeepSeekAPI.askAI(p, this, "（" + greeting + "）");
        }
    }
    private String getTimeBasedGreeting() {
        int hour = java.time.LocalTime.now().getHour();
        if (hour >= 5 && hour < 9) return "早上好呀，老公~";
        else if (hour >= 11 && hour < 14) return "中午好，老公吃饭了吗？";
        else if (hour >= 18 && hour < 21) return "晚上好，今天辛苦了";
        else if (hour >= 21) return "夜深了，要不要休息呀";
        else return "老公~";
    }


    public void startKiss(Player p) {
        this.isPerformingKiss = true;
        this.kissTargetPlayer = p;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_BEHAVIOR_STATE, 0);
        entityData.define(MOOD_LEVEL, 50);
        entityData.define(DATA_VARIANT_ID, 0);
        entityData.define(TRIGGER_ANIM, "");
        entityData.define(CURRENT_TASK, "IDLE");
        entityData.define(IS_SIDE_LYING, false);
    }

    public int getVariant() { return entityData.get(DATA_VARIANT_ID); }
    public void setVariant(int v) { entityData.set(DATA_VARIANT_ID, v); }
    public int getMoodLevel() { return entityData.get(MOOD_LEVEL); }

    public void adjustMood(int i) {
        entityData.set(MOOD_LEVEL, Mth.clamp(getMoodLevel() + i, 0, 100));
    }

    public int getBehaviorState() { return entityData.get(DATA_BEHAVIOR_STATE); }
    
    public void setBehaviorState(int state) {
        entityData.set(DATA_BEHAVIOR_STATE, state);
    }

    public void triggerAnimation(String n) {
        if (!level().isClientSide) {
            entityData.set(TRIGGER_ANIM, n.toLowerCase() + tickCount);
            this.level().broadcastEntityEvent(this, (byte) 4);
        }
    }

    private void refreshDefaultName() {
        if (getCustomName() == null) {
            String defaultName = DeepSeekAPI.getName(this);
            setCustomName(Component.literal(defaultName));
        }
    }

    public void activeEmbrace(Player player) {
        if (level().isClientSide || isSleeping || isPerformingKiss) return;
        if (this.distanceToSqr(player) <= 4.0D) {
            triggerAnimation("embrace");
            adjustMood(6);
            this.getLookControl().setLookAt(player, 180F, 180F);
            addMessageToMemory("user", "（老公呼唤我，你主动抱住了老公）");
            DeepSeekAPI.askAI(player, this, "（害羞温柔地说话）");
        } else {
            this.getNavigation().moveTo(player, 1.3D);
            isEmbracingNav = true;
        }
    }

    private void handleMoodEffects() {
        if (this.level().isClientSide || this.isSleeping) return;

        if (this.getMoodLevel() >= 80 && this.getNavigation().isInProgress()) {
            if (this.onGround() && this.tickCount % 20 == 0) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, 0.3, 0));
                this.triggerAnimation("jump");
            }
        }

        if (this.getMoodLevel() >= 90 && this.tickCount % 12000 == 0) {
            Player husband = this.level().getPlayerByUUID(this.husbandUUID);
            if (husband != null && this.distanceTo(husband) < 5) {
                this.spawnAtLocation(Blocks.POPPY);
                this.triggerAnimation("happy");
                DeepSeekAPI.askAI(husband, this, "（你心情非常好，主动送给老公一朵花，并开心地转了个圈）");
            }
        }
    }

    public void tryKiss(Player p) {
        if (this.getMoodLevel() < 40) {
            this.triggerAnimation("shy");
            this.addMessageToMemory("system", "（老公想亲你，但你现在心情很差，推开了他）");
            DeepSeekAPI.askAI(p, this, "（老公想亲你，你现在心里乱糟糟的，推开了他并委屈地说现在不想这样）");
        } else {
            startKiss(p);
        }
    }

    public void bindHusband(UUID playerUUID) {
        this.husbandUUID = playerUUID;
        EmotionalMemoryModule.getOrCreateEmotionMemory(this).firstMeetDate =
                java.time.LocalDate.now().toString();
        EmotionalMemoryModule.getOrCreateEmotionMemory(this).addSpecialEvent("成为情侣");
    }

    private void setChanged() {
        this.hasImpulse = true;
    }

    @Override
    public InteractionResult mobInteract(Player p, InteractionHand h) {
        if (h != InteractionHand.MAIN_HAND) return InteractionResult.PASS;

        if (getHusbandUUID() == null) {
            this.husbandUUID = p.getUUID();
            p.displayClientMessage(Component.literal("§d[系统] §f你已成为 " + this.getDisplayName().getString() + " 的老公！"), true);
        }

        boolean isHusband = p.getUUID().equals(this.husbandUUID);
        if (!isHusband) {
            if (!level().isClientSide) {
                this.triggerAnimation("shy");
                p.sendSystemMessage(Component.literal("§c[" + this.getDisplayName().getString() + "] §f我只喜欢我老公，你别找我！"));
                addMessageToMemory("system", "（陌生人试图勾搭你，你拒绝了）");
            }
            return InteractionResult.SUCCESS;
        }

        // ========== 手里有物品 → 送礼物 ==========
        ItemStack heldItem = p.getItemInHand(h);
        if (!heldItem.isEmpty()) {
            if (!level().isClientSide) {
                GirlfriendActionHandler.onGiveItem(p, this, heldItem);
            }
            return InteractionResult.SUCCESS;
        }

        // ========== 潜行右键：摸头 ==========
        if (p.isShiftKeyDown()) {
            // 玩家摸女友的头
            this.triggerAnimation("head_pat");
            this.adjustMood(8);
            DeepSeekAPI.askAI(p, this, "（老公温柔摸了摸你的头，你有点害羞心动）");
            return InteractionResult.SUCCESS;
        }

        // ========== 普通右键（空手）：拥抱 / 亲嘴 ==========
        if (!isSleeping) {
            // 女友同时触发摸玩家头动画+动作
            PlayerAnimationHolder.get().playAnim("head_pat", p.tickCount);
            this.triggerAnimation("embrace");
            activeEmbrace(p);
            // 触发亲嘴概率
            if (getMoodLevel() > 60) {
                startKiss(p);
            }
        }

        return super.mobInteract(p, h);
    }

    public void resetPoseDown() {
        this.setNoGravity(false);
        this.getNavigation().stop();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag n) {
        super.addAdditionalSaveData(n);
        n.putInt("Variant", getVariant());
        n.put("Inventory", this.inventory.createTag());
        n.putString("ChatHistory", this.chatHistory);
        ListTag memoryList = new ListTag();
        for (int i = 0; i < chatMessages.size(); i++) {
            memoryList.add(StringTag.valueOf(chatMessages.get(i).toString()));
        }
        n.put("ChatMemory", memoryList);
        if (husbandUUID != null) n.putUUID("HusbandUUID", husbandUUID);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag n) {
        super.readAdditionalSaveData(n);
        setVariant(n.getInt("Variant"));
        this.inventory.fromTag(n.getList("Inventory", 10));
        if (n.contains("ChatHistory")) {
            this.chatHistory = n.getString("ChatHistory");
        }
        if (n.contains("ChatMemory", 9)) {
            ListTag memoryList = n.getList("ChatMemory", 8);
            this.chatMessages = new JsonArray();
            for (int i = 0; i < memoryList.size(); i++) {
                try {
                    this.chatMessages.add(JsonParser.parseString(memoryList.getString(i)));
                } catch (Exception ignored) {}
            }
        }
        if (n.hasUUID("HusbandUUID")) this.husbandUUID = n.getUUID("HusbandUUID");
    }

    public void setHusband(ServerPlayer player) {
        if (player == null) return;
        this.husbandUUID = player.getUUID();
        this.setPersistenceRequired();
        this.setChanged();
        this.addMessageToMemory("system", "（你已被唐浩宇做了出来）");
    }

    private void handleSweetJealousy() {

    }
    
    // ==========================================
    // 保护老公功能
    // 当检测到老公受到伤害时，自动攻击攻击者
    // ==========================================
    private void handleProtectHusband() {
        Player husband = level().getPlayerByUUID(husbandUUID);
        if (husband == null) return;
        
        // 检查老公是否正在受到伤害
        if (husband.hurtTime > 0) {
            husbandHurtTime = 60; // 重置保护时间
        }
        
        // 如果老公刚受到伤害，寻找攻击者
        if (husbandHurtTime > 0) {
            husbandHurtTime--;
            
            // 检查是否有攻击者
            DamageSource lastDamageSource = husband.getLastDamageSource();
            if (lastDamageSource != null) {
                Entity attacker = lastDamageSource.getEntity();
                if (attacker instanceof LivingEntity livingAttacker && attacker != this) {
                    // 验证攻击者：不能是其他女友或和平生物
                    if (isValidAttackTarget(livingAttacker)) {
                        setAttackTarget(livingAttacker);
                        return;
                    }
                }
            }
            
            // 如果没有直接攻击者，搜索附近的敌对实体
            if (currentAttackTarget == null || !currentAttackTarget.isAlive()) {
                searchForHostileEntities(husband);
            }
        }
    }
    
    // ==========================================
    // 协同攻击功能
    // 当老公攻击玩家或怪物时，女友一起攻击
    // ==========================================
    private void handleAssistAttack() {
        Player husband = level().getPlayerByUUID(husbandUUID);
        if (husband == null) return;
        
        // 检查老公是否正在攻击（使用 attackAnim 来检测）
        if (husband.attackAnim > 0) {
            // 寻找老公正在攻击的目标
            Entity target = husband.getLastHurtMob();
            if (target instanceof LivingEntity livingTarget && target != this) {
                // 验证目标：不能是其他女友
                if (isValidAttackTarget(livingTarget)) {
                    setAttackTarget(livingTarget);
                    return;
                }
            }
            
            // 搜索老公附近的敌对实体
            searchForHostileEntities(husband);
        }
    }
    
    // ==========================================
    // 搜索附近的敌对实体
    // ==========================================
    private void searchForHostileEntities(Player husband) {
        double maxDistance = PROTECT_RANGE;
        List<LivingEntity> nearbyEntities = level().getEntitiesOfClass(
            LivingEntity.class,
            new AABB(
                husband.getX() - maxDistance,
                husband.getY() - 2,
                husband.getZ() - maxDistance,
                husband.getX() + maxDistance,
                husband.getY() + 3,
                husband.getZ() + maxDistance
            )
        );
        
        // 找到最近的敌对实体
        LivingEntity nearestHostile = null;
        double nearestDistance = Double.MAX_VALUE;
        
        for (LivingEntity entity : nearbyEntities) {
            if (entity != this && isValidAttackTarget(entity)) {
                double distance = entity.distanceTo(husband);
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestHostile = entity;
                }
            }
        }
        
        if (nearestHostile != null) {
            setAttackTarget(nearestHostile);
        }
    }
    
    // ==========================================
    // 验证攻击目标是否有效
    // ==========================================
    private boolean isValidAttackTarget(LivingEntity entity) {
        // 不能是自己
        if (entity == this) return false;
        
        // 不能是和平生物（被动生物）
        if (entity instanceof Animal) {
            return false;
        }
        
        // 不能是玩家（除非是攻击老公的玩家）
        if (entity instanceof Player player) {
            // 如果是老公，不攻击
            if (player.getUUID().equals(husbandUUID)) {
                return false;
            }
            // 检查是否是其他女友
            if (isOtherGirlfriend(player)) {
                return false;
            }
            // 允许攻击其他玩家（作为保护老公的一部分）
            return true;
        }
        
        // 敌对怪物
        if (entity instanceof Enemy) {
            return true;
        }
        
        return false;
    }
    
    // ==========================================
    // 检查玩家是否是其他女友
    // ==========================================
    private boolean isOtherGirlfriend(Player player) {
        // 可以在这里添加检查逻辑，比如检查玩家是否有女友组件
        return false;
    }
    
    // ==========================================
    // 设置攻击目标
    // ==========================================
    private void setAttackTarget(LivingEntity target) {
        if (protectCooldown > 0 && currentAttackTarget != null) {
            return; // 有冷却时间
        }
        
        currentAttackTarget = target;
        protectCooldown = 20; // 20tick冷却
        
        // 触发愤怒动画
        triggerAnimation("angry");
        adjustMood(-5); // 战斗会降低心情
        
        // 向老公报告
        Player husband = level().getPlayerByUUID(husbandUUID);
        if (husband != null) {
            addMessageToMemory("system", "（老公有危险！你准备战斗！）");
            DeepSeekAPI.askAI(husband, this, "（看到老公有危险，你眼神变得坚定，握紧拳头准备保护他）");
        }
    }
    
    // ==========================================
    // 更新攻击目标状态
    // ==========================================
    private void updateAttackTarget() {
        if (currentAttackTarget == null || !currentAttackTarget.isAlive()) {
            currentAttackTarget = null;
            return;
        }
        
        Player husband = level().getPlayerByUUID(husbandUUID);
        
        // 检查目标是否超出保护范围
        if (husband != null) {
            double distanceToHusband = currentAttackTarget.distanceTo(husband);
            if (distanceToHusband > MAX_PROTECT_DISTANCE) {
                currentAttackTarget = null;
                return;
            }
        }
        
        // 移动到目标附近
        double distance = this.distanceTo(currentAttackTarget);
        if (distance > 1.5D) {
            // 先面向目标
            this.getLookControl().setLookAt(currentAttackTarget, 90F, 90F);
            
            // 如果距离太远，向目标移动
            if (distance > 20D) {
                // 先跑到老公身边，再去战斗
                if (husband != null && this.distanceTo(husband) > 5D) {
                    this.getNavigation().moveTo(husband, 1.5D);
                } else {
                    this.getNavigation().moveTo(currentAttackTarget, 1.3D);
                }
            } else {
                this.getNavigation().moveTo(currentAttackTarget, 1.3D);
            }
        } else {
            // 近距离攻击
            this.getNavigation().stop();
            this.getLookControl().setLookAt(currentAttackTarget, 90F, 90F);
            
            // 触发攻击动画
            triggerAnimation("middle_right");
            
            // 造成伤害
            currentAttackTarget.hurt(this.damageSources().mobAttack(this), 3.0F);
            
            // 添加记忆
            addMessageToMemory("system", "（你攻击了敌人）");
            
            // 攻击冷却
            protectCooldown = 30;
        }
    }
    
    // ==========================================
    // 自主移动行为
    // 让女友不再一动不动，而是会主动活动
    // ==========================================
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, true) {
            @Override
            public boolean canUse() {
                return getBehaviorState() == 2 && !isSleeping && !isWatchingHusband() && super.canUse();
            }
        });
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F) {
            @Override
            public boolean canUse() { return !isSleeping && super.canUse(); }
        });
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D) {
            @Override
            public boolean canUse() {
                // 修改：允许女友在非睡觉状态下自由走动
                return !isSleeping && !isPerformingKiss && !isWatchingHusband() 
                        && currentAttackTarget == null && super.canUse();
            }
        });
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this) {
            @Override
            public boolean canUse() { return !isSleeping && !isWatchingHusband() && super.canUse(); }
        });
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public boolean canUse() {
                return getBehaviorState() == 2 && !isSleeping && !isWatchingHusband() && super.canUse();
            }
        });
        this.goalSelector.addGoal(1, new MoveToBlockGoal(this, 1.2D, 12) {
            @Override
            protected boolean isValidTarget(LevelReader level, BlockPos pos) {
                String task = entityData.get(CURRENT_TASK);
                if (task.equals("SMELT")) return level.getBlockState(pos).is(Blocks.FURNACE);
                if (task.equals("OPEN_CHEST")) return level.getBlockState(pos).is(Blocks.CHEST);
                return false;
            }
            @Override
            public void tick() {
                super.tick();
                if (this.isReachedTarget()) executeWorkLogic();
            }
        });
        this.goalSelector.addGoal(1, new MoveToBlockGoal(this, 1.2D, 16) {
            @Override
            protected boolean isValidTarget(LevelReader level, BlockPos pos) {
                return pos.equals(autonomousTargetPos);
            }
            @Override
            public void tick() {
                super.tick();
                if (this.isReachedTarget()) performAutonomousTask();
            }
        });
        this.goalSelector.addGoal(1, new OpenDoorGoal(this, true));
        
        // 添加跟随老公的行为
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0D, 5.0F, 2.0F) {
            @Override
            public boolean canUse() {
                if (isSleeping || isPerformingKiss || currentAttackTarget != null) {
                    return false;
                }
                Player husband = level().getPlayerByUUID(husbandUUID);
                return husband != null && !isWatchingHusband();
            }
        });
    }
    
    // ==========================================
    // 跟随老公的目标（内部类）
    // ==========================================
    private class FollowOwnerGoal extends Goal {
        private final CreeperMother girlfriend;
        private final double speedModifier;
        private final float startDistance;
        private final float stopDistance;
        private Player owner;
        private int timeToRecalcPath;
        
        public FollowOwnerGoal(CreeperMother girlfriend, double speedModifier, float startDistance, float stopDistance) {
            this.girlfriend = girlfriend;
            this.speedModifier = speedModifier;
            this.startDistance = startDistance;
            this.stopDistance = stopDistance;
        }
        
        @Override
        public boolean canUse() {
            if (girlfriend.isSleeping || girlfriend.isPerformingKiss || girlfriend.currentAttackTarget != null) {
                return false;
            }
            Player husband = girlfriend.level().getPlayerByUUID(girlfriend.husbandUUID);
            if (husband == null) {
                return false;
            }
            if (husband.isSpectator()) {
                return false;
            }
            // 只有当距离超过startDistance时才跟随
            return girlfriend.distanceTo(husband) > startDistance;
        }
        
        @Override
        public boolean canContinueToUse() {
            return canUse() && girlfriend.distanceTo(owner) > stopDistance;
        }
        
        @Override
        public void start() {
            owner = girlfriend.level().getPlayerByUUID(girlfriend.husbandUUID);
            timeToRecalcPath = 0;
        }
        
        @Override
        public void tick() {
            if (owner == null) {
                return;
            }
            
            girlfriend.getLookControl().setLookAt(owner, 90.0F, 90.0F);
            
            if (--timeToRecalcPath <= 0) {
                timeToRecalcPath = 10;
                girlfriend.getNavigation().moveTo(owner, speedModifier);
            }
        }
        
        @Override
        public void stop() {
            girlfriend.getNavigation().stop();
        }
    }
}