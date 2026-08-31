package dev.cammiescorner.arcanus.component.entity;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.cammiescorner.arcanus.registry.ArcanusArcana;
import dev.cammiescorner.arcanus.registry.ArcanusAttributes;
import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.HashMap;
import java.util.Map;

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
	public void readData(ValueInput readView) {
		resetArcana();

		var map = readView.read("ArcanaValues", Codec.unboundedMap(Codec.STRING, Codec.DOUBLE)).orElse(Map.of());

		map.forEach((s, aDouble) -> {
			if(ArcanusArcana.REGISTRY.getValue(Identifier.parse(s)) instanceof PrimalArcana primalArcana)
				arcanaMap.put(primalArcana, (double) aDouble);
		});
	}

	@Override
	public void writeData(ValueOutput writeView) {
		Map<String, Double> map = new HashMap<>();

		arcanaMap.forEach((primalArcana, aDouble) -> map.put(ArcanusArcana.REGISTRY.getKey(primalArcana).toString(), aDouble));

		writeView.store("ArcanaValues", Codec.unboundedMap(Codec.STRING, Codec.DOUBLE), map);
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
