package dev.cammiescorner.arcanus.common.util;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.cammiescorner.arcanus.api.entity.Targetable;
import dev.cammiescorner.arcanus.api.spell.Pattern;
import dev.cammiescorner.arcanus.common.block.AbstractPipeBlock;
import dev.cammiescorner.arcanus.common.block.ArcanaPipeBlock;
import dev.cammiescorner.arcanus.common.block.ArcanaPumpBlock;
import dev.cammiescorner.arcanus.common.component.MagicColorComponent;
import dev.cammiescorner.arcanus.common.data.ArcanusEntityTags;
import dev.cammiescorner.arcanus.common.data_component.ArcanaStack;
import dev.cammiescorner.arcanus.common.entity.magic.TemporalDilationField;
import dev.cammiescorner.arcanus.common.item.BookPouchItem;
import dev.cammiescorner.arcanus.common.registry.ArcanusArcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlocks;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import dev.cammiescorner.arcanus.common.util.supporters.WizardData;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import dev.upcraft.sparkweave.api.color.Color;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
	public static void findAndTransferArcana(LevelAccessor level, BlockPos pos, ArcanaStack arcanaStack, double amount) {
		if(arcanaStack.isEmpty() || level.isClientSide())
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
						|| (level.getBlockEntity(sidePos) instanceof ArcanaContainer container && (!container.outputDirections().contains(direction) || container.isFull())))
						continue;

					if(!blockPos.equals(pos) && level.getBlockEntity(pos) instanceof ArcanaContainer start && level.getBlockEntity(sidePos) instanceof ArcanaContainer end && end.inputDirections().contains(direction.getOpposite())) {
						transferArcana(start, end, arcanaStack, amount, arcanaReduction.get());
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

	public static void transferArcana(ArcanaContainer start, ArcanaContainer end, ArcanaStack startStack, double amount, double lossPercentage) {
		if(startStack.isEmpty() || end.isFull())
			return;

		if(!end.contains(startStack.arcana()))
			end.addArcanaStack(new ArcanaStack(startStack.arcana(), 0));

		int index = end.indexOf(new ArcanaStack(startStack.arcana(), 0));
		ArcanaStack endStack = end.getArcanaStack(index);
		double startArcanaAmount = startStack.amount();
		double endArcanaAmount = endStack.amount();

		if(startStack.arcana() != endStack.arcana() || startArcanaAmount <= 0 || endArcanaAmount >= end.maximumArcana())
			return;

		double maxDrain = Math.clamp(amount, 0, end.maximumArcana() - endArcanaAmount);

		end.setArcanaStack(new ArcanaStack(startStack.arcana(), endArcanaAmount + (maxDrain * lossPercentage)), index);
		start.setArcanaStack(new ArcanaStack(startStack.arcana(), startArcanaAmount - maxDrain), index);
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

	public static int getSpellIndex(List<Pattern> patternList) {
		String pattern = patternList.get(0).getLetter() + patternList.get(1).getLetter() + patternList.get(2).getLetter();

		return switch(pattern) {
			case "LLL" -> 0;
			case "LLR" -> 1;
			case "LRL" -> 2;
			case "LRR" -> 3;
			case "RRR" -> 4;
			case "RRL" -> 5;
			case "RLR" -> 6;
			case "RLL" -> 7;
			default -> 0;
		};
	}

	public static List<Pattern> getSpellPattern(int index) {
		return switch(index) {
			case 1 -> List.of(Pattern.LEFT, Pattern.LEFT, Pattern.RIGHT);
			case 2 -> List.of(Pattern.LEFT, Pattern.RIGHT, Pattern.LEFT);
			case 3 -> List.of(Pattern.LEFT, Pattern.RIGHT, Pattern.RIGHT);
			case 4 -> List.of(Pattern.RIGHT, Pattern.RIGHT, Pattern.RIGHT);
			case 5 -> List.of(Pattern.RIGHT, Pattern.RIGHT, Pattern.LEFT);
			case 6 -> List.of(Pattern.RIGHT, Pattern.LEFT, Pattern.RIGHT);
			case 7 -> List.of(Pattern.RIGHT, Pattern.LEFT, Pattern.LEFT);
			default -> List.of(Pattern.LEFT, Pattern.LEFT, Pattern.LEFT);
		};
	}

	public static MutableComponent getSpellPatternAsText(int index) {
		String string = switch(index) {
			case 0 -> Pattern.LEFT.getSymbol() + "-" + Pattern.LEFT.getSymbol() + "-" + Pattern.LEFT.getSymbol();
			case 1 -> Pattern.LEFT.getSymbol() + "-" + Pattern.LEFT.getSymbol() + "-" + Pattern.RIGHT.getSymbol();
			case 2 -> Pattern.LEFT.getSymbol() + "-" + Pattern.RIGHT.getSymbol() + "-" + Pattern.LEFT.getSymbol();
			case 3 -> Pattern.LEFT.getSymbol() + "-" + Pattern.RIGHT.getSymbol() + "-" + Pattern.RIGHT.getSymbol();
			case 4 -> Pattern.RIGHT.getSymbol() + "-" + Pattern.RIGHT.getSymbol() + "-" + Pattern.RIGHT.getSymbol();
			case 5 -> Pattern.RIGHT.getSymbol() + "-" + Pattern.RIGHT.getSymbol() + "-" + Pattern.LEFT.getSymbol();
			case 6 -> Pattern.RIGHT.getSymbol() + "-" + Pattern.LEFT.getSymbol() + "-" + Pattern.RIGHT.getSymbol();
			case 7 -> Pattern.RIGHT.getSymbol() + "-" + Pattern.LEFT.getSymbol() + "-" + Pattern.LEFT.getSymbol();
			default -> "ERROR";
		};

		return Component.literal(string).withStyle(style -> style.withFont(Arcanus.MAGIC_SYMBOLS_FONT_ID));
	}

	public static ItemStack getActiveSpellBook(LivingEntity entity) {
		if(TrinketsApi.getTrinketComponent(entity).get() instanceof TrinketComponent component) {
			if(component.isEquipped(ArcanusItems.SPELL_BOOK.get()))
				return component.getEquipped(ArcanusItems.SPELL_BOOK.get()).getFirst().getB();

			if(component.isEquipped(ArcanusItems.BOOK_POUCH.get()))
				return BookPouchItem.getActiveSpellBook(component.getEquipped(ArcanusItems.BOOK_POUCH.get()).getFirst().getB());
		}

		return ItemStack.EMPTY;
	}

	public static Object2DoubleArrayMap<PrimalArcana> constructArcanaMap(double ignisArcana, double terraArcana, double aquaArcana, double aerArcana, double aetherArcana) {
		Object2DoubleArrayMap<PrimalArcana> map = new Object2DoubleArrayMap<>();

		map.put(ArcanusArcana.IGNIS.get(), ignisArcana);
		map.put(ArcanusArcana.TERRA.get(), terraArcana);
		map.put(ArcanusArcana.AQUA.get(), aquaArcana);
		map.put(ArcanusArcana.AER.get(), aerArcana);
		map.put(ArcanusArcana.AETHER.get(), aetherArcana);

		return map;
	}
}
