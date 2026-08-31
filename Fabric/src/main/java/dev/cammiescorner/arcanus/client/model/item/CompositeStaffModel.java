package dev.cammiescorner.arcanus.client.model.item;

import dev.cammiescorner.arcanus.data_component.StaffParts;
import dev.cammiescorner.arcanus.item.StaffCapItem;
import dev.cammiescorner.arcanus.item.StaffCoreItem;
import dev.cammiescorner.arcanus.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.registry.ArcanusItems;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompositeStaffModel implements FabricBakedModel, BakedModel, UnbakedModel {
	private final Map<StaffCoreItem, BakedModel> bakedCoreModels = new HashMap<>();
	private final Map<StaffCapItem, BakedModel> bakedCapModels = new HashMap<>();
	private final Map<StaffCoreItem, UnbakedModel> unbakedCoreModels;
	private final Map<StaffCapItem, UnbakedModel> unbakedCapModels;

	public CompositeStaffModel(Map<StaffCoreItem, UnbakedModel> unbakedCoreModels, Map<StaffCapItem, UnbakedModel> unbakedCapModels) {
		this.unbakedCoreModels = unbakedCoreModels;
		this.unbakedCapModels = unbakedCapModels;
	}

	@Override
	public void emitItemQuads(ItemStack stack, Supplier<RandomSource> randomSupplier, RenderContext context) {
		StaffParts staffParts = stack.getOrDefault(ArcanusDataComponents.STAFF_PARTS.get(), StaffParts.defaultInstance());

		bakedCoreModels.get((StaffCoreItem) staffParts.staffCore().getItem()).emitItemQuads(stack, randomSupplier, context);
		bakedCapModels.get((StaffCapItem) staffParts.staffCap().getItem()).emitItemQuads(stack, randomSupplier, context);
	}

	@Override
	public @Nullable BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState state) {
		Registry<Item> registry = BuiltInRegistries.ITEM;

		for(ResourceLocation location : registry.keySet()) {
			Item item = registry.get(location);

			if(item instanceof StaffCoreItem staffCore)
				bakedCoreModels.put(staffCore, baker.bake(StaffCoreItem.getStaffModelLocation(location), state));
			if(item instanceof StaffCapItem staffCap)
				bakedCapModels.put(staffCap, baker.bake(StaffCapItem.getStaffModelLocation(location), state));
		}

		return this;
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, RandomSource random) {
		List<BakedQuad> bakedQuads = new ArrayList<>();

		for(Map.Entry<StaffCoreItem, BakedModel> entry : bakedCoreModels.entrySet())
			bakedQuads.addAll(entry.getValue().getQuads(state, direction, random));
		for(Map.Entry<StaffCapItem, BakedModel> entry : bakedCapModels.entrySet())
			bakedQuads.addAll(entry.getValue().getQuads(state, direction, random));

		return bakedQuads;
	}

	@Override
	public boolean isVanillaAdapter() {
		return false;
	}

	@Override
	public boolean useAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return false;
	}

	@Override
	public boolean usesBlockLight() {
		return false;
	}

	@Override
	public boolean isCustomRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleIcon() {
		return bakedCoreModels.get(ArcanusItems.WOODEN_STAFF_CORE.get()).getParticleIcon();
	}

	@Override
	public ItemTransforms getTransforms() {
		return bakedCoreModels.get(ArcanusItems.WOODEN_STAFF_CORE.get()).getTransforms();
	}

	@Override
	public ItemOverrides getOverrides() {
		return bakedCoreModels.get(ArcanusItems.WOODEN_STAFF_CORE.get()).getOverrides();
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		List<ResourceLocation> dependencies = new ArrayList<>();

		for(Map.Entry<StaffCoreItem, UnbakedModel> entry : unbakedCoreModels.entrySet())
			dependencies.addAll(entry.getValue().getDependencies());
		for(Map.Entry<StaffCapItem, UnbakedModel> entry : unbakedCapModels.entrySet())
			dependencies.addAll(entry.getValue().getDependencies());

		return dependencies;
	}

	@Override
	public void resolveParents(Function<ResourceLocation, UnbakedModel> resolver) {
		for(Map.Entry<StaffCoreItem, UnbakedModel> entry : unbakedCoreModels.entrySet())
			entry.getValue().resolveParents(resolver);
		for(Map.Entry<StaffCapItem, UnbakedModel> entry : unbakedCapModels.entrySet())
			entry.getValue().resolveParents(resolver);
	}
}
