package dev.cammiescorner.arcanus.common.item;

import com.google.common.base.Preconditions;
import dev.cammiescorner.arcanus.common.data_component.BookPouchComponent;
import dev.cammiescorner.arcanus.common.menu.providers.BookPouchMenuProvider;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;

import java.util.List;

// TODO scroll wheel this shit babyyyy
public class BookPouchItem extends Item {
	public static final int SLOT_COUNT = 8;

	public BookPouchItem() {
		super(new Properties().stacksTo(1).component(ArcanusDataComponents.BOOK_POUCH.get(), BookPouchComponent.EMPTY).component(ArcanusDataComponents.BOOK_POUCH_INDEX.get(), 0));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		player.openMenu(new BookPouchMenuProvider(stack));

		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}

	@Override
	public void onDestroyed(ItemEntity itemEntity) {
		BookPouchComponent spellBooks = itemEntity.getItem().get(ArcanusDataComponents.BOOK_POUCH.get());

		if(spellBooks != null) {
			itemEntity.getItem().set(ArcanusDataComponents.BOOK_POUCH.get(), BookPouchComponent.EMPTY);
			ItemUtils.onContainerDestroyed(itemEntity, List.copyOf(spellBooks.spellBooks()));
		}
	}

	public static ItemStack getActiveSpellBook(ItemStack stack) {
		if(stack.is(ArcanusItems.BOOK_POUCH.get())) {
			BookPouchComponent spellBooks = stack.getOrDefault(ArcanusDataComponents.BOOK_POUCH.get(), BookPouchComponent.EMPTY);
			int index = stack.getOrDefault(ArcanusDataComponents.BOOK_POUCH_INDEX.get(), 0);

			Preconditions.checkArgument(index < BookPouchItem.SLOT_COUNT, "Index is larger than inventory size!");

			return spellBooks.spellBooks().get(index);
		}

		return ItemStack.EMPTY;
	}
}
