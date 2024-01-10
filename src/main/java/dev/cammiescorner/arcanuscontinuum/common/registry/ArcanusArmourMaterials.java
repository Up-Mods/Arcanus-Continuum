package dev.cammiescorner.arcanuscontinuum.common.registry;

import com.mojang.serialization.Codec;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ArcanusArmourMaterials implements StringIdentifiable, ArmorMaterial {
	WIZARD("wizard", 5, Util.make(new EnumMap<>(ArmorItem.ArmorSlot.class), (map) -> {
		map.put(ArmorItem.ArmorSlot.BOOTS, 1);
		map.put(ArmorItem.ArmorSlot.LEGGINGS, 2);
		map.put(ArmorItem.ArmorSlot.CHESTPLATE, 3);
		map.put(ArmorItem.ArmorSlot.HELMET, 1);
	}), 25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0f, 0f, () -> Ingredient.ofItems(Items.LEATHER)),
	BATTLE_MAGE("battle_mage", 35, Util.make(new EnumMap<>(ArmorItem.ArmorSlot.class), (map) -> {
		map.put(ArmorItem.ArmorSlot.BOOTS, 2);
		map.put(ArmorItem.ArmorSlot.LEGGINGS, 6);
		map.put(ArmorItem.ArmorSlot.CHESTPLATE, 8);
		map.put(ArmorItem.ArmorSlot.HELMET, 3);
	}), 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0f, 0f, () -> Ingredient.ofItems(Items.AMETHYST_SHARD));

	public static final Codec<ArmorMaterials> CODEC = StringIdentifiable.createCodec(ArmorMaterials::values);
	private static final EnumMap<ArmorItem.ArmorSlot, Integer> BASE_DURABILITY_VALUES = Util.make(new EnumMap<>(ArmorItem.ArmorSlot.class), (map) -> {
		map.put(ArmorItem.ArmorSlot.BOOTS, 13);
		map.put(ArmorItem.ArmorSlot.LEGGINGS, 15);
		map.put(ArmorItem.ArmorSlot.CHESTPLATE, 16);
		map.put(ArmorItem.ArmorSlot.HELMET, 11);
	});
	private final String name;
	private final int durabilityMultiplier;
	private final EnumMap<ArmorItem.ArmorSlot, Integer> slotProtections;
	private final int enchantability;
	private final SoundEvent equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private final Lazy<Ingredient> repairIngredientSupplier;

	ArcanusArmourMaterials(String name, int durability, EnumMap<ArmorItem.ArmorSlot, Integer> slotProtections, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> supplier) {
		this.name = name;
		this.durabilityMultiplier = durability;
		this.slotProtections = slotProtections;
		this.enchantability = enchantability;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredientSupplier = new Lazy<>(supplier);
	}

	@Override
	public int getDurability(ArmorItem.ArmorSlot slot) {
		return BASE_DURABILITY_VALUES.get(slot) * durabilityMultiplier;
	}

	@Override
	public int getProtection(ArmorItem.ArmorSlot slot) {
		return slotProtections.get(slot);
	}

	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	@Override
	public SoundEvent getEquipSound() {
		return this.equipSound;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredientSupplier.get();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}

	@Override
	public String asString() {
		return name;
	}
}
