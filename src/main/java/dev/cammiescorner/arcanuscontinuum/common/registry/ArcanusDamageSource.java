package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class ArcanusDamageSource {
	public static DamageSource getMagicDamage(Entity source) {
		return new EntityDamageSource(Arcanus.MOD_ID + ".magic_damage", source).setUsesMagic();
	}
}
