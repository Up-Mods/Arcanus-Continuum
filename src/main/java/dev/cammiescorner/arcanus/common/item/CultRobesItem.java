package dev.cammiescorner.arcanus.common.item;

import com.google.common.base.Suppliers;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.util.TranslationKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;
import java.util.function.Supplier;

public class CultRobesItem extends ArmorItem {
	public static final ResourceLocation FULL_SET_BONUS = Arcanus.id("full_set_bonus");
	private final Supplier<ItemAttributeModifiers> defaultModifiers;
	private final ManaType manaType;

	public CultRobesItem(Holder<ArmorMaterial> holder, Type type, ManaType manaType) {
		super(holder, type, type == Type.HELMET ? new Properties().stacksTo(1).component(ArcanusDataComponents.HOOD_DOWN.get(), false) : new Properties().stacksTo(1));

		this.manaType = manaType;

		this.defaultModifiers = Suppliers.memoize(() -> {
			ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
			EquipmentSlotGroup equipmentSlotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
			ResourceLocation resourceLocation = Arcanus.id("armor." + type.getName());
			float knockbackResist = material.value().knockbackResistance();

			builder.add(Attributes.ARMOR, new AttributeModifier(resourceLocation, material.value().getDefense(type), AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);
			builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourceLocation, material.value().toughness(), AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);

			if(knockbackResist > 0f)
				builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourceLocation, knockbackResist, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);

			builder.add(manaType.getAttribute(), new AttributeModifier(resourceLocation, 10, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup);

			return builder.build();
		});
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		tooltipComponents.add(Component.translatable(TranslationKeys.SET_BONUS, Component.translatable(manaType.getTranslationKey())).withStyle(ChatFormatting.YELLOW));
	}

	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers() {
		return defaultModifiers.get();
	}

	public Holder<Attribute> getAttribute() {
		return manaType.getAttribute();
	}

	public ManaType getManaType() {
		return manaType;
	}

	public static boolean isWearingFullSet(LivingEntity entity) {
		ItemStack headStack = entity.getItemBySlot(EquipmentSlot.HEAD);
		ItemStack chestStack = entity.getItemBySlot(EquipmentSlot.CHEST);
		ItemStack legsStack = entity.getItemBySlot(EquipmentSlot.LEGS);
		ItemStack bootsStack = entity.getItemBySlot(EquipmentSlot.FEET);

		if(headStack.getItem() instanceof CultRobesItem hood && chestStack.getItem() instanceof CultRobesItem robes && legsStack.getItem() instanceof CultRobesItem pants && bootsStack.getItem() instanceof CultRobesItem boots)
			return hood.manaType == robes.manaType && hood.manaType == pants.manaType && hood.manaType == boots.manaType;

		return false;
	}
}
