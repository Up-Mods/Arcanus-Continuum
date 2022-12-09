package dev.cammiescorner.arcanuscontinuum.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellComponent;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import dev.cammiescorner.arcanuscontinuum.common.screens.SpellcraftScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class SpellcraftScreen extends HandledScreen<SpellcraftScreenHandler> {
	private static final Identifier BOOK_TEXTURE = Arcanus.id("textures/gui/spell_book.png");
	private static final Identifier PANEL_TEXTURE = Arcanus.id("textures/gui/spell_crafting.png");
	private static final List<SpellComponent> SPELL_SHAPES = Arcanus.SPELL_COMPONENTS.stream().filter(component -> component != ArcanusSpellComponents.EMPTY && component instanceof SpellShape).toList();
	private static final List<SpellComponent> SPELL_EFFECTS = Arcanus.SPELL_COMPONENTS.stream().filter(component -> component != ArcanusSpellComponents.EMPTY && component instanceof SpellEffect).toList();
	private int leftScroll = 0, rightScroll = 0;

	public SpellcraftScreen(SpellcraftScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, Text.empty());
	}

	@Override
	protected void init() {
		super.init();
		x = (width - 256) / 2;
		y = (height - 180) / 2;
		playerInventoryTitleY = -10000;
		addCloseButton();
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, BOOK_TEXTURE);
		DrawableHelper.drawTexture(matrices, x, y, 0, 0, 256, 180, 256, 256);

		RenderSystem.setShaderTexture(0, PANEL_TEXTURE);
		DrawableHelper.drawTexture(matrices, x - 62, y + 1, 0, 0, 380, 178, 384, 256);
	}

	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		int scale = (int) client.getWindow().getScaleFactor();

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, PANEL_TEXTURE);
		DrawableHelper.drawTexture(matrices, -58, 5, 0, 184, 12, 22, 384, 256);
		DrawableHelper.drawTexture(matrices, 302, 5, 0, 184, 12, 22, 384, 256);

		// Render Spell Shapes
		RenderSystem.enableScissor((x - 37) * scale, (y + 6) * scale, 28 * scale, 168 * scale);
		matrices.push();
		matrices.translate(0, -leftScroll * 14, 0);

		for(int i = 0; i < SPELL_SHAPES.size(); i++) {
			SpellComponent component = SPELL_SHAPES.get(i);
			RenderSystem.setShaderColor(0.25F, 0.25F, 0.3F, 1F);
			RenderSystem.setShaderTexture(0, component.getTexture());
			DrawableHelper.drawTexture(matrices, -35, 8 + (i * 28), 0, 0, 24, 24, 24, 24);
		}

		matrices.pop();
		RenderSystem.disableScissor();

		// Render Spell Effects
		RenderSystem.enableScissor((x + 265) * scale, (y + 6) * scale, 28 * scale, 168 * scale);
		matrices.push();
		matrices.translate(0, -rightScroll * 14, 0);

		for(int i = 0; i < SPELL_EFFECTS.size(); i++) {
			SpellComponent component = SPELL_EFFECTS.get(i);
			RenderSystem.setShaderColor(0.25F, 0.25F, 0.3F, 1F);
			RenderSystem.setShaderTexture(0, component.getTexture());
			DrawableHelper.drawTexture(matrices, 267, 8 + (i * 28), 0, 0, 24, 24, 24, 24);
		}

		matrices.pop();
		RenderSystem.disableScissor();

		super.drawForeground(matrices, mouseX, mouseY);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		if(mouseX >= x - 62 && mouseX <= x - 4 && mouseY >= y + 1 && mouseY <= y + 179) {
			if(leftScroll > 0 && amount > 0)
				leftScroll--;
			if(leftScroll < SPELL_SHAPES.size() * 2 - 12 && amount < 0)
				leftScroll++;
		}

		if(mouseX >= x + 260 && mouseX <= x + 318 && mouseY >= y + 1 && mouseY <= y + 179) {
			if(rightScroll > 0 && amount > 0)
				rightScroll--;
			if(rightScroll < SPELL_EFFECTS.size() * 2 - 12 && amount < 0)
				rightScroll++;
		}

		return super.mouseScrolled(mouseX, mouseY, amount);
	}

	@Override
	public List<ClickableWidget> getButtons() {
		return null;
	}

	@Override
	public ItemRenderer getItemRenderer() {
		return getClient().getItemRenderer();
	}

	@Override
	public TextRenderer getTextRenderer() {
		return getClient().textRenderer;
	}

	@Override
	public MinecraftClient getClient() {
		return MinecraftClient.getInstance();
	}

	protected void addCloseButton() {
		addDrawableChild(new ButtonWidget(width / 2 - 100, y + 170, 98, 20, ScreenTexts.DONE, (button) -> closeScreen()));
		addDrawableChild(new ButtonWidget(width / 2 + 2, y + 170, 98, 20, Text.translatable("lectern.take_book"), (button) -> {
			client.interactionManager.clickButton(handler.syncId, 0);
			closeScreen();
		}));
	}
}
