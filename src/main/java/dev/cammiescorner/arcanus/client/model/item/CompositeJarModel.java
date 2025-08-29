package dev.cammiescorner.arcanus.client.model.item;

import dev.cammiescorner.arcanus.Arcanus;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class CompositeJarModel implements FabricBakedModel, BakedModel, UnbakedModel {
	private final List<BakedModel> bakedModels = new ArrayList<>();
	private final List<UnbakedModel> unbakedModels;
	private final UnbakedModel unbakedJarModel;
	private BakedModel bakedJarModel;

	public CompositeJarModel(UnbakedModel unbakedJarModel, List<UnbakedModel> unbakedModels) {
		this.unbakedJarModel = unbakedJarModel;
		this.unbakedModels = unbakedModels;
	}

	@Override
	public @Nullable BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState state) {
		bakedJarModel = baker.bake(Arcanus.id("block/jar"), state);

		for(int i = 1; i < unbakedModels.size() + 1; i++)
			bakedModels.add(baker.bake(Arcanus.id("block/jar/jar_fluid_" + i), state));

		return this;
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, RandomSource random) {
		List<BakedQuad> bakedQuads = new ArrayList<>(bakedJarModel.getQuads(state, direction, random));

		for(BakedModel bakedModel : bakedModels)
			bakedQuads.addAll(bakedModel.getQuads(state, direction, random));

		return bakedQuads;
	}

	@Override
	public boolean isVanillaAdapter() {
		return bakedJarModel.isVanillaAdapter();
	}

	@Override
	public boolean useAmbientOcclusion() {
		return bakedJarModel.useAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return bakedJarModel.isGui3d();
	}

	@Override
	public boolean usesBlockLight() {
		return bakedJarModel.usesBlockLight();
	}

	@Override
	public boolean isCustomRenderer() {
		return bakedJarModel.isCustomRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleIcon() {
		return bakedJarModel.getParticleIcon();
	}

	@Override
	public ItemTransforms getTransforms() {
		return bakedJarModel.getTransforms();
	}

	@Override
	public ItemOverrides getOverrides() {
		return bakedJarModel.getOverrides();
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return new ArrayList<>(unbakedJarModel.getDependencies());
	}

	@Override
	public void resolveParents(Function<ResourceLocation, UnbakedModel> resolver) {
		unbakedJarModel.resolveParents(resolver);
	}
}
