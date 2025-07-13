package dev.cammiescorner.arcanus.api.spell.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spell.Weight;
import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public abstract class SpellShape extends SpellComponent {
	public static final Codec<SpellShape> CODEC = ArcanusSpellComponents.REGISTRY.byNameCodec().flatXmap(spellComponent -> spellComponent instanceof SpellShape shape ? DataResult.success(shape) : DataResult.error(() -> "Not instance of SpellShape"), DataResult::success);
	private final Supplier<Weight> weight;
	private final Supplier<Double> manaModifier;
	private final Supplier<Double> potencyModifier;
	private final Supplier<Double> coolDownModifier;

	public static SpellShape empty() {
		return (SpellShape) ArcanusSpellComponents.EMPTY.get();
	}

	public SpellShape(Supplier<Boolean> isEnabled, Supplier<Weight> weight, Supplier<Map<ManaType, Double>> manaCost, Supplier<Double> manaModifier, Supplier<Double> potencyModifier, Supplier<Double> coolDownModifier, Supplier<Boolean> procsOnce) {
		super(isEnabled, manaCost, procsOnce);
		this.weight = weight;
		this.manaModifier = manaModifier;
		this.potencyModifier = potencyModifier;
		this.coolDownModifier = coolDownModifier;
	}

	public Weight getWeight() {
		return weight.get();
	}

	public double getPotencyModifier() {
		return potencyModifier.get();
	}

	public double getManaModifier() {
		return manaModifier.get() - 1;
	}

	public double getCoolDownModifier() {
		return coolDownModifier.get();
	}

	public String getPotencyModifierAsString() {
		return (getPotencyModifier() < 0 ? "" : "+") + Arcanus.format(getPotencyModifier() * 100) + "%";
	}

	public String getManaMultiplierAsString() {
		return (getManaModifier() < 0 ? "" : "+") + Arcanus.format(getManaModifier() * 100) + "%";
	}

	public String getCoolDownModifierAsString() {
		return "x" + Arcanus.format(getCoolDownModifier());
	}

	public abstract void cast(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency);

	public static void castNext(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		if(spellGroups.size() <= groupIndex + 1)
			return;

		SpellGroup group = spellGroups.get(groupIndex + 1);
		group.shape().cast(caster, castFrom, castSource, level, stack, group.effects(), spellGroups, groupIndex + 1, potency);
	}
}
