package dev.cammiescorner.arcanus.common.spell_component.effects.attack;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.entity.Targetable;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.api.spell.components.DamageModifyingSpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.common.registry.ArcanusDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DamageSpellEffect extends SpellEffect {
	public DamageSpellEffect() {
		super(
			() -> ArcanusConfig.AttackEffects.DamageEffectProperties.enabled,
			() -> SpellType.ATTACK,
			() -> ArcanusConfig.AttackEffects.DamageEffectProperties.arcanaCosts(),
			() -> ArcanusConfig.AttackEffects.DamageEffectProperties.activatesOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;
			Entity entity = entityHit.getEntity();
			float damage = ArcanusConfig.AttackEffects.DamageEffectProperties.baseDamage;

			if(entity instanceof Player playerTarget && caster instanceof Player playerCaster && !playerCaster.canHarmPlayer(playerTarget))
				return;

			if(caster != null && entity instanceof Targetable targetable && targetable.arcanus$canBeTargeted()) {
				List<DamageModifyingSpellEffect> modifiers = effects.stream().filter(spellEffect -> spellEffect instanceof DamageModifyingSpellEffect).map(spellEffect -> (DamageModifyingSpellEffect) spellEffect).toList();
				DamageSource damageSource = modifiers.stream().map(spellEffect -> spellEffect.damageSource(level.damageSources())).findAny().orElse(sourceEntity instanceof Projectile projectile ? ArcanusDamageTypes.getMagicProjectileDamage(projectile, caster) : ArcanusDamageTypes.getMagicDamage(caster));

				for(Float value : modifiers.stream().map(spellEffect -> spellEffect.multiplyDamage(entity)).toList())
					damage *= value;

				entity.invulnerableTime = 0;
				entity.hurt(damageSource, (float) (damage * potency));
			}
		}
	}
}
