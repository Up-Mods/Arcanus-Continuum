package dev.cammiescorner.arcanuscontinuum.common.entities;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.task.ForgetAttackTargetTask;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.*;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRetaliateTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.entity.effect.api.StatusEffectRemovalReason;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("ALL")
public class OpossumEntity extends TameableEntity implements SmartBrainOwner<OpossumEntity> {
	public OpossumEntity(EntityType<? extends TameableEntity> entityType, World world) {
		super(entityType, world);
		Arrays.fill(armorDropChances, 1F);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return TameableEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28);
	}

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack handStack = player.getStackInHand(hand);
		ItemStack stack = handStack.copy();
		ItemStack hatStack = getEquippedStack(EquipmentSlot.HEAD).copy();

		if(isTamed()) {
			if(handStack.isOf(ArcanusItems.WIZARD_HAT)) {
				if(!this.world.isClient) {
					equipStack(EquipmentSlot.HEAD, stack);

					if(!player.isCreative())
						handStack.decrement(1);
					if(!getEquippedStack(EquipmentSlot.HEAD).isEmpty() && !player.isCreative())
						player.setStackInHand(hand, hatStack);

					return ActionResult.SUCCESS;
				}
				else {
					return ActionResult.CONSUME;
				}
			}
			else if(handStack.isEmpty()) {
				if(player.isSneaking() && !getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
					player.setStackInHand(hand, hatStack);
					equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
					return ActionResult.SUCCESS;
				}

				if(getOwner() != null && player.getId() == getOwner().getId()) {
					setSitting(!isSitting());
					return ActionResult.SUCCESS;
				}
			}
		}
		else if(handStack.isOf(Items.CARROT)) {
			if(!world.isClient) {
				if(!player.isCreative())
					handStack.decrement(1);

				if(random.nextInt(3) == 0) {
					setOwner(player);
					navigation.stop();
					setTarget(null);
					setSitting(true);
					world.sendEntityStatus(this, (byte) 7);
				}
				else {
					world.sendEntityStatus(this, (byte) 6);
				}

				return ActionResult.SUCCESS;
			}
		}

		return super.interactMob(player, hand);
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

	@Override
	protected void mobTick() {
		super.mobTick();
		tickBrain(this);
	}

	@Override
	protected Brain.Profile<?> createBrainProfile() {
		return new SmartBrainProvider<>(this);
	}

	@Override
	public List<ExtendedSensor<OpossumEntity>> getSensors() {
		return ObjectArrayList.of(
				new NearbyLivingEntitySensor<>(),
				new HurtBySensor<>()
		);
	}

	@Override
	public BrainActivityGroup<OpossumEntity> getCoreTasks() {
		return BrainActivityGroup.coreTasks(
				new FloatToSurfaceOfFluid<>(),
				new FleeTarget<>().speedModifier(1.5F),
				new LookAtTarget<>(),
				new FollowOwner<>(),
				new MoveToWalkTarget<>()
		);
	}

	@Override
	public BrainActivityGroup<OpossumEntity> getIdleTasks() {
		return BrainActivityGroup.idleTasks(
				new FirstApplicableBehaviour<OpossumEntity>(
						new SetRetaliateTarget<>(),
						new SetPlayerLookTarget<>(),
						new SetRandomLookTarget<>()
				),
				new OneRandomBehaviour<>(
						new SetRandomWalkTarget<>(),
						new Idle<>().runFor(entity -> entity.getRandom().nextInt(30) + 30)
				)
		);
	}

	@Override
	public BrainActivityGroup<OpossumEntity> getFightTasks() {
		return BrainActivityGroup.fightTasks(
				new ForgetAttackTargetTask<>(target -> !target.isAlive() || target.squaredDistanceTo(this) > (32 * 32) || target instanceof PlayerEntity player && player.isCreative())
		);
	}
}
