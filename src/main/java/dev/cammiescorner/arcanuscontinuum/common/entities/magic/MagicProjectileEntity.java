package dev.cammiescorner.arcanuscontinuum.common.entities.magic;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MagicProjectileEntity extends PersistentProjectileEntity {
	private ItemStack stack = ItemStack.EMPTY;
	private List<SpellEffect> effects = new ArrayList<>();
	private List<SpellGroup> spellGroups = new ArrayList<>();
	private int groupIndex;
	private double potency;

	public MagicProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
//		if(getShape() == ArcanusSpellComponents.PROJECTILE && age >= 20)
//			kill();

		super.tick();
	}

	@Override
	protected void onEntityHit(EntityHitResult target) {
		if(world instanceof ServerWorld server) {
			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect((LivingEntity) getOwner(), world, target, effects, stack, potency);

			if(getOwner() instanceof LivingEntity caster)
				SpellShape.castNext(caster, target.getPos(), target.getEntity(), server, stack, spellGroups, groupIndex, potency);
		}

		super.onEntityHit(target);
		kill();
	}

	@Override
	protected void onBlockHit(BlockHitResult target) {
		if(getOwner() instanceof LivingEntity caster && world instanceof ServerWorld server) {
			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect(caster, world, target, effects, stack, potency);

			SpellShape.castNext(caster, target.getPos(), this, server, stack, spellGroups, groupIndex, potency);
		}

		super.onBlockHit(target);
//		kill();
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		effects.clear();
		spellGroups.clear();

		stack = ItemStack.fromNbt(tag.getCompound("ItemStack"));
		potency = tag.getDouble("Potency");
		groupIndex = tag.getInt("GroupIndex");

		NbtList effectList = tag.getList("Effects", NbtElement.STRING_TYPE);
		NbtList groupsList = tag.getList("SpellGroups", NbtElement.COMPOUND_TYPE);

		for(int i = 0; i < effectList.size(); i++)
			effects.add((SpellEffect) Arcanus.SPELL_COMPONENTS.get(new Identifier(effectList.getString(i))));
		for(int i = 0; i < groupsList.size(); i++)
			spellGroups.add(SpellGroup.fromNbt(groupsList.getCompound(i)));
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		NbtList effectList = new NbtList();
		NbtList groupsList = new NbtList();

		tag.put("ItemStack", stack.writeNbt(new NbtCompound()));
		tag.putDouble("Potency", potency);
		tag.putInt("GroupIndex", groupIndex);

		for(SpellEffect effect : effects)
			effectList.add(NbtString.of(Arcanus.SPELL_COMPONENTS.getId(effect).toString()));
		for(SpellGroup group : spellGroups)
			groupsList.add(group.toNbt());

		tag.put("Effects", effectList);
		tag.put("SpellGroups", groupsList);
	}

	@Override
	protected SoundEvent getHitSound() {
		return super.getHitSound();
	}

	@Override
	protected ItemStack asItemStack() {
		return ItemStack.EMPTY;
	}

	public SpellShape getShape() {
		return ArcanusComponents.getSpellShape(this);
	}

	public int getColour() {
		return ArcanusComponents.getColour(this);
	}

	public void setProperties(Entity caster, SpellShape shape, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> groups, int groupIndex, double potency, float speed, boolean noGravity, int colour) {
		setProperties(caster, caster.getPitch(), caster.getYaw(), 0F, speed, 1F);
		setOwner(caster);
		setPos(caster.getX(), caster.getEyeY(), caster.getZ());
		setNoGravity(noGravity);
		setDamage(0);
		ArcanusComponents.setSpellShape(this, shape);
		this.stack = stack;
		this.effects = effects;
		this.spellGroups = groups;
		this.groupIndex = groupIndex;
		this.potency = potency;
		ArcanusComponents.setColour(this, colour);
	}
}
