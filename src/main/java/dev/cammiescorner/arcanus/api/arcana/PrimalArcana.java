package dev.cammiescorner.arcanus.api.arcana;

import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

public record PrimalArcana(Holder<Attribute> arcanaAttribute, Holder<Attribute> regenAttribute, ChatFormatting formatting, Color color) implements Arcana {
	public double getMaxArcana(LivingEntity entity) {
		return entity.getAttributeValue(arcanaAttribute());
	}
}
