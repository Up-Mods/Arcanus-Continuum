package dev.cammiescorner.arcanuscontinuum;

import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.api.spells.Pattern;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellComponent;
import dev.cammiescorner.arcanuscontinuum.common.compat.ArcanusConfig;
import dev.cammiescorner.arcanuscontinuum.common.packets.c2s.CastSpellPacket;
import dev.cammiescorner.arcanuscontinuum.common.packets.c2s.SaveBookDataPacket;
import dev.cammiescorner.arcanuscontinuum.common.packets.c2s.SetCastingPacket;
import dev.cammiescorner.arcanuscontinuum.common.packets.c2s.SyncPatternPacket;
import dev.cammiescorner.arcanuscontinuum.common.packets.s2c.SyncStatusEffectPacket;
import dev.cammiescorner.arcanuscontinuum.common.packets.s2c.SyncSupporterData;
import dev.cammiescorner.arcanuscontinuum.common.registry.*;
import dev.cammiescorner.arcanuscontinuum.common.util.SupporterData;
import eu.midnightdust.lib.config.MidnightConfig;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.ModMetadata;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.quiltmc.qsl.lifecycle.api.event.ServerLifecycleEvents;
import org.quiltmc.qsl.networking.api.EntityTrackingEvents;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.resource.loader.api.ResourceLoaderEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Arcanus implements ModInitializer {
	public static final String URL = "https://cammiescorner.dev/data/supporters.json";
	public static final String MOD_ID = "arcanuscontinuum";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##,####.##");
	public static final DefaultedRegistry<SpellComponent> SPELL_COMPONENTS = FabricRegistryBuilder.createDefaulted(SpellComponent.class, id("spell_components"), id("empty")).buildAndRegister();
	public static final SupporterStorage STORAGE = new SupporterStorage();
	public static final int DEFAULT_MAGIC_COLOUR = 0x68e1ff;

	public static int getMagicColour(UUID playerUuid) {
		SupporterData.Supporter supporter = getSupporters().get(playerUuid);

		if(supporter != null)
			return supporter.magicColour();

		return DEFAULT_MAGIC_COLOUR;
	}

	public static class SupporterStorage {
		public final Map<UUID, SupporterData.Supporter> supporters = new Object2ObjectOpenHashMap<>();
		public Instant lastRefreshedSupporters = Instant.EPOCH;
	}

	@Override
	public void onInitialize(ModContainer mod) {
		MidnightConfig.init(MOD_ID, ArcanusConfig.class);

		Registry.register(Registry.ATTRIBUTE, id("max_mana"), ArcanusEntityAttributes.MAX_MANA);
		Registry.register(Registry.ATTRIBUTE, id("mana_regen"), ArcanusEntityAttributes.MANA_REGEN);
		Registry.register(Registry.ATTRIBUTE, id("burnout_regen"), ArcanusEntityAttributes.BURNOUT_REGEN);
		Registry.register(Registry.ATTRIBUTE, id("mana_lock"), ArcanusEntityAttributes.MANA_LOCK);
		Registry.register(Registry.ATTRIBUTE, id("spell_potency"), ArcanusEntityAttributes.SPELL_POTENCY);
		Registry.register(Registry.ATTRIBUTE, id("magic_resistance"), ArcanusEntityAttributes.MAGIC_RESISTANCE);

		ArcanusEntities.register();
		ArcanusBlocks.register();
		ArcanusBlockEntities.register();
		ArcanusItems.register();
		ArcanusStatusEffects.register();
		ArcanusSpellComponents.register();
		ArcanusRecipes.register();
		ArcanusScreenHandlers.register();
		ArcanusCommands.register();
		ArcanusPointsOfInterest.register();

		ServerPlayNetworking.registerGlobalReceiver(CastSpellPacket.ID, CastSpellPacket::handler);
		ServerPlayNetworking.registerGlobalReceiver(SetCastingPacket.ID, SetCastingPacket::handler);
		ServerPlayNetworking.registerGlobalReceiver(SaveBookDataPacket.ID, SaveBookDataPacket::handler);
		ServerPlayNetworking.registerGlobalReceiver(SyncPatternPacket.ID, SyncPatternPacket::handler);

		CommandRegistrationCallback.EVENT.register(ArcanusCommands::init);

		ServerLifecycleEvents.STARTING.register(server -> Arcanus.refreshSupporterData(server, true));

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			Arcanus.refreshSupporterData(server, false);
			SyncSupporterData.send(handler.player);
			SyncStatusEffectPacket.sendToAll(handler.player, ArcanusStatusEffects.ANONYMITY, handler.player.hasStatusEffect(ArcanusStatusEffects.ANONYMITY));
		});

		ResourceLoaderEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, error) -> {
			if(server != null)
				Arcanus.refreshSupporterData(server, true);
		});

		EntityTrackingEvents.START_TRACKING.register((trackedEntity, player) -> {
			if(trackedEntity instanceof ServerPlayerEntity playerEntity)
				SyncStatusEffectPacket.sendTo(player, playerEntity, ArcanusStatusEffects.ANONYMITY, playerEntity.hasStatusEffect(ArcanusStatusEffects.ANONYMITY));
		});
	}

	public static void refreshSupporterData(MinecraftServer server, boolean force) {
		Instant currentTime = Instant.now();

		if(force || currentTime.isAfter(STORAGE.lastRefreshedSupporters.plus(10, TimeUnit.MINUTES.toChronoUnit()))) {
			CompletableFuture.supplyAsync(() -> {
				LOGGER.info("Updating supporter data...");

				try {
					HttpsURLConnection connection = (HttpsURLConnection) new URL(URL).openConnection();
					ModMetadata metadata = QuiltLoader.getModContainer(MOD_ID).orElseThrow().metadata();
					connection.setRequestProperty("User-Agent", metadata.id() + "/" + metadata.version().raw());

					try(InputStream stream = connection.getInputStream()) {
						STORAGE.lastRefreshedSupporters = currentTime;
						return Arrays.asList(SupporterData.fromJson(stream).supporters);
					}
				}
				catch(Exception e) {
					LOGGER.error("Failed to read supporter JSON!", e);
					return List.<SupporterData.Supporter>of();
				}
			}).thenAcceptAsync(supporters -> {
				STORAGE.supporters.clear();
				supporters.forEach(supporter -> STORAGE.supporters.put(supporter.uuid(), supporter));
				SyncSupporterData.sendToAll(server);
			}, server);
		}
	}

	public static Map<UUID, SupporterData.Supporter> getSupporters() {
		return STORAGE.supporters;
	}

	public static Identifier id(String name) {
		return new Identifier(MOD_ID, name);
	}

	public static MutableText translate(@Nullable String prefix, String... value) {
		String translationKey = Arcanus.MOD_ID + "." + String.join(".", value);
		return Text.translatable(prefix != null ? (prefix + "." + translationKey) : translationKey);
	}

	public static String format(double d) {
		return DECIMAL_FORMAT.format(d);
	}

	public static int getSpellIndex(List<Pattern> patternList) {
		String pattern = patternList.get(0).getLetter() + patternList.get(1).getLetter() + patternList.get(2).getLetter();

		return switch(pattern) {
			case "LLL" -> 0;
			case "LLR" -> 1;
			case "LRL" -> 2;
			case "LRR" -> 3;
			case "RRR" -> 4;
			case "RRL" -> 5;
			case "RLR" -> 6;
			case "RLL" -> 7;
			default -> 0;
		};
	}

	public static List<Pattern> getSpellPattern(int index) {
		return switch(index) {
			case 0 -> List.of(Pattern.LEFT, Pattern.LEFT, Pattern.LEFT);
			case 1 -> List.of(Pattern.LEFT, Pattern.LEFT, Pattern.RIGHT);
			case 2 -> List.of(Pattern.LEFT, Pattern.RIGHT, Pattern.LEFT);
			case 3 -> List.of(Pattern.LEFT, Pattern.RIGHT, Pattern.RIGHT);
			case 4 -> List.of(Pattern.RIGHT, Pattern.RIGHT, Pattern.RIGHT);
			case 5 -> List.of(Pattern.RIGHT, Pattern.RIGHT, Pattern.LEFT);
			case 6 -> List.of(Pattern.RIGHT, Pattern.LEFT, Pattern.RIGHT);
			case 7 -> List.of(Pattern.RIGHT, Pattern.LEFT, Pattern.LEFT);
			default -> List.of(Pattern.LEFT, Pattern.LEFT, Pattern.LEFT);
		};
	}

	public static MutableText getSpellPatternAsText(int index) {
		String text = switch(index) {
			case 0 -> Pattern.LEFT.getSymbol() + "-" + Pattern.LEFT.getSymbol() + "-" + Pattern.LEFT.getSymbol();
			case 1 -> Pattern.LEFT.getSymbol() + "-" + Pattern.LEFT.getSymbol() + "-" + Pattern.RIGHT.getSymbol();
			case 2 -> Pattern.LEFT.getSymbol() + "-" + Pattern.RIGHT.getSymbol() + "-" + Pattern.LEFT.getSymbol();
			case 3 -> Pattern.LEFT.getSymbol() + "-" + Pattern.RIGHT.getSymbol() + "-" + Pattern.RIGHT.getSymbol();
			case 4 -> Pattern.RIGHT.getSymbol() + "-" + Pattern.RIGHT.getSymbol() + "-" + Pattern.RIGHT.getSymbol();
			case 5 -> Pattern.RIGHT.getSymbol() + "-" + Pattern.RIGHT.getSymbol() + "-" + Pattern.LEFT.getSymbol();
			case 6 -> Pattern.RIGHT.getSymbol() + "-" + Pattern.LEFT.getSymbol() + "-" + Pattern.RIGHT.getSymbol();
			case 7 -> Pattern.RIGHT.getSymbol() + "-" + Pattern.LEFT.getSymbol() + "-" + Pattern.LEFT.getSymbol();
			default -> "ERROR";
		};

		return Text.literal(text);
	}

	public static MutableText getSpellInputs(List<Pattern> pattern) {
		MutableText hyphen = Text.literal("-").formatted(Formatting.GRAY);
		return getSpellInputs(pattern, 0).append(hyphen).append(getSpellInputs(pattern, 1)).append(hyphen).append(getSpellInputs(pattern, 2));
	}

	public static MutableText getSpellInputs(List<Pattern> pattern, int index) {
		return index >= pattern.size() || pattern.get(index) == null ? Text.literal("?").formatted(Formatting.GRAY, Formatting.UNDERLINE) : Text.literal(pattern.get(index).getSymbol()).formatted(Formatting.GREEN);
	}
}
