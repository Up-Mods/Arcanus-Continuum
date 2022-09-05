package dev.cammiescorner.arcanuscontinuum.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusItems;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class ArcanusClient implements ClientModInitializer {
	private static final Identifier HUD_ELEMENTS = Arcanus.id("textures/gui/hud/mana_and_spells.png");

	@Override
	public void onInitializeClient(ModContainer mod) {
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableItem) stack.getItem()).getColor(stack) : 0xffffff,
				ArcanusItems.WOODEN_STAFF, ArcanusItems.AMETHYST_SHARD_STAFF, ArcanusItems.QUARTZ_SHARD_STAFF,
				ArcanusItems.ENDER_SHARD_STAFF, ArcanusItems.ECHO_SHARD_STAFF
		);

		final MinecraftClient client = MinecraftClient.getInstance();
		var manaTimer = new Object() {
			int value;
		};

		HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
			PlayerEntity player = client.player;

			if(player != null && !player.isSpectator()) {
				double maxMana = ArcanusComponents.getMaxMana(player);
				double halfMaxMana = maxMana * 0.5;
				double mana = ArcanusComponents.getMana(player);
				double manaLock = ArcanusComponents.getManaLock(player);
				double burnout = ArcanusComponents.getBurnout(player) + manaLock;

				if(player.getMainHandStack().getItem() instanceof StaffItem || mana < maxMana)
					manaTimer.value = Math.min(manaTimer.value + 1, 40);
				else
					manaTimer.value = Math.max(manaTimer.value - 1, 0);

				if(manaTimer.value > 0) {
					int scaledWidth = client.getWindow().getScaledWidth();
					int scaledHeight = client.getWindow().getScaledHeight();
					int x = scaledWidth - 64;
					int y = scaledHeight - 64;
					float alpha = manaTimer.value > 20 ? 1F : manaTimer.value / 20F;

					RenderSystem.enableBlend();
					RenderSystem.setShaderTexture(0, HUD_ELEMENTS);
					RenderSystem.setShaderColor(1F, 1F, 1F, alpha);

					// render background
					DrawableHelper.drawTexture(matrices, x, y, 64, 0, 64, 64, 256, 256);

					// render mana
					int m = (int) (63 * (Math.min(mana, halfMaxMana) / halfMaxMana));
					DrawableHelper.drawTexture(matrices, x + 1, y + 1 + (63 - m), 128, 1 + (63 - m), 8, m, 256, 256);

					if(mana > halfMaxMana)
						DrawableHelper.drawTexture(matrices, x + 1, y + 1, 1, 64, (int) (63 * ((mana - halfMaxMana) / halfMaxMana)), 8, 256, 256);

					// render burnout
					int b = (int) Math.ceil(63 * (Math.min(burnout, halfMaxMana) / halfMaxMana));
					DrawableHelper.drawTexture(matrices, x + 1 + (63 - b), y + 1, 65 + (63 - b), 64, b, 8, 256, 256);

					if(burnout > halfMaxMana)
						DrawableHelper.drawTexture(matrices, x + 1, y + 1, 136, 1, 8, (int) Math.ceil(63 * ((burnout - halfMaxMana) / halfMaxMana)), 256, 256);

					// render mana lock
					int l = (int) Math.ceil(63 * (Math.min(manaLock, halfMaxMana) / halfMaxMana));
					DrawableHelper.drawTexture(matrices, x + 1 + (63 - l), y + 1, 129 + (63 - l), 64, l, 8, 256, 256);

					if(manaLock > halfMaxMana)
						DrawableHelper.drawTexture(matrices, x + 1, y + 1, 144, 1, 8, (int) Math.ceil(63 * ((manaLock - halfMaxMana) / halfMaxMana)), 256, 256);

					// render frame
					DrawableHelper.drawTexture(matrices, x, y, 0, 0, 64, 64, 256, 256);
				}
			}
		});
	}
}
