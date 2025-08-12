package dev.cammiescorner.arcanus.common.component.entity;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.entity.magic.MagicOrb;
import dev.cammiescorner.arcanus.common.registry.ArcanusAttributes;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.UUID;

public class GuardianOrbComponent implements ServerTickingComponent {
	public static final ResourceLocation uUID = Arcanus.id("guardian_orb_arcana_lock");
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
			setArcanaLock(orbId, strength);

		if(!orbId.equals(Util.NIL_UUID) && entity.level() instanceof ServerLevel world) {
			if(world.getEntity(orbId) instanceof MagicOrb orb && entity == orb.getCaster())
				return;

			setArcanaLock(Util.NIL_UUID, 0);
		}
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		orbId = tag.getUUID("OrbId");
		strength = tag.getInt("Strength");
		dirty = true;
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		tag.putUUID("OrbId", orbId);
		tag.putInt("Strength", strength);
	}

	public UUID getOrbId() {
		return orbId;
	}

	public void setArcanaLock(UUID orbId, int strength) {
		AttributeInstance arcanaLock = entity.getAttribute(ArcanusAttributes.ARCANA_LOCK.holder());

		// TODO redo all this mess
//		if(arcanaLock != null)
//			arcanaLock.removeModifier(uUID);
//		if(maxMana != null && arcanaLock != null && !orbId.equals(Util.NIL_UUID))
//			arcanaLock.addPermanentModifier(new AttributeModifier(uUID, maxMana.getValue() * (strength * (ArcanusConfig.SpellShapes.EntangledOrbShapeProperties.maximumManaLock / 11)), AttributeModifier.Operation.ADD_VALUE));

		this.orbId = orbId;
		this.strength = strength;
	}
}
