package dev.cammiescorner.arcanus.client.plugin;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.model.item.CompositeJarModel;
import dev.cammiescorner.arcanus.client.model.item.CompositeStaffModel;
import dev.cammiescorner.arcanus.common.item.StaffCapItem;
import dev.cammiescorner.arcanus.common.item.StaffCoreItem;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffModelLoadingPlugin implements ModelLoadingPlugin {
	public static final ModelResourceLocation STAFF_RESOURCE_LOCATION = ModelResourceLocation.inventory(Arcanus.id("staff"));
	public static final ModelResourceLocation JAR_RESOURCE_LOCATION = ModelResourceLocation.inventory(Arcanus.id("jar"));

	@Override
	public void onInitializeModelLoader(Context pluginContext) {
		Registry<Item> registry = BuiltInRegistries.ITEM;

		for(ResourceLocation location : registry.keySet()) {
			Item item = registry.get(location);

			if(item instanceof StaffCoreItem)
				pluginContext.addModels(StaffCoreItem.getStaffModelLocation(location));
			if(item instanceof StaffCapItem)
				pluginContext.addModels(StaffCapItem.getStaffModelLocation(location));
		}

		pluginContext.modifyModelOnLoad().register((model, context) -> {
			if(STAFF_RESOURCE_LOCATION.equals(context.topLevelId())) {
				Map<StaffCoreItem, UnbakedModel> coreModels = new HashMap<>();
				Map<StaffCapItem, UnbakedModel> capModels = new HashMap<>();

				for(ResourceLocation location : registry.keySet()) {
					Item item = registry.get(location);

					if(item instanceof StaffCoreItem staffCore)
						coreModels.put(staffCore, context.getOrLoadModel(StaffCoreItem.getStaffModelLocation(location)));
					if(item instanceof StaffCapItem staffCap)
						capModels.put(staffCap, context.getOrLoadModel(StaffCapItem.getStaffModelLocation(location)));
				}

				return new CompositeStaffModel(coreModels, capModels);
			}

			if(JAR_RESOURCE_LOCATION.equals(context.topLevelId())) {
				List<UnbakedModel> unbakedModels = new ArrayList<>();
				UnbakedModel unbakedJarModel = context.getOrLoadModel(Arcanus.id("block/jar"));

				for(int i = 1; i < 9; i++)
					unbakedModels.add(context.getOrLoadModel(Arcanus.id("block/jar/jar_fluid_" + i)));

				return new CompositeJarModel(unbakedJarModel, unbakedModels);
			}

			return model;
		});
	}
}
