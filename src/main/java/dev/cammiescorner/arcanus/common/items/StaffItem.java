package dev.cammiescorner.arcanus.common.items;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.util.StaffType;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
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
import java.util.UUID;

public class StaffItem extends Item {
	public final StaffType staffType;
	public final Color defaultPrimaryColor;
	public final Color defaultSecondaryColor;
	public final boolean isDonorOnly;

	// TODO add data component for attached spell books
	public StaffItem(StaffType staffType, Color defaultPrimaryColor, Color defaultSecondaryColor) {
		this(staffType, defaultPrimaryColor, defaultSecondaryColor, false);
	}

	public StaffItem(StaffType staffType, Color defaultPrimaryColor, Color defaultSecondaryColor, boolean isDonorOnly) {
		super(new Item.Properties().stacksTo(1).component(ArcanusDataComponents.PRIMARY_COLOR.get(), defaultPrimaryColor).component(ArcanusDataComponents.SECONDARY_COLOR.get(), defaultSecondaryColor).attributes(createAttributes()));
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
	public void onCraftedBy(ItemStack stack, Level world, Player player) {
		if(!world.isClientSide()) {
//			CompoundTag tag = stack.getOrCreateTagElement(Arcanus.MOD_ID);
//
//			if(tag.isEmpty()) {
//				ListTag list = new ListTag();
//
//				for(int i = 0; i < 8; i++)
//					list.add(i, new Spell().toNbt());
//
//				tag.put("Spells", list);
//			}
		}

		super.onCraftedBy(stack, world, player);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if(!world.isClientSide()) {
//			CompoundTag tag = stack.getOrCreateTagElement(Arcanus.MOD_ID);
//
//			if(tag.isEmpty()) {
//				ListTag list = new ListTag();
//
//				for(int i = 0; i < 8; i++)
//					list.add(i, new Spell().toNbt());
//
//				tag.put("Spells", list);
//			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
//		CompoundTag tag = stack.getTagElement(Arcanus.MOD_ID);
//		int primaryColour = getPrimaryColorRGB(stack);
//		int secondaryColour = getSecondaryColorRGB(stack);
//
//		tooltip.add(Component.translatable("staff.arcanus.primary_color").withStyle(style -> style.withColor(primaryColour)).append(Component.literal(": " + String.format(Locale.ROOT, "#%06X", primaryColour)).withStyle(ChatFormatting.GRAY)));
//		tooltip.add(Component.translatable("staff.arcanus.secondary_color").withStyle(style -> style.withColor(secondaryColour)).append(Component.literal(": " + String.format(Locale.ROOT, "#%06X", secondaryColour)).withStyle(ChatFormatting.GRAY)));
//		tooltip.add(Component.empty());
//
//		if(tag != null && !tag.isEmpty()) {
//			ListTag list = tag.getList("Spells", Tag.TAG_COMPOUND);
//
//			for(int i = 0; i < list.size(); i++) {
//				Spell spell = Spell.fromNbt(list.getCompound(i));
//
//				if(spell.getComponentGroups().isEmpty()) {
//					tooltip.add(Component.translatable("staff.arcanus.invalid_data").withStyle(ChatFormatting.DARK_RED));
//					return;
//				}
//
//				MutableComponent text = Component.literal(spell.getName()).withStyle(spell.isEmpty() ? ChatFormatting.GRAY : ChatFormatting.GREEN);
//				tooltip.add(text.append(Component.literal(" (").withStyle(ChatFormatting.DARK_GRAY)).append(Arcanus.getSpellPatternAsText(i).withStyle(ChatFormatting.GRAY)).append(Component.literal(")").withStyle(ChatFormatting.DARK_GRAY)));
//			}
//		}
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
		return getPrimaryColor(stack).asInt(Color.Ordering.RGB);
	}

	public static void setSecondaryColor(ItemStack stack, Color color) {
		stack.set(ArcanusDataComponents.SECONDARY_COLOR.get(), color);
	}

	public static Color getSecondaryColor(ItemStack stack) {
		return stack.get(ArcanusDataComponents.SECONDARY_COLOR.get());
	}

	public static int getSecondaryColorRGB(ItemStack stack) {
		return getSecondaryColor(stack).asInt(Color.Ordering.RGB);
	}

	public static ItemStack setCraftedBy(ItemStack stack, UUID uuid) {
		stack.set(ArcanusDataComponents.OWNER_ID.get(), uuid);
		return stack;
	}
}
