package dev.cammiescorner.arcanus.common.items;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.util.StaffType;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static dev.cammiescorner.arcanus.common.util.TranslationKeys.*;

public class StaffItem extends Item {
	public final StaffType staffType;
	public final Color defaultPrimaryColor;
	public final Color defaultSecondaryColor;
	public final boolean isDonorOnly;

	public StaffItem(StaffType staffType, Color defaultPrimaryColor, Color defaultSecondaryColor) {
		this(staffType, defaultPrimaryColor, defaultSecondaryColor, false);
	}

	public StaffItem(StaffType staffType, Color defaultPrimaryColor, Color defaultSecondaryColor, boolean isDonorOnly) {
		super(new Item.Properties().stacksTo(1).component(ArcanusDataComponents.SPELL_LIST.get(), NonNullList.withSize(8, new Spell())).component(ArcanusDataComponents.PRIMARY_COLOR.get(), defaultPrimaryColor).component(ArcanusDataComponents.SECONDARY_COLOR.get(), defaultSecondaryColor));
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
		List<Spell> spells = stack.getOrDefault(ArcanusDataComponents.SPELL_LIST.get(), NonNullList.withSize(8, new Spell()));
		int primaryColour = getPrimaryColorRGB(stack);
		int secondaryColour = getSecondaryColorRGB(stack);

		tooltip.add(Component.translatable(STAFF_PRIMARY_COLOR).withStyle(style -> style.withColor(primaryColour)).append(Component.literal(": " + String.format(Locale.ROOT, "#%06x", primaryColour & 0xffffff)).withStyle(ChatFormatting.GRAY)));
		tooltip.add(Component.translatable(STAFF_SECONDARY_COLOR).withStyle(style -> style.withColor(secondaryColour)).append(Component.literal(": " + String.format(Locale.ROOT, "#%06x", secondaryColour & 0xffffff)).withStyle(ChatFormatting.GRAY)));
		tooltip.add(Component.empty());

		for(int i = 0; i < spells.size(); i++) {
			Spell spell = spells.get(i);

			if(spell.getComponentGroups().isEmpty()) {
				tooltip.add(Component.translatable(STAFF_INVALID_DATA).withStyle(ChatFormatting.DARK_RED));
				return;
			}

			MutableComponent text = Component.literal(spell.getName()).withStyle(spell.isEmpty() ? ChatFormatting.GRAY : ChatFormatting.GREEN);
			tooltip.add(text.append(Component.literal(" (").withStyle(ChatFormatting.DARK_GRAY))
				.append(Arcanus.getSpellPatternAsText(i).withStyle(ChatFormatting.GRAY))
				.append(Component.literal(")").withStyle(ChatFormatting.DARK_GRAY)));
		}
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level world, BlockPos pos, Player miner) {
		return false;
	}

	public static ItemAttributeModifiers createAttributes() {
		return ItemAttributeModifiers.builder()
			.add(Attributes.ATTACK_SPEED, new AttributeModifier(Arcanus.id("attack_speed_modifier"), -1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(Arcanus.id("entity_interact_range_modifier"), 0.5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
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
