package dev.cammiescorner.arcanuscontinuum.mixin;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.api.spells.Pattern;
import dev.cammiescorner.arcanuscontinuum.api.spells.Spell;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
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

import java.util.List;
import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Shadow public abstract @Nullable EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);
	@Shadow public abstract ItemStack getMainHandStack();
	@Shadow public abstract boolean hasStatusEffect(StatusEffect effect);
	@Shadow public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

	@Unique private static final UUID uUID = UUID.fromString("e348efa3-7987-4912-b82a-03c5c75eccb1");
	@Unique private final LivingEntity self = (LivingEntity) (Object) this;
	@Unique private Vec3d prevVelocity;

	public LivingEntityMixin(EntityType<?> type, World world) { super(type, world); }

	@ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
	private float arcanuscontinuum$damage(float amount, DamageSource source) {
		EntityAttributeInstance attributeInstance = getAttributeInstance(ArcanusEntityAttributes.MAGIC_RESISTANCE);

		if(attributeInstance != null && source.isMagic())
			amount /= Math.max(attributeInstance.getValue(), 0.000001);

		if(hasStatusEffect(ArcanusStatusEffects.FORTIFY))
			amount *= 1 - (getStatusEffect(ArcanusStatusEffects.FORTIFY).getAmplifier() + 1) * 0.05;
		if(hasStatusEffect(ArcanusStatusEffects.VULNERABILITY))
			amount *= 1 + (getStatusEffect(ArcanusStatusEffects.VULNERABILITY).getAmplifier() + 1) * 0.05;

		return amount;
	}

	@Inject(method = "getMovementSpeed()F", at = @At("HEAD"), cancellable = true)
	private void arcanuscontinuum$getMovementSpeed(CallbackInfoReturnable<Float> info) {
		if(ArcanusComponents.getStunTimer(self) > 0)
			info.setReturnValue(0F);
	}

	@Inject(method = "tryAttack", at = @At("HEAD"), cancellable = true)
	private void arcanuscontinuum$tryAttack(Entity target, CallbackInfoReturnable<Boolean> info) {
		if(ArcanusComponents.getStunTimer(self) > 0)
			info.setReturnValue(false);
	}

	@ModifyArg(method = "fall", at = @At(value = "INVOKE", target = "Lnet/minecraft/particle/BlockStateParticleEffect;<init>(Lnet/minecraft/particle/ParticleType;Lnet/minecraft/block/BlockState;)V"))
	private BlockState arcanuscontinuum$fall(BlockState value) {
		if(hasStatusEffect(ArcanusStatusEffects.BOUNCY))
			return Blocks.SLIME_BLOCK.getDefaultState();

		return value;
	}

	@Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
	private void arcanuscontinuum$handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
		if(prevVelocity != null && damageSource != DamageSource.STALAGMITE && fallDistance > getSafeFallDistance() && hasStatusEffect(ArcanusStatusEffects.BOUNCY)) {
			if(!world.isClient) {
				world.playSoundFromEntity(null, this, SoundEvents.BLOCK_SLIME_BLOCK_FALL, getSoundCategory(), 1, 1);

				if(!bypassesLandingEffects()) {
					setVelocity(getVelocity().getX(), -prevVelocity.getY() * 0.99, getVelocity().getZ());
					velocityModified = true;
				}
			}

			info.setReturnValue(false);
		}
	}

	@Inject(method = "tick", at = @At("HEAD"))
	private void arcanuscontinuum$tick(CallbackInfo info) {
		if(!world.isClient() && ArcanusComponents.STUN_COMPONENT.isProvidedBy(self) && ArcanusComponents.CASTING_COMPONENT.isProvidedBy(self)) {
			prevVelocity = getVelocity();

			EntityAttributeInstance speedAttr = getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
			List<Pattern> pattern = ArcanusComponents.getPattern(self);
			ItemStack stack = getMainHandStack();

			if(speedAttr != null) {
				if(stack.getItem() instanceof StaffItem && ArcanusComponents.isCasting(self) && pattern.size() == 3) {
					int index = Arcanus.getSpellIndex(pattern);
					NbtCompound tag = stack.getOrCreateSubNbt(Arcanus.MOD_ID);
					NbtList list = tag.getList("Spells", NbtElement.COMPOUND_TYPE);

					if(!list.isEmpty() && index < list.size()) {
						Spell spell = Spell.fromNbt(list.getCompound(index));
						EntityAttributeModifier speedMod = new EntityAttributeModifier(uUID, "Spell Speed Modifier", spell.getWeight().getSlowdown(), EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

						if(!speedAttr.hasModifier(speedMod))
							speedAttr.addTemporaryModifier(speedMod);
					}
				}
				else if(speedAttr.getModifier(uUID) != null)
					speedAttr.removeModifier(uUID);
			}
		}
	}

	@Inject(method = "createLivingAttributes", at = @At("RETURN"))
	private static void arcanuscontinuum$createPlayerAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
		info.getReturnValue().add(ArcanusEntityAttributes.MAX_MANA).add(ArcanusEntityAttributes.MANA_REGEN).add(ArcanusEntityAttributes.BURNOUT_REGEN).add(ArcanusEntityAttributes.MANA_LOCK).add(ArcanusEntityAttributes.SPELL_POTENCY).add(ArcanusEntityAttributes.MAGIC_RESISTANCE);
	}
}
