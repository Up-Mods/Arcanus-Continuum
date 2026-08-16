package dev.cammiescorner.arcanus.common.creative_tabs;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.data_component.StaffCapComponent;
import dev.cammiescorner.arcanus.common.data_component.StaffParts;
import dev.cammiescorner.arcanus.common.item.StaffCapItem;
import dev.cammiescorner.arcanus.common.item.StaffCoreItem;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import dev.upcraft.sparkweave.api.item.CreativeTabHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ArtificeCreativeTab {
	public static final ResourceKey<CreativeModeTab> RESOURCE_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Arcanus.id("01_artifice_tab"));

	public static CreativeModeTab buildTab() {
		return CreativeTabHelper.newBuilder(RESOURCE_KEY).icon(ArcanusItems.ARTIFICER_HELMET.get()::getDefaultInstance).displayItems((params, output) -> {
			ItemStack ebonyStaff = new ItemStack(ArcanusItems.STAFF.get());
			ItemStack tbdStaff = new ItemStack(ArcanusItems.STAFF.get());

			ebonyStaff.set(ArcanusDataComponents.STAFF_PARTS.get(), new StaffParts(ArcanusItems.EBONY_STAFF_CORE.get().getDefaultInstance(), ArcanusItems.GOLDEN_STAFF_CAP.get().getDefaultInstance()));
			tbdStaff.set(ArcanusDataComponents.STAFF_PARTS.get(), new StaffParts(ArcanusItems.TBD_STAFF_CORE.get().getDefaultInstance(), ArcanusItems.ARCANEUM_STAFF_CAP.get().getDefaultInstance()));

			output.accept(ArcanusItems.ARTIFICER_HELMET.get());
			output.accept(ArcanusItems.ARTIFICER_CHESTPLATE.get());
			output.accept(ArcanusItems.ARTIFICER_LEGGINGS.get());
			output.accept(ArcanusItems.ARTIFICER_BOOTS.get());

			output.accept(ArcanusItems.BOOK_POUCH.get());

			output.accept(ArcanusItems.STAFF.get());
			output.accept(ebonyStaff);

			BuiltInRegistries.ITEM.stream().filter(item -> item instanceof StaffCapItem && BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(Arcanus.MOD_ID)).forEach(item -> {
				ItemStack stack = item.getDefaultInstance();
				StaffCapComponent component = stack.get(ArcanusDataComponents.STAFF_CAP.get());
				output.accept(item);

				if(stack.has(ArcanusDataComponents.STAFF_CAP.get()) && component.isInert() && !stack.is(ArcanusItems.NETHERITE_STAFF_CAP.get())) {
					stack.set(ArcanusDataComponents.STAFF_CAP.get(), new StaffCapComponent(component.potency()));
					output.accept(stack);
				}
			});

			// staff cores
			BuiltInRegistries.ITEM.stream().filter(item -> item instanceof StaffCoreItem && BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(Arcanus.MOD_ID)).forEach(output::accept);
		}).build();
	}
}
