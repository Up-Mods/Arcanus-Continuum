package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.registry.ArcanusDamageTypes;
import dev.upcraft.sparkweave.api.datagen.provider.SparkweaveDynamicRegistryEntryProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class ArcanusDamageTypeProvider extends SparkweaveDynamicRegistryEntryProvider {
	@Override
	public void generate(RegistrySetBuilder builder) {
		builder.add(Registries.DAMAGE_TYPE, bootstapContext -> {
			bootstapContext.register(ArcanusDamageTypes.MAGIC, new DamageType("arcanus.magic", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.2f));
			bootstapContext.register(ArcanusDamageTypes.MAGIC_PROJECTILE, new DamageType("arcanus.magic_projectile", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.2f));
		});
	}

	@Override
	public String getName() {
		return Arcanus.MOD_ID;
	}
}
