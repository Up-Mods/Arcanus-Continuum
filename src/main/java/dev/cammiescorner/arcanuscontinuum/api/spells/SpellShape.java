package dev.cammiescorner.arcanuscontinuum.api.spells;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class SpellShape extends SpellComponent {

	public static SpellShape empty() {
		return (SpellShape) ArcanusSpellComponents.EMPTY.get();
	}

	private final double manaMultiplier;

	public SpellShape(Weight weight, double manaCost, double manaMultiplier, int coolDown, int minLevel) {
		super(weight, manaCost, coolDown, minLevel);
		this.manaMultiplier = manaMultiplier;
	}

	public SpellShape(Weight weight, double manaCost, int coolDown, int minLevel) {
		this(weight, manaCost, 1, coolDown, minLevel);
	}

	public double getManaMultiplier() {
		return manaMultiplier - 1;
	}

	public String getManaMultiplierAsString() {
		return (getManaMultiplier() < 0 ? "" : "+") + Arcanus.format(getManaMultiplier() * 100) + "%";
	}

	public abstract void cast(@Nullable LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency);

	public static void castNext(@Nullable LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, ItemStack stack, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		if(spellGroups.size() <= groupIndex + 1)
			return;

		SpellGroup group = spellGroups.get(groupIndex + 1);
		group.shape().cast(caster, castFrom, castSource, world, stack, group.effects(), spellGroups, groupIndex + 1, potency);
	}
}
