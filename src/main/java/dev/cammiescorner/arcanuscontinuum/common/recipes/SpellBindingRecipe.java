package dev.cammiescorner.arcanuscontinuum.common.recipes;

import com.google.common.collect.Lists;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Spell;
import dev.cammiescorner.arcanuscontinuum.common.items.SpellBookItem;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusRecipes;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class SpellBindingRecipe extends SpecialCraftingRecipe {
	public SpellBindingRecipe(Identifier identifier) {
		super(identifier);
	}

	@Override
	public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {
		DefaultedList<ItemStack> list = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

		for(int i = 0; i < list.size(); ++i)
			if(inventory.getStack(i).getItem() instanceof SpellBookItem)
				list.set(i, inventory.getStack(i).copy());

		return list;
	}

	@Override
	public boolean matches(CraftingInventory inv, World world) {
		List<ItemStack> list = Lists.newArrayList();
		ItemStack result = ItemStack.EMPTY;

		for(int i = 0; i < inv.size(); ++i) {
			ItemStack stack = inv.getStack(i);

			if(!stack.isEmpty()) {
				Item item = stack.getItem();

				if(item instanceof StaffItem) {
					if(!result.isEmpty() || i != 4)
						return false;

					result = stack.copy();
				}
				else {
					if(!(item instanceof SpellBookItem) || i == 4)
						return false;

					list.add(stack);
				}
			}
		}

		return !result.isEmpty() && !list.isEmpty();
	}

	@Override
	public ItemStack craft(CraftingInventory inv) {
		DefaultedList<Spell> spells = DefaultedList.ofSize(8, Spell.EMPTY);
		ItemStack result = ItemStack.EMPTY;

		if(inv.getStack(4).getItem() instanceof StaffItem) {
			if(!result.isEmpty())
				return ItemStack.EMPTY;

			result = inv.getStack(4).copy();
		}

		for(int i = 0; i < inv.size(); i++) {
			if(i == 4)
				continue;

			ItemStack stack = inv.getStack(i);

			if(!stack.isEmpty()) {
				Spell spell = Spell.EMPTY;
				int[] indices = new int[] { 7, 0, 1, 6, 0, 2, 5, 4, 3 };

				if(stack.getItem() instanceof SpellBookItem book)
					spell = book.getSpell();

				spells.set(indices[i], spell);
			}
		}

		return !result.isEmpty() && !spells.isEmpty() ? applySpells(result, spells) : ItemStack.EMPTY;
	}

	@Override
	public boolean fits(int width, int height) {
		return width * height == 9;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ArcanusRecipes.SPELL_BINDING;
	}

	private ItemStack applySpells(ItemStack stack, List<Spell> spells) {
		NbtCompound tag = stack.getSubNbt(Arcanus.MOD_ID);

		if(tag == null || tag.isEmpty())
			return ItemStack.EMPTY;

		NbtList list = tag.getList("Spells", NbtElement.STRING_TYPE);

		for(int i = 0; i < spells.size(); i++) {
			Spell spell = spells.get(i);

			if(spell == Spell.EMPTY)
				continue;

			list.set(i, NbtString.of(Arcanus.SPELLS.getId(spell).toString()));
		}

		tag.put("Spells", list);

		return stack;
	}
}
