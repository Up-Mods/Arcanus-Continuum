package dev.cammiescorner.arcanus.common.item;

import dev.cammiescorner.arcanus.api.staff.StaffCore;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class StaffCoreItem extends Item {
	public StaffCoreItem() {
		super(new Properties().component(ArcanusDataComponents.STAFF_CORE.get(), null));
	}

	@Override
	public String getDescriptionId(ItemStack stack) {
		Holder<StaffCore> holder = stack.get(ArcanusDataComponents.STAFF_CORE.get());

		if(holder instanceof Holder.Reference<StaffCore> reference)
			return StaffCore.getDescriptionId(reference);

		return super.getDescriptionId(stack);
	}
}
