package dev.cammiescorner.arcanuscontinuum.common.compat;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import virtuoel.pehkui.api.ScaleModifier;
import virtuoel.pehkui.api.ScaleModifiers;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.api.ScaleType;

public class PehkuiCompat {
	public static final ScaleType SIZE = create("size", ScaleModifiers.BASE_MULTIPLIER, ScaleModifiers.HEIGHT_MULTIPLIER, ScaleModifiers.WIDTH_MULTIPLIER);

	private static ScaleType create(String id, ScaleModifier valueModifier, ScaleModifier... dependantModifiers) {
		ScaleType.Builder builder = ScaleType.Builder.create().affectsDimensions();

		if(valueModifier != null)
			builder.addBaseValueModifier(valueModifier);

		for(ScaleModifier scaleModifier : dependantModifiers)
			builder.addDependentModifier(scaleModifier);

		return ScaleRegistries.register(ScaleRegistries.SCALE_TYPES, Arcanus.id(id), builder.build());
	}

	public static void register() { }
}
