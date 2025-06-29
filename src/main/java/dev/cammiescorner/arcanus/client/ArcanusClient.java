package dev.cammiescorner.arcanus.client;

import com.google.auto.service.AutoService;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spells.ManaColor;
import dev.cammiescorner.arcanus.api.spells.Pattern;
import dev.cammiescorner.arcanus.client.gui.screens.ArcaneWorkbenchScreen;
import dev.cammiescorner.arcanus.client.gui.screens.SpellBookScreen;
import dev.cammiescorner.arcanus.client.gui.screens.SpellScrollScreen;
import dev.cammiescorner.arcanus.client.gui.screens.SpellcraftScreen;
import dev.cammiescorner.arcanus.client.models.armor.BattleMageArmorModel;
import dev.cammiescorner.arcanus.client.models.armor.WizardArmorModel;
import dev.cammiescorner.arcanus.client.models.block.SpellScrollModel;
import dev.cammiescorner.arcanus.client.models.entity.living.OpossumModel;
import dev.cammiescorner.arcanus.client.models.entity.living.WizardModel;
import dev.cammiescorner.arcanus.client.models.entity.magic.*;
import dev.cammiescorner.arcanus.client.models.feature.HaloModel;
import dev.cammiescorner.arcanus.client.models.feature.SpellPatternModel;
import dev.cammiescorner.arcanus.client.particles.CollapseParticle;
import dev.cammiescorner.arcanus.client.renderer.armor.BattleMageArmorRenderer;
import dev.cammiescorner.arcanus.client.renderer.armor.WizardRobesRenderer;
import dev.cammiescorner.arcanus.client.renderer.block.LecternSpellScrollRenderer;
import dev.cammiescorner.arcanus.client.renderer.block.MagicBlockEntityRenderer;
import dev.cammiescorner.arcanus.client.renderer.block.PedestalBlockEntityRenderer;
import dev.cammiescorner.arcanus.client.renderer.block.SpatialRiftExitBlockEntityRenderer;
import dev.cammiescorner.arcanus.client.renderer.entity.living.OpossumRenderer;
import dev.cammiescorner.arcanus.client.renderer.entity.living.WizardRenderer;
import dev.cammiescorner.arcanus.client.renderer.entity.magic.*;
import dev.cammiescorner.arcanus.client.renderer.item.StaffItemRenderer;
import dev.cammiescorner.arcanus.common.compat.ArcanusCompat;
import dev.cammiescorner.arcanus.common.compat.FirstPersonCompat;
import dev.cammiescorner.arcanus.common.data.ArcanusItemTags;
import dev.cammiescorner.arcanus.common.items.BattleMageArmorItem;
import dev.cammiescorner.arcanus.common.items.StaffItem;
import dev.cammiescorner.arcanus.common.registry.*;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import dev.upcraft.sparkweave.api.client.event.RegisterCustomArmorRenderersEvent;
import dev.upcraft.sparkweave.api.client.event.RegisterEntityRenderersEvent;
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
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BooleanSupplier;

@Environment(EnvType.CLIENT)
@AutoService(ClientEntryPoint.class)
public class ArcanusClient implements ClientEntryPoint {
	private static final ResourceLocation HUD_ELEMENTS2 = Arcanus.id("textures/gui/hud/mana_bars.png");
	private static final ResourceLocation STUN_OVERLAY = Arcanus.id("textures/gui/hud/stunned_vignette.png");
	private static final ResourceLocation MAGIC_CIRCLES = Arcanus.id("textures/entity/feature/magic_circles.png");
	public static final ResourceLocation WHITE = ResourceLocation.withDefaultNamespace("textures/misc/white.png");
	public static final RenderType LAYER = ArcanusClient.getMagicCircles(Arcanus.id("textures/block/warded_block.png"));
	public static BooleanSupplier FIRST_PERSON_MODEL_ENABLED = () -> false;
	public static BooleanSupplier FIRST_PERSON_SHOW_HANDS = () -> true;
	public static boolean castingSpeedHasCoolDown;
	private final Minecraft client = Minecraft.getInstance();
	private static int hudTimer;
	private static int hitTimer;

	@Override
	public void onInitializeClient(ModContainer mod) {
		ArcanusCompat.FIRST_PERSON.ifEnabled(() -> FirstPersonCompat::init);

		MenuScreens.register(ArcanusMenus.SPELLCRAFT_MENU.get(), SpellcraftScreen::new);
		MenuScreens.register(ArcanusMenus.SPELL_SCROLL_MENU.get(), SpellScrollScreen::new);
		MenuScreens.register(ArcanusMenus.SPELL_BOOK_MENU.get(), SpellBookScreen::new);
		MenuScreens.register(ArcanusMenus.ARCANE_WORKBENCH_MENU.get(), ArcaneWorkbenchScreen::new);

		EntityModelLayerRegistry.registerModelLayer(WizardArmorModel.MODEL_LAYER, WizardArmorModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(BattleMageArmorModel.MODEL_LAYER, BattleMageArmorModel::getTexturedModelData);
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
			event.registerRenderer(ArcanusEntities.ENTANGLED_ORB, EntangledOrbRenderer::new);
			event.registerRenderer(ArcanusEntities.PORTAL, PocketDimensionPortalRenderer::new);
			event.registerRenderer(ArcanusEntities.AGGRESSORB, AggressorbRenderer::new);
			event.registerRenderer(ArcanusEntities.TEMPORAL_DILATION_FIELD, TemporalDilationFieldRenderer::new);
		});

		RegisterCustomArmorRenderersEvent.EVENT.register(event -> {
			event.register(WizardRobesRenderer::new, ArcanusItems.WIZARD_HAT, ArcanusItems.WIZARD_ROBES, ArcanusItems.WIZARD_PANTS, ArcanusItems.WIZARD_BOOTS);
		});

		ArmorRenderer.register(new BattleMageArmorRenderer(), ArcanusItems.BATTLE_MAGE_HELMET.get(), ArcanusItems.BATTLE_MAGE_CHESTPLATE.get(), ArcanusItems.BATTLE_MAGE_LEGGINGS.get(), ArcanusItems.BATTLE_MAGE_BOOTS.get());

		ParticleFactoryRegistry.getInstance().register(ArcanusParticles.COLLAPSE.get(), CollapseParticle.Factory::new);

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), ArcanusBlocks.MAGIC_DOOR.get(), ArcanusBlocks.ARCANE_WORKBENCH.get(), ArcanusBlocks.ARCANE_WORKBENCH.get());
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.translucent(), ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get());
		BlockEntityRenderers.register(ArcanusBlockEntities.MAGIC_BLOCK.get(), MagicBlockEntityRenderer.factory(ArcanusHelper::getMagicColor));
		BlockEntityRenderers.register(ArcanusBlockEntities.SPATIAL_RIFT_EXIT.get(), SpatialRiftExitBlockEntityRenderer::new);
		BlockEntityRenderers.register(ArcanusBlockEntities.SPATIAL_RIFT_WALL.get(), MagicBlockEntityRenderer.factory(ArcanusHelper::getPocketDimensionColor));
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

		ItemProperties.register(ArcanusItems.BATTLE_MAGE_HELMET.get(), Arcanus.id("oxidation"), (stack, world, entity, seed) -> BattleMageArmorItem.getOxidation(stack).ordinal() / 10f);
		ItemProperties.register(ArcanusItems.BATTLE_MAGE_CHESTPLATE.get(), Arcanus.id("oxidation"), (stack, world, entity, seed) -> BattleMageArmorItem.getOxidation(stack).ordinal() / 10f);
		ItemProperties.register(ArcanusItems.BATTLE_MAGE_LEGGINGS.get(), Arcanus.id("oxidation"), (stack, world, entity, seed) -> BattleMageArmorItem.getOxidation(stack).ordinal() / 10f);
		ItemProperties.register(ArcanusItems.BATTLE_MAGE_BOOTS.get(), Arcanus.id("oxidation"), (stack, world, entity, seed) -> BattleMageArmorItem.getOxidation(stack).ordinal() / 10f);

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
			MultiPlayerGameMode interactionManager = client.gameMode;
			LocalPlayer player = client.player;
			ClientLevel world = context.world();
			PoseStack matrices = context.matrixStack();
			MultiBufferSource vertices = context.consumers();
			Vec3 cameraPos = context.camera().getPosition();

			if(player != null && vertices != null && interactionManager != null) {
				if(client.hitResult instanceof BlockHitResult hitResult && ArcanusComponents.isBlockWarded(world, hitResult.getBlockPos()) && player.swinging) {
					if(client.options.keyAttack.isDown() && player.attackAnim == 0)
						hitTimer = 20;

					if(!ArcanusComponents.isOwnerOfBlock(player, hitResult.getBlockPos()) || hitTimer > 0) {
						BlockPos blockPos = hitResult.getBlockPos();
						float alpha = Mth.clamp(hitTimer / 20f, 0f, 1f);

						renderWardedBlock(matrices, vertices, world, cameraPos, blockPos, alpha);
						player.displayClientMessage(Component.translatable("text.arcanus.block_is_warded").withStyle(ChatFormatting.RED), true);
					}

					if(hitTimer > 0)
						hitTimer -= 1;
				}

				if(player.getMainHandItem().is(ArcanusItemTags.STAVES) || player.getOffhandItem().is(ArcanusItemTags.STAVES)) {
					AtomicReferenceArray<LevelChunk> chunks = context.world().getChunkSource().storage.chunks;
					float alpha = Mth.sin(world.getGameTime() * 0.06f) * 0.4f + 0.6f;

					for(int i = 0; i < chunks.length(); i++) {
						ChunkAccess chunk = chunks.get(i);

						if(chunk != null) {
							ArcanusComponents.getWardedBlocks(chunk).forEach((blockPos, uuid) -> {
								if(blockPos.distSqr(context.camera().getBlockPosition()) < 256)
									renderWardedBlock(matrices, vertices, world, cameraPos, blockPos, alpha);
							});
						}
					}
				}
			}

			return true;
		});

		HudRenderCallback.EVENT.register((gui, tickDelta) -> {
			PoseStack matrices = gui.pose();
			Player player = client.player;

			if(player != null && !player.isSpectator() && !client.options.hideGui) {
				int stunTimer = ArcanusComponents.getStunTimer(player);

				if(stunTimer > 0) {
					if(stunTimer > 5)
						renderOverlay(STUN_OVERLAY, Math.min(1f, 0.5f + (stunTimer % 5f) / 10f));
					else
						renderOverlay(STUN_OVERLAY, Math.min(1f, stunTimer / 5f));
				}

				if(!client.gameRenderer.getMainCamera().isDetached() && !FIRST_PERSON_MODEL_ENABLED.getAsBoolean()) {
					List<Pattern> list = ArcanusComponents.getPattern(player);

					if(!list.isEmpty()) {
						MultiBufferSource.BufferSource vertices = client.renderBuffers().bufferSource();
						RenderType renderLayer = getMagicCircles(MAGIC_CIRCLES);
						VertexConsumer vertex = vertices.getBuffer(renderLayer);
						Color color = ArcanusHelper.getMagicColor(player);
						float x = client.getWindow().getGuiScaledWidth() / 2f;
						float y = client.getWindow().getGuiScaledHeight() / 2f;
						float scale = 3f;

						matrices.pushPose();
						matrices.translate(x, y, 0);

						for(int i = 0; i < list.size(); i++) {
							Pattern pattern = list.get(i);
							matrices.pushPose();

							if(i == 1)
								matrices.mulPose(Axis.ZP.rotationDegrees((player.tickCount + player.getId() + tickDelta.getGameTimeDeltaTicks()) * (5 + (2.5f * i))));
							else
								matrices.mulPose(Axis.ZN.rotationDegrees((player.tickCount + player.getId() + tickDelta.getGameTimeDeltaTicks()) * (5 + (2.5f * i))));

							matrices.scale(scale, scale, 0);
							matrices.translate(-8.5, -8.5, 0);
							drawTexture(vertex, matrices, color, 0, 0, i * 34, pattern == Pattern.LEFT ? 0 : 24, 17, 17, 128, 48);
							matrices.popPose();
						}

						matrices.popPose();
						vertices.endLastBatch();
					}
				}

				if(player.getMainHandItem().getItem() instanceof StaffItem)
					hudTimer = Math.min(hudTimer + 1, 40);
				else
					hudTimer = Math.max(hudTimer - 1, 0);

				if(hudTimer > 0) {
					PoseStack poseStack = gui.pose();
					int scaledHeight = client.getWindow().getGuiScaledHeight();
					int scaledWidth = client.getWindow().getGuiScaledWidth();
					float alpha = hudTimer > 20 ? 1f : hudTimer / 20f;

					RenderSystem.enableBlend();

					poseStack.pushPose();
					int x = ArcanusConfig.rightSideManaBars.mirror() ? scaledWidth - 29 : 0;
					int y = ArcanusConfig.manaBarsOnTop ? 11 : scaledHeight - 18;
					float startingAngle;

					if(ArcanusConfig.manaBarsOnTop) {
						if(ArcanusConfig.rightSideManaBars.mirror())
							startingAngle = 189;
						else
							startingAngle = -9f;
					}
					else {
						if(ArcanusConfig.rightSideManaBars.mirror())
							startingAngle = -81f;
						else
							startingAngle = -99f;
					}

					float angleOffset = ArcanusConfig.rightSideManaBars.mirror() ? -27f : 27f;
					poseStack.translate(x, y, 0);
					poseStack.scale(0.225f, 0.225f, 1f);

					// render mana bars
					for(int i = 0; i < ManaColor.values().length; i++) {
						ManaColor manaColor = ManaColor.values()[i];
						Color color = manaColor.getColor();

						RenderSystem.setShaderColor(color.redF(), color.greenF(), color.blueF(), alpha);

						poseStack.pushPose();
						x = ArcanusConfig.rightSideManaBars.mirror() ? 68 : 60;
						y = ArcanusConfig.manaBarsOnTop ? 12 : 20;
						poseStack.translate(x, y, 0);
						poseStack.mulPose(Axis.ZP.rotationDegrees(startingAngle + angleOffset * i));
						poseStack.translate(-8, -8, 0);

						double maxMana = ArcanusComponents.getMaxMana(player, manaColor);
						double mana = ArcanusComponents.getMana(player, manaColor);
						double ratio = Math.min(1f, maxMana <= 0f ? 0f : (mana / maxMana));
						double halfNHalf = ArcanusConfig.scaleManaBarsWithMaxMana ? (maxMana - 12) / 2f : (35 - 6);
						int bottomMana = (int) (halfNHalf * Math.clamp(ratio / 0.44f, 0f, 1f));
						int switchMana = (int) (12 * (ratio <= 0.56f ? Math.clamp((ratio - 0.44f) / 0.12f, 0f, 1f) : 1f));
						int topMana = (int) (halfNHalf * Math.clamp((ratio - 0.56f) / 0.44f, 0f, 1f));

						gui.blit(HUD_ELEMENTS2, 85, 0, 0, 200, bottomMana, 16);
						gui.blit(HUD_ELEMENTS2, (int) (85 + halfNHalf), 0, 128, 32, switchMana, 16);
						gui.blit(HUD_ELEMENTS2, (int) (85 + halfNHalf + 12), 0, (int) (256 - halfNHalf), 216, topMana, 16);

						poseStack.translate(-8, -8, 0);
						RenderSystem.setShaderColor(1f, 1f, 1f, alpha);

						halfNHalf = 14 + halfNHalf;
						gui.blit(HUD_ELEMENTS2, 80, 0, 0, 128, (int) halfNHalf, 32);
						gui.blit(HUD_ELEMENTS2, (int) (80 + halfNHalf), 0, 128, 0, 10, 32);
						gui.blit(HUD_ELEMENTS2, (int) (80 + halfNHalf + 10), 0, (int) (256 - halfNHalf), 160, (int) halfNHalf, 32);

						poseStack.popPose();
					}

					// render frame
					gui.blit(HUD_ELEMENTS2, 0, -48, 0, 0, 128, 128);

					poseStack.popPose();

					x = ArcanusConfig.rightSideManaBars.mirror() ? scaledWidth - 21 : 8;
					y = ArcanusConfig.manaBarsOnTop ? 8 : scaledHeight - 21;

					poseStack.pushPose();
					poseStack.translate(x, y, 0);
					poseStack.scale(0.8f, 0.8f, 1f);

					gui.renderItem(Arcanus.getActiveSpellBook(player), 0, 0);

					poseStack.popPose();

					RenderSystem.disableBlend();
					RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
				}
			}
		});
	}

	public static void renderBolts(LivingEntity entity, Vec3 startPos, PoseStack matrices, MultiBufferSource vertices) {
		if(ArcanusComponents.shouldRenderBolt(entity)) {
			VertexConsumer vertex = vertices.getBuffer(getMagicCircles(WHITE));
			RandomSource random = RandomSource.create((entity.tickCount + entity.getId()) / 2);
			Vec3 endPos = ArcanusComponents.getBoltPos(entity);

			var color = ArcanusHelper.getMagicColor(entity);

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

	private static void renderWardedBlock(PoseStack poseStack, MultiBufferSource buffer, Level level, Vec3 cameraPos, BlockPos blockPos, float alpha) {
		VertexConsumer consumer = buffer.getBuffer(LAYER);
		Vec3 pos = Vec3.atCenterOf(blockPos);

		poseStack.pushPose();
		poseStack.translate(pos.x() - cameraPos.x(), pos.y() - cameraPos.y(), pos.z() - cameraPos.z());
		poseStack.scale(1.001f, 1.001f, 1.001f);
		poseStack.translate(-0.5, -0.5, -0.5);

		PoseStack.Pose pose = poseStack.last();
		Matrix4f matrix4f = pose.pose();
		Color color = ArcanusHelper.getMagicColor(ArcanusComponents.getWardedBlocks(level.getChunk(blockPos)).get(blockPos));
		int light = level.getMaxLocalRawBrightness(blockPos);
		int overlay = OverlayTexture.NO_OVERLAY;
		float r = Mth.clamp(color.redF() * alpha, 0f, 1f);
		float g = Mth.clamp(color.greenF() * alpha, 0f, 1f);
		float b = Mth.clamp(color.blueF() * alpha, 0f, 1f);

		color = Color.fromFloatsRGB(r, g, b);

		for(Direction direction : Direction.values()) {
			BlockPos posToSide = blockPos.relative(direction);
			BlockState stateToSide = level.getBlockState(posToSide);

			if(stateToSide.isFaceSturdy(level, posToSide, direction.getOpposite(), SupportType.FULL) || ArcanusComponents.isBlockWarded(level, posToSide))
				continue;

			switch(direction) {
				case SOUTH ->
					renderSide(matrix4f, consumer, 0f, 1f, 0f, 1f, 1f, 1f, 1f, 1f, color, light, overlay, pose, Direction.SOUTH);
				case NORTH ->
					renderSide(matrix4f, consumer, 0f, 1f, 1f, 0f, 0f, 0f, 0f, 0f, color, light, overlay, pose, Direction.NORTH);
				case EAST ->
					renderSide(matrix4f, consumer, 1f, 1f, 1f, 0f, 0f, 1f, 1f, 0f, color, light, overlay, pose, Direction.EAST);
				case WEST ->
					renderSide(matrix4f, consumer, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 0f, color, light, overlay, pose, Direction.WEST);
				case DOWN ->
					renderSide(matrix4f, consumer, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 1f, color, light, overlay, pose, Direction.DOWN);
				case UP ->
					renderSide(matrix4f, consumer, 0f, 1f, 1f, 1f, 1f, 1f, 0f, 0f, color, light, overlay, pose, Direction.UP);
			}
		}

		poseStack.popPose();
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

	public static void drawTexture(VertexConsumer vertex, PoseStack matrices, Color color, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
		drawTexturedQuad(vertex, matrices.last().pose(), color, x, x + width, y, y + height, u / (float) textureWidth, (u + width) / (float) textureWidth, v / (float) textureHeight, (v + height) / (float) textureHeight);
	}

	private static void drawTexturedQuad(VertexConsumer vertex, Matrix4f matrix, Color color, int x0, int x1, int y0, int y1, float u0, float u1, float v0, float v1) {
		vertex.addVertex(matrix, x0, y1, 0).setColor(color.red(), color.green(), color.blue(), color.alpha()).setUv(u0, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 0, 1);
		vertex.addVertex(matrix, x1, y1, 0).setColor(color.red(), color.green(), color.blue(), color.alpha()).setUv(u1, v1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 0, 1);
		vertex.addVertex(matrix, x1, y0, 0).setColor(color.red(), color.green(), color.blue(), color.alpha()).setUv(u1, v0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 0, 1);
		vertex.addVertex(matrix, x0, y0, 0).setColor(color.red(), color.green(), color.blue(), color.alpha()).setUv(u0, v0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 0, 1);
	}

	public static void renderSide(Matrix4f matrix4f, VertexConsumer vertices, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, Color color, int light, int overlay, PoseStack.Pose normal, Direction direction) {
		vertices.addVertex(matrix4f, x1, y1, z1).setColor(color.red(), color.green(), color.blue(), color.alpha()).setUv(0, 1).setOverlay(overlay).setLight(light).setNormal(normal, direction.getNormal().getX(), direction.getNormal().getY(), direction.getNormal().getZ());
		vertices.addVertex(matrix4f, x2, y1, z2).setColor(color.red(), color.green(), color.blue(), color.alpha()).setUv(1, 1).setOverlay(overlay).setLight(light).setNormal(normal, direction.getNormal().getX(), direction.getNormal().getY(), direction.getNormal().getZ());
		vertices.addVertex(matrix4f, x2, y2, z3).setColor(color.red(), color.green(), color.blue(), color.alpha()).setUv(1, 0).setOverlay(overlay).setLight(light).setNormal(normal, direction.getNormal().getX(), direction.getNormal().getY(), direction.getNormal().getZ());
		vertices.addVertex(matrix4f, x1, y2, z4).setColor(color.red(), color.green(), color.blue(), color.alpha()).setUv(0, 0).setOverlay(overlay).setLight(light).setNormal(normal, direction.getNormal().getX(), direction.getNormal().getY(), direction.getNormal().getZ());
	}

	private void renderOverlay(ResourceLocation texture, float opacity) {
		Minecraft client = Minecraft.getInstance();
		float scaledHeight = client.getWindow().getGuiScaledHeight();
		float scaledWidth = client.getWindow().getGuiScaledWidth();

		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1f, 1f, 1f, opacity);
		RenderSystem.setShaderTexture(0, texture);

		Tesselator tessellator = Tesselator.getInstance();
		BufferBuilder bufferBuilder = tessellator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

		bufferBuilder.addVertex(0f, scaledHeight, -90f).setUv(0f, 1f);
		bufferBuilder.addVertex(scaledWidth, scaledHeight, -90f).setUv(1f, 1f);
		bufferBuilder.addVertex(scaledWidth, 0f, -90f).setUv(1f, 0f);
		bufferBuilder.addVertex(0f, 0f, -90f).setUv(0f, 0f);

		BufferUploader.drawWithShader(bufferBuilder.build());
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
	}
}
