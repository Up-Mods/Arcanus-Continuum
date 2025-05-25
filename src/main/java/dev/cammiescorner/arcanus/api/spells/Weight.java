package dev.cammiescorner.arcanus.api.spells;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringRepresentable;

public enum Weight implements StringRepresentable {
	NONE(0, "none"), VERY_LIGHT(0.15, "very light"), LIGHT(0.3, "light"), MEDIUM(0.45, "medium"), HEAVY(0.6, "heavy"), VERY_HEAVY(0.75, "very heavy");

	public static final Codec<Weight> CODEC = RecordCodecBuilder.create(weightInstance -> weightInstance.group(
		Codec.STRING.fieldOf("Name").forGetter(Weight::getSerializedName)).apply(weightInstance, Weight::valueOf)
	);
	private final double speed;
	private final String name;

	Weight(double slowdown, String name) {
		this.speed = slowdown;
		this.name = name;
	}

	public double getSlowdown() {
		return -speed;
	}

	@Override
	public String getSerializedName() {
		return name;
	}
}
