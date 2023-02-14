package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;

public class ArcanusParticles {
	//-----Particle Map-----//
	public static final LinkedHashMap<DefaultParticleType, Identifier> PARTICLE_TYPES = new LinkedHashMap<>();

	//-----Particles-----//
	public static final DefaultParticleType COLLAPSE = create("collapse");

	//-----Registry-----//
	public static void register() {
		PARTICLE_TYPES.keySet().forEach(particleType -> Registry.register(Registries.PARTICLE_TYPE, PARTICLE_TYPES.get(particleType), particleType));
	}

	private static DefaultParticleType create(String name) {
		DefaultParticleType particleType = FabricParticleTypes.simple(true);
		PARTICLE_TYPES.put(particleType, Arcanus.id(name));
		return particleType;
	}
}
