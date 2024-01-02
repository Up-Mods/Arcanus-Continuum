package dev.cammiescorner.arcanuscontinuum.common.items;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class WizardArmorItem extends DyeableArmorItem {
	private static final Map<ArmorSlot, UUID> MODIFIER_IDS = Map.of(
		ArmorSlot.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
		ArmorSlot.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
		ArmorSlot.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
		ArmorSlot.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
	);
	private final Supplier<Multimap<EntityAttribute, EntityAttributeModifier>> attributeModifiers;

	public WizardArmorItem(ArmorMaterial armorMaterial, ArmorSlot equipmentSlot, double manaRegen, double magicResist, double spellPotency) {
		super(armorMaterial, equipmentSlot, new QuiltItemSettings().maxCount(1));

		this.attributeModifiers = Suppliers.memoize(() -> {
			UUID modifierID = MODIFIER_IDS.get(equipmentSlot);
			return ImmutableMultimap.<EntityAttribute, EntityAttributeModifier>builder()
				.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(modifierID, "Armor modifier", armorMaterial.getProtection(equipmentSlot), EntityAttributeModifier.Operation.ADDITION))
				.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(modifierID, "Armor toughness", armorMaterial.getToughness(), EntityAttributeModifier.Operation.ADDITION))
				.put(ArcanusEntityAttributes.MANA_REGEN.get(), new EntityAttributeModifier(modifierID, "Armor modifier", manaRegen, EntityAttributeModifier.Operation.ADDITION))
				.put(ArcanusEntityAttributes.MAGIC_RESISTANCE.get(), new EntityAttributeModifier(modifierID, "Armor modifier", magicResist, EntityAttributeModifier.Operation.MULTIPLY_BASE))
				.put(ArcanusEntityAttributes.SPELL_POTENCY.get(), new EntityAttributeModifier(modifierID, "Armor modifier", spellPotency, EntityAttributeModifier.Operation.MULTIPLY_BASE))
				.build();
		});

		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(this, CauldronBehavior.CLEAN_DYEABLE_ITEM);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);

		if(!world.isClient() && stack.isDamaged() && entity instanceof LivingEntity livingEntity && ArcanusComponents.drainMana(livingEntity, 1, false))
			stack.setDamage(stack.getDamage() - 1);
	}

	@Override
	public int getColor(ItemStack stack) {
		NbtCompound tag = stack.getSubNbt("display");
		return tag != null && tag.contains("color", 99) ? tag.getInt("color") : 0x52392a;
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		return slot == getPreferredSlot() ? attributeModifiers.get() : super.getAttributeModifiers(slot);
	}
}
