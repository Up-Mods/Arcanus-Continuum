package dev.cammiescorner.arcanuscontinuum.common.entities;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class OpossumEntity extends TameableEntity {
	public OpossumEntity(EntityType<? extends TameableEntity> entityType, World world) {
		super(entityType, world);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return TameableEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 15).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.425);
	}

	@Nullable
	@Override
	public OpossumEntity createChild(ServerWorld world, PassiveEntity entity) {
		OpossumEntity opossumEntity = ArcanusEntities.OPOSSUM.create(world);
		UUID uUID = getOwnerUuid();

		if(uUID != null && opossumEntity != null) {
			opossumEntity.setOwnerUuid(uUID);
			opossumEntity.setTamed(true);
		}

		return opossumEntity;
	}
}
