package dev.cammiescorner.arcanus.common.compat.lambdynamiclights;

import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import dev.cammiescorner.arcanus.common.compat.DynamicLightsCompat;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.lambdaurora.lambdynlights.api.entity.luminance.EntityLuminance;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSourceManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public record BoltEntityLuminance() implements EntityLuminance {
	public static final MapCodec<BoltEntityLuminance> CODEC = MapCodec.of(Encoder.empty(), Decoder.unit(BoltEntityLuminance::new));

	@Override
	public @NotNull Type type() {
		return DynamicLightsCompat.BOLT_ENTITY_LUMINANCE;
	}

	@Override
	public @Range(from = 0L, to = 15L) int getLuminance(@NotNull ItemLightSourceManager itemLightSourceManager, @NotNull Entity entity) {
		return entity instanceof LivingEntity livingEntity && ArcanusComponents.shouldRenderBolt(livingEntity) ? 15 : 0;
	}
}
