package dev.cammiescorner.arcanus.data;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;

// TODO set enchantment values for all of these
public class ArcanusArmorMaterials {
	public static final ArmorMaterial ARCANIST = new ArmorMaterial(
		25,
		ArmorMaterials.makeDefense(1, 4, 5, 2, 5),
		10,
		SoundEvents.ARMOR_EQUIP_LEATHER,
		0f,
		0f,
		ArcanusTags.Items.REPAIRS_ARCANIST_ARMOR,
		ArcanusEquipmentAssets.ARCANIST_GEAR
	);
	public static final ArmorMaterial ARTIFICER = new ArmorMaterial(
		25,
		ArmorMaterials.makeDefense(2, 5, 6, 3, 6),
		10,
		SoundEvents.ARMOR_EQUIP_LEATHER,
		0f,
		0f,
		ArcanusTags.Items.REPAIRS_ARTIFICER_ARMOR,
		ArcanusEquipmentAssets.ARTIFICER_GEAR
	);
	public static final ArmorMaterial ALCHEMIST = new ArmorMaterial(
		25,
		ArmorMaterials.makeDefense(1, 4, 5, 2, 5),
		10,
		SoundEvents.ARMOR_EQUIP_LEATHER,
		0f,
		0f,
		ArcanusTags.Items.REPAIRS_ALCHEMIST_ARMOR,
		ArcanusEquipmentAssets.ALCHEMIST_GEAR
	);
	public static final ArmorMaterial CULTIST_CLERIC = new ArmorMaterial(
		25,
		ArmorMaterials.makeDefense(1, 4, 5, 2, 5),
		10,
		SoundEvents.ARMOR_EQUIP_LEATHER,
		0f,
		0f,
		ArcanusTags.Items.REPAIRS_CULTIST_CLERIC_ARMOR,
		ArcanusEquipmentAssets.CULTIST_CLERIC_GEAR
	);
	public static final ArmorMaterial CULTIST_KNIGHT = new ArmorMaterial(
		25,
		ArmorMaterials.makeDefense(2, 5, 7, 3, 7),
		10,
		SoundEvents.ARMOR_EQUIP_IRON,
		0f,
		0f,
		ArcanusTags.Items.REPAIRS_CULTIST_KNIGHT_ARMOR,
		ArcanusEquipmentAssets.CULTIST_KNIGHT_GEAR
	);
}
