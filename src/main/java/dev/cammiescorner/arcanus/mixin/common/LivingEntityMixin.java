package dev.cammiescorner.arcanus.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import commonnetwork.api.Network;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanus.api.entities.Targetable;
import dev.cammiescorner.arcanus.api.spells.Pattern;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.common.effects.ArcanusStatusEffect;
import dev.cammiescorner.arcanus.common.items.StaffItem;
import dev.cammiescorner.arcanus.common.packets.clientbound.ClientboundStatusEffectPacket;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Targetable {
	@Unique private final LivingEntity self = (LivingEntity) (Entity) this;
	@Unique private Vec3 prevVelocity;
	@Shadow protected boolean jumping;

	@Shadow public abstract ItemStack getMainHandItem();
	@Shadow public abstract boolean isDamageSourceBlocked(DamageSource source);
	@Shadow public abstract boolean removeAllEffects();
	@Shadow public abstract boolean addEffect(MobEffectInstance effect);
	@Shadow public abstract float getSpeed();
	@Shadow public abstract boolean randomTeleport(double x, double y, double z, boolean particleEffects);
	@Shadow public abstract boolean hasEffect(Holder<MobEffect> effect);
	@Shadow public abstract boolean removeEffect(Holder<MobEffect> effect);
	@Shadow public abstract @Nullable MobEffectInstance getEffect(Holder<MobEffect> effect);
	@Shadow public abstract @Nullable AttributeInstance getAttribute(Holder<Attribute> attribute);

	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
	private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
		if(amount > 0 && !isDamageSourceBlocked(source)) {
			if(ArcanusComponents.isCounterActive(self) && source.getDirectEntity() instanceof LivingEntity attacker)
				ArcanusComponents.castCounter(self, attacker);

			if(hasEffect(ArcanusMobEffects.FLOAT.holder()) && ArcanusConfig.MovementEffects.FloatEffectProperties.removedUponTakingDamage)
				removeEffect(ArcanusMobEffects.FLOAT.holder());

			if(hasEffect(ArcanusMobEffects.STOCKPILE.holder()) && amount >= ArcanusConfig.AttackEffects.StockpileEffectProperties.damageNeededToIncrease) {
				MobEffectInstance stockpile = getEffect(ArcanusMobEffects.STOCKPILE.holder());

				if(stockpile.getAmplifier() < 9) {
					removeAllEffects();

					addEffect(new MobEffectInstance(stockpile.getEffect(), stockpile.getDuration(), stockpile.getAmplifier() + Mth.floor(Math.round(amount) / 10f)));
				}
			}

			if(hasEffect(ArcanusMobEffects.DANGER_SENSE.holder()) && (source.is(DamageTypeTags.IS_PROJECTILE) || source.is(DamageTypeTags.IS_EXPLOSION))) {
				MobEffectInstance dangerSense = getEffect(ArcanusMobEffects.DANGER_SENSE.holder());

				if(random.nextFloat() < ArcanusConfig.SupportEffects.DangerSenseEffectProperties.baseChanceToActivate * (dangerSense.getAmplifier() + 1)) {
					if(level() instanceof ServerLevel world) {
						double d = getX();
						double e = getY();
						double f = getZ();

						for(int i = 0; i < 16; ++i) {
							double g = getX() + (random.nextDouble() - 0.5) * 16.0;
							double h = Mth.clamp(
								getY() + random.nextInt(16) - 8,
								world.getMinBuildHeight(),
								world.getMinBuildHeight() + world.getLogicalHeight() - 1
							);
							double j = getZ() + (random.nextDouble() - 0.5) * 16.0;

							if(isPassenger())
								stopRiding();

							Vec3 vec3d = position();

							if(randomTeleport(g, h, j, true)) {
								world.gameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Context.of(self));
								SoundEvent soundEvent = self instanceof Fox ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
								world.playSound(null, d, e, f, soundEvent, SoundSource.PLAYERS, 1f, 1f);
								playSound(soundEvent, 1f, 1f);
								break;
							}
						}
					}

					removeEffect(ArcanusMobEffects.DANGER_SENSE.holder());
					info.setReturnValue(false);
				}
			}
		}
	}

	@ModifyVariable(method = "hurt", at = @At("HEAD"), argsOnly = true)
	private float modifyDamage(float amount, DamageSource source) {
		AttributeInstance attributeInstance = getAttribute(ArcanusEntityAttributes.MAGIC_RESISTANCE.holder());

		if(attributeInstance != null && source.is(DamageTypeTags.WITCH_RESISTANT_TO))
			amount /= Math.max((float) attributeInstance.getValue(), 0.000001F);
		if(hasEffect(ArcanusMobEffects.FORTIFY.holder()))
			amount /= 1 + (getEffect(ArcanusMobEffects.FORTIFY.holder()).getAmplifier() + 1) * 0.25F;
		if(hasEffect(ArcanusMobEffects.VULNERABILITY.holder()))
			amount *= 1 + 0.8F * ((getEffect(ArcanusMobEffects.VULNERABILITY.holder()).getAmplifier() + 1) / 10F);
		if(source.getEntity() instanceof LivingEntity attacker && attacker.hasEffect(ArcanusMobEffects.STOCKPILE.holder())) {
			amount *= attacker.getEffect(ArcanusMobEffects.STOCKPILE.holder()).getAmplifier() + 1;
			attacker.removeEffect(ArcanusMobEffects.STOCKPILE.holder());
		}

		return amount;
	}

	@ModifyArg(method = "checkFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/particles/BlockParticleOption;<init>(Lnet/minecraft/core/particles/ParticleType;Lnet/minecraft/world/level/block/state/BlockState;)V"))
	private BlockState bouncy(BlockState value) {
		if(hasEffect(ArcanusMobEffects.BOUNCY.holder()))
			return Blocks.SLIME_BLOCK.defaultBlockState();
		if(hasEffect(ArcanusMobEffects.FLOAT.holder()))
			fallDistance = 0;

		return value;
	}

	@Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
	private void negateFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
		if(prevVelocity != null && !damageSources().source(DamageTypes.STALAGMITE).equals(damageSource) && fallDistance > getMaxFallDistance() && hasEffect(ArcanusMobEffects.BOUNCY.holder())) {
			if(!level().isClientSide) {
				level().playSound(null, this, SoundEvents.SLIME_BLOCK_FALL, getSoundSource(), 1, 1);

				if(!isSuppressingBounce()) {
					setDeltaMovement(getDeltaMovement().x(), -prevVelocity.y() * 0.99, getDeltaMovement().z());
					hurtMarked = true;
				}
			}

			info.setReturnValue(false);
		}
	}

	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo info) {
		if(!level().isClientSide() && ArcanusComponents.PATTERN_COMPONENT.isProvidedBy(this) && ArcanusComponents.CASTING_COMPONENT.isProvidedBy(this)) {
			prevVelocity = getDeltaMovement();

			AttributeInstance speedAttr = getAttribute(Attributes.MOVEMENT_SPEED);
			List<Pattern> pattern = ArcanusComponents.getPattern((LivingEntity) (Object) this);
			ItemStack stack = getMainHandItem();

			if(speedAttr != null) {
				if(stack.getItem() instanceof StaffItem && ArcanusComponents.isCasting((LivingEntity) (Object) this) && pattern.size() == 3) {
					List<Spell> list = stack.getOrDefault(ArcanusDataComponents.SPELL_LIST.get(), new ArrayList<>());
					int index = Arcanus.getSpellIndex(pattern);

					if(!list.isEmpty() && index < list.size()) {
						Spell spell = list.get(index);
						AttributeModifier speedMod = new AttributeModifier(Arcanus.SPELL_SPEED_MODIFIER_ID, spell.getWeight().getSlowdown(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

						if(!speedAttr.hasModifier(Arcanus.SPELL_SPEED_MODIFIER_ID))
							speedAttr.addTransientModifier(speedMod);
					}
				}
				else if(speedAttr.getModifier(Arcanus.SPELL_SPEED_MODIFIER_ID) != null)
					speedAttr.removeModifier(Arcanus.SPELL_SPEED_MODIFIER_ID);
			}
		}
	}

	@ModifyReturnValue(method = "createLivingAttributes", at = @At("RETURN"))
	private static AttributeSupplier.Builder createPlayerAttributes(AttributeSupplier.Builder builder) {
		ArcanusEntityAttributes.registerAll();

		return builder
			.add(ArcanusEntityAttributes.MAX_MANA.holder())
			.add(ArcanusEntityAttributes.MANA_REGEN.holder())
			.add(ArcanusEntityAttributes.BURNOUT_REGEN.holder())
			.add(ArcanusEntityAttributes.MANA_LOCK.holder())
			.add(ArcanusEntityAttributes.SPELL_POTENCY.holder())
			.add(ArcanusEntityAttributes.MAGIC_RESISTANCE.holder())
			.add(ArcanusEntityAttributes.MANA_COST.holder())
			.add(ArcanusEntityAttributes.SPELL_COOL_DOWN.holder());
	}

	@WrapOperation(method = "handleRelativeFrictionAndCalculateMovement", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/world/entity/LivingEntity;getDeltaMovement()Lnet/minecraft/world/phys/Vec3;",
		ordinal = 1
	))
	private Vec3 floatAround(LivingEntity livingEntity, Operation<Vec3> original, Vec3 movementInput, float slipperiness) {
		// FIXME smooth out vertical movement, currently a bit jolting
		if(hasEffect(ArcanusMobEffects.FLOAT.holder()))
			return getDeltaMovement().add(0, jumping ? getSpeed() : isShiftKeyDown() ? -getSpeed() : 0, 0);

		return original.call(livingEntity);
	}

	@Inject(method = "onEffectRemoved", at = @At("HEAD"), cancellable = true)
	private void attemptToRemoveEffect(MobEffectInstance effectInstance, CallbackInfo info) {
		if(effectInstance.getEffect() == ArcanusMobEffects.COPPER_CURSE.get())
			info.cancel();

		if(level() instanceof ServerLevel level && effectInstance.getEffect().value() instanceof ArcanusStatusEffect effect) {
			if(effect.shouldSync)
				Network.getNetworkHandler().sendToAllClients(new ClientboundStatusEffectPacket(getId(), effectInstance.getEffect(), true), level.getServer());

			if(effect == ArcanusMobEffects.FLOAT.get())
				setNoGravity(true);
		}
	}

	@ModifyVariable(method = "travel", at = @At("HEAD"), argsOnly = true)
	public Vec3 invertInput(Vec3 movementInput) {
		if(!(self instanceof Player) && hasEffect(ArcanusMobEffects.DISCOMBOBULATE.holder()))
			movementInput = movementInput.multiply(-1, 1, -1);

		return movementInput;
	}
}
