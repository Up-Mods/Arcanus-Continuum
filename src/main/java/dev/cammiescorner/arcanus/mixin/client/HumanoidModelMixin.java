package dev.cammiescorner.arcanus.mixin.client;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.common.item.StaffItem;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
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

			if(rightStack.getItem() instanceof StaffItem) {
				rightArm.xRot *= 0.5f;

				if(ArcanusConfig.ClientStuff.classicStaffCarryAnimation || livingEntity.isSprinting())
					leftArm.xRot *= 0.5f;
			}

			if(leftStack.getItem() instanceof StaffItem) {
				leftArm.xRot *= 0.5f;

				if(ArcanusConfig.ClientStuff.classicStaffCarryAnimation || livingEntity.isSprinting())
					rightArm.xRot *= 0.5f;
			}
		}
	}

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
	private void staffRunningAnim(T entity, float f, float g, float h, float i, float j, CallbackInfo info) {
		if(ArcanusConfig.ClientStuff.classicStaffCarryAnimation && entity.isSprinting() && ArcanusComponents.CASTING_COMPONENT.isProvidedBy(entity) && !ArcanusComponents.isCasting(entity) && !entity.isSwimming() && !entity.isFallFlying()) {
			Minecraft client = Minecraft.getInstance();
			ItemStack rightStack = client.options.mainHand().get() == HumanoidArm.RIGHT ? entity.getMainHandItem() : entity.getOffhandItem();
			ItemStack leftStack = client.options.mainHand().get() == HumanoidArm.RIGHT ? entity.getOffhandItem() : entity.getMainHandItem();

			if(rightStack.getItem() instanceof StaffItem) {
				rightArm.zRot = rightArm.zRot * 0.5f - 1.0472f;
				rightArm.xRot = rightArm.xRot * 0.25f - 0.698132f;

				leftArm.zRot = -leftArm.zRot * 0.5f - 0.261799f;
				leftArm.xRot = -leftArm.xRot * 0.25f - 0.436332f;
			}

			if(leftStack.getItem() instanceof StaffItem) {
				leftArm.zRot = leftArm.zRot * 0.5f + 1.0472f;
				leftArm.xRot = leftArm.xRot * 0.25f - 0.698132f;

				rightArm.zRot = -rightArm.zRot * 0.5f + 0.261799f;
				rightArm.xRot = -rightArm.xRot * 0.25f - 0.436332f;
			}
		}
	}

	@Inject(method = "poseRightArm", at = @At("TAIL"), cancellable = true)
	private void positionRightArm(T entity, CallbackInfo info) {
		Minecraft client = Minecraft.getInstance();
		ItemStack rightStack = client.options.mainHand().get() == HumanoidArm.RIGHT ? entity.getMainHandItem() : entity.getOffhandItem();

		if(rightStack.getItem() instanceof StaffItem) {
			if(ArcanusComponents.CASTING_COMPONENT.isProvidedBy(entity) && ArcanusComponents.isCasting(entity)) {
				head.yRot = head.yRot + 1.13446f;
				rightArm.xRot = -1.13446f;
				rightArm.yRot = 0.610865f;
				rightArm.zRot = -1.13446f;
				leftArm.xRot = -0.349066f;
				leftArm.yRot = -0.610865f;
			}
			else {
				if(ArcanusConfig.ClientStuff.classicStaffCarryAnimation) {
					rightArm.xRot = rightArm.xRot * 0.5f - 1.047198f;
					rightArm.yRot = 0f;
				}
				else {
					rightArm.zRot = rightArm.zRot * 0.5f - 1.047198f;
					rightArm.xRot = rightArm.xRot * 0.25f - 0.8726646f;

					leftArm.zRot = -leftArm.zRot * 0.5f - 0.261799f;
					leftArm.xRot = -leftArm.xRot * 0.25f - 0.3490659f;
				}
			}

			info.cancel();
		}
	}

	@Inject(method = "poseLeftArm", at = @At("TAIL"), cancellable = true)
	private void positionLeftArm(T entity, CallbackInfo info) {
		Minecraft client = Minecraft.getInstance();
		ItemStack leftStack = client.options.mainHand().get() == HumanoidArm.RIGHT ? entity.getOffhandItem() : entity.getMainHandItem();

		if(leftStack.getItem() instanceof StaffItem) {
			if(ArcanusComponents.CASTING_COMPONENT.isProvidedBy(entity) && ArcanusComponents.isCasting(entity)) {
				head.yRot = head.yRot * 0.5f - 1.13446f;
				leftArm.xRot = -1.13446f;
				leftArm.yRot = -0.610865f;
				leftArm.zRot = 1.13446f;
				rightArm.xRot = -0.349066f;
				rightArm.yRot = 0.610865f;
			}
			else {
				if(ArcanusConfig.ClientStuff.classicStaffCarryAnimation) {
					leftArm.xRot = leftArm.xRot * 0.5f - 1.047198f;
					leftArm.yRot = 0f;
				}
				else {
					leftArm.zRot = leftArm.zRot * 0.5f - 1.047198f;
					leftArm.xRot = leftArm.xRot * 0.25f - 0.8726646f;

					rightArm.zRot = -rightArm.zRot * 0.5f - 0.261799f;
					rightArm.xRot = -rightArm.xRot * 0.25f - 0.3490659f;
				}
			}

			info.cancel();
		}
	}
}
