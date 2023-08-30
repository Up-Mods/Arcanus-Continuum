package dev.cammiescorner.arcanuscontinuum.api.entities;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;

public class ArcanusEntityAttributes {
	public static final EntityAttribute MAX_MANA = new ClampedEntityAttribute(Arcanus.translationKey("attribute.name.generic", "max_mana"), 10D, 0D, 1024D).setTracked(true);
	public static final EntityAttribute MANA_REGEN = new ClampedEntityAttribute(Arcanus.translationKey("attribute.name.generic", "mana_regen"), 0.5D, 0D, 1024D).setTracked(true);
	public static final EntityAttribute BURNOUT_REGEN = new ClampedEntityAttribute(Arcanus.translationKey("attribute.name.generic", "burnout_regen"), 0.5D, 0D, 1024D).setTracked(true);
	public static final EntityAttribute MANA_LOCK = new ClampedEntityAttribute(Arcanus.translationKey("attribute.name.generic", "mana_lock"), 0D, 0D, 1024D).setTracked(true);
	public static final EntityAttribute SPELL_POTENCY = new ClampedEntityAttribute(Arcanus.translationKey("attribute.name.generic", "spell_potency"), 1D, 0D, 1024D).setTracked(true);
	public static final EntityAttribute MAGIC_RESISTANCE = new ClampedEntityAttribute(Arcanus.translationKey("attribute.name.generic", "magic_resistance"), 1D, 0D, 1024D).setTracked(true);
}
