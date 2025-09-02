package dev.cammiescorner.arcanus.common.item;

import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.cammiescorner.arcanus.common.block.entities.ManaBeanBlockEntity;
import dev.cammiescorner.arcanus.common.registry.ArcanusArcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class ManaBeanItem extends BlockItem {
	public ManaBeanItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public InteractionResult place(BlockPlaceContext context) {
		Player player = context.getPlayer();
		Level level = context.getLevel();

		if(player != null && !level.isClientSide()) {
			ItemStack stack = player.getItemInHand(context.getHand());
			InteractionResult result = super.place(context);

			if(result.consumesAction() && level.getBlockEntity(context.getClickedPos()) instanceof ManaBeanBlockEntity manaBean) {
				manaBean.setArcana(stack.getOrDefault(ArcanusDataComponents.ARCANA.get(), ArcanusArcana.NIL.get()));

				return result;
			}
		}

		return super.place(context);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
		if(stack.getOrDefault(ArcanusDataComponents.ARCANA.get(), ArcanusArcana.NIL.get()) instanceof PrimalArcana arcana)
			ArcanusComponents.addArcana(livingEntity, arcana, 1, false);

		return super.finishUsingItem(stack, level, livingEntity);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		Arcana arcana = stack.getOrDefault(ArcanusDataComponents.ARCANA.get(), ArcanusArcana.NIL.get());

		// TODO make this translated better
		tooltipComponents.add(Component.literal("1 ").append(Component.translatable(arcana.translationKey())).withColor(arcana.color().asIntARGB()));
	}
}
