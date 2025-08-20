package dev.cammiescorner.arcanus.common.item;

import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class StaffCapItem extends Item {
	public StaffCapItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public String getDescriptionId(ItemStack stack) {
		ResourceLocation location = BuiltInRegistries.ITEM.getKey(stack.getItem());

		if(stack.has(ArcanusDataComponents.STAFF_CAP.get()) && stack.get(ArcanusDataComponents.STAFF_CAP.get()).isInert())
			location = location.withPrefix("inert_");

		return Util.makeDescriptionId("item", location);
	}

	public static ResourceLocation getStaffModelLocation(ResourceLocation location) {
		return location.withPrefix("arcanus/staff_part/staff_cap/");
	}
}
