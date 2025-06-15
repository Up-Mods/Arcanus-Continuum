package dev.cammiescorner.arcanus.mixin.client;

import dev.cammiescorner.arcanus.common.items.StaffItem;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.util.StaffType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// TODO all my animations are broken lmao :sobbing:
@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> extends AgeableListModel<T> implements ArmedModel, HeadedModel {
	@Shadow @Final public ModelPart rightArm;
	@Shadow @Final public ModelPart leftArm;
	@Shadow @Final public ModelPart head;

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At(
		value = "INVOKE_ASSIGN",
		target = "Lnet/minecraft/util/Mth;cos(F)F",
		ordinal = 1
	))
	private void modifyArmSwing(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
		if(!livingEntity.isSprinting() && !livingEntity.isSwimming() && !livingEntity.isFallFlying()) {
			Minecraft client = Minecraft.getInstance();
			ItemStack rightStack = client.options.mainHand().get() == HumanoidArm.RIGHT ? livingEntity.getMainHandItem() : livingEntity.getOffhandItem();
			ItemStack leftStack = client.options.mainHand().get() == HumanoidArm.RIGHT ? livingEntity.getOffhandItem() : livingEntity.getMainHandItem();

			if(rightStack.getItem() instanceof StaffItem item && item.staffType == StaffType.STAFF)
				rightArm.xRot *= 0.5f;
			if(leftStack.getItem() instanceof StaffItem item && item.staffType == StaffType.STAFF)
				leftArm.xRot *= 0.5f;
		}
	}

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
	private void staffRunningAnim(T entity, float f, float g, float h, float i, float j, CallbackInfo info) {
		if(entity.isSprinting() && ArcanusComponents.CASTING_COMPONENT.isProvidedBy(entity) && !ArcanusComponents.isCasting(entity) && !entity.isSwimming() && !entity.isFallFlying()) {
			Minecraft client = Minecraft.getInstance();
			ItemStack rightStack = client.options.mainHand().get() == HumanoidArm.RIGHT ? entity.getMainHandItem() : entity.getOffhandItem();
			ItemStack leftStack = client.options.mainHand().get() == HumanoidArm.RIGHT ? entity.getOffhandItem() : entity.getMainHandItem();

			if(rightStack.getItem() instanceof StaffItem item && item.staffType == StaffType.STAFF) {
				rightArm.zRot = rightArm.zRot * 0.5f - 1.0472f;
				rightArm.xRot = rightArm.xRot * 0.25f - 0.698132f;

				leftArm.zRot = -leftArm.zRot * 0.5f - 0.261799f;
				leftArm.xRot = -leftArm.xRot * 0.25f - 0.436332f;
			}

			if(leftStack.getItem() instanceof StaffItem item && item.staffType == StaffType.STAFF) {
				leftArm.zRot = leftArm.zRot * 0.5f + 1.0472f;
				leftArm.xRot = leftArm.xRot * 0.25f - 0.698132f;

				rightArm.zRot = -rightArm.zRot * 0.5f + 0.261799f;
				rightArm.xRot = -rightArm.xRot * 0.25f - 0.436332f;
			}
		}
	}

	@Inject(method = "poseRightArm", at = @At(
		value = "FIELD",
		target = "Lnet/minecraft/client/model/geom/ModelPart;xRot:F",
		ordinal = 2
	), cancellable = true)
	private void positionRightArm(T entity, CallbackInfo info) {
		Minecraft client = Minecraft.getInstance();
		ItemStack rightStack = client.options.mainHand().get() == HumanoidArm.RIGHT ? entity.getMainHandItem() : entity.getOffhandItem();

		if(rightStack.getItem() instanceof StaffItem item) {
			if(ArcanusComponents.CASTING_COMPONENT.isProvidedBy(entity) && ArcanusComponents.isCasting(entity)) {
				switch(item.staffType) {
					case STAFF -> {
						head.yRot = head.yRot + 1.13446f;
						rightArm.xRot = -1.13446f;
						rightArm.zRot = -1.13446f;
						rightArm.yRot = 0.610865f;
						leftArm.xRot = -0.349066f;
						leftArm.yRot = -0.610865f;
					}
					case BOOK -> {
						rightArm.xRot = rightArm.xRot * 0.5f - (float) (Math.PI / 10);
						leftArm.xRot = -1.39626f;
					}
					case GUN -> {
						rightArm.xRot = -1.309f;
						leftArm.xRot = -1.309f;
						rightArm.yRot = -0.785398f;
						leftArm.yRot = 0.785398f;
					}
					case WAND, GAUNTLET -> {
						rightArm.xRot = -1.309f;
					}
				}
			}
			else {
				if(item.staffType == StaffType.STAFF)
					rightArm.xRot = rightArm.xRot * 0.5f - 1.22173f;
				else
					rightArm.xRot = rightArm.xRot * 0.5f - (float) (Math.PI / 10);

				rightArm.yRot = 0f;
			}

			info.cancel();
		}
	}

	@Inject(method = "poseLeftArm", at = @At(
		value = "FIELD",
		target = "Lnet/minecraft/client/model/geom/ModelPart;xRot:F",
		ordinal = 2
	), cancellable = true)
	private void positionLeftArm(T entity, CallbackInfo info) {
		Minecraft client = Minecraft.getInstance();
		ItemStack leftStack = client.options.mainHand().get() == HumanoidArm.RIGHT ? entity.getOffhandItem() : entity.getMainHandItem();

		if(leftStack.getItem() instanceof StaffItem item) {
			if(ArcanusComponents.CASTING_COMPONENT.isProvidedBy(entity) && ArcanusComponents.isCasting(entity)) {
				switch(item.staffType) {
					case STAFF -> {
						head.yRot = head.yRot * 0.5f - 1.13446f;
						leftArm.xRot = -1.13446f;
						leftArm.zRot = 1.13446f;
						leftArm.yRot = -0.610865f;
						rightArm.xRot = -0.349066f;
						rightArm.yRot = 0.610865f;
					}
					case BOOK -> {
						leftArm.xRot = leftArm.xRot * 0.5f - (float) (Math.PI / 10);
						rightArm.xRot = -1.39626f;
					}
					case GUN -> {
						leftArm.xRot = -1.309f;
						rightArm.xRot = -1.309f;
						leftArm.yRot = 0.785398f;
						rightArm.yRot = -0.785398f;
					}
					case WAND, GAUNTLET -> {
						leftArm.xRot = -1.309f;
					}
				}
			}
			else {
				if(item.staffType == StaffType.STAFF)
					leftArm.xRot = leftArm.xRot * 0.5f - 1.22173f;
				else
					leftArm.xRot = leftArm.xRot * 0.5f - (float) (Math.PI / 10);

				leftArm.yRot = 0f;
			}

			info.cancel();
		}
	}
}
