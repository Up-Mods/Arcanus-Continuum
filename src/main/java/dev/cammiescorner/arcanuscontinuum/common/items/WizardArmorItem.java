package dev.cammiescorner.arcanuscontinuum.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.UUID;

public class WizardArmorItem extends DyeableArmorItem {
	private static final UUID[] MODIFIERS = new UUID[]{
			UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
			UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
			UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
			UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
	};
	private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

	public WizardArmorItem(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, double manaRegen) {
		super(armorMaterial, equipmentSlot, new QuiltItemSettings().group(Arcanus.ITEM_GROUP).maxCount(1));
		ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		UUID uUID = MODIFIERS[equipmentSlot.getEntitySlotId()];
		builder.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uUID, "Armor modifier", armorMaterial.getProtectionAmount(equipmentSlot), EntityAttributeModifier.Operation.ADDITION));
		builder.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(uUID, "Armor toughness", armorMaterial.getToughness(), EntityAttributeModifier.Operation.ADDITION));
		builder.put(ArcanusEntityAttributes.MANA_REGEN, new EntityAttributeModifier(uUID, "Armor modifier", manaRegen, EntityAttributeModifier.Operation.MULTIPLY_BASE));
		attributeModifiers = builder.build();
	}

	@Override
	public int getColor(ItemStack stack) {
		NbtCompound nbtCompound = stack.getSubNbt("display");
		return nbtCompound != null && nbtCompound.contains("color", 99) ? nbtCompound.getInt("color") : 0x52392a;
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		return slot == this.slot ? attributeModifiers : super.getAttributeModifiers(slot);
	}
}
