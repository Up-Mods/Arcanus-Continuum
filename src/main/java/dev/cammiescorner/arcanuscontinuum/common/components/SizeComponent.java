package dev.cammiescorner.arcanuscontinuum.common.components;

import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

public class SizeComponent implements ServerTickingComponent {
	private final Entity entity;
	private long timer = 0;

	public SizeComponent(Entity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {
		if(timer > 0 && entity.world.getTime() > timer)
			resetScale();
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		timer = tag.getLong("Timer");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putLong("Timer", timer);
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

		timer = entity.world.getTime() + Math.round(100 * strength);
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

		timer = -1;
	}
}
