package dev.cammiescorner.arcanuscontinuum.common.entities.magic;

import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;

public class MagicProjectileEntity extends PersistentProjectileEntity {
	private SpellShape shape;
	private StaffItem staffItem;
	private ItemStack stack;
	private List<SpellEffect> effects;
	private List<SpellGroup> spellGroups;
	private int groupIndex;

	public MagicProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
		if(shape == ArcanusSpellComponents.PROJECTILE && age >= 20)
			kill();

		super.tick();
	}

	@Override
	protected void onEntityHit(EntityHitResult target) {
		if(getOwner() instanceof LivingEntity caster && world instanceof ServerWorld server) {
			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect(caster, world, target, effects, staffItem, stack, caster.getAttributeValue(ArcanusEntityAttributes.SPELL_POTENCY));

			shape.castNext(caster, target.getPos(), target.getEntity(), server, staffItem, stack, spellGroups, groupIndex);
		}

		super.onEntityHit(target);
		kill();
	}

	@Override
	protected void onBlockHit(BlockHitResult target) {
		if(getOwner() instanceof LivingEntity caster && world instanceof ServerWorld server) {
			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect(caster, world, target, effects, staffItem, stack, caster.getAttributeValue(ArcanusEntityAttributes.SPELL_POTENCY));

			shape.castNext(caster, target.getPos(), this, server, staffItem, stack, spellGroups, groupIndex);
		}

		super.onBlockHit(target);
		kill();
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
		return shape;
	}

	public void setProperties(Entity caster, SpellShape shape, StaffItem staffItem, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> groups, int groupIndex, float speed, boolean noGravity) {
		setProperties(caster, caster.getPitch(), caster.getYaw(), 0F, speed, 1F);
		setOwner(caster);
		setPos(caster.getX(), caster.getEyeY(), caster.getZ());
		setNoGravity(noGravity);
		setDamage(0);
		this.shape = shape;
		this.staffItem = staffItem;
		this.stack = stack;
		this.effects = effects;
		this.spellGroups = groups;
		this.groupIndex = groupIndex;
	}
}
