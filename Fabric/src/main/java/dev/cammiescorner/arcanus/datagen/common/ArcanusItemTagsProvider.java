package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.data.ArcanusTags;
import dev.cammiescorner.arcanus.registry.ArcanusItems;
import dev.upcraft.sparkweave.api.datagen.provider.common.SparkweaveItemTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ArcanusItemTagsProvider extends SparkweaveItemTagProvider {

	public ArcanusItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable IntrinsicHolderTagsProvider<Block> blockTagBuilderProvider) {
		super(output, Arcanus.MOD_ID, lookupProvider, blockTagBuilderProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		existingTag(ArcanusTags.Items.C_FEATHERS)
			.add(net.minecraft.world.item.Items.FEATHER);

		existingTag(ItemTags.BOOKSHELF_BOOKS)
			.add(ArcanusItems.SPELL_SCROLL);

		existingTag(ItemTags.LECTERN_BOOKS)
			.add(ArcanusItems.SPELL_SCROLL);

		existingTag(ConventionalItemTags.TOOLS)
			.addTag(ArcanusTags.Items.STAVES);

		tag(ArcanusTags.Items.ARCANIST_ARMOR, "Arcanist Armor")
			.add(ArcanusItems.ARCANIST_HAT)
			.add(ArcanusItems.ARCANIST_ROBES)
			.add(ArcanusItems.ARCANIST_PANTS)
			.add(ArcanusItems.ARCANIST_BOOTS)
			.add(ArcanusItems.CULTIST_CLERIC_HOOD)
			.add(ArcanusItems.CULTIST_CLERIC_ROBES)
			.add(ArcanusItems.CULTIST_CLERIC_PANTS)
			.add(ArcanusItems.CULTIST_CLERIC_BOOTS);
//			.add(ArcanusItems.BATTLE_MAGE_HELMET.get())
//			.add(ArcanusItems.BATTLE_MAGE_CHESTPLATE.get())
//			.add(ArcanusItems.BATTLE_MAGE_LEGGINGS.get())
//			.add(ArcanusItems.BATTLE_MAGE_BOOTS.get());

		tag(ArcanusTags.Items.REPAIRS_ARCANIST_ARMOR, "Repairs Arcanist Armor")
			.addExistingTag(ItemTags.REPAIRS_LEATHER_ARMOR);

		tag(ArcanusTags.Items.REPAIRS_ARTIFICER_ARMOR, "Repairs Artificer Armor")
			.add(ArcanusItems.ARCANEUM_INGOT);

		tag(ArcanusTags.Items.REPAIRS_ALCHEMIST_ARMOR, "Repairs Alchemist Armor")
			.addExistingTag(ItemTags.REPAIRS_LEATHER_ARMOR);

		tag(ArcanusTags.Items.REPAIRS_CULTIST_CLERIC_ARMOR, "Repairs Cultist Cleric Armor")
			.addExistingTag(ItemTags.REPAIRS_LEATHER_ARMOR);

		tag(ArcanusTags.Items.REPAIRS_CULTIST_KNIGHT_ARMOR, "Repairs Cultist Knight Armor")
			.addExistingTag(ItemTags.REPAIRS_IRON_ARMOR);
	}
}
