package dev.cammiescorner.arcanuscontinuum.api.entities;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.RegistryKeys;
import org.jetbrains.annotations.ApiStatus;

public class ArcanusEntityAttributes {

	@ApiStatus.Internal
	public static RegistryHandler<EntityAttribute> ENTITY_ATTRIBUTES = RegistryHandler.create(RegistryKeys.ENTITY_ATTRIBUTE, Arcanus.MOD_ID);

	public static final RegistrySupplier<EntityAttribute> MAX_MANA = ENTITY_ATTRIBUTES.register("max_mana", () -> new ClampedEntityAttribute(Arcanus.translationKey("attribute.name.generic", "max_mana"), 10D, 0D, 1024D).setTracked(true));
	public static final RegistrySupplier<EntityAttribute> MANA_REGEN = ENTITY_ATTRIBUTES.register("mana_regen", () -> new ClampedEntityAttribute(Arcanus.translationKey("attribute.name.generic", "mana_regen"), 0.5D, 0D, 1024D).setTracked(true));
	public static final RegistrySupplier<EntityAttribute> BURNOUT_REGEN = ENTITY_ATTRIBUTES.register("burnout_regen", () -> new ClampedEntityAttribute(Arcanus.translationKey("attribute.name.generic", "burnout_regen"), 0.5D, 0D, 1024D).setTracked(true));
	public static final RegistrySupplier<EntityAttribute> MANA_LOCK = ENTITY_ATTRIBUTES.register("mana_lock", () -> new ClampedEntityAttribute(Arcanus.translationKey("attribute.name.generic", "mana_lock"), 0D, 0D, 1024D).setTracked(true));
	public static final RegistrySupplier<EntityAttribute> SPELL_POTENCY = ENTITY_ATTRIBUTES.register("spell_potency", () -> new ClampedEntityAttribute(Arcanus.translationKey("attribute.name.generic", "spell_potency"), 1D, 0D, 1024D).setTracked(true));
	public static final RegistrySupplier<EntityAttribute> MAGIC_RESISTANCE = ENTITY_ATTRIBUTES.register("magic_resistance", () -> new ClampedEntityAttribute(Arcanus.translationKey("attribute.name.generic", "magic_resistance"), 1D, 0D, 1024D).setTracked(true));
}
