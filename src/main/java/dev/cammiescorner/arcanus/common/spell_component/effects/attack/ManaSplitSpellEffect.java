package dev.cammiescorner.arcanus.common.spell_component.effects.attack;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.arcana.ArcanaType;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaSplitSpellEffect extends SpellEffect {
	public ManaSplitSpellEffect() {
		super(
			() -> ArcanusConfig.AttackEffects.ManaSplitEffectProperties.enabled,
			() -> SpellType.ATTACK,
			() -> ArcanusConfig.AttackEffects.ManaSplitEffectProperties.arcanaCosts(),
			() -> ArcanusConfig.AttackEffects.ManaSplitEffectProperties.procsOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;
			Entity entity = entityHit.getEntity();

			if(entity instanceof Player playerTarget && caster instanceof Player playerCaster && !playerCaster.canHarmPlayer(playerTarget))
				return;

			if(entity instanceof LivingEntity livingEntity && caster != null) {
				for(ArcanaType arcanaType : ArcanaType.values()) {
					double splitArcana = ArcanusComponents.getArcana(caster, arcanaType) + ArcanusComponents.getArcana(livingEntity, arcanaType);
					double percent = 0.5 * potency;
					double casterArcana = splitArcana * percent;

					ArcanusComponents.setArcana(caster, arcanaType, casterArcana);
					ArcanusComponents.setArcana(livingEntity, arcanaType, splitArcana - casterArcana);
				}
			}
		}
	}
}
