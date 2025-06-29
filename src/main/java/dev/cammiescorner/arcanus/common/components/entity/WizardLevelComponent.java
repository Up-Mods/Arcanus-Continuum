package dev.cammiescorner.arcanus.common.components.entity;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusCriteriaTriggers;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class WizardLevelComponent implements AutoSyncedComponent {
	public static final int MAX_LEVEL = 10;
	public static final ResourceLocation MANA_MODIFIER = Arcanus.id("wizard_level_mana_modifier");
	private final LivingEntity entity;
	private int level = 0;

	public WizardLevelComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		level = Mth.clamp(tag.getInt("WizardLevel"), 0, getMaxLevel());

		if(entity instanceof ServerPlayer serverPlayer) {
			ArcanusCriteriaTriggers.WIZARD_LEVEL_CRITERION.get().trigger(serverPlayer);
		}
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		tag.putInt("WizardLevel", level);
	}

	public int getLevel() {
		return level;
	}

	public int getMaxLevel() {
		return MAX_LEVEL;
	}

	public void setLevel(int level) {
		this.level = Mth.clamp(level, 0, getMaxLevel());

		ArcanusComponents.WIZARD_LEVEL_COMPONENT.sync(entity);
		if(entity instanceof ServerPlayer serverPlayer) {
			ArcanusCriteriaTriggers.WIZARD_LEVEL_CRITERION.get().trigger(serverPlayer);
		}
	}
}
