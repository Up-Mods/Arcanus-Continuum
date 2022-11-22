package dev.cammiescorner.arcanuscontinuum.common.components;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;

public class WizardLevelComponent implements AutoSyncedComponent {
	private final LivingEntity entity;
	private int level = 0;

	public WizardLevelComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		level = tag.getInt("WizardLevel");
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
		ArcanusComponents.WIZARD_LEVEL_COMPONENT.sync(entity);
	}
}
