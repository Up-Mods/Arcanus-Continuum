package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.effect.ArcanusStatusEffect;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ArcanusMobEffects {
	public static final RegistryHandler<MobEffect> MOB_EFFECTS = RegistryHandler.create(Registries.MOB_EFFECT, Arcanus.MOD_ID);

	public static final RegistrySupplier<MobEffect> MANA_LOCK = MOB_EFFECTS.register("arcana_lock", () -> new ArcanusStatusEffect(MobEffectCategory.HARMFUL, 0xa89d9b).addAttributeModifier(ArcanusAttributes.ARCANA_LOCK.holder(), Arcanus.id("arcana_lock_potion"), 7, AttributeModifier.Operation.ADD_VALUE));
	public static final RegistrySupplier<MobEffect> VULNERABILITY = MOB_EFFECTS.register("vulnerability", () -> new ArcanusStatusEffect(MobEffectCategory.HARMFUL, 0x3a8e99));
	public static final RegistrySupplier<MobEffect> FORTIFY = MOB_EFFECTS.register("fortify", () -> new ArcanusStatusEffect(MobEffectCategory.BENEFICIAL, 0xbbbbbb));
	public static final RegistrySupplier<MobEffect> BOUNCY = MOB_EFFECTS.register("bouncy", () -> new ArcanusStatusEffect(MobEffectCategory.NEUTRAL, 0x77ff88));
	public static final RegistrySupplier<MobEffect> ANONYMITY = MOB_EFFECTS.register("anonymity", () -> new ArcanusStatusEffect(MobEffectCategory.NEUTRAL, 0x555555, true, false));
	public static final RegistrySupplier<MobEffect> DISCOMBOBULATE = MOB_EFFECTS.register("discombobulate", () -> new ArcanusStatusEffect(MobEffectCategory.HARMFUL, 0x7b1375));
	public static final RegistrySupplier<MobEffect> FLOAT = MOB_EFFECTS.register("float", () -> new ArcanusStatusEffect(MobEffectCategory.NEUTRAL, 0xceffff));
	public static final RegistrySupplier<MobEffect> DANGER_SENSE = MOB_EFFECTS.register("danger_sense", () -> new ArcanusStatusEffect(MobEffectCategory.BENEFICIAL, 0xaeeff2));
	public static final RegistrySupplier<MobEffect> SHRINK = MOB_EFFECTS.register("shrink", () -> new ArcanusStatusEffect(MobEffectCategory.NEUTRAL, 0x00ffc8, false, true)
		.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, Arcanus.id("shrink_effect"), -0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
		.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, Arcanus.id("shrink_effect"), -0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
		.addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, Arcanus.id("shrink_effect"), 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
		.addAttributeModifier(Attributes.MOVEMENT_SPEED, Arcanus.id("shrink_effect"), -0.125, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
		.addAttributeModifier(Attributes.JUMP_STRENGTH, Arcanus.id("shrink_effect"), -0.075, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
		.addAttributeModifier(Attributes.STEP_HEIGHT, Arcanus.id("shrink_effect"), -0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
		.addAttributeModifier(Attributes.SCALE, Arcanus.id("shrink_effect"), -0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
	);
	public static final RegistrySupplier<MobEffect> ENLARGE = MOB_EFFECTS.register("enlarge", () -> new ArcanusStatusEffect(MobEffectCategory.NEUTRAL, 0xff9600, false, true)
		.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, Arcanus.id("enlarge_effect"), 0.3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
		.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, Arcanus.id("enlarge_effect"), 0.3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
		.addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, Arcanus.id("enlarge_effect"), 1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
		.addAttributeModifier(Attributes.MOVEMENT_SPEED, Arcanus.id("enlarge_effect"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
		.addAttributeModifier(Attributes.JUMP_STRENGTH, Arcanus.id("enlarge_effect"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
		.addAttributeModifier(Attributes.STEP_HEIGHT, Arcanus.id("enlarge_effect"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
		.addAttributeModifier(Attributes.SCALE, Arcanus.id("enlarge_effect"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
	);
}
