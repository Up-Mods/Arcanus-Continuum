package dev.cammiescorner.arcanuscontinuum.client.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.Rectangle;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellComponent;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.client.gui.widgets.SpellComponentWidget;
import dev.cammiescorner.arcanuscontinuum.client.gui.widgets.UndoButtonWidget;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import dev.cammiescorner.arcanuscontinuum.common.screens.SpellcraftScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class SpellcraftScreen extends HandledScreen<SpellcraftScreenHandler> {
	public static final Identifier BOOK_TEXTURE = Arcanus.id("textures/gui/spell_book.png");
	public static final Identifier PANEL_TEXTURE = Arcanus.id("textures/gui/spell_crafting.png");
	private static List<SpellComponent> SPELL_SHAPES;
	private static List<SpellComponent> SPELL_EFFECTS;
	private final List<SpellComponentWidget> spellShapeWidgets = Lists.newArrayList();
	private final List<SpellComponentWidget> spellEffectWidgets = Lists.newArrayList();
	private int leftScroll, rightScroll;
	private double leftKnobPos, rightKnobPos;
	private boolean draggingLeft, draggingRight;

	public SpellcraftScreen(SpellcraftScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, Text.empty());
	}

	@Override
	protected void init() {
		super.init();
		x = (width - 256) / 2;
		y = (height - 180) / 2;
		playerInventoryTitleY = -10000;
		SPELL_SHAPES = Arcanus.SPELL_COMPONENTS.stream().filter(component -> component != ArcanusSpellComponents.EMPTY && ArcanusComponents.getWizardLevel(MinecraftClient.getInstance().player) >= component.getMinLevel() && component instanceof SpellShape).toList();
		SPELL_EFFECTS = Arcanus.SPELL_COMPONENTS.stream().filter(component -> component != ArcanusSpellComponents.EMPTY && ArcanusComponents.getWizardLevel(MinecraftClient.getInstance().player) >= component.getMinLevel() && component instanceof SpellEffect).toList();

		if(client != null) {
			for(SpellComponent component : SPELL_SHAPES)
				if(ArcanusComponents.getWizardLevel(client.player) >= component.getMinLevel())
					addSpellShapeChild(new SpellComponentWidget(-34, component));
			for(SpellComponent component : SPELL_EFFECTS)
				if(ArcanusComponents.getWizardLevel(client.player) >= component.getMinLevel())
					addSpellEffectChild(new SpellComponentWidget(267, component));
		}

		addCloseButton();
		addDrawableChild(new TextFieldWidget(client.textRenderer, (width - 80) / 2, y + 8, 104, 14, Text.literal("Spell Name...")));
		addDrawableChild(new UndoButtonWidget((width - 128) / 2, y + 7));
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
		Rectangle leftKnob = getLeftScrollKnob();
		Rectangle rightKnob = getRightScrollKnob();

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, PANEL_TEXTURE);

		DrawableHelper.drawTexture(matrices, leftKnob.x(), leftKnob.y(), draggingLeft ? 12 : 0, 184, leftKnob.width(), leftKnob.height(), 384, 256);
		DrawableHelper.drawTexture(matrices, rightKnob.x(), rightKnob.y(), draggingRight ? 12 : 0, 184, rightKnob.width(), rightKnob.height(), 384, 256);

		drawWidgets(matrices, mouseX, mouseY, MinecraftClient.getInstance().getTickDelta());

		super.drawForeground(matrices, mouseX, mouseY);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		if(isPointWithinBounds(-62, 1, 58, 178, mouseX, mouseY)) {
			if(leftScroll > 0 && amount > 0)
				leftScroll--;
			if(leftScroll < SPELL_SHAPES.size() * 2 - 12 && amount < 0)
				leftScroll++;

			leftKnobPos = leftScroll * (148F / (SPELL_SHAPES.size() * 2 - 12));
		}

		if(isPointWithinBounds(260, 1, 58, 178, mouseX, mouseY)) {
			if(rightScroll > 0 && amount > 0)
				rightScroll--;
			if(rightScroll < SPELL_EFFECTS.size() * 2 - 12 && amount < 0)
				rightScroll++;

			rightKnobPos = rightScroll * (148F / (SPELL_EFFECTS.size() * 2 - 12));
		}

		return super.mouseScrolled(mouseX, mouseY, amount);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		if(draggingLeft) {
			leftKnobPos = MathHelper.clamp(mouseY - y - 16, 0, 148);
			leftScroll = (int) (leftKnobPos / 148 * (SPELL_SHAPES.size() * 2 - 12));
		}
		else if(draggingRight) {
			rightKnobPos = MathHelper.clamp(mouseY - y - 16, 0, 148);
			rightScroll = (int) (rightKnobPos / 148 * (SPELL_EFFECTS.size() * 2 - 12));
		}

		return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if(button == 0) {
			if(isPointWithinBounds(-58, 5, 12, 170, mouseX, mouseY)) {
				draggingLeft = true;
				leftKnobPos = MathHelper.clamp(mouseY - y - 16, 0, 148);
				leftScroll = (int) (leftKnobPos / 148 * (SPELL_SHAPES.size() * 2 - 12));
			}
			else if(isPointWithinBounds(302, 5, 12, 170, mouseX, mouseY)) {
				draggingRight = true;
				rightKnobPos = MathHelper.clamp(mouseY - y - 16, 0, 148);
				rightScroll = (int) (rightKnobPos / 148 * (SPELL_EFFECTS.size() * 2 - 12));
			}
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if(draggingLeft)
			draggingLeft = false;
		if(draggingRight)
			draggingRight = false;

		return super.mouseReleased(mouseX, mouseY, button);
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

	private void drawWidgets(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		int scale = (int) client.getWindow().getScaleFactor();

		// Render Spell Shapes
		RenderSystem.enableScissor((x - 37) * scale, (y + 6) * scale, 28 * scale, 168 * scale);

		for(int i = 0; i < spellShapeWidgets.size(); i++) {
			SpellComponentWidget widget = spellShapeWidgets.get(i);
			widget.setY(8 + (i * 28) - leftScroll * 14);
			widget.render(matrices, mouseX - x, mouseY - y, delta);
		}

		RenderSystem.disableScissor();

		// Render Spell Effects
		RenderSystem.enableScissor((x + 265) * scale, (y + 6) * scale, 28 * scale, 168 * scale);

		for(int i = 0; i < spellEffectWidgets.size(); i++) {
			SpellComponentWidget widget = spellEffectWidgets.get(i);
			widget.setY(8 + (i * 28) - rightScroll * 14);
			widget.render(matrices, mouseX - x, mouseY - y, delta);
		}

		RenderSystem.disableScissor();

		for(SpellComponentWidget widget : spellShapeWidgets)
			if(widget.isHoveredOrFocused())
				widget.renderTooltip(matrices, mouseX - x, mouseY - y);
		for(SpellComponentWidget widget : spellEffectWidgets)
			if(widget.isHoveredOrFocused())
				widget.renderTooltip(matrices, mouseX - x, mouseY - y);
	}

	protected void addCloseButton() {
		addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> closeScreen()).position(width / 2 - 100, y + 170).size(98, 20).build());
		addDrawableChild(ButtonWidget.builder(Text.translatable("lectern.take_book"), (button) -> {
			client.interactionManager.clickButton(handler.syncId, 0);
			closeScreen();
		}).position(width / 2 + 2, y + 170).size(98, 20).build());
	}

	public <T extends SpellComponentWidget> T addSpellShapeChild(T drawable) {
		spellShapeWidgets.add(drawable);
		return addSelectableChild(drawable);
	}

	public <T extends SpellComponentWidget> T addSpellEffectChild(T drawable) {
		spellEffectWidgets.add(drawable);
		return addSelectableChild(drawable);
	}

	public Rectangle getLeftScrollKnob() {
		return new Rectangle(-58, (int) (5 + leftKnobPos), 12, 22);
	}

	public Rectangle getRightScrollKnob() {
		return new Rectangle(302, (int) (5 + rightKnobPos), 12, 22);
	}
}
