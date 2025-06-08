package dev.cammiescorner.arcanus.api.entities;

import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class ArcanusRangedAttribute extends RangedAttribute {
	private boolean invertStyling = false;

	public ArcanusRangedAttribute(String descriptionId, double defaultValue, double min, double max) {
		super(descriptionId, defaultValue, min, max);
	}

	@Override
	public ChatFormatting getStyle(boolean isPositive) {
		return super.getStyle(invertStyling != isPositive);
	}

	public Attribute invertStyling() {
		invertStyling = true;
		return this;
	}
}
