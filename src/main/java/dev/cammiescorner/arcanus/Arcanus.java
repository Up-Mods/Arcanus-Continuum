package dev.cammiescorner.arcanus;

import com.mojang.authlib.GameProfile;
import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import commonnetwork.api.Network;
import dev.cammiescorner.arcanus.api.spells.Pattern;
import dev.cammiescorner.arcanus.common.blocks.MagicDoorBlock;
import dev.cammiescorner.arcanus.common.blocks.entities.MagicDoorBlockEntity;
import dev.cammiescorner.arcanus.common.entities.living.NecroSkeleton;
import dev.cammiescorner.arcanus.common.entities.living.Opossum;
import dev.cammiescorner.arcanus.common.entities.living.Wizard;
import dev.cammiescorner.arcanus.common.networking.clientbound.*;
import dev.cammiescorner.arcanus.common.networking.serverbound.*;
import dev.cammiescorner.arcanus.common.registry.*;
import dev.cammiescorner.arcanus.common.screens.providers.SpellcraftMenuProvider;
import dev.cammiescorner.arcanus.common.util.supporters.HaloData;
import dev.cammiescorner.arcanus.common.util.supporters.WizardData;
import dev.upcraft.datasync.api.DataSyncAPI;
import dev.upcraft.datasync.api.SyncToken;
import dev.upcraft.sparkweave.api.color.Color;
import dev.upcraft.sparkweave.api.event.RegisterCustomLecternMenuEvent;
import dev.upcraft.sparkweave.api.platform.services.RegistryService;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.List;

public class Arcanus implements ModInitializer {
	public static final String MOD_ID = "arcanus";
	public static final Configurator configurator = new Configurator(MOD_ID);
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##,####.##");

	public static final Color DEFAULT_MAGIC_COLOUR = Color.fromInt(0x68e1ff, Color.Ordering.RGB);

	public static final SyncToken<WizardData> WIZARD_DATA = DataSyncAPI.register(WizardData.class, WizardData.ID, WizardData.CODEC);
	public static final SyncToken<HaloData> HALO_DATA = DataSyncAPI.register(HaloData.class, HaloData.ID, HaloData.CODEC);
	public static final ResourceLocation SPELL_SPEED_MODIFIER_ID = Arcanus.id("speed_effect_modifier");

	@Override
	public void onInitialize() {
		configurator.register(ArcanusConfig.class);

		RegistryService registryService = RegistryService.get();
		ArcanusEntityAttributes.registerAll();
		ArcanusEntities.ENTITY_TYPES.accept(registryService);
		ArcanusArmourMaterials.MATERIALS.accept(registryService);
		ArcanusDataComponents.DATA_COMPONENTS.accept(registryService);
		ArcanusItems.ITEMS.accept(registryService);
		ArcanusItems.ITEM_GROUPS.accept(registryService);
		ArcanusBlocks.BLOCKS.accept(registryService);
		ArcanusBlockEntities.BLOCK_ENTITIES.accept(registryService);
		ArcanusParticles.PARTICLE_TYPES.accept(registryService);
		ArcanusPointsOfInterest.register();
		ArcanusRecipes.RECIPE_SERIALIZERS.accept(registryService);
		ArcanusMenus.MENUS.accept(registryService);
		ArcanusSpellComponents.SPELL_COMPONENTS.accept(registryService);
		ArcanusMobEffects.MOB_EFFECTS.accept(registryService);
		ArcanusStructureProcessorTypes.STRUCTURE_PROCESSORS.accept(registryService);
		ArcanusCriteriaTriggers.CRITERIA_TRIGGERS.accept(registryService);

		FabricDefaultAttributeRegistry.register(ArcanusEntities.WIZARD.get(), Wizard.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ArcanusEntities.OPOSSUM.get(), Opossum.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ArcanusEntities.NECRO_SKELETON.get(), NecroSkeleton.createAttributes());

		Network.registerPacket(ServerboundCastSpellPacket.TYPE, ServerboundCastSpellPacket.class, ServerboundCastSpellPacket.CODEC, ServerboundCastSpellPacket::handle);
		Network.registerPacket(ServerboundIsCastingPacket.TYPE, ServerboundIsCastingPacket.class, ServerboundIsCastingPacket.CODEC, ServerboundIsCastingPacket::handle);
		Network.registerPacket(ServerboundSaveBookDataPacket.TYPE, ServerboundSaveBookDataPacket.class, ServerboundSaveBookDataPacket.CODEC, ServerboundSaveBookDataPacket::handle);
		Network.registerPacket(ServerboundShootOrbsPacket.TYPE, ServerboundShootOrbsPacket.class, ServerboundShootOrbsPacket.CODEC, ServerboundShootOrbsPacket::handle);
		Network.registerPacket(ServerboundSyncPatternPacket.TYPE, ServerboundSyncPatternPacket.class, ServerboundSyncPatternPacket.CODEC, ServerboundSyncPatternPacket::handle);

		Network.registerPacket(ClientboundUpdateSpellcraftScreenPacket.TYPE, ClientboundUpdateSpellcraftScreenPacket.class, ClientboundUpdateSpellcraftScreenPacket.CODEC, ClientboundUpdateSpellcraftScreenPacket::handle);
		Network.registerPacket(ClientboundUpdateSpellBookScreenPacket.TYPE, ClientboundUpdateSpellBookScreenPacket.class, ClientboundUpdateSpellBookScreenPacket.CODEC, ClientboundUpdateSpellBookScreenPacket::handle);
		Network.registerPacket(ClientboundEnforceConfigPacket.TYPE, ClientboundEnforceConfigPacket.class, ClientboundEnforceConfigPacket.CODEC, ClientboundEnforceConfigPacket::handle);
		Network.registerPacket(ClientboundBurstVfxPacket.TYPE, ClientboundBurstVfxPacket.class, ClientboundBurstVfxPacket.CODEC, ClientboundBurstVfxPacket::handle);
		Network.registerPacket(ClientboundStaffTemplatePacket.TYPE, ClientboundStaffTemplatePacket.class, ClientboundStaffTemplatePacket.CODEC, ClientboundStaffTemplatePacket::handle);
		Network.registerPacket(ClientboundStatusEffectPacket.TYPE, ClientboundStatusEffectPacket.class, ClientboundStatusEffectPacket.CODEC, ClientboundStatusEffectPacket::handle);
		Network.registerPacket(ClientboundWorkbenchModePacket.TYPE, ClientboundWorkbenchModePacket.class, ClientboundWorkbenchModePacket.CODEC, ClientboundWorkbenchModePacket::handle);

		CommandRegistrationCallback.EVENT.register(ArcanusCommands::init);

		// TODO for some reason isn't always syncing the book in the lectern when it should be...
		RegisterCustomLecternMenuEvent.EVENT.register(event -> {
			event.register((level, pos, player, blockEntity, stack) -> new SpellcraftMenuProvider(level, stack, pos, blockEntity.bookAccess), ArcanusItems.SPELL_BOOK);
		});

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			GameProfile hostProfile = server.getSingleplayerProfile();

			if(hostProfile == null || !hostProfile.getId().equals(handler.player.getGameProfile().getId()))
				Network.getNetworkHandler().sendToClient(new ClientboundEnforceConfigPacket(ArcanusConfig.castingSpeedHasCoolDown), handler.player);

			Network.getNetworkHandler().sendToClients(new ClientboundStatusEffectPacket(handler.player.getId(), ArcanusMobEffects.ANONYMITY.holder(), handler.player.hasEffect(ArcanusMobEffects.ANONYMITY.holder())), List.copyOf(PlayerLookup.tracking(handler.player)));
		});

		EntityTrackingEvents.START_TRACKING.register((trackedEntity, player) -> {
			if(trackedEntity instanceof ServerPlayer playerEntity)
				Network.getNetworkHandler().sendToClient(new ClientboundStatusEffectPacket(playerEntity.getId(), ArcanusMobEffects.ANONYMITY.holder(), playerEntity.hasEffect(ArcanusMobEffects.ANONYMITY.holder())), player);
		});

		EntitySleepEvents.STOP_SLEEPING.register((entity, sleepingPos) -> {
			if(!entity.level().isClientSide() && entity.level().getDayTime() == 24000) {
				MobEffectInstance copperCurse = entity.getEffect(ArcanusMobEffects.COPPER_CURSE.holder());

				if(copperCurse != null) {
					entity.removeEffect(ArcanusMobEffects.COPPER_CURSE.holder());

					if(copperCurse.getDuration() > 24000)
						entity.addEffect(new MobEffectInstance(ArcanusMobEffects.COPPER_CURSE.holder(), copperCurse.getDuration() - 24000, 0, true, false));
				}
			}
		});

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			ItemStack stack = player.getItemInHand(hand);
			BlockPos pos = hitResult.getBlockPos();
			BlockState state = world.getBlockState(pos);

			if(!world.isClientSide() && player.isShiftKeyDown() && stack.is(Items.NAME_TAG) && stack.has(DataComponents.CUSTOM_NAME)) {
				MagicDoorBlockEntity door = MagicDoorBlock.getBlockEntity(world, state, pos);

				if(door != null && door.getOwner() == player) {
					door.setPassword(stack.getHoverName().getString());

					if(!player.isCreative())
						stack.shrink(1);

					return InteractionResult.SUCCESS;
				}
			}

			if(ArcanusComponents.isBlockWarded(world, pos) && !ArcanusComponents.isOwnerOfBlock(player, pos)) {
				UseOnContext ctx = new BlockPlaceContext(world, player, hand, stack, hitResult);
				InteractionResult result = stack.useOn(ctx);

				if(!result.consumesAction()) {
					player.displayClientMessage(Component.translatable("text.arcanus.block_is_warded").withStyle(ChatFormatting.RED), true);
					player.swing(hand);

					return InteractionResult.FAIL;
				}

				return result;
			}

			return InteractionResult.PASS;
		});
	}

	public static ResourceLocation id(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
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

	public static MutableComponent getSpellPatternAsText(int index) {
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

		return Component.literal(string).withStyle(style -> style.withFont(Arcanus.id("magic_symbols")));
	}
}
