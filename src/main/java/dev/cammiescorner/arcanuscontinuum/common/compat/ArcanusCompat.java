package dev.cammiescorner.arcanuscontinuum.common.compat;

import org.quiltmc.loader.api.QuiltLoader;

import java.util.function.Supplier;

public enum ArcanusCompat {
	PEHKUI("pehkui"),
	FIRST_PERSON("firstperson");

	private final String modid;
	private final boolean enabled;

	ArcanusCompat(String modid) {
		this.modid = modid;
		this.enabled = QuiltLoader.isModLoaded(modid);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void ifEnabled(Supplier<Runnable> runnable) {
		if(isEnabled()) {
			runnable.get().run();
		}
	}

	public void orThrow() {
		if(!isEnabled()) {
			throw new IllegalStateException("[ArcanusCompat] Error: mod " + modid + " is not loaded!");
		}
	}
}
