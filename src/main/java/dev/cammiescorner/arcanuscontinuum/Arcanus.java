package dev.cammiescorner.arcanuscontinuum;

import com.teamresourceful.resourcefulconfig.common.config.Configurator;
import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.api.spells.Pattern;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellComponent;
import dev.cammiescorner.arcanuscontinuum.common.blocks.MagicDoorBlock;
import dev.cammiescorner.arcanuscontinuum.common.blocks.entities.MagicDoorBlockEntity;
import dev.cammiescorner.arcanuscontinuum.common.packets.c2s.*;
import dev.cammiescorner.arcanuscontinuum.common.packets.s2c.SyncConfigValuesPacket;
import dev.cammiescorner.arcanuscontinuum.common.packets.s2c.SyncStatusEffectPacket;
import dev.cammiescorner.arcanuscontinuum.common.packets.s2c.SyncSupporterData;
import dev.cammiescorner.arcanuscontinuum.common.registry.*;
import dev.cammiescorner.arcanuscontinuum.common.structures.WizardTowerProcessor;
import dev.cammiescorner.arcanuscontinuum.common.util.SupporterData;
import dev.upcraft.sparkweave.api.registry.RegistryService;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.ModMetadata;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.chat.api.QuiltChatEvents;
import org.quiltmc.qsl.chat.api.QuiltMessageType;
import org.quiltmc.qsl.chat.api.types.ChatC2SMessage;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.quiltmc.qsl.lifecycle.api.event.ServerLifecycleEvents;
import org.quiltmc.qsl.networking.api.EntityTrackingEvents;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.registry.api.event.RegistryEvents;
import org.quiltmc.qsl.resource.loader.api.ResourceLoaderEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Arcanus implements ModInitializer {
	public static final Configurator configurator = new Configurator();
	public static final String URL = "https://cammiescorner.dev/data/supporters.json";
	public static final String MOD_ID = "arcanuscontinuum";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##,####.##");

	public static final RegistryKey<Registry<SpellComponent>> SPELL_COMPONENTS_REGISTRY_KEY = RegistryKey.ofRegistry(id("spell_components"));
	public static final DefaultedRegistry<SpellComponent> SPELL_COMPONENTS = FabricRegistryBuilder.createDefaulted(SPELL_COMPONENTS_REGISTRY_KEY, id("empty")).buildAndRegister();
	public static final StructureProcessorType<WizardTowerProcessor> WIZARD_TOWER_PROCESSOR = StructureProcessorType.register(Arcanus.id("wizard_tower_processor").toString(), WizardTowerProcessor.CODEC);
	public static final StructureProcessorList WIZARD_TOWER_PROCESSOR_LIST = new StructureProcessorList(List.of(WizardTowerProcessor.INSTANCE));
	public static final SupporterStorage STORAGE = new SupporterStorage();
	public static final int DEFAULT_MAGIC_COLOUR = 0x68e1ff;

	public static BooleanSupplier supporterCheck = () -> false;

	public static int getMagicColour(UUID playerUuid) {
		SupporterData.Supporter supporter = getSupporters().get(playerUuid);

		if(supporter != null)
			return supporter.magicColour();

		return DEFAULT_MAGIC_COLOUR;
	}

	public static boolean isCurrentPlayerSupporter() {
		return supporterCheck.getAsBoolean();
	}

	public static boolean isPlayerSupporter(UUID uuid) {
		return getSupporters().containsKey(uuid);
	}

	public static class SupporterStorage {
		public final Map<UUID, SupporterData.Supporter> supporters = new Object2ObjectOpenHashMap<>();
		public Instant lastRefreshedSupporters = Instant.EPOCH;
	}

	@Override
	public void onInitialize(ModContainer mod) {
		configurator.registerConfig(ArcanusConfig.class);

		RegistryService registryService = RegistryService.get();
		ArcanusEntityAttributes.registerAll(registryService);
		ArcanusEntities.ENTITY_TYPES.accept(registryService);
		ArcanusBlocks.BLOCKS.accept(registryService);
		ArcanusItems.ITEM_GROUPS.accept(registryService);
		ArcanusItems.ITEMS.accept(registryService);
		ArcanusBlockEntities.BLOCK_ENTITY_TYPES.accept(registryService);
		ArcanusParticles.PARTICLE_TYPES.accept(registryService);
		ArcanusPointsOfInterest.register();
		ArcanusRecipes.RECIPE_SERIALIZERS.accept(registryService);
		ArcanusScreenHandlers.SCREEN_HANDLERS.accept(registryService);
		ArcanusSpellComponents.SPELL_COMPONENTS.accept(registryService);
		ArcanusStatusEffects.STATUS_EFFECTS.accept(registryService);

		RegistryEvents.DYNAMIC_REGISTRY_SETUP.register(context -> context.register(RegistryKeys.STRUCTURE_PROCESSOR_LIST, Arcanus.id("wizard_tower_processors"), () -> WIZARD_TOWER_PROCESSOR_LIST));

		ServerPlayNetworking.registerGlobalReceiver(CastSpellPacket.ID, CastSpellPacket::handler);
		ServerPlayNetworking.registerGlobalReceiver(SetCastingPacket.ID, SetCastingPacket::handler);
		ServerPlayNetworking.registerGlobalReceiver(SaveBookDataPacket.ID, SaveBookDataPacket::handler);
		ServerPlayNetworking.registerGlobalReceiver(SyncPatternPacket.ID, SyncPatternPacket::handler);
		ServerPlayNetworking.registerGlobalReceiver(ShootOrbsPacket.ID, ShootOrbsPacket::handler);

		CommandRegistrationCallback.EVENT.register(ArcanusCommands::init);

		ServerLifecycleEvents.STARTING.register(server -> Arcanus.refreshSupporterData(server, true));

		EntityElytraEvents.CUSTOM.register((entity, tickElytra) -> entity.hasStatusEffect(ArcanusStatusEffects.MANA_WINGS.get()));

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			if(!server.isSingleplayer())
				SyncConfigValuesPacket.send(handler.player);

			Arcanus.refreshSupporterData(server, false);
			SyncSupporterData.send(handler.player);
			SyncStatusEffectPacket.sendToAll(handler.player, ArcanusStatusEffects.ANONYMITY.get(), handler.player.hasStatusEffect(ArcanusStatusEffects.ANONYMITY.get()));
		});

		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
			if(ArcanusComponents.areUpdatesBlocked(handler.player))
				ArcanusComponents.setBlockUpdates(handler.player, false);
		});

		EntityTrackingEvents.AFTER_START_TRACKING.register((trackedEntity, player) -> {
			if(trackedEntity instanceof ServerPlayerEntity playerEntity)
				SyncStatusEffectPacket.sendTo(player, playerEntity, ArcanusStatusEffects.ANONYMITY.get(), playerEntity.hasStatusEffect(ArcanusStatusEffects.ANONYMITY.get()));

			// FIXME temporal dilation no worky
//			if(trackedEntity instanceof LivingEntity livingEntity)
//				SyncStatusEffectPacket.sendTo(player, livingEntity, ArcanusStatusEffects.TEMPORAL_DILATION.get(), livingEntity.hasStatusEffect(ArcanusStatusEffects.TEMPORAL_DILATION.get()));
		});

		ResourceLoaderEvents.END_DATA_PACK_RELOAD.register(ctx -> {
			var server = ctx.server();
			if (server != null)
				Arcanus.refreshSupporterData(server, true);
		});

		QuiltChatEvents.CANCEL.register(EnumSet.of(QuiltMessageType.CHAT), abstractMessage -> {
			PlayerEntity player = abstractMessage.getPlayer();
			World world = player.getWorld();

			if(world instanceof ServerWorld server && abstractMessage instanceof ChatC2SMessage packet) {
				String message = packet.getMessage();

				CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
					PointOfInterestStorage poiStorage = server.getChunkManager().getPointOfInterestStorage();
					Stream<BlockPos> pointOfInterest = poiStorage.getInSquare(poiTypeHolder -> poiTypeHolder.isRegistryKey(ArcanusPointsOfInterest.MAGIC_DOOR), player.getBlockPos(), 8, PointOfInterestStorage.OccupationStatus.ANY).map(PointOfInterest::getPos);
					boolean beep = false;

					for(BlockPos pos : pointOfInterest.collect(Collectors.toSet())) {
						BlockState state = world.getBlockState(pos);

						if(state.getBlock() instanceof MagicDoorBlock doorBlock && world.getBlockEntity(pos) instanceof MagicDoorBlockEntity door) {
							if (message.toLowerCase(Locale.ROOT).equals(door.getPassword())) {
								doorBlock.setOpen(null, world, state, pos, true);
								player.sendMessage(translate("door", "access_granted").formatted(Formatting.GRAY, Formatting.ITALIC), true);
								beep = true;
							}
						}
					}
					return beep;
				}, world.getServer());

				return future.join();
			}

			return false;
		});

		EntitySleepEvents.STOP_SLEEPING.register((entity, sleepingPos) -> {
			if(!entity.getWorld().isClient() && entity.getWorld().getTimeOfDay() == 24000) {
				StatusEffectInstance copperCurse = entity.getStatusEffect(ArcanusStatusEffects.COPPER_CURSE.get());

				if(copperCurse != null) {
					entity.removeStatusEffect(ArcanusStatusEffects.COPPER_CURSE.get());

					if(copperCurse.getDuration() > 24000)
						entity.addStatusEffect(new StatusEffectInstance(ArcanusStatusEffects.COPPER_CURSE.get(), copperCurse.getDuration() - 24000, 0, true, false));
				}
			}
		});

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			ItemStack stack = player.getStackInHand(hand);
			BlockPos pos = hitResult.getBlockPos();
			BlockState state = world.getBlockState(pos);

			if(!world.isClient() && player.isSneaking() && stack.isOf(Items.NAME_TAG) && stack.hasCustomName()) {
				MagicDoorBlockEntity door = MagicDoorBlock.getBlockEntity(world, state, pos);

				if(door != null && door.getOwner() == player) {
					door.setPassword(stack.getName().getString());

					if(!player.isCreative())
						stack.decrement(1);

					return ActionResult.SUCCESS;
				}
			}

			if(ArcanusComponents.isBlockWarded(world, pos) && !ArcanusComponents.isOwnerOfBlock(player, pos)) {
				ItemUsageContext ctx = new ItemPlacementContext(world, player, hand, stack, hitResult);
				ActionResult result = stack.useOnBlock(ctx);

				if(!result.isAccepted()) {
					player.sendMessage(Arcanus.translate("text", "block_is_warded").formatted(Formatting.RED), true);
					player.swingHand(hand);

					return ActionResult.FAIL;
				}

				return result;
			}

			return ActionResult.PASS;
		});

		// FIXME temporal dilation no worky
//		ServerWorldTickEvents.END.register((server, world) -> {
//			List<Entity> loadedEntityList = new ArrayList<>();
//			world.iterateEntities().forEach(loadedEntityList::add);
//			StatusEffect statusEffect = ArcanusStatusEffects.TEMPORAL_DILATION.get();
//			float radius = 3;
//
//			for(Entity entity : loadedEntityList) {
//				if(ArcanusComponents.isTimeSlowed(entity)) {
//					List<Entity> targets = world.getOtherEntities(entity, new Box(-radius, -radius, -radius, radius, radius, radius).offset(entity.getPos()), target -> target.squaredDistanceTo(entity) <= radius * radius);
//
//					if(targets.stream().noneMatch(target -> target instanceof LivingEntity livingTarget && livingTarget.hasStatusEffect(statusEffect))) {
//						ArcanusComponents.setSlowTime(entity, false);
//						ArcanusComponents.setBlockUpdates(entity, false);
//					}
//				}
//			}
//		});
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
		return Text.translatable(translationKey(prefix, value));
	}

	public static String translationKey(@Nullable String prefix, String... value) {
		String translationKey = Arcanus.MOD_ID + "." + String.join(".", value);
		return prefix != null ? (prefix + "." + translationKey) : translationKey;
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
		String string = switch(index) {
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

		MutableText text = Text.literal(string);
		return text.setStyle(text.getStyle().withFont(Arcanus.id("magic_symbols")));
	}
}
