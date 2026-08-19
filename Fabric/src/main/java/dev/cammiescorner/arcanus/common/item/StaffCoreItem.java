package dev.cammiescorner.arcanus.common.item;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public class StaffCoreItem extends Item {
	public StaffCoreItem(Item.Properties properties) {
		super(properties);
	}

	public static Identifier getStaffModelLocation(Identifier identifier) {
		return identifier.withPrefix("arcanus/staff_part/staff_core/");
	}
}
