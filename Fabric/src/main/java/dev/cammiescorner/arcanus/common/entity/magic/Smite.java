package dev.cammiescorner.arcanus.common.entity.magic;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.entity.Targetable;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.common.registry.ArcanusSoundEvents;
import dev.cammiescorner.arcanus.common.registry.ArcanusSpellComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

public class Smite extends Entity implements Targetable {
	private final List<UUID> hasHit = new ArrayList<>();
	private UUID casterId = Util.NIL_UUID;
	private ItemStack stack = ItemStack.EMPTY;
	private List<SpellEffect> effects = new ArrayList<>();
	private double potency;

	public Smite(EntityType<? extends Entity> entityType, Level world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
		if(level() instanceof ServerLevel serverLevel) {
			if((getCaster() == null || !getCaster().isAlive())) {
				kill(serverLevel);
				return;
			}

			if(tickCount <= 9) {
				AABB box = new AABB(getX() - 4, getY() - 1, getZ() - 4, getX() + 4, (level().getHeight() + 2048) - getY(), getZ() + 4);

				for(SpellEffect effect : new HashSet<>(effects)) {
					if(effect.singleCastOnly()) {
						effect.effect(getCaster(), this, level(), new EntityHitResult(this), effects, stack, potency);
						continue;
					}

					level().getEntitiesOfClass(Entity.class, box, entity -> entity.isAlive() && !entity.isSpectator() && entity instanceof Targetable targetable && targetable.arcanus$canBeTargeted()).forEach(entity -> {
						if(!hasHit.contains(entity.getUUID())) {
							effect.effect(getCaster(), this, level(), new EntityHitResult(entity), effects, stack, potency);

							hasHit.add(entity.getUUID());
						}
					});
				}
			}

			if(tickCount > 23) {
				kill(serverLevel);
			}
		}
		else {
			clientTick();
		}

		super.tick();
	}

	@Environment(EnvType.CLIENT)
	public void clientTick() {
		if(tickCount == 1) {
			level().playLocalSound(getX(), getY(), getZ(), ArcanusSoundEvents.SMITE, SoundSource.NEUTRAL, Mth.clamp(1 - (distanceTo(Minecraft.getInstance().player) / 100f), 0, 1), (1f + (random.nextFloat() - random.nextFloat()) * 0.2f) * 0.7f, false);
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {

	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
		return false;
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		effects.clear();
		hasHit.clear();

		casterId = input.read("CasterId", UUIDUtil.CODEC).orElse(Util.NIL_UUID);
		stack = input.read("ItemStack", ItemStack.CODEC).orElse(ItemStack.EMPTY);
		potency = input.getDoubleOr("Potency", 0);

		for(String s : input.read("Effects", Codec.STRING.listOf()).orElse(List.of())) {
			if(ArcanusSpellComponents.REGISTRY.getValue(Identifier.parse(s)) instanceof SpellEffect spellEffect)
				effects.add(spellEffect);
		}

		hasHit.addAll(input.read("HasHit", UUIDUtil.CODEC.listOf()).orElse(List.of()));
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		List<String> stringEffects = new ArrayList<>();

		output.store("CasterId", UUIDUtil.CODEC, casterId);
		output.store("ItemStack", ItemStack.CODEC, stack);
		output.putDouble("Potency", potency);

		for(SpellEffect effect : effects)
			stringEffects.add(ArcanusSpellComponents.REGISTRY.getKey(effect).toString());

		output.store("Effects", Codec.STRING.listOf(), stringEffects);
		output.store("HasHit", UUIDUtil.CODEC.listOf(), hasHit);
	}

	public UUID getCasterId() {
		return casterId;
	}

	private LivingEntity getCaster() {
		if(level() instanceof ServerLevel serverWorld && serverWorld.getEntity(casterId) instanceof LivingEntity caster) {
			return caster;
		}

		return null;
	}

	public void setProperties(UUID casterId, Entity sourceEntity, Vec3 pos, ItemStack stack, List<SpellEffect> effects, double potency) {
		setPos(pos);
		setNoGravity(true);
		setYRot(sourceEntity.getYRot());
		this.casterId = casterId;
		this.stack = stack;
		this.effects = effects;
		this.potency = potency;
	}
}
