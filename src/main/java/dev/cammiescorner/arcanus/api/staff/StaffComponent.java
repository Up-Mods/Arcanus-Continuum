package dev.cammiescorner.arcanus.api.staff;

import net.minecraft.resources.ResourceLocation;

public interface StaffComponent {
	ResourceLocation getResourceLocation();
	ResourceLocation getItemModelLocation();
	ResourceLocation getStaffModelLocation();
	String getDescriptionId();
	String getStaffId();
}
