package dev.cammiescorner.arcanus.common.entities.magic;

import dev.cammiescorner.arcanus.api.entities.Targetable;
import dev.cammiescorner.arcanus.api.spells.SpellEffect;
import dev.cammiescorner.arcanus.api.spells.SpellGroup;
import dev.cammiescorner.arcanus.api.spells.SpellShape;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class GuidedShot extends ThrowableItemProjectile implements Targetable {
	private static final EntityDataAccessor<Integer> OWNER_ID = SynchedEntityData.defineId(GuidedShot.class, EntityDataSerializers.INT);
	private List<SpellEffect> effects = new ArrayList<>();
	private List<SpellGroup> spellGroups = new ArrayList<>();
	private ItemStack stack = ItemStack.EMPTY;
	private UUID casterId = Util.NIL_UUID;
	private int groupIndex;
	private double potency;

	public GuidedShot(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
		super(entityType, level);
		setNoGravity(true);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(OWNER_ID, -1);
	}

	@Override
	public void tick() {
		if(getCaster() == null) {
			kill();
			return;
		}

		HitResult result = ArcanusHelper.raycast(getCaster(), distanceTo(getCaster()) * 1.5, true, false);
		Vec3 targetPos = result.getLocation();

		// TODO make it turn smoothly rather than instantly, and add config values for turning speed and projectile speed
		setDeltaMovement(targetPos.subtract(position()).normalize().scale(0.5));
		super.tick();
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		if(!level().isClientSide()) {
			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect(getCaster(), this, level(), result, effects, stack, potency);

			SpellShape.castNext(getCaster(), position(), result.getEntity(), (ServerLevel) level(), stack, spellGroups, groupIndex, potency);

			kill();
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		if(!level().isClientSide()) {
			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect(getCaster(), this, level(), result, effects, stack, potency);

			SpellShape.castNext(getCaster(), position(), this, (ServerLevel) level(), stack, spellGroups, groupIndex, potency);

			super.onHitBlock(result);
			kill();
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		effects.clear();
		spellGroups.clear();

		casterId = tag.getUUID("CasterId");
		stack = ItemStack.parseOptional(registryAccess(), tag.getCompound("ItemStack"));
		groupIndex = tag.getInt("GroupIndex");
		potency = tag.getDouble("Potency");

		ListTag effectList = tag.getList("Effects", Tag.TAG_STRING);
		ListTag groupsList = tag.getList("SpellGroups", Tag.TAG_COMPOUND);

		for(int i = 0; i < effectList.size(); i++)
			effects.add((SpellEffect) ArcanusSpellComponents.REGISTRY.get(ResourceLocation.parse(effectList.getString(i))));
		for(int i = 0; i < groupsList.size(); i++)
			spellGroups.add(SpellGroup.fromNbt(groupsList.getCompound(i)));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		ListTag effectList = new ListTag();
		ListTag groupsList = new ListTag();

		tag.putUUID("CasterId", casterId);
		tag.put("ItemStack", stack.save(registryAccess()));
		tag.putInt("GroupIndex", groupIndex);
		tag.putDouble("Potency", potency);

		for(SpellEffect effect : effects)
			effectList.add(StringTag.valueOf(ArcanusSpellComponents.REGISTRY.getKey(effect).toString()));
		for(SpellGroup group : spellGroups)
			groupsList.add(group.toNbt());

		tag.put("Effects", effectList);
		tag.put("SpellGroups", groupsList);
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	protected Item getDefaultItem() {
		return Items.AIR;
	}

	public LivingEntity getCaster() {
		if(level() instanceof ServerLevel serverWorld && serverWorld.getEntity(casterId) instanceof LivingEntity caster)
			return caster;
		else if(level().isClientSide() && level().getEntity(entityData.get(OWNER_ID)) instanceof LivingEntity caster)
			return caster;

		return null;
	}

	public void setProperties(@Nullable LivingEntity caster, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> groups, int groupIndex, double potency) {
		this.effects.clear();
		this.spellGroups.clear();
		this.effects.addAll(effects);
		this.spellGroups.addAll(groups);

		if(caster != null) {
			this.casterId = caster.getUUID();
			this.entityData.set(OWNER_ID, caster.getId());
			ArcanusHelper.copyMagicColor(this, caster);
		}

		this.stack = stack;
		this.groupIndex = groupIndex;
		this.potency = potency;
	}
}
