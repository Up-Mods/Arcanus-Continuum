package dev.cammiescorner.arcanus;

import com.google.auto.service.AutoService;
import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import commonnetwork.api.Network;
import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.cammiescorner.arcanus.api.spell.Pattern;
import dev.cammiescorner.arcanus.common.item.BookPouchItem;
import dev.cammiescorner.arcanus.common.menu.providers.SpellcraftMenuProvider;
import dev.cammiescorner.arcanus.common.networking.clientbound.*;
import dev.cammiescorner.arcanus.common.networking.serverbound.*;
import dev.cammiescorner.arcanus.common.registry.*;
import dev.cammiescorner.arcanus.common.util.supporters.HaloData;
import dev.cammiescorner.arcanus.common.util.supporters.WizardData;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import dev.upcraft.datasync.api.DataSyncAPI;
import dev.upcraft.datasync.api.SyncToken;
import dev.upcraft.sparkweave.api.color.Color;
import dev.upcraft.sparkweave.api.entrypoint.MainEntryPoint;
import dev.upcraft.sparkweave.api.event.ItemMenuInteractionEvent;
import dev.upcraft.sparkweave.api.event.RegisterCustomLecternMenuEvent;
import dev.upcraft.sparkweave.api.platform.ModContainer;
import dev.upcraft.sparkweave.api.platform.services.RegistryService;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import net.minecraft.SharedConstants;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.List;

@AutoService(MainEntryPoint.class)
public class Arcanus implements MainEntryPoint {
	public static final String MOD_ID = "arcanus";
	public static final Configurator configurator = new Configurator(MOD_ID);
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##,####.##");
	public static final Color DEFAULT_MAGIC_COLOR = Color.fromInt(0x68e1ff, Color.Ordering.RGB);
	public static final SyncToken<WizardData> WIZARD_DATA = DataSyncAPI.register(WizardData.class, WizardData.ID, WizardData.CODEC);
	public static final SyncToken<HaloData> HALO_DATA = DataSyncAPI.register(HaloData.class, HaloData.ID, HaloData.CODEC);
	public static final ResourceLocation SPELL_SPEED_MODIFIER_ID = Arcanus.id("speed_effect_modifier");
	public static final ResourceLocation MAGIC_SYMBOLS_FONT_ID = Arcanus.id("magic_symbols");

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
			event.register((level, pos, player, blockEntity, stack) -> new SpellcraftMenuProvider(level, stack, pos, blockEntity.bookAccess), ArcanusItems.SPELL_SCROLL);
		});

		ItemMenuInteractionEvent.EVENT.register((menu, player, level, clickAction, slot, slotStack, cursorStack) -> {
			if(clickAction == ClickAction.SECONDARY && cursorStack.isEmpty() && slotStack.has(ArcanusDataComponents.HOOD_DOWN.get())) {
				DataComponentType<Boolean> hoodData = ArcanusDataComponents.HOOD_DOWN.get();
				boolean value = !slotStack.getOrDefault(hoodData, true);

				slotStack.set(hoodData, value);
				Network.getNetworkHandler().sendToServer(new ServerboundOpenCloseHoodPacket(slot.getContainerSlot(), value));

				if(slotStack.getItem() instanceof Equipable equipable)
					level.playSeededSound(player, player.getX(), player.getY(), player.getZ(), equipable.getEquipSound().value(), SoundSource.NEUTRAL, 1f, 1f, player.getRandom().nextLong());

				return true;
			}

			return false;
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

		return Component.literal(string).withStyle(style -> style.withFont(MAGIC_SYMBOLS_FONT_ID));
	}

	public static ItemStack getActiveSpellBook(LivingEntity entity) {
		if(TrinketsApi.getTrinketComponent(entity).get() instanceof TrinketComponent component) {
			if(component.isEquipped(ArcanusItems.SPELL_BOOK.get()))
				return component.getEquipped(ArcanusItems.SPELL_BOOK.get()).getFirst().getB();

			if(component.isEquipped(ArcanusItems.BOOK_POUCH.get()))
				return BookPouchItem.getActiveSpellBook(component.getEquipped(ArcanusItems.BOOK_POUCH.get()).getFirst().getB());
		}

		return ItemStack.EMPTY;
	}

	public static Object2DoubleArrayMap<PrimalArcana> constructArcanaMap(double ignisArcana, double terraArcana, double aquaArcana, double aerArcana, double aetherArcana) {
		Object2DoubleArrayMap<PrimalArcana> map = new Object2DoubleArrayMap<>();

		map.put(ArcanusArcana.IGNIS.get(), ignisArcana);
		map.put(ArcanusArcana.TERRA.get(), terraArcana);
		map.put(ArcanusArcana.AQUA.get(), aquaArcana);
		map.put(ArcanusArcana.AER.get(), aerArcana);
		map.put(ArcanusArcana.AETHER.get(), aetherArcana);

		return map;
	}
}
