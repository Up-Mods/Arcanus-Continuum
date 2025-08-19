package dev.cammiescorner.arcanus.client;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.client.gui.screens.*;
import dev.cammiescorner.arcanus.client.renderer.armor.ArcanistRobesRenderer;
import dev.cammiescorner.arcanus.client.renderer.armor.ArtificerArmorRenderer;
import dev.cammiescorner.arcanus.client.renderer.armor.CultRobesRenderer;
import dev.cammiescorner.arcanus.client.renderer.block.*;
import dev.cammiescorner.arcanus.client.renderer.entity.living.ArcanistRenderer;
import dev.cammiescorner.arcanus.client.renderer.entity.living.CultistRenderer;
import dev.cammiescorner.arcanus.client.renderer.entity.living.OpossumRenderer;
import dev.cammiescorner.arcanus.client.renderer.entity.magic.*;
import dev.cammiescorner.arcanus.common.compat.ArcanusCompat;
import dev.cammiescorner.arcanus.common.compat.FirstPersonCompat;
import dev.cammiescorner.arcanus.common.entity.living.Cultist;
import dev.cammiescorner.arcanus.common.registry.*;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.client.event.RegisterCustomArmorRenderersEvent;
import dev.upcraft.sparkweave.api.client.event.RegisterEntityRenderersEvent;
import dev.upcraft.sparkweave.api.client.event.RegisterItemPropertiesEvent;
import dev.upcraft.sparkweave.api.client.event.RegisterLecternItemRendererEvent;
import dev.upcraft.sparkweave.api.color.Color;
import dev.upcraft.sparkweave.api.entrypoint.ClientEntryPoint;
import dev.upcraft.sparkweave.api.platform.ModContainer;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
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
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@AutoService(ClientEntryPoint.class)
public class ArcanusClient implements ClientEntryPoint {
	public static final ResourceLocation WHITE = ResourceLocation.withDefaultNamespace("textures/misc/white.png");
	public static final Map<PlayerSkin.Model, EntityRendererProvider<Cultist>> CULTIST_PROVIDERS = Map.of(
		PlayerSkin.Model.WIDE,
		context -> new CultistRenderer<>(context, false),
		PlayerSkin.Model.SLIM,
		context -> new CultistRenderer<>(context, true)
	);
	public static BooleanSupplier FIRST_PERSON_MODEL_ENABLED = () -> false;
	public static BooleanSupplier FIRST_PERSON_SHOW_HANDS = () -> true;
	public static boolean castingSpeedHasCoolDown;

	@Override
	public void onInitializeClient(ModContainer mod) {
		ArcanusCompat.FIRST_PERSON.ifEnabled(() -> FirstPersonCompat::init);

		MenuScreens.register(ArcanusMenus.SPELLCRAFT_MENU.get(), SpellcraftScreen::new);
		MenuScreens.register(ArcanusMenus.SCROLL_OF_KNOWLEDGE_MENU.get(), ScrollOfKnowledgeScreen::new);
		MenuScreens.register(ArcanusMenus.SPELL_SCROLL_MENU.get(), SpellScrollScreen::new);
		MenuScreens.register(ArcanusMenus.SPELL_BOOK_MENU.get(), SpellBookScreen::new);
		MenuScreens.register(ArcanusMenus.BOOK_POUCH_MENU.get(), BookPouchScreen::new);
		MenuScreens.register(ArcanusMenus.ARCANE_WORKBENCH_MENU.get(), ArcaneWorkbenchScreen::new);

		RegisterEntityRenderersEvent.EVENT.register(event -> {
			event.registerRenderer(ArcanusEntities.ARCANIST, ArcanistRenderer::new);
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
			event.registerRenderer(ArcanusEntities.MAGIC_ORB, EntangledOrbRenderer::new);
			event.registerRenderer(ArcanusEntities.PORTAL, PocketDimensionPortalRenderer::new);
			event.registerRenderer(ArcanusEntities.STOCKPILE_ORB, AggressorbRenderer::new);
			event.registerRenderer(ArcanusEntities.TEMPORAL_DILATION_FIELD, TemporalDilationFieldRenderer::new);
		});

		RegisterCustomArmorRenderersEvent.EVENT.register(event -> {
			event.register(ArcanistRobesRenderer::new, ArcanusItems.ARCANIST_HAT, ArcanusItems.ARCANIST_ROBES, ArcanusItems.ARCANIST_PANTS, ArcanusItems.ARCANIST_BOOTS);
			event.register(ArtificerArmorRenderer::new, ArcanusItems.ARTIFICER_HELMET, ArcanusItems.ARTIFICER_CHESTPLATE, ArcanusItems.ARTIFICER_LEGGINGS, ArcanusItems.ARTIFICER_BOOTS);
			event.register(CultRobesRenderer::new, ArcanusItems.CULTIST_HOOD, ArcanusItems.CULTIST_ROBES, ArcanusItems.CULTIST_PANTS, ArcanusItems.CULTIST_BOOTS);
		});

		BlockEntityRenderers.register(ArcanusBlockEntities.MAGIC_BLOCK.get(), MagicBlockEntityRenderer.factory(ArcanusHelper::getMagicColor));
		BlockEntityRenderers.register(ArcanusBlockEntities.SPATIAL_RIFT_EXIT.get(), SpatialRiftExitBlockEntityRenderer::new);
		BlockEntityRenderers.register(ArcanusBlockEntities.SPATIAL_RIFT_WALL.get(), MagicBlockEntityRenderer.factory(ArcanusHelper::getPocketDimensionColor));
		BlockEntityRenderers.register(ArcanusBlockEntities.ARCANE_PLINTH.get(), ArcanePlinthBlockEntityRenderer::new);
		BlockEntityRenderers.register(ArcanusBlockEntities.PEDESTAL.get(), PedestalBlockEntityRenderer::new);

		RegisterLecternItemRendererEvent.EVENT.register(event -> {
			event.registerRenderer(LecternSpellScrollRenderer::new, ArcanusItems.SPELL_SCROLL);
		});

		RegisterItemPropertiesEvent.EVENT.register(event -> {
			for(Supplier<Item> itemSupplier : ArcanusItems.HOOD_ITEMS)
				event.register(itemSupplier, Arcanus.id("hood_down"), (stack, level, entity, seed) -> stack.getOrDefault(ArcanusDataComponents.HOOD_DOWN.get(), false) ? 1f : 0f);
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

	public static void renderBolts(LivingEntity entity, Vec3 startPos, PoseStack poseStack, MultiBufferSource vertices) {
		if(ArcanusComponents.shouldRenderBolt(entity)) {
			VertexConsumer vertex = vertices.getBuffer(getMagicCircles(WHITE));
			RandomSource random = RandomSource.create((entity.tickCount + entity.getId()) / 2);
			Vec3 endPos = ArcanusComponents.getBoltPos(entity);

			Color color = ArcanusHelper.getMagicColor(entity);
			int steps = (int) (startPos.distanceTo(endPos) * 5);

			renderBolt(poseStack, vertex, random, startPos, endPos, steps, 0, true, color.redF(), color.greenF(), color.blueF(), OverlayTexture.NO_OVERLAY, LightTexture.FULL_BRIGHT);
		}
	}

	public static void renderBolt(PoseStack poseStack, VertexConsumer vertex, RandomSource random, Vec3 startPos, Vec3 endPos, int steps, int currentStep, boolean recurse, float r, float g, float b, int overlay, int light) {
		Vec3 direction = endPos.subtract(startPos);
		Vec3 lastPos = startPos;
		Matrix4f modelMatrix = poseStack.last().pose();

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

				vertex.addVertex(modelMatrix, (float) vert2.x(), (float) vert2.y(), (float) vert2.z()).setColor(r, g, b, 0.6f).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(poseStack.last(), (float) normal.x(), (float) normal.y(), (float) normal.z());
				vertex.addVertex(modelMatrix, (float) vert4.x(), (float) vert4.y(), (float) vert4.z()).setColor(r, g, b, 0.6f).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(poseStack.last(), (float) normal.x(), (float) normal.y(), (float) normal.z());
				vertex.addVertex(modelMatrix, (float) vert3.x(), (float) vert3.y(), (float) vert3.z()).setColor(r, g, b, 0.6f).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(poseStack.last(), (float) normal.x(), (float) normal.y(), (float) normal.z());
				vertex.addVertex(modelMatrix, (float) vert1.x(), (float) vert1.y(), (float) vert1.z()).setColor(r, g, b, 0.6f).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(poseStack.last(), (float) normal.x(), (float) normal.y(), (float) normal.z());
			}

			while(recurse && random.nextFloat() < 0.2f) {
				Vec3 randomOffset1 = new Vec3(random.nextGaussian(), random.nextGaussian(), random.nextGaussian());
				renderBolt(poseStack, vertex, random, lastPos, endPos.add(randomOffset1.scale(Math.min(random.nextFloat(), 0.6f))), steps, i + 1, false, r, g, b, overlay, light);
			}

			lastPos = nextPos;
		}
	}

	public static void renderFirstPersonBolt(@Nullable LocalPlayer player, PoseStack poseStack, Camera camera, DeltaTracker deltaTracker, MultiBufferSource bufferSource) {
		if(player != null) {
			Vec3 camPos = camera.getPosition();
			float tickDelta = deltaTracker.getGameTimeDeltaTicks();
			Vec3 startPos = player.getPosition(tickDelta).add(0, player.getEyeHeight(player.getPose()), 0);

			poseStack.pushPose();
			poseStack.translate(-camPos.x(), -camPos.y(), -camPos.z());
			renderBolts(player, startPos.add(0, -0.1, 0), poseStack, bufferSource);
			poseStack.popPose();
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
