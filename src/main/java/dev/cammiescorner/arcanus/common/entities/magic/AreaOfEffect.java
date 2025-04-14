package dev.cammiescorner.arcanus.common.entities.magic;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.entities.Targetable;
import dev.cammiescorner.arcanus.api.spells.SpellEffect;
import dev.cammiescorner.arcanus.api.spells.SpellGroup;
import dev.cammiescorner.arcanus.api.spells.SpellShape;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class AreaOfEffect extends Entity implements Targetable {
	private UUID casterId = Util.NIL_UUID;
	private ItemStack stack = ItemStack.EMPTY;
	private List<SpellEffect> effects = new ArrayList<>();
	private List<SpellGroup> spellGroups = new ArrayList<>();
	private int groupIndex;
	private double potency;
	private int trueAge;

	public AreaOfEffect(EntityType<?> variant, Level world) {
		super(variant, world);
	}

	@Override
	public void tick() {
		if(!level().isClientSide() && (getCaster() == null || !getCaster().isAlive())) {
			kill();
			return;
		}

		List<AreaOfEffect> list = level().getEntitiesOfClass(AreaOfEffect.class, getBoundingBox(), EntitySelector.ENTITY_STILL_ALIVE);

		if(!list.isEmpty()) {
			int i = level().getGameRules().getInt(GameRules.RULE_MAX_ENTITY_CRAMMING);

			if(i > 0 && list.size() > i - 1) {
				int j = 0;

				for(AreaOfEffect ignored : list)
					++j;

				if(j > i - 1) {
					kill();
					return;
				}
			}
		}

		if(!level().isClientSide()) {
			int baseLifeSpan = ArcanusConfig.SpellShapes.AOEShapeProperties.baseLifeSpan;
			int timesToApplyEffects = ArcanusConfig.SpellShapes.AOEShapeProperties.timesToApplyEffects;
			int timesToCastNextShape = timesToCastNextShape();
			int actualLifeSpan = (int) (baseLifeSpan * 0.9);

			if(trueAge > 0) {
				if(trueAge <= actualLifeSpan) {
					if(trueAge % (actualLifeSpan / timesToApplyEffects) == 0) {
						AABB box = new AABB(-2, 0, -2, 2, 2.5, 2).move(position());

						for(SpellEffect effect : new HashSet<>(effects)) {
							if(effect.singleCastOnly())
								continue;

							level().getEntitiesOfClass(Entity.class, box, entity -> entity.isAlive() && !entity.isSpectator() && entity instanceof Targetable targetable && targetable.arcanus$canBeTargeted()).forEach(entity -> {
								effect.effect(getCaster(), this, level(), new EntityHitResult(entity), effects, stack, potency);
							});
						}
					}

					if(trueAge == actualLifeSpan) {
						for(int i = 0; i < timesToCastNextShape; i++) {
							SpellShape.castNext(getCaster(), position(), this, (ServerLevel) level(), stack, spellGroups, groupIndex, potency);
							setYRot(getYRot() + (360f / timesToCastNextShape));
						}

						for(SpellEffect effect : new HashSet<>(effects))
							if(effect.singleCastOnly())
								effect.effect(getCaster(), this, level(), new EntityHitResult(this), effects, stack, potency);
					}
				}
			}

			if(trueAge >= baseLifeSpan)
				kill();
		}

		super.tick();
		trueAge++;
	}

	@Override
	protected void defineSynchedData() {

	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
		effects.clear();
		spellGroups.clear();

		casterId = tag.getUUID("CasterId");
		stack = ItemStack.of(tag.getCompound("ItemStack"));
		groupIndex = tag.getInt("GroupIndex");
		potency = tag.getDouble("Potency");
		trueAge = tag.getInt("TrueAge");

		ListTag effectList = tag.getList("Effects", Tag.TAG_STRING);
		ListTag groupsList = tag.getList("SpellGroups", Tag.TAG_COMPOUND);

		for(int i = 0; i < effectList.size(); i++)
			effects.add((SpellEffect) Arcanus.SPELL_COMPONENTS.get(new ResourceLocation(effectList.getString(i))));
		for(int i = 0; i < groupsList.size(); i++)
			spellGroups.add(SpellGroup.fromNbt(groupsList.getCompound(i)));
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
		ListTag effectList = new ListTag();
		ListTag groupsList = new ListTag();

		tag.putUUID("CasterId", casterId);
		tag.put("ItemStack", stack.save(new CompoundTag()));
		tag.putInt("GroupIndex", groupIndex);
		tag.putDouble("Potency", potency);
		tag.putInt("TrueAge", trueAge);

		for(SpellEffect effect : effects)
			effectList.add(StringTag.valueOf(Arcanus.SPELL_COMPONENTS.getKey(effect).toString()));
		for(SpellGroup group : spellGroups)
			groupsList.add(group.toNbt());

		tag.put("Effects", effectList);
		tag.put("SpellGroups", groupsList);
	}

	public UUID getCasterId() {
		return casterId;
	}

	private LivingEntity getCaster() {
		if(level() instanceof ServerLevel serverWorld && serverWorld.getEntity(casterId) instanceof LivingEntity caster)
			return caster;

		return null;
	}

	public int getTrueAge() {
		return trueAge;
	}

	public void setProperties(UUID casterId, Vec3 pos, ItemStack stack, List<SpellEffect> effects, double potency, List<SpellGroup> groups, int groupIndex) {
		HitResult hitResult = level().clip(new ClipContext(pos, pos.add(0, -64, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, this));
		setPos(pos);

		// TODO clips into ground by one pixel for some reason?
//		if(hitResult.getType() != HitResult.Type.MISS)
//			setPos(hitResult.getLocation());

		setYRot(random.nextFloat() * 360f);
		this.casterId = casterId;
		this.stack = stack;
		this.effects = effects;
		this.spellGroups = groups;
		this.groupIndex = groupIndex;
		this.potency = potency;
		this.trueAge = random.nextInt(3);
	}

	private int timesToCastNextShape() {
		if(spellGroups.size() <= groupIndex + 1)
			return 0;
		else if(spellGroups.get(groupIndex + 1).shape().singleCastOnly())
			return 1;

		return ArcanusConfig.SpellShapes.AOEShapeProperties.timesToCastNextShape;
	}
}
