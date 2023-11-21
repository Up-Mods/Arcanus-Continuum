package dev.cammiescorner.arcanuscontinuum.common.components.entity;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.List;

public class QuestComponent implements AutoSyncedComponent {
	private final PlayerEntity player;
	private final List<Identifier> questIds = List.of(Arcanus.id("start"));
	private long lastCompletedQuestTime;

	public QuestComponent(PlayerEntity player) {
		this.player = player;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		lastCompletedQuestTime = tag.getLong("LastCompletedQuestTime");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putLong("LastCompletedQuestTime", lastCompletedQuestTime);
	}

	public List<Identifier> getQuestIds() {
		return questIds;
	}

	public long getLastCompletedQuestTime() {
		return lastCompletedQuestTime;
	}

	public void setLastCompletedQuestTime(long time) {
		this.lastCompletedQuestTime = time;
		ArcanusComponents.QUEST_COMPONENT.sync(player);
	}
}
