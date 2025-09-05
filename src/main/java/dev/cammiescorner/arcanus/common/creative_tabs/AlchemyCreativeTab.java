package dev.cammiescorner.arcanus.common.creative_tabs;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.data_component.ArcanaStorage;
import dev.cammiescorner.arcanus.common.registry.ArcanusArcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlocks;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import dev.upcraft.sparkweave.api.item.CreativeTabHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AlchemyCreativeTab {
	public static final ResourceKey<CreativeModeTab> RESOURCE_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Arcanus.id("02_alchemy_tab"));

	public static CreativeModeTab buildTab() {
		return CreativeTabHelper.newBuilder(RESOURCE_KEY).icon(ArcanusItems.ALCHEMIST_GOGGLES.get()::getDefaultInstance).displayItems((params, output) -> {
			output.accept(ArcanusItems.ALCHEMIST_GOGGLES.get());
			output.accept(ArcanusItems.ALCHEMIST_ROBES.get());
			output.accept(ArcanusItems.ALCHEMIST_PANTS.get());
			output.accept(ArcanusItems.ALCHEMIST_BOOTS.get());

			output.accept(ArcanusItems.ARCANEUM_INGOT.get());
			output.accept(ArcanusItems.ARCANEUM_NUGGET.get());
			output.accept(ArcanusItems.VOID_INGOT.get());
			output.accept(ArcanusItems.VOID_NUGGET.get());

			output.accept(ArcanusBlocks.ARCANA_PIPE.get());
			output.accept(ArcanusBlocks.ARCANA_PUMP.get());
			output.accept(ArcanusBlocks.WARDED_JAR.get());

			ArcanusArcana.primalArcana().forEach(arcana -> {
				ItemStack stack = new ItemStack(ArcanusBlocks.WARDED_JAR.get());

				stack.set(ArcanusDataComponents.ARCANA_STORAGE.get(), new ArcanaStorage(arcana, 64));
				output.accept(stack);
			});

			ArcanusArcana.compoundArcana().forEach(arcana -> {
				ItemStack stack = new ItemStack(ArcanusBlocks.WARDED_JAR.get());

				stack.set(ArcanusDataComponents.ARCANA_STORAGE.get(), new ArcanaStorage(arcana, 64));
				output.accept(stack);
			});

			ArcanusArcana.primalArcana().forEach(arcana -> {
				ItemStack stack = new ItemStack(ArcanusBlocks.MANA_BEAN.get());

				stack.set(ArcanusDataComponents.ARCANA.get(), arcana);
				output.accept(stack);
			});
		}).build();
	}
}
