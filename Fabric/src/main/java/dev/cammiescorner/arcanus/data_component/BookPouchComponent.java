package dev.cammiescorner.arcanus.data_component;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.item.BookPouchItem;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Function;

public record BookPouchComponent(List<ItemStack> spellBooks) {
	public static final BookPouchComponent EMPTY = new BookPouchComponent(NonNullList.withSize(BookPouchItem.SLOT_COUNT, ItemStack.EMPTY));
	public static final Codec<BookPouchComponent> CODEC = BookPouchComponent.Slot.CODEC.listOf().xmap(BookPouchComponent::fromSlots, BookPouchComponent::asSlots);
	public static final StreamCodec<RegistryFriendlyByteBuf, BookPouchComponent> STREAM_CODEC = BookPouchComponent.Slot.STREAM_CODEC.apply(ByteBufCodecs.list(BookPouchItem.SLOT_COUNT)).map(BookPouchComponent::fromSlots, BookPouchComponent::asSlots);

	public List<BookPouchComponent.Slot> asSlots() {
		ImmutableList.Builder<BookPouchComponent.Slot> builder = ImmutableList.builder();

		for(int i = 0; i < spellBooks.size(); i++) {
			ItemStack stack = spellBooks.get(i);

			if(!stack.isEmpty())
				builder.add(new BookPouchComponent.Slot(i, stack));
		}

		return builder.build();
	}

	public static BookPouchComponent fromSlots(List<BookPouchComponent.Slot> slots) {
		NonNullList<ItemStack> newSpells = NonNullList.withSize(BookPouchItem.SLOT_COUNT, ItemStack.EMPTY);

		for(BookPouchComponent.Slot slot : slots)
			newSpells.set(slot.index(), slot.stack);

		return new BookPouchComponent(newSpells);
	}

	public BookPouchComponent withSpellBooks(NonNullList<ItemStack> newBooks) {
		Preconditions.checkArgument(newBooks.size() == BookPouchItem.SLOT_COUNT, "Invalid book list size!");
		return new BookPouchComponent(newBooks);
	}

	public record Slot(int index, ItemStack stack) {
		public static final Codec<BookPouchComponent.Slot> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.intRange(0, BookPouchItem.SLOT_COUNT - 1).fieldOf("index").forGetter(BookPouchComponent.Slot::index),
			ItemStack.CODEC.fieldOf("stack").forGetter(BookPouchComponent.Slot::stack)
		).apply(instance, BookPouchComponent.Slot::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, BookPouchComponent.Slot> STREAM_CODEC = StreamCodec.composite(
			//index
			ByteBufCodecs.VAR_INT.map(idx -> Preconditions.checkElementIndex(idx, BookPouchItem.SLOT_COUNT), Function.identity()),
			BookPouchComponent.Slot::index,
			//---
			//stack
			ItemStack.STREAM_CODEC,
			BookPouchComponent.Slot::stack,
			//---
			BookPouchComponent.Slot::new
		);
	}
}
