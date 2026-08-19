package dev.cammiescorner.arcanus.common.entity.magic;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.entity.Targetable;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Missile extends AbstractArrow implements Targetable {
	private ItemStack stack = ItemStack.EMPTY;
	private List<SpellEffect> effects = new ArrayList<>();
	private List<SpellGroup> spellGroups = new ArrayList<>();
	private int groupIndex;
	private double potency;

	public Missile(EntityType<? extends AbstractArrow> entityType, Level world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
		int lifeSpan = ArcanusConfig.SpellShapes.MissileShapeProperties.baseLifeSpan;

		if(level() instanceof ServerLevel server && (getOwner() == null || !getOwner().isAlive() || tickCount >= lifeSpan)) {
			EntityHitResult target = new EntityHitResult(this);

			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect((LivingEntity) getOwner(), this, level(), target, effects, stack, potency);

			if(getOwner() instanceof LivingEntity caster)
				SpellShape.castNext(caster, target.getLocation(), null, server, stack, spellGroups, groupIndex, potency);

			kill(server);
			return;
		}

		super.tick();
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	protected void onHitEntity(EntityHitResult target) {
		playSound(getHitGroundSoundEvent(), 1f, 1.2f / (random.nextFloat() * 0.2f + 0.9f));

		if(level() instanceof ServerLevel server) {
			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect((LivingEntity) getOwner(), this, level(), target, effects, stack, potency);

			if(getOwner() instanceof LivingEntity caster)
				SpellShape.castNext(caster, target.getLocation(), target.getEntity(), server, stack, spellGroups, groupIndex, potency);

			kill(server);
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult target) {
		super.onHitBlock(target);

		if(getOwner() instanceof LivingEntity caster && level() instanceof ServerLevel server) {
			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect(caster, this, level(), target, effects, stack, potency);

			SpellShape.castNext(caster, target.getLocation(), this, server, stack, spellGroups, groupIndex, potency);

			kill(server);
		}
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		effects.clear();
		spellGroups.clear();

		stack = input.read("ItemStack", ItemStack.CODEC).orElse(ItemStack.EMPTY);
		groupIndex = input.getIntOr("GroupIndex", 0);
		potency = input.getDoubleOr("Potency", 0);

		for(String s : input.read("Effects", Codec.STRING.listOf()).orElse(List.of())) {
			if(ArcanusSpellComponents.REGISTRY.getValue(Identifier.parse(s)) instanceof SpellEffect spellEffect)
				effects.add(spellEffect);
		}

		spellGroups.addAll(input.read("SpellGroups", SpellGroup.CODEC.listOf()).orElse(List.of()));
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		List<String> stringEffects = new ArrayList<>();

		output.store("ItemStack", ItemStack.CODEC, stack);
		output.putInt("GroupIndex", groupIndex);
		output.putDouble("Potency", potency);

		for(SpellEffect effect : effects)
			stringEffects.add(ArcanusSpellComponents.REGISTRY.getKey(effect).toString());

		output.store("Effects", Codec.STRING.listOf(), stringEffects);
		output.store("SpellGroups", SpellGroup.CODEC.listOf(), spellGroups);
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return new ItemStack(Items.DIRT);
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.AMETHYST_CLUSTER_BREAK; // TODO make its own hit sound
	}

	@Override
	protected float getWaterInertia() {
		return 1f;
	}

	public void setProperties(Entity caster, @Nullable Entity castSource, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> groups, int groupIndex, double potency) {
		Entity sourceEntity = castSource != null ? castSource : caster;
		shootFromRotation(sourceEntity, sourceEntity.getXRot(), sourceEntity.getYRot(), 0f, ArcanusConfig.SpellShapes.MissileShapeProperties.projectileSpeed, 1f);
		setOwner(caster);
		setPosRaw(sourceEntity.getX(), sourceEntity.getEyeY(), sourceEntity.getZ());
		setNoGravity(true);
		setBaseDamage(0);
		this.stack = stack;
		this.effects = effects;
		this.spellGroups = groups;
		this.groupIndex = groupIndex;
		this.potency = potency;
		ArcanusHelper.copyMagicColor(this, caster);
	}
}
