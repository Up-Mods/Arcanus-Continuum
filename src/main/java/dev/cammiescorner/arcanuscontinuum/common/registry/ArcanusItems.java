package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.items.CompendiumItem;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class ArcanusItems {
	//-----Item Map-----//
	public static final LinkedHashMap<Item, Identifier> ITEMS = new LinkedHashMap<>();

	//-----Items-----//
	public static final Item COMPENDIUM_ARCANUS = create("compendium_arcanus", new CompendiumItem());
	public static final Item WOODEN_STAFF = create("wooden_staff", new StaffItem());
	public static final Item AMETHYST_SHARD_STAFF = create("amethyst_shard_staff", new StaffItem());
	public static final Item QUARTZ_SHARD_STAFF = create("quartz_shard_staff", new StaffItem());
	public static final Item ENDER_SHARD_STAFF = create("ender_shard_staff", new StaffItem());
	public static final Item ECHO_SHARD_STAFF = create("echo_shard_staff", new StaffItem());

	//-----Registry-----//
	public static void register() {
		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
	}

	private static <T extends Item> T create(String name, T item) {
		ITEMS.put(item, Arcanus.id(name));
		return item;
	}
}
