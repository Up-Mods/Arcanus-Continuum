package dev.cammiescorner.arcanuscontinuum.common.compat;

import org.quiltmc.loader.api.QuiltLoader;

import java.util.function.Supplier;

public enum ArcanusCompat {
	FIRST_PERSON("firstperson"),
	PATCHOULI("patchouli"),
	PEHKUI("pehkui");

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

	public <T> T orElse(Supplier<Supplier<T>> supplier, T defaultValue) {
		return isEnabled() ? supplier.get().get() : defaultValue;
	}

	public <T> T orElseGet(Supplier<Supplier<T>> supplier, Supplier<T> defaultValue) {
		return isEnabled() ? supplier.get().get() : defaultValue.get();
	}

	public void orThrow() {
		if(!isEnabled()) {
			throw new IllegalStateException("[ArcanusCompat] Error: mod " + modid + " is not loaded!");
		}
	}
}
