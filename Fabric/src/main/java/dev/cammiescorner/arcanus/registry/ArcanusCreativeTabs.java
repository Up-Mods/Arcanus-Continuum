package dev.cammiescorner.arcanus.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.creative_tabs.AlchemyCreativeTab;
import dev.cammiescorner.arcanus.creative_tabs.ArcanusCreativeTab;
import dev.cammiescorner.arcanus.creative_tabs.ArtificeCreativeTab;
import dev.cammiescorner.arcanus.creative_tabs.OccultismCreativeTab;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;

public class ArcanusCreativeTabs {
	public static final RegistryHandler<CreativeModeTab> CREATIVE_TABS = RegistryHandler.create(Registries.CREATIVE_MODE_TAB, Arcanus.MOD_ID);

	public static final RegistrySupplier<CreativeModeTab> ARCANUS = CREATIVE_TABS.register(ArcanusCreativeTab.RESOURCE_KEY, ArcanusCreativeTab::buildTab);
	public static final RegistrySupplier<CreativeModeTab> ARTIFICE = CREATIVE_TABS.register(ArtificeCreativeTab.RESOURCE_KEY, ArtificeCreativeTab::buildTab);
	public static final RegistrySupplier<CreativeModeTab> ALCHEMY = CREATIVE_TABS.register(AlchemyCreativeTab.RESOURCE_KEY, AlchemyCreativeTab::buildTab);
	public static final RegistrySupplier<CreativeModeTab> OCCULTISM = CREATIVE_TABS.register(OccultismCreativeTab.RESOURCE_KEY, OccultismCreativeTab::buildTab);
}
