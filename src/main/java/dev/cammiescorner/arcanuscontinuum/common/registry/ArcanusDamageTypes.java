package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ArcanusDamageTypes {
	public static final RegistryKey<DamageType> MAGIC = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Arcanus.id("magic"));
	public static final RegistryKey<DamageType> MAGIC_PROJECTILE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Arcanus.id("magic_projectile"));

	public static DamageSource getMagicDamage(Entity source) {
		return create(source.getWorld(), MAGIC, source);
	}

	public static DamageSource getMagicDamage(ProjectileEntity source, @Nullable Entity attacker) {
		return create(source.getWorld(), MAGIC_PROJECTILE, source, attacker);
	}

	public static DamageSource create(World world, RegistryKey<DamageType> key, Entity attacker) {
		return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).getHolderOrThrow(key), attacker);
	}

	public static DamageSource create(World world, RegistryKey<DamageType> key, ProjectileEntity source, @Nullable Entity attacker) {
		return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).getHolderOrThrow(key), source, attacker);
	}
}
