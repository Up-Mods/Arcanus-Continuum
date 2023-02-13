package dev.cammiescorner.arcanuscontinuum.common.components;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;

public class MagicColourComponent implements AutoSyncedComponent {
	private final Entity entity;
	private int colour = 0x68e1ff;

	public MagicColourComponent(Entity entity) {
		this.entity = entity;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		colour = tag.getInt("MagicColour");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putInt("MagicColour", colour);
	}

	public int getColour() {
		return colour;
	}

	public void setColour(int colour) {
		this.colour = colour;
		ArcanusComponents.MAGIC_COLOUR.sync(entity);
	}
}
