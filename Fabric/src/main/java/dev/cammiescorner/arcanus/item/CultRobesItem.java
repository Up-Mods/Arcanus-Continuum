package dev.cammiescorner.arcanus.item;

import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class CultRobesItem extends Item {
//	private final Supplier<ItemAttributeModifiers> defaultModifiers;

	// TODO move primal arcana to data component
	public CultRobesItem(Item.Properties properties, Supplier<PrimalArcana> primalArcana) {
		super(properties);

//		this.defaultModifiers = Suppliers.memoize(() -> {
//			ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
//			EquipmentSlotGroup equipmentSlotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
//			ResourceLocation resourceLocation = Arcanus.id("armor." + type.getName());
//			float knockbackResist = material.value().knockbackResistance();
//
//			builder.add(Attributes.ARMOR, new AttributeModifier(resourceLocation, material.value().getDefense(type), AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
//			builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourceLocation, material.value().toughness(), AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
//
//			if(knockbackResist > 0f)
//				builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourceLocation, knockbackResist, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
//
//			builder.add(ArcanusAttributes.AETHER_ARCANA.holder(), new AttributeModifier(resourceLocation, 10, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
//			builder.add(primalArcana.get().arcanaAttribute(), new AttributeModifier(resourceLocation, 10, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
//
//			return builder.build();
//		});
	}

	// fixme modifier display?
//	@Override
//	public ItemAttributeModifiers getDefaultAttributeModifiers() {
//		return defaultModifiers.get();
//	}
}
