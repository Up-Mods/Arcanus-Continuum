package dev.cammiescorner.arcanus.spell_component.effects.utility;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.data.ArcanusTags;
import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.util.TranslationKeys;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WardingSpellEffect extends SpellEffect {
	public WardingSpellEffect() {
		super(
			() -> ArcanusConfig.UtilityEffects.WardingEffectProperties.enabled,
			() -> SpellType.UTILITY,
			() -> ArcanusConfig.UtilityEffects.WardingEffectProperties.arcanaCosts(),
			() -> ArcanusConfig.UtilityEffects.WardingEffectProperties.activatesOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.BLOCK && caster instanceof Player player) {
			var blockHit = (BlockHitResult) target;
			var pos = blockHit.getBlockPos();
			var state = level.getBlockState(pos);

			if(level.mayInteract(player, pos)) {
				var isWarded = ArcanusComponents.isBlockWarded(level, pos);

				if(isWarded || !state.getCollisionShape(level, pos).isEmpty()) {
					if(isWarded) {
						ArcanusComponents.removeWardedBlock(player, pos);
					}
					else {
						Holder<DimensionType> dimensionHolder = level.dimensionTypeRegistration();

						if(dimensionHolder.is(ArcanusTags.Dimensions.WARDING_NOT_ALLOWED))
							player.sendSystemMessage(Component.translatable(TranslationKeys.CANT_WARD_IN_DIM));
						else if(state.is(ArcanusTags.Blocks.WARDING_NOT_ALLOWED))
							player.sendSystemMessage(Component.translatable(TranslationKeys.CANT_WARD_BLOCK));
						else
							ArcanusComponents.addWardedBlock(player, pos);
					}
				}
			}
		}
	}
}
