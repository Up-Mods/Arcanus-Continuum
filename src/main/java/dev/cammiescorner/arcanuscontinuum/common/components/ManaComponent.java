package dev.cammiescorner.arcanuscontinuum.common.components;

import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;

public class ManaComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity entity;
	private double mana;

	public ManaComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {
		EntityAttributeInstance manaRegenAttr = entity.getAttributeInstance(ArcanusEntityAttributes.MANA_REGEN.get());
		long timer = entity.getWorld().getTime() - ArcanusComponents.getLastCastTime(entity);

		if(manaRegenAttr != null && addMana(manaRegenAttr.getValue(), true) && timer % (entity instanceof PlayerEntity player && player.isCreative() ? 1 : 20) == 0)
			addMana(manaRegenAttr.getValue(), false);
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		mana = tag.getDouble("Mana");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putDouble("Mana", mana);
	}

	public double getMana() {
		return mana;
	}

	public void setMana(double mana) {
		this.mana = MathHelper.clamp(mana, 0, getTrueMaxMana());

		if(entity instanceof PlayerEntity)
			ArcanusComponents.MANA_COMPONENT.sync(entity);
	}

	public double getTrueMaxMana() {
		return (getMaxMana() - getManaLock()) - ArcanusComponents.getBurnout(entity);
	}

	public double getMaxMana() {
		EntityAttributeInstance maxManaAttr = entity.getAttributeInstance(ArcanusEntityAttributes.MAX_MANA.get());

		if(maxManaAttr != null)
			return maxManaAttr.getValue();

		return 0;
	}

	public double getManaLock() {
		EntityAttributeInstance manaLockAttr = entity.getAttributeInstance(ArcanusEntityAttributes.MANA_LOCK.get());

		if(manaLockAttr != null)
			return manaLockAttr.getValue();

		return 0;
	}

	public boolean addMana(double amount, boolean simulate) {
		if(getMana() < getTrueMaxMana()) {
			if(!simulate)
				setMana(Math.min(getTrueMaxMana(), getMana() + amount));

			return true;
		}

		if(getMana() > getTrueMaxMana())
			setMana(getTrueMaxMana());

		return false;
	}

	public boolean drainMana(double amount, boolean simulate) {
		if(getMana() >= 0 && getMana() + getTrueMaxMana() >= amount) {
			if(!simulate) {
				if(amount > getMana()) {
					ArcanusComponents.addBurnout(entity, amount - getMana(), false);

					if(ArcanusComponents.getBurnout(entity) >= ArcanusComponents.getMaxMana(entity) * 0.5F)
						entity.damage(entity.getDamageSources().outOfWorld(), (float) Math.min(entity.getHealth() - 1, amount - getMana()));
				}

				setMana(Math.max(0, getMana() - amount));
			}

			return true;
		}

		return false;
	}
}
