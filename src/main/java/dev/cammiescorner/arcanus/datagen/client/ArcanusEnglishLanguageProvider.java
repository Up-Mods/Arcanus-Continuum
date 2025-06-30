package dev.cammiescorner.arcanus.datagen.client;

import dev.cammiescorner.arcanus.common.registry.ArcanusAttributes;
import dev.cammiescorner.arcanus.api.spells.SpellComponent;
import dev.cammiescorner.arcanus.common.data.*;
import dev.cammiescorner.arcanus.common.items.BattleMageArmorItem;
import dev.cammiescorner.arcanus.common.registry.*;
import dev.upcraft.sparkweave.api.datagen.ContextAwarePackOutput;
import dev.upcraft.sparkweave.api.datagen.TranslationBuilder;
import dev.upcraft.sparkweave.api.datagen.provider.SparkweaveLanguageProvider;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.locale.Language;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.WeatheringCopper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static dev.cammiescorner.arcanus.common.util.TranslationKeys.*;

public class ArcanusEnglishLanguageProvider extends SparkweaveLanguageProvider {
	private final CompletableFuture<HolderLookup.Provider> registriesFuture;

	public ArcanusEnglishLanguageProvider(ContextAwarePackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(dataOutput, registriesFuture, Language.DEFAULT);
		this.registriesFuture = registriesFuture;
	}

	@Override
	public void generateTranslations(HolderLookup.Provider registerLookup, TranslationBuilder builder) {
		builder.creativeTab(ArcanusItems.ITEM_GROUP, "Arcanus");
		builder.item(ArcanusItems.WOODEN_STAFF, "Wooden Staff");
		builder.item(ArcanusItems.CRYSTAL_STAFF, "Crystal Staff");
		builder.item(ArcanusItems.DIVINATION_STAFF, "Divination Staff");
		builder.item(ArcanusItems.CRESCENT_STAFF, "Crescent Staff");
		builder.item(ArcanusItems.ANCIENT_STAFF, "Ancient Staff");
		builder.item(ArcanusItems.WAND, "Wand");
		builder.item(ArcanusItems.THAUMATURGES_GAUNTLET, "Thaumaturge's Gauntlet");
		builder.item(ArcanusItems.MIND_STAFF, "Mind Staff");
		builder.item(ArcanusItems.MAGIC_TOME, "Magic Tome");
		builder.item(ArcanusItems.MAGE_PISTOL, "Mage Pistol");
		builder.item(ArcanusItems.WIZARD_HAT, "Wizard Hat");
		builder.item(ArcanusItems.WIZARD_ROBES, "Wizard Robes");
		builder.item(ArcanusItems.WIZARD_PANTS, "Wizard Pants");
		builder.item(ArcanusItems.WIZARD_BOOTS, "Wizard Boots");
		builder.item(ArcanusItems.BATTLE_MAGE_HELMET, "Battle Mage Helmet");
		builder.item(ArcanusItems.BATTLE_MAGE_CHESTPLATE, "Battle Mage Chestplate");
		builder.item(ArcanusItems.BATTLE_MAGE_LEGGINGS, "Battle Mage Leggings");
		builder.item(ArcanusItems.BATTLE_MAGE_BOOTS, "Battle Mage Boots");
		builder.item(ArcanusItems.SPELL_BOOK, "Spell Book");
		builder.item(ArcanusItems.SPELL_SCROLL, "Spell Scroll");
		builder.item(ArcanusItems.SCROLL_OF_KNOWLEDGE, "Scroll of Knowledge");
		builder.item(ArcanusItems.WIZARD_SPAWN_EGG, "Wizard Spawn Egg");
		builder.item(ArcanusItems.OPOSSUM_SPAWN_EGG, "Opossum Spawn Egg");

		builder.item(ArcanusItems.BATTLE_MAGE_UPGRADE_SMITHING_TEMPLATE, "Smithing Template");
		builder.add(BATTLE_MAGE_UPGRADE, "Battle Mage Upgrade");
		builder.add(BATTLE_MAGE_UPGRADE_APPLIES_TO, "Diamond Armor");
		builder.add(BATTLE_MAGE_UPGRADE_INGREDIENTS, "Amethyst Shard");
		builder.add(BATTLE_MAGE_UPGRADE_BASE_SLOT_DESC, "Add diamond armor");
		builder.add(BATTLE_MAGE_UPGRADE_ADDITIONS_SLOT_DESC, "Add Amethyst Shard");

		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_HELMET, WeatheringCopper.WeatherState.UNAFFECTED, false), "Battle Mage Helmet");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_HELMET, WeatheringCopper.WeatherState.UNAFFECTED, true), "Waxed Battle Mage Helmet");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_HELMET, WeatheringCopper.WeatherState.EXPOSED, false), "Exposed Battle Mage Helmet");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_HELMET, WeatheringCopper.WeatherState.EXPOSED, true), "Waxed Exposed Battle Mage Helmet");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_HELMET, WeatheringCopper.WeatherState.WEATHERED, false), "Weathered Battle Mage Helmet");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_HELMET, WeatheringCopper.WeatherState.WEATHERED, true), "Waxed Weathered Battle Mage Helmet");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_HELMET, WeatheringCopper.WeatherState.OXIDIZED, false), "Oxidized Battle Mage Helmet");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_HELMET, WeatheringCopper.WeatherState.OXIDIZED, true), "Waxed Oxidized Battle Mage Helmet");

		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_CHESTPLATE, WeatheringCopper.WeatherState.UNAFFECTED, false), "Battle Mage Chestplate");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_CHESTPLATE, WeatheringCopper.WeatherState.UNAFFECTED, true), "Waxed Battle Mage Chestplate");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_CHESTPLATE, WeatheringCopper.WeatherState.EXPOSED, false), "Exposed Battle Mage Chestplate");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_CHESTPLATE, WeatheringCopper.WeatherState.EXPOSED, true), "Waxed Exposed Battle Mage Chestplate");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_CHESTPLATE, WeatheringCopper.WeatherState.WEATHERED, false), "Weathered Battle Mage Chestplate");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_CHESTPLATE, WeatheringCopper.WeatherState.WEATHERED, true), "Waxed Weathered Battle Mage Chestplate");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_CHESTPLATE, WeatheringCopper.WeatherState.OXIDIZED, false), "Oxidized Battle Mage Chestplate");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_CHESTPLATE, WeatheringCopper.WeatherState.OXIDIZED, true), "Waxed Oxidized Battle Mage Chestplate");

		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_LEGGINGS, WeatheringCopper.WeatherState.UNAFFECTED, false), "Battle Mage Leggings");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_LEGGINGS, WeatheringCopper.WeatherState.UNAFFECTED, true), "Waxed Battle Mage Leggings");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_LEGGINGS, WeatheringCopper.WeatherState.EXPOSED, false), "Exposed Battle Mage Leggings");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_LEGGINGS, WeatheringCopper.WeatherState.EXPOSED, true), "Waxed Exposed Battle Mage Leggings");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_LEGGINGS, WeatheringCopper.WeatherState.WEATHERED, false), "Weathered Battle Mage Leggings");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_LEGGINGS, WeatheringCopper.WeatherState.WEATHERED, true), "Waxed Weathered Battle Mage Leggings");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_LEGGINGS, WeatheringCopper.WeatherState.OXIDIZED, false), "Oxidized Battle Mage Leggings");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_LEGGINGS, WeatheringCopper.WeatherState.OXIDIZED, true), "Waxed Oxidized Battle Mage Leggings");

		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_BOOTS, WeatheringCopper.WeatherState.UNAFFECTED, false), "Battle Mage Boots");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_BOOTS, WeatheringCopper.WeatherState.UNAFFECTED, true), "Waxed Battle Mage Boots");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_BOOTS, WeatheringCopper.WeatherState.EXPOSED, false), "Exposed Battle Mage Boots");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_BOOTS, WeatheringCopper.WeatherState.EXPOSED, true), "Waxed Exposed Battle Mage Boots");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_BOOTS, WeatheringCopper.WeatherState.WEATHERED, false), "Weathered Battle Mage Boots");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_BOOTS, WeatheringCopper.WeatherState.WEATHERED, true), "Waxed Weathered Battle Mage Boots");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_BOOTS, WeatheringCopper.WeatherState.OXIDIZED, false), "Oxidized Battle Mage Boots");
		itemStack(builder, BattleMageArmorItem.getStack(ArcanusItems.BATTLE_MAGE_BOOTS, WeatheringCopper.WeatherState.OXIDIZED, true), "Waxed Oxidized Battle Mage Boots");

		biome(builder, ArcanusBiomes.POCKET_DIMENSION, "Pocket Dimension");

		builder.block(ArcanusBlocks.ARCANE_WORKBENCH, "Arcane Workbench");
		builder.block(ArcanusBlocks.PEDESTAL, "Pedestal");
		builder.block(ArcanusBlocks.DUMMY_BOOKSHELF, "[DEVELOPER ONLY] Dummy Chiseled Bookshelf");
		builder.block(ArcanusBlocks.MAGIC_BLOCK, "Magic Block");
		builder.block(ArcanusBlocks.MAGIC_DOOR, "Magic Door");
		builder.block(ArcanusBlocks.SPATIAL_RIFT_EXIT, "Spatial Rift Exit");
		builder.block(ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE, "Spatial Rift Wall");
		builder.block(ArcanusBlocks.SPATIAL_RIFT_WALL, "Spatial Rift Wall");

		builder.entity(ArcanusEntities.AGGRESSORB, "Aggressorb");
		builder.entity(ArcanusEntities.AOE, "Area Of Effect");
		builder.entity(ArcanusEntities.BEAM, "Beam");
		builder.entity(ArcanusEntities.ENTANGLED_ORB, "Entangled Orb");
		builder.entity(ArcanusEntities.MISSILE, "Missile");
		builder.entity(ArcanusEntities.LOB, "Lob");
		builder.entity(ArcanusEntities.GUIDED_SHOT, "Guided Shot");
		builder.entity(ArcanusEntities.MAGIC_RUNE, "Magic Rune");
		builder.entity(ArcanusEntities.MANA_SHIELD, "Mana Shield");
		builder.entity(ArcanusEntities.NECRO_SKELETON, "Necro Skeleton");
		builder.entity(ArcanusEntities.OPOSSUM, "Opossum");
		builder.entity(ArcanusEntities.PORTAL, "Pocket Dimension Portal");
		builder.entity(ArcanusEntities.TEMPORAL_DILATION_FIELD, "Temporal Dilation Field");
		builder.entity(ArcanusEntities.SMITE, "Smite");
		builder.entity(ArcanusEntities.WIZARD, "Wizard");

		builder.add(ArcanusMobEffects.ANONYMITY.get().getDescriptionId(), "Anonymity");
		builder.add(ArcanusMobEffects.BOUNCY.get().getDescriptionId(), "Bouncy");
		builder.add(ArcanusMobEffects.COPPER_CURSE.get().getDescriptionId(), "Copper Curse");
		builder.add(ArcanusMobEffects.DANGER_SENSE.get().getDescriptionId(), "Danger Sense");
		builder.add(ArcanusMobEffects.DISCOMBOBULATE.get().getDescriptionId(), "Discombobulate");
		builder.add(ArcanusMobEffects.FLOAT.get().getDescriptionId(), "Float");
		builder.add(ArcanusMobEffects.FORTIFY.get().getDescriptionId(), "Fortify");
		builder.add(ArcanusMobEffects.MANA_LOCK.get().getDescriptionId(), "Mana Lock");
		builder.add(ArcanusMobEffects.STOCKPILE.get().getDescriptionId(), "Stockpile");
		builder.add(ArcanusMobEffects.VULNERABILITY.get().getDescriptionId(), "Vulnerability");
		builder.add(ArcanusMobEffects.SHRINK.get().getDescriptionId(), "Shrink");
		builder.add(ArcanusMobEffects.ENLARGE.get().getDescriptionId(), "Enlarge");

		tag(builder, ArcanusBiomeTags.C_HAS_VILLAGE, "Has Village");
		tag(builder, ArcanusBiomeTags.HAS_WIZARD_TOWER, "Has Wizard Tower");
		tag(builder, ArcanusBiomeTags.IS_POCKET_DIMENSION, "Is Pocket Dimension");

		tag(builder, ArcanusBlockTags.WARDING_NOT_ALLOWED, "Warding Not Allowed");

		tag(builder, ArcanusDimensionTags.WARDING_NOT_ALLOWED, "Warding Not Allowed");

		tag(builder, ArcanusEntityTags.C_IMMOVABLE, "Immovable");
		tag(builder, ArcanusEntityTags.DISPELLABLE, "Dispellable");
		tag(builder, ArcanusEntityTags.RUNE_TRIGGER_IGNORED, "Does not trigger Magic Runes");
		tag(builder, ArcanusEntityTags.SPATIAL_RIFT_IMMUNE, "Immune to Spatial Rifts");
		tag(builder, ArcanusEntityTags.TEMPORAL_DILATION_IMMUNE, "Immune to Temporal Dilation Fields");

		tag(builder, ArcanusEnchantmentTags.MANA_POOL_COMPATIBLE_WITH, "Compatible enchantments for Mana Pool");

		tag(builder, ArcanusItemTags.C_FEATHERS, "Feathers");
		tag(builder, ArcanusItemTags.BRACELET_HAND, "Bracelets");
		tag(builder, ArcanusItemTags.BRACELET_OFFHAND, "Bracelets");
		tag(builder, ArcanusItemTags.SPELL_BOOK, "Spell Books");
		tag(builder, ArcanusItemTags.COPPER_CURSE_IMMUNE, "Ignored by Copper Curse");
		tag(builder, ArcanusItemTags.STAVES, "Staves");
		tag(builder, ArcanusItemTags.STAVES_FOR_WIZARDS, "Staves for Wizards");
		tag(builder, ArcanusItemTags.WIZARD_ARMOR, "Wizard Armor");

		builder.add(SPELL_BOOK_SLOT, "Spell Book");
		builder.add(BRACELET_HAND_SLOT, "Bracelet");

		builder.add(BLOCK_IS_WARDED, "This block is warded.");
		builder.add(CANT_WARD_IN_DIM, "Cannot ward blocks in this dimension!");
		builder.add(CANT_WARD_BLOCK, "Cannot ward this block!");
		builder.add(DISABLED_COMPONENT, "This spell contains one or more disabled spell components. Aborting spell.");
		builder.add(TOO_MANY_ORBS, "That target already has the maximum amount of Aggressorbs!");

		builder.add(USE_SCROLL_SUCCESS, "You feel a little smarter...");
		builder.add(USE_SCROLL_MAX_LEVEL, "You have learned everything you can.");

		builder.add(WIZARD_NO_WIZARD_ARMOR, "Come back to me when you look like a wizard.");
		builder.add(WIZARD_ARMOR_HINT, "Maybe wearing Wizard Robes will help...");

		builder.add(MAGIC_DOOR_ACCESS_GRANTED, "Access Granted...");
		builder.add(MAGIC_DOOR_NOT_OWNER, "You do not own this door");
		builder.add(MAGIC_DOOR_SAY_MAGIC_WORD, "This door needs a magic word to open");
		builder.add(MAGIC_DOOR_SET_PASSWORD, "Password set to %s");

		builder.add(STAFF_INVALID_DATA, "!!! INVALID DATA !!!");
		builder.add(STAFF_PRIMARY_COLOR, "Focus Color");
		builder.add(STAFF_SECONDARY_COLOR, "Body Color");

		builder.add(COMMAND_REGEN_POCKET_WALLS_ONLY, "Repaired the walls of %s's pocket dimension");
		builder.add(COMMAND_REGEN_POCKET_SUCCESS, "Regenerated %s's pocket dimension");
		builder.add(COMMAND_REGEN_POCKET_TELEPORT, "The pocket dimension plot you were in has been cleared by an admin.");

		builder.add(COMMAND_EXPORT_POCKET_SUCCESS, "Successfully exported %s's pocket dimension");
		builder.add(COMMAND_EXPORT_POCKET_DIM_NOT_FOUND, "unable to find %s!");
		builder.add(COMMAND_EXPORT_POCKET_POCKET_NOT_FOUND, "Pocket dimension for player %s has not been created yet!");

		builder.add(COMMAND_WIZARD_LEVEL_GET_SUCCESS, "%s's wizard level is %s");
		builder.add(COMMAND_WIZARD_LEVEL_SET_SUCCESS, "Set %s's wizard level to %s");

		advancement(builder, ArcanusAdvancements.ARCANE_ROOT, "Arcanus", "[insert witty sales pitch for something you're already playing]");
		advancement(builder, ArcanusAdvancements.A_MAGICAL_CRYSTAL, "A Magical Crystal", "Find an Amethyst Shard");
		advancement(builder, ArcanusAdvancements.ARCANE_ARTIFICE, "Arcane Artifice", "Craft an Arcane Workbench");
		advancement(builder, ArcanusAdvancements.MAGICAL_MEET_AND_GREET, "Magical Meet & Greet", "Find a Wizard Tower and meet the residents");
		advancement(builder, ArcanusAdvancements.WIZARD_LEVEL_1, "First Steps", "Obtain your first Scroll of Knowledge from a Wizard");
		advancement(builder, ArcanusAdvancements.WIZARD_LEVEL_2, "Wizard Level 2", "Use a Scroll of Knowledge to reach level 2");
		advancement(builder, ArcanusAdvancements.WIZARD_LEVEL_3, "Wizard Level 3", "Use a Scroll of Knowledge to reach level 3");
		advancement(builder, ArcanusAdvancements.WIZARD_LEVEL_4, "Wizard Level 4", "Use a Scroll of Knowledge to reach level 4");
		advancement(builder, ArcanusAdvancements.WIZARD_LEVEL_5, "Wizard Level 5", "Use a Scroll of Knowledge to reach level 5");
		advancement(builder, ArcanusAdvancements.WIZARD_LEVEL_6, "Wizard Level 6", "Use a Scroll of Knowledge to reach level 6");
		advancement(builder, ArcanusAdvancements.WIZARD_LEVEL_7, "Wizard Level 7", "Use a Scroll of Knowledge to reach level 7");
		advancement(builder, ArcanusAdvancements.WIZARD_LEVEL_8, "Wizard Level 8", "Use a Scroll of Knowledge to reach level 8");
		advancement(builder, ArcanusAdvancements.WIZARD_LEVEL_9, "Wizard Level 9", "Use a Scroll of Knowledge to reach level 9");
		advancement(builder, ArcanusAdvancements.WIZARD_LEVEL_10, "Master of The Arcane", "Achieve maximum magical power!");

		damageType(builder, ArcanusDamageTypes.MAGIC, "%s was killed by %s using magic", null, "%s was killed by %s using magic with %s");
		damageType(builder, ArcanusDamageTypes.MAGIC_PROJECTILE, "%s was shot by %s using magic", null, "%s was shot by %s using magic with %s");

		builder.add(ArcanusAttributes.RED_MANA.get().getDescriptionId(), "Red Mana");
		builder.add(ArcanusAttributes.GREEN_MANA.get().getDescriptionId(), "Green Mana");
		builder.add(ArcanusAttributes.BLUE_MANA.get().getDescriptionId(), "Blue Mana");
		builder.add(ArcanusAttributes.WHITE_MANA.get().getDescriptionId(), "White Mana");
		builder.add(ArcanusAttributes.BLACK_MANA.get().getDescriptionId(), "Black Mana");
		builder.add(ArcanusAttributes.MANA_REGEN.get().getDescriptionId(), "Mana Regeneration");
		builder.add(ArcanusAttributes.MANA_LOCK.get().getDescriptionId(), "Mana Lock");
		builder.add(ArcanusAttributes.SPELL_POTENCY.get().getDescriptionId(), "Spell Potency");
		builder.add(ArcanusAttributes.MANA_COST.get().getDescriptionId(), "Mana Cost");
		builder.add(ArcanusAttributes.MAGIC_RESISTANCE.get().getDescriptionId(), "Magic Resistance");
		builder.add(ArcanusAttributes.SPELL_COOL_DOWN.get().getDescriptionId(), "Spell Cooldown");

		builder.add(SpellComponent.DISABLED_TRANSLATION_KEY, "Unreadable Text");
		spell(builder, ArcanusSpellComponents.EMPTY, "EMPTY");
		spell(builder, ArcanusSpellComponents.SELF, "Self Shape");
		spell(builder, ArcanusSpellComponents.MISSILE, "Missile Shape");
		spell(builder, ArcanusSpellComponents.LOB, "Lob Shape");
		spell(builder, ArcanusSpellComponents.BOLT, "Bolt Shape");
		spell(builder, ArcanusSpellComponents.BEAM, "Beam Shape");
		spell(builder, ArcanusSpellComponents.RUNE, "Rune Shape");
		spell(builder, ArcanusSpellComponents.SMITE, "Smite Shape");
		spell(builder, ArcanusSpellComponents.AOE, "Area Of Effect Shape");
		spell(builder, ArcanusSpellComponents.BURST, "Burst Shape");
		spell(builder, ArcanusSpellComponents.GUIDED_SHOT, "Guided Shot Shape");
		spell(builder, ArcanusSpellComponents.COUNTER, "Counter Shape");
		spell(builder, ArcanusSpellComponents.ENTANGLED_ORB, "Entangled Orb Shape");
		spell(builder, ArcanusSpellComponents.AGGRESSORB, "Aggressorb Shape");

		spell(builder, ArcanusSpellComponents.DAMAGE, "Damage Effect");
		spell(builder, ArcanusSpellComponents.HEAL, "Heal Effect");
		spell(builder, ArcanusSpellComponents.FIRE, "Fire Effect");
		spell(builder, ArcanusSpellComponents.ELECTRIC, "Electric Effect");
		spell(builder, ArcanusSpellComponents.ICE, "Ice Effect");
		spell(builder, ArcanusSpellComponents.PUSH, "Push Effect");
		spell(builder, ArcanusSpellComponents.PULL, "Pull Effect");
		spell(builder, ArcanusSpellComponents.TELEPORT, "Teleport Effect");
		spell(builder, ArcanusSpellComponents.DISPEL, "Dispel Effect");
		spell(builder, ArcanusSpellComponents.MANA_LOCK, "Mana Lock Effect");
		spell(builder, ArcanusSpellComponents.WITHERING, "Withering Effect");
		spell(builder, ArcanusSpellComponents.REGENERATE, "Regenerate Effect");
		spell(builder, ArcanusSpellComponents.VULNERABILITY, "Vulnerability Effect");
		spell(builder, ArcanusSpellComponents.FORTIFY, "Fortify Effect");
		spell(builder, ArcanusSpellComponents.BOUNCY, "Bouncy Effect");
		spell(builder, ArcanusSpellComponents.FEATHER, "Feather Effect");
		spell(builder, ArcanusSpellComponents.NECROMANCY, "Necromancy Effect");
		spell(builder, ArcanusSpellComponents.MANA_SPLIT, "Mana Split Effect");
		spell(builder, ArcanusSpellComponents.ANONYMITY, "Anonymity Effect");
		spell(builder, ArcanusSpellComponents.MINE, "Mine Effect");
		spell(builder, ArcanusSpellComponents.BUILD, "Build Effect");
		spell(builder, ArcanusSpellComponents.LEVITATE, "Levitate Effect");
		spell(builder, ArcanusSpellComponents.GROWTH, "Growth Effect");
		spell(builder, ArcanusSpellComponents.MANA_SHIELD, "Mana Shield Effect");
		spell(builder, ArcanusSpellComponents.TEMPORAL_DILATION, "Temporal Dilation Effect");
		spell(builder, ArcanusSpellComponents.SPEED, "Speed Effect");
		spell(builder, ArcanusSpellComponents.SHRINK, "Shrink Effect");
		spell(builder, ArcanusSpellComponents.ENLARGE, "Enlarge Effect");
		spell(builder, ArcanusSpellComponents.COPPER_CURSE, "Copper Curse Effect");
		spell(builder, ArcanusSpellComponents.SPATIAL_RIFT, "Spatial Rift Effect");
		spell(builder, ArcanusSpellComponents.WARDING, "Warding Effect");
		spell(builder, ArcanusSpellComponents.DISCOMBOBULATE, "Discombobulate Effect");
		spell(builder, ArcanusSpellComponents.HASTE, "Haste Effect");
		spell(builder, ArcanusSpellComponents.FLOAT, "Float Effect");
		spell(builder, ArcanusSpellComponents.STOCKPILE, "Stockpile Effect");
		spell(builder, ArcanusSpellComponents.DANGER_SENSE, "Danger Sense Effect");

		builder.add(SCREEN_SPELL_COMPONENT_COUNT, "Spell Components");
		builder.add(SCREEN_CHANGE_MODE, "Change Mode");
		builder.add(SCREEN_CYCLE_UP, "Previous Skin");
		builder.add(SCREEN_CYCLE_DOWN, "Next Skin");

		builder.add(SCREEN_UNDO, "Undo");
		builder.add(SCREEN_REDO, "Redo");
		builder.add(SCREEN_CHECK_ENABLED, "✓");
		builder.add(SCREEN_CHECK_DISABLED, "✘");

		builder.add(SCREEN_SUPPORT_US_TITLE, "Support %s");
		builder.add(SCREEN_SUPPORT_US_SUBTITLE, "Arcanus");
		builder.add(SCREEN_SUPPORT_US_WELCOME, "Hey there, %s! If you wish to support the development of our many mods, please consider donating.");
		builder.add(SCREEN_SUPPORT_US_PERKS, "Monthly subscribers get various perks across our mods, like being able to customize your magic color as well as access to more staves/wands!");
		builder.add(SCREEN_SUPPORT_US_CAMMIE, "Support Cammie!");
		builder.add(SCREEN_SUPPORT_US_UP, "Support Up!");

		builder.add(BUTTON_TAKE_SCROLL, "Take Scroll");

		builder.add(TWO_ARGUMENT_KEY, "%1$s: %2$s");

		builder.add(SPELL_BOOK_WEIGHT, "Weight");
		builder.add(SPELL_BOOK_WEIGHT_NONE, "None");
		builder.add(SPELL_BOOK_WEIGHT_VERY_LIGHT, "Very Light");
		builder.add(SPELL_BOOK_WEIGHT_LIGHT, "Light");
		builder.add(SPELL_BOOK_WEIGHT_MEDIUM, "Medium");
		builder.add(SPELL_BOOK_WEIGHT_HEAVY, "Heavy");
		builder.add(SPELL_BOOK_WEIGHT_VERY_HEAVY, "Very Heavy");
		builder.add(SPELL_BOOK_RED_MANA, "Red Mana");
		builder.add(SPELL_BOOK_GREEN_MANA, "Green Mana");
		builder.add(SPELL_BOOK_BLUE_MANA, "Blue Mana");
		builder.add(SPELL_BOOK_WHITE_MANA, "White Mana");
		builder.add(SPELL_BOOK_BLACK_MANA, "Black Mana");
		builder.add(SPELL_BOOK_MANA_MULTIPLIER, "Mana Multiplier");
		builder.add(SPELL_BOOK_POTENCY_MODIFIER, "Potency Modifier");
		builder.add(SPELL_BOOK_COOL_DOWN, "Cool Down");

		builder.add(SPELL_TOO_MANY_COMPONENTS, "Too many components!");
		builder.add(SPELL_TOO_LOW_LEVEL, "Wizard level too low!");
		builder.add(SPELL_NOT_ENOUGH_MANA, "Not enough mana!");

		builder.add(COMPENDIUM_ARCANUS, "Compendium Arcanus");
		builder.add(COMPENDIUM_ARCANUS_LANDING, "Arcanus introduces a new magic system to the world of Minecraft. Players can create their own custom spells to suit however they like to play the game!");

		builder.add(CONFIG_SUPPORTER_SETTINGS, "Supporter Perks");
		builder.add(CONFIG_SUPPORTER_SETTINGS_MAGIC_COLOR, "Magic Color");
		builder.add(CONFIG_SUPPORTER_SETTINGS_POCKET_COLOR, "Pocket Dimension Color");
		builder.add(CONFIG_SUPPORTER_SETTINGS_HALO_COLOR, "Halo Color");
		builder.add(CONFIG_SUPPORTER_SETTINGS_HALO_ENABLED, "Halo Enabled");
		builder.add(CONFIG_SUPPORTER_SETTINGS_HALO_DISABLED, "Halo Disabled");
		builder.add(CONFIG_SUPPORTER_SETTINGS_SAVE_AND_EXIT, "Save and Exit");
		builder.add(CONFIG_SUPPORTER_SETTINGS_SAVING, "Saving...");

		builder.add(CONFIG_ENCHANTS_CATEGORY, "Enchantments");
		builder.add(CONFIG_MANA_POOL_PROPERTIES, "Mana Pool");
		builder.add(CONFIG_SPELL_SHAPES_CATEGORY, "Spell Shapes");
		builder.add(CONFIG_ATTACK_EFFECTS_CATEGORY, "Attack Effects");
		builder.add(CONFIG_SUPPORT_EFFECTS_CATEGORY, "Support Effects");
		builder.add(CONFIG_UTILITY_EFFECTS_CATEGORY, "Utility Effects");
		builder.add(CONFIG_MOVEMENT_EFFECTS_CATEGORY, "Movement Effects");
		builder.add(CONFIG_CASTING_HAS_SPEED_LIMIT, "Use Attack Cool Down for Casting?");
		builder.add(CONFIG_SIZE_CHANGE_IS_PERMA, "Size Changing Effects are Permanent for Players and Tamed Animals (Requires Pehkui)");
		builder.add(CONFIG_SCALE_MANA_BARS, "Scale Mana Bars Based on Maximum Mana");
		builder.add(CONFIG_MANA_BARS_MIN_LENGTH, "Min Length of Scaled Mana Bars");
		builder.add(CONFIG_MANA_BARS_MAX_LENGTH, "Max Length of Scaled Mana Bars");
		builder.add(CONFIG_MANA_BARS_ON_TOP, "Move Mana Bars to the Top");
		builder.add(CONFIG_RIGHT_SIDE_MANA_BARS, "Move Mana Bars to the Right");
		builder.add(CONFIG_NUMERICAL_MANA_DISPLAY, "Display Exact Mana Amounts by Mana Bars");
		builder.add(CONFIG_MAX_ENCHANT_LEVEL, "Max Enchantment Level");
		builder.add(CONFIG_EXTRA_MANA_PER_LEVEL, "Extra Mana Per Level");
		builder.add(CONFIG_MANA_MODIFIER_OP, "Mana Modifier Operation");
		builder.add(CONFIG_ENABLED, "Enabled");
		builder.add(CONFIG_WEIGHT, "Weight");
		builder.add(CONFIG_RED_MANA_COST, "Red Mana Cost");
		builder.add(CONFIG_GREEN_MANA_COST, "Green Mana Cost");
		builder.add(CONFIG_BLUE_MANA_COST, "Blue Mana Cost");
		builder.add(CONFIG_WHITE_MANA_COST, "White Mana Cost");
		builder.add(CONFIG_BLACK_MANA_COST, "Black Mana Cost");
		builder.add(CONFIG_MANA_MULTIPLIER, "Mana Multiplier");
		builder.add(CONFIG_COOL_DOWN, "Cool Down");
		builder.add(CONFIG_PROCS_ONCE, "Only Procs Once When Chained");
		builder.add(CONFIG_POTENCY_MODIFIER, "Potency Modifier");
		builder.add(CONFIG_PROJECTILE_SPEED, "Projectile Speed");
		builder.add(CONFIG_BASE_LIFE_SPAN, "Base Life Span");
		builder.add(CONFIG_LIFE_SPAN_MODIFIER, "Life Span Modifier");
		builder.add(CONFIG_RANGE, "Range");
		builder.add(CONFIG_DELAY, "Delay");
		builder.add(CONFIG_BURST_SHAPE_RADIUS, "Burst Radius");
		builder.add(CONFIG_MAX_MANA_LOCK, "Maximum Mana Lock");
		builder.add(CONFIG_MAX_AGGRESSORBS, "Maximum Aggressorbs");
		builder.add(CONFIG_AGGRESSORBS_PER_CAST, "How Many Aggressorbs Spawn per Cast");
		builder.add(CONFIG_BASE_DAMAGE, "Base Damage");
		builder.add(CONFIG_BASE_TIME_ON_FIRE, "Base Time On Fire");
		builder.add(CONFIG_BASE_STUN_TIME, "Base Stun Time");
		builder.add(CONFIG_WET_ENTITY_DAMAGE_MULTIPLIER, "Damage Multiplier Against Wet Entities");
		builder.add(CONFIG_BASE_FREEZING_TIME, "Base Freezing Time");
		builder.add(CONFIG_BASE_EFFECT_DURATION, "Base Effect Duration");
		builder.add(CONFIG_BASE_HEALTH, "Base Health");
		builder.add(CONFIG_EFFECT_DURATION_MODIFIER, "Effect Duration Modifier");
		builder.add(CONFIG_DAMAGE_TO_INCREASE, "Damage Needed To Increase");
		builder.add(CONFIG_BASE_HEAL_AMOUNT, "Base Heal Amount");
		builder.add(CONFIG_BASE_ACTIVATION_CHANCE, "Base Chance To Activate");
		builder.add(CONFIG_BASE_SHRINK_AMOUNT, "Base Shrink Amount");
		builder.add(CONFIG_BASE_ENLARGE_AMOUNT, "Base Enlarge Amount");
		builder.add(CONFIG_CAN_SUCK_ENTITIES_IN, "Can Suck Entities In");
		builder.add(CONFIG_PORTAL_GROW_TIME, "Spatial Rift Grow Time");
		builder.add(CONFIG_POCKET_WIDTH, "Pocket Dimension Width");
		builder.add(CONFIG_POCKET_HEIGHT, "Pocket Dimension Height");
		builder.add(CONFIG_CAN_BE_REMOVED_BY_OTHERS, "Can Be Removed By Other Players");
		builder.add(CONFIG_BASE_PUSH_STRENGTH, "Base Push Strength");
		builder.add(CONFIG_BASE_PULL_STRENGTH, "Base Pull Strength");
		builder.add(CONFIG_BASE_TELEPORT_DISTANCE, "Base Teleport Distance");
		builder.add(CONFIG_REMOVED_ON_DAMAGE_TAKEN, "Removed Upon Taking Damage");
		builder.add(CONFIG_BASE_MOVEMENT_SPEED, "Base Movement Speed");
		builder.add(CONFIG_BASE_MANA_DRAIN, "Base Mana Drain");
		builder.add(CONFIG_TIMES_TO_APPLY_EFFECTS, "Number of Times to Apply Spell Effects");
		builder.add(CONFIG_TIMES_TO_CAST_NEXT_SHAPE, "Number of Times to Cast Next Spell Shape");
		builder.add(CONFIG_SELF_SHAPE_PROPERTIES, "Self Shape");
		builder.add(CONFIG_TOUCH_SHAPE_PROPERTIES, "Touch Shape");
		builder.add(CONFIG_MISSILE_SHAPE_PROPERTIES, "Missile Shape");
		builder.add(CONFIG_LOB_SHAPE_PROPERTIES, "Lob Shape");
		builder.add(CONFIG_BOLT_SHAPE_PROPERTIES, "Bolt Shape");
		builder.add(CONFIG_BEAM_SHAPE_PROPERTIES, "Beam Shape");
		builder.add(CONFIG_RUNE_SHAPE_PROPERTIES, "Rune Shape");
		builder.add(CONFIG_BURST_SHAPE_PROPERTIES, "Burst Shape");
		builder.add(CONFIG_COUNTER_SHAPE_PROPERTIES, "Counter Shape");
		builder.add(CONFIG_AOE_SHAPE_PROPERTIES, "Area Of Effect Shape");
		builder.add(CONFIG_SMITE_SHAPE_PROPERTIES, "Smite Shape");
		builder.add(CONFIG_ENTANGLED_ORB_SHAPE_PROPERTIES, "Entangled Orb Shape");
		builder.add(CONFIG_AGGRESSORB_SHAPE_PROPERTIES, "Aggressorb Shape");
		builder.add(CONFIG_DAMAGE_EFFECT_PROPERTIES, "Damage Effect");
		builder.add(CONFIG_FIRE_EFFECT_PROPERTIES, "Fire Effect");
		builder.add(CONFIG_ELECTRIC_EFFECT_PROPERTIES, "Electric Effect");
		builder.add(CONFIG_ICE_EFFECT_PROPERTIES, "Ice Effect");
		builder.add(CONFIG_VULNERABILITY_EFFECT_PROPERTIES, "Vulnerability Effect");
		builder.add(CONFIG_MANA_LOCK_EFFECT_PROPERTIES, "Mana Lock Effect");
		builder.add(CONFIG_WITHERING_EFFECT_PROPERTIES, "Withering Effect");
		builder.add(CONFIG_NECROMANCY_EFFECT_PROPERTIES, "Necromancy Effect");
		builder.add(CONFIG_MANA_SPLIT_EFFECT_PROPERTIES, "Mana Split Effect");
		builder.add(CONFIG_COPPER_CURSE_EFFECT_PROPERTIES, "Copper Curse Effect");
		builder.add(CONFIG_DISCOMBOBULATE_EFFECT_PROPERTIES, "Discombobulate Effect");
		builder.add(CONFIG_STOCKPILE_EFFECT_PROPERTIES, "Stockpile Effect");
		builder.add(CONFIG_HEAL_EFFECT_PROPERTIES, "Heal Effect");
		builder.add(CONFIG_DISPEL_EFFECT_PROPERTIES, "Dispel Effect");
		builder.add(CONFIG_REGENERATE_EFFECT_PROPERTIES, "Regenerate Effect");
		builder.add(CONFIG_FORTIFY_EFFECT_PROPERTIES, "Fortify Effect");
		builder.add(CONFIG_HASTE_EFFECT_PROPERTIES, "Haste Effect");
		builder.add(CONFIG_MANA_SHIELD_EFFECT_PROPERTIES, "Mana Shield Effect");
		builder.add(CONFIG_DANGER_SENSE_EFFECT_PROPERTIES, "Danger Sense Effect");
		builder.add(CONFIG_TEMPORAL_DILATION_EFFECT_PROPERTIES, "Temporal Dilation Effect");
		builder.add(CONFIG_PUSH_EFFECT_PROPERTIES, "Push Effect");
		builder.add(CONFIG_PULL_EFFECT_PROPERTIES, "Pull Effect");
		builder.add(CONFIG_ANONYMITY_EFFECT_PROPERTIES, "Anonymity Effect");
		builder.add(CONFIG_MINE_EFFECT_PROPERTIES, "Mine Effect");
		builder.add(CONFIG_GROWTH_EFFECT_PROPERTIES, "Growth Effect");
		builder.add(CONFIG_SHRINK_EFFECT_PROPERTIES, "Shrink Effect");
		builder.add(CONFIG_ENLARGE_EFFECT_PROPERTIES, "Enlarge Effect");
		builder.add(CONFIG_SPATIAL_RIFT_EFFECT_PROPERTIES, "Spatial Rift Effect");
		builder.add(CONFIG_WARDING_EFFECT_PROPERTIES, "Warding Effect");
		builder.add(CONFIG_BUILD_EFFECT_PROPERTIES, "Build Effect");
		builder.add(CONFIG_LEVITATE_EFFECT_PROPERTIES, "Levitate Effect");
		builder.add(CONFIG_SPEED_EFFECT_PROPERTIES, "Speed Effect");
		builder.add(CONFIG_TELEPORT_EFFECT_PROPERTIES, "Teleport Effect");
		builder.add(CONFIG_BOUNCY_EFFECT_PROPERTIES, "Bouncy Effect");
		builder.add(CONFIG_FEATHER_EFFECT_PROPERTIES, "Feather Effect");
		builder.add(CONFIG_FLOAT_EFFECT_PROPERTIES, "Float Effect");
	}

	private void damageType(TranslationBuilder builder, ResourceKey<DamageType> typeKey, String defaultTranslation, @Nullable String killedByTranslation, @Nullable String killedWithItemTranslation) {
		registriesFuture.thenAccept(registries -> {
			var damageTypes = registries.lookupOrThrow(Registries.DAMAGE_TYPE);
			var type = damageTypes.getOrThrow(typeKey).value();

			if(type.deathMessageType() != DeathMessageType.DEFAULT) {
				throw new IllegalArgumentException("Death message type not currently supported: " + type.deathMessageType());
			}

			var translationKey = "death.attack." + type.msgId();
			builder.add(translationKey, defaultTranslation);
			builder.add(translationKey + ".player", killedByTranslation != null ? killedByTranslation : defaultTranslation);
			builder.add(translationKey + ".stack", killedWithItemTranslation != null ? killedWithItemTranslation : defaultTranslation);
		});
	}

	private void itemStack(TranslationBuilder builder, ItemStack stack, String translationValue) {
		builder.add(stack.getDescriptionId(), translationValue);
	}

	private void advancement(TranslationBuilder builder, ResourceLocation advancementId, String title, String description) {
		builder.add(Util.makeDescriptionId("advancements", advancementId.withSuffix(".title")), title);
		builder.add(Util.makeDescriptionId("advancements", advancementId.withSuffix(".description")), description);
	}

	private void spell(TranslationBuilder builder, RegistrySupplier<? extends SpellComponent> component, String translation) {
		builder.add(component.get().getTranslationKey(), translation);
	}

	private void tag(TranslationBuilder builder, TagKey<?> tag, String translation) {
		var registryName = tag.registry().location().toShortLanguageKey().replace('/', '.');
		var tagName = Util.makeDescriptionId("tag." + registryName, tag.location());
		builder.add(tagName, translation);
	}

	private void biome(TranslationBuilder builder, ResourceKey<Biome> biome, String translation) {
		builder.add(Util.makeDescriptionId("biome", biome.location()), translation);
	}
}
