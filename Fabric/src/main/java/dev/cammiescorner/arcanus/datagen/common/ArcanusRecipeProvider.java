package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.data.ArcanusTags;
import dev.cammiescorner.arcanus.registry.ArcanusBlocks;
import dev.cammiescorner.arcanus.registry.ArcanusItems;
import dev.upcraft.sparkweave.api.datagen.provider.common.SparkweaveRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class ArcanusRecipeProvider extends SparkweaveRecipeProvider {

	public ArcanusRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
		super(registries, output);
	}

	@Override
	public void buildRecipes() {
		shaped(RecipeCategory.DECORATIONS, Blocks.CHISELED_BOOKSHELF)
			.pattern("###")
			.pattern("XXX")
			.pattern("###")
			.define('#', ItemTags.PLANKS)
			.define('X', ItemTags.WOODEN_SLABS)
			.unlockedBy("has_book", has(ItemTags.BOOKSHELF_BOOKS))
			.save(output, Arcanus.id("chiseled_bookshelf"));

		shaped(RecipeCategory.DECORATIONS, ArcanusBlocks.ARCANE_WORKBENCH.get())
			.pattern("CCC")
			.pattern("A#A")
			.pattern("AAA")
			.define('C', ItemTags.CANDLES)
			.define('A', Items.AMETHYST_SHARD)
			.define('#', Blocks.CRAFTING_TABLE)
			.unlockedBy("has_amethyst", has(Items.AMETHYST_SHARD))
			.save(output);

//		shaped(RecipeCategory.MISC, ArcanusItems.BATTLE_MAGE_UPGRADE_SMITHING_TEMPLATE.get())
//			.pattern("#A#")
//			.pattern("#S#")
//			.pattern("###")
//			.define('#', Blocks.COPPER_BLOCK)
//			.define('S', Blocks.STONE)
//			.define('A', Items.AMETHYST_SHARD)
//			.unlockedBy("has_amethyst", has(Items.AMETHYST_SHARD))
//			.save(output);

		shaped(RecipeCategory.BUILDING_BLOCKS, ArcanusBlocks.MAGIC_DOOR.get())
			.pattern("##")
			.pattern("AA")
			.pattern("##")
			.define('A', net.minecraft.world.item.Items.AMETHYST_SHARD)
			.define('#', ItemTags.PLANKS)
			.unlockedBy("has_amethyst", has(net.minecraft.world.item.Items.AMETHYST_SHARD))
			.save(output);

		shaped(RecipeCategory.COMBAT, ArcanusItems.ARCANIST_HAT.get())
			.pattern(" # ")
			.pattern(" # ")
			.pattern("G#G")
			.define('G', ConventionalItemTags.GOLD_INGOTS)
			.define('#', net.minecraft.world.item.Items.LEATHER)
			.unlockedBy("has_gold_ingot", has(ConventionalItemTags.GOLD_INGOTS))
			.save(output);

		shaped(RecipeCategory.COMBAT, ArcanusItems.ARCANIST_ROBES.get())
			.pattern("# #")
			.pattern("#G#")
			.pattern("#G#")
			.define('G', ConventionalItemTags.GOLD_INGOTS)
			.define('#', net.minecraft.world.item.Items.LEATHER)
			.unlockedBy("has_gold_ingot", has(ConventionalItemTags.GOLD_INGOTS))
			.save(output);

		shaped(RecipeCategory.COMBAT, ArcanusItems.ARCANIST_PANTS.get())
			.pattern("G#G")
			.pattern("# #")
			.pattern("# #")
			.define('G', ConventionalItemTags.GOLD_INGOTS)
			.define('#', net.minecraft.world.item.Items.LEATHER)
			.unlockedBy("has_gold_ingot", has(ConventionalItemTags.GOLD_INGOTS))
			.save(output);

		shaped(RecipeCategory.COMBAT, ArcanusItems.ARCANIST_BOOTS.get())
			.pattern("G G")
			.pattern("# #")
			.define('G', ConventionalItemTags.GOLD_INGOTS)
			.define('#', net.minecraft.world.item.Items.LEATHER)
			.unlockedBy("has_gold_ingot", has(ConventionalItemTags.GOLD_INGOTS))
			.save(output);

		shapeless(RecipeCategory.MISC, ArcanusItems.SPELL_SCROLL.get())
			.requires(net.minecraft.world.item.Items.BOOK)
			.requires(ArcanusTags.Items.C_FEATHERS)
			.requires(net.minecraft.world.item.Items.GLOW_INK_SAC)
			.unlockedBy("has_glow_ink", has(net.minecraft.world.item.Items.GLOW_INK_SAC))
			.group(Arcanus.id("spell_book").toString())
			.save(output);

		shapeless(RecipeCategory.MISC, ArcanusItems.SPELL_SCROLL.get())
			.requires(net.minecraft.world.item.Items.WRITABLE_BOOK)
			.requires(net.minecraft.world.item.Items.GLOW_INK_SAC)
			.unlockedBy("has_glow_ink", has(net.minecraft.world.item.Items.GLOW_INK_SAC))
			.group(Arcanus.id("spell_book").toString())
			.save(output, Arcanus.id("spell_book_from_writable_book"));

//		battleMageSmithing(exporter, Items.DIAMOND_HELMET, RecipeCategory.COMBAT, ArcanusItems.BATTLE_MAGE_HELMET.get());
//		battleMageSmithing(exporter, Items.DIAMOND_CHESTPLATE, RecipeCategory.COMBAT, ArcanusItems.BATTLE_MAGE_CHESTPLATE.get());
//		battleMageSmithing(exporter, Items.DIAMOND_LEGGINGS, RecipeCategory.COMBAT, ArcanusItems.BATTLE_MAGE_LEGGINGS.get());
//		battleMageSmithing(exporter, Items.DIAMOND_BOOTS, RecipeCategory.COMBAT, ArcanusItems.BATTLE_MAGE_BOOTS.get());
	}

//	public static void battleMageSmithing(RecipeOutput finishedRecipeConsumer, Item ingredientItem, RecipeCategory category, Item resultItem) {
//		SmithingTransformRecipeBuilder.smithing(Ingredient.of(ArcanusItems.BATTLE_MAGE_UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(ingredientItem), Ingredient.of(Items.AMETHYST_SHARD), category, resultItem).unlocks("has_amethyst", has(Items.AMETHYST_SHARD)).save(finishedRecipeConsumer, getItemName(resultItem) + "_smithing");
//	}
}
