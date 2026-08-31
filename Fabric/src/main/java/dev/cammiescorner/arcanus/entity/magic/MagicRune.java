package dev.cammiescorner.arcanus.entity.magic;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.entity.Targetable;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.data.ArcanusTags;
import dev.cammiescorner.arcanus.registry.ArcanusSpellComponents;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Util;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class MagicRune extends Entity implements Targetable {
	private UUID casterId = Util.NIL_UUID;
	private ItemStack stack = ItemStack.EMPTY;
	private List<SpellEffect> effects = new ArrayList<>();
	private List<SpellGroup> spellGroups = new ArrayList<>();
	private int groupIndex;
	private double potency;

	public MagicRune(EntityType<?> variant, Level world) {
		super(variant, world);
	}

	@Override
	public void tick() {
		if(level() instanceof ServerLevel serverLevel && (getCaster() == null || !getCaster().isAlive())) {
			kill(serverLevel);
			return;
		}

		if(level() instanceof ServerLevel serverWorld && tickCount > ArcanusConfig.SpellShapes.RuneShapeProperties.delay) {
			LivingEntity entity = serverWorld.getNearestEntity(LivingEntity.class, TargetingConditions.forNonCombat().selector(MagicRune::isValidTarget), null, getX(), getY(), getZ(), new AABB(-0.5, 0, -0.5, 0.5, 0.2, 0.5).move(position()));

			if(entity != null) {
				for(SpellEffect effect : new HashSet<>(effects))
					effect.effect(getCaster(), this, level(), new EntityHitResult(entity), effects, stack, potency);

				SpellShape.castNext(getCaster(), position(), this, serverWorld, stack, spellGroups, groupIndex, potency);
				kill(serverWorld);
			}
		}

		super.tick();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {

	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		effects.clear();
		spellGroups.clear();

		casterId = input.read("CasterId", UUIDUtil.CODEC).orElse(Util.NIL_UUID);
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

		output.store("CasterId", UUIDUtil.CODEC, casterId);
		output.store("ItemStack", ItemStack.CODEC, stack);
		output.putInt("GroupIndex", groupIndex);
		output.putDouble("Potency", potency);

		for(SpellEffect effect : effects)
			stringEffects.add(ArcanusSpellComponents.REGISTRY.getKey(effect).toString());

		output.store("Effects", Codec.STRING.listOf(), stringEffects);
		output.store("SpellGroups", SpellGroup.CODEC.listOf(), spellGroups);
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
		return false;
	}

	public UUID getCasterId() {
		return casterId;
	}

	private LivingEntity getCaster() {
		if(level() instanceof ServerLevel serverWorld && serverWorld.getEntity(casterId) instanceof LivingEntity caster)
			return caster;

		return null;
	}

	public void setProperties(UUID casterId, Entity sourceEntity, Vec3 pos, ItemStack stack, List<SpellEffect> effects, double potency, List<SpellGroup> groups, int groupIndex) {
		setPos(pos);
		setYRot(sourceEntity.getYRot());
		setXRot(sourceEntity.getXRot());
		this.casterId = casterId;
		this.stack = stack;
		this.effects = effects;
		this.spellGroups = groups;
		this.groupIndex = groupIndex;
		this.potency = potency;
	}

	private static boolean isValidTarget(LivingEntity livingEntity, ServerLevel level) {
		if(!livingEntity.isAlive() || livingEntity.isSpectator() || livingEntity.isIgnoringBlockTriggers()) {
			return false;
		}

		return livingEntity.arcanus$canBeTargeted() && !livingEntity.is(ArcanusTags.Entities.RUNE_TRIGGER_IGNORED);
	}
}
