package dev.cammiescorner.arcanuscontinuum.mixin.common;

import dev.cammiescorner.arcanuscontinuum.common.entities.magic.AggressorbEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ThrownEntity.class)
public abstract class ThrownEntityMixin extends ProjectileEntity {
	@Unique private final ThrownEntity self = (ThrownEntity) (Entity) this;

	public ThrownEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) { super(entityType, world); }

	@ModifyArg(method = "tick", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/util/math/Vec3d;multiply(D)Lnet/minecraft/util/math/Vec3d;"
	))
	private double arcanuscontinuum$noWaterDrag(double value) {
		return self instanceof AggressorbEntity && self.isTouchingWater() ? 0.99f : value;
	}
}
