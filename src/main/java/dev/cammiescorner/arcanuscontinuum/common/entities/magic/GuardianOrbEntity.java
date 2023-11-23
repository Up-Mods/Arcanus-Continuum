package dev.cammiescorner.arcanuscontinuum.common.entities.magic;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.api.entities.Targetable;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class GuardianOrbEntity extends Entity implements Targetable {
	private static final TrackedData<Integer> OWNER_ID = DataTracker.registerData(GuardianOrbEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Integer> TARGET_ID = DataTracker.registerData(GuardianOrbEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private final List<SpellEffect> effects = new ArrayList<>();
	private final List<SpellGroup> groups = new ArrayList<>();
	private UUID casterId = Util.NIL_UUID;
	private UUID targetId = Util.NIL_UUID;
	private ItemStack stack = ItemStack.EMPTY;
	private int groupIndex = 0;
	private double potency = 1F;

	public GuardianOrbEntity(EntityType<?> variant, World world) {
		super(variant, world);
		this.noClip = true;
	}

	@Override
	protected void initDataTracker() {
		dataTracker.startTracking(OWNER_ID, -1);
		dataTracker.startTracking(TARGET_ID, -1);
	}

	@Override
	public void tick() {
		if((getCaster() == null || getTarget() == null || getCaster().squaredDistanceTo(getTarget()) > 32 * 32)) {
			kill();
			return;
		}

		if(!getWorld().isClient()) {
			if(dataTracker.get(TARGET_ID) == -1 && getTarget() != null)
				dataTracker.set(TARGET_ID, getTarget().getId());
		}

		Vec3d rotation = getTarget().getRotationVec(1F).multiply(getTarget().getWidth() / 2 + 0.5).rotateY((float) Math.toRadians(105));
		Vec3d targetPos = getTarget().getPos().add(rotation.getX(), getTarget().getHeight(), rotation.getZ());
		Vec3d direction = targetPos.subtract(getPos());
		move(MovementType.SELF, direction.multiply(0.25f));

		if(age % 8 == 0) {
			Vec3d vel = (direction.lengthSquared() <= 1 ? direction : direction.normalize()).multiply(0.125f);

			getWorld().addParticle(ParticleTypes.END_ROD, getX(), getY() + getHeight() / 2, getZ(), vel.getX(), vel.getY(), vel.getZ());
		}

		if(age % 100 == 0) {
			EntityHitResult target = new EntityHitResult(getTarget());

			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect(getCaster(), this, getWorld(), target, effects, stack, potency);
		}

		super.tick();
	}

	@Override
	public boolean collides() {
		return true;
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		kill();
		return true;
	}

	@Override
	public void kill() {
		if(!getWorld().isClient() && getCaster() != null) {
			EntityAttributeInstance inst = getCaster().getAttributeInstance(ArcanusEntityAttributes.MANA_LOCK.get());

			if(inst != null)
				inst.removeModifier(casterId);

			SpellShape.castNext(getCaster(), getPos(), this, (ServerWorld) getWorld(), stack, groups, groupIndex, potency);
		}

		super.kill();
	}

	@Override
	public boolean doesRenderOnFire() {
		return false;
	}

	@Override
	public boolean isFireImmune() {
		return true;
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound tag) {
		effects.clear();
		groups.clear();

		casterId = tag.getUuid("CasterId");
		targetId = tag.getUuid("TargetId");
		stack = ItemStack.fromNbt(tag.getCompound("ItemStack"));
		groupIndex = tag.getInt("GroupIndex");
		potency = tag.getDouble("Potency");

		NbtList effectList = tag.getList("Effects", NbtElement.STRING_TYPE);
		NbtList groupsList = tag.getList("SpellGroups", NbtElement.COMPOUND_TYPE);

		for(int i = 0; i < effectList.size(); i++)
			effects.add((SpellEffect) Arcanus.SPELL_COMPONENTS.get(new Identifier(effectList.getString(i))));
		for(int i = 0; i < groupsList.size(); i++)
			groups.add(SpellGroup.fromNbt(groupsList.getCompound(i)));
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound tag) {
		NbtList effectList = new NbtList();
		NbtList groupsList = new NbtList();

		tag.putUuid("CasterId", casterId);
		tag.putUuid("TargetId", targetId);
		tag.put("ItemStack", stack.writeNbt(new NbtCompound()));
		tag.putInt("GroupIndex", groupIndex);
		tag.putDouble("Potency", potency);

		for(SpellEffect effect : effects)
			effectList.add(NbtString.of(Arcanus.SPELL_COMPONENTS.getId(effect).toString()));
		for(SpellGroup group : groups)
			groupsList.add(group.toNbt());

		tag.put("Effects", effectList);
		tag.put("SpellGroups", groupsList);
	}

	@Override
	public boolean shouldRender(double sqDistance) {
		return sqDistance <= 64 * 64;
	}

	public int getColour() {
		return ArcanusComponents.getColour(this);
	}

	public void setColour(int colour) {
		ArcanusComponents.setColour(this, colour);
	}

	public LivingEntity getCaster() {
		if(getWorld() instanceof ServerWorld serverWorld && serverWorld.getEntity(casterId) instanceof LivingEntity caster)
			return caster;
		else if(getWorld().isClient() && getWorld().getEntityById(dataTracker.get(OWNER_ID)) instanceof LivingEntity caster)
			return caster;

		return null;
	}

	public LivingEntity getTarget() {
		if(getWorld() instanceof ServerWorld serverWorld && serverWorld.getEntity(targetId) instanceof LivingEntity target)
			return target;
		else if(getWorld().isClient() && getWorld().getEntityById(dataTracker.get(TARGET_ID)) instanceof LivingEntity target)
			return target;

		return null;
	}

	public void setProperties(@Nullable LivingEntity caster, LivingEntity target, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> groups, int groupIndex, int colour, double potency) {
		this.effects.clear();
		this.groups.clear();
		this.effects.addAll(effects);
		this.groups.addAll(groups);
		setColour(colour);

		if(caster != null) {
			this.casterId = caster.getUuid();
			this.dataTracker.set(OWNER_ID, caster.getId());
			EntityAttributeInstance maxMana = caster.getAttributeInstance(ArcanusEntityAttributes.MAX_MANA.get());
			EntityAttributeInstance manaLock = caster.getAttributeInstance(ArcanusEntityAttributes.MANA_LOCK.get());

			if(maxMana != null && manaLock != null) {
				double maximumManaLock = 0.9;

				manaLock.addPersistentModifier(new EntityAttributeModifier(casterId, "Orb Mana Lock", maxMana.getValue() * (effects.size() * (maximumManaLock / 11)), EntityAttributeModifier.Operation.ADDITION));
			}
		}

		this.targetId = target.getUuid();
		this.dataTracker.set(TARGET_ID, target.getId());
		this.stack = stack;
		this.groupIndex = groupIndex;
		this.potency = potency;
	}
}
