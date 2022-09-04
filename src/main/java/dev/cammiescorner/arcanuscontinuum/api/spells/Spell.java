package dev.cammiescorner.arcanuscontinuum.api.spells;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Util;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class Spell {
	public static final Spell EMPTY = new Spell(null, Weight.VERY_LIGHT, 0, 10) { @Override public void cast(LivingEntity caster, World world, StaffItem staff) { } };
	private final Spell parent;
	private final Weight weight;
	private final double manaCost;
	private final int cooldown;
	private String translationKey;

	public Spell(@Nullable Spell parent, Weight weight, double manaCost, int cooldown) {
		this.parent = parent;
		this.weight = weight;
		this.manaCost = manaCost;
		this.cooldown = cooldown;
	}

	public Spell getParent() {
		return parent;
	}

	public Weight getWeight() {
		return weight;
	}

	public double getSlowdown() {
		return -weight.getSlowdown();
	}

	public double getManaCost() {
		return manaCost;
	}

	public int getCooldown() {
		return cooldown;
	}

	public String getTranslationKey() {
		if(translationKey == null)
			translationKey = Util.createTranslationKey("spell", Arcanus.SPELLS.getId(this));

		return translationKey;
	}

	public abstract void cast(LivingEntity caster, World world, StaffItem staff);
}
