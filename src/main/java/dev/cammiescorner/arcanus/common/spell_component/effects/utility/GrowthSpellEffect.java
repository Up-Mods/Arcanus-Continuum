package dev.cammiescorner.arcanus.common.spell_component.effects.utility;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GrowthSpellEffect extends SpellEffect {
	public GrowthSpellEffect() {
		super(
			() -> ArcanusConfig.UtilityEffects.GrowthEffectProperties.enabled,
			() -> SpellType.UTILITY,
			() -> ArcanusConfig.UtilityEffects.GrowthEffectProperties.arcanaCosts(),
			() -> ArcanusConfig.UtilityEffects.GrowthEffectProperties.activatesOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;

			if(entityHit.getEntity() instanceof Animal animal && animal.isBaby())
				animal.ageUp((int) (AgeableMob.getSpeedUpSecondsWhenFeeding(-animal.getAge()) * potency), true);
		}
		else if(target.getType() == HitResult.Type.BLOCK) {
			BlockHitResult blockHit = (BlockHitResult) target;
			BlockPos pos = blockHit.getBlockPos().relative(blockHit.getDirection());

			for(int i = 0; i < potency; i++) {
				BoneMealItem.growCrop(ItemStack.EMPTY, level, pos);
				BoneMealItem.growWaterPlant(ItemStack.EMPTY, level, pos, blockHit.getDirection());
			}
		}
	}
}
