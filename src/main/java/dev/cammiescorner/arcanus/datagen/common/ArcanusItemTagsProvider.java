package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.common.data.ArcanusItemTags;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ArcanusItemTagsProvider extends FabricTagProvider.ItemTagProvider {
	public ArcanusItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable BlockTagProvider blockTagProvider) {
		super(output, completableFuture, blockTagProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		getOrCreateTagBuilder(ArcanusItemTags.C_FEATHERS)
			.add(Items.FEATHER);

		getOrCreateTagBuilder(ItemTags.BOOKSHELF_BOOKS)
			.add(ArcanusItems.SPELL_SCROLL.get());

		getOrCreateTagBuilder(ItemTags.LECTERN_BOOKS)
			.add(ArcanusItems.SPELL_SCROLL.get());

		getOrCreateTagBuilder(ConventionalItemTags.TOOLS)
			.addTag(ArcanusItemTags.STAVES);

		getOrCreateTagBuilder(ArcanusItemTags.COPPER_CURSE_IMMUNE)
			.forceAddTag(ConventionalItemTags.TOOLS)
			.add(Items.FISHING_ROD)
			.forceAddTag(ConventionalItemTags.BOW_TOOLS)
			.forceAddTag(ConventionalItemTags.SHIELD_TOOLS)
			.forceAddTag(ConventionalItemTags.SPEAR_TOOLS)
			.forceAddTag(ConventionalItemTags.SHEAR_TOOLS)
			.forceAddTag(ItemTags.TRIMMABLE_ARMOR)
			.add(Items.ELYTRA)
			.forceAddTag(ItemTags.TRIM_TEMPLATES)
			.add(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
//			.add(ArcanusItems.BATTLE_MAGE_UPGRADE_SMITHING_TEMPLATE.get())
			.forceAddTag(ItemTags.DECORATED_POT_SHERDS)
			.addTag(ArcanusItemTags.ARCANIST_ARMOR);

		getOrCreateTagBuilder(ArcanusItemTags.ARCANIST_ARMOR)
			.add(ArcanusItems.ARCANIST_HAT.get())
			.add(ArcanusItems.ARCANIST_ROBES.get())
			.add(ArcanusItems.ARCANIST_PANTS.get())
			.add(ArcanusItems.ARCANIST_BOOTS.get())
			.add(ArcanusItems.CULTIST_HOOD.get())
			.add(ArcanusItems.CULTIST_ROBES.get())
			.add(ArcanusItems.CULTIST_PANTS.get())
			.add(ArcanusItems.CULTIST_BOOTS.get());
//			.add(ArcanusItems.BATTLE_MAGE_HELMET.get())
//			.add(ArcanusItems.BATTLE_MAGE_CHESTPLATE.get())
//			.add(ArcanusItems.BATTLE_MAGE_LEGGINGS.get())
//			.add(ArcanusItems.BATTLE_MAGE_BOOTS.get());
	}
}
