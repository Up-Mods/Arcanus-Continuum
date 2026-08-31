package dev.cammiescorner.arcanus.compat;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.compat.lambdynamiclights.BoltEntityLuminance;
import dev.cammiescorner.arcanus.compat.lambdynamiclights.MagicEntityLuminance;
import dev.cammiescorner.arcanus.compat.lambdynamiclights.SmiteEntityLuminance;
import dev.cammiescorner.arcanus.registry.ArcanusEntities;
import dev.lambdaurora.lambdynlights.api.DynamicLightsContext;
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import dev.lambdaurora.lambdynlights.api.entity.luminance.EntityLuminance;
import net.minecraft.world.entity.EntityType;

public class DynamicLightsCompat implements DynamicLightsInitializer {
	public static final EntityLuminance.Type MAGIC_ENTITY_LUMINANCE = EntityLuminance.Type.register(Arcanus.id("magic_entity"), MagicEntityLuminance.CODEC);
	public static final EntityLuminance.Type SMITE_ENTITY_LUMINANCE = EntityLuminance.Type.register(Arcanus.id("smite_entity"), SmiteEntityLuminance.CODEC);
	public static final EntityLuminance.Type BOLT_ENTITY_LUMINANCE = EntityLuminance.Type.register(Arcanus.id("bolt_entity"), BoltEntityLuminance.CODEC);

	@Override
	public void onInitializeDynamicLights(DynamicLightsContext context) {
		context.entityLightSourceManager().onRegisterEvent().register(ctx -> {
			ctx.register(ArcanusEntities.AOE.get(), new MagicEntityLuminance(7, 8));
			ctx.register(ArcanusEntities.BEAM.get(), new MagicEntityLuminance(4, 5));
			ctx.register(ArcanusEntities.MAGIC_ORB.get(), new MagicEntityLuminance(5, 6));
			ctx.register(ArcanusEntities.STOCKPILE_ORB.get(), new MagicEntityLuminance(5, 6));
			ctx.register(ArcanusEntities.MISSILE.get(), new MagicEntityLuminance(3, 4));
			ctx.register(ArcanusEntities.MAGIC_RUNE.get(), new MagicEntityLuminance(3, 4));
			ctx.register(ArcanusEntities.MANA_SHIELD.get(), new MagicEntityLuminance(7, 8));
			ctx.register(ArcanusEntities.SMITE.get(), new SmiteEntityLuminance());
			ctx.register(ArcanusEntities.PORTAL.get(), new MagicEntityLuminance(7, 8));
			ctx.register(EntityType.PLAYER, new BoltEntityLuminance());
		});
	}
}
