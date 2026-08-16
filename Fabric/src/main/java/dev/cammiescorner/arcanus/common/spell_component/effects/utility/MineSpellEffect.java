package dev.cammiescorner.arcanus.common.spell_component.effects.utility;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MineSpellEffect extends SpellEffect {
	public MineSpellEffect() {
		super(
			() -> ArcanusConfig.UtilityEffects.MineEffectProperties.enabled,
			() -> SpellType.UTILITY,
			() -> ArcanusConfig.UtilityEffects.MineEffectProperties.arcanaCosts(),
			() -> ArcanusConfig.UtilityEffects.MineEffectProperties.activatesOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.BLOCK) {
			BlockHitResult blockHit = (BlockHitResult) target;
			BlockState state = level.getBlockState(blockHit.getBlockPos());

			if(state.getDestroySpeed(level, blockHit.getBlockPos()) > 0) {
				if(!(caster instanceof Player player) || level.mayInteract(player, blockHit.getBlockPos())) {
					if(potency < 2 && state.is(BlockTags.NEEDS_STONE_TOOL))
						return;
					if(potency < 3 && state.is(BlockTags.NEEDS_IRON_TOOL))
						return;
					if(potency < 4 && state.is(BlockTags.NEEDS_DIAMOND_TOOL))
						return;

					level.destroyBlock(blockHit.getBlockPos(), true, caster);
				}
			}
		}
	}
}
