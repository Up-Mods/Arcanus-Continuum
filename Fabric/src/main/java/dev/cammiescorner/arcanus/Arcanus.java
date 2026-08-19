package dev.cammiescorner.arcanus;

import com.google.auto.service.AutoService;
import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import commonnetwork.api.Network;
import dev.cammiescorner.arcanus.common.data.ArcanusBiomeTags;
import dev.cammiescorner.arcanus.common.menu.providers.SpellcraftMenuProvider;
import dev.cammiescorner.arcanus.common.networking.clientbound.*;
import dev.cammiescorner.arcanus.common.networking.serverbound.*;
import dev.cammiescorner.arcanus.common.registry.*;
import dev.cammiescorner.arcanus.common.util.supporters.HaloData;
import dev.cammiescorner.arcanus.common.util.supporters.WizardData;
import dev.upcraft.datasync.api.DataSyncAPI;
import dev.upcraft.datasync.api.SyncToken;
import dev.upcraft.sparkweave.api.color.Color;
import dev.upcraft.sparkweave.api.entrypoint.MainEntryPoint;
import dev.upcraft.sparkweave.api.event.ItemMenuInteractionEvent;
import dev.upcraft.sparkweave.api.event.RegisterCustomLecternMenuEvent;
import dev.upcraft.sparkweave.api.platform.ModContainer;
import dev.upcraft.sparkweave.api.platform.services.RegistryService;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.SharedConstants;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

@AutoService(MainEntryPoint.class)
public class Arcanus implements MainEntryPoint {
	public static final String MOD_ID = "arcanus";
	public static final Configurator configurator = new Configurator(MOD_ID);
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##,####.##");
	public static final Color DEFAULT_MAGIC_COLOR = Color.fromInt(0x68e1ff, Color.Ordering.RGB);
	public static final SyncToken<WizardData> WIZARD_DATA = DataSyncAPI.register(WizardData.class, WizardData.ID, WizardData.CODEC);
	public static final SyncToken<HaloData> HALO_DATA = DataSyncAPI.register(HaloData.class, HaloData.ID, HaloData.CODEC);
	public static final Identifier SPELL_SPEED_MODIFIER_ID = Arcanus.id("speed_effect_modifier");
	public static final Identifier MAGIC_SYMBOLS_FONT_ID = Arcanus.id("magic_symbols");

	@Override
	public void onInitialize(ModContainer mod) {
		SharedConstants.IS_RUNNING_IN_IDE = true;
		configurator.register(ArcanusConfig.class);

		// FIXME sparkweave bug: need to delay the actual registering on fabric
		//  first block entities, then blocks, then items,
		//  then remaining vanilla registries in alphabetical order,
		//  then modded registries in alphabetical order
		RegistryService registryService = RegistryService.get();

		ArcanusAttributes.registerAll();
		ArcanusArcana.ARCANA.accept(registryService);
		ArcanusSpellComponents.SPELL_COMPONENTS.accept(registryService);
		ArcanusEntities.ENTITY_TYPES.accept(registryService);
		ArcanusArmorMaterials.MATERIALS.accept(registryService);
		ArcanusDataComponents.DATA_COMPONENTS.accept(registryService);
		ArcanusItems.ITEMS.accept(registryService);
		ArcanusCreativeTabs.CREATIVE_TABS.accept(registryService);
		ArcanusBlocks.BLOCKS.accept(registryService);
		ArcanusBlockEntities.BLOCK_ENTITIES.accept(registryService);
		ArcanusParticles.PARTICLE_TYPES.accept(registryService);
		ArcanusPointsOfInterest.register();
		ArcanusRecipes.RECIPE_TYPES.accept(registryService);
		ArcanusRecipes.RECIPE_SERIALIZERS.accept(registryService);
		ArcanusRiteResultTypes.RITE_RESULTS.accept(registryService);
		ArcanusMenus.MENUS.accept(registryService);
		ArcanusMobEffects.MOB_EFFECTS.accept(registryService);
		ArcanusStructureProcessorTypes.STRUCTURE_PROCESSORS.accept(registryService);
		ArcanusCriteriaTriggers.CRITERIA_TRIGGERS.accept(registryService);

		Network.registerPacket(ClientboundUpdateSpellcraftScreenPacket.TYPE, ClientboundUpdateSpellcraftScreenPacket.class, ClientboundUpdateSpellcraftScreenPacket.CODEC, ClientboundUpdateSpellcraftScreenPacket::handle);
		Network.registerPacket(ClientboundEnforceConfigPacket.TYPE, ClientboundEnforceConfigPacket.class, ClientboundEnforceConfigPacket.CODEC, ClientboundEnforceConfigPacket::handle);
		Network.registerPacket(ClientboundBurstVfxPacket.TYPE, ClientboundBurstVfxPacket.class, ClientboundBurstVfxPacket.CODEC, ClientboundBurstVfxPacket::handle);
		Network.registerPacket(ClientboundStaffTemplatePacket.TYPE, ClientboundStaffTemplatePacket.class, ClientboundStaffTemplatePacket.CODEC, ClientboundStaffTemplatePacket::handle);
		Network.registerPacket(ClientboundStatusEffectPacket.TYPE, ClientboundStatusEffectPacket.class, ClientboundStatusEffectPacket.CODEC, ClientboundStatusEffectPacket::handle);
		Network.registerPacket(ClientboundWorkbenchModePacket.TYPE, ClientboundWorkbenchModePacket.class, ClientboundWorkbenchModePacket.CODEC, ClientboundWorkbenchModePacket::handle);

		Network.registerPacket(ServerboundIsCastingPacket.TYPE, ServerboundIsCastingPacket.class, ServerboundIsCastingPacket.CODEC, ServerboundIsCastingPacket::handle);
		Network.registerPacket(ServerboundSaveBookDataPacket.TYPE, ServerboundSaveBookDataPacket.class, ServerboundSaveBookDataPacket.CODEC, ServerboundSaveBookDataPacket::handle);
		Network.registerPacket(ServerboundShootOrbsPacket.TYPE, ServerboundShootOrbsPacket.class, ServerboundShootOrbsPacket.CODEC, ServerboundShootOrbsPacket::handle);
		Network.registerPacket(ServerboundSyncPatternPacket.TYPE, ServerboundSyncPatternPacket.class, ServerboundSyncPatternPacket.CODEC, ServerboundSyncPatternPacket::handle);
		Network.registerPacket(ServerboundOpenCloseHoodPacket.TYPE, ServerboundOpenCloseHoodPacket.class, ServerboundOpenCloseHoodPacket.CODEC, ServerboundOpenCloseHoodPacket::handle);
		Network.registerPacket(ServerboundCycleBookPouchPacket.TYPE, ServerboundCycleBookPouchPacket.class, ServerboundCycleBookPouchPacket.CODEC, ServerboundCycleBookPouchPacket::handle);

		RegisterCustomLecternMenuEvent.EVENT.register(event -> {
			event.register(ArcanusItems.SPELL_SCROLL, (level, pos, player, blockEntity, stack) -> new SpellcraftMenuProvider(level, stack, pos, blockEntity.bookAccess));
		});

		ItemMenuInteractionEvent.EVENT.register((menu, player, level, clickAction, slot, slotStack, cursorStack) -> {
			if(clickAction == ClickAction.SECONDARY && cursorStack.isEmpty() && slotStack.has(ArcanusDataComponents.HOOD_DOWN.get())) {
				DataComponentType<Boolean> hoodData = ArcanusDataComponents.HOOD_DOWN.get();
				boolean value = !slotStack.getOrDefault(hoodData, true);

				slotStack.set(hoodData, value);
				Network.getNetworkHandler().sendToServer(new ServerboundOpenCloseHoodPacket(slot.getContainerSlot(), value));

				// TODO figure out how to play the equip sound
//				level.playSeededSound(player, player.getX(), player.getY(), player.getZ(), equipable.getEquipSound().value(), SoundSource.NEUTRAL, 1f, 1f, player.getRandom().nextLong());

				return true;
			}

			return false;
		});

		BiomeModifications.addFeature(BiomeSelectors.tag(ArcanusBiomeTags.SUITABLE_FOR_EBONY), GenerationStep.Decoration.VEGETAL_DECORATION, ArcanusFeatures.PLACED_EBONY_TREE);

		StrippableBlockRegistry.register(ArcanusBlocks.EBONY_LOG.get(), ArcanusBlocks.STRIPPED_EBONY_LOG.get());
		StrippableBlockRegistry.register(ArcanusBlocks.EBONY_WOOD.get(), ArcanusBlocks.STRIPPED_EBONY_WOOD.get());

		FlammableBlockRegistry.getDefaultInstance().add(ArcanusBlocks.EBONY_LEAVES.get(), 30, 60);
		FlammableBlockRegistry.getDefaultInstance().add(ArcanusBlocks.EBONY_LOG.get(), 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ArcanusBlocks.EBONY_WOOD.get(), 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ArcanusBlocks.STRIPPED_EBONY_LOG.get(), 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ArcanusBlocks.STRIPPED_EBONY_WOOD.get(), 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ArcanusBlocks.EBONY_PLANKS.get(), 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ArcanusBlocks.EBONY_SLAB.get(), 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ArcanusBlocks.EBONY_STAIRS.get(), 5, 20);
	}

	public static Identifier id(String name) {
		return Identifier.fromNamespaceAndPath(MOD_ID, name);
	}

	public static String format(double d) {
		return DECIMAL_FORMAT.format(d);
	}
}
