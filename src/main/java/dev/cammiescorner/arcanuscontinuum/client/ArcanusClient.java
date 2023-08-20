package dev.cammiescorner.arcanuscontinuum.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Pattern;
import dev.cammiescorner.arcanuscontinuum.client.gui.screens.ArcaneWorkbenchScreen;
import dev.cammiescorner.arcanuscontinuum.client.gui.screens.SpellBookScreen;
import dev.cammiescorner.arcanuscontinuum.client.gui.screens.SpellcraftScreen;
import dev.cammiescorner.arcanuscontinuum.client.models.armour.WizardArmourModel;
import dev.cammiescorner.arcanuscontinuum.client.models.entity.*;
import dev.cammiescorner.arcanuscontinuum.client.models.feature.LotusHaloModel;
import dev.cammiescorner.arcanuscontinuum.client.models.feature.SpellPatternModel;
import dev.cammiescorner.arcanuscontinuum.client.particles.CollapseParticle;
import dev.cammiescorner.arcanuscontinuum.client.renderer.armour.WizardArmourRenderer;
import dev.cammiescorner.arcanuscontinuum.client.renderer.block.MagicBlockEntityRenderer;
import dev.cammiescorner.arcanuscontinuum.client.renderer.entity.living.OpossumEntityRenderer;
import dev.cammiescorner.arcanuscontinuum.client.renderer.entity.living.WizardEntityRenderer;
import dev.cammiescorner.arcanuscontinuum.client.renderer.entity.magic.*;
import dev.cammiescorner.arcanuscontinuum.client.renderer.item.StaffItemRenderer;
import dev.cammiescorner.arcanuscontinuum.common.compat.FirstPersonCompat;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.packets.s2c.SyncStaffTemplatePacket;
import dev.cammiescorner.arcanuscontinuum.common.packets.s2c.SyncStatusEffectPacket;
import dev.cammiescorner.arcanuscontinuum.common.packets.s2c.SyncSupporterData;
import dev.cammiescorner.arcanuscontinuum.common.packets.s2c.SyncWorkbenchModePacket;
import dev.cammiescorner.arcanuscontinuum.common.registry.*;
import dev.cammiescorner.arcanuscontinuum.common.util.ArcanusHelper;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;

import java.util.List;
import java.util.function.BooleanSupplier;

public class ArcanusClient implements ClientModInitializer {
	private static final Identifier HUD_ELEMENTS = Arcanus.id("textures/gui/hud/mana_bar.png");
	private static final Identifier STUN_OVERLAY = Arcanus.id("textures/gui/hud/stunned_vignette.png");
	private static final Identifier MAGIC_CIRCLES = Arcanus.id("textures/entity/feature/magic_circles.png");
	public static final Identifier WHITE = new Identifier("textures/misc/white.png");
	public static BooleanSupplier FIRST_PERSON_MODEL_ENABLED = () -> false;
	public static BooleanSupplier FIRST_PERSON_SHOW_HANDS = () -> true;

	@Override
	public void onInitializeClient(ModContainer mod) {
		if(QuiltLoader.isModLoaded("firstperson")) {
			FIRST_PERSON_MODEL_ENABLED = FirstPersonCompat.isEnabled();
			FIRST_PERSON_SHOW_HANDS = FirstPersonCompat.showVanillaHands();
		}

		HandledScreens.register(ArcanusScreenHandlers.SPELLCRAFT_SCREEN_HANDLER, SpellcraftScreen::new);
		HandledScreens.register(ArcanusScreenHandlers.SPELL_BOOK_SCREEN_HANDLER, SpellBookScreen::new);
		HandledScreens.register(ArcanusScreenHandlers.ARCANE_WORKBENCH_SCREEN_HANDLER, ArcaneWorkbenchScreen::new);

		EntityModelLayerRegistry.registerModelLayer(WizardArmourModel.MODEL_LAYER, WizardArmourModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(WizardEntityModel.MODEL_LAYER, WizardEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(OpossumEntityModel.MODEL_LAYER, OpossumEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(MagicLobEntityModel.MODEL_LAYER, MagicLobEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(MagicProjectileEntityModel.MODEL_LAYER, MagicProjectileEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(MagicRuneEntityModel.MODEL_LAYER, MagicRuneEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(AreaOfEffectEntityModel.MODEL_LAYER, AreaOfEffectEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(SpellPatternModel.MODEL_LAYER, SpellPatternModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(LotusHaloModel.MODEL_LAYER, LotusHaloModel::getTexturedModelData);

		ArmorRenderer.register(new WizardArmourRenderer(), ArcanusItems.WIZARD_HAT, ArcanusItems.WIZARD_ROBES, ArcanusItems.WIZARD_PANTS, ArcanusItems.WIZARD_BOOTS);

		EntityRendererRegistry.register(ArcanusEntities.WIZARD, WizardEntityRenderer::new);
		EntityRendererRegistry.register(ArcanusEntities.OPOSSUM, OpossumEntityRenderer::new);
		EntityRendererRegistry.register(ArcanusEntities.NECRO_SKELETON, SkeletonEntityRenderer::new);
		EntityRendererRegistry.register(ArcanusEntities.MANA_SHIELD, ManaShieldEntityRenderer::new);
		EntityRendererRegistry.register(ArcanusEntities.MAGIC_PROJECTILE, MagicProjectileEntityRenderer::new);
		EntityRendererRegistry.register(ArcanusEntities.AOE, AreaOfEffectEntityRenderer::new);
		EntityRendererRegistry.register(ArcanusEntities.SMITE, SmiteEntityRenderer::new);
		EntityRendererRegistry.register(ArcanusEntities.MAGIC_RUNE, MagicRuneEntityRenderer::new);
		EntityRendererRegistry.register(ArcanusEntities.BEAM, BeamEntityRenderer::new);

		ParticleFactoryRegistry.getInstance().register(ArcanusParticles.COLLAPSE, CollapseParticle.Factory::new);

		BlockRenderLayerMap.put(RenderLayer.getCutout(), ArcanusBlocks.MAGIC_DOOR, ArcanusBlocks.ARCANE_WORKBENCH);
		BlockEntityRendererFactories.register(ArcanusBlockEntities.MAGIC_BLOCK, MagicBlockEntityRenderer::new);

		ClientPlayNetworking.registerGlobalReceiver(SyncStatusEffectPacket.ID, SyncStatusEffectPacket::handle);
		ClientPlayNetworking.registerGlobalReceiver(SyncWorkbenchModePacket.ID, SyncWorkbenchModePacket::handle);
		ClientPlayNetworking.registerGlobalReceiver(SyncStaffTemplatePacket.ID, SyncStaffTemplatePacket::handle);
		ClientPlayNetworking.registerGlobalReceiver(SyncSupporterData.ID, SyncSupporterData::handle);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? StaffItem.getPrimaryColour(stack) : tintIndex == 1 ? StaffItem.getSecondaryColour(stack) : 0xffffff,
				ArcanusItems.WOODEN_STAFF, ArcanusItems.CRYSTAL_STAFF, ArcanusItems.DIVINATION_STAFF,
				ArcanusItems.CRESCENT_STAFF, ArcanusItems.ANCIENT_STAFF, ArcanusItems.WAND,
				ArcanusItems.THAUMATURGES_GAUNTLET, ArcanusItems.MAGIC_TOME, ArcanusItems.MAGE_PISTOL
		);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableArmorItem) stack.getItem()).getColor(stack) : 0xffffff,
				ArcanusItems.WIZARD_HAT, ArcanusItems.WIZARD_ROBES, ArcanusItems.WIZARD_PANTS, ArcanusItems.WIZARD_BOOTS
		);

		for(Item item : ArcanusItems.ITEMS.keySet()) {
			if(item instanceof StaffItem) {
				Identifier itemId = Registries.ITEM.getId(item);
				StaffItemRenderer staffItemRenderer = new StaffItemRenderer(itemId);
				ResourceLoader.get(ResourceType.CLIENT_RESOURCES).registerReloader(staffItemRenderer);
				BuiltinItemRendererRegistry.INSTANCE.register(item, staffItemRenderer);
				ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
					out.accept(new ModelIdentifier(itemId.withPath(itemId.getPath() + "_gui"), "inventory"));
					out.accept(new ModelIdentifier(itemId.withPath(itemId.getPath() + "_handheld"), "inventory"));
				});
			}
		}

		final MinecraftClient client = MinecraftClient.getInstance();

		WorldRenderEvents.AFTER_ENTITIES.register(context -> {
			if(!context.camera().isThirdPerson() && !FIRST_PERSON_MODEL_ENABLED.getAsBoolean())
				renderFirstPersonBolt(context, client);
		});

		var obj = new Object() {
			int timer;
		};

		HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
			PlayerEntity player = client.player;

			if(player != null && !player.isSpectator()) {
				int stunTimer = ArcanusComponents.getStunTimer(player);

				if(stunTimer > 0) {
					if(stunTimer > 5)
						renderOverlay(STUN_OVERLAY, Math.min(1F, 0.5F + (stunTimer % 5F) / 10F));
					else
						renderOverlay(STUN_OVERLAY, Math.min(1F, stunTimer / 5F));
				}

				if(!client.gameRenderer.getCamera().isThirdPerson() && !FIRST_PERSON_MODEL_ENABLED.getAsBoolean()) {
					List<Pattern> list = ArcanusComponents.getPattern(player);

					if(!list.isEmpty()) {
						VertexConsumerProvider.Immediate vertices = client.getBufferBuilders().getEntityVertexConsumers();
						RenderLayer renderLayer = getMagicCircles(MAGIC_CIRCLES);
						VertexConsumer vertex = vertices.getBuffer(renderLayer);
						int colour = Arcanus.getMagicColour(player.getGameProfile().getId());
						float x = client.getWindow().getScaledWidth() / 2F;
						float y = client.getWindow().getScaledHeight() / 2F;
						float scale = 3F;

						matrices.push();
						matrices.translate(x, y, 0);

						for(int i = 0; i < list.size(); i++) {
							Pattern pattern = list.get(i);
							matrices.push();

							if(i == 1)
								matrices.multiply(Axis.Z_POSITIVE.rotationDegrees((player.age + player.getId() + tickDelta) * (5 + (2.5F * i))));
							else
								matrices.multiply(Axis.Z_NEGATIVE.rotationDegrees((player.age + player.getId() + tickDelta) * (5 + (2.5F * i))));

							matrices.scale(scale, scale, 0);
							matrices.translate(-8.5, -8.5, 0);
							drawTexture(vertex, matrices, colour, 0, 0, i * 34, pattern == Pattern.LEFT ? 0 : 24, 17, 17, 128, 48);
							matrices.pop();
						}

						matrices.pop();
						vertices.drawCurrentLayer();
					}
				}

				double maxMana = ArcanusComponents.getMaxMana(player);
				double mana = ArcanusComponents.getMana(player);
				double burnout = ArcanusComponents.getBurnout(player);
				double manaLock = ArcanusComponents.getManaLock(player);

				if(player.getMainHandStack().getItem() instanceof StaffItem)
					obj.timer = Math.min(obj.timer + 1, 40);
				else
					obj.timer = Math.max(obj.timer - 1, 0);

				if(obj.timer > 0) {
					int x = 0;
					int y = client.getWindow().getScaledHeight() - 28;
					int width = 96;
					float alpha = obj.timer > 20 ? 1F : obj.timer / 20F;

					RenderSystem.enableBlend();
					RenderSystem.setShaderTexture(0, HUD_ELEMENTS);
					RenderSystem.setShaderColor(1F, 1F, 1F, alpha);

					// render frame
					DrawableHelper.drawTexture(matrices, x, y, 0, 0, 101, 28, 256, 256);

					// render mana
					DrawableHelper.drawTexture(matrices, x, y + 5, 0, 32, (int) (width * (mana / maxMana)), 23, 256, 256);

					// render burnout
					int i = (int) Math.ceil(width * ((burnout + manaLock) / maxMana));
					DrawableHelper.drawTexture(matrices, x + (width - i), y + 5, width - i, 56, i, 23, 256, 256);

					// render mana lock
					i = (int) Math.ceil(width * (manaLock / maxMana));
					DrawableHelper.drawTexture(matrices, x + (width - i), y + 5, width - i, 80, i, 23, 256, 256);
				}
			}
		});
	}

	private static void renderFirstPersonBolt(WorldRenderContext context, MinecraftClient client) {
		if(client.player != null) {
			MatrixStack matrices = context.matrixStack();
			Vec3d camPos = context.camera().getPos();
			float tickDelta = context.tickDelta();
			Vec3d startPos = client.player.getLerpedPos(tickDelta).add(0, client.player.getEyeHeight(client.player.getPose()), 0);

			matrices.push();
			matrices.translate(-camPos.getX(), -camPos.getY(), -camPos.getZ());
			ArcanusHelper.renderBolts(client.player, startPos.add(0, -0.1, 0), matrices, context.consumers());
			matrices.pop();
		}
	}

	public static RenderLayer getMagicCircles(Identifier texture) {
		return RenderLayer.of(Arcanus.id("magic_circle").toString(), VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, false, true, RenderLayer.MultiPhaseParameters.builder().shader(RenderLayer.ENTITY_TRANSLUCENT_EMISSIVE_SHADER).texture(new RenderPhase.Texture(texture, false, false)).overlay(RenderPhase.ENABLE_OVERLAY_COLOR).transparency(RenderLayer.ADDITIVE_TRANSPARENCY).writeMaskState(RenderLayer.ALL_MASK).cull(RenderPhase.DISABLE_CULLING).build(false));
	}

	public static RenderLayer getMagicCirclesTri(Identifier texture) {
		return RenderLayer.of(Arcanus.id("magic_circle_tri").toString(), VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.TRIANGLES, 256, false, true, RenderLayer.MultiPhaseParameters.builder().shader(RenderLayer.ENTITY_TRANSLUCENT_EMISSIVE_SHADER).texture(new RenderPhase.Texture(texture, false, false)).overlay(RenderPhase.ENABLE_OVERLAY_COLOR).transparency(RenderLayer.ADDITIVE_TRANSPARENCY).writeMaskState(RenderLayer.ALL_MASK).cull(RenderPhase.DISABLE_CULLING).build(false));
	}

	public static void drawTexture(VertexConsumer vertex, MatrixStack matrices, int colour, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
		drawTexturedQuad(vertex, matrices.peek().getModel(),
				colour,
				x,
				x + width,
				y,
				y + height,
				u / (float) textureWidth,
				(u + width) / (float) textureWidth,
				v / (float) textureHeight,
				(v + height) / (float) textureHeight
		);
	}

	private static void drawTexturedQuad(VertexConsumer vertex, Matrix4f matrix, int colour, int x0, int x1, int y0, int y1, float u0, float u1, float v0, float v1) {
		vertex.vertex(matrix, x0, y1, 0).color(colour).uv(u0, v1).overlay(OverlayTexture.DEFAULT_UV).light(15728850).normal(0, 0, 1).next();
		vertex.vertex(matrix, x1, y1, 0).color(colour).uv(u1, v1).overlay(OverlayTexture.DEFAULT_UV).light(15728850).normal(0, 0, 1).next();
		vertex.vertex(matrix, x1, y0, 0).color(colour).uv(u1, v0).overlay(OverlayTexture.DEFAULT_UV).light(15728850).normal(0, 0, 1).next();
		vertex.vertex(matrix, x0, y0, 0).color(colour).uv(u0, v0).overlay(OverlayTexture.DEFAULT_UV).light(15728850).normal(0, 0, 1).next();
	}

	private void renderOverlay(Identifier texture, float opacity) {
		MinecraftClient client = MinecraftClient.getInstance();
		double scaledHeight = client.getWindow().getScaledHeight();
		double scaledWidth = client.getWindow().getScaledWidth();

		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, opacity);
		RenderSystem.setShaderTexture(0, texture);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBufferBuilder();
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
		bufferBuilder.vertex(0.0, scaledHeight, -90.0).uv(0.0F, 1.0F).next();
		bufferBuilder.vertex(scaledWidth, scaledHeight, -90.0).uv(1.0F, 1.0F).next();
		bufferBuilder.vertex(scaledWidth, 0.0, -90.0).uv(1.0F, 0.0F).next();
		bufferBuilder.vertex(0.0, 0.0, -90.0).uv(0.0F, 0.0F).next();
		tessellator.draw();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static Vector3f RGBtoHSB(int r, int g, int b) {
		float hue, saturation, brightness;
		int cmax = Math.max(r, g);
		if(b > cmax)
			cmax = b;
		int cmin = Math.min(r, g);
		if(b < cmin)
			cmin = b;

		brightness = ((float) cmax) / 255.0f;
		if(cmax != 0)
			saturation = ((float) (cmax - cmin)) / ((float) cmax);
		else
			saturation = 0;
		if(saturation == 0)
			hue = 0;
		else {
			float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
			float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
			float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
			if(r == cmax)
				hue = bluec - greenc;
			else if(g == cmax)
				hue = 2.0f + redc - bluec;
			else
				hue = 4.0f + greenc - redc;
			hue = hue / 6.0f;
			if(hue < 0)
				hue = hue + 1.0f;
		}

		return new Vector3f(hue, saturation, brightness);
	}
}
