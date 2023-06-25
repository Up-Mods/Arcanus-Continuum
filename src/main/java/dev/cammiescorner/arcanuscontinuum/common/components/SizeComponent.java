package dev.cammiescorner.arcanuscontinuum.common.components;

import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

public class SizeComponent implements ServerTickingComponent {
	private final Entity entity;
	private int timer = 0;

	public SizeComponent(Entity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {
		if(entity instanceof PlayerEntity || (entity instanceof TameableEntity tameable && tameable.getOwnerUuid() != null)) {
			if(timer <= 0) {
				float normalHeight = ScaleTypes.HEIGHT.getDefaultBaseScale() / ScaleTypes.HEIGHT.getScaleData(entity).getBaseScale();
				Box box = new Box(entity.getBoundingBox().minX, entity.getBoundingBox().minY, entity.getBoundingBox().minZ, entity.getBoundingBox().maxX, entity.getBoundingBox().maxY * normalHeight, entity.getBoundingBox().maxZ);

				if(entity.world.isSpaceEmpty(entity, box) || ScaleTypes.HEIGHT.getScaleData(entity).getBaseScale() > ScaleTypes.HEIGHT.getDefaultBaseScale())
					resetScale();
			}
			else {
				timer--;
			}
		}
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		timer = tag.getInt("Timer");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putInt("Timer", timer);
	}

	public void setScale(float scale, double strength) {
		ScaleData heightData = ScaleTypes.HEIGHT.getScaleData(entity);
		ScaleData widthData = ScaleTypes.WIDTH.getScaleData(entity);
		ScaleData reachData = ScaleTypes.REACH.getScaleData(entity);
		ScaleData speedData = ScaleTypes.MOTION.getScaleData(entity);

		heightData.setScaleTickDelay(10);
		widthData.setScaleTickDelay(10);
		reachData.setScaleTickDelay(10);
		speedData.setScaleTickDelay(10);

		heightData.setTargetScale((float) MathHelper.clamp(heightData.getBaseScale() * scale, 0.0625, 6));
		widthData.setTargetScale((float) MathHelper.clamp(widthData.getBaseScale() * scale, 0.0625, 6));
		reachData.setTargetScale((float) MathHelper.clamp(reachData.getBaseScale() * scale, 0.0625, 6));
		speedData.setTargetScale((float) MathHelper.clamp(speedData.getBaseScale() * scale, 0.0625, 6));

		timer = (int) Math.round(100 * strength);
	}

	public void resetScale() {
		ScaleData heightData = ScaleTypes.HEIGHT.getScaleData(entity);
		ScaleData widthData = ScaleTypes.WIDTH.getScaleData(entity);
		ScaleData reachData = ScaleTypes.REACH.getScaleData(entity);
		ScaleData speedData = ScaleTypes.MOTION.getScaleData(entity);

		heightData.setScaleTickDelay(10);
		widthData.setScaleTickDelay(10);
		reachData.setScaleTickDelay(10);
		speedData.setScaleTickDelay(10);

		heightData.setTargetScale(ScaleTypes.HEIGHT.getDefaultBaseScale());
		widthData.setTargetScale(ScaleTypes.WIDTH.getDefaultBaseScale());
		reachData.setTargetScale(ScaleTypes.REACH.getDefaultBaseScale());
		speedData.setTargetScale(ScaleTypes.MOTION.getDefaultBaseScale());

		timer = 0;
	}
}
