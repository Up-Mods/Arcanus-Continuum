package dev.cammiescorner.arcanuscontinuum.common.components;

import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class BurnoutComponent implements AutoSyncedComponent, ServerTickingComponent {
	public static final UUID uUID = UUID.fromString("c2223d02-f2f0-4fa9-b9d8-5b2c265a8195");
	private final LivingEntity entity;
	private double burnout, prevBurnout;

	public BurnoutComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {
		EntityAttributeInstance burnoutRegenAttr = entity.getAttributeInstance(ArcanusEntityAttributes.BURNOUT_REGEN);
		EntityAttributeInstance attackSpeedAttr = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
		long timer = entity.world.getTime() - ArcanusComponents.getLastCastTime(entity);

		if(burnoutRegenAttr != null && drainBurnout(burnoutRegenAttr.getValue(), true) && timer % 40 == 0)
			drainBurnout(burnoutRegenAttr.getValue(), false);

		if(attackSpeedAttr != null) {
			if(burnout > 0 && attackSpeedAttr.getModifier(uUID) == null)
				attackSpeedAttr.addPersistentModifier(new EntityAttributeModifier(uUID, "Burnout modifier", -0.5, EntityAttributeModifier.Operation.MULTIPLY_BASE));
			if(burnout <= 0 && attackSpeedAttr.getModifier(uUID) != null)
				attackSpeedAttr.removeModifier(uUID);
		}
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		burnout = tag.getDouble("Burnout");
		prevBurnout = tag.getDouble("PrevBurnout");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putDouble("Burnout", burnout);
		tag.putDouble("PrevBurnout", prevBurnout);
	}

	public double getPrevBurnout() {
		return prevBurnout;
	}

	public double getBurnout() {
		return burnout;
	}

	public void setBurnout(double burnout) {
		this.prevBurnout = this.burnout;
		this.burnout = burnout;

		if(entity instanceof PlayerEntity)
			ArcanusComponents.BURNOUT_COMPONENT.sync(entity);
	}

	public boolean addBurnout(double amount, boolean simulate) {
		double maxMana = ArcanusComponents.getMaxMana(entity) - ArcanusComponents.getManaLock(entity);

		if(getBurnout() < maxMana) {
			if(!simulate)
				setBurnout(Math.min(maxMana, getBurnout() + amount));

			return true;
		}

		return false;
	}

	public boolean drainBurnout(double amount, boolean simulate) {
		if(getBurnout() > 0) {
			if(!simulate)
				setBurnout(Math.max(0, getBurnout() - amount));

			return true;
		}

		return false;
	}
}
