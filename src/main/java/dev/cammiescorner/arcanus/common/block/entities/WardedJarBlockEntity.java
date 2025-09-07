package dev.cammiescorner.arcanus.common.block.entities;

import dev.cammiescorner.arcanus.client.util.ColorRenderData;
import dev.cammiescorner.arcanus.common.block.WardedJarBlock;
import dev.cammiescorner.arcanus.common.data_component.ArcanaStack;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.util.ArcanaContainer;
import dev.upcraft.sparkweave.api.color.Color;
import net.fabricmc.fabric.api.blockview.v2.RenderDataBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WardedJarBlockEntity extends BlockEntity implements RenderDataBlockEntity, ArcanaContainer {
	private ArcanaStack arcanaStack = ArcanaStack.EMPTY;

	public WardedJarBlockEntity(BlockPos pos, BlockState blockState) {
		super(ArcanusBlockEntities.WARDED_JAR.get(), pos, blockState);
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		CompoundTag tag = super.getUpdateTag(registries);
		saveAdditional(tag, registries);

		return tag;
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);

		tag.put("Arcana", ArcanaStack.CODEC.encodeStart(NbtOps.INSTANCE, arcanaStack).result().orElseThrow());
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);

		if(tag.contains("Arcana", Tag.TAG_COMPOUND))
			arcanaStack = ArcanaStack.CODEC.parse(NbtOps.INSTANCE, tag.get("Arcana")).result().orElseThrow();
		else
			arcanaStack = ArcanaStack.EMPTY;

		if(hasLevel())
			markUpdated();
	}

	@Override
	public Object getRenderData() {
		return new ColorRenderData(arcanaStack != null ? arcanaStack.arcana().color() : Color.fromARGB(0xffffffff));
	}

	@Override
	protected void applyImplicitComponents(DataComponentInput componentInput) {
		super.applyImplicitComponents(componentInput);

		arcanaStack = componentInput.getOrDefault(ArcanusDataComponents.ARCANA_STACK.get(), ArcanaStack.EMPTY);
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.Builder components) {
		super.collectImplicitComponents(components);

		components.set(ArcanusDataComponents.ARCANA_STACK.get(), arcanaStack);
	}

	@Override
	public ArcanaStack getArcanaStack(int index) {
		return arcanaStack;
	}

	@Override
	public void setArcanaStack(ArcanaStack arcanaStack, int index) {
		this.arcanaStack = arcanaStack;
		markUpdated();
	}

	@Override
	public void addArcanaStack(ArcanaStack arcanaStack) {
		setArcanaStack(arcanaStack, 0);
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return arcanaStack == ArcanaStack.EMPTY;
	}

	@Override
	public List<Direction> inputDirections() {
		return List.of(Direction.UP);
	}

	@Override
	public List<Direction> outputDirections() {
		return List.of(Direction.DOWN);
	}

	protected void markUpdated() {
		BlockState newState = getBlockState().setValue(WardedJarBlock.LEVEL, (int) Math.ceil(arcanaStack.amount() / 8f));
		setChanged();
		getLevel().setBlockAndUpdate(getBlockPos(), newState);
	}
}
