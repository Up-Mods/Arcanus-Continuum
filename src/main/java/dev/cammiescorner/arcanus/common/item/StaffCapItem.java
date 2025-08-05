package dev.cammiescorner.arcanus.common.item;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class StaffCapItem extends Item {
	public StaffCapItem(Item.Properties properties) {
		super(properties);
	}

	public static ResourceLocation getStaffModelLocation(Holder.Reference<StaffCapItem> holder) {
		return holder.key().location().withPrefix("arcanus/staff_part/staff_core/");
	}
}
