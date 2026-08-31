package dev.cammiescorner.arcanus.item;

import dev.cammiescorner.arcanus.data_component.BookPouchComponent;
import dev.cammiescorner.arcanus.menu.providers.BookPouchMenuProvider;
import dev.cammiescorner.arcanus.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.registry.ArcanusItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;

public class BookPouchItem extends Item {
	public static final int SLOT_COUNT = 8;

	public BookPouchItem(Item.Properties properties) {
		super(properties.stacksTo(1).component(ArcanusDataComponents.BOOK_POUCH.get(), BookPouchComponent.EMPTY).component(ArcanusDataComponents.BOOK_POUCH_INDEX.get(), 0));
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		player.openMenu(new BookPouchMenuProvider(stack));

		return InteractionResult.SUCCESS_SERVER;
	}

	@Override
	public void onDestroyed(ItemEntity itemEntity) {
		BookPouchComponent spellBooks = itemEntity.getItem().get(ArcanusDataComponents.BOOK_POUCH.get());

		if(spellBooks != null) {
			itemEntity.getItem().set(ArcanusDataComponents.BOOK_POUCH.get(), BookPouchComponent.EMPTY);
			ItemUtils.onContainerDestroyed(itemEntity, spellBooks.spellBooks().stream());
		}
	}

	public static ItemStack getActiveSpellBook(ItemStack stack) {
		if(stack.is(ArcanusItems.BOOK_POUCH.get())) {
			BookPouchComponent spellBooks = stack.getOrDefault(ArcanusDataComponents.BOOK_POUCH.get(), BookPouchComponent.EMPTY);
			int index = stack.getOrDefault(ArcanusDataComponents.BOOK_POUCH_INDEX.get(), 0);
			if(index >= 0 && index < SLOT_COUNT) {
				return spellBooks.spellBooks().get(index);
			}
		}

		return ItemStack.EMPTY;
	}
}
