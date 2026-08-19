package dev.cammiescorner.arcanus.common.item;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.data_component.StaffParts;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public class StaffItem extends Item {
	public StaffItem() {
		super(new Item.Properties().stacksTo(1).attributes(createAttributes()).component(ArcanusDataComponents.STAFF_PARTS.get(), StaffParts.defaultInstance()));
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
		if(itemStack.has(ArcanusDataComponents.STAFF_PARTS.get())) {
			var staffParts = itemStack.get(ArcanusDataComponents.STAFF_PARTS.get());
			var staffCore = staffParts.staffCore().get(ArcanusDataComponents.STAFF_CORE.get());
			var staffCap = staffParts.staffCap().get(ArcanusDataComponents.STAFF_CAP.get());

			staffCore.addToTooltip(context, builder, tooltipFlag, components());
			staffCap.addToTooltip(context, builder, tooltipFlag, components());
		}
	}

	@Override
	public boolean canDestroyBlock(ItemStack itemStack, BlockState state, Level level, BlockPos pos, LivingEntity user) {
		return user instanceof Player player && !player.isCreative() && !ArcanusComponents.isCasting(player);
	}

	public static ItemAttributeModifiers createAttributes() {
		return ItemAttributeModifiers.builder()
			.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 4, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -3, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(Arcanus.id("staff_reach"), 0.5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
			.build();
	}
}
