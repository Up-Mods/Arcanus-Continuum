package dev.cammiescorner.arcanus.common.compat;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.components.entity.SizeComponent;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.world.entity.Entity;
import virtuoel.pehkui.api.ScaleRegistries;

public class PehkuiCompat {

	public static void registerEntityComponents(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(Entity.class, ArcanusComponents.SIZE).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(SizeComponent::new);
	}

	public static void registerModifiers() {
		ScaleRegistries.register(ScaleRegistries.SCALE_MODIFIERS, Arcanus.id("size_modifier"), SizeComponent.ArcanusScaleModifier.INSTANCE);
	}
}
