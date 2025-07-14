package dev.cammiescorner.arcanus.common.component.entity;

import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import dev.cammiescorner.arcanus.common.registry.ArcanusAttributes;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.Arrays;

public class ManaComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity entity;
	private final double[] mana = new double[ManaType.values().length];

	public ManaComponent(LivingEntity entity) {
		this.entity = entity;

		Arrays.fill(mana, 25d);
	}

	@Override
	public void serverTick() {
		for(ManaType manaType : ManaType.values()) {
			if(entity instanceof Player player && player.isCreative() && getMana(manaType) < manaType.getMaxMana(entity))
				addMana(manaType, 1, false);
			else if(getMana(manaType) < manaType.getMaxMana(entity) && entity.getAttributeValue(manaType.getRegenAttribute()) > 0)
				addMana(manaType, entity.getAttributeValue(manaType.getRegenAttribute()) / 20, false);

			if(getMana(manaType) > manaType.getMaxMana(entity))
				setMana(manaType, manaType.getMaxMana(entity));
		}
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		for (ManaType value : ManaType.values()) {
			mana[value.ordinal()] = tag.getDouble(value.getSerializedName());
		}
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		for (ManaType value : ManaType.values()) {
			tag.putDouble(value.getSerializedName(), mana[value.ordinal()]);
		}
	}

	public double getMana(ManaType manaType) {
		return mana[manaType.ordinal()];
	}

	public void setMana(ManaType manaType, double amount) {
		mana[manaType.ordinal()] = Mth.clamp(amount, 0, manaType.getMaxMana(entity));
		ArcanusComponents.MANA_COMPONENT.sync(entity);
	}

	public double getTrueMaxMana(ManaType manaType) {
		return manaType.getMaxMana(entity) - getManaLock();
	}

	public double getManaLock() {
		AttributeInstance manaLockAttr = entity.getAttribute(ArcanusAttributes.MANA_LOCK.holder());

		if(manaLockAttr != null)
			return manaLockAttr.getValue();

		return 0;
	}

	public boolean addMana(ManaType manaType, double amount, boolean simulate) {
		if(getMana(manaType) < getTrueMaxMana(manaType)) {
			if(!simulate)
				setMana(manaType, getMana(manaType) + amount);

			return true;
		}

		return false;
	}

	public boolean drainMana(ManaType manaType, double amount, boolean simulate) {
		AttributeInstance instance = entity.getAttribute(ArcanusAttributes.MANA_COST.holder());

		if(instance != null)
			amount *= instance.getValue();

		if(getMana(manaType) >= 0 && getMana(manaType) >= amount) {
			if(!simulate)
				setMana(manaType, Math.max(0, getMana(manaType) - amount));

			return true;
		}

		return false;
	}
}
