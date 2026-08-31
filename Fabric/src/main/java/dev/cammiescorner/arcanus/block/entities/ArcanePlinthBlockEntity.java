package dev.cammiescorner.arcanus.block.entities;

import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.cammiescorner.arcanus.api.crafting.RiteRecipeInput;
import dev.cammiescorner.arcanus.registry.ArcanusBlockEntities;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ArcanePlinthBlockEntity extends AbstractPedestalBlockEntity implements RiteRecipeInput {
	public final Object2DoubleArrayMap<PrimalArcana> arcanaMap = new Object2DoubleArrayMap<>();

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
	public double getArcana(PrimalArcana type) {
		return arcanaMap.getOrDefault(type, 0d);
	}

	public void setMana(PrimalArcana type, double amount) {
		arcanaMap.put(type, amount);
		markUpdated();
	}

	@Override
	public int size() {
		return getContainerSize();
	}

	@Override
	public StackedContents<ItemStack> getStackedContents() {
		var stackedContents = new StackedContents<ItemStack>();

		stackedContents.account(this.getItem(), 1);

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
