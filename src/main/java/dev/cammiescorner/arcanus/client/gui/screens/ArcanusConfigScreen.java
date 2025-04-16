package dev.cammiescorner.arcanus.client.gui.screens;

import com.teamresourceful.resourcefulconfig.api.types.ResourcefulConfig;
import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import org.jetbrains.annotations.Nullable;

public class ArcanusConfigScreen extends ConfigScreen {
	public ArcanusConfigScreen(@Nullable ConfigScreen configScreen, ResourcefulConfig config) {
		super(configScreen, config);
	}

	// TODO figure out footer stuff once more
//	@Override
//	protected void createFooter() {
//		super.createFooter();
//
//		addRenderableWidget(Button.builder(Component.translatable("config.arcanus.supporter_settings").withStyle(ChatFormatting.AQUA), buttonWidget -> SupporterScreen.open(this)).bounds(width / 2 - 55, height - 27, 110, 20).build());
//	}
}
