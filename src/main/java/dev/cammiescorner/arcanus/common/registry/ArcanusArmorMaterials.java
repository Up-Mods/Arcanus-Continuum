package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;

public class ArcanusArmorMaterials {
	public static final RegistryHandler<ArmorMaterial> MATERIALS = RegistryHandler.create(Registries.ARMOR_MATERIAL, Arcanus.MOD_ID);

	public static final RegistrySupplier<ArmorMaterial> WIZARD = MATERIALS.register("wizard", () -> new ArmorMaterial(
		Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
			map.put(ArmorItem.Type.BOOTS, 1);
			map.put(ArmorItem.Type.LEGGINGS, 4);
			map.put(ArmorItem.Type.CHESTPLATE, 5);
			map.put(ArmorItem.Type.HELMET, 2);
			map.put(ArmorItem.Type.BODY, 5);
		}),
		25,
		SoundEvents.ARMOR_EQUIP_LEATHER,
		() -> Ingredient.of(Items.LEATHER),
		List.of(),
		0f,
		0f
	));
	public static final RegistrySupplier<ArmorMaterial> BATTLE_MAGE = MATERIALS.register("battle_mage", () -> new ArmorMaterial(
		Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
			map.put(ArmorItem.Type.BOOTS, 2);
			map.put(ArmorItem.Type.LEGGINGS, 7);
			map.put(ArmorItem.Type.CHESTPLATE, 8);
			map.put(ArmorItem.Type.HELMET, 3);
			map.put(ArmorItem.Type.BODY, 8);
		}),
		25,
		SoundEvents.ARMOR_EQUIP_IRON,
		() -> Ingredient.of(Items.AMETHYST_SHARD),
		List.of(),
		0f,
		0f
	));
}
