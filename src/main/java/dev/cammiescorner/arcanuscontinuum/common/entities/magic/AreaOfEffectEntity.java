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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class AreaOfEffectEntity extends Entity {
	private UUID casterId = Util.NIL_UUID;
	private ItemStack stack = ItemStack.EMPTY;
	private List<SpellEffect> effects = new ArrayList<>();
	private List<SpellGroup> spellGroups = new ArrayList<>();
	private int groupIndex;
	private double potency;
	private int trueAge;

	public AreaOfEffectEntity(EntityType<?> variant, World world) {
		super(variant, world);
	}

	@Override
	public void tick() {
		if(!world.isClient()) {
			if(trueAge % 20 == 0 && trueAge <= 80 && trueAge > 0) {
				Box box = new Box(-2, 0, -2, 2, 2.5, 2).offset(getPos());

				world.getEntitiesByClass(LivingEntity.class, box, livingEntity -> livingEntity.isAlive() && !livingEntity.isSpectator()).forEach(entity -> {
					for(SpellEffect effect : new HashSet<>(effects))
						effect.effect(getCaster(), world, new EntityHitResult(entity), effects, stack, potency);
				});

				SpellShape.castNext(getCaster(), getPos(), this, (ServerWorld) world, stack, spellGroups, groupIndex, potency);
				setYaw(getYaw() + 90);
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

	private LivingEntity getCaster() {
		if(world instanceof ServerWorld serverWorld && serverWorld.getEntity(casterId) instanceof LivingEntity caster)
			return caster;

		return null;
	}

	public int getColour() {
		return ArcanusComponents.getColour(this);
	}

	public int getTrueAge() {
		return trueAge;
	}

	public void setProperties(LivingEntity caster, Vec3d pos, ItemStack stack, List<SpellEffect> effects, double potency, List<SpellGroup> groups, int groupIndex, int colour) {
		setPos(pos.getX(), pos.getY(), pos.getZ());
		setYaw(caster.getYaw());
		setPitch(-5);
		this.casterId = caster.getUuid();
		this.stack = stack;
		this.effects = effects;
		this.spellGroups = groups;
		this.groupIndex = groupIndex;
		this.potency = potency;
		ArcanusComponents.setColour(this, colour);
		this.trueAge = random.nextInt(3);
	}
}
