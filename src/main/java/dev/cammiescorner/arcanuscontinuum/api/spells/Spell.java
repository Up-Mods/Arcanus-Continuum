package dev.cammiescorner.arcanuscontinuum.api.spells;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public abstract class Spell {
	private final Weight weight;
	private final double manaCost;
	private final int cooldown;
	private String translationKey;

	public Spell(Weight weight, double manaCost, int cooldown) {
		this.weight = weight;
		this.manaCost = manaCost;
		this.cooldown = cooldown;
	}

	public double getSlowdown() {
		return weight.getSlowdown();
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
