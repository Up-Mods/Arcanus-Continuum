package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.api.arcana.CompoundArcana;
import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.upcraft.sparkweave.api.color.Color;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.Registry;

import java.util.stream.Stream;

public class ArcanusArcana {
	public static final RegistryHandler<Arcana> ARCANA = RegistryHandler.create(ArcanusRegistries.ARCANA, Arcanus.MOD_ID);
	public static final Registry<Arcana> REGISTRY = ARCANA.createNewRegistry(true, Arcanus.id("nil"));

	/**
	 * DO NOT DELETE OR DISABLE. WILL BREAK THE ENTIRE MOD.
	 **/
	public static final RegistrySupplier<Arcana> NIL = ARCANA.register("nil", () -> () -> Color.fromARGB(0x00000000));

	// Primal Arcana
	public static final RegistrySupplier<PrimalArcana> IGNIS = ARCANA.register("ignis", () -> new PrimalArcana(ArcanusAttributes.IGNIS_ARCANA.holder(), ArcanusAttributes.IGNIS_ARCANA_REGEN.holder(), Color.fromARGB(0xffb81c0e)));
	public static final RegistrySupplier<PrimalArcana> TERRA = ARCANA.register("terra", () -> new PrimalArcana(ArcanusAttributes.TERRA_ARCANA.holder(), ArcanusAttributes.TERRA_ARCANA_REGEN.holder(), Color.fromARGB(0xff367c26)));
	public static final RegistrySupplier<PrimalArcana> AQUA = ARCANA.register("aqua", () -> new PrimalArcana(ArcanusAttributes.AQUA_ARCANA.holder(), ArcanusAttributes.AQUA_ARCANA_REGEN.holder(), Color.fromARGB(0xff06338d)));
	public static final RegistrySupplier<PrimalArcana> AER = ARCANA.register("aer", () -> new PrimalArcana(ArcanusAttributes.AER_ARCANA.holder(), ArcanusAttributes.AER_ARCANA_REGEN.holder(), Color.fromARGB(0xfffffbd5)));
	public static final RegistrySupplier<PrimalArcana> AETHER = ARCANA.register("aether", () -> new PrimalArcana(ArcanusAttributes.AETHER_ARCANA.holder(), ArcanusAttributes.AETHER_ARCANA_REGEN.holder(), Color.fromARGB(0xff722576)));

	// Compound Arcana
	public static final RegistrySupplier<CompoundArcana> METALLUM = ARCANA.register("metallum", () -> new CompoundArcana(IGNIS, TERRA, Color.fromARGB(0xffb5b5b5)));
	public static final RegistrySupplier<CompoundArcana> ANIMA = ARCANA.register("anima", () -> new CompoundArcana(IGNIS, AQUA, Color.fromARGB(0xff7a5b30)));
	public static final RegistrySupplier<CompoundArcana> LUX = ARCANA.register("lux", () -> new CompoundArcana(IGNIS, AER, Color.fromARGB(0xffffff6a)));
	public static final RegistrySupplier<CompoundArcana> RUINA = ARCANA.register("ruina", () -> new CompoundArcana(IGNIS, AETHER, Color.fromARGB(0xff690d0d)));
	public static final RegistrySupplier<CompoundArcana> HERBA = ARCANA.register("herba", () -> new CompoundArcana(TERRA, AQUA, Color.fromARGB(0xff2ac500)));
	public static final RegistrySupplier<CompoundArcana> CRYSTALLUM = ARCANA.register("crystallum", () -> new CompoundArcana(TERRA, AER, Color.fromARGB(0xff27e7e1)));
	public static final RegistrySupplier<CompoundArcana> VIS = ARCANA.register("vis", () -> new CompoundArcana(TERRA, AETHER, Color.fromARGB(0xffc60098)));
	public static final RegistrySupplier<CompoundArcana> IMUM = ARCANA.register("imum", () -> new CompoundArcana(AQUA, AER, Color.fromARGB(0xff111431)));
	public static final RegistrySupplier<CompoundArcana> AEVUM = ARCANA.register("aevum", () -> new CompoundArcana(AQUA, AETHER, Color.fromARGB(0xffe6e6e6)));
	public static final RegistrySupplier<CompoundArcana> SPATIUM = ARCANA.register("spatium", () -> new CompoundArcana(AER, AETHER, Color.fromARGB(0xff0a0a0a)));

	public static Stream<PrimalArcana> primalArcana() {
		return REGISTRY.stream().filter(arcana -> arcana instanceof PrimalArcana).map(arcana -> (PrimalArcana) arcana);
	}

	public static Stream<CompoundArcana> compoundArcana() {
		return REGISTRY.stream().filter(arcana -> arcana instanceof CompoundArcana).map(arcana -> (CompoundArcana) arcana);
	}
}
