package dev.cammiescorner.arcanus.common.items;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spells.Spell;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Locale;

public class SpellBookItem extends Item {
	public SpellBookItem() {
		super(new Item.Properties().stacksTo(1));
	}

	@Override
	public Component getName(ItemStack stack) {
		Spell spell = getSpell(stack);

		return ((MutableComponent) super.getName(stack)).append(" (" + spell.getName() + ")");
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		Spell spell = getSpell(stack);

		String manaCost = Arcanus.format(spell.getManaCost());
		String coolDown = Arcanus.format(spell.getCoolDown() / 20D);

		// TODO make ALL of it translatable
		tooltipComponents.add(Component.literal(spell.getName()).withStyle(ChatFormatting.GOLD));
		tooltipComponents.add(Component.translatable("spell_book.arcanus.weight").append(": ").withStyle(ChatFormatting.GREEN)
			.append(Component.translatable("spell_book.arcanus.weight." + spell.getWeight().toString().toLowerCase(Locale.ROOT)).withStyle(ChatFormatting.GRAY)));
		tooltipComponents.add(Component.translatable("spell_book.arcanus.mana_cost").append(": ").withStyle(ChatFormatting.BLUE)
			.append(Component.literal(manaCost).withStyle(ChatFormatting.GRAY)));
		tooltipComponents.add(Component.translatable("spell_book.arcanus.cool_down").append(": ").withStyle(ChatFormatting.RED)
			.append(Component.literal(coolDown).append(Component.translatable("spell_book.arcanus.seconds")).withStyle(ChatFormatting.GRAY)));

		super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = world.getBlockState(pos);

		if(state.is(Blocks.LECTERN))
			return LecternBlock.tryPlaceBook(context.getPlayer(), world, pos, state, context.getItemInHand()) ? InteractionResult.sidedSuccess(world.isClientSide) : InteractionResult.PASS;

		return InteractionResult.PASS;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		Spell spell = getSpell(stack);

		if(spell.isEmpty())
			return super.use(world, player, hand);

		// TODO use Sparkweave for the menu stuff please
//		player.openMenu(new ExtendedScreenHandlerFactory() {
//			@Override
//			public Object getScreenOpeningData(ServerPlayer player) {
//				return stack;
//			}
//
//			@Override
//			public Component getDisplayName() {
//				return Component.literal(spell.getName());
//			}
//
//			@Override
//			public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
//				return new SpellBookScreenHandler(i, playerInventory, stack);
//			}
//		});

		return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
	}

	public static Spell getSpell(ItemStack stack) {
		// TODO spell as data component
//		return stack.hasTag() ? Spell.fromNbt(stack.getTag().getCompound("Spell")) : new Spell();
		return new Spell();
	}
}
