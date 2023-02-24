package dev.cammiescorner.arcanuscontinuum.common.compat;

import dev.tr7zw.firstperson.FirstPersonModelCore;
import dev.tr7zw.firstperson.api.FirstPersonAPI;

import java.util.function.BooleanSupplier;

public class FirstPersonCompat {
	public static BooleanSupplier isEnabled() {
		return FirstPersonAPI::isEnabled;
	}

	public static BooleanSupplier showVanillaHands() {
		return FirstPersonModelCore.instance::showVanillaHands;
	}
}
