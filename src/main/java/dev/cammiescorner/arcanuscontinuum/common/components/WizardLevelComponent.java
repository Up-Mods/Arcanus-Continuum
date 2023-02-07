package dev.cammiescorner.arcanuscontinuum.common.components;

import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class WizardLevelComponent implements AutoSyncedComponent {
	public static final UUID MANA_MODIFIER = UUID.fromString("a64a245e-0f14-494f-8875-7aa8146b3fc1");
	public static final UUID SPELL_POTENCY_MODIFIER = UUID.fromString("cf02e50c-c7f4-4019-b101-5cda65b38c64");
	private final LivingEntity entity;
	private int level = 0;

	public WizardLevelComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		level = tag.getInt("WizardLevel");
		EntityAttributeInstance manaAttr = entity.getAttributeInstance(ArcanusEntityAttributes.MAX_MANA);
		EntityAttributeInstance spellPotencyAttr = entity.getAttributeInstance(ArcanusEntityAttributes.SPELL_POTENCY);

		if(manaAttr != null) {
			if(manaAttr.getModifier(MANA_MODIFIER) != null)
				manaAttr.removeModifier(MANA_MODIFIER);

			manaAttr.addPersistentModifier(new EntityAttributeModifier(MANA_MODIFIER, "Wizard Level Modifier", Math.max((level - 1) * 10, 10), EntityAttributeModifier.Operation.ADDITION));
		}

		if(spellPotencyAttr != null) {
			if(spellPotencyAttr.getModifier(SPELL_POTENCY_MODIFIER) != null)
				spellPotencyAttr.removeModifier(SPELL_POTENCY_MODIFIER);

			spellPotencyAttr.addPersistentModifier(new EntityAttributeModifier(SPELL_POTENCY_MODIFIER, "Wizard Level Modifier", level * 0.05, EntityAttributeModifier.Operation.MULTIPLY_BASE));
		}
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putInt("WizardLevel", level);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
		EntityAttributeInstance manaAttr = entity.getAttributeInstance(ArcanusEntityAttributes.MAX_MANA);
		EntityAttributeInstance spellPotencyAttr = entity.getAttributeInstance(ArcanusEntityAttributes.SPELL_POTENCY);

		if(manaAttr != null) {
			if(manaAttr.getModifier(MANA_MODIFIER) != null)
				manaAttr.removeModifier(MANA_MODIFIER);

			manaAttr.addPersistentModifier(new EntityAttributeModifier(MANA_MODIFIER, "Wizard Level Modifier", Math.max((level - 1) * 10, 10), EntityAttributeModifier.Operation.ADDITION));
		}

		if(spellPotencyAttr != null) {
			if(spellPotencyAttr.getModifier(SPELL_POTENCY_MODIFIER) != null)
				spellPotencyAttr.removeModifier(SPELL_POTENCY_MODIFIER);

			spellPotencyAttr.addPersistentModifier(new EntityAttributeModifier(SPELL_POTENCY_MODIFIER, "Wizard Level Modifier", level * 0.05, EntityAttributeModifier.Operation.MULTIPLY_BASE));
		}

		ArcanusComponents.WIZARD_LEVEL_COMPONENT.sync(entity);
	}
}
