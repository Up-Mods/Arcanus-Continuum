package dev.cammiescorner.arcanus.common.spell_component.shapes;

import commonnetwork.api.Network;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.common.networking.clientbound.ClientboundBurstVfxPacket;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;

public class BurstSpellShape extends SpellShape {
	public BurstSpellShape() {
		super(
			() -> ArcanusConfig.SpellShapes.BurstShapeProperties.enabled,
			() -> ArcanusConfig.SpellShapes.BurstShapeProperties.weight,
			() -> ArcanusConfig.SpellShapes.BurstShapeProperties.manaCosts(),
			() -> ArcanusConfig.SpellShapes.BurstShapeProperties.manaMultiplier,
			() -> ArcanusConfig.SpellShapes.BurstShapeProperties.potencyModifier,
			() -> ArcanusConfig.SpellShapes.BurstShapeProperties.coolDownModifier,
			() -> ArcanusConfig.SpellShapes.BurstShapeProperties.procsOnce);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		Entity sourceEntity = castSource != null ? castSource : caster;
		float radius = ArcanusConfig.SpellShapes.BurstShapeProperties.radius;
		AABB boundingBox = new AABB(castFrom.add(-radius, -radius, -radius), castFrom.add(radius, radius, radius));
		potency += getPotencyModifier();

		for(BlockPos blockPos : BlockPos.betweenClosedStream(boundingBox).toList()) {
			Vec3 pos = Vec3.atCenterOf(blockPos);

			if(pos.distanceTo(castFrom) <= radius)
				continue;

			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect(caster, sourceEntity, level, new BlockHitResult(pos, Direction.UP, blockPos, true), effects, stack, potency);
		}

		for(SpellEffect effect : new HashSet<>(effects)) {
			if(effect.singleCastOnly()) {
				effect.effect(caster, sourceEntity, level, new EntityHitResult(sourceEntity), effects, stack, potency);
				continue;
			}

			for(Entity entity : level.getEntities(sourceEntity == caster ? caster : null, boundingBox, entity -> entity.isAlive() && !entity.isSpectator() && castFrom.distanceTo(entity.position()) <= radius))
				effect.effect(caster, sourceEntity, level, new EntityHitResult(entity), effects, stack, potency);
		}

		// TODO add vfx & sfx for burst
		level.gameEvent(caster, GameEvent.EXPLODE, new Vec3(castFrom.x(), castFrom.y(), castFrom.z()));
		level.playSeededSound(null, castFrom.x(), castFrom.y(), castFrom.z(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4f, (1f + (level.random.nextFloat() - level.random.nextFloat()) * 0.2f) * 0.7f, 1);

		for(ServerPlayer player : PlayerLookup.tracking(level, BlockPos.containing(castFrom.x(), castFrom.y(), castFrom.z())))
			Network.getNetworkHandler().sendToClient(new ClientboundBurstVfxPacket(castFrom, 4f, effects.contains(ArcanusSpellComponents.MINE.get())), player);

		castNext(caster, castFrom, castSource, level, stack, spellGroups, groupIndex, potency);
	}
}
