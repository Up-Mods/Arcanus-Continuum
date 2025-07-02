package dev.cammiescorner.arcanus.common.item;

import com.google.common.base.Suppliers;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spells.mana.ManaType;
import dev.cammiescorner.arcanus.common.registry.ArcanusAttributes;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class WizardArmorItem extends ArmorItem {
	private static final Map<Type, UUID> MODIFIER_IDS = Map.of(
		Type.BOOTS, UUID.fromString("845DB27C-C624-495f-8C9f-6020A9A58B6B"),
		Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
		Type.CHESTPLATE, UUID.fromString("9f3D476D-C118-4544-8365-64846904B48E"),
		Type.HELMET, UUID.fromString("2AD3f246-FEE1-4E67-B886-69fD380BB150")
	);
	private final Supplier<ItemAttributeModifiers> defaultModifiers;

	public WizardArmorItem(Holder<ArmorMaterial> armorMaterial, Type equipmentSlot, double manaRegen, double magicResist, double spellPotency, double manaCostMultiplier, double spellCoolDown) {
		super(armorMaterial, equipmentSlot, new Item.Properties().stacksTo(1));

		this.defaultModifiers = Suppliers.memoize(() -> {
			ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
			EquipmentSlotGroup equipmentSlotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
			ResourceLocation resourceLocation = Arcanus.id("armor." + type.getName());
			float knockbackResist = material.value().knockbackResistance();

			builder.add(Attributes.ARMOR, new AttributeModifier(resourceLocation, material.value().getDefense(type), AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
			builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourceLocation, material.value().toughness(), AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);

			if(knockbackResist > 0f)
				builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourceLocation, knockbackResist, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);

			builder.add(ArcanusAttributes.MANA_REGEN.holder(), new AttributeModifier(resourceLocation, manaRegen, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
			builder.add(ArcanusAttributes.MAGIC_RESISTANCE.holder(), new AttributeModifier(resourceLocation, magicResist, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), equipmentSlotGroup);
			builder.add(ArcanusAttributes.SPELL_POTENCY.holder(), new AttributeModifier(resourceLocation, spellPotency, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), equipmentSlotGroup);
			builder.add(ArcanusAttributes.MANA_COST.holder(), new AttributeModifier(resourceLocation, manaCostMultiplier, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), equipmentSlotGroup);
			builder.add(ArcanusAttributes.SPELL_COOL_DOWN.holder(), new AttributeModifier(resourceLocation, spellCoolDown, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), equipmentSlotGroup);

			return builder.build();
		});

		CauldronInteraction.WATER.map().put(this, CauldronInteraction.DYED_ITEM);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		double manaDrain = 1;

		if(!world.isClientSide() && stack.isDamaged() && entity instanceof LivingEntity livingEntity && livingEntity.getItemBySlot(getEquipmentSlot()) == stack && ArcanusComponents.drainMana(livingEntity, ManaType.WHITE, manaDrain, false))
			stack.setDamageValue(stack.getDamageValue() - 1);
	}

	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers() {
		return defaultModifiers.get();
	}
}
