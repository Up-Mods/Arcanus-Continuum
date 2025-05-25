package dev.cammiescorner.arcanus.client.gui.screens;

import com.teamresourceful.resourcefulconfig.api.types.ResourcefulConfig;
import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import com.teamresourceful.resourcefulconfig.client.components.base.CustomButton;
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

		CustomButton supporterScreen = new CustomButton(90, 16, Component.translatable("config.arcanus.supporter_settings").withStyle(ChatFormatting.AQUA), () -> SupporterScreen.open(this));
		supporterScreen.setX(width - 110);
		supporterScreen.setY(21);

		addRenderableWidget(supporterScreen);
	}
}
