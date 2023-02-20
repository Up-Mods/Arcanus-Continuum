package dev.cammiescorner.arcanuscontinuum.common.screens;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusBlocks;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusScreenHandlers;
import dev.cammiescorner.arcanuscontinuum.common.util.WorkbenchMode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Optional;

public class ArcaneWorkbenchScreenHandler extends AbstractRecipeScreenHandler<CraftingInventory> {
	private final CraftingResultInventory result = new CraftingResultInventory();
	private final PlayerInventory playerInventory;
	private final ScreenHandlerContext context;
	private final PlayerEntity player;
	private CraftingInventory input;
	private WorkbenchMode mode;

	public ArcaneWorkbenchScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}

	public ArcaneWorkbenchScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(ArcanusScreenHandlers.ARCANE_WORKBENCH_SCREEN_HANDLER, syncId);
		this.playerInventory = playerInventory;
		this.mode = WorkbenchMode.CUSTOMIZE;
		this.context = context;
		this.player = playerInventory.player;
		getSlotsForMode(mode);
	}

	@Override
	public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
		if(getMode() == WorkbenchMode.SPELLBINDING) {
			ItemStack itemStack = ItemStack.EMPTY;
			Slot slot = slots.get(fromIndex);

			if(slot.hasStack()) {
				ItemStack itemStack2 = slot.getStack();
				itemStack = itemStack2.copy();

				if(fromIndex == 0) {
					context.run((world, pos) -> itemStack2.getItem().onCraft(itemStack2, world, player));

					if(!insertItem(itemStack2, 10, 46, true))
						return ItemStack.EMPTY;

					slot.onQuickTransfer(itemStack2, itemStack);
				}
				else if(fromIndex >= 10 && fromIndex < 46) {
					if(!insertItem(itemStack2, 1, 10, false)) {
						if(fromIndex < 37) {
							if(!insertItem(itemStack2, 37, 46, false))
								return ItemStack.EMPTY;
						}
						else if(!insertItem(itemStack2, 10, 37, false)) {
							return ItemStack.EMPTY;
						}
					}
				}
				else if(!insertItem(itemStack2, 10, 46, false)) {
					return ItemStack.EMPTY;
				}

				if(itemStack2.isEmpty())
					slot.setStack(ItemStack.EMPTY);
				else
					slot.markDirty();

				if(itemStack2.getCount() == itemStack.getCount())
					return ItemStack.EMPTY;

				slot.onTakeItem(player, itemStack2);

				if(fromIndex == 0)
					player.dropItem(itemStack2, false);
			}

			return itemStack;
		}
		else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return canUse(context, player, ArcanusBlocks.ARCANE_WORKBENCH);
	}

	public PlayerInventory getPlayerInventory() {
		return playerInventory;
	}

	public ScreenHandlerContext getContext() {
		return context;
	}

	public WorkbenchMode getMode() {
		return mode;
	}

	public void setMode(WorkbenchMode mode) {
		this.mode = mode;
		context.run((world, pos) -> dropInventory(player, input));
		getSlotsForMode(mode);
	}

	public void getSlotsForMode(WorkbenchMode mode) {
		slots.clear();

		switch(mode) {
			case SPELLBINDING -> {
				input = new CraftingInventory(this, 3, 3);

				addSlot(new CraftingResultSlot(player, input, result, 0, 135, 31));

				for(int i = 0; i < 3; ++i)
					for(int j = 0; j < 3; ++j)
						addSlot(new Slot(input, j + i * 3, 24 + j * 20, 11 + i * 20));
			}
			case CUSTOMIZE -> {
				input = new CraftingInventory(this, 3, 1);

				addSlot(new CraftingResultSlot(player, input, result, 0, 136, 35));

				addSlot(new Slot(input, 0, 54, 35));
				addSlot(new Slot(input, 1, 95, 24));
				addSlot(new Slot(input, 2, 95, 46));
			}
		}

		for(int i = 0; i < 3; ++i)
			for(int j = 0; j < 9; ++j)
				addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for(int i = 0; i < 9; ++i)
			addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
	}

	protected static void updateResult(ScreenHandler handler, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory) {
		if(!world.isClient() && player instanceof ServerPlayerEntity serverPlayer) {
			ItemStack itemStack = ItemStack.EMPTY;
			Optional<CraftingRecipe> optional = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);

			if(optional.isPresent()) {
				CraftingRecipe craftingRecipe = optional.get();

				if(resultInventory.shouldCraftRecipe(world, serverPlayer, craftingRecipe)) {
					ItemStack itemStack2 = craftingRecipe.craft(craftingInventory);

					if(itemStack2.m_eyzvudzj(world.getEnabledFlags()))
						itemStack = itemStack2;
				}
			}

			resultInventory.setStack(0, itemStack);
			handler.setPreviousTrackedSlot(0, itemStack);
			serverPlayer.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
		}
	}

	@Override
	public void onContentChanged(Inventory inventory) {
		context.run((world, pos) -> updateResult(this, world, player, input, result));
	}

	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		context.run((world, pos) -> dropInventory(player, input));
	}

	@Override
	public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
		return slot.inventory != result && super.canInsertIntoSlot(stack, slot);
	}

	@Override
	public void populateRecipeFinder(RecipeMatcher finder) {
		input.provideRecipeInputs(finder);
	}

	@Override
	public void clearCraftingSlots() {
		input.clear();
		result.clear();
	}

	@Override
	public boolean matches(Recipe<? super CraftingInventory> recipe) {
		return recipe.matches(input, player.world);
	}

	@Override
	public int getCraftingResultSlotIndex() {
		return 0;
	}

	@Override
	public int getCraftingWidth() {
		return input.getWidth();
	}

	@Override
	public int getCraftingHeight() {
		return input.getHeight();
	}

	@Override
	public int getCraftingSlotCount() {
		return input.size() + 1;
	}

	@Override
	public RecipeBookCategory getCategory() {
		return RecipeBookCategory.CRAFTING;
	}

	@Override
	public boolean canInsertIntoSlot(int index) {
		return index != this.getCraftingResultSlotIndex();
	}
}
