package dev.cammiescorner.arcanus.compat;

import com.teamresourceful.resourcefulconfig.api.types.ResourcefulConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.client.gui.screens.ArcanusConfigScreen;

public class ModMenuCompat implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			ResourcefulConfig config = Arcanus.configurator.getConfig(ArcanusConfig.class);

			if(config == null)
				return null;

			return new ArcanusConfigScreen(null, config);
		};
	}
}
