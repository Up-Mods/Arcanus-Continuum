package dev.cammiescorner.arcanuscontinuum.common.components.entity;

import dev.cammiescorner.arcanuscontinuum.api.spells.Pattern;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.List;

public class PatternComponent implements AutoSyncedComponent {
	private final LivingEntity entity;
	private final List<Pattern> list = new ArrayList<>();

	public PatternComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		list.clear();
		NbtList nbtList = tag.getList("Pattern", NbtElement.INT_TYPE);

		for(int i = 0; i < nbtList.size(); i++)
			list.add(Pattern.values()[nbtList.getInt(i)]);
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		NbtList nbtList = new NbtList();

		for(Pattern pattern : list)
			nbtList.add(NbtInt.of(pattern.ordinal()));

		tag.put("Pattern", nbtList);
	}

	public List<Pattern> getPattern() {
		return list;
	}

	public void setPattern(List<Pattern> pattern) {
		list.clear();
		list.addAll(pattern);

		if(entity instanceof PlayerEntity)
			ArcanusComponents.PATTERN_COMPONENT.sync(entity);
	}

	public void clearPattern() {
		list.clear();

		if(entity instanceof PlayerEntity)
			ArcanusComponents.PATTERN_COMPONENT.sync(entity);
	}
}
