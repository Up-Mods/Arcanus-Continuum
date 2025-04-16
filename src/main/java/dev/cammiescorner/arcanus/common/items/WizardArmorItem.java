package dev.cammiescorner.arcanus.common.items;

import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.UUID;

public class WizardArmorItem extends ArmorItem {
	private static final Map<Type, UUID> MODIFIER_IDS = Map.of(
		Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
		Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
		Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
		Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
	);

	public WizardArmorItem(Holder<ArmorMaterial> armorMaterial, Type equipmentSlot, double manaRegen, double magicResist, double spellPotency, double manaCostMultiplier, double spellCoolDown) {
		super(armorMaterial, equipmentSlot, new Item.Properties().stacksTo(1));

		CauldronInteraction.WATER.map().put(this, CauldronInteraction.DYED_ITEM);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		double manaDrain = 1;

		if(!world.isClientSide() && stack.isDamaged() && entity instanceof LivingEntity livingEntity && livingEntity.getItemBySlot(getEquipmentSlot()) == stack && ArcanusComponents.getMana(livingEntity) >= manaDrain && ArcanusComponents.drainMana(livingEntity, manaDrain, false))
			stack.setDamageValue(stack.getDamageValue() - 1);
	}

	// TODO oh dear gods they made it more confusing
//	public static ItemAttributeModifiers createAttributes() {
//		return ItemAttributeModifiers.builder()
//			.add(Attributes.ARMOR, new AttributeModifier(modifierID, armorMaterial.getDefense(equipmentSlot), AttributeModifier.Operation.ADD_VALUE))
//			.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(modifierID, armorMaterial.toughness(), AttributeModifier.Operation.ADD_VALUE))
//			.add(ArcanusEntityAttributes.MANA_REGEN.get(), new AttributeModifier(modifierID, manaRegen, AttributeModifier.Operation.ADD_VALUE))
//			.add(ArcanusEntityAttributes.MAGIC_RESISTANCE.get(), new AttributeModifier(modifierID, magicResist, AttributeModifier.Operation.ADD_MULTIPLIED_BASE))
//			.add(ArcanusEntityAttributes.SPELL_POTENCY.get(), new AttributeModifier(modifierID, spellPotency, AttributeModifier.Operation.ADD_MULTIPLIED_BASE))
//			.add(ArcanusEntityAttributes.MANA_COST.get(), new AttributeModifier(modifierID, manaCostMultiplier, AttributeModifier.Operation.ADD_MULTIPLIED_BASE))
//			.add(ArcanusEntityAttributes.SPELL_COOL_DOWN.get(), new AttributeModifier(modifierID, spellCoolDown, AttributeModifier.Operation.ADD_MULTIPLIED_BASE))
//			.build();
//	}
}
