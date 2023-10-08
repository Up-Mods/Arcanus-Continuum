package dev.cammiescorner.arcanuscontinuum.common.compat;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.item.ItemStack;
import vazkii.patchouli.api.PatchouliAPI;

public class PatchouliCompat {

	public static ItemStack getCompendiumArcanus() {
		return PatchouliAPI.get().getBookStack(Arcanus.id("compendium_arcanus"));
	}
}
