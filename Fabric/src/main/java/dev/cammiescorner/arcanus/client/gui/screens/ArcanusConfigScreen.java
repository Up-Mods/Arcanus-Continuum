package dev.cammiescorner.arcanus.client.gui.screens;

import com.teamresourceful.resourcefulconfig.api.types.ResourcefulConfig;
import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import com.teamresourceful.resourcefulconfig.client.components.base.CustomButton;
import dev.cammiescorner.arcanus.util.TranslationKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class ArcanusConfigScreen extends ConfigScreen {
	public ArcanusConfigScreen(@Nullable ConfigScreen configScreen, ResourcefulConfig config) {
		super(configScreen, config);
	}

	@Override
	protected void init() {
		super.init();

		CustomButton supporterScreen = new CustomButton(90, 16, Component.translatable(TranslationKeys.CONFIG_SUPPORTER_SETTINGS).withStyle(ChatFormatting.AQUA), () -> SupporterScreen.open(this));
		supporterScreen.setX(width - 110);
		supporterScreen.setY(21);

		addRenderableWidget(supporterScreen);
	}
}
