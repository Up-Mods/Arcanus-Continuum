package dev.cammiescorner.arcanus.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spells.*;
import dev.cammiescorner.arcanus.common.items.SpellBookItem;
import dev.cammiescorner.arcanus.common.screens.SpellBookMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class SpellBookScreen extends AbstractContainerScreen<SpellBookMenu> {
	public static final ResourceLocation BOOK_TEXTURE = Arcanus.id("textures/gui/spell_book.png");
	public static final ResourceLocation PANEL_TEXTURE = Arcanus.id("textures/gui/spell_crafting.png");
	public final LinkedList<SpellGroup> SPELL_GROUPS = new LinkedList<>();
	private ItemStack stack = ItemStack.EMPTY;
	private Spell spell = new Spell();

	public SpellBookScreen(SpellBookMenu screenHandler, Inventory playerInventory, Component text) {
		super(screenHandler, playerInventory, text);
	}

	@Override
	protected void init() {
		super.init();
		leftPos = (width - 256) / 2;
		topPos = (height - 180) / 2;
		inventoryLabelY = -10000;

		addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) -> onClose()).pos(width / 2 - 49, topPos + 170).size(98, 20).build());
		setSpell(SpellBookItem.getSpell(stack));
		SPELL_GROUPS.addAll(getSpell().getComponentGroups());
	}

	@Override
	protected void renderBg(GuiGraphics gui, float delta, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		gui.blit(BOOK_TEXTURE, leftPos, topPos, 0, 0, 256, 180, 256, 256);
	}

	@Override
	protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
		PoseStack matrices = gui.pose();
		MutableComponent title = Component.literal(getSpell().getName()).withStyle(ChatFormatting.BOLD, ChatFormatting.UNDERLINE);
		gui.drawString(font, title, 128 - font.width(title) / 2, 11, 0x50505D, false);

		for(int i = 0; i < SPELL_GROUPS.size(); i++) {
			SpellGroup group = SPELL_GROUPS.get(i);
			List<Vector2i> positions = group.positions();
			RenderSystem.setShader(GameRenderer::getPositionShader);
			RenderSystem.setShaderColor(0.25f, 0.25f, 0.3f, 1f);
			BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
			matrices.pushPose();
			matrices.translate(12, 12, 0);
			Matrix4f matrix = matrices.last().pose();
			boolean hasData = false;

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
				float dx = Mth.cos(angle);
				float dy = Mth.sin(angle);

				bufferBuilder.addVertex(matrix, x2 - dx, y2 - dy, 0).setColor(0);
				bufferBuilder.addVertex(matrix, x2 + dx, y2 + dy, 0).setColor(0);
				bufferBuilder.addVertex(matrix, x1 + dx, y1 + dy, 0).setColor(0);
				bufferBuilder.addVertex(matrix, x1 - dx, y1 - dy, 0).setColor(0);

				hasData = true;
			}

			if(hasData)
				BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());

			matrices.popPose();
		}

		for(SpellGroup group : SPELL_GROUPS) {
			for(Vector2i pos : group.positions()) {
				RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
				gui.blit(PANEL_TEXTURE, pos.x - 3, pos.y - 3, 60, 208, 30, 30, 384, 256);

				RenderSystem.setShaderColor(0.25f, 0.25f, 0.3f, 1f);
				gui.blit(PANEL_TEXTURE, pos.x - 3, pos.y - 3, 30, 208, 30, 30, 384, 256);

				gui.blit(group.getAllComponents().toList().get(group.positions().indexOf(pos)).getTexture(), pos.x, pos.y, 0, 0, 24, 24, 24, 24);
			}
		}

		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

		MutableComponent weight = Component.translatable(getWeight().translationKey()).withStyle(ChatFormatting.DARK_GREEN);
		MutableComponent mana = Component.literal(Arcanus.format(getManaCost())).withStyle(ChatFormatting.BLUE);
		MutableComponent coolDown = Component.literal(Arcanus.format(getCoolDown() / 20d) + "s").withStyle(ChatFormatting.RED);

		gui.drawString(font, weight, 240 - font.width(weight), 7, 0xffffff, false);
		gui.drawString(font, mana, 240 - font.width(mana), 17, 0xffffff, false);
		gui.drawString(font, coolDown, 240 - font.width(coolDown), 27, 0xffffff, false);

		for(SpellGroup group : SPELL_GROUPS) {
			for(int i = 0; i < group.positions().size(); i++) {
				Vector2i position = group.positions().get(i);

				if(isHovering(position.x() - 2, position.y() - 2, 28, 28, mouseX, mouseY)) {
					List<Component> textList = new ArrayList<>();
					SpellComponent component = group.getAllComponents().toList().get(i);

					// TODO do all the translation stuff
					textList.add(component.getName());
					textList.add(Component.translatable("spell_book.arcanus.weight").append(": ").withStyle(ChatFormatting.GREEN).append(Component.translatable("spell_book.arcanus.weight", component.getWeight().toString().toLowerCase(Locale.ROOT)).withStyle(ChatFormatting.GRAY)));
					textList.add(Component.translatable("spell_book.arcanus.mana_cost").append(": ").withStyle(ChatFormatting.BLUE).append(Component.literal(component.getManaCostAsString()).withStyle(ChatFormatting.GRAY)));

					if(component instanceof SpellShape shape) {
						if(shape.getManaMultiplier() != 0)
							textList.add(Component.translatable("spell_book.arcanus.mana_multiplier").append(": ").withStyle(ChatFormatting.LIGHT_PURPLE).append(Component.literal(shape.getManaMultiplierAsString()).withStyle(ChatFormatting.GRAY)));
						if(shape.getPotencyModifier() != 0)
							textList.add(Component.translatable("spell_book.arcanus.potency_modifier").append(": ").withStyle(ChatFormatting.YELLOW).append(Component.literal(shape.getPotencyModifierAsString()).withStyle(ChatFormatting.GRAY)));
					}

					textList.add(Component.translatable("spell_book.arcanus.cool_down").append(": ").withStyle(ChatFormatting.RED).append(Component.literal(component.getCoolDownAsString()).withStyle(ChatFormatting.GRAY)));

					gui.renderComponentTooltip(font, textList, mouseX - leftPos, mouseY - topPos);
				}
			}
		}
	}

	@Override
	protected void clearWidgets() {
		super.clearWidgets();
		SPELL_GROUPS.clear();
	}

	public void setBook(ItemStack stack) {
		this.stack = stack;
		setSpell(SpellBookItem.getSpell(stack));
		SPELL_GROUPS.clear();
		SPELL_GROUPS.addAll(SpellBookItem.getSpell(stack).getComponentGroups());
	}

	public void setSpell(Spell spell) {
		this.spell = spell;
	}

	protected boolean isHovering(int x, int y, int width, int height, double pointX, double pointY) {
		int i = this.leftPos;
		int j = this.topPos;
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
