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
	private final LivingEntity entity;
	private int level = 0;

	public WizardLevelComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		level = tag.getInt("WizardLevel");
		EntityAttributeInstance manaAttr = entity.getAttributeInstance(ArcanusEntityAttributes.MAX_MANA.get());

		if(manaAttr != null) {
			if(manaAttr.getModifier(MANA_MODIFIER) != null)
				manaAttr.removeModifier(MANA_MODIFIER);

			manaAttr.addPersistentModifier(new EntityAttributeModifier(MANA_MODIFIER, "Wizard Level Modifier", Math.max(level - 1, 0) * 10, EntityAttributeModifier.Operation.ADDITION));
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
		EntityAttributeInstance manaAttr = entity.getAttributeInstance(ArcanusEntityAttributes.MAX_MANA.get());

		if(manaAttr != null) {
			if(manaAttr.getModifier(MANA_MODIFIER) != null)
				manaAttr.removeModifier(MANA_MODIFIER);

			manaAttr.addPersistentModifier(new EntityAttributeModifier(MANA_MODIFIER, "Wizard Level Modifier", Math.max(level - 1, 0) * 10, EntityAttributeModifier.Operation.ADDITION));
		}

		ArcanusComponents.WIZARD_LEVEL_COMPONENT.sync(entity);
	}
}
