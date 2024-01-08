package dev.cammiescorner.arcanuscontinuum.common.components.entity;

import dev.cammiescorner.arcanuscontinuum.ArcanusConfig;
import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.GuardianOrbEntity;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;

import java.util.UUID;

public class GuardianOrbComponent implements ServerTickingComponent {
	public static final UUID uUID = UUID.fromString("ef75422a-3096-4111-965f-9526c3ed55e0");
	private final LivingEntity entity;
	private UUID orbId = Util.NIL_UUID;
	private int strength = 0;
	private boolean dirty = false;

	public GuardianOrbComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {
		if(dirty)
			setManaLock(orbId, strength);

		if(!orbId.equals(Util.NIL_UUID) && entity.getWorld() instanceof ServerWorld world) {
			if(world.getEntity(orbId) instanceof GuardianOrbEntity orb && entity == orb.getCaster())
				return;

			setManaLock(Util.NIL_UUID, 0);
		}
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		orbId = tag.getUuid("OrbId");
		strength = tag.getInt("Strength");
		dirty = true;
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putUuid("OrbId", orbId);
		tag.putInt("Strength", strength);
	}

	public UUID getOrbId() {
		return orbId;
	}

	public void setManaLock(UUID orbId, int strength) {
		EntityAttributeInstance maxMana = entity.getAttributeInstance(ArcanusEntityAttributes.MAX_MANA.get());
		EntityAttributeInstance manaLock = entity.getAttributeInstance(ArcanusEntityAttributes.MANA_LOCK.get());

		if(manaLock != null)
			manaLock.removeModifier(uUID);
		if(maxMana != null && manaLock != null && !orbId.equals(Util.NIL_UUID))
			manaLock.addPersistentModifier(new EntityAttributeModifier(uUID, "Orb Mana Lock", maxMana.getValue() * (strength * (ArcanusConfig.SpellShapes.GuardianOrbShapeProperties.maximumManaLock / 11)), EntityAttributeModifier.Operation.ADDITION));

		this.orbId = orbId;
		this.strength = strength;
	}
}
