package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.common.effects.ArcanusStatusEffect;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.registry.RegistryKeys;

public class ArcanusStatusEffects {
	public static final RegistryHandler<StatusEffect> STATUS_EFFECTS = RegistryHandler.create(RegistryKeys.STATUS_EFFECT, Arcanus.MOD_ID);

	public static final RegistrySupplier<StatusEffect> MANA_LOCK = STATUS_EFFECTS.register("mana_lock", () -> new ArcanusStatusEffect(StatusEffectType.HARMFUL, 0xa89d9b).addAttributeModifier(ArcanusEntityAttributes.MANA_LOCK.get(), "c5fa384f-c7f3-479b-9448-2843ff80a588", 7, EntityAttributeModifier.Operation.ADDITION));
	public static final RegistrySupplier<StatusEffect> VULNERABILITY = STATUS_EFFECTS.register("vulnerability", () -> new ArcanusStatusEffect(StatusEffectType.HARMFUL, 0x3a8e99));
	public static final RegistrySupplier<StatusEffect> FORTIFY = STATUS_EFFECTS.register("fortify", () -> new ArcanusStatusEffect(StatusEffectType.BENEFICIAL, 0xbbbbbb));
	public static final RegistrySupplier<StatusEffect> BOUNCY = STATUS_EFFECTS.register("bouncy", () -> new ArcanusStatusEffect(StatusEffectType.NEUTRAL, 0x77ff88));
	public static final RegistrySupplier<StatusEffect> ANONYMITY = STATUS_EFFECTS.register("anonymity", () -> new ArcanusStatusEffect(StatusEffectType.NEUTRAL, 0x555555));
	public static final RegistrySupplier<StatusEffect> COPPER_CURSE = STATUS_EFFECTS.register("copper_curse", () -> new ArcanusStatusEffect(StatusEffectType.HARMFUL, 0xc15a36));
	public static final RegistrySupplier<StatusEffect> DISCOMBOBULATE = STATUS_EFFECTS.register("discombobulate", () -> new ArcanusStatusEffect(StatusEffectType.HARMFUL, 0x7b1375));
	public static final RegistrySupplier<StatusEffect> TEMPORAL_DILATION = STATUS_EFFECTS.register("temporal_dilation", () -> new ArcanusStatusEffect(StatusEffectType.BENEFICIAL, 0x68e1ff));
	public static final RegistrySupplier<StatusEffect> ANTI_GRAVITY = STATUS_EFFECTS.register("anti_gravity", () -> new ArcanusStatusEffect(StatusEffectType.NEUTRAL, 0xceffff));
	public static final RegistrySupplier<StatusEffect> MANA_WINGS = STATUS_EFFECTS.register("mana_wings", () -> new ArcanusStatusEffect(StatusEffectType.BENEFICIAL, 0x716e8c));
}
