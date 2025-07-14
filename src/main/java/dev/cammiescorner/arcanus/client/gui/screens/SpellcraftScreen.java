package dev.cammiescorner.arcanus.client.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import commonnetwork.api.Network;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spell.*;
import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import dev.cammiescorner.arcanus.api.util.Rectangle;
import dev.cammiescorner.arcanus.client.gui.util.Action;
import dev.cammiescorner.arcanus.client.gui.util.UndoRedoStack;
import dev.cammiescorner.arcanus.client.gui.widgets.SpellComponentWidget;
import dev.cammiescorner.arcanus.client.gui.widgets.UndoRedoButtonWidget;
import dev.cammiescorner.arcanus.common.item.SpellScrollItem;
import dev.cammiescorner.arcanus.common.menu.SpellcraftMenu;
import dev.cammiescorner.arcanus.common.networking.serverbound.ServerboundSaveBookDataPacket;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import dev.cammiescorner.arcanus.common.util.TranslationKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector4i;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static dev.cammiescorner.arcanus.common.util.TranslationKeys.*;

public class SpellcraftScreen extends AbstractContainerScreen<SpellcraftMenu> {
	public static final ResourceLocation BOOK_TEXTURE = Arcanus.id("textures/gui/spell_scroll.png");
	public static final ResourceLocation PANEL_TEXTURE = Arcanus.id("textures/gui/spell_crafting.png");
	private static final Vector4i VALID_BOUNDS = new Vector4i(30, 40, 197, 114);
	private static final LinkedList<SpellGroup> SPELL_GROUPS = new LinkedList<>();
	private static List<SpellComponent> spellShapes;
	private static List<SpellComponent> spellEffects;
	private final List<SpellComponentWidget> spellShapeWidgets = Lists.newArrayList();
	private final List<SpellComponentWidget> spellEffectWidgets = Lists.newArrayList();
	private final UndoRedoStack undoRedoStack = new UndoRedoStack();
	private SpellComponent draggedComponent = ArcanusSpellComponents.EMPTY.get();
	private ItemStack stack = ItemStack.EMPTY;
	private BlockPos blockPos = BlockPos.ZERO;
	private EditBox textBox;
	private int leftScroll, rightScroll;
	private double leftKnobPos, rightKnobPos;
	private boolean draggingLeft, draggingRight;

	public SpellcraftScreen(SpellcraftMenu screenHandler, Inventory playerInventory, Component text) {
		super(screenHandler, playerInventory, Component.empty());
		this.imageWidth = 380;
		this.imageHeight = 178;
	}

	@Override
	protected void init() {
		super.init();
		leftPos = (width - 256) / 2;
		topPos = (height - 180) / 2;
		inventoryLabelY = -10000;
		spellShapes = ArcanusSpellComponents.REGISTRY.stream().filter(component -> !ArcanusSpellComponents.EMPTY.is(component) && component.isEnabled() && component instanceof SpellShape).toList();
		spellEffects = ArcanusSpellComponents.REGISTRY.stream().filter(component -> !ArcanusSpellComponents.EMPTY.is(component) && component.isEnabled() && component instanceof SpellEffect).toList();

		if(minecraft != null) {
			for(SpellComponent component : spellShapes) {
				if(ArcanusComponents.knowsSpellComponents(minecraft.player, component)) {
					addSpellShapeChild(new SpellComponentWidget(-35, component, widget -> {
						if(spellComponentCount() < ArcanusComponents.maxSpellSize())
							draggedComponent = widget.getSpellComponent();
					}));
				}
			}

			for(SpellComponent component : spellEffects) {
				if(ArcanusComponents.knowsSpellComponents(minecraft.player, component)) {
					addSpellEffectChild(new SpellComponentWidget(267, component, widget -> {
						if(spellComponentCount() < ArcanusComponents.maxSpellSize())
							draggedComponent = widget.getSpellComponent();
					}));
				}
			}
		}

		addCloseButtons();
		textBox = addRenderableWidget(new EditBox(minecraft.font, leftPos + 15, topPos + 8, 88, 14, Component.empty()));
		textBox.setValue(SpellScrollItem.getSpell(stack).getName());

		for(SpellGroup group : SpellScrollItem.getSpell(stack).getComponentGroups()) {
			if(!group.isEmpty()) {
				undoRedoStack.addAction(new Action(group.shape(), group.positions().getFirst(), () -> SPELL_GROUPS.add(group), () -> SPELL_GROUPS.remove(group))).Do().run();

				for(int i = 0; i < group.effects().size(); i++) {
					SpellEffect effect = group.effects().get(i);
					Vector2i pos = group.positions().get(i + 1);

					undoRedoStack.addAction(new Action(effect, pos, () -> {
						group.effects().add(effect);
						group.positions().add(pos);
					}, () -> {
						group.effects().remove(effect);
						group.positions().remove(pos);
					}));
				}
			}
		}

		addRenderableWidget(new UndoRedoButtonWidget((width - 48) / 2, topPos - 8, true, undoRedoStack, button -> undoRedoStack.undo()));
		addRenderableWidget(new UndoRedoButtonWidget(width / 2, topPos - 8, false, undoRedoStack, button -> undoRedoStack.redo()));
	}

	@Override
	protected void renderBg(GuiGraphics gui, float delta, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		gui.blit(BOOK_TEXTURE, leftPos - 32, topPos, 0, 0, 320, 180, 320, 256);

		RenderSystem.setShaderTexture(0, PANEL_TEXTURE);
		gui.blit(PANEL_TEXTURE, leftPos - 62, topPos + 1, 0, 0, 380, 178, 384, 256);
	}

	@Override
	protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
		Rectangle leftKnob = getLeftScrollKnob();
		Rectangle rightKnob = getRightScrollKnob();

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		RenderSystem.setShaderTexture(0, PANEL_TEXTURE);

		gui.blit(PANEL_TEXTURE, leftKnob.x(), leftKnob.y(), draggingLeft ? 12 : 0, 184, leftKnob.width(), leftKnob.height(), 384, 256);
		gui.blit(PANEL_TEXTURE, rightKnob.x(), rightKnob.y(), draggingRight ? 12 : 0, 184, rightKnob.width(), rightKnob.height(), 384, 256);

		drawWidgets(gui, mouseX, mouseY, minecraft.getTimer().getGameTimeDeltaTicks());
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
		if(isHovering(-62, 1, 58, 178, mouseX, mouseY)) {
			if(leftScroll > 0 && scrollY > 0)
				leftScroll--;
			if(leftScroll < spellShapes.size() * 2 - 12 && scrollY < 0)
				leftScroll++;

			leftKnobPos = leftScroll * (148f / (spellShapes.size() * 2 - 12));
		}

		if(isHovering(260, 1, 58, 178, mouseX, mouseY)) {
			if(rightScroll > 0 && scrollY > 0)
				rightScroll--;
			if(rightScroll < spellEffects.size() * 2 - 12 && scrollY < 0)
				rightScroll++;

			rightKnobPos = rightScroll * (148f / (spellEffects.size() * 2 - 12));
		}

		return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		if(draggingLeft) {
			leftKnobPos = Mth.clamp(mouseY - topPos - 16, 0, 148);
			leftScroll = (int) (leftKnobPos / 148 * (spellShapes.size() * 2 - 12));
		}
		else if(draggingRight) {
			rightKnobPos = Mth.clamp(mouseY - topPos - 16, 0, 148);
			rightScroll = (int) (rightKnobPos / 148 * (spellEffects.size() * 2 - 12));
		}

		return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if(button == 0) {
			for(SpellComponentWidget widget : spellShapeWidgets)
				if(isHovering(widget.getX(), widget.getY(), widget.getWidth(), widget.getHeight(), mouseX, mouseY))
					widget.onClick(mouseX, mouseY);
			for(SpellComponentWidget widget : spellEffectWidgets)
				if(isHovering(widget.getX(), widget.getY(), widget.getWidth(), widget.getHeight(), mouseX, mouseY))
					widget.onClick(mouseX, mouseY);

			if(isHovering(-58, 5, 12, 170, mouseX, mouseY)) {
				draggingLeft = true;
				leftKnobPos = Mth.clamp(mouseY - topPos - 16, 0, 148);
				leftScroll = (int) (leftKnobPos / 148 * (spellShapes.size() * 2 - 12));
			}
			else if(isHovering(302, 5, 12, 170, mouseX, mouseY)) {
				draggingRight = true;
				rightKnobPos = Mth.clamp(mouseY - topPos - 16, 0, 148);
				rightScroll = (int) (rightKnobPos / 148 * (spellEffects.size() * 2 - 12));
			}
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if(!ArcanusSpellComponents.EMPTY.is(draggedComponent) && button == 0) {
			if(isHovering(VALID_BOUNDS.x(), VALID_BOUNDS.y(), VALID_BOUNDS.z(), VALID_BOUNDS.w(), mouseX, mouseY) && !isTooCloseToComponents(mouseX, mouseY)) {
				Vector2i pos = new Vector2i((int) (mouseX - this.leftPos - 12), (int) (mouseY - this.topPos - 12));

				if(draggedComponent instanceof SpellShape shape) {
					List<Vector2i> positions = new ArrayList<>();
					positions.add(pos);
					SpellGroup group = new SpellGroup(shape, new ArrayList<>(), positions);
					Action action = undoRedoStack.addAction(new Action(draggedComponent, pos, () -> SPELL_GROUPS.add(group), () -> SPELL_GROUPS.remove(group)));

					action.Do().run();
				}

				if(draggedComponent instanceof SpellEffect effect && !SPELL_GROUPS.isEmpty() && (SPELL_GROUPS.getLast().effects().isEmpty() || !SPELL_GROUPS.getLast().effects().contains(effect)) && !ArcanusSpellComponents.EMPTY.is(SPELL_GROUPS.getLast().shape())) {
					Action action = undoRedoStack.addAction(new Action(draggedComponent, pos, () -> {
						SPELL_GROUPS.getLast().effects().add(effect);
						SPELL_GROUPS.getLast().positions().add(pos);
					}, () -> {
						SPELL_GROUPS.getLast().effects().remove(effect);
						SPELL_GROUPS.getLast().positions().remove(pos);
					}));

					action.Do().run();
				}
			}

			draggedComponent = ArcanusSpellComponents.EMPTY.get();
		}

		if(draggingLeft)
			draggingLeft = false;
		if(draggingRight)
			draggingRight = false;

		return super.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if(textBox.isFocused()) {
			if(keyCode == GLFW.GLFW_KEY_ESCAPE) {
				textBox.setFocused(false);
				return false;
			}

			if(keyCode == GLFW.GLFW_KEY_E)
				return false;
		}

		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	protected void clearWidgets() {
		super.clearWidgets();
		SPELL_GROUPS.clear();
		spellShapeWidgets.clear();
		spellEffectWidgets.clear();
	}

	@Override
	public void onClose() {
		Network.getNetworkHandler().sendToServer(new ServerboundSaveBookDataPacket(blockPos, getSpell()));
		super.onClose();
	}

	public void setBook(ItemStack stack) {
		this.stack = stack;
		textBox.setValue(SpellScrollItem.getSpell(stack).getName());
		SPELL_GROUPS.clear();

		for(SpellGroup group : SpellScrollItem.getSpell(stack).getComponentGroups()) {
			if(!group.isEmpty()) {
				undoRedoStack.addAction(new Action(group.shape(), group.positions().getFirst(), () -> SPELL_GROUPS.add(group), () -> SPELL_GROUPS.remove(group))).Do().run();

				for(int i = 0; i < group.effects().size(); i++) {
					SpellEffect effect = group.effects().get(i);
					Vector2i pos = group.positions().get(i + 1);

					undoRedoStack.addAction(new Action(effect, pos, () -> {
						group.effects().add(effect);
						group.positions().add(pos);
					}, () -> {
						group.effects().remove(effect);
						group.positions().remove(pos);
					}));
				}
			}
		}
	}

	public void setBlockPos(BlockPos blockPos) {
		this.blockPos = blockPos;
	}

	private void drawWidgets(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
		PoseStack poseStack = gui.pose();
		int scale = (int) minecraft.getWindow().getGuiScale();

		// Render Spell Shapes
		RenderSystem.enableScissor((leftPos - 38) * scale, (topPos + 5) * scale, 30 * scale, 170 * scale);

		for(int i = 0; i < spellShapeWidgets.size(); i++) {
			SpellComponentWidget widget = spellShapeWidgets.get(i);
			widget.setY(8 + (i * 28) - leftScroll * 14);
			widget.render(gui, mouseX - leftPos, mouseY - topPos, partialTick);

			RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

			if(widget.isHoveredOrFocused())
				gui.blit(PANEL_TEXTURE, widget.getX() - 3, widget.getY() - 3, 0, 208, 30, 30, 384, 256);
		}

		RenderSystem.disableScissor();

		// Render Spell Effects
		RenderSystem.enableScissor((leftPos + 264) * scale, (topPos + 5) * scale, 30 * scale, 170 * scale);

		for(int i = 0; i < spellEffectWidgets.size(); i++) {
			SpellComponentWidget widget = spellEffectWidgets.get(i);
			widget.setY(8 + (i * 28) - rightScroll * 14);
			widget.render(gui, mouseX - leftPos, mouseY - topPos, partialTick);

			RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

			if(widget.isHoveredOrFocused())
				gui.blit(PANEL_TEXTURE, widget.getX() - 3, widget.getY() - 3, 0, 208, 30, 30, 384, 256);
		}

		RenderSystem.disableScissor();

		for(int i = 0; i < SPELL_GROUPS.size(); i++) {
			SpellGroup group = SPELL_GROUPS.get(i);
			List<Vector2i> positions = group.positions();
			RenderSystem.setShader(GameRenderer::getPositionShader);
			RenderSystem.setShaderColor(0.25f, 0.25f, 0.3f, 1f);
			BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
			poseStack.pushPose();
			poseStack.translate(12, 12, 0);
			Matrix4f matrix = poseStack.last().pose();

			for(int j = 0; j < positions.size(); j++) {
				Vector2i pos = positions.get(j);
				Vector2i prevPos = positions.get(Math.max(0, j - 1));

				if(j == 0 && i > 0 && !SPELL_GROUPS.get(i - 1).isEmpty()) {
					List<Vector2i> prevPositions = SPELL_GROUPS.get(i - 1).positions();
					prevPos = prevPositions.getLast();
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

				bufferBuilder.addVertex(matrix, x2 - dx, y2 - dy, 0);
				bufferBuilder.addVertex(matrix, x2 + dx, y2 + dy, 0);
				bufferBuilder.addVertex(matrix, x1 + dx, y1 + dy, 0);
				bufferBuilder.addVertex(matrix, x1 - dx, y1 - dy, 0);
			}

			if(bufferBuilder.build() instanceof MeshData data)
				BufferUploader.drawWithShader(data);

			poseStack.popPose();
		}

		for(SpellGroup group : SPELL_GROUPS) {
			List<Vector2i> positions = group.positions();

			for(int j = 0; j < positions.size(); j++) {
				Vector2i pos = positions.get(j);
				RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
				gui.blit(PANEL_TEXTURE, pos.x - 3, pos.y - 3, 60, 208, 30, 30, 384, 256);

				RenderSystem.setShaderColor(0.25f, 0.25f, 0.3f, 1f);
				gui.blit(PANEL_TEXTURE, pos.x - 3, pos.y - 3, 30, 208, 30, 30, 384, 256);

				gui.blit(group.getAllComponents().toList().get(j).getTexture(minecraft.player), pos.x, pos.y, 0, 0, 24, 24, 24, 24);
			}
		}

		if(!ArcanusSpellComponents.EMPTY.is(draggedComponent)) {
			int color = 0xff0000;

			if((isHovering(VALID_BOUNDS.x(), VALID_BOUNDS.y(), VALID_BOUNDS.z(), VALID_BOUNDS.w(), mouseX, mouseY) && !isTooCloseToComponents(mouseX, mouseY)) && (!(draggedComponent instanceof SpellEffect) || (!SPELL_GROUPS.isEmpty() && !SPELL_GROUPS.getLast().isEmpty() && (SPELL_GROUPS.getLast().effects().isEmpty() || !SPELL_GROUPS.getLast().effects().contains(draggedComponent)))))
				color = 0x00ff00;

			float r = (color >> 16 & 255) / 255f;
			float g = (color >> 8 & 255) / 255f;
			float b = (color & 255) / 255f;

			RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
			gui.blit(PANEL_TEXTURE, mouseX - leftPos - 15, mouseY - topPos - 15, 60, 208, 30, 30, 384, 256);

			RenderSystem.setShaderColor(r, g, b, 1f);
			gui.blit(PANEL_TEXTURE, mouseX - leftPos - 15, mouseY - topPos - 15, 30, 208, 30, 30, 384, 256);

			RenderSystem.setShaderColor(r, g, b, 1f);
			gui.blit(draggedComponent.getTexture(minecraft.player), mouseX - leftPos - 12, mouseY - topPos - 12, 0, 0, 24, 24, 24, 24);
		}

		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

		int componentCount = spellComponentCount();
		int maxComponents = ArcanusComponents.maxSpellSize();
		int componentCounterColor = 0x5555ff;

		if(componentCount >= maxComponents)
			componentCounterColor = 0xcc2222;

		String spellComponentCount = String.valueOf(componentCount);
		String maxSpellComponentCount = String.valueOf(maxComponents);

		gui.drawString(font, spellComponentCount, 118 - font.width(spellComponentCount) / 2, 11, componentCounterColor, false);
		gui.drawString(font, " / ", 128 - font.width(" / ") / 2, 11, 0x555555, false);
		gui.drawString(font, maxSpellComponentCount, 138 - font.width(maxSpellComponentCount) / 2, 11, componentCounterColor, false);

		MutableComponent mana = Component.empty();
		MutableComponent weight = Component.translatable(getWeight().translationKey()).withStyle(ChatFormatting.DARK_GREEN);
		MutableComponent coolDown = (getCoolDown() > 0 ? Component.literal(Arcanus.format(getCoolDown() / 20d) + "s") : Component.translatable(SPELL_BOOK_INSTANT_COOL_DOWN)).withStyle(ChatFormatting.DARK_RED);

		for(ManaType manaType : ManaType.values()) {
			if(!mana.equals(Component.empty()))
				mana.append(Component.literal(", ").withStyle(ChatFormatting.GRAY));

			mana.append(Component.literal(Arcanus.format(getManaCost(manaType))).withStyle(manaType.getChatFormatting()));
		}

		gui.drawString(font, mana, 240 - font.width(mana), 7, 0xffffff, false);
		gui.drawString(font, weight, 240 - font.width(weight), 17, 0xffffff, false);
		gui.drawString(font, coolDown, 240 - font.width(coolDown), 27, 0xffffff, false);

		if(isHovering(109, 8, font.width("12 / 12"), font.lineHeight + 4, mouseX, mouseY))
			gui.renderTooltip(font, Component.translatable(TranslationKeys.SCREEN_SPELL_COMPONENT_COUNT), mouseX - leftPos, mouseY - topPos);

		for(SpellComponentWidget widget : spellShapeWidgets)
			if(widget.isHoveredOrFocused())
				widget.renderTooltip(gui, mouseX - leftPos, mouseY - topPos);

		for(SpellComponentWidget widget : spellEffectWidgets)
			if(widget.isHoveredOrFocused())
				widget.renderTooltip(gui, mouseX - leftPos, mouseY - topPos);

		for(SpellGroup group : SPELL_GROUPS) {
			for(int i = 0; i < group.positions().size(); i++) {
				Vector2i position = group.positions().get(i);

				if(isHovering(position.x() - 2, position.y() - 2, 28, 28, mouseX, mouseY)) {
					List<Component> textList = new ArrayList<>();
					SpellComponent component = group.getAllComponents().toList().get(i);
					boolean knowsComponent = ArcanusComponents.knowsSpellComponents(minecraft.player, component);

					textList.add(knowsComponent ? component.getName() : Component.literal("???"));

					for(ManaType manaType : ManaType.values()) {
						textList.add(Component.translatable(TWO_ARGUMENT_KEY,
							Component.translatable(manaType.getTranslationKey()),
							Component.literal(knowsComponent ? component.getManaCostAsString(manaType) : "???").withStyle(ChatFormatting.GRAY)
						).withStyle(manaType.getChatFormatting()));
					}

					if(component instanceof SpellShape shape) {
						if(shape.getManaModifier() != 0)
							textList.add(Component.translatable(TWO_ARGUMENT_KEY,
								Component.translatable(SPELL_BOOK_MANA_MULTIPLIER),
								Component.literal(knowsComponent ? shape.getManaMultiplierAsString() : "???").withStyle(ChatFormatting.GRAY)
							).withStyle(ChatFormatting.LIGHT_PURPLE));
						if(shape.getPotencyModifier() != 0)
							textList.add(Component.translatable(TWO_ARGUMENT_KEY,
								Component.translatable(SPELL_BOOK_POTENCY_MODIFIER),
								Component.literal(knowsComponent ? shape.getManaMultiplierAsString() : "???").withStyle(ChatFormatting.GRAY)
							).withStyle(ChatFormatting.YELLOW));
						if(shape.getCoolDownModifier() != 1)
							textList.add(Component.translatable(TWO_ARGUMENT_KEY,
								Component.translatable(SPELL_BOOK_COOL_DOWN_MODIFIER),
								Component.literal(knowsComponent ? shape.getCoolDownModifierAsString() : "???").withStyle(ChatFormatting.GRAY)
							).withStyle(ChatFormatting.AQUA));

						textList.add(Component.translatable(TWO_ARGUMENT_KEY,
							Component.translatable(SPELL_BOOK_WEIGHT),
							Component.translatable(knowsComponent ? shape.getWeight().translationKey() : "???").withStyle(ChatFormatting.GRAY)
						).withStyle(ChatFormatting.DARK_GREEN));
					}

					gui.renderComponentTooltip(font, textList, mouseX - leftPos, mouseY - topPos);
				}
			}
		}
	}

	protected void addCloseButtons() {
		addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) -> {
			onClose();
		}).pos(width / 2 - 100, topPos + 170).size(98, 20).build());

		addRenderableWidget(Button.builder(Component.translatable(BUTTON_TAKE_SCROLL), (button) -> {
			minecraft.gameMode.handleInventoryButtonClick(menu.containerId, 0);
			onClose();
		}).pos(width / 2 + 2, topPos + 170).size(98, 20).build());
	}

	public <T extends SpellComponentWidget> T addSpellShapeChild(T drawable) {
		spellShapeWidgets.add(drawable);
		return drawable;
	}

	public <T extends SpellComponentWidget> T addSpellEffectChild(T drawable) {
		spellEffectWidgets.add(drawable);
		return drawable;
	}

	public Rectangle getLeftScrollKnob() {
		return new Rectangle(-58, (int) (5 + leftKnobPos), 12, 22);
	}

	public Rectangle getRightScrollKnob() {
		return new Rectangle(302, (int) (5 + rightKnobPos), 12, 22);
	}

	public boolean isTooCloseToComponents(double mouseX, double mouseY) {
		return distanceToNearestComponent(mouseX, mouseY) < 40;
	}

	public double distanceToNearestComponent(double mouseX, double mouseY) {
		return SPELL_GROUPS.stream().mapToDouble(spellGroup -> spellGroup.positions().stream().mapToDouble(position -> position.distance((int) (mouseX - leftPos - 12), (int) (mouseY - topPos - 12))).min().orElse(Double.MAX_VALUE)).min().orElse(Double.MAX_VALUE);
	}

	public int spellComponentCount() {
		int count = 0;

		for(SpellGroup group : SPELL_GROUPS)
			if(!group.isEmpty())
				count += group.getAllComponents().toList().size();

		return count;
	}

	public Spell getSpell() {
		if(SPELL_GROUPS.isEmpty())
			return new Spell();

		if(SPELL_GROUPS.get(0).isEmpty() && SPELL_GROUPS.size() > 1 && !SPELL_GROUPS.get(1).isEmpty())
			SPELL_GROUPS.removeFirst();

		// TODO store aspects from original spell
		return new Spell(SPELL_GROUPS, textBox.getValue().isBlank() ? "Empty" : textBox.getValue(), new SpellAspects(1, 1, 1));
	}

	public Weight getWeight() {
		return getSpell().getWeight();
	}

	public double getManaCost(ManaType manaType) {
		return getSpell().getManaCost().get(manaType);
	}

	public int getCoolDown() {
		return getSpell().getCoolDown();
	}
}
