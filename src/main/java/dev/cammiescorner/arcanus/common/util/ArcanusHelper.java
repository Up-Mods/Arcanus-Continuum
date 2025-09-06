package dev.cammiescorner.arcanus.common.util;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.api.entity.Targetable;
import dev.cammiescorner.arcanus.common.block.AbstractPipeBlock;
import dev.cammiescorner.arcanus.common.block.ArcanaPipeBlock;
import dev.cammiescorner.arcanus.common.block.ArcanaPumpBlock;
import dev.cammiescorner.arcanus.common.component.MagicColorComponent;
import dev.cammiescorner.arcanus.common.data.ArcanusEntityTags;
import dev.cammiescorner.arcanus.common.entity.magic.TemporalDilationField;
import dev.cammiescorner.arcanus.common.registry.ArcanusArcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlocks;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.util.supporters.WizardData;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ArcanusHelper {
	public static void findAndTransferArcana(LevelAccessor level, BlockPos pos, Arcana arcana, double amount) {
		if(arcana == ArcanusArcana.NIL.get() || level.isClientSide())
			return;

		List<BlockPos> alreadyChecked = new ArrayList<>();
		List<BlockPos> newPipes = new ArrayList<>();
		AtomicReference<Float> arcanaReduction = new AtomicReference<>(1f);
		AtomicInteger addVertical = new AtomicInteger(0);

		alreadyChecked.add(pos);
		newPipes.add(pos);

		while(!newPipes.isEmpty()) {
			List.copyOf(newPipes).forEach(blockPos -> {
				for(Direction direction : Direction.values()) {
					BlockState state = level.getBlockState(blockPos);
					BlockPos sidePos = blockPos.relative(direction);
					BlockState sideState = level.getBlockState(sidePos);

					if(alreadyChecked.contains(sidePos))
						continue;

					if((state.is(ArcanusBlocks.ARCANA_PIPE.get()) && !state.getValue(ArcanaPipeBlock.CONNECTION_BY_DIRECTION.get(direction.getOpposite())))
						|| (state.is(ArcanusBlocks.ARCANA_PUMP.get()) && !state.getValue(ArcanaPumpBlock.AXIS).test(direction))
						|| (level.getBlockEntity(sidePos) instanceof ArcanaContainer container
						&& (!container.outputDirections().contains(direction) || (container.getArcana() != ArcanusArcana.NIL.get() && container.getArcana() != arcana))))
						continue;

					if(!blockPos.equals(pos) && level.getBlockEntity(pos) instanceof ArcanaContainer start && level.getBlockEntity(sidePos) instanceof ArcanaContainer end
						&& (end.getArcana() == arcana || end.getArcana() == ArcanusArcana.NIL.get()) && end.getArcanaAmount() < end.getMaxArcanaAmount()
						&& end.inputDirections().contains(direction.getOpposite())
					) {
						transferArcana(start, end, arcana, amount, arcanaReduction.get());
						newPipes.clear();
						break;
					}

					if(sideState.is(ArcanusBlocks.ARCANA_PUMP.get()) && sideState.getValue(ArcanaPumpBlock.AXIS).test(direction)) {
						addVertical.getAndAdd(10);
						arcanaReduction.set(Math.max(arcanaReduction.get() - 0.1f, 0f));
					}

					if(sideState.getBlock() instanceof AbstractPipeBlock) {
						BooleanProperty property = ArcanaPipeBlock.EXTENSION_BY_DIRECTION.get(direction.getOpposite());

						if(!alreadyChecked.contains(sidePos) && sideState.hasProperty(property) && sideState.getValue(property)
							&& direction == Direction.UP && sidePos.getY() > pos.getY() + addVertical.get())
							continue;

						newPipes.add(sidePos);
					}

					alreadyChecked.add(sidePos);
				}

				newPipes.remove(blockPos);
			});
		}
	}

	public static void transferArcana(ArcanaContainer start, ArcanaContainer end, Arcana arcana, double amount, double lossPercentage) {
		if(arcana == ArcanusArcana.NIL.get())
			return;

		Arcana endArcana = end.getArcana();
		double startArcanaAmount = start.getArcanaAmount();
		double endArcanaAmount = end.getArcanaAmount();

		if(endArcana == ArcanusArcana.NIL.get())
			end.setArcana(arcana);
		if(arcana != endArcana || startArcanaAmount <= 0 || endArcanaAmount >= end.getMaxArcanaAmount())
			return;

		double maxDrain = Math.clamp(amount, 0, end.getMaxArcanaAmount() - endArcanaAmount);

		end.setArcanaAmount(endArcanaAmount + (maxDrain * lossPercentage));
		start.setArcanaAmount(startArcanaAmount - maxDrain);
	}

	public static boolean shouldTimeDilate(Entity target, Level level) {
		return !target.getType().is(ArcanusEntityTags.TEMPORAL_DILATION_IMMUNE) && !level.getEntities(target, target.getBoundingBox(), entity -> entity instanceof TemporalDilationField && entity.position().add(0, 4.5, 0).distanceTo(target.position()) <= entity.getBbWidth() / 2).isEmpty();
	}

	public static Color getMagicColor(@Nullable Object provider) {
		if(provider == null)
			return Arcanus.DEFAULT_MAGIC_COLOR;

		MagicColorComponent component = ArcanusComponents.MAGIC_COLOR.getNullable(provider);

		if(component != null)
			return component.getColor();

		// if Entity
		if(provider instanceof TraceableEntity ownable) {
			Entity owner = ownable.getOwner();

			if(owner != null)
				return getMagicColor(owner);
		}

		return Arcanus.DEFAULT_MAGIC_COLOR;
	}

	public static Color getPocketDimensionColor(@Nullable Object provider) {
		if(provider == null)
			return Arcanus.DEFAULT_MAGIC_COLOR;

		MagicColorComponent component = ArcanusComponents.MAGIC_COLOR.getNullable(provider);

		if(component != null)
			return component.getPocketDimensionColor();

		// if Entity
		if(provider instanceof TraceableEntity ownable) {
			Entity owner = ownable.getOwner();

			if(owner != null)
				return getPocketDimensionColor(owner);
		}

		return Arcanus.DEFAULT_MAGIC_COLOR;
	}

	public static Color getMagicColor(@Nullable UUID playerId) {
		if(playerId == null || Util.NIL_UUID.equals(playerId))
			return Arcanus.DEFAULT_MAGIC_COLOR;

		return WizardData.getOrEmpty(playerId).magicColor();
	}

	public static Color getPocketDimensionColor(@Nullable UUID playerId) {
		if(playerId == null || Util.NIL_UUID.equals(playerId))
			return Arcanus.DEFAULT_MAGIC_COLOR;

		return WizardData.getOrEmpty(playerId).pocketDimensionColor();
	}

	public static HitResult raycast(Entity origin, double maxDistance, boolean includeEntities, boolean includeFluids) {
		Vec3 startPos = origin.getEyePosition(1f);
		Vec3 rotation = origin.getViewVector(1f);
		Vec3 endPos = startPos.add(rotation.scale(maxDistance));
		HitResult hitResult = origin.level().clip(new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, includeFluids ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, origin));

		endPos = hitResult.getLocation();
		maxDistance *= maxDistance;

		if(includeEntities) {
			EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(origin, startPos, endPos, origin.getBoundingBox().expandTowards(rotation.scale(maxDistance)).inflate(1d, 1d, 1d), entity -> !entity.isSpectator() && entity instanceof Targetable targetable && targetable.arcanus$canBeTargeted(), maxDistance);

			if(entityHitResult != null)
				return entityHitResult;
		}

		return hitResult;
	}

	public static ItemStack applyColorToItem(ItemStack stack, int color) {
		stack.set(DataComponents.DYED_COLOR, new DyedItemColor(color, false));
		return stack;
	}

	/**
	 * sets the magic color if the entity can store it
	 *
	 * @param sourceId the UUID of the source player to get the magic color from
	 */
	public static void setMagicColorSource(Object obj, UUID sourceId) {
		ArcanusComponents.MAGIC_COLOR.maybeGet(obj).ifPresent(component -> component.setSourceId(sourceId));
	}

	/**
	 * sets the magic color if the entity can store it
	 *
	 * @param from the entity to take the color from.
	 *             If this entity does not have an attached {@link MagicColorComponent},
	 *             will default to {@link Arcanus#DEFAULT_MAGIC_COLOR}
	 */
	public static void copyMagicColor(Object to, Entity from) {
		ArcanusComponents.MAGIC_COLOR.maybeGet(from).ifPresent(sourceComponent -> setMagicColorSource(to, sourceComponent.getSourceId()));
	}

	public static void giveOrDrop(Player player, ItemStack stack) {
		if(!player.addItem(stack)) {
			@Nullable var itemEntity = player.drop(stack, false);

			if(itemEntity != null) {
				itemEntity.setNoPickUpDelay();
				itemEntity.setTarget(player.getUUID());
			}
		}
	}

	public static MutableComponent formatColorARGB(int color) {
		return Component.literal(String.format("#%08X", color));
	}

	public static MutableComponent formatColorRGB(int color) {
		return  Component.literal(String.format("#%06X", color & 0x00FFFFFF));
	}
}
