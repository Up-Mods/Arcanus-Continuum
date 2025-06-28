package dev.cammiescorner.arcanus.common.datacomponents;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.common.items.SpellBookItem;
import dev.cammiescorner.arcanus.common.items.SpellScrollItem;
import dev.cammiescorner.arcanus.common.menus.SpellBookMenu;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Range;

import java.util.List;
import java.util.function.Function;

public record SpellBookComponent(List<ItemStack> spellScrolls) {
	private static final int MAX_SLOT_INDEX = SpellBookItem.SLOT_COUNT - 1;
	private static final SpellBookComponent EMPTY = new SpellBookComponent(NonNullList.withSize(SpellBookItem.SLOT_COUNT, ItemStack.EMPTY));

	public static final Codec<SpellBookComponent> CODEC = Slot.CODEC.listOf().xmap(SpellBookComponent::fromSlots, SpellBookComponent::asSlots);

	public static final StreamCodec<RegistryFriendlyByteBuf, SpellBookComponent> STREAM_CODEC = Slot.STREAM_CODEC.apply(ByteBufCodecs.list(SpellBookItem.SLOT_COUNT)).map(SpellBookComponent::fromSlots, SpellBookComponent::asSlots);

	public static SpellBookComponent empty() {
		return EMPTY;
	}

	public Spell getSpell(@Range(from = 0, to = MAX_SLOT_INDEX) int slot) {
		return SpellScrollItem.getSpell(this.spellScrolls.get(slot));
	}

	public SpellBookComponent withSpellScroll(@Range(from = 0, to = MAX_SLOT_INDEX) int slot, ItemStack spellScroll) {
		Preconditions.checkArgument(spellScroll.isEmpty() || SpellBookMenu.isValidSpellScroll(spellScroll), "Spell stack must be a spell scroll or empty!");

		NonNullList<ItemStack> newSpells = NonNullList.withSize(SpellBookItem.SLOT_COUNT, ItemStack.EMPTY);

		for(int i = 0; i < newSpells.size(); i++)
			newSpells.set(i, i == slot ? spellScroll : this.spellScrolls.get(i));

		return new SpellBookComponent(newSpells);
	}

	public SpellBookComponent withSpells(NonNullList<ItemStack> newSpells) {
		Preconditions.checkArgument(newSpells.size() == SpellBookItem.SLOT_COUNT, "Invalid spell list size!");
		return new SpellBookComponent(newSpells);
	}

	public boolean hasSpell(@Range(from = 0, to = MAX_SLOT_INDEX) int index) {
		return !this.spellScrolls.get(index).isEmpty();
	}

	public List<Slot> asSlots() {
		ImmutableList.Builder<Slot> builder = ImmutableList.builder();
		for (int i = 0; i < spellScrolls.size(); i++) {
			ItemStack stack = spellScrolls.get(i);

			if(!stack.isEmpty()) {
				builder.add(new Slot(i, stack));
			}
		}

		return builder.build();
	}

	public static SpellBookComponent fromSlots(List<Slot> slots) {
		NonNullList<ItemStack> newSpells = NonNullList.withSize(SpellBookItem.SLOT_COUNT, ItemStack.EMPTY);

		for(Slot slot : slots) {
			newSpells.set(slot.index(), slot.stack);
		}

		return new SpellBookComponent(newSpells);
	}

	public record Slot(int index, ItemStack stack) {
		public static final Codec<Slot> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.intRange(0, MAX_SLOT_INDEX).fieldOf("index").forGetter(Slot::index),
			ItemStack.CODEC.fieldOf("stack").forGetter(Slot::stack)
		).apply(instance, Slot::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, Slot> STREAM_CODEC = StreamCodec.composite(
			//index
			ByteBufCodecs.VAR_INT.map(idx -> Preconditions.checkElementIndex(idx, SpellBookItem.SLOT_COUNT), Function.identity()),
			Slot::index,
			//---
			//stack
			ItemStack.STREAM_CODEC,
			Slot::stack,
			//---
			Slot::new
		);
	}
}
