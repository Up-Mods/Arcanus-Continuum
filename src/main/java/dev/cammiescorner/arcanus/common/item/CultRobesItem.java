package dev.cammiescorner.arcanus.common.item;

import com.google.common.base.Suppliers;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.arcana.ArcanaType;
import dev.cammiescorner.arcanus.common.registry.ArcanusAttributes;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.function.Supplier;

public class CultRobesItem extends ArmorItem {
	private final Supplier<ItemAttributeModifiers> defaultModifiers;

	public CultRobesItem(Holder<ArmorMaterial> holder, Type type, ArcanaType arcanaType) {
		super(holder, type, type == Type.HELMET ? new Properties().stacksTo(1).component(ArcanusDataComponents.HOOD_DOWN.get(), false) : new Properties().stacksTo(1));

		this.defaultModifiers = Suppliers.memoize(() -> {
			ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
			EquipmentSlotGroup equipmentSlotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
			ResourceLocation resourceLocation = Arcanus.id("armor." + type.getName());
			float knockbackResist = material.value().knockbackResistance();

			builder.add(Attributes.ARMOR, new AttributeModifier(resourceLocation, material.value().getDefense(type), AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
			builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourceLocation, material.value().toughness(), AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);

			if(knockbackResist > 0f)
				builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourceLocation, knockbackResist, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);

			builder.add(ArcanusAttributes.AETHER_ARCANA.holder(), new AttributeModifier(resourceLocation, 10, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
			builder.add(arcanaType.getArcanaAttribute(), new AttributeModifier(resourceLocation, 10, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);

			return builder.build();
		});
	}

	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers() {
		return defaultModifiers.get();
	}
}
