package dev.cammiescorner.arcanus.common.item;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import dev.cammiescorner.arcanus.common.util.StaffType;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;
import java.util.UUID;

import static dev.cammiescorner.arcanus.common.util.TranslationKeys.STAFF_PRIMARY_COLOR;
import static dev.cammiescorner.arcanus.common.util.TranslationKeys.STAFF_SECONDARY_COLOR;

public class StaffItem extends Item {
	public final StaffType staffType;
	public final Color defaultPrimaryColor;
	public final Color defaultSecondaryColor;
	public final boolean isDonorOnly;

	public StaffItem(StaffType staffType, Color defaultPrimaryColor, Color defaultSecondaryColor) {
		this(staffType, defaultPrimaryColor, defaultSecondaryColor, false);
	}

	public StaffItem(StaffType staffType, Color defaultPrimaryColor, Color defaultSecondaryColor, boolean isDonorOnly) {
		super(new Item.Properties().stacksTo(1).attributes(createAttributes()).component(ArcanusDataComponents.PRIMARY_COLOR.get(), defaultPrimaryColor).component(ArcanusDataComponents.SECONDARY_COLOR.get(), defaultSecondaryColor));
		this.staffType = staffType;
		this.defaultPrimaryColor = defaultPrimaryColor;
		this.defaultSecondaryColor = defaultSecondaryColor;
		this.isDonorOnly = isDonorOnly;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
		int primaryColor = getPrimaryColorRGB(stack);
		int secondaryColor = getSecondaryColorRGB(stack);

		tooltip.add(Component.translatable(STAFF_PRIMARY_COLOR, ArcanusHelper.formatColorRGB(primaryColor).withStyle(ChatFormatting.GRAY)).withColor(primaryColor));
		tooltip.add(Component.translatable(STAFF_SECONDARY_COLOR, ArcanusHelper.formatColorRGB(secondaryColor).withStyle(ChatFormatting.GRAY)).withColor(secondaryColor));
	}

	public static ItemAttributeModifiers createAttributes() {
		return ItemAttributeModifiers.builder()
			.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Arcanus.id("staff_attack_damage"), 3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ATTACK_SPEED, new AttributeModifier(Arcanus.id("staff_attack_speed"), -1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(Arcanus.id("staff_reach"), 0.5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.build();
	}

	public static void setPrimaryColor(ItemStack stack, Color color) {
		stack.set(ArcanusDataComponents.PRIMARY_COLOR.get(), color);
	}

	public static Color getPrimaryColor(ItemStack stack) {
		return stack.get(ArcanusDataComponents.PRIMARY_COLOR.get());
	}

	public static int getPrimaryColorRGB(ItemStack stack) {
		return getPrimaryColor(stack).asInt(Color.Ordering.ARGB);
	}

	public static void setSecondaryColor(ItemStack stack, Color color) {
		stack.set(ArcanusDataComponents.SECONDARY_COLOR.get(), color);
	}

	public static Color getSecondaryColor(ItemStack stack) {
		return stack.get(ArcanusDataComponents.SECONDARY_COLOR.get());
	}

	public static int getSecondaryColorRGB(ItemStack stack) {
		return getSecondaryColor(stack).asInt(Color.Ordering.ARGB);
	}

	public static ItemStack setCraftedBy(ItemStack stack, UUID uuid) {
		stack.set(ArcanusDataComponents.OWNER_ID.get(), uuid);
		return stack;
	}
}
