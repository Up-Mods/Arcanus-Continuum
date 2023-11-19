package dev.cammiescorner.arcanuscontinuum.api.spells;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class SpellEffect extends SpellComponent {
	private final SpellType type;

	public SpellEffect(boolean isEnabled, SpellType type, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(isEnabled, weight, manaCost, coolDown, minLevel);
		this.type = type;
	}

	public SpellType getType() {
		return type;
	}

	public abstract void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, World world, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency);

	public boolean shouldTriggerOnceOnExplosion() {
		return false;
	}
}
