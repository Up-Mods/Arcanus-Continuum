package dev.cammiescorner.arcanus.common.block.entities;

import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlockEntities;
import dev.cammiescorner.arcanus.common.util.ArcanaContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class AlembicBlockEntity extends BlockEntity implements ArcanaContainer {
	public AlembicBlockEntity(BlockPos pos, BlockState blockState) {
		super(ArcanusBlockEntities.ALEMBIC.get(), pos, blockState);
	}

	@Override
	public Arcana getArcana() {
		return null;
	}

	@Override
	public void setArcana(Arcana arcana) {

	}

	@Override
	public double getArcanaAmount() {
		return 0;
	}

	@Override
	public void setArcanaAmount(double amount) {

	}

	@Override
	public List<Direction> inputDirections() {
		return ArcanaContainer.super.inputDirections();
	}

	@Override
	public List<Direction> outputDirections() {
		return ArcanaContainer.super.outputDirections();
	}
}
