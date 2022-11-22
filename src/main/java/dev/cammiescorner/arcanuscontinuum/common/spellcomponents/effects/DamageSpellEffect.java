package dev.cammiescorner.arcanuscontinuum.common.spellcomponents.effects;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DamageSpellEffect extends SpellEffect {
	public DamageSpellEffect(ParticleEffect particle, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(particle, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable Entity entityTarget, @Nullable Block blockTarget, World world, StaffItem staffItem) {

	}
}
