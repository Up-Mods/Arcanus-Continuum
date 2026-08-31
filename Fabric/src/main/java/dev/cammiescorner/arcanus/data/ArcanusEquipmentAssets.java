package dev.cammiescorner.arcanus.data;

import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

public class ArcanusEquipmentAssets {
	public static final ResourceKey<EquipmentAsset> ARCANIST_GEAR = asset("arcanist_gear");
	public static final ResourceKey<EquipmentAsset> ARTIFICER_GEAR = asset("artificer_gear");
	public static final ResourceKey<EquipmentAsset> ALCHEMIST_GEAR = asset("alchemist_gear");
	public static final ResourceKey<EquipmentAsset> CULTIST_CLERIC_GEAR = asset("cultist_cleric_gear");
	public static final ResourceKey<EquipmentAsset> CULTIST_KNIGHT_GEAR = asset("cultist_knight_gear");

	private static ResourceKey<EquipmentAsset> asset(String name) {
		return ResourceKey.create(EquipmentAssets.ROOT_ID, Arcanus.id(name));
	}
}
