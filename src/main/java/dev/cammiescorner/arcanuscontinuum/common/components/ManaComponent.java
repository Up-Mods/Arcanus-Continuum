package dev.cammiescorner.arcanuscontinuum.common.components;

import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.nbt.NbtCompound;

public class ManaComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity entity;
	private int mana = 0;

	public ManaComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {
		EntityAttributeInstance manaRegenAttr = entity.getAttributeInstance(ArcanusEntityAttributes.MANA_REGEN);
		long timer = entity.world.getTime() - ArcanusComponents.getLastCastTime(entity);

		if(manaRegenAttr != null && addMana(1, true) && timer % (manaRegenAttr.getValue() * 20) == 0)
			addMana(1, false);
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		mana = tag.getInt("Mana");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putInt("Mana", mana);
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
		ArcanusComponents.MANA_COMPONENT.sync(entity);
	}

	public int getMaxMana() {
		EntityAttributeInstance maxManaAttr = entity.getAttributeInstance(ArcanusEntityAttributes.MAX_MANA);
		EntityAttributeInstance manaLockAttr = entity.getAttributeInstance(ArcanusEntityAttributes.MANA_LOCK);

		if(maxManaAttr != null && manaLockAttr != null)
			return (int) (maxManaAttr.getValue() - ArcanusComponents.getBurnout(entity) - manaLockAttr.getValue());
		else
			return 0;
	}

	public boolean addMana(int amount, boolean simulate) {
		if(getMana() < getMaxMana()) {
			if(!simulate)
				setMana(getMana() + amount);

			return true;
		}

		if(getMana() > getMaxMana())
			setMana(getMaxMana());

		return false;
	}

	public boolean drainMana(int amount, boolean simulate) {
		if(getMana() - amount >= 0) {
			if(!simulate)
				setMana(getMana() - amount);

			return true;
		}

		return false;
	}
}
