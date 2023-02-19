package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;

public class ArcanusSoundEvents {
	//-----Sound Map-----//
	public static final LinkedHashMap<SoundEvent, Identifier> SOUNDS = new LinkedHashMap<>();

	//-----Sound Events-----//
	public static final SoundEvent SMITE = create("smite");

	//-----Registry-----//
	public static void register() {
		SOUNDS.keySet().forEach(sound -> Registry.register(Registries.SOUND_EVENT, SOUNDS.get(sound), sound));
	}

	private static SoundEvent create(String name) {
		SoundEvent sound = SoundEvent.createVariableRangeEvent(Arcanus.id(name));
		SOUNDS.put(sound, Arcanus.id(name));
		return sound;
	}

	private static SoundEvent create(String name, float range) {
		SoundEvent sound = SoundEvent.createFixedRangeEvent(Arcanus.id(name), range);
		SOUNDS.put(sound, Arcanus.id(name));
		return sound;
	}
}
