package dev.cammiescorner.arcanuscontinuum.common.entities.magic;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.entities.Targetable;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class AggressorbEntity extends Entity implements Targetable {
	private static final TrackedData<Integer> OWNER_ID = DataTracker.registerData(AggressorbEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Integer> TARGET_ID = DataTracker.registerData(AggressorbEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private final List<SpellEffect> effects = new ArrayList<>();
	private final List<SpellGroup> groups = new ArrayList<>();
	private UUID casterId = Util.NIL_UUID;
	private UUID targetId = Util.NIL_UUID;
	private ItemStack stack = ItemStack.EMPTY;
	private int groupIndex = 0;
	private double potency = 1F;
	private boolean boundToTarget = true;

	public AggressorbEntity(EntityType<?> variant, World world) {
		super(variant, world);
		noClip = true;
	}

	@Override
	protected void initDataTracker() {
		dataTracker.startTracking(OWNER_ID, -1);
		dataTracker.startTracking(TARGET_ID, -1);
	}

	@Override
	public void tick() {
		if(getCaster() == null || getTarget() == null) {
			kill();
			return;
		}

		if(!getWorld().isClient()) {
			if(dataTracker.get(TARGET_ID) == -1 && getTarget() != null)
				dataTracker.set(TARGET_ID, getTarget().getId());
		}

		if(isBoundToTarget()) {
			int orbCount = ArcanusComponents.orbCount(getTarget());
			int orbIndex = ArcanusComponents.orbIndex(getTarget(), this) + 1;
			double angle = Math.toRadians(360d / orbCount * orbIndex);
			double cosYaw = Math.cos(Math.toRadians(-getTarget().bodyYaw));
			double sinYaw = Math.sin(Math.toRadians(-getTarget().bodyYaw));
			double radius = getTarget().getHeight() / 1.5;
			double rotXZ = Math.sin(getTarget().age * 0.2 + angle) * radius;
			double rotY = Math.cos(getTarget().age * 0.2 + angle) * radius;
			Vec3d bodyYaw = new Vec3d(sinYaw, 1, cosYaw);
			Vec3d offset = new Vec3d(sinYaw, 0, cosYaw).multiply(-0.75);
			Vec3d imInSpainWithoutTheA = bodyYaw.multiply(rotXZ, rotY, rotXZ).rotateY((float) Math.toRadians(90));
			Vec3d targetPos = getTarget().getPos().add(0, radius, 0).add(imInSpainWithoutTheA).add(offset);
			Vec3d direction = targetPos.subtract(getPos());
			move(MovementType.SELF, direction);
		}
		else {
			HitResult hitResult = getHitResult();

			if(hitResult != null) {
				for(SpellEffect effect : new HashSet<>(effects))
					effect.effect(getCaster(), this, getWorld(), hitResult, effects, stack, potency);

				if(!getWorld().isClient())
					SpellShape.castNext(getCaster(), getTarget().getPos(), hitResult.getType() == HitResult.Type.ENTITY ? ((EntityHitResult) hitResult).getEntity() : this, (ServerWorld) getWorld(), stack, groups, groupIndex, potency);

				kill();
			}
		}

		super.tick();
	}

	@Override
	public boolean collides() {
		return true;
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		fire(getTarget().getPos().add(0, getHeight() * 0.5, 0).subtract(getPos()), 3f);
		return true;
	}

	@Override
	public void kill() {
		if(!getWorld().isClient() && getTarget() != null)
			ArcanusComponents.removeOrbFromEntity(getTarget(), getUuid());

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
		boundToTarget = tag.getBoolean("BoundToTarget");

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
		tag.putBoolean("BoundToTarget", boundToTarget);

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

	public boolean isBoundToTarget() {
		return boundToTarget;
	}

	public void setBoundToTarget(boolean boundToTarget) {
		this.boundToTarget = boundToTarget;
	}

	public HitResult getHitResult() {
		if(noClip)
			return null;

		EntityHitResult entityTarget = ProjectileUtil.getEntityCollision(getWorld(), this, getTarget().getPos(), getPos(), getBoundingBox().offset(getPos()), entity -> !entity.isSpectator() && entity.isAlive() && entity instanceof Targetable);
		HitResult blockTarget = getWorld().raycast(new RaycastContext(getTarget().getPos(), getPos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));

		return entityTarget != null && entityTarget.getType() != HitResult.Type.MISS ? entityTarget : blockTarget;
	}

	public void fire(Vec3d direction, float speed) {
		Vec3d vel = direction.normalize().multiply(speed);

		setBoundToTarget(false);
		setVelocity(vel);
		setYaw((float) Math.toDegrees(MathHelper.atan2(vel.getX(), vel.getZ())));
		setPitch((float) Math.toDegrees(MathHelper.atan2(vel.getY(), vel.horizontalLength())));

		velocityModified = true;
		prevYaw = getYaw();
		prevPitch = getPitch();
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
		}

		ArcanusComponents.addOrbToEntity(target, getUuid());
		setBoundToTarget(true);

		this.targetId = target.getUuid();
		this.dataTracker.set(TARGET_ID, target.getId());
		this.stack = stack;
		this.groupIndex = groupIndex;
		this.potency = potency;
	}
}
