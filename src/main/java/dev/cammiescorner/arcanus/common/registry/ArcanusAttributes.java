package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.entity.ArcanusRangedAttribute;
import dev.upcraft.sparkweave.api.platform.services.RegistryService;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.jetbrains.annotations.ApiStatus;

public class ArcanusAttributes {
	private static volatile boolean isInitialized = false;
	private static final RegistryHandler<Attribute> ENTITY_ATTRIBUTES = RegistryHandler.create(Registries.ATTRIBUTE, Arcanus.MOD_ID);

	public static final RegistrySupplier<Attribute> RED_MANA = ENTITY_ATTRIBUTES.register("red_mana", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.red_mana", 25d, 0d, 10000d).setSyncable(true));
	public static final RegistrySupplier<Attribute> GREEN_MANA = ENTITY_ATTRIBUTES.register("green_mana", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.green_mana", 25d, 0d, 10000d).setSyncable(true));
	public static final RegistrySupplier<Attribute> BLUE_MANA = ENTITY_ATTRIBUTES.register("blue_mana", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.blue_mana", 25d, 0d, 10000d).setSyncable(true));
	public static final RegistrySupplier<Attribute> WHITE_MANA = ENTITY_ATTRIBUTES.register("white_mana", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.white_mana", 25d, 0d, 10000d).setSyncable(true));
	public static final RegistrySupplier<Attribute> BLACK_MANA = ENTITY_ATTRIBUTES.register("black_mana", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.black_mana", 25d, 0d, 10000d).setSyncable(true));
	public static final RegistrySupplier<Attribute> MANA_LOCK = ENTITY_ATTRIBUTES.register("mana_lock", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.mana_lock", 0d, 0d, 10000d).setSyncable(true));
	public static final RegistrySupplier<Attribute> MANA_REGEN = ENTITY_ATTRIBUTES.register("mana_regen", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.mana_regen", 0d, 0d, 1024d).setSyncable(true));
	public static final RegistrySupplier<Attribute> SPELL_POTENCY = ENTITY_ATTRIBUTES.register("spell_potency", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.spell_potency", 1d, 0d, 5d).setSyncable(true));
	public static final RegistrySupplier<Attribute> MANA_COST = ENTITY_ATTRIBUTES.register("mana_cost", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.mana_cost", 1d, 0d, 1024d).invertStyling().setSyncable(true));
	public static final RegistrySupplier<Attribute> MAGIC_RESISTANCE = ENTITY_ATTRIBUTES.register("magic_resistance", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.magic_resistance", 1d, 0d, 1024d).setSyncable(true));
	public static final RegistrySupplier<Attribute> SPELL_COOL_DOWN = ENTITY_ATTRIBUTES.register("spell_cool_down", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.spell_cool_down", 1d, 0d, 1024d).invertStyling().setSyncable(true));

	@ApiStatus.Internal
	public static synchronized void registerAll() {
		if(!isInitialized) {
			ArcanusAttributes.ENTITY_ATTRIBUTES.accept(RegistryService.get());
			isInitialized = true;
		}
	}
}
