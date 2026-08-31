package dev.cammiescorner.arcanus.component.entity;

import dev.cammiescorner.arcanus.api.spell.Pattern;
import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import java.util.ArrayList;
import java.util.List;

public class PatternComponent implements AutoSyncedComponent {
	private final LivingEntity entity;
	private final List<Pattern> list = new ArrayList<>();

	public PatternComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void readData(ValueInput readView) {
		list.clear();
		list.addAll(readView.read("Pattern", Pattern.CODEC.listOf()).orElse(List.of()));
	}

	@Override
	public void writeData(ValueOutput writeView) {
		writeView.store("Pattern", Pattern.CODEC.listOf(), list);
	}

	public List<Pattern> getPattern() {
		return list;
	}

	public void setPattern(List<Pattern> pattern) {
		list.clear();
		list.addAll(pattern);

		if(entity instanceof Player)
			ArcanusComponents.PATTERN_COMPONENT.sync(entity);
	}

	public void clearPattern() {
		list.clear();

		if(entity instanceof Player)
			ArcanusComponents.PATTERN_COMPONENT.sync(entity);
	}
}
