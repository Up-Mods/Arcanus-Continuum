package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpatialRiftSpellEffect extends SpellEffect {
	private static final RegistryKey<World> POCKET_DIMENSION_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, Arcanus.id("pocket_dimension"));

	public SpatialRiftSpellEffect(boolean isEnabled, SpellType type, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(isEnabled, type, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, World world, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(!world.isClient() && caster instanceof PlayerEntity player && world.getRegistryKey() != POCKET_DIMENSION_WORLD_KEY) {
			Vec3d pos = target.getPos();
			ArcanusComponents.createPortal(player, (ServerWorld) world, pos, effects.stream().filter(ArcanusSpellComponents.SPATIAL_RIFT::is).count() * potency);
		}
	}

	@Override
	public boolean shouldTriggerOnceOnExplosion() {
		return true;
	}
}
