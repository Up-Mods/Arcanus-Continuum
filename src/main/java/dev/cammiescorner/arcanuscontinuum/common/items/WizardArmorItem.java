package dev.cammiescorner.arcanuscontinuum.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
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

	public WizardArmorItem(ArmorMaterial armorMaterial, ArmorSlot equipmentSlot, double manaRegen, double magicResist, double spellPotency) {
		super(armorMaterial, equipmentSlot, new QuiltItemSettings().maxCount(1));
		ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		UUID uUID = MODIFIERS[equipmentSlot.getEquipmentSlot().getEntitySlotId()];
		builder.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uUID, "Armor modifier", armorMaterial.getProtection(equipmentSlot), EntityAttributeModifier.Operation.ADDITION));
		builder.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(uUID, "Armor toughness", armorMaterial.getToughness(), EntityAttributeModifier.Operation.ADDITION));
		builder.put(ArcanusEntityAttributes.MANA_REGEN, new EntityAttributeModifier(uUID, "Armor modifier", manaRegen, EntityAttributeModifier.Operation.MULTIPLY_BASE));
		builder.put(ArcanusEntityAttributes.MAGIC_RESISTANCE, new EntityAttributeModifier(uUID, "Armor modifier", magicResist, EntityAttributeModifier.Operation.MULTIPLY_BASE));
		builder.put(ArcanusEntityAttributes.SPELL_POTENCY, new EntityAttributeModifier(uUID, "Armor modifier", spellPotency, EntityAttributeModifier.Operation.MULTIPLY_BASE));
		attributeModifiers = builder.build();
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		NbtCompound tag = stack.getOrCreateSubNbt("display");

		if(!world.isClient() && entity instanceof PlayerEntity player && player.getUuidAsString().equals("1b44461a-f605-4b29-a7a9-04e649d1981c") && !tag.contains("color", 99))
			tag.putInt("color", 0xff005a);
	}

	@Override
	public int getColor(ItemStack stack) {
		NbtCompound tag = stack.getSubNbt("display");
		return tag != null && tag.contains("color", 99) ? tag.getInt("color") : 0x52392a;
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		return slot == getPreferredSlot() ? attributeModifiers : super.getAttributeModifiers(slot);
	}
}
