package dev.cammiescorner.arcanuscontinuum.common.util;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.util.Identifier;

public enum WorkbenchMode {
	SPELLBINDING(Arcanus.id("textures/gui/arcane_workbench_spellbinding.png")), CUSTOMIZE(Arcanus.id("textures/gui/arcane_workbench_customize.png"));

	final Identifier texture;

	WorkbenchMode(Identifier texture) {
		this.texture = texture;
	}

	public Identifier getTexture() {
		return texture;
	}
}
