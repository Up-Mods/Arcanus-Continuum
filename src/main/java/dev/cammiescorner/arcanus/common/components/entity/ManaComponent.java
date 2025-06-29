package dev.cammiescorner.arcanus.common.components.entity;

import dev.cammiescorner.arcanus.api.spells.ManaColor;
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
	private final Map<ManaColor, Double> manaMap = new HashMap<>();
	private double mana;

	public ManaComponent(LivingEntity entity) {
		this.entity = entity;

		manaMap.putIfAbsent(ManaColor.RED, 25d);
		manaMap.putIfAbsent(ManaColor.GREEN, 25d);
		manaMap.putIfAbsent(ManaColor.BLUE, 25d);
		manaMap.putIfAbsent(ManaColor.WHITE, 25d);
		manaMap.putIfAbsent(ManaColor.BLACK, 25d);
	}

	@Override
	public void serverTick() {
		AttributeInstance manaRegenAttr = entity.getAttribute(ArcanusAttributes.MANA_REGEN.holder());

		for(ManaColor manaColor : manaMap.keySet()) {
			if(manaRegenAttr != null)
				addMana(manaColor, manaRegenAttr.getValue() / (entity instanceof Player player && player.isCreative() ? 1 : 20), false);

			if(getMana(manaColor) > manaColor.getMaxMana(entity))
				setMana(manaColor, manaColor.getMaxMana(entity));
		}
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		manaMap.replaceAll((color, d) -> tag.getDouble(color.getSerializedName()));
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		for(Map.Entry<ManaColor, Double> entry : manaMap.entrySet())
			tag.putDouble(entry.getKey().getSerializedName(), entry.getValue());
	}

	public double getMana(ManaColor manaColor) {
		return manaMap.get(manaColor);
	}

	public void setMana(ManaColor manaColor, double mana) {
		manaMap.put(manaColor, Mth.clamp(mana, 0, manaColor.getMaxMana(entity)));
		ArcanusComponents.MANA_COMPONENT.sync(entity);
	}

	public double getTrueMaxMana(ManaColor manaColor) {
		return manaColor.getMaxMana(entity) - getManaLock();
	}

	public double getManaLock() {
		AttributeInstance manaLockAttr = entity.getAttribute(ArcanusAttributes.MANA_LOCK.holder());

		if(manaLockAttr != null)
			return manaLockAttr.getValue();

		return 0;
	}

	public boolean addMana(ManaColor manaColor, double amount, boolean simulate) {
		if(getMana(manaColor) < getTrueMaxMana(manaColor)) {
			if(!simulate)
				setMana(manaColor, getMana(manaColor) + amount);

			return true;
		}

		return false;
	}

	public boolean drainMana(ManaColor manaColor, double amount, boolean simulate) {
		AttributeInstance instance = entity.getAttribute(ArcanusAttributes.MANA_COST.holder());

		if(instance != null)
			amount *= instance.getValue();

		if(getMana(manaColor) >= 0 && getMana(manaColor) >= amount) {
			if(!simulate)
				setMana(manaColor, Math.max(0, getMana(manaColor) - amount));

			return true;
		}

		return false;
	}
}
