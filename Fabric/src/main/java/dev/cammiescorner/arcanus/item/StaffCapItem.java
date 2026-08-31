package dev.cammiescorner.arcanus.item;

import dev.cammiescorner.arcanus.registry.ArcanusDataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class StaffCapItem extends Item {
	public StaffCapItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public Component getName(ItemStack itemStack) {
		Identifier location = BuiltInRegistries.ITEM.getKey(itemStack.getItem());

		if(itemStack.has(ArcanusDataComponents.STAFF_CAP.get()) && itemStack.get(ArcanusDataComponents.STAFF_CAP.get()).isInert())
			location = location.withPrefix("inert_");

		return Component.translatable(Util.makeDescriptionId("item", location));
	}

	public static Identifier getStaffModelLocation(Identifier identifier) {
		return identifier.withPrefix("arcanus/staff_part/staff_cap/");
	}
}
