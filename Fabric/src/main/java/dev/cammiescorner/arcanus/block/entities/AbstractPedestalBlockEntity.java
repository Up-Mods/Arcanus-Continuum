package dev.cammiescorner.arcanus.block.entities;

import dev.cammiescorner.arcanus.util.ArcanusHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractPedestalBlockEntity extends BlockEntity implements Container {
	private final NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);

	public AbstractPedestalBlockEntity(BlockEntityType<? extends AbstractPedestalBlockEntity> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		ContainerHelper.saveAllItems(output, inventory, true);
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		ContainerHelper.loadAllItems(input, inventory);
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		CompoundTag tag = super.getUpdateTag(registries);

		if(!this.inventory.isEmpty()) {
			RegistryOps<Tag> ops = registries.createSerializationContext(NbtOps.INSTANCE);
			tag.store("Inventory", ItemStack.CODEC.listOf(), ops, this.inventory);
		}

		return tag;
	}

	@Override
	public int getContainerSize() {
		return inventory.size();
	}

	@Override
	public boolean isEmpty() {
		return inventory.stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	public ItemStack getItem(int slot) {
		return inventory.get(slot);
	}

	@Override
	public ItemStack removeItem(int slot, int amount) {
		var removed = ContainerHelper.removeItem(inventory, slot, amount);
		markUpdated();
		return removed;
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.removeItem(inventory, slot, 1);
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		inventory.set(slot, stack);
		markUpdated();
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public void clearContent() {
		inventory.clear();
		markUpdated();
	}

	public ItemStack getItem() {
		return getItem(0);
	}

	public void setItem(ItemStack stack) {
		setItem(0, stack);
	}

	public ItemStack removeItem() {
		var removed = removeItemNoUpdate();
		markUpdated();
		return removed;
	}

	public ItemStack removeItemNoUpdate() {
		return removeItemNoUpdate(0);
	}

	protected void markUpdated() {
		setChanged();
		getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
	}

	public InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		ItemStack itemStack = player.isCreative() ? stack.copyWithCount(1) : stack.split(1);

		if(this.isEmpty() && !itemStack.isEmpty()) {
			this.setItem(itemStack);

			return InteractionResult.SUCCESS_SERVER;
		}

		if(!this.isEmpty()) {
			ItemStack removed = this.removeItem();
			ArcanusHelper.giveOrDrop(player, removed);

			return InteractionResult.SUCCESS_SERVER;
		}

		return InteractionResult.PASS;
	}
}
