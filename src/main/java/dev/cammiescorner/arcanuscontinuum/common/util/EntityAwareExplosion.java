package dev.cammiescorner.arcanuscontinuum.common.util;

import net.minecraft.entity.Entity;

import java.util.List;

public interface EntityAwareExplosion {

	List<Entity> getAffectedEntities();
}
