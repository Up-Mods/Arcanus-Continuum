package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.common.effects.ArcanusStatusEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;

public class ArcanusStatusEffects {
	//-----Status Effect Map-----//
	public static final LinkedHashMap<StatusEffect, Identifier> STATUS_EFFECTS = new LinkedHashMap<>();

	//-----Status Effects-----//
	public static final StatusEffect MANA_LOCK = create("mana_lock", new ArcanusStatusEffect(StatusEffectType.HARMFUL, 0xa89d9b).addAttributeModifier(ArcanusEntityAttributes.MANA_LOCK, "c5fa384f-c7f3-479b-9448-2843ff80a588", 7, EntityAttributeModifier.Operation.ADDITION));
	public static final StatusEffect VULNERABILITY = create("vulnerability", new ArcanusStatusEffect(StatusEffectType.HARMFUL, 0x3a8e99));
	public static final StatusEffect FORTIFY = create("fortify", new ArcanusStatusEffect(StatusEffectType.BENEFICIAL, 0xbbbbbb));
	public static final StatusEffect BOUNCY = create("bouncy", new ArcanusStatusEffect(StatusEffectType.NEUTRAL, 0x77ff88));
	public static final StatusEffect ANONYMITY = create("anonymity", new ArcanusStatusEffect(StatusEffectType.NEUTRAL, 0x555555));

	//-----Registry-----//
	public static void register() {
		STATUS_EFFECTS.keySet().forEach(item -> Registry.register(Registries.STATUS_EFFECT, STATUS_EFFECTS.get(item), item));
	}

	private static <T extends StatusEffect> T create(String name, T statusEffect) {
		STATUS_EFFECTS.put(statusEffect, Arcanus.id(name));
		return statusEffect;
	}
}
