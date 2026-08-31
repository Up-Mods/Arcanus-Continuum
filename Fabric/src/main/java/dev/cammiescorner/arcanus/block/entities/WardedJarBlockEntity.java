package dev.cammiescorner.arcanus.block.entities;

import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.client.util.ColorRenderData;
import dev.cammiescorner.arcanus.block.WardedJarBlock;
import dev.cammiescorner.arcanus.data_component.ArcanaStack;
import dev.cammiescorner.arcanus.registry.ArcanusBlockEntities;
import dev.cammiescorner.arcanus.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.util.ArcanaContainer;
import dev.upcraft.sparkweave.api.color.Color;
import net.fabricmc.fabric.api.blockgetter.v2.RenderDataBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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

		tag.put("Arcana", ArcanaStack.CODEC.encodeStart(NbtOps.INSTANCE, arcanaStack).result().orElseThrow());

		return tag;
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);

		output.store("Arcana", ArcanaStack.CODEC, arcanaStack);
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);

		arcanaStack = input.read("Arcana", ArcanaStack.CODEC).orElse(ArcanaStack.EMPTY);

		if(hasLevel())
			markUpdated();
	}

	@Override
	public Object getRenderData() {
		return new ColorRenderData(arcanaStack != null ? arcanaStack.arcana().color() : Color.fromARGB(0xffffffff));
	}

	@Override
	protected void applyImplicitComponents(DataComponentGetter components) {
		super.applyImplicitComponents(components);

		arcanaStack = components.getOrDefault(ArcanusDataComponents.ARCANA_STACK.get(), ArcanaStack.EMPTY);
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
		if(this.arcanaStack.isEmpty())
			setArcanaStack(arcanaStack, 0);
	}

	@Override
	public int indexOf(ArcanaStack arcanaStack) {
		return 0;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public double maximumArcana() {
		return 64;
	}

	@Override
	public boolean isEmpty() {
		return arcanaStack.isEmpty();
	}

	@Override
	public boolean isFull() {
		return arcanaStack.amount() >= maximumArcana();
	}

	@Override
	public boolean contains(Arcana arcana) {
		return this.arcanaStack.arcana() == arcana;
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
