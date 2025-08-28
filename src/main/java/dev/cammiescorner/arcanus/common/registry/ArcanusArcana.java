package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.api.arcana.CompoundArcana;
import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.upcraft.sparkweave.api.color.Color;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;

import java.util.stream.Stream;

public class ArcanusArcana {
	public static final RegistryHandler<Arcana> ARCANA = RegistryHandler.create(ArcanusRegistries.ARCANA, Arcanus.MOD_ID);
	public static final Registry<Arcana> REGISTRY = ARCANA.createNewRegistry(true, Arcanus.id("nil"));

	/**
	 * DO NOT DELETE OR DISABLE. WILL BREAK THE ENTIRE MOD.
	 **/
	public static final RegistrySupplier<Arcana> NIL = ARCANA.register("nil", () -> new Arcana() {
		@Override
		public ChatFormatting formatting() {
			return ChatFormatting.BLACK;
		}

		@Override
		public Color color() {
			return Color.fromARGB(0x00000000);
		}
	});

	// Primal Arcana
	public static final RegistrySupplier<PrimalArcana> IGNIS = ARCANA.register("ignis", () -> new PrimalArcana(ArcanusAttributes.IGNIS_ARCANA.holder(), ArcanusAttributes.IGNIS_ARCANA_REGEN.holder(), ChatFormatting.RED, Color.fromRGB(184, 28, 14)));
	public static final RegistrySupplier<PrimalArcana> TERRA = ARCANA.register("terra", () -> new PrimalArcana(ArcanusAttributes.TERRA_ARCANA.holder(), ArcanusAttributes.TERRA_ARCANA_REGEN.holder(), ChatFormatting.GREEN, Color.fromRGB(54, 124, 38)));
	public static final RegistrySupplier<PrimalArcana> AQUA = ARCANA.register("aqua", () -> new PrimalArcana(ArcanusAttributes.AQUA_ARCANA.holder(), ArcanusAttributes.AQUA_ARCANA_REGEN.holder(), ChatFormatting.BLUE, Color.fromRGB(6, 51, 141)));
	public static final RegistrySupplier<PrimalArcana> AER = ARCANA.register("aer", () -> new PrimalArcana(ArcanusAttributes.AER_ARCANA.holder(), ArcanusAttributes.AER_ARCANA_REGEN.holder(), ChatFormatting.WHITE, Color.fromRGB(255, 251, 213)));
	public static final RegistrySupplier<PrimalArcana> AETHER = ARCANA.register("aether", () -> new PrimalArcana(ArcanusAttributes.AETHER_ARCANA.holder(), ArcanusAttributes.AETHER_ARCANA_REGEN.holder(), ChatFormatting.DARK_PURPLE, Color.fromRGB(114, 37, 118)));

	// Compound Arcana
	// TODO do the colors for the compound arcana nerd
	public static final RegistrySupplier<CompoundArcana> METALLUM = ARCANA.register("metallum", () -> new CompoundArcana(IGNIS, TERRA, ChatFormatting.GRAY, Color.fromRGB(128, 128, 128)));
	public static final RegistrySupplier<CompoundArcana> ANIMA = ARCANA.register("anima", () -> new CompoundArcana(IGNIS, AQUA, ChatFormatting.GRAY, Color.fromRGB(128, 128, 128)));
	public static final RegistrySupplier<CompoundArcana> LUX = ARCANA.register("lux", () -> new CompoundArcana(IGNIS, AER, ChatFormatting.GRAY, Color.fromRGB(128, 128, 128)));
	public static final RegistrySupplier<CompoundArcana> RUINA = ARCANA.register("ruina", () -> new CompoundArcana(IGNIS, AETHER, ChatFormatting.GRAY, Color.fromRGB(128, 128, 128)));
	public static final RegistrySupplier<CompoundArcana> HERBA = ARCANA.register("herba", () -> new CompoundArcana(TERRA, AQUA, ChatFormatting.GRAY, Color.fromRGB(128, 128, 128)));
	public static final RegistrySupplier<CompoundArcana> CRYSTALLUM = ARCANA.register("crystallum", () -> new CompoundArcana(TERRA, AER, ChatFormatting.GRAY, Color.fromRGB(128, 128, 128)));
	public static final RegistrySupplier<CompoundArcana> VIS = ARCANA.register("vis", () -> new CompoundArcana(TERRA, AETHER, ChatFormatting.GRAY, Color.fromRGB(128, 128, 128)));
	public static final RegistrySupplier<CompoundArcana> TBD = ARCANA.register("tbd", () -> new CompoundArcana(AQUA, AER, ChatFormatting.GRAY, Color.fromRGB(128, 128, 128)));
	public static final RegistrySupplier<CompoundArcana> AEVUM = ARCANA.register("aevum", () -> new CompoundArcana(AQUA, AETHER, ChatFormatting.GRAY, Color.fromRGB(128, 128, 128)));
	public static final RegistrySupplier<CompoundArcana> SPATIUM = ARCANA.register("spatium", () -> new CompoundArcana(AER, AETHER, ChatFormatting.GRAY, Color.fromRGB(128, 128, 128)));

	public static Stream<PrimalArcana> primalArcana() {
		return REGISTRY.stream().filter(arcana -> arcana instanceof PrimalArcana).map(arcana -> (PrimalArcana) arcana);
	}
}
