package dev.cammiescorner.arcanus.common.components.entity;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntityAttributes;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.HashMap;
import java.util.Map;

public class ManaComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity entity;
	private final Map<Color, Double> manaMap = new HashMap<>();
	private double mana;

	public ManaComponent(LivingEntity entity) {
		this.entity = entity;

		manaMap.putIfAbsent(Color.RED, 25d);
		manaMap.putIfAbsent(Color.GREEN, 25d);
		manaMap.putIfAbsent(Color.BLUE, 25d);
		manaMap.putIfAbsent(Color.WHITE, 25d);
		manaMap.putIfAbsent(Color.BLACK, 25d);
	}

	@Override
	public void serverTick() {
		AttributeInstance manaRegenAttr = entity.getAttribute(ArcanusEntityAttributes.MANA_REGEN.holder());

		if(manaRegenAttr != null)
			for(Color color : manaMap.keySet())
				addMana(color, manaRegenAttr.getValue() / (entity instanceof Player player && player.isCreative() ? 1 : 20), false);
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		manaMap.replaceAll((color, d) -> tag.getDouble(color.getSerializedName()));
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		for(Map.Entry<Color, Double> entry : manaMap.entrySet())
			tag.putDouble(entry.getKey().getSerializedName(), entry.getValue());
	}

	public double getMana(Color color) {
		return manaMap.get(color);
	}

	public void setMana(Color color, double mana) {
		manaMap.put(color, Mth.clamp(mana, 0, color.getMaxMana(entity)));
		ArcanusComponents.MANA_COMPONENT.sync(entity);
	}

	public double getTrueMaxMana(Color color) {
		return color.getMaxMana(entity) - getManaLock();
	}

	public double getManaLock() {
		AttributeInstance manaLockAttr = entity.getAttribute(ArcanusEntityAttributes.MANA_LOCK.holder());

		if(manaLockAttr != null)
			return manaLockAttr.getValue();

		return 0;
	}

	public boolean addMana(Color color, double amount, boolean simulate) {
		if(getMana(color) < getTrueMaxMana(color)) {
			if(!simulate)
				setMana(color, getMana(color) + amount);

			return true;
		}

		return false;
	}

	public boolean drainMana(Color color, double amount, boolean simulate) {
		AttributeInstance instance = entity.getAttribute(ArcanusEntityAttributes.MANA_COST.holder());

		if(instance != null)
			amount *= instance.getValue();

		if(getMana(color) >= 0 && getMana(color) >= amount) {
			if(!simulate)
				setMana(color, Math.max(0, getMana(color) - amount));

			return true;
		}

		return false;
	}

	public enum Color implements StringRepresentable {
		RED(ArcanusEntityAttributes.RED_MANA, "RedMana"),
		GREEN(ArcanusEntityAttributes.GREEN_MANA, "GreenMana"),
		BLUE(ArcanusEntityAttributes.BLUE_MANA, "BlueMana"),
		WHITE(ArcanusEntityAttributes.WHITE_MANA, "WhiteMana"),
		BLACK(ArcanusEntityAttributes.BLACK_MANA, "BlackMana");

		public static final Codec<Color> CODEC = StringRepresentable.fromValues(Color::values);
		final Holder<Attribute> attribute;
		final String serializedName;

		Color(RegistrySupplier<Attribute> attributeSupplier, String name) {
			this(attributeSupplier.holder(), name);
		}

		Color(Holder<Attribute> attribute, String name) {
			this.attribute = attribute;
			this.serializedName = name;
		}

		public Holder<Attribute> getAttribute() {
			return attribute;
		}

		public double getMaxMana(LivingEntity entity) {
			return entity.getAttributeValue(attribute);
		}

		@Override
		public String getSerializedName() {
			return serializedName;
		}
	}
}
