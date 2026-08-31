package dev.cammiescorner.arcanus.component.entity;

import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class BoltTargetComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity entity;
	private Vec3 pos = Vec3.ZERO;
	private int age;
	private boolean shouldRender;

	public BoltTargetComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {
		if(age >= 3 && shouldRender)
			setShouldRender(false);

		age++;
	}

	@Override
	public void readData(ValueInput readView) {
		pos = readView.read("Position", Vec3.CODEC).orElse(Vec3.ZERO);
		shouldRender = readView.getBooleanOr("ShouldRender", false);
	}

	@Override
	public void writeData(ValueOutput writeView) {
		writeView.store("Position", Vec3.CODEC, pos);
		writeView.putBoolean("ShouldRender", shouldRender);
	}

	public Vec3 getPos() {
		return pos;
	}

	public void setPos(Vec3 pos) {
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
