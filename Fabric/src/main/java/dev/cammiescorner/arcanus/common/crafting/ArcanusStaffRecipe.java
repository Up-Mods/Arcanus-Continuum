package dev.cammiescorner.arcanus.common.crafting;

import dev.cammiescorner.arcanus.common.data.ArcanusItemTags;
import dev.cammiescorner.arcanus.common.data_component.StaffParts;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import dev.cammiescorner.arcanus.common.registry.ArcanusRecipes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;

public class ArcanusStaffRecipe extends CustomRecipe {
	private static final ShapedRecipePattern PATTERN = ShapedRecipePattern.of(
		Map.of(
			'I', Ingredient.of(BuiltInRegistries.ITEM.get(ArcanusItemTags.STAFF_CORES).orElseThrow()),
			'C', Ingredient.of(BuiltInRegistries.ITEM.get(ArcanusItemTags.STAFF_CAPS).orElseThrow())
		),
		"  C",
		" I ",
		"C  "
	);

	public ArcanusStaffRecipe() {
		super();
	}

	@Override
	public boolean matches(CraftingInput input, Level level) {
		if(PATTERN.matches(input)) {
			List<ItemStack> capStacks = input.items().stream().filter(stack -> stack.is(ArcanusItemTags.STAFF_CAPS) && !stack.get(ArcanusDataComponents.STAFF_CAP.get()).isInert()).toList();
			return capStacks.size() == 2 && capStacks.stream().allMatch(stack -> ItemStack.isSameItemSameComponents(stack, capStacks.getFirst()));
		}

		return false;
	}

	@Override
	public ItemStack assemble(CraftingInput input) {
		ItemStack result = new ItemStack(ArcanusItems.STAFF.get());
		ItemStack core = input.items().stream().filter(stack -> stack.is(ArcanusItemTags.STAFF_CORES)).findFirst().get().copy();
		ItemStack cap = input.items().stream().filter(stack -> stack.is(ArcanusItemTags.STAFF_CAPS)).findFirst().get().copy();

		result.set(ArcanusDataComponents.STAFF_PARTS.get(), new StaffParts(core, cap));

		return result;
	}

	@Override
	public RecipeSerializer<? extends CustomRecipe> getSerializer() {
		return ArcanusRecipes.STAFF_SERIALIZER.get();
	}
}
