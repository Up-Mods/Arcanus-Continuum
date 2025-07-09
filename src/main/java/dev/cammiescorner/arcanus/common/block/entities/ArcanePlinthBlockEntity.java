package dev.cammiescorner.arcanus.common.block.entities;

import dev.cammiescorner.arcanus.api.crafting.RiteRecipeInput;
import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

// TODO block entity renderer
public class ArcanePlinthBlockEntity extends AbstractPedestalBlockEntity implements RiteRecipeInput {

	public ArcanePlinthBlockEntity(BlockPos pos, BlockState blockState) {
		super(ArcanusBlockEntities.ARCANE_PLINTH.get(), pos, blockState);
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		super.setItem(slot, stack);
		if(slot == 0) {
			// TODO update crafting process
		}
	}

	@Override
	public double getMana(ManaType type) {
		// TODO implement
		return 0;
	}

	@Override
	public int size() {
		return getContainerSize();
	}

	@Override
	public StackedContents getStackedContents() {
		var stackedContents = new StackedContents();

		stackedContents.accountStack(this.getItem(), 1);

		// TODO add all pedestals etc

		return stackedContents;
	}

	@Override
	public Vec3 getOrigin() {
		return Vec3.upFromBottomCenterOf(getBlockPos(), 1.0D);
	}

	@Override
	public BlockPos getOriginBlockPos() {
		return getBlockPos();
	}
}
