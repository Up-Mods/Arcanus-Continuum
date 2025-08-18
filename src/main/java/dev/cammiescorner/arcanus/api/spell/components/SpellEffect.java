package dev.cammiescorner.arcanus.api.spell.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public abstract class SpellEffect extends SpellComponent {
	// Checks to see if a given SpellComponent is a SpellEffect. If true, return success. If false, return a String.
	public static final Codec<SpellEffect> CODEC = ArcanusSpellComponents.REGISTRY.byNameCodec().flatXmap(spellComponent -> spellComponent instanceof SpellEffect effect ? DataResult.success(effect) : DataResult.error(() -> "Not an instance of SpellEffect"), DataResult::success);
	private final Supplier<SpellType> type;

	public SpellEffect(Supplier<Boolean> isEnabled, Supplier<SpellType> type, Supplier<Object2DoubleArrayMap<PrimalArcana>> arcanaCost, Supplier<Boolean> procsOnce) {
		super(isEnabled, arcanaCost, procsOnce);
		this.type = type;
	}

	public SpellType getType() {
		return type.get();
	}

	public abstract void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency);
}
