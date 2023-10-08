package dev.cammiescorner.arcanuscontinuum.common.registry;

import com.google.common.collect.ImmutableMap;
import dev.cammiescorner.arcanuscontinuum.common.compat.ArcanusCompat;
import dev.cammiescorner.arcanuscontinuum.common.compat.PatchouliCompat;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.Util;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArcanusTradeOffers {
	public static final Int2ObjectMap<TradeOffers.Factory[]> WIZARD_TRADES = Util.make(() -> {
		List<TradeOffers.Factory> factories = new ArrayList<>(32);
		Collections.addAll(factories,
			new SellItemFactory(Items.GLOWSTONE_DUST, 3, 3, 100, 1),
			new SellItemFactory(Items.LAPIS_LAZULI, 3, 3, 100, 1),
			new SellItemFactory(Items.GUNPOWDER, 3, 5, 100, 1),
			new SellItemFactory(Items.REDSTONE, 3, 5, 100, 1),
			new SellItemFactory(ArcanusItems.WOODEN_STAFF.get(), 5, 1, 100, 3),
			new SellItemFactory(ArcanusItems.CRYSTAL_STAFF.get(), 5, 1, 100, 3),
			new SellItemFactory(ArcanusItems.DIVINATION_STAFF.get(), 5, 1, 100, 3),
			new SellItemFactory(ArcanusItems.CRESCENT_STAFF.get(), 5, 1, 100, 3),
			new SellItemFactory(ArcanusItems.ANCIENT_STAFF.get(), 5, 1, 100, 3),
			new SellItemFactory(ArcanusItems.MAGIC_TOME.get(), 5, 1, 100, 3),
			new SellItemFactory(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.INVISIBILITY), 10, 100, 5),
			new SellItemFactory(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.NIGHT_VISION), 10, 100, 5),
			new SellItemFactory(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.LEAPING), 10, 100, 5),
			new SellItemFactory(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.FIRE_RESISTANCE), 10, 100, 5),
			new SellItemFactory(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.SWIFTNESS), 10, 100, 5),
			new SellItemFactory(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER_BREATHING), 10, 100, 5),
			new SellItemFactory(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.LONG_REGENERATION), 10, 100, 5),
			new SellItemFactory(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.STRENGTH), 10, 100, 5),
			new SellItemFactory(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.SLOW_FALLING), 10, 100, 5)
		);
		ArcanusCompat.PATCHOULI.ifEnabled(() -> () -> factories.add(0, new SellItemFactory(PatchouliCompat.getCompendiumArcanus(), 2, 100, 1)));

		return new Int2ObjectOpenHashMap<>(
			ImmutableMap.of(
				1, factories.toArray(TradeOffers.Factory[]::new),
				2, new TradeOffers.Factory[]{
					new SellItemFactory(ArcanusItems.SCROLL_OF_KNOWLEDGE.get(), 64, 1, 100, 10)
				}
			)
		);
	});

	private static class SellItemFactory implements TradeOffers.Factory {
		private final ItemStack sell;
		private final int price;
		private final int maxUses;
		private final int experience;

		public SellItemFactory(Item item, int price, int count, int maxUses, int experience) {
			this(new ItemStack(item, count), price, maxUses, experience);
		}

		public SellItemFactory(ItemStack stack, int price, int maxUses, int experience) {
			this.sell = stack;
			this.price = price;
			this.maxUses = maxUses;
			this.experience = experience;
		}

		@Override
		public TradeOffer create(Entity entity, RandomGenerator random) {
			return new TradeOffer(new ItemStack(Items.AMETHYST_SHARD, price), sell, maxUses, experience, 0.05F);
		}
	}
}
