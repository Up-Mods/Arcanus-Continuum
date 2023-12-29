package dev.cammiescorner.arcanuscontinuum.common.components.entity;

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
	public static final UUID ATTACK_SPEED_MODIFIER = UUID.fromString("c2223d02-f2f0-4fa9-b9d8-5b2c265a8195");
	public static final UUID MOVE_SPEED_MODIFIER = UUID.fromString("38e12f7a-64d8-4054-b609-039e240eb2a9");
	private final LivingEntity entity;
	private double burnout;

	public BurnoutComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {
		EntityAttributeInstance burnoutRegenAttr = entity.getAttributeInstance(ArcanusEntityAttributes.BURNOUT_REGEN.get());
		EntityAttributeInstance attackSpeedAttr = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
		EntityAttributeInstance moveSpeedAttr = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

		if(burnoutRegenAttr != null && drainBurnout(burnoutRegenAttr.getValue(), true)) {
			drainBurnout(burnoutRegenAttr.getValue() / (entity instanceof PlayerEntity player && player.isCreative() ? 1 : 30), false);

			if(entity instanceof PlayerEntity player)
				player.addExhaustion(0.01F);
		}

		if(attackSpeedAttr != null) {
			if(burnout > 0 && attackSpeedAttr.getModifier(ATTACK_SPEED_MODIFIER) == null)
				attackSpeedAttr.addPersistentModifier(new EntityAttributeModifier(ATTACK_SPEED_MODIFIER, "Burnout modifier", -0.5, EntityAttributeModifier.Operation.MULTIPLY_BASE));
			if(burnout <= 0 && attackSpeedAttr.getModifier(ATTACK_SPEED_MODIFIER) != null)
				attackSpeedAttr.removeModifier(ATTACK_SPEED_MODIFIER);
		}

		if(moveSpeedAttr != null) {
			if(burnout > 0 && moveSpeedAttr.getModifier(MOVE_SPEED_MODIFIER) == null)
				moveSpeedAttr.addPersistentModifier(new EntityAttributeModifier(MOVE_SPEED_MODIFIER, "Burnout modifier", -0.1, EntityAttributeModifier.Operation.MULTIPLY_BASE));
			if(burnout <= 0 && moveSpeedAttr.getModifier(MOVE_SPEED_MODIFIER) != null)
				moveSpeedAttr.removeModifier(MOVE_SPEED_MODIFIER);
		}
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		burnout = tag.getDouble("Burnout");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putDouble("Burnout", burnout);
	}

	public double getBurnout() {
		return burnout;
	}

	public void setBurnout(double burnout) {
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
