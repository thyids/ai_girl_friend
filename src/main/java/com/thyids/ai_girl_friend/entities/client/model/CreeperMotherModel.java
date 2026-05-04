package com.thyids.ai_girl_friend.entities.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thyids.ai_girl_friend.entities.client.animation.CreeperMotherAnimation;
import com.thyids.ai_girl_friend.world.entity.girl_friend.CreeperMother;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class CreeperMotherModel<T extends CreeperMother> extends HierarchicalModel<T> {
	// 图层定义
	public static final ModelLayerLocation CREEPER_MOTHER_LAYER  = new ModelLayerLocation(new ResourceLocation("modid", "creepermother"), "main");
	private final ModelPart root;
	private final ModelPart main;
	private final ModelPart Left_Leg;
	private final ModelPart Right_Leg;
	private final ModelPart Waist;
	private final ModelPart Head;
	private final ModelPart Body;
	private final ModelPart Right_Arm;
	private final ModelPart Left_Arm;

	public CreeperMotherModel(ModelPart root) {
		this.root = root;
		this.main = root.getChild("main");
		this.Left_Leg = this.main.getChild("Left_Leg");
		this.Right_Leg = this.main.getChild("Right_Leg");
		this.Waist = this.main.getChild("Waist");
		this.Head = this.Waist.getChild("Head");
		this.Body = this.Waist.getChild("Body");
		this.Right_Arm = this.Waist.getChild("Right_Arm");
		this.Left_Arm = this.Waist.getChild("Left_Arm");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Left_Leg = main.addOrReplaceChild("Left_Leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(1.9F, -12.0F, 0.0F));

		PartDefinition Right_Leg = main.addOrReplaceChild("Right_Leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.9F, -12.0F, 0.0F));

		PartDefinition Waist = main.addOrReplaceChild("Waist", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition Head = Waist.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition Body = Waist.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition Right_Arm = Waist.addOrReplaceChild("Right_Arm", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, -10.0F, 0.0F));

		PartDefinition Left_Arm = Waist.addOrReplaceChild("Left_Arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, -10.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.Head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.Head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(part -> part.resetPose());
		this.applyHeadRotation(netHeadYaw, headPitch);

		// 走路动画：没有播放拥抱/亲吻/开心时才播放
		if (!entity.embraceAnimationState.isStarted()
				&& !entity.kissAnimationState.isStarted()
				&& !entity.happyAnimationState.isStarted()) {
			this.animateWalk(CreeperMotherAnimation.walk, limbSwing, limbSwingAmount, 2.0F, 2.5F);
		}

		// ==============================================
		// 原版动画
		// ==============================================
		this.animate(entity.jumpAnimationState, CreeperMotherAnimation.jump, ageInTicks);
		this.animate(entity.middleRightAnimationState, CreeperMotherAnimation.middle_right, ageInTicks);
		this.animate(entity.kissAnimationState, CreeperMotherAnimation.kiss, ageInTicks);
		this.animate(entity.happyAnimationState, CreeperMotherAnimation.happy, ageInTicks);
		this.animate(entity.embraceAnimationState, CreeperMotherAnimation.embrace, ageInTicks);

		// ==============================================
		// 新增：恋爱甜蜜动画 全部绑定
		// ==============================================
		this.animate(entity.shyAnimationState, CreeperMotherAnimation.shy, ageInTicks);          // 害羞
		this.animate(entity.heartAnimationState, CreeperMotherAnimation.heart, ageInTicks);      // 比心
		this.animate(entity.leanAnimationState, CreeperMotherAnimation.lean, ageInTicks);          // 依偎
		this.animate(entity.holdHandAnimationState, CreeperMotherAnimation.hold_hand, ageInTicks); // 牵手
		this.animate(entity.coquetryAnimationState, CreeperMotherAnimation.coquetry, ageInTicks);  // 撒娇
		this.animate(entity.spinAnimationState, CreeperMotherAnimation.spin, ageInTicks);          // 转圈
		this.animate(entity.headPatAnimationState, CreeperMotherAnimation.head_pat, ageInTicks);   // 摸头杀回应
		this.animate(entity.angryAnimationState, CreeperMotherAnimation.angry, ageInTicks);

		// 睡觉姿势
		if (entity.isSleeping()) {
			if (entity.getEntityData().get(CreeperMother.IS_SIDE_LYING)) {
				this.root().zRot = (float) Math.toRadians(90);
				this.root().y = 18.0F;
			} else {
				this.root().zRot = 0;
				this.root().y = 24.0F;
			}
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.root().render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}