package dev.cammiescorner.arcanus.datagen.common;

import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.api.spells.components.SpellGroup;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetCustomDataFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.joml.Vector2i;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ArcanusChestLootProvider extends SimpleFabricLootTableProvider {
	public ArcanusChestLootProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(output, registryLookup, LootContextParamSets.CHEST);
	}

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
		// TODO figure this shit out too now
//		output.accept(ArcanusLootTables.WIZARD_TOWER_CHEST, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1f)).add(LootTableReference.lootTableReference(BuiltInLootTables.IGLOO_CHEST))));
//		output.accept(ArcanusLootTables.WIZARD_TOWER_BOOKSHELF, LootTable.lootTable()
//			.withPool(LootPool.lootPool()
//				.setRolls(UniformGenerator.between(0, 6))
//				.add(AlternativesEntry.alternatives(
//					LootItem.lootTableItem(ArcanusItems.SPELL_BOOK.get())
//						.apply(setSpellNbt(ArcanusChestLootProvider::healSelfSpell))
//						.when(LootItemRandomChanceCondition.randomChance(0.25f)),
//					LootItem.lootTableItem(ArcanusItems.SPELL_BOOK.get())
//						.apply(setSpellNbt(ArcanusChestLootProvider::healAllySpell))
//						.when(LootItemRandomChanceCondition.randomChance(0.25f)),
//					LootItem.lootTableItem(ArcanusItems.SPELL_BOOK.get())
//						.apply(setSpellNbt(ArcanusChestLootProvider::paladinsShieldSpell))
//						.when(LootItemRandomChanceCondition.randomChance(0.25f)),
//					LootItem.lootTableItem(ArcanusItems.SPELL_BOOK.get())
//						.apply(setSpellNbt(ArcanusChestLootProvider::fireballSpell))
//						.when(LootItemRandomChanceCondition.randomChance(0.25f)),
//					LootItem.lootTableItem(ArcanusItems.SPELL_BOOK.get())
//						.apply(setSpellNbt(ArcanusChestLootProvider::magicMissileSpell))
//						.when(LootItemRandomChanceCondition.randomChance(0.25f)),
//					LootItem.lootTableItem(ArcanusItems.SPELL_BOOK.get())
//						.apply(setSpellNbt(ArcanusChestLootProvider::smiteSpell))
//						.when(LootItemRandomChanceCondition.randomChance(0.25f)),
//					LootItem.lootTableItem(ArcanusItems.SPELL_BOOK.get())
//						.apply(setSpellNbt(ArcanusChestLootProvider::blinkSpell))
//						.when(LootItemRandomChanceCondition.randomChance(0.25f)),
//					LootItem.lootTableItem(ArcanusItems.SPELL_BOOK.get())
//						.apply(setSpellNbt(ArcanusChestLootProvider::zoomiesSpell))
//						.when(LootItemRandomChanceCondition.randomChance(0.25f))
//				))
//				.add(LootItem.lootTableItem(Items.WRITABLE_BOOK).setWeight(5))
//				.add(LootItem.lootTableItem(Items.BOOK).setWeight(5))
//				.add(LootItem.lootTableItem(Items.BOOK).setWeight(3).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(1, 3))))
//				.add(LootItem.lootTableItem(Items.BOOK).setWeight(2).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(11, 20))))
//				.add(LootItem.lootTableItem(Items.BOOK).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(21, 30))))
//			)
//		);
	}

	public static LootItemConditionalFunction.Builder<?> setSpellNbt(Supplier<Spell> spell) {
		CompoundTag outerTag = new CompoundTag();
		outerTag.put("Spell", spell.get().toNbt());
		return SetCustomDataFunction.setCustomData(outerTag);
	}

	private static Spell healSelfSpell() {
		return new Spell(List.of(new SpellGroup(ArcanusSpellComponents.SELF.get(), List.of(ArcanusSpellComponents.HEAL.get()), List.of(new Vector2i(82, 83), new Vector2i(146, 81)))), "Heal Self");
	}

	private static Spell healAllySpell() {
		return new Spell(List.of(new SpellGroup(ArcanusSpellComponents.MISSILE.get(), List.of(ArcanusSpellComponents.HEAL.get(), ArcanusSpellComponents.HEAL.get()), List.of(new Vector2i(80, 84), new Vector2i(133, 48), new Vector2i(139, 118)))), "Heal Ally");
	}

	private static Spell paladinsShieldSpell() {
		return new Spell(List.of(
			new SpellGroup(ArcanusSpellComponents.SELF.get(), List.of(ArcanusSpellComponents.MANA_SHIELD.get()), List.of(new Vector2i(119, 40), new Vector2i(172, 68))),
			new SpellGroup(ArcanusSpellComponents.AOE.get(), List.of(ArcanusSpellComponents.HEAL.get(), ArcanusSpellComponents.HEAL.get(), ArcanusSpellComponents.HEAL.get()), List.of(new Vector2i(146, 120), new Vector2i(94, 133), new Vector2i(67, 76), new Vector2i(120, 85)))
		), "Paladin's Shield");
	}

	private static Spell fireballSpell() {
		return new Spell(List.of(new SpellGroup(ArcanusSpellComponents.LOB.get(), List.of(ArcanusSpellComponents.DAMAGE.get(), ArcanusSpellComponents.DAMAGE.get(), ArcanusSpellComponents.DAMAGE.get(), ArcanusSpellComponents.FIRE.get()), List.of(new Vector2i(118, 81), new Vector2i(116, 36), new Vector2i(71, 82), new Vector2i(118, 129), new Vector2i(169, 81)))), "Fireball");
	}

	private static Spell magicMissileSpell() {
		return new Spell(List.of(new SpellGroup(ArcanusSpellComponents.MISSILE.get(), List.of(ArcanusSpellComponents.DAMAGE.get(), ArcanusSpellComponents.DAMAGE.get()), List.of(new Vector2i(86, 82), new Vector2i(146, 81), new Vector2i(117, 127)))), "Magic Missile");
	}

	private static Spell smiteSpell() {
		return new Spell(List.of(
			new SpellGroup(ArcanusSpellComponents.SMITE.get(), List.of(ArcanusSpellComponents.DAMAGE.get(), ArcanusSpellComponents.DAMAGE.get(), ArcanusSpellComponents.DAMAGE.get(), ArcanusSpellComponents.DAMAGE.get()), List.of(new Vector2i(117, 46), new Vector2i(173, 73), new Vector2i(144, 128), new Vector2i(77, 124), new Vector2i(60, 65))),
			new SpellGroup(ArcanusSpellComponents.BURST.get(), List.of(), List.of(new Vector2i(117, 91)))
		), "Smite");
	}

	private static Spell blinkSpell() {
		return new Spell(List.of(new SpellGroup(ArcanusSpellComponents.BOLT.get(), List.of(ArcanusSpellComponents.TELEPORT.get(), ArcanusSpellComponents.TELEPORT.get(), ArcanusSpellComponents.TELEPORT.get()), List.of(new Vector2i(119, 50), new Vector2i(159, 98), new Vector2i(116, 135), new Vector2i(79, 92)))), "Blink");
	}

	private static Spell zoomiesSpell() {
		return new Spell(List.of(new SpellGroup(ArcanusSpellComponents.SELF.get(), List.of(ArcanusSpellComponents.SPEED.get(), ArcanusSpellComponents.SPEED.get(), ArcanusSpellComponents.SPEED.get(), ArcanusSpellComponents.SPEED.get()), List.of(new Vector2i(121, 41), new Vector2i(168, 88), new Vector2i(115, 134), new Vector2i(69, 84), new Vector2i(118, 85)))), "Zoomies");
	}
}
