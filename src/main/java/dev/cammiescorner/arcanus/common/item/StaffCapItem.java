package dev.cammiescorner.arcanus.common.item;

import dev.cammiescorner.arcanus.api.staff.StaffCap;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class StaffCapItem extends Item {
	public StaffCapItem() {
		super(new Properties().component(ArcanusDataComponents.STAFF_CAP.get(), null));
	}

	@Override
	public String getDescriptionId(ItemStack stack) {
		Holder<StaffCap> holder = stack.get(ArcanusDataComponents.STAFF_CAP.get());

		if(holder instanceof Holder.Reference<StaffCap> reference)
			return StaffCap.getDescriptionId(reference);

		return super.getDescriptionId(stack);
	}
}
