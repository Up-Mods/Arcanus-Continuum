package dev.cammiescorner.arcanuscontinuum.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.*;
import dev.cammiescorner.arcanuscontinuum.common.items.SpellBookItem;
import dev.cammiescorner.arcanuscontinuum.common.screens.SpellBookScreenHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.CommonTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class SpellBookScreen extends HandledScreen<SpellBookScreenHandler> {
	public static final Identifier BOOK_TEXTURE = Arcanus.id("textures/gui/spell_book.png");
	public static final Identifier PANEL_TEXTURE = Arcanus.id("textures/gui/spell_crafting.png");
	public final LinkedList<SpellGroup> SPELL_GROUPS = new LinkedList<>();

	public SpellBookScreen(SpellBookScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
	}

	@Override
	protected void init() {
		super.init();
		x = (width - 256) / 2;
		y = (height - 180) / 2;
		playerInventoryTitleY = -10000;

		addDrawableChild(ButtonWidget.builder(CommonTexts.DONE, (button) -> closeScreen()).position(width / 2 - 49, y + 170).size(98, 20).build());
		SPELL_GROUPS.addAll(SpellBookItem.getSpell(getScreenHandler().getSpellBook()).getComponentGroups());
	}

	@Override
	protected void drawBackground(GuiGraphics gui, float delta, int mouseX, int mouseY) {
		this.renderBackground(gui);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		gui.drawTexture(BOOK_TEXTURE, x, y, 0, 0, 256, 180, 256, 256);
	}

	@Override
	protected void drawForeground(GuiGraphics gui, int mouseX, int mouseY) {
		MatrixStack matrices = gui.getMatrices();
		MutableText title = this.title.copy().formatted(Formatting.BOLD, Formatting.UNDERLINE);
		gui.drawText(textRenderer, title, 128 - textRenderer.getWidth(title) / 2, 11, 0x50505D, false);

		for(int i = 0; i < SPELL_GROUPS.size(); i++) {
			SpellGroup group = SPELL_GROUPS.get(i);
			List<Vector2i> positions = group.positions();
			RenderSystem.setShader(GameRenderer::getPositionShader);
			RenderSystem.setShaderColor(0.25F, 0.25F, 0.3F, 1F);
			BufferBuilder bufferBuilder = Tessellator.getInstance().getBufferBuilder();

			bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
			matrices.push();
			matrices.translate(12, 12, 0);
			Matrix4f matrix = matrices.peek().getModel();

			for(int j = 0; j < positions.size(); j++) {
				Vector2i pos = positions.get(j);
				Vector2i prevPos = positions.get(Math.max(0, j - 1));

				if(j == 0 && i > 0 && !SPELL_GROUPS.get(i - 1).isEmpty()) {
					List<Vector2i> prevPositions = SPELL_GROUPS.get(i - 1).positions();
					prevPos = prevPositions.get(prevPositions.size() - 1);
				}
				if(pos.equals(prevPos))
					continue;

				int x1 = prevPos.x();
				int y1 = prevPos.y();
				int x2 = pos.x();
				int y2 = pos.y();
				float angle = (float) (Math.atan2(y2 - y1, x2 - x1) - (Math.PI * 0.5));
				float dx = MathHelper.cos(angle);
				float dy = MathHelper.sin(angle);

				bufferBuilder.vertex(matrix, x2 - dx, y2 - dy, 0).color(0).next();
				bufferBuilder.vertex(matrix, x2 + dx, y2 + dy, 0).color(0).next();
				bufferBuilder.vertex(matrix, x1 + dx, y1 + dy, 0).color(0).next();
				bufferBuilder.vertex(matrix, x1 - dx, y1 - dy, 0).color(0).next();
			}

			BufferRenderer.drawWithShader(bufferBuilder.end());
			matrices.pop();
		}

		for(SpellGroup group : SPELL_GROUPS) {
			List<Vector2i> positions = group.positions();

			for(int j = 0; j < positions.size(); j++) {
				Vector2i pos = positions.get(j);
				RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
				gui.drawTexture(PANEL_TEXTURE, pos.x - 3, pos.y - 3, 60, 208, 30, 30, 384, 256);

				RenderSystem.setShaderColor(0.25F, 0.25F, 0.3F, 1F);
				gui.drawTexture(PANEL_TEXTURE, pos.x - 3, pos.y - 3, 30, 208, 30, 30, 384, 256);

				gui.drawTexture(group.getAllComponents().toList().get(j).getTexture(), pos.x, pos.y, 0, 0, 24, 24, 24, 24);
			}
		}

		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

		MutableText weight = Arcanus.translate("spell_book", "weight", getWeight().toString().toLowerCase(Locale.ROOT)).formatted(Formatting.DARK_GREEN);
		MutableText mana = Text.literal(Arcanus.format(getManaCost())).formatted(Formatting.BLUE);
		MutableText coolDown = Text.literal(Arcanus.format(getCoolDown() / 20D)).append(Arcanus.translate("spell_book", "seconds")).formatted(Formatting.RED);

		gui.drawText(textRenderer, weight, 240 - textRenderer.getWidth(weight), 7, 0xffffff, false);
		gui.drawText(textRenderer, mana, 240 - textRenderer.getWidth(mana), 17, 0xffffff, false);
		gui.drawText(textRenderer, coolDown, 240 - textRenderer.getWidth(coolDown), 27, 0xffffff, false);

		for(SpellGroup group : SPELL_GROUPS) {
			for(int i = 0; i < group.positions().size(); i++) {
				Vector2i position = group.positions().get(i);

				if(isPointWithinBounds(position.x() - 2, position.y() - 2, 28, 28, mouseX, mouseY)) {
					List<Text> textList = new ArrayList<>();
					SpellComponent component = group.getAllComponents().toList().get(i);

					textList.add(component.getTranslatedName());
					textList.add(Arcanus.translate("spell_book", "weight").append(": ").formatted(Formatting.GREEN).append(Arcanus.translate("spell_book", "weight", component.getWeight().toString().toLowerCase(Locale.ROOT)).formatted(Formatting.GRAY)));
					textList.add(Arcanus.translate("spell_book", "mana_cost").append(": ").formatted(Formatting.BLUE).append(Text.literal(component.getManaCostAsString()).formatted(Formatting.GRAY)));

					if(component instanceof SpellShape shape) {
						if(shape.getManaMultiplier() != 0)
							textList.add(Arcanus.translate("spell_book", "mana_multiplier").append(": ").formatted(Formatting.LIGHT_PURPLE).append(Text.literal(shape.getManaMultiplierAsString()).formatted(Formatting.GRAY)));
						if(shape.getPotencyModifier() != 0)
							textList.add(Arcanus.translate("spell_book", "potency_modifier").append(": ").formatted(Formatting.YELLOW).append(Text.literal(shape.getPotencyModifierAsString()).formatted(Formatting.GRAY)));
					}

					textList.add(Arcanus.translate("spell_book", "cool_down").append(": ").formatted(Formatting.RED).append(Text.literal(component.getCoolDownAsString()).append(Arcanus.translate("spell_book", "seconds")).formatted(Formatting.GRAY)));

					gui.drawTooltip(textRenderer, textList, mouseX - x, mouseY - y);
				}
			}
		}
	}

	@Override
	protected void clearChildren() {
		super.clearChildren();
		SPELL_GROUPS.clear();
	}

	protected boolean isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY) {
		int i = this.x;
		int j = this.y;
		pointX -= i;
		pointY -= j;

		return pointX >= (x - 1) && pointX < (x + width + 1) && pointY >= (y - 1) && pointY < (y + height + 1);
	}

	public Spell getSpell() {
		return new Spell(SPELL_GROUPS, title.getString());
	}

	public Weight getWeight() {
		return getSpell().getWeight();
	}

	public double getManaCost() {
		return getSpell().getManaCost();
	}

	public int getCoolDown() {
		return getSpell().getCoolDown();
	}
}
