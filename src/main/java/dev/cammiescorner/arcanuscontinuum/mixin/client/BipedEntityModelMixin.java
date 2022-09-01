package dev.cammiescorner.arcanuscontinuum.mixin.client;

import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AnimalModel<T> implements ModelWithArms, ModelWithHead {
	@Shadow @Final public ModelPart rightArm;
	@Shadow @Final public ModelPart leftArm;
	@Shadow @Final public ModelPart head;

	@Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At(value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/util/math/MathHelper;cos(F)F",
			ordinal = 1
	))
	private void arcanuscontinuum$modifyArmSwing(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
		if(!livingEntity.isSprinting() && !livingEntity.isSwimming() && !livingEntity.isFallFlying()) {
			MinecraftClient client = MinecraftClient.getInstance();
			ItemStack rightStack = client.options.getMainArm().get() == Arm.RIGHT ? livingEntity.getMainHandStack() : livingEntity.getOffHandStack();
			ItemStack leftStack = client.options.getMainArm().get() == Arm.RIGHT ? livingEntity.getOffHandStack() : livingEntity.getMainHandStack();

			if(rightStack.getItem() instanceof StaffItem)
				rightArm.pitch *= 0.5F;
			if(leftStack.getItem() instanceof StaffItem)
				leftArm.pitch *= 0.5F;
		}
	}

	@Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
	private void arcanuscontinuum$staffRunningAnim(T entity, float f, float g, float h, float i, float j, CallbackInfo info) {
		if(entity.isSprinting() && !ArcanusComponents.isCasting(entity) && !entity.isSwimming() && !entity.isFallFlying()) {
			MinecraftClient client = MinecraftClient.getInstance();
			ItemStack rightStack = client.options.getMainArm().get() == Arm.RIGHT ? entity.getMainHandStack() : entity.getOffHandStack();
			ItemStack leftStack = client.options.getMainArm().get() == Arm.RIGHT ? entity.getOffHandStack() : entity.getMainHandStack();

			if(rightStack.getItem() instanceof StaffItem) {
				rightArm.roll = rightArm.roll * 0.5F - 1.0472F;
				rightArm.pitch = rightArm.pitch * 0.25F - 0.698132F;

				leftArm.roll = -leftArm.roll * 0.5F - 0.261799F;
				leftArm.pitch = -leftArm.pitch * 0.25F - 0.436332F;
			}

			if(leftStack.getItem() instanceof StaffItem) {
				leftArm.roll = leftArm.roll * 0.5F + 1.0472F;
				leftArm.pitch = leftArm.pitch * 0.25F - 0.698132F;

				rightArm.roll = -rightArm.roll * 0.5F + 0.261799F;
				rightArm.pitch = -rightArm.pitch * 0.25F - 0.436332F;
			}
		}
	}

	@Inject(method = "positionRightArm", at = @At(value = "FIELD",
			target = "Lnet/minecraft/client/model/ModelPart;pitch:F",
			ordinal = 2
	), cancellable = true)
	private void arcanuscontinuum$positionRightArm(T entity, CallbackInfo info) {
		MinecraftClient client = MinecraftClient.getInstance();
		ItemStack rightStack = client.options.getMainArm().get() == Arm.RIGHT ? entity.getMainHandStack() : entity.getOffHandStack();

		if(rightStack.getItem() instanceof StaffItem) {
			if(ArcanusComponents.isCasting(entity)) {
				head.yaw = head.yaw + 1.13446F;
				rightArm.pitch = -1.13446F;
				rightArm.roll = -1.13446F;
				rightArm.yaw = 0.610865F;
				leftArm.pitch = -0.349066F;
				leftArm.yaw = -0.610865F;
			}
			else {
				rightArm.pitch = rightArm.pitch * 0.5F - 1.22173F;
				rightArm.yaw = 0F;
			}

			info.cancel();
		}
	}

	@Inject(method = "positionLeftArm", at = @At(value = "FIELD",
			target = "Lnet/minecraft/client/model/ModelPart;pitch:F",
			ordinal = 2
	), cancellable = true)
	private void arcanuscontinuum$positionLeftArm(T entity, CallbackInfo info) {
		MinecraftClient client = MinecraftClient.getInstance();
		ItemStack leftStack = client.options.getMainArm().get() == Arm.RIGHT ? entity.getOffHandStack() : entity.getMainHandStack();

		if(leftStack.getItem() instanceof StaffItem) {
			if(ArcanusComponents.isCasting(entity)) {
				head.yaw = head.yaw * 0.5F - 1.13446F;
				leftArm.pitch = -1.13446F;
				leftArm.roll = 1.13446F;
				leftArm.yaw = -0.610865F;
				rightArm.pitch = -0.349066F;
				rightArm.yaw = 0.610865F;
			}
			else {
				leftArm.pitch = leftArm.pitch * 0.5F - 1.22173F;
				leftArm.yaw = 0F;
			}

			info.cancel();
		}
	}
}
