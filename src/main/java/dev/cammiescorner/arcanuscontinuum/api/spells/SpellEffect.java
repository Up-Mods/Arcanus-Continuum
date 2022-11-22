package dev.cammiescorner.arcanuscontinuum.api.spells;

import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class SpellEffect extends SpellComponent {
	private final ParticleEffect particle;

	public SpellEffect(ParticleEffect particle, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(weight, manaCost, coolDown, minLevel);
		this.particle = particle;
	}

	public ParticleEffect getParticleType() {
		return particle;
	}

	public abstract void effect(@Nullable Entity entityTarget, @Nullable Block blockTarget, World world, StaffItem staffItem);
}
