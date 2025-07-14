package dev.cammiescorner.arcanus.common.entity.living;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

// TODO make renderer that pulls from default skins & give it the armor
public class Cultist extends Mob {
	public Cultist(EntityType<? extends Mob> entityType, Level level) {
		super(entityType, level);
	}
}
