package dev.cammiescorner.arcanus.entrypoints;

import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.client.gui.overlay.ArcanaBarOverlay;
import dev.cammiescorner.arcanus.client.gui.overlay.FirstPersonCastingOverlay;
import dev.cammiescorner.arcanus.client.gui.overlay.StunOverlay;
import dev.cammiescorner.arcanus.client.model.armor.ArcanistRobesModel;
import dev.cammiescorner.arcanus.client.model.armor.ArtificerArmorModel;
import dev.cammiescorner.arcanus.client.model.armor.CultistRobesModel;
import dev.cammiescorner.arcanus.client.model.block.SpellScrollModel;
import dev.cammiescorner.arcanus.client.model.entity.living.ArcanistModel;
import dev.cammiescorner.arcanus.client.model.entity.living.OpossumModel;
import dev.cammiescorner.arcanus.client.model.entity.magic.*;
import dev.cammiescorner.arcanus.client.model.feature.HaloModel;
import dev.cammiescorner.arcanus.client.model.feature.SpellBookModel;
import dev.cammiescorner.arcanus.client.model.feature.SpellPatternModel;
import dev.cammiescorner.arcanus.client.particle.CollapseParticle;
import dev.cammiescorner.arcanus.client.plugin.StaffModelLoadingPlugin;
import dev.cammiescorner.arcanus.client.renderer.world.WardedBlockRenderer;
import dev.cammiescorner.arcanus.client.util.JarRenderData;
import dev.cammiescorner.arcanus.common.block.ManaBeanBlock;
import dev.cammiescorner.arcanus.common.data_component.ArcanaStorage;
import dev.cammiescorner.arcanus.common.registry.*;
import dev.upcraft.sparkweave.api.color.Color;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.component.DyedItemColor;

public class FabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		final Minecraft client = Minecraft.getInstance();

		EntityModelLayerRegistry.registerModelLayer(ArcanistRobesModel.MODEL_LAYER, ArcanistRobesModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ArtificerArmorModel.MODEL_LAYER, ArtificerArmorModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(CultistRobesModel.MODEL_LAYER, CultistRobesModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ArcanistModel.MODEL_LAYER, ArcanistModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(OpossumModel.MODEL_LAYER, OpossumModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(MagicLobModel.MODEL_LAYER, MagicLobModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(MagicProjectileModel.MODEL_LAYER, MagicProjectileModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(MagicRuneModel.MODEL_LAYER, MagicRuneModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(AreaOfEffectModel.MODEL_LAYER, AreaOfEffectModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(SpellPatternModel.MODEL_LAYER, SpellPatternModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(HaloModel.MODEL_LAYER, HaloModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(EntangledOrbModel.MODEL_LAYER, EntangledOrbModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(PocketDimensionPortalModel.MODEL_LAYER, PocketDimensionPortalModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(SpatialRiftSigilModel.MODEL_LAYER, SpatialRiftSigilModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(AggressorbModel.MODEL_LAYER, AggressorbModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(TemporalDilationFieldModel.MODEL_LAYER, TemporalDilationFieldModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(SpellScrollModel.MODEL_LAYER, SpellScrollModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(SpellBookModel.MODEL_LAYER, SpellBookModel::getTexturedModelData);

		ParticleFactoryRegistry.getInstance().register(ArcanusParticles.COLLAPSE.get(), CollapseParticle.Factory::new);

		ModelLoadingPlugin.register(new StaffModelLoadingPlugin());

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
			ArcanusBlocks.MAGIC_DOOR.get(),
			ArcanusBlocks.ARCANE_WORKBENCH.get()
		);

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.translucent(),
			ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get(),
			ArcanusBlocks.JAR.get(),
			ArcanusBlocks.IGNIS_BEAN.get(),
			ArcanusBlocks.TERRA_BEAN.get(),
			ArcanusBlocks.AQUA_BEAN.get(),
			ArcanusBlocks.AER_BEAN.get(),
			ArcanusBlocks.AETHER_BEAN.get()
		);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? DyedItemColor.getOrDefault(stack, 0xff52392a) : -1,
			ArcanusItems.ARCANIST_HAT.get(),
			ArcanusItems.ARCANIST_ROBES.get(),
			ArcanusItems.ARCANIST_PANTS.get(),
			ArcanusItems.ARCANIST_BOOTS.get(),
			ArcanusItems.SPELL_BOOK.get()
		);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 1 ? stack.getOrDefault(ArcanusDataComponents.ARCANA_STORAGE.get(), new ArcanaStorage(ArcanusArcana.NIL.get(), 0)).arcana().color().asIntARGB() : 0xffffffff,
			ArcanusBlocks.JAR.get()
		);

		ColorProviderRegistry.BLOCK.register((state, tintGetter, pos, tintIndex) -> tintIndex == 1 && state.getBlock() instanceof ManaBeanBlock arcanaFruit ? arcanaFruit.getArcanaType().color().asIntARGB() : 0xffffffff,
			ArcanusBlocks.IGNIS_BEAN.get(),
			ArcanusBlocks.TERRA_BEAN.get(),
			ArcanusBlocks.AQUA_BEAN.get(),
			ArcanusBlocks.AER_BEAN.get(),
			ArcanusBlocks.AETHER_BEAN.get()
		);

		ColorProviderRegistry.BLOCK.register((state, tintGetter, pos, tintIndex) -> tintIndex == 1 && tintGetter.getBlockEntityRenderData(pos) instanceof JarRenderData(
				Color color) ? color.asIntARGB() : 0xffffffff,
			ArcanusBlocks.JAR.get()
		);

		WorldRenderEvents.AFTER_ENTITIES.register(context -> {
			if(!context.camera().isDetached() && !ArcanusClient.FIRST_PERSON_MODEL_ENABLED.getAsBoolean())
				ArcanusClient.renderFirstPersonBolt(client.player, context.matrixStack(), context.camera(), context.tickCounter(), context.consumers());
		});

		WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register((context, outlineContext) -> {
			LocalPlayer player = client.player;
			MultiBufferSource bufferSource = context.consumers();

			if(player != null && bufferSource != null)
				WardedBlockRenderer.render(context.matrixStack(), bufferSource, player, context.camera().getPosition());

			return true;
		});

		HudRenderCallback.EVENT.register((gui, tickDelta) -> {
			if(client.player != null && !client.player.isSpectator() && !client.options.hideGui) {
				FirstPersonCastingOverlay.render(gui, tickDelta, client.player);
				StunOverlay.render(gui, tickDelta, client.player);
				ArcanaBarOverlay.render(gui, tickDelta, client.player);
			}
		});
	}
}
