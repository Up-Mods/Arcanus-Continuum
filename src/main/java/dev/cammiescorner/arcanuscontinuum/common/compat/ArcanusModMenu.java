package dev.cammiescorner.arcanuscontinuum.common.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.cammiescorner.arcanuscontinuum.Arcanus;

public class ArcanusModMenu implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> ArcanusConfig.getScreen(parent, Arcanus.MOD_ID);
	}
}
