package dev.cammiescorner.arcanus.common.block.entities;

import dev.cammiescorner.arcanus.api.crafting.RiteRecipeInput;
import dev.cammiescorner.arcanus.api.arcana.ArcanaType;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public class ArcanePlinthBlockEntity extends AbstractPedestalBlockEntity implements RiteRecipeInput {
	public final Map<ArcanaType, Double> arcana = new HashMap<>();

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
	public double getMana(ArcanaType type) {
		return arcana.getOrDefault(type, 0d);
	}

	public void setMana(ArcanaType type, double amount) {
		arcana.put(type, amount);
		markUpdated();
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
		return Vec3.upFromBottomCenterOf(getBlockPos(), 1);
	}

	@Override
	public BlockPos getOriginBlockPos() {
		return getBlockPos();
	}
}
