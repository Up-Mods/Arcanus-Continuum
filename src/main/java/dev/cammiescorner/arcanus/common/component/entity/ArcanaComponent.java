package dev.cammiescorner.arcanus.common.component.entity;

import dev.cammiescorner.arcanus.api.arcana.ArcanaType;
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
	private final double[] arcana = new double[ArcanaType.values().length];

	public ArcanaComponent(LivingEntity entity) {
		this.entity = entity;

		Arrays.fill(arcana, 25d);
	}

	@Override
	public void serverTick() {
		for(ArcanaType arcanaType : ArcanaType.values()) {
			if(entity instanceof Player player && player.isCreative() && getArcana(arcanaType) < arcanaType.getMaxArcana(entity))
				addArcana(arcanaType, 1, false);
			else if(getArcana(arcanaType) < arcanaType.getMaxArcana(entity) && entity.getAttributeValue(arcanaType.getRegenAttribute()) > 0)
				addArcana(arcanaType, entity.getAttributeValue(arcanaType.getRegenAttribute()) / 20, false);

			if(getArcana(arcanaType) > arcanaType.getMaxArcana(entity))
				setArcana(arcanaType, arcanaType.getMaxArcana(entity));
		}
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		for (ArcanaType value : ArcanaType.values()) {
			arcana[value.ordinal()] = tag.getDouble(value.getSerializedName());
		}
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		for (ArcanaType value : ArcanaType.values()) {
			tag.putDouble(value.getSerializedName(), arcana[value.ordinal()]);
		}
	}

	public double getArcana(ArcanaType arcanaType) {
		return arcana[arcanaType.ordinal()];
	}

	public void setArcana(ArcanaType arcanaType, double amount) {
		arcana[arcanaType.ordinal()] = Mth.clamp(amount, 0, arcanaType.getMaxArcana(entity));
		ArcanusComponents.ARCANA_COMPONENT.sync(entity);
	}

	public double getTrueMaxArcana(ArcanaType arcanaType) {
		return arcanaType.getMaxArcana(entity) - getArcanaLock();
	}

	public double getArcanaLock() {
		AttributeInstance arcanaLockAttr = entity.getAttribute(ArcanusAttributes.ARCANA_LOCK.holder());

		if(arcanaLockAttr != null)
			return arcanaLockAttr.getValue();

		return 0;
	}

	public boolean addArcana(ArcanaType arcanaType, double amount, boolean simulate) {
		if(getArcana(arcanaType) < getTrueMaxArcana(arcanaType)) {
			if(!simulate)
				setArcana(arcanaType, getArcana(arcanaType) + amount);

			return true;
		}

		return false;
	}

	public boolean drainArcana(ArcanaType arcanaType, double amount, boolean simulate) {
		AttributeInstance instance = entity.getAttribute(ArcanusAttributes.MANA_COST.holder());

		if(instance != null)
			amount *= instance.getValue();

		if(getArcana(arcanaType) >= 0 && getArcana(arcanaType) >= amount) {
			if(!simulate)
				setArcana(arcanaType, Math.max(0, getArcana(arcanaType) - amount));

			return true;
		}

		return false;
	}
}
