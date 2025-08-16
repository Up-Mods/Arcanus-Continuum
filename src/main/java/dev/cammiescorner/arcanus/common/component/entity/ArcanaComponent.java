package dev.cammiescorner.arcanus.common.component.entity;

import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
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

public class ArcanaComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity entity;
	private final double[] arcana = new double[PrimalArcana.values().length];

	public ArcanaComponent(LivingEntity entity) {
		this.entity = entity;

		Arrays.fill(arcana, 25d);
	}

	@Override
	public void serverTick() {
		for(PrimalArcana primalArcana : PrimalArcana.values()) {
			if(entity instanceof Player player && player.isCreative() && getArcana(primalArcana) < primalArcana.getMaxArcana(entity))
				addArcana(primalArcana, 1, false);
			else if(getArcana(primalArcana) < primalArcana.getMaxArcana(entity) && entity.getAttributeValue(primalArcana.getRegenAttribute()) > 0)
				addArcana(primalArcana, entity.getAttributeValue(primalArcana.getRegenAttribute()) / 20, false);

			if(getArcana(primalArcana) > primalArcana.getMaxArcana(entity))
				setArcana(primalArcana, primalArcana.getMaxArcana(entity));
		}
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		for (PrimalArcana value : PrimalArcana.values()) {
			arcana[value.ordinal()] = tag.getDouble(value.getSerializedName());
		}
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		for (PrimalArcana value : PrimalArcana.values()) {
			tag.putDouble(value.getSerializedName(), arcana[value.ordinal()]);
		}
	}

	public double getArcana(PrimalArcana primalArcana) {
		return arcana[primalArcana.ordinal()];
	}

	public void setArcana(PrimalArcana primalArcana, double amount) {
		arcana[primalArcana.ordinal()] = Mth.clamp(amount, 0, primalArcana.getMaxArcana(entity));
		ArcanusComponents.ARCANA_COMPONENT.sync(entity);
	}

	public double getTrueMaxArcana(PrimalArcana primalArcana) {
		return primalArcana.getMaxArcana(entity) - getArcanaLock();
	}

	public double getArcanaLock() {
		AttributeInstance arcanaLockAttr = entity.getAttribute(ArcanusAttributes.ARCANA_LOCK.holder());

		if(arcanaLockAttr != null)
			return arcanaLockAttr.getValue();

		return 0;
	}

	public boolean addArcana(PrimalArcana primalArcana, double amount, boolean simulate) {
		if(getArcana(primalArcana) < getTrueMaxArcana(primalArcana)) {
			if(!simulate)
				setArcana(primalArcana, getArcana(primalArcana) + amount);

			return true;
		}

		return false;
	}

	public boolean drainArcana(PrimalArcana primalArcana, double amount, boolean simulate) {
		AttributeInstance instance = entity.getAttribute(ArcanusAttributes.MANA_COST.holder());

		if(instance != null)
			amount *= instance.getValue();

		if(getArcana(primalArcana) >= 0 && getArcana(primalArcana) >= amount) {
			if(!simulate)
				setArcana(primalArcana, Math.max(0, getArcana(primalArcana) - amount));

			return true;
		}

		return false;
	}
}
