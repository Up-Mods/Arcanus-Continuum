package dev.cammiescorner.arcanuscontinuum.common.entities;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.entity.effect.api.StatusEffectRemovalReason;

import java.util.List;
import java.util.UUID;

public class OpossumEntity extends TameableEntity implements SmartBrainOwner<OpossumEntity> {
	private static final TrackedData<ItemStack> HAT_TRACKER = DataTracker.registerData(OpossumEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

	public OpossumEntity(EntityType<? extends TameableEntity> entityType, World world) {
		super(entityType, world);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return TameableEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 15).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.425);
	}

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);

		if(stack.isOf(ArcanusItems.WIZARD_HAT) && getHatStack().isEmpty()) {
			if(!this.world.isClient) {
				setHatStack(stack.copy());

				if(!player.isCreative())
					stack.decrement(1);

				return ActionResult.SUCCESS;
			}
			else {
				return ActionResult.CONSUME;
			}
		}
		else if(stack.isEmpty() && !getHatStack().isEmpty()) {
			player.giveItemStack(getHatStack());
			setHatStack(ItemStack.EMPTY);
			return ActionResult.SUCCESS;
		}
		else {
			return super.interactMob(player, hand);
		}
	}

	@Nullable
	@Override
	public OpossumEntity createChild(ServerWorld world, PassiveEntity entity) {
		OpossumEntity opossumEntity = ArcanusEntities.OPOSSUM.create(world);
		UUID uUID = getOwnerUuid();

		if(uUID != null && opossumEntity != null) {
			opossumEntity.setOwnerUuid(uUID);
			opossumEntity.setTamed(true);
		}

		return opossumEntity;
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		dataTracker.startTracking(HAT_TRACKER, ItemStack.EMPTY);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		dataTracker.set(HAT_TRACKER, ItemStack.fromNbt(nbt.getCompound("OpossumHat")));
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		nbt.put("OpossumHat", dataTracker.get(HAT_TRACKER).writeNbt(new NbtCompound()));
		return super.writeNbt(nbt);
	}

	@Override
	public boolean removeStatusEffect(@NotNull StatusEffect type, @NotNull StatusEffectRemovalReason reason) {
		return false;
	}

	@Override
	public int clearStatusEffects(@NotNull StatusEffectRemovalReason reason) {
		return 0;
	}

	@Override
	public void onStatusEffectRemoved(@NotNull StatusEffectInstance effect, @NotNull StatusEffectRemovalReason reason) {

	}

	public ItemStack getHatStack() {
		return dataTracker.get(HAT_TRACKER);
	}

	public void setHatStack(ItemStack stack) {
		dataTracker.set(HAT_TRACKER, stack);
	}

	@Override
	protected Brain.Profile<?> createBrainProfile() {
		return new SmartBrainProvider<>(this);
	}

	@Override
	protected void mobTick() {
		tickBrain(this);
		super.mobTick();
	}

	@Override
	public List<ExtendedSensor<OpossumEntity>> getSensors() {
		return ObjectArrayList.of(
				new NearbyLivingEntitySensor<>(),
				new HurtBySensor<>()
		);
	}
}
