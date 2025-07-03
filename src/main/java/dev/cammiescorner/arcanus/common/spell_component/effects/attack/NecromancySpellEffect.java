package dev.cammiescorner.arcanus.common.spell_component.effects.attack;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.SpellType;
import dev.cammiescorner.arcanus.common.entity.living.NecroSkeleton;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NecromancySpellEffect extends SpellEffect {
	public NecromancySpellEffect() {
		super(
			() -> ArcanusConfig.AttackEffects.NecromancyEffectProperties.enabled,
			() -> SpellType.ATTACK,
			() -> ArcanusConfig.AttackEffects.NecromancyEffectProperties.weight,
			() -> ArcanusConfig.AttackEffects.NecromancyEffectProperties.manaCosts(),
			() -> ArcanusConfig.AttackEffects.NecromancyEffectProperties.coolDown,
			() -> ArcanusConfig.AttackEffects.NecromancyEffectProperties.procsOnce
		);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, Level level, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(caster != null) {
			NecroSkeleton skeleton = ArcanusEntities.NECRO_SKELETON.get().create(level);
			int effectCount = (int) effects.stream().filter(ArcanusSpellComponents.NECROMANCY::is).count();

			if(skeleton != null) {
				AttributeInstance damage = skeleton.getAttribute(Attributes.ATTACK_DAMAGE);

				skeleton.setPos(target.getLocation());
				skeleton.setMaxHealth((ArcanusConfig.AttackEffects.NecromancyEffectProperties.baseHealth + effectCount) * potency);
				skeleton.setOwner(caster);
				skeleton.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_AXE));
				skeleton.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ArcanusItems.WIZARD_HAT.get()));

				if(damage != null)
					damage.addPermanentModifier(new AttributeModifier(Arcanus.id("necro_skele_attack_modifier"), (effectCount / 2d) * potency, AttributeModifier.Operation.ADD_VALUE));

				level.addFreshEntity(skeleton);
			}
		}
	}
}
