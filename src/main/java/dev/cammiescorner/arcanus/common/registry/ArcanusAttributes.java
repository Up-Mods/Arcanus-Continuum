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

	public static final RegistrySupplier<Attribute> IGNIS_ARCANA = ENTITY_ATTRIBUTES.register("ignis_arcana", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.ignis_arcana", 20d, 0d, 10000d).setSyncable(true));
	public static final RegistrySupplier<Attribute> TERRA_ARCANA = ENTITY_ATTRIBUTES.register("terra_arcana", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.terra_arcana", 20d, 0d, 10000d).setSyncable(true));
	public static final RegistrySupplier<Attribute> AQUA_ARCANA = ENTITY_ATTRIBUTES.register("aqua_arcana", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.aqua_arcana", 20d, 0d, 10000d).setSyncable(true));
	public static final RegistrySupplier<Attribute> AER_ARCANA = ENTITY_ATTRIBUTES.register("aer_arcana", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.aer_arcana", 20d, 0d, 10000d).setSyncable(true));
	public static final RegistrySupplier<Attribute> AETHER_ARCANA = ENTITY_ATTRIBUTES.register("aether_arcana", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.aether_arcana", 20d, 0d, 10000d).setSyncable(true));
	public static final RegistrySupplier<Attribute> IGNIS_ARCANA_REGEN = ENTITY_ATTRIBUTES.register("ignis_arcana_regen", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.ignis_arcana_regen", 0d, 0d, 1024d).setSyncable(true));
	public static final RegistrySupplier<Attribute> TERRA_ARCANA_REGEN = ENTITY_ATTRIBUTES.register("terra_arcana_regen", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.terra_arcana_regen", 0d, 0d, 1024d).setSyncable(true));
	public static final RegistrySupplier<Attribute> AQUA_ARCANA_REGEN = ENTITY_ATTRIBUTES.register("aqua_arcana_regen", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.aqua_arcana_regen", 0d, 0d, 1024d).setSyncable(true));
	public static final RegistrySupplier<Attribute> AER_ARCANA_REGEN = ENTITY_ATTRIBUTES.register("aer_arcana_regen", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.aer_arcana_regen", 0d, 0d, 1024d).setSyncable(true));
	public static final RegistrySupplier<Attribute> AETHER_ARCANA_REGEN = ENTITY_ATTRIBUTES.register("aether_arcana_regen", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.aether_arcana_regen", 0d, 0d, 1024d).setSyncable(true));
	public static final RegistrySupplier<Attribute> ARCANA_LOCK = ENTITY_ATTRIBUTES.register("arcana_lock", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.arcana_lock", 0d, 0d, 10000d).setSyncable(true));
	public static final RegistrySupplier<Attribute> SPELL_POTENCY = ENTITY_ATTRIBUTES.register("spell_potency", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.spell_potency", 1d, 0d, 5d).setSyncable(true));
	public static final RegistrySupplier<Attribute> MANA_COST = ENTITY_ATTRIBUTES.register("arcana_cost", () -> new ArcanusRangedAttribute("attribute.name.generic.arcanus.arcana_cost", 1d, 0d, 1024d).invertStyling().setSyncable(true));
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
