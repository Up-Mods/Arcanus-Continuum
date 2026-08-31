package dev.cammiescorner.arcanus.compat.lambdynamiclights;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.compat.DynamicLightsCompat;
import dev.lambdaurora.lambdynlights.api.entity.luminance.EntityLuminance;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSourceManager;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public record MagicEntityLuminance(int multiplier, int baseLightLevel) implements EntityLuminance {
	public static final MapCodec<MagicEntityLuminance> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Codec.INT.fieldOf("multiplier").forGetter(MagicEntityLuminance::multiplier),
		Codec.INT.fieldOf("baseLightLevel").forGetter(MagicEntityLuminance::baseLightLevel)
	).apply(instance, MagicEntityLuminance::new));

	@Override
	public @NotNull Type type() {
		return DynamicLightsCompat.MAGIC_ENTITY_LUMINANCE;
	}

	@Override
	public @Range(from = 0L, to = 15L) int getLuminance(@NotNull ItemLightSourceManager itemLightSourceManager, @NotNull Entity entity) {
		return (int) (Math.abs(Math.sin(entity.tickCount * 0.05)) * multiplier() + baseLightLevel());
	}
}
