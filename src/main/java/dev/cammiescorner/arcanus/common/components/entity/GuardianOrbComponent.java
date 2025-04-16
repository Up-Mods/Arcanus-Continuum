package dev.cammiescorner.arcanus.common.components.entity;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanus.common.entities.magic.EntangledOrb;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class GuardianOrbComponent implements ServerTickingComponent {
	public static final ResourceLocation uUID = Arcanus.id("guardian_orb_mana_lock");
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

		if(!orbId.equals(Util.NIL_UUID) && entity.level() instanceof ServerLevel world) {
			if(world.getEntity(orbId) instanceof EntangledOrb orb && entity == orb.getCaster())
				return;

			setManaLock(Util.NIL_UUID, 0);
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

	public void setManaLock(UUID orbId, int strength) {
		AttributeInstance maxMana = entity.getAttribute(ArcanusEntityAttributes.MAX_MANA.holder());
		AttributeInstance manaLock = entity.getAttribute(ArcanusEntityAttributes.MANA_LOCK.holder());

		if(manaLock != null)
			manaLock.removeModifier(uUID);
		if(maxMana != null && manaLock != null && !orbId.equals(Util.NIL_UUID))
			manaLock.addPermanentModifier(new AttributeModifier(uUID, maxMana.getValue() * (strength * (ArcanusConfig.SpellShapes.EntangledOrbShapeProperties.maximumManaLock / 11)), AttributeModifier.Operation.ADD_VALUE));

		this.orbId = orbId;
		this.strength = strength;
	}
}
