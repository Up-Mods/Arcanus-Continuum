package dev.cammiescorner.arcanus.client;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.gui.overlay.FirstPersonCastingOverlay;
import dev.cammiescorner.arcanus.client.gui.overlay.ManaBarOverlay;
import dev.cammiescorner.arcanus.client.gui.overlay.StunOverlay;
import dev.cammiescorner.arcanus.client.gui.screens.*;
import dev.cammiescorner.arcanus.client.model.armor.CultRobesModel;
import dev.cammiescorner.arcanus.client.model.armor.WizardRobesModel;
import dev.cammiescorner.arcanus.client.model.block.SpellScrollModel;
import dev.cammiescorner.arcanus.client.model.entity.living.OpossumModel;
import dev.cammiescorner.arcanus.client.model.entity.living.WizardModel;
import dev.cammiescorner.arcanus.client.model.entity.magic.*;
import dev.cammiescorner.arcanus.client.model.feature.HaloModel;
import dev.cammiescorner.arcanus.client.model.feature.SpellPatternModel;
import dev.cammiescorner.arcanus.client.particle.CollapseParticle;
import dev.cammiescorner.arcanus.client.renderer.armor.CultRobesRenderer;
import dev.cammiescorner.arcanus.client.renderer.armor.WizardRobesRenderer;
import dev.cammiescorner.arcanus.client.renderer.block.*;
import dev.cammiescorner.arcanus.client.renderer.entity.living.CultistRenderer;
import dev.cammiescorner.arcanus.client.renderer.entity.living.OpossumRenderer;
import dev.cammiescorner.arcanus.client.renderer.entity.living.WizardRenderer;
import dev.cammiescorner.arcanus.client.renderer.entity.magic.*;
import dev.cammiescorner.arcanus.client.renderer.item.StaffItemRenderer;
import dev.cammiescorner.arcanus.client.renderer.world.WardedBlockRenderer;
import dev.cammiescorner.arcanus.client.util.JarRenderData;
import dev.cammiescorner.arcanus.common.compat.ArcanusCompat;
import dev.cammiescorner.arcanus.common.compat.FirstPersonCompat;
import dev.cammiescorner.arcanus.common.entity.living.Cultist;
import dev.cammiescorner.arcanus.common.item.StaffItem;
import dev.cammiescorner.arcanus.common.registry.*;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.client.event.RegisterCustomArmorRenderersEvent;
import dev.upcraft.sparkweave.api.client.event.RegisterEntityRenderersEvent;
import dev.upcraft.sparkweave.api.client.event.RegisterItemPropertiesEvent;
import dev.upcraft.sparkweave.api.client.event.RegisterLecternItemRendererEvent;
import dev.upcraft.sparkweave.api.color.Color;
import dev.upcraft.sparkweave.api.entrypoint.ClientEntryPoint;
import dev.upcraft.sparkweave.api.platform.ModContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
@AutoService(ClientEntryPoint.class)
public class ArcanusClient implements ClientEntryPoint {
	public static final ResourceLocation WHITE = ResourceLocation.withDefaultNamespace("textures/misc/white.png");
	private static final ResourceLocation RED_CULT_ROBES = Arcanus.id("textures/entity/armor/red_cult_robes.png");
	private static final ResourceLocation GREEN_CULT_ROBES = Arcanus.id("textures/entity/armor/green_cult_robes.png");
	private static final ResourceLocation BLUE_CULT_ROBES = Arcanus.id("textures/entity/armor/blue_cult_robes.png");
	private static final ResourceLocation WHITE_CULT_ROBES = Arcanus.id("textures/entity/armor/white_cult_robes.png");
	private static final ResourceLocation BLACK_CULT_ROBES = Arcanus.id("textures/entity/armor/black_cult_robes.png");
	private static final Map<PlayerSkin.Model, EntityRendererProvider<Cultist>> CULTIST_PROVIDERS = Map.of(
		PlayerSkin.Model.WIDE,
		context -> new CultistRenderer<>(context, false),
		PlayerSkin.Model.SLIM,
		context -> new CultistRenderer<>(context, true)
	);
	public static BooleanSupplier FIRST_PERSON_MODEL_ENABLED = () -> false;
	public static BooleanSupplier FIRST_PERSON_SHOW_HANDS = () -> true;
	public static boolean castingSpeedHasCoolDown;
	private final Minecraft client = Minecraft.getInstance();

	@Override
	public void onInitializeClient(ModContainer mod) {
		ArcanusCompat.FIRST_PERSON.ifEnabled(() -> FirstPersonCompat::init);

		MenuScreens.register(ArcanusMenus.SPELLCRAFT_MENU.get(), SpellcraftScreen::new);
		MenuScreens.register(ArcanusMenus.SCROLL_OF_KNOWLEDGE_MENU.get(), ScrollOfKnowledgeScreen::new);
		MenuScreens.register(ArcanusMenus.SPELL_SCROLL_MENU.get(), SpellScrollScreen::new);
		MenuScreens.register(ArcanusMenus.SPELL_BOOK_MENU.get(), SpellBookScreen::new);
		MenuScreens.register(ArcanusMenus.BOOK_POUCH_MENU.get(), BookPouchScreen::new);
		MenuScreens.register(ArcanusMenus.ARCANE_WORKBENCH_MENU.get(), ArcaneWorkbenchScreen::new);

		EntityModelLayerRegistry.registerModelLayer(WizardRobesModel.MODEL_LAYER, WizardRobesModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(CultRobesModel.MODEL_LAYER, CultRobesModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(WizardModel.MODEL_LAYER, WizardModel::getTexturedModelData);
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

		RegisterEntityRenderersEvent.EVENT.register(event -> {
			event.registerRenderer(ArcanusEntities.WIZARD, WizardRenderer::new);
			event.registerRenderer(ArcanusEntities.OPOSSUM, OpossumRenderer::new);
			event.registerRenderer(ArcanusEntities.NECRO_SKELETON, SkeletonRenderer::new);
			event.registerRenderer(ArcanusEntities.MANA_SHIELD, ManaShieldRenderer::new);
			event.registerRenderer(ArcanusEntities.MISSILE, MissileRenderer::new);
			event.registerRenderer(ArcanusEntities.LOB, LobRenderer::new);
			event.registerRenderer(ArcanusEntities.AOE, AreaOfEffectRenderer::new);
			event.registerRenderer(ArcanusEntities.SMITE, SmiteRenderer::new);
			event.registerRenderer(ArcanusEntities.MAGIC_RUNE, MagicRuneRenderer::new);
			event.registerRenderer(ArcanusEntities.GUIDED_SHOT, GuidedShotRenderer::new);
			event.registerRenderer(ArcanusEntities.BEAM, BeamRenderer::new);
			event.registerRenderer(ArcanusEntities.FOLLOWING_ORB, EntangledOrbRenderer::new);
			event.registerRenderer(ArcanusEntities.PORTAL, PocketDimensionPortalRenderer::new);
			event.registerRenderer(ArcanusEntities.AGGRESSORB, AggressorbRenderer::new);
			event.registerRenderer(ArcanusEntities.TEMPORAL_DILATION_FIELD, TemporalDilationFieldRenderer::new);
		});

		RegisterCustomArmorRenderersEvent.EVENT.register(event -> {
			event.register(WizardRobesRenderer::new, ArcanusItems.WIZARD_HAT, ArcanusItems.WIZARD_ROBES, ArcanusItems.WIZARD_PANTS, ArcanusItems.WIZARD_BOOTS);
			event.register((livingEntity, context, layerParent) -> new CultRobesRenderer(context, RED_CULT_ROBES), ArcanusItems.RED_CULT_HOOD, ArcanusItems.RED_CULT_ROBES, ArcanusItems.RED_CULT_PANTS, ArcanusItems.RED_CULT_BOOTS);
			event.register((livingEntity, context, layerParent) -> new CultRobesRenderer(context, GREEN_CULT_ROBES), ArcanusItems.GREEN_CULT_HOOD, ArcanusItems.GREEN_CULT_ROBES, ArcanusItems.GREEN_CULT_PANTS, ArcanusItems.GREEN_CULT_BOOTS);
			event.register((livingEntity, context, layerParent) -> new CultRobesRenderer(context, BLUE_CULT_ROBES), ArcanusItems.BLUE_CULT_HOOD, ArcanusItems.BLUE_CULT_ROBES, ArcanusItems.BLUE_CULT_PANTS, ArcanusItems.BLUE_CULT_BOOTS);
			event.register((livingEntity, context, layerParent) -> new CultRobesRenderer(context, WHITE_CULT_ROBES), ArcanusItems.WHITE_CULT_HOOD, ArcanusItems.WHITE_CULT_ROBES, ArcanusItems.WHITE_CULT_PANTS, ArcanusItems.WHITE_CULT_BOOTS);
			event.register((livingEntity, context, layerParent) -> new CultRobesRenderer(context, BLACK_CULT_ROBES), ArcanusItems.BLACK_CULT_HOOD, ArcanusItems.BLACK_CULT_ROBES, ArcanusItems.BLACK_CULT_PANTS, ArcanusItems.BLACK_CULT_BOOTS);
		});

		ParticleFactoryRegistry.getInstance().register(ArcanusParticles.COLLAPSE.get(), CollapseParticle.Factory::new);

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), ArcanusBlocks.MAGIC_DOOR.get(), ArcanusBlocks.ARCANE_WORKBENCH.get(), ArcanusBlocks.CHALK.get());
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.translucent(), ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get(), ArcanusBlocks.JAR.get());
		BlockEntityRenderers.register(ArcanusBlockEntities.MAGIC_BLOCK.get(), MagicBlockEntityRenderer.factory(ArcanusHelper::getMagicColor));
		BlockEntityRenderers.register(ArcanusBlockEntities.SPATIAL_RIFT_EXIT.get(), SpatialRiftExitBlockEntityRenderer::new);
		BlockEntityRenderers.register(ArcanusBlockEntities.SPATIAL_RIFT_WALL.get(), MagicBlockEntityRenderer.factory(ArcanusHelper::getPocketDimensionColor));
		BlockEntityRenderers.register(ArcanusBlockEntities.ARCANE_PLINTH.get(), ArcanePlinthBlockEntityRenderer::new);
		BlockEntityRenderers.register(ArcanusBlockEntities.PEDESTAL.get(), PedestalBlockEntityRenderer::new);

		RegisterLecternItemRendererEvent.EVENT.register(event -> {
			event.registerRenderer(LecternSpellScrollRenderer::new, ArcanusItems.SPELL_SCROLL);
		});

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> switch(tintIndex) {
				case 0 -> StaffItem.getPrimaryColorRGB(stack);
				case 1 -> StaffItem.getSecondaryColorRGB(stack);
				default -> 0xffffffff;
			},
			ArcanusItems.WOODEN_STAFF.get(),
			ArcanusItems.CRYSTAL_STAFF.get(),
			ArcanusItems.DIVINATION_STAFF.get(),
			ArcanusItems.CRESCENT_STAFF.get(),
			ArcanusItems.ANCIENT_STAFF.get(),
			ArcanusItems.WAND.get(),
			ArcanusItems.THAUMATURGES_GAUNTLET.get(),
			ArcanusItems.MAGIC_TOME.get(),
			ArcanusItems.MAGE_PISTOL.get()
		);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? DyedItemColor.getOrDefault(stack, 0xff52392a) : -1,
			ArcanusItems.WIZARD_HAT.get(),
			ArcanusItems.WIZARD_ROBES.get(),
			ArcanusItems.WIZARD_PANTS.get(),
			ArcanusItems.WIZARD_BOOTS.get(),
			ArcanusItems.SPELL_BOOK.get()
		);

		ColorProviderRegistry.BLOCK.register((state, tintGetter, pos, tintIndex) -> tintIndex == 1 && tintGetter.getBlockEntityRenderData(pos) instanceof JarRenderData(Color color) ? color.asIntARGB() : 0xffffffff,
			ArcanusBlocks.JAR.get()
		);

		RegisterItemPropertiesEvent.EVENT.register(event -> {
			for(Supplier<Item> itemSupplier : ArcanusItems.HOOD_ITEMS)
				event.register(itemSupplier, Arcanus.id("hood_down"), (stack, level, entity, seed) -> stack.getOrDefault(ArcanusDataComponents.HOOD_DOWN.get(), false) ? 1f : 0f);
		});

		ArcanusItems.ITEMS.stream().forEach(holder -> {
			if(holder.get() instanceof StaffItem item) {
				ResourceLocation id = holder.getId().withPrefix("item/");
				StaffItemRenderer staffItemRenderer = new StaffItemRenderer(id);
				ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(staffItemRenderer);
				BuiltinItemRendererRegistry.INSTANCE.register(item, staffItemRenderer);
				ModelLoadingPlugin.register(ctx -> ctx.addModels(id.withSuffix("_gui"), id.withSuffix("_in_hand")));
			}
		});

		WorldRenderEvents.AFTER_ENTITIES.register(context -> {
			if(!context.camera().isDetached() && !FIRST_PERSON_MODEL_ENABLED.getAsBoolean())
				renderFirstPersonBolt(context);
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
				ManaBarOverlay.render(gui, tickDelta, client.player);
			}
		});
	}

	public static Map<PlayerSkin.Model, EntityRenderer<? extends Cultist>> createCultistRenderers(EntityRendererProvider.Context context) {
		ImmutableMap.Builder<PlayerSkin.Model, EntityRenderer<? extends Cultist>> builder = ImmutableMap.builder();
		CULTIST_PROVIDERS.forEach((model, entityRendererProvider) -> {
			try {
				builder.put(model, entityRendererProvider.create(context));
			}
			catch (Exception var5) {
				throw new IllegalArgumentException("Failed to create cultist model for " + model, var5);
			}
		});
		return builder.build();
	}

	public static void renderBolts(LivingEntity entity, Vec3 startPos, PoseStack matrices, MultiBufferSource vertices) {
		if(ArcanusComponents.shouldRenderBolt(entity)) {
			VertexConsumer vertex = vertices.getBuffer(getMagicCircles(WHITE));
			RandomSource random = RandomSource.create((entity.tickCount + entity.getId()) / 2);
			Vec3 endPos = ArcanusComponents.getBoltPos(entity);

			Color color = ArcanusHelper.getMagicColor(entity);
			int steps = (int) (startPos.distanceTo(endPos) * 5);

			renderBolt(matrices, vertex, random, startPos, endPos, steps, 0, true, color.redF(), color.greenF(), color.blueF(), OverlayTexture.NO_OVERLAY, LightTexture.FULL_BRIGHT);
		}
	}

	private static void renderBolt(PoseStack matrices, VertexConsumer vertex, RandomSource random, Vec3 startPos, Vec3 endPos, int steps, int currentStep, boolean recurse, float r, float g, float b, int overlay, int light) {
		Vec3 direction = endPos.subtract(startPos);
		Vec3 lastPos = startPos;
		Matrix4f modelMatrix = matrices.last().pose();

		for(int i = currentStep; i < steps; i++) {
			Vec3 randomOffset = new Vec3(random.nextGaussian(), random.nextIntBetweenInclusive(-1 / (steps * 2), 1 / (steps * 2)), random.nextGaussian());
			Vec3 nextPos = startPos.add(direction.scale((i + 1) / (float) steps)).add(randomOffset.scale(1 / 12f));

			for(int j = 0; j < 4; j++) {
				Vec3 vert1 = switch(j) {
					case 0 -> lastPos.add(0.025, 0.025, 0);
					case 1 -> lastPos.add(-0.025, 0.025, 0);
					case 2 -> lastPos.add(-0.025, -0.025, 0);
					case 3 -> lastPos.add(0.025, -0.025, 0);
					default -> lastPos;
				};
				Vec3 vert2 = switch(j) {
					case 0 -> lastPos.add(-0.025, 0.025, 0);
					case 1 -> lastPos.add(-0.025, -0.025, 0);
					case 2 -> lastPos.add(0.025, -0.025, 0);
					case 3 -> lastPos.add(0.025, 0.025, 0);
					default -> lastPos;
				};
				Vec3 vert3 = switch(j) {
					case 0 -> nextPos.add(0.025, 0.025, 0);
					case 1 -> nextPos.add(-0.025, 0.025, 0);
					case 2 -> nextPos.add(-0.025, -0.025, 0);
					case 3 -> nextPos.add(0.025, -0.025, 0);
					default -> nextPos;
				};
				Vec3 vert4 = switch(j) {
					case 0 -> nextPos.add(-0.025, 0.025, 0);
					case 1 -> nextPos.add(-0.025, -0.025, 0);
					case 2 -> nextPos.add(0.025, -0.025, 0);
					case 3 -> nextPos.add(0.025, 0.025, 0);
					default -> nextPos;
				};
				Vec3 normal = vert2.subtract(vert1).cross(vert3.subtract(vert1));

				vertex.addVertex(modelMatrix, (float) vert2.x(), (float) vert2.y(), (float) vert2.z()).setColor(r, g, b, 0.6f).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(matrices.last(), (float) normal.x(), (float) normal.y(), (float) normal.z());
				vertex.addVertex(modelMatrix, (float) vert4.x(), (float) vert4.y(), (float) vert4.z()).setColor(r, g, b, 0.6f).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(matrices.last(), (float) normal.x(), (float) normal.y(), (float) normal.z());
				vertex.addVertex(modelMatrix, (float) vert3.x(), (float) vert3.y(), (float) vert3.z()).setColor(r, g, b, 0.6f).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(matrices.last(), (float) normal.x(), (float) normal.y(), (float) normal.z());
				vertex.addVertex(modelMatrix, (float) vert1.x(), (float) vert1.y(), (float) vert1.z()).setColor(r, g, b, 0.6f).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(matrices.last(), (float) normal.x(), (float) normal.y(), (float) normal.z());
			}

			while(recurse && random.nextFloat() < 0.2f) {
				Vec3 randomOffset1 = new Vec3(random.nextGaussian(), random.nextGaussian(), random.nextGaussian());
				renderBolt(matrices, vertex, random, lastPos, endPos.add(randomOffset1.scale(Math.min(random.nextFloat(), 0.6f))), steps, i + 1, false, r, g, b, overlay, light);
			}

			lastPos = nextPos;
		}
	}

	private static void renderFirstPersonBolt(WorldRenderContext context) {
		LocalPlayer player = context.gameRenderer().getMinecraft().player;

		if(player != null) {
			PoseStack matrices = context.matrixStack();
			Vec3 camPos = context.camera().getPosition();
			float tickDelta = context.tickCounter().getGameTimeDeltaTicks();
			Vec3 startPos = player.getPosition(tickDelta).add(0, player.getEyeHeight(player.getPose()), 0);

			matrices.pushPose();
			matrices.translate(-camPos.x(), -camPos.y(), -camPos.z());
			renderBolts(player, startPos.add(0, -0.1, 0), matrices, context.consumers());
			matrices.popPose();
		}
	}

	public static RenderType getMagicCircles(ResourceLocation texture) {
		return RenderType.create(Arcanus.id("magic_circle").toString(), DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(RenderType.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER).setTextureState(new RenderStateShard.TextureStateShard(texture, false, false)).setOverlayState(RenderStateShard.OVERLAY).setTransparencyState(RenderType.ADDITIVE_TRANSPARENCY).setWriteMaskState(RenderType.COLOR_DEPTH_WRITE).setCullState(RenderStateShard.NO_CULL).createCompositeState(false));
	}

	public static RenderType getMagicPortal(ResourceLocation texture) {
		return RenderType.create(Arcanus.id("magic_portal").toString(), DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(RenderType.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER).setTextureState(new RenderStateShard.TextureStateShard(texture, false, false)).setOverlayState(RenderStateShard.OVERLAY).setTransparencyState(RenderType.ADDITIVE_TRANSPARENCY).setWriteMaskState(RenderType.COLOR_DEPTH_WRITE).setCullState(RenderStateShard.NO_CULL).setDepthTestState(RenderType.NO_DEPTH_TEST).createCompositeState(false));
	}

	public static RenderType getMagicCirclesTri(ResourceLocation texture) {
		return RenderType.create(Arcanus.id("magic_circle_tri").toString(), DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.TRIANGLES, 256, false, true, RenderType.CompositeState.builder().setShaderState(RenderType.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER).setTextureState(new RenderStateShard.TextureStateShard(texture, false, false)).setOverlayState(RenderStateShard.OVERLAY).setTransparencyState(RenderType.ADDITIVE_TRANSPARENCY).setWriteMaskState(RenderType.COLOR_DEPTH_WRITE).setCullState(RenderStateShard.NO_CULL).createCompositeState(false));
	}

	public static void renderQuad(Matrix4f matrix4f, VertexConsumer vertices, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, Color color, int light, int overlay, PoseStack.Pose normal, Direction direction) {
		vertices.addVertex(matrix4f, x1, y1, z1).setColor(color.red(), color.green(), color.blue(), color.alpha()).setUv(0, 1).setOverlay(overlay).setLight(light).setNormal(normal, direction.getNormal().getX(), direction.getNormal().getY(), direction.getNormal().getZ());
		vertices.addVertex(matrix4f, x2, y1, z2).setColor(color.red(), color.green(), color.blue(), color.alpha()).setUv(1, 1).setOverlay(overlay).setLight(light).setNormal(normal, direction.getNormal().getX(), direction.getNormal().getY(), direction.getNormal().getZ());
		vertices.addVertex(matrix4f, x2, y2, z3).setColor(color.red(), color.green(), color.blue(), color.alpha()).setUv(1, 0).setOverlay(overlay).setLight(light).setNormal(normal, direction.getNormal().getX(), direction.getNormal().getY(), direction.getNormal().getZ());
		vertices.addVertex(matrix4f, x1, y2, z4).setColor(color.red(), color.green(), color.blue(), color.alpha()).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(normal, direction.getNormal().getX(), direction.getNormal().getY(), direction.getNormal().getZ());
	}
}
