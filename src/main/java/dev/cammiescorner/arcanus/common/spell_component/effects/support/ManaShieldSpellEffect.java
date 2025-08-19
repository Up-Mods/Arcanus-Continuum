package dev.cammiescorner.arcanus.common.spell_component.effects.support;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.common.entity.magic.ManaShield;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaShieldSpellEffect extends SpellEffect {
	public ManaShieldSpellEffect() {
		super(
			() -> ArcanusConfig.SupportEffects.ManaShieldEffectProperties.enabled,
			() -> SpellType.SUPPORT,
			() -> ArcanusConfig.SupportEffects.ManaShieldEffectProperties.arcanaCosts(),
			() -> ArcanusConfig.SupportEffects.ManaShieldEffectProperties.activatesOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() != HitResult.Type.MISS && caster != null) {
			List<? extends ManaShield> list = ((ServerLevel) level).getEntities(EntityTypeTest.forClass(ManaShield.class), entity -> entity.getOwnerId().equals(caster.getUUID()));

			for(int i = 0; i < list.size() - 10; i++)
				list.get(i).kill();

			ManaShield arcanaShield = ArcanusEntities.MANA_SHIELD.get().create(level);

			if(arcanaShield != null) {
				arcanaShield.setProperties(caster.getUUID(), target.getLocation().add(0d, -0.7d, 0d), (int) ((ArcanusConfig.SupportEffects.ManaShieldEffectProperties.baseLifeSpan + ArcanusConfig.SupportEffects.ManaShieldEffectProperties.lifeSpanModifier * potency)));
				ArcanusHelper.copyMagicColor(arcanaShield, caster);
				level.addFreshEntity(arcanaShield);
			}
		}
	}
}
