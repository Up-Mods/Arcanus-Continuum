package dev.cammiescorner.arcanuscontinuum.api.spells;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class SpellEffect extends SpellComponent {
	private final SpellType type;
	private final ParticleEffect particle;

	public SpellEffect(SpellType type, ParticleEffect particle, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(weight, manaCost, coolDown, minLevel);
		this.type = type;
		this.particle = particle;
	}

	public ParticleEffect getParticleType() {
		return particle;
	}

	public SpellType getType() {
		return type;
	}

	public abstract void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, World world, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency);

	public boolean shouldTriggerOnceOnExplosion() {
		return false;
	}
}
