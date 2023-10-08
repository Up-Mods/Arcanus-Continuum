package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.RegistryKeys;

public class ArcanusParticles {
	public static final RegistryHandler<ParticleType<?>> PARTICLE_TYPES = RegistryHandler.create(RegistryKeys.PARTICLE_TYPE, Arcanus.MOD_ID);

	public static final RegistrySupplier<DefaultParticleType> COLLAPSE = PARTICLE_TYPES.register("collapse", () -> FabricParticleTypes.simple(true));
	public static final RegistrySupplier<DefaultParticleType> SPEED = PARTICLE_TYPES.register("speed", () -> FabricParticleTypes.simple(true));
	public static final RegistrySupplier<DefaultParticleType> EXCHANGE = PARTICLE_TYPES.register("exchange", () -> FabricParticleTypes.simple(true));
	public static final RegistrySupplier<DefaultParticleType> BOUNCY = PARTICLE_TYPES.register("bouncy", () -> FabricParticleTypes.simple(true));
	public static final RegistrySupplier<DefaultParticleType> FEATHER = PARTICLE_TYPES.register("feather", () -> FabricParticleTypes.simple(true));

}
