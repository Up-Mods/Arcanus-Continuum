package dev.cammiescorner.arcanus.compat.lambdynamiclights;

import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import dev.cammiescorner.arcanus.compat.DynamicLightsCompat;
import dev.lambdaurora.lambdynlights.api.entity.luminance.EntityLuminance;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSourceManager;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public record SmiteEntityLuminance() implements EntityLuminance {
	public static final MapCodec<SmiteEntityLuminance> CODEC = MapCodec.of(Encoder.empty(), Decoder.unit(SmiteEntityLuminance::new));

	@Override
	public @NotNull Type type() {
		return DynamicLightsCompat.SMITE_ENTITY_LUMINANCE;
	}

	@Override
	public @Range(from = 0L, to = 15L) int getLuminance(@NotNull ItemLightSourceManager itemLightSourceManager, @NotNull Entity entity) {
		return (int) (-0.103501 * entity.tickCount * entity.tickCount + 2.15793 * entity.tickCount + 3.38905);
	}
}
