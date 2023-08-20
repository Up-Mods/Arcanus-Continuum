package dev.cammiescorner.arcanuscontinuum.common.entities.magic;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.*;

public class AreaOfEffectEntity extends Entity {
	private UUID casterId = Util.NIL_UUID;
	private ItemStack stack = ItemStack.EMPTY;
	private List<SpellEffect> effects = new ArrayList<>();
	private List<SpellGroup> spellGroups = new ArrayList<>();
	private int groupIndex;
	private double potency;
	private int trueAge;
	private boolean isFocused = true;

	public AreaOfEffectEntity(EntityType<?> variant, World world) {
		super(variant, world);
	}

	@Override
	public void tick() {
		if(!world.isClient() && (getCaster() == null || !getCaster().isAlive()))
			kill();

		List<AreaOfEffectEntity> list = world.getEntitiesByClass(AreaOfEffectEntity.class, getBoundingBox(), EntityPredicates.VALID_ENTITY);

		if(!list.isEmpty()) {
			int i = world.getGameRules().getInt(GameRules.MAX_ENTITY_CRAMMING);

			if(i > 0 && list.size() > i - 1) {
				int j = 0;

				for(AreaOfEffectEntity ignored : list)
					++j;

				if(j > i - 1) {
					kill();
					return;
				}
			}
		}

		if(!world.isClient()) {
			if(trueAge <= 90 && trueAge > 0) {
				if(trueAge % 30 == 0) {
					Box box = new Box(-2, 0, -2, 2, 2.5, 2).offset(getPos());

					for(SpellEffect effect : new HashSet<>(effects)) {
						if(effect.shouldTriggerOnceOnExplosion())
							continue;

						world.getEntitiesByClass(LivingEntity.class, box, livingEntity -> livingEntity.isAlive() && !livingEntity.isSpectator()).forEach(entity -> {
							effect.effect(getCaster(), this, world, new EntityHitResult(entity), effects, stack, potency);
						});
					}

					SpellShape.castNext(getCaster(), getPos(), this, (ServerWorld) world, stack, spellGroups, groupIndex, potency);

					if(!isFocused)
						setYaw(getYaw() + 110 + random.nextInt(21));
				}

				if(trueAge % 50 == 0) {
					for(SpellEffect effect : new HashSet<>(effects))
						if(effect.shouldTriggerOnceOnExplosion())
							effect.effect(getCaster(), this, world, new EntityHitResult(this), effects, stack, potency);
				}
			}

			if(trueAge >= 100)
				kill();
		}

		super.tick();
		trueAge++;
	}

	@Override
	protected void initDataTracker() {

	}

	@Override
	public boolean doesRenderOnFire() {
		return false;
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound tag) {
		effects.clear();
		spellGroups.clear();

		casterId = tag.getUuid("CasterId");
		stack = ItemStack.fromNbt(tag.getCompound("ItemStack"));
		groupIndex = tag.getInt("GroupIndex");
		potency = tag.getDouble("Potency");
		trueAge = tag.getInt("TrueAge");

		NbtList effectList = tag.getList("Effects", NbtElement.STRING_TYPE);
		NbtList groupsList = tag.getList("SpellGroups", NbtElement.COMPOUND_TYPE);

		for(int i = 0; i < effectList.size(); i++)
			effects.add((SpellEffect) Arcanus.SPELL_COMPONENTS.get(new Identifier(effectList.getString(i))));
		for(int i = 0; i < groupsList.size(); i++)
			spellGroups.add(SpellGroup.fromNbt(groupsList.getCompound(i)));
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound tag) {
		NbtList effectList = new NbtList();
		NbtList groupsList = new NbtList();

		tag.putUuid("CasterId", casterId);
		tag.put("ItemStack", stack.writeNbt(new NbtCompound()));
		tag.putInt("GroupIndex", groupIndex);
		tag.putDouble("Potency", potency);
		tag.putInt("TrueAge", trueAge);

		for(SpellEffect effect : effects)
			effectList.add(NbtString.of(Arcanus.SPELL_COMPONENTS.getId(effect).toString()));
		for(SpellGroup group : spellGroups)
			groupsList.add(group.toNbt());

		tag.put("Effects", effectList);
		tag.put("SpellGroups", groupsList);
	}

	public UUID getCasterId() {
		return casterId;
	}

	private LivingEntity getCaster() {
		if(world instanceof ServerWorld serverWorld && serverWorld.getEntity(casterId) instanceof LivingEntity caster)
			return caster;

		return null;
	}

	public int getColour() {
		return ArcanusComponents.getColour(this);
	}

	public void setColour(int colour) {
		ArcanusComponents.setColour(this, colour);
	}

	public int getTrueAge() {
		return trueAge;
	}

	public void setProperties(UUID casterId, Entity sourceEntity, Vec3d pos, ItemStack stack, List<SpellEffect> effects, double potency, List<SpellGroup> groups, int groupIndex, int colour) {
		setPos(pos.getX(), pos.getY(), pos.getZ());
		setYaw(sourceEntity.getYaw());
		setPitch(sourceEntity.getPitch());
		this.casterId = casterId;
		this.isFocused = sourceEntity instanceof AreaOfEffectEntity aoe ? aoe.isFocused : sourceEntity.getUuid().equals(casterId) && sourceEntity.isSneaking();
		this.stack = stack;
		this.effects = effects;
		this.spellGroups = groups;
		this.groupIndex = groupIndex;
		this.potency = potency;
		ArcanusComponents.setColour(this, colour);
		this.trueAge = random.nextInt(3);
	}
}
