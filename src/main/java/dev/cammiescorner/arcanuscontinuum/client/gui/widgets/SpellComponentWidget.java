package dev.cammiescorner.arcanuscontinuum.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellComponent;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class SpellComponentWidget extends PressableWidget {
	private final TooltipSupplier tooltipSupplier;
	private final PressAction onPress;
	private final MinecraftClient client = MinecraftClient.getInstance();
	private final SpellComponent component;

	public SpellComponentWidget(int x, SpellComponent component, PressAction onPress) {
		super(x, 0, 24, 24, Text.empty());
		this.component = component;
		this.onPress = onPress;

		List<Text> textList = new ArrayList<>();
		textList.add(Text.translatable(component.getTranslationKey()));
		textList.add(Arcanus.translate("spell_book", "weight").append(": ").formatted(Formatting.GREEN).append(Arcanus.translate("spell_book", "weight", component.getWeight().toString().toLowerCase(Locale.ROOT)).formatted(Formatting.GRAY)));
		textList.add(Arcanus.translate("spell_book", "mana_cost").append(": ").formatted(Formatting.BLUE).append(Text.literal(component.getManaCostAsString()).formatted(Formatting.GRAY)));

		if(component instanceof SpellShape shape && shape.getManaMultiplier() != 0)
			textList.add(Arcanus.translate("spell_book", "mana_multiplier").append(": ").formatted(Formatting.LIGHT_PURPLE).append(Text.literal(shape.getManaMultiplierAsString()).formatted(Formatting.GRAY)));

		textList.add(Arcanus.translate("spell_book", "cool_down").append(": ").formatted(Formatting.RED).append(Text.literal(component.getCoolDownAsString()).append(Arcanus.translate("spell_book", "seconds")).formatted(Formatting.GRAY)));


		this.tooltipSupplier = new TooltipSupplier() {
			@Override
			public void onTooltip(SpellComponentWidget spellComponentWidget, MatrixStack matrices, int mouseX, int mouseY) {
				if(client.currentScreen != null)
					client.currentScreen.renderTooltip(matrices, textList, mouseX, mouseY);
			}

			@Override
			public void supply(Consumer<Text> consumer) {
				consumer.accept(textList.get(0));
			}
		};
	}

	@Override
	public void onPress() {
		onPress.onPress(this);
	}

	@Override
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(0.25F, 0.25F, 0.3F, 1F);
		RenderSystem.setShaderTexture(0, component.getTexture());
		DrawableHelper.drawTexture(matrices, x, y, 0, 0, 24, 24, 24, 24);
	}

	public void renderTooltip(MatrixStack matrices, int mouseX, int mouseY) {
		tooltipSupplier.onTooltip(this, matrices, mouseX, mouseY);
	}

	@Override
	public void appendNarrations(NarrationMessageBuilder builder) {
		appendDefaultNarrations(builder);
		tooltipSupplier.supply(text -> builder.put(NarrationPart.HINT, text));
	}

	public SpellComponent getSpellComponent() {
		return component;
	}

	public interface TooltipSupplier {
		void onTooltip(SpellComponentWidget spellComponentWidget, MatrixStack matrixStack, int i, int j);

		default void supply(Consumer<Text> consumer) {
		}
	}

	public interface PressAction {
		void onPress(SpellComponentWidget buttonWidget);
	}
}
