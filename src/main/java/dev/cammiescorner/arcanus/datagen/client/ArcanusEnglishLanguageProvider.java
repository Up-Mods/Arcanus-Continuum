package dev.cammiescorner.arcanus.datagen.client;

import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.common.data.*;
import dev.cammiescorner.arcanus.common.registry.*;
import dev.cammiescorner.arcanus.common.util.TranslationKeys;
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
		builder.creativeTab(ArcanusCreativeTabs.ARCANUS, "Arcanus");
		builder.creativeTab(ArcanusCreativeTabs.GEAR, "Arcanus - Gear");
		builder.creativeTab(ArcanusCreativeTabs.SCROLLS, "Arcanus - Spell Components");

		builder.item(ArcanusItems.ARCANEUM_INGOT, "Arcaneum Ingot");
		builder.item(ArcanusItems.ARCANEUM_NUGGET, "Arcaneum Nugget");
		builder.item(ArcanusItems.ARCANIST_HAT, "Arcanist Hat");
		builder.item(ArcanusItems.ARCANIST_ROBES, "Arcanist Robes");
		builder.item(ArcanusItems.ARCANIST_PANTS, "Arcanist Pants");
		builder.item(ArcanusItems.ARCANIST_BOOTS, "Arcanist Boots");
		builder.item(ArcanusItems.CULTIST_HOOD, "Cultist Hood");
		builder.item(ArcanusItems.CULTIST_ROBES, "Cultist Robes");
		builder.item(ArcanusItems.CULTIST_PANTS, "Cultist Pants");
		builder.item(ArcanusItems.CULTIST_BOOTS, "Cultist Boots");
		builder.item(ArcanusItems.BOOK_POUCH, "Book Pouch");
		builder.item(ArcanusItems.SPELL_BOOK, "Spell Book");
		builder.item(ArcanusItems.SPELL_SCROLL, "Spell Scroll");
		builder.add(SPELL_SCROLL_WITH_SPELL, "%s (%s)");
		builder.item(ArcanusItems.SCROLL_OF_KNOWLEDGE, "Scroll of Knowledge");
		builder.item(ArcanusItems.ARCANIST_SPAWN_EGG, "Arcanist Spawn Egg");
		builder.item(ArcanusItems.CULTIST_CLERIC_SPAWN_EGG, "Cultist Cleric Spawn Egg");
		builder.item(ArcanusItems.CULTIST_KNIGHT_SPAWN_EGG, "Cultist Knight Spawn Egg");
		builder.item(ArcanusItems.OPOSSUM_SPAWN_EGG, "Opossum Spawn Egg");

		builder.item(ArcanusItems.STAFF, "Staff");

		builder.item(ArcanusItems.IRON_STAFF_CAP, "Iron Staff Cap");
		builder.item(ArcanusItems.GOLDEN_STAFF_CAP, "Golden Staff Cap");
		builder.item(ArcanusItems.COPPER_STAFF_CAP, "Copper Staff Cap");
		builder.item(ArcanusItems.NETHERITE_STAFF_CAP, "Netherite Staff Cap");
		builder.item(ArcanusItems.ARCANEUM_STAFF_CAP, "Arcaneum Staff Cap");

		builder.item(ArcanusItems.WOODEN_STAFF_CORE, "Wooden Staff Core");
		builder.item(ArcanusItems.CRIMSON_STAFF_CORE, "Crimson Staff Core");
		builder.item(ArcanusItems.WARPED_STAFF_CORE, "Warped Staff Core");

		biome(builder, ArcanusBiomes.POCKET_DIMENSION, "Pocket Dimension");

		builder.block(ArcanusBlocks.ARCANE_WORKBENCH, "Arcane Workbench");
		builder.block(ArcanusBlocks.ARCANE_PLINTH, "Arcane Plinth");
		builder.block(ArcanusBlocks.PEDESTAL, "Pedestal");
		builder.block(ArcanusBlocks.JAR, "Jar");
		builder.block(ArcanusBlocks.CHALK, "Chalk");
		builder.block(ArcanusBlocks.IGNIS_FRUIT, "Ignis Fruit");
		builder.block(ArcanusBlocks.TERRA_FRUIT, "Terra Fruit");
		builder.block(ArcanusBlocks.AQUA_FRUIT, "Aqua Fruit");
		builder.block(ArcanusBlocks.AER_FRUIT, "Aer Fruit");
		builder.block(ArcanusBlocks.AETHER_FRUIT, "Aether Fruit");
		builder.block(ArcanusBlocks.DUMMY_BOOKSHELF, "[DEVELOPER ONLY] Dummy Chiseled Bookshelf");
		builder.block(ArcanusBlocks.MAGIC_BLOCK, "Magic Block");
		builder.block(ArcanusBlocks.MAGIC_DOOR, "Magic Door");
		builder.block(ArcanusBlocks.SPATIAL_RIFT_EXIT, "Spatial Rift Exit");
		builder.block(ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE, "Spatial Rift Wall");
		builder.block(ArcanusBlocks.SPATIAL_RIFT_WALL, "Spatial Rift Wall");

		builder.entity(ArcanusEntities.STOCKPILE_ORB, "Stockpile Orb");
		builder.entity(ArcanusEntities.AOE, "Area Of Effect");
		builder.entity(ArcanusEntities.BEAM, "Beam");
		builder.entity(ArcanusEntities.MAGIC_ORB, "Magic Orb");
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
		builder.entity(ArcanusEntities.ARCANIST, "Arcanist");
		builder.entity(ArcanusEntities.CULTIST_CLERIC, "Cultist Cleric");
		builder.entity(ArcanusEntities.CULTIST_KNIGHT, "Cultist Knight");

		builder.add(ArcanusMobEffects.ANONYMITY.get().getDescriptionId(), "Anonymity");
		builder.add(ArcanusMobEffects.BOUNCY.get().getDescriptionId(), "Bouncy");
		builder.add(ArcanusMobEffects.DANGER_SENSE.get().getDescriptionId(), "Danger Sense");
		builder.add(ArcanusMobEffects.DISCOMBOBULATE.get().getDescriptionId(), "Discombobulate");
		builder.add(ArcanusMobEffects.FLOAT.get().getDescriptionId(), "Float");
		builder.add(ArcanusMobEffects.FORTIFY.get().getDescriptionId(), "Fortify");
		builder.add(ArcanusMobEffects.MANA_LOCK.get().getDescriptionId(), "Mana Lock");
		builder.add(ArcanusMobEffects.VULNERABILITY.get().getDescriptionId(), "Vulnerability");
		builder.add(ArcanusMobEffects.SHRINK.get().getDescriptionId(), "Shrink");
		builder.add(ArcanusMobEffects.ENLARGE.get().getDescriptionId(), "Enlarge");

		tag(builder, ArcanusBiomeTags.C_HAS_VILLAGE, "Has Village");
		tag(builder, ArcanusBiomeTags.HAS_WIZARD_TOWER, "Has Arcanist Tower");
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
		tag(builder, ArcanusItemTags.ARCANIST_ARMOR, "Arcanist Armor");

		builder.add(SPELL_BOOK_SLOT, "Spell Book");
		builder.add(BRACELET_HAND_SLOT, "Bracelet");

		builder.add(BLOCK_IS_WARDED, "This block is warded.");
		builder.add(CANT_WARD_IN_DIM, "Cannot ward blocks in this dimension!");
		builder.add(CANT_WARD_BLOCK, "Cannot ward this block!");
		builder.add(DISABLED_COMPONENT, "Disabled Spell Component");
		builder.add(SPELL_HAS_DISABLED_COMPONENT, "This spell contains one or more disabled spell components. Aborting spell.");
		builder.add(TOO_MANY_ORBS, "That target already has the maximum amount of Stockpile Orbs!");

		builder.add(USE_SCROLL_SUCCESS, "You feel a little smarter...");
		builder.add(USE_SCROLL_ALREADY_KNOW, "You have learned everything you can.");
		builder.add(USE_SCROLL_DISABLED_COMPONENT, "This spell component doesn't seem possible.");

		builder.add(ARCANIST_NO_ARCANIST_ARMOR, "Come back to me when you look like a arcanist.");
		builder.add(ARCANIST_ARMOR_HINT, "Maybe wearing Arcanist Robes will help...");

		builder.add(SET_BONUS, "Full Set Bonus: +40 %s");
		builder.add(UNKNOWN, "???");

		builder.add(MAGIC_DOOR_ACCESS_GRANTED, "Access Granted...");
		builder.add(MAGIC_DOOR_NOT_OWNER, "You do not own this door");
		builder.add(MAGIC_DOOR_SAY_MAGIC_WORD, "This door needs a magic word to open");
		builder.add(MAGIC_DOOR_SET_PASSWORD, "Password set to %s");

		builder.add(STAFF_INVALID_DATA, "!!! INVALID DATA !!!");
		builder.add(STAFF_PRIMARY_COLOR, "Focus Color: %s");
		builder.add(STAFF_SECONDARY_COLOR, "Body Color: %s");

		builder.add(COMMAND_REGEN_POCKET_WALLS_ONLY, "Repaired the walls of %s's pocket dimension");
		builder.add(COMMAND_REGEN_POCKET_SUCCESS, "Regenerated %s's pocket dimension");
		builder.add(COMMAND_REGEN_POCKET_TELEPORT, "The pocket dimension plot you were in has been cleared by an admin.");

		builder.add(COMMAND_EXPORT_POCKET_SUCCESS, "Successfully exported %s's pocket dimension");
		builder.add(COMMAND_EXPORT_POCKET_DIM_NOT_FOUND, "unable to find %s!");
		builder.add(COMMAND_EXPORT_POCKET_POCKET_NOT_FOUND, "Pocket dimension for player %s has not been created yet!");

		builder.add(COMMAND_WIZARD_LEVEL_GET_SUCCESS, "%s's arcanist level is %s");
		builder.add(COMMAND_WIZARD_LEVEL_SET_SUCCESS, "Set %s's arcanist level to %s");

		builder.add(COMMAND_SPELL_COMPONENT_LIST_SUCCESS, "%s's known Spell Components:");
		builder.add(COMMAND_SPELL_COMPONENT_LIST_FAIL, "%s doesn't know any Spell Components");
		builder.add(COMMAND_SPELL_COMPONENT_LEARN_SUCCESS, "%s has learned %s");
		builder.add(COMMAND_SPELL_COMPONENT_LEARN_FAIL, "%s already knows %s");
		builder.add(COMMAND_SPELL_COMPONENT_REVOKE_SUCCESS, "%s has forgotten %s");
		builder.add(COMMAND_SPELL_COMPONENT_REVOKE_FAIL, "%s doesn't know %s");

		advancement(builder, ArcanusAdvancements.ARCANE_ROOT, "Arcanus", "[insert witty sales pitch for something you're already playing]");
		advancement(builder, ArcanusAdvancements.A_MAGICAL_CRYSTAL, "A Magical Crystal", "Find an Amethyst Shard");
		advancement(builder, ArcanusAdvancements.ARCANE_ARTIFICE, "Arcane Artifice", "Craft an Arcane Workbench");
		advancement(builder, ArcanusAdvancements.MAGICAL_MEET_AND_GREET, "Magical Meet & Greet", "Find an Arcanist Tower and meet the residents");

		damageType(builder, ArcanusDamageTypes.MAGIC, "%s was killed by %s using magic", null, "%s was killed by %s using magic with %s");
		damageType(builder, ArcanusDamageTypes.MAGIC_PROJECTILE, "%s was shot by %s using magic", null, "%s was shot by %s using magic with %s");

		builder.add(ArcanusAttributes.IGNIS_ARCANA.get().getDescriptionId(), "Ignis Arcana");
		builder.add(ArcanusAttributes.TERRA_ARCANA.get().getDescriptionId(), "Terra Arcana");
		builder.add(ArcanusAttributes.AQUA_ARCANA.get().getDescriptionId(), "Aqua Arcana");
		builder.add(ArcanusAttributes.AER_ARCANA.get().getDescriptionId(), "Aer Arcana");
		builder.add(ArcanusAttributes.AETHER_ARCANA.get().getDescriptionId(), "Aether Arcana");
		builder.add(ArcanusAttributes.IGNIS_ARCANA_REGEN.get().getDescriptionId(), "Ignis Arcana Regeneration");
		builder.add(ArcanusAttributes.TERRA_ARCANA_REGEN.get().getDescriptionId(), "Terra Arcana Regeneration");
		builder.add(ArcanusAttributes.AQUA_ARCANA_REGEN.get().getDescriptionId(), "Aqua Arcana Regeneration");
		builder.add(ArcanusAttributes.AER_ARCANA_REGEN.get().getDescriptionId(), "Aer Arcana Regeneration");
		builder.add(ArcanusAttributes.AETHER_ARCANA_REGEN.get().getDescriptionId(), "Aether Arcana Regeneration");
		builder.add(ArcanusAttributes.ARCANA_LOCK.get().getDescriptionId(), "Arcana Lock");
		builder.add(ArcanusAttributes.SPELL_POTENCY.get().getDescriptionId(), "Spell Potency");
		builder.add(ArcanusAttributes.MANA_COST.get().getDescriptionId(), "Arcana Cost");
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
		spell(builder, ArcanusSpellComponents.MAGIC_ORB, "Magic Orb Shape");
		spell(builder, ArcanusSpellComponents.STOCKPILE, "Stockpile Shape");

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
		spell(builder, ArcanusSpellComponents.SPATIAL_RIFT, "Spatial Rift Effect");
		spell(builder, ArcanusSpellComponents.WARDING, "Warding Effect");
		spell(builder, ArcanusSpellComponents.DISCOMBOBULATE, "Discombobulate Effect");
		spell(builder, ArcanusSpellComponents.HASTE, "Haste Effect");
		spell(builder, ArcanusSpellComponents.FLOAT, "Float Effect");
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
		builder.add(SPELL_BOOK_MANA_MULTIPLIER, "Arcana Multiplier");
		builder.add(SPELL_BOOK_POTENCY_MODIFIER, "Potency Modifier");
		builder.add(SPELL_BOOK_COOL_DOWN_MODIFIER, "Cool Down Modifier");
		builder.add(SPELL_BOOK_COOL_DOWN, "Cool Down");
		builder.add(SPELL_BOOK_INSTANT_COOL_DOWN, "Instant");
		builder.add(SPELL_BOOK_SPELL_WITH_PATTERN_TOOLTIP, "%s (%s)");

		builder.add(SPELL_TOO_MANY_COMPONENTS, "Too many components!");
		builder.add(SPELL_UNKNOWN_SPELL_COMPONENTS, "You don't know some of these spell components!");
		builder.add(SPELL_NOT_ENOUGH_MANA, "Not enough arcana!");

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
		builder.add(CONFIG_SPELL_SHAPES_CATEGORY, "Spell Shapes");
		builder.add(CONFIG_ATTACK_EFFECTS_CATEGORY, "Attack Effects");
		builder.add(CONFIG_SUPPORT_EFFECTS_CATEGORY, "Support Effects");
		builder.add(CONFIG_UTILITY_EFFECTS_CATEGORY, "Utility Effects");
		builder.add(CONFIG_MOVEMENT_EFFECTS_CATEGORY, "Movement Effects");
		builder.add(CONFIG_CASTING_HAS_SPEED_LIMIT, "Use Attack Cool Down for Casting?");
		builder.add(CONFIG_SIZE_CHANGE_IS_PERMA, "Size Changing Effects are Permanent for Players and Tamed Animals (Requires Pehkui)");
		builder.add(CONFIG_SCALE_ARCANA_BARS, "Scale Arcana Bars Based on Maximum Arcana");
		builder.add(CONFIG_ARCANA_BARS_MIN_LENGTH, "Min Length of Scaled Arcana Bars");
		builder.add(CONFIG_ARCANA_BARS_MAX_LENGTH, "Max Length of Scaled Arcana Bars");
		builder.add(CONFIG_COOL_DOWN_PER_COMPONENT, "Cool Down in Ticks Added Per Component");
		builder.add(CONFIG_MINIMUM_COOL_DOWN, "Minimum Spell Cool Down");
		builder.add(CONFIG_ARCANA_BARS_ON_TOP, "Move Arcana Bars to the Top");
		builder.add(CONFIG_RIGHT_SIDE_ARCANA_BARS, "Move Arcana Bars to the Right");
		builder.add(CONFIG_NUMERICAL_ARCANA_DISPLAY, "Display Exact Arcana Amounts by Arcana Bars");
		builder.add(CONFIG_MAX_ENCHANT_LEVEL, "Max Enchantment Level");
		builder.add(CONFIG_ARCANA_MANA_PER_LEVEL, "Extra Arcana Per Level");
		builder.add(CONFIG_ARCANA_MODIFIER_OP, "Arcana Modifier Operation");
		builder.add(CONFIG_ENABLED, "Enabled");
		builder.add(CONFIG_WEIGHT, "Weight");
		builder.add(CONFIG_RED_MANA_COST, "Ignis Arcana Cost");
		builder.add(CONFIG_GREEN_MANA_COST, "Terra Arcana Cost");
		builder.add(CONFIG_BLUE_MANA_COST, "Aqua Arcana Cost");
		builder.add(CONFIG_WHITE_MANA_COST, "Aer Arcana Cost");
		builder.add(CONFIG_BLACK_MANA_COST, "Aether Arcana Cost");
		builder.add(CONFIG_MANA_MODIFIER, "Arcana Cost Modifier");
		builder.add(CONFIG_COOL_DOWN_MODIFIER, "Cool Down Modifier");
		builder.add(CONFIG_PROCS_ONCE, "Only Procs Once When Chained");
		builder.add(CONFIG_POTENCY_MODIFIER, "Potency Modifier");
		builder.add(CONFIG_PROJECTILE_SPEED, "Projectile Speed");
		builder.add(CONFIG_BASE_LIFE_SPAN, "Base Life Span");
		builder.add(CONFIG_LIFE_SPAN_MODIFIER, "Life Span Modifier");
		builder.add(CONFIG_RANGE, "Range");
		builder.add(CONFIG_DELAY, "Delay");
		builder.add(CONFIG_BURST_SHAPE_RADIUS, "Burst Radius");
		builder.add(CONFIG_MAX_MANA_LOCK, "Maximum Arcana Lock");
		builder.add(CONFIG_MAXIMUM_STOCKPILE_ORBS, "Maximum Stockpile Orbs");
		builder.add(CONFIG_STOCKPILE_ORBS_PER_CAST, "How Many Stockpile Orbs Spawn per Cast");
		builder.add(CONFIG_BASE_DAMAGE, "Base Damage");
		builder.add(CONFIG_BASE_TIME_ON_FIRE, "Base Time On Fire");
		builder.add(CONFIG_BASE_STUN_TIME, "Base Stun Time");
		builder.add(TranslationKeys.CONFIG_STUNNED_ENTITY_DAMAGE_MULTIPLIER, "Damage Multiplier Against Stunned Entities");
		builder.add(TranslationKeys.CONFIG_FROZEN_ENTITY_DAMAGE_MULTIPLIER, "Damage Multiplier Against Frozen Entities");
		builder.add(TranslationKeys.CONFIG_BURNING_ENTITY_DAMAGE_MULTIPLIER, "Damage Multiplier Against Burning Entities");
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
		builder.add(CONFIG_BASE_MANA_DRAIN, "Base Arcana Drain");
		builder.add(CONFIG_TIMES_TO_APPLY_EFFECTS, "Number of Times to Apply Spell Effects");
		builder.add(CONFIG_TIMES_TO_CAST_NEXT_SHAPE, "Number of Times to Cast Next Spell Shape");
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
