package dev.cammiescorner.arcanus.common.util;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.entity.Targetable;
import dev.cammiescorner.arcanus.common.components.MagicColorComponent;
import dev.cammiescorner.arcanus.common.data.ArcanusEntityTags;
import dev.cammiescorner.arcanus.common.entity.magic.TemporalDilationField;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.util.supporters.WizardData;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ArcanusHelper {
	public static boolean shouldTimeDilate(Entity target, Level level) {
		return !target.getType().is(ArcanusEntityTags.TEMPORAL_DILATION_IMMUNE) && !level.getEntities(target, target.getBoundingBox(), entity -> entity instanceof TemporalDilationField && entity.position().add(0, 4.5, 0).distanceTo(target.position()) <= entity.getBbWidth() / 2).isEmpty();
	}

	public static Color getMagicColor(@Nullable Object provider) {
		if(provider == null) {
			return Arcanus.DEFAULT_MAGIC_COLOR;
		}

		var component = ArcanusComponents.MAGIC_COLOR.getNullable(provider);
		if(component != null) {
			return component.getColor();
		}

		// if Entity
		if(provider instanceof TraceableEntity ownable) {
			var owner = ownable.getOwner();
			if(owner != null) {
				return getMagicColor(owner);
			}
		}

		return Arcanus.DEFAULT_MAGIC_COLOR;
	}

	public static Color getPocketDimensionColor(@Nullable Object provider) {
		if(provider == null) {
			return Arcanus.DEFAULT_MAGIC_COLOR;
		}

		var component = ArcanusComponents.MAGIC_COLOR.getNullable(provider);
		if(component != null) {
			return component.getPocketDimensionColor();
		}

		// if Entity
		if(provider instanceof TraceableEntity ownable) {
			var owner = ownable.getOwner();
			if(owner != null) {
				return getPocketDimensionColor(owner);
			}
		}

		return Arcanus.DEFAULT_MAGIC_COLOR;
	}

	public static Color getMagicColor(@Nullable UUID playerId) {
		if(playerId == null || Util.NIL_UUID.equals(playerId)) {
			return Arcanus.DEFAULT_MAGIC_COLOR;
		}

		return WizardData.getOrEmpty(playerId).magicColor();
	}

	public static Color getPocketDimensionColor(@Nullable UUID playerId) {
		if(playerId == null || Util.NIL_UUID.equals(playerId)) {
			return Arcanus.DEFAULT_MAGIC_COLOR;
		}

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
}
