package dev.cammiescorner.arcanuscontinuum.common.components;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;

public class BoltTargetComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity entity;
	private Vec3d pos = Vec3d.ZERO;
	private int age;
	private boolean shouldRender;

	public BoltTargetComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {
		if(age >= 6 && shouldRender)
			setShouldRender(false);

		age++;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		pos = new Vec3d(tag.getDouble("PosX"), tag.getDouble("PosY"), tag.getDouble("PosZ"));
		shouldRender = tag.getBoolean("ShouldRender");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putDouble("PosX", pos.getX());
		tag.putDouble("PosY", pos.getY());
		tag.putDouble("PosZ", pos.getZ());
		tag.putBoolean("ShouldRender", shouldRender);
	}

	public Vec3d getPos() {
		return pos;
	}

	public void setPos(Vec3d pos) {
		this.pos = pos;
		ArcanusComponents.BOLT_TARGET.sync(entity);
	}

	public boolean shouldRender() {
		return shouldRender;
	}

	public void setShouldRender(boolean shouldRender) {
		this.shouldRender = shouldRender;
		ArcanusComponents.BOLT_TARGET.sync(entity);
	}

	public void setAge(int age) {
		this.age = age;
	}
}
