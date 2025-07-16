package dev.cammiescorner.arcanus.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static dev.cammiescorner.arcanus.common.util.TranslationKeys.*;

public class SpellComponentWidget extends AbstractButton {
	private final TooltipSupplier tooltipSupplier;
	private final PressAction onPress;
	private final Minecraft client = Minecraft.getInstance();
	private final SpellComponent component;

	public SpellComponentWidget(int x, SpellComponent component, PressAction onPress) {
		super(x, 0, 24, 24, Component.empty());
		this.component = component;
		this.onPress = onPress;

		List<Component> textList = new ArrayList<>();
		MutableComponent manaCost = Component.empty();

		textList.add(component.getName());

		for(ManaType manaType : ManaType.values()) {
			if(!manaCost.equals(Component.empty()))
				manaCost.append(Component.literal(" | ").withStyle(ChatFormatting.GRAY));

			manaCost.append(Component.literal(Arcanus.format(component.getManaCost().get(manaType))).withStyle(manaType.getChatFormatting()));
		}

		textList.add(manaCost);

		if(component instanceof SpellShape shape) {
			if(shape.getManaModifier() != 0)
				textList.add(Component.translatable(TWO_ARGUMENT_KEY,
					Component.translatable(SPELL_BOOK_MANA_MULTIPLIER),
					Component.literal(shape.getManaMultiplierAsString()).withStyle(ChatFormatting.GRAY)
				).withStyle(ChatFormatting.LIGHT_PURPLE));
			if(shape.getPotencyModifier() != 0)
				textList.add(Component.translatable(TWO_ARGUMENT_KEY,
					Component.translatable(SPELL_BOOK_POTENCY_MODIFIER),
					Component.literal(shape.getPotencyModifierAsString()).withStyle(ChatFormatting.GRAY)
				).withStyle(ChatFormatting.YELLOW));
			if(shape.getCoolDownModifier() != 1)
				textList.add(Component.translatable(TWO_ARGUMENT_KEY,
					Component.translatable(SPELL_BOOK_COOL_DOWN_MODIFIER),
					Component.literal(shape.getCoolDownModifierAsString()).withStyle(ChatFormatting.GRAY)
				).withStyle(ChatFormatting.AQUA));

			textList.add(Component.translatable(TWO_ARGUMENT_KEY,
				Component.translatable(SPELL_BOOK_WEIGHT),
				Component.translatable(shape.getWeight().translationKey()).withStyle(ChatFormatting.GRAY)
			).withStyle(ChatFormatting.DARK_GREEN));
		}

		this.tooltipSupplier = new TooltipSupplier() {
			@Override
			public void onTooltip(SpellComponentWidget spellComponentWidget, GuiGraphics gui, int mouseX, int mouseY) {
				if(client.screen != null)
					gui.renderComponentTooltip(client.font, textList, mouseX, mouseY);
			}

			@Override
			public void supply(Consumer<Component> consumer) {
				consumer.accept(textList.get(0));
			}
		};
	}

	@Override
	public void onPress() {
		onPress.onPress(this);
	}

	@Override
	public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
		RenderSystem.setShaderColor(0.25f, 0.25f, 0.3f, 1f);
		gui.blit(component.getTexture(client.player), getX(), getY(), 0, 0, 24, 24, 24, 24);
	}

	public void renderTooltip(GuiGraphics gui, int mouseX, int mouseY) {
		tooltipSupplier.onTooltip(this, gui, mouseX, mouseY);
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput builder) {
		defaultButtonNarrationText(builder);
		tooltipSupplier.supply(text -> builder.add(NarratedElementType.HINT, text));
	}

	public SpellComponent getSpellComponent() {
		return component;
	}

	public interface TooltipSupplier {
		void onTooltip(SpellComponentWidget spellComponentWidget, GuiGraphics gui, int i, int j);

		default void supply(Consumer<Component> consumer) {
		}
	}

	public interface PressAction {
		void onPress(SpellComponentWidget buttonWidget);
	}
}
