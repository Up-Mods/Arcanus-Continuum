package dev.cammiescorner.arcanus.api.spells;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class SpellEffect extends SpellComponent {
	public static final Codec<SpellEffect> CODEC = ArcanusSpellComponents.REGISTRY.byNameCodec().flatXmap(spellComponent -> spellComponent instanceof SpellEffect effect ? DataResult.success(effect) : DataResult.error(() -> "Not an instance of SpellEffect"), DataResult::success);
	private final SpellType type;

	public SpellEffect(boolean isEnabled, SpellType type, Weight weight, double manaCost, int coolDown, int minLevel, boolean procsOnce) {
		super(isEnabled, weight, manaCost, coolDown, minLevel, procsOnce);
		this.type = type;
	}

	public SpellType getType() {
		return type;
	}

	public abstract void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency);
}
