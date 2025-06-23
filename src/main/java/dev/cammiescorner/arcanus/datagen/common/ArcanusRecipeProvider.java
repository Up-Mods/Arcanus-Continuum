package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.data.ArcanusItemTags;
import dev.cammiescorner.arcanus.common.recipes.SpellBindingRecipe;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlocks;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ArcanusRecipeProvider extends FabricRecipeProvider {
	public ArcanusRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void buildRecipes(RecipeOutput exporter) {
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.CHISELED_BOOKSHELF)
			.pattern("###")
			.pattern("XXX")
			.pattern("###")
			.define('#', ItemTags.PLANKS)
			.define('X', ItemTags.WOODEN_SLABS)
			.unlockedBy("has_book", has(ItemTags.BOOKSHELF_BOOKS))
			.save(exporter, Arcanus.id("chiseled_bookshelf"));

		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ArcanusBlocks.ARCANE_WORKBENCH.get())
			.pattern("CCC")
			.pattern("A#A")
			.pattern("AAA")
			.define('C', ItemTags.CANDLES)
			.define('A', Items.AMETHYST_SHARD)
			.define('#', Blocks.CRAFTING_TABLE)
			.unlockedBy("has_amethyst", has(Items.AMETHYST_SHARD))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ArcanusItems.BATTLE_MAGE_UPGRADE_SMITHING_TEMPLATE.get())
			.pattern("#A#")
			.pattern("#S#")
			.pattern("###")
			.define('#', Blocks.COPPER_BLOCK)
			.define('S', Blocks.STONE)
			.define('A', Items.AMETHYST_SHARD)
			.unlockedBy("has_amethyst", has(Items.AMETHYST_SHARD))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ArcanusItems.CRYSTAL_STAFF.get())
			.pattern("  A")
			.pattern(" # ")
			.pattern("#  ")
			.define('A', Items.AMETHYST_SHARD)
			.define('#', ItemTags.LOGS)
			.unlockedBy("has_amethyst", has(Items.AMETHYST_SHARD))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ArcanusBlocks.MAGIC_DOOR.get())
			.pattern("##")
			.pattern("AA")
			.pattern("##")
			.define('A', Items.AMETHYST_SHARD)
			.define('#', ItemTags.PLANKS)
			.unlockedBy("has_amethyst", has(Items.AMETHYST_SHARD))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ArcanusItems.WIZARD_HAT.get())
			.pattern(" # ")
			.pattern(" # ")
			.pattern("G#G")
			.define('G', ConventionalItemTags.GOLD_INGOTS)
			.define('#', Items.LEATHER)
			.unlockedBy("has_gold_ingot", has(ConventionalItemTags.GOLD_INGOTS))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ArcanusItems.WIZARD_ROBES.get())
			.pattern("# #")
			.pattern("#G#")
			.pattern("#G#")
			.define('G', ConventionalItemTags.GOLD_INGOTS)
			.define('#', Items.LEATHER)
			.unlockedBy("has_gold_ingot", has(ConventionalItemTags.GOLD_INGOTS))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ArcanusItems.WIZARD_PANTS.get())
			.pattern("G#G")
			.pattern("# #")
			.pattern("# #")
			.define('G', ConventionalItemTags.GOLD_INGOTS)
			.define('#', Items.LEATHER)
			.unlockedBy("has_gold_ingot", has(ConventionalItemTags.GOLD_INGOTS))
			.save(exporter);

		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ArcanusItems.WIZARD_BOOTS.get())
			.pattern("G G")
			.pattern("# #")
			.define('G', ConventionalItemTags.GOLD_INGOTS)
			.define('#', Items.LEATHER)
			.unlockedBy("has_gold_ingot", has(ConventionalItemTags.GOLD_INGOTS))
			.save(exporter);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ArcanusItems.SPELL_SCROLL.get())
			.requires(Items.BOOK)
			.requires(ArcanusItemTags.C_FEATHERS)
			.requires(Items.GLOW_INK_SAC)
			.unlockedBy("has_glow_ink", has(Items.GLOW_INK_SAC))
			.group(Arcanus.id("spell_book").toString())
			.save(exporter);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ArcanusItems.SPELL_SCROLL.get())
			.requires(Items.WRITABLE_BOOK)
			.requires(Items.GLOW_INK_SAC)
			.unlockedBy("has_glow_ink", has(Items.GLOW_INK_SAC))
			.group(Arcanus.id("spell_book").toString())
			.save(exporter, Arcanus.id("spell_book_from_writable_book"));

		battleMageSmithing(exporter, Items.DIAMOND_HELMET, RecipeCategory.COMBAT, ArcanusItems.BATTLE_MAGE_HELMET.get());
		battleMageSmithing(exporter, Items.DIAMOND_CHESTPLATE, RecipeCategory.COMBAT, ArcanusItems.BATTLE_MAGE_CHESTPLATE.get());
		battleMageSmithing(exporter, Items.DIAMOND_LEGGINGS, RecipeCategory.COMBAT, ArcanusItems.BATTLE_MAGE_LEGGINGS.get());
		battleMageSmithing(exporter, Items.DIAMOND_BOOTS, RecipeCategory.COMBAT, ArcanusItems.BATTLE_MAGE_BOOTS.get());

		SpecialRecipeBuilder.special(SpellBindingRecipe::new).save(exporter, Arcanus.id("spell_binding").toString());
	}

	public static void battleMageSmithing(RecipeOutput finishedRecipeConsumer, Item ingredientItem, RecipeCategory category, Item resultItem) {
		SmithingTransformRecipeBuilder.smithing(Ingredient.of(ArcanusItems.BATTLE_MAGE_UPGRADE_SMITHING_TEMPLATE.get()), Ingredient.of(ingredientItem), Ingredient.of(Items.AMETHYST_SHARD), category, resultItem).unlocks("has_amethyst", has(Items.AMETHYST_SHARD)).save(finishedRecipeConsumer, getItemName(resultItem) + "_smithing");
	}
}
