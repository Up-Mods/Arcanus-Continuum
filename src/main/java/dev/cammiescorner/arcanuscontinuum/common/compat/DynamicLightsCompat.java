package dev.cammiescorner.arcanuscontinuum.common.compat;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import dev.lambdaurora.lambdynlights.api.DynamicLightHandlers;
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import net.minecraft.entity.EntityType;

public class DynamicLightsCompat implements DynamicLightsInitializer {
	@Override
	public void onInitializeDynamicLights() {
		DynamicLightHandlers.registerDynamicLightHandler(ArcanusEntities.AOE.get(), entity -> (int) (Math.abs(Math.sin(entity.age * 0.05)) * 7 + 8));
		DynamicLightHandlers.registerDynamicLightHandler(ArcanusEntities.BEAM.get(), entity -> (int) (Math.abs(Math.sin(entity.age * 0.05)) * 4 + 5));
		DynamicLightHandlers.registerDynamicLightHandler(ArcanusEntities.GUARDIAN_ORB.get(), entity -> (int) (Math.abs(Math.sin(entity.age * 0.05)) * 5 + 6));
		DynamicLightHandlers.registerDynamicLightHandler(ArcanusEntities.MAGIC_PROJECTILE.get(), entity -> (int) (Math.abs(Math.sin(entity.age * 0.05)) * 3 + 4));
		DynamicLightHandlers.registerDynamicLightHandler(ArcanusEntities.MAGIC_RUNE.get(), entity -> (int) (Math.abs(Math.sin(entity.age * 0.05)) * 3 + 4));
		DynamicLightHandlers.registerDynamicLightHandler(ArcanusEntities.MANA_SHIELD.get(), entity -> (int) (Math.abs(Math.sin(entity.age * 0.05)) * 7 + 8));
		DynamicLightHandlers.registerDynamicLightHandler(ArcanusEntities.SMITE.get(), entity -> (int) (-0.103501 * entity.age * entity.age + 2.15793 * entity.age + 3.38905));
		DynamicLightHandlers.registerDynamicLightHandler(EntityType.PLAYER, playerEntity -> ArcanusComponents.shouldRenderBolt(playerEntity) ? 15 : 0);
	}
}
