package dev.cammiescorner.arcanus.common.item;

import com.google.common.base.Suppliers;
import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.core.Holder;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.function.Supplier;

public class WizardArmorItem extends ArmorItem {
	private final Supplier<ItemAttributeModifiers> defaultModifiers;

	public WizardArmorItem(Holder<ArmorMaterial> armorMaterial, Type equipmentSlot) {
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

			return builder.build();
		});

		CauldronInteraction.WATER.map().put(this, CauldronInteraction.DYED_ITEM);
	}

	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers() {
		return defaultModifiers.get();
	}
}
