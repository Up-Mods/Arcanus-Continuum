package dev.cammiescorner.arcanus.spell_component.effects.attack;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.api.spell.components.DamageModifyingSpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FireSpellEffect extends DamageModifyingSpellEffect {
	public FireSpellEffect() {
		super(
			() -> ArcanusConfig.AttackEffects.FireEffectProperties.enabled,
			() -> SpellType.ATTACK,
			() -> ArcanusConfig.AttackEffects.FireEffectProperties.arcanaCosts(),
			() -> ArcanusConfig.AttackEffects.FireEffectProperties.activatesOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;
			Entity entity = entityHit.getEntity();

			if(entity instanceof Player playerTarget && caster instanceof Player playerCaster && !playerCaster.canHarmPlayer(playerTarget))
				return;

			if(entity instanceof LivingEntity livingEntity)
				livingEntity.setRemainingFireTicks((int) (ArcanusConfig.AttackEffects.FireEffectProperties.baseTimeOnFire * potency));
		}
		else if(target.getType() == HitResult.Type.BLOCK) {
			BlockHitResult blockHit = (BlockHitResult) target;
			BlockPos pos = blockHit.getBlockPos().relative(blockHit.getDirection());
			BlockState state = Blocks.FIRE.defaultBlockState().setValue(switch(blockHit.getDirection()) {
				case UP, DOWN -> FireBlock.UP;
				case NORTH -> FireBlock.SOUTH;
				case SOUTH -> FireBlock.NORTH;
				case WEST -> FireBlock.EAST;
				case EAST -> FireBlock.WEST;
			}, blockHit.getDirection() != Direction.UP);

			if(level.isUnobstructed(state, pos, CollisionContext.empty()) && level.getBlockState(pos).canBeReplaced())
				level.setBlockAndUpdate(pos, state);
		}
	}

	@Override
	public DamageSource damageSource(DamageSources damageSources) {
		return damageSources.inFire();
	}

	@Override
	public float multiplyDamage(Entity target) {
		if(ArcanusComponents.isStunned(target))
			return ArcanusConfig.AttackEffects.FireEffectProperties.stunnedEntityDamageMultiplier;

		return super.multiplyDamage(target);
	}
}
