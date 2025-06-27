package dev.cammiescorner.arcanus.common.datacomponents;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.common.items.SpellBookItem;
import dev.cammiescorner.arcanus.common.items.SpellScrollItem;
import dev.cammiescorner.arcanus.common.menus.SpellBookMenu;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Range;

import java.util.List;

public record SpellBookComponent(List<ItemStack> spellScrolls) {

	private static final int MAX_SLOT_INDEX = SpellBookItem.SLOT_COUNT - 1;
	private static final SpellBookComponent EMPTY = new SpellBookComponent(NonNullList.withSize(SpellBookItem.SLOT_COUNT, ItemStack.EMPTY));

	public static final Codec<SpellBookComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ItemStack.CODEC.listOf().fieldOf("spell_scrolls").forGetter(SpellBookComponent::spellScrolls)
	).apply(instance, SpellBookComponent::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, SpellBookComponent> STREAM_CODEC = StreamCodec.composite(
		//spell list
		ItemStack.LIST_STREAM_CODEC,
		SpellBookComponent::spellScrolls,
		//---
		SpellBookComponent::new
	);

	public static SpellBookComponent empty() {
		return EMPTY;
	}

	public Spell getSpell(@Range(from = 0, to = MAX_SLOT_INDEX) int slot) {
		return SpellScrollItem.getSpell(this.spellScrolls.get(slot));
	}

	public SpellBookComponent withSpellScroll(@Range(from = 0, to = MAX_SLOT_INDEX) int slot, ItemStack spellScroll) {
		Preconditions.checkArgument(spellScroll.isEmpty() || SpellBookMenu.isValidSpellScroll(spellScroll), "Spell stack must be a spell scroll or empty!");

		var newSpells = NonNullList.withSize(SpellBookItem.SLOT_COUNT, ItemStack.EMPTY);
		for (int i = 0; i < newSpells.size(); i++) {
			newSpells.set(i, i == slot ? spellScroll : this.spellScrolls.get(i));
		}

		return new SpellBookComponent(newSpells);
	}

	public SpellBookComponent withSpells(NonNullList<ItemStack> newSpells) {
		Preconditions.checkArgument(newSpells.size() == SpellBookItem.SLOT_COUNT, "Invalid spell list size!");
		return new SpellBookComponent(newSpells);
	}

	public boolean hasSpell(@Range(from = 0, to = MAX_SLOT_INDEX) int index) {
		return !this.spellScrolls.get(index).isEmpty();
	}
}
