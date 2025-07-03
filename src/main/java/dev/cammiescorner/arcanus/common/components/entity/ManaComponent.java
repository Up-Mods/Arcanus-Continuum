package dev.cammiescorner.arcanus.common.components.entity;

import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusAttributes;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.HashMap;
import java.util.Map;

public class ManaComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity entity;
	private final Map<ManaType, Double> manaMap = new HashMap<>();
	private double mana;

	public ManaComponent(LivingEntity entity) {
		this.entity = entity;

		manaMap.putIfAbsent(ManaType.RED, 25d);
		manaMap.putIfAbsent(ManaType.GREEN, 25d);
		manaMap.putIfAbsent(ManaType.BLUE, 25d);
		manaMap.putIfAbsent(ManaType.WHITE, 25d);
		manaMap.putIfAbsent(ManaType.BLACK, 25d);
	}

	@Override
	public void serverTick() {
		AttributeInstance manaRegenAttr = entity.getAttribute(ArcanusAttributes.MANA_REGEN.holder());

		for(ManaType manaType : manaMap.keySet()) {
			if(manaRegenAttr != null)
				addMana(manaType, manaRegenAttr.getValue() / (entity instanceof Player player && player.isCreative() ? 1 : 20), false);

			if(getMana(manaType) > manaType.getMaxMana(entity))
				setMana(manaType, manaType.getMaxMana(entity));
		}
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		manaMap.replaceAll((color, d) -> tag.getDouble(color.getSerializedName()));
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		for(Map.Entry<ManaType, Double> entry : manaMap.entrySet())
			tag.putDouble(entry.getKey().getSerializedName(), entry.getValue());
	}

	public double getMana(ManaType manaType) {
		return manaMap.get(manaType);
	}

	public void setMana(ManaType manaType, double mana) {
		manaMap.put(manaType, Mth.clamp(mana, 0, manaType.getMaxMana(entity)));
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
