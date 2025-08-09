package dev.cammiescorner.arcanus.api.spell.components;

import dev.cammiescorner.arcanus.api.arcana.ArcanaType;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;

import java.util.Map;
import java.util.function.Supplier;

public abstract class DamageModifyingSpellEffect extends SpellEffect {
	public DamageModifyingSpellEffect(Supplier<Boolean> isEnabled, Supplier<SpellType> type, Supplier<Map<ArcanaType, Double>> arcanaCost, Supplier<Boolean> procsOnce) {
		super(isEnabled, type, arcanaCost, procsOnce);
	}

	public abstract DamageSource damageSource(DamageSources damageSources);
}
