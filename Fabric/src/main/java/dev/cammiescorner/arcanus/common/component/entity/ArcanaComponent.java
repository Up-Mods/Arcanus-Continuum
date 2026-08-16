package dev.cammiescorner.arcanus.common.component.entity;

import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusArcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusAttributes;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class ArcanaComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity entity;
	private final Object2DoubleMap<PrimalArcana> arcanaMap = new Object2DoubleArrayMap<>();

	public ArcanaComponent(LivingEntity entity) {
		this.entity = entity;

		resetArcana();
	}

	@Override
	public void serverTick() {
		for(PrimalArcana primalArcana : arcanaMap.keySet()) {
			if(entity instanceof Player player && player.isCreative() && getArcana(primalArcana) < primalArcana.getMaxArcana(entity))
				addArcana(primalArcana, 1, false);
			else if(getArcana(primalArcana) < primalArcana.getMaxArcana(entity) && entity.getAttributeValue(primalArcana.regenAttribute()) > 0)
				addArcana(primalArcana, entity.getAttributeValue(primalArcana.regenAttribute()) / 20, false);

			if(getArcana(primalArcana) > primalArcana.getMaxArcana(entity))
				setArcana(primalArcana, primalArcana.getMaxArcana(entity));
		}
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		ListTag list = tag.getList("ArcanaValues", Tag.TAG_COMPOUND);
		resetArcana();

		for(int i = 0; i < list.size(); i++) {
			CompoundTag compoundTag = list.getCompound(i);
			Arcana arcana = ArcanusArcana.REGISTRY.get(ResourceLocation.parse(compoundTag.getString("PrimalArcana")));

			if(arcana instanceof PrimalArcana primalArcana)
				arcanaMap.put(primalArcana, compoundTag.getDouble("Value"));
		}
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		ListTag list = new ListTag();

		arcanaMap.forEach((primalArcana, aDouble) -> {
			CompoundTag compoundTag = new CompoundTag();

			compoundTag.putString("PrimalArcana", ArcanusArcana.REGISTRY.getKey(primalArcana).toString());
			compoundTag.putDouble("Value", aDouble);

			list.add(compoundTag);
		});

		tag.put("ArcanaValues", list);
	}

	public double getArcana(PrimalArcana primalArcana) {
		return arcanaMap.getOrDefault(primalArcana, 20d);
	}

	public void setArcana(PrimalArcana primalArcana, double amount) {
		arcanaMap.put(primalArcana, Mth.clamp(amount, 0, primalArcana.getMaxArcana(entity)));
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

	private void resetArcana() {
		ArcanusArcana.primalArcana().forEach(primalArcana -> arcanaMap.put(primalArcana, 20d));
	}
}
