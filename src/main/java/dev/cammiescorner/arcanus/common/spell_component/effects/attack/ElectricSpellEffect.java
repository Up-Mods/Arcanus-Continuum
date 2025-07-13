package dev.cammiescorner.arcanus.common.spell_component.effects.attack;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElectricSpellEffect extends SpellEffect {
	public ElectricSpellEffect() {
		super(
			() -> ArcanusConfig.AttackEffects.ElectricEffectProperties.enabled,
			() -> SpellType.ATTACK,
			() -> ArcanusConfig.AttackEffects.ElectricEffectProperties.manaCosts(),
			() -> ArcanusConfig.AttackEffects.ElectricEffectProperties.procsOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;
			Entity entity = entityHit.getEntity();

			if(entity instanceof Player playerTarget && caster instanceof Player playerCaster && !playerCaster.canHarmPlayer(playerTarget))
				return;

			if(entity instanceof LivingEntity livingEntity) {
				ArcanusComponents.setStunTimer(livingEntity, (int) (ArcanusConfig.AttackEffects.ElectricEffectProperties.baseStunTime * potency));

				if(livingEntity instanceof Creeper creeper && !creeper.getEntityData().get(Creeper.DATA_IS_POWERED))
					creeper.getEntityData().set(Creeper.DATA_IS_POWERED, true);
			}
		}
	}
}
