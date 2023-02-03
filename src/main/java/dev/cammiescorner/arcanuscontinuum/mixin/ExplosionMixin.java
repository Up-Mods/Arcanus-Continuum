package dev.cammiescorner.arcanuscontinuum.mixin;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import dev.cammiescorner.arcanuscontinuum.common.util.EntityAwareExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.explosion.Explosion;
import org.apache.commons.compress.utils.Lists;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(Explosion.class)
public abstract class ExplosionMixin implements EntityAwareExplosion {

	@Unique
	private final List<Entity> affectedEntities = Lists.newArrayList();

	@ModifyReceiver(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
	private Entity collectBlocksAndDamageEntities(Entity entity, DamageSource ds, float amount) {
		this.affectedEntities.add(entity);
		return entity;
	}

	@Override
	public List<Entity> getAffectedEntities() {
		return affectedEntities;
	}
}
