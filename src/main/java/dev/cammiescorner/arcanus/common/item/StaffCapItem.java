package dev.cammiescorner.arcanus.common.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class StaffCapItem extends Item {
	public StaffCapItem(Item.Properties properties) {
		super(properties);
	}

	public static ResourceLocation getStaffModelLocation(ResourceLocation location) {
		return location.withPrefix("arcanus/staff_part/staff_cap/");
	}
}
