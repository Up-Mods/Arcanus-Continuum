package dev.cammiescorner.arcanuscontinuum.common.entities;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class OpossumEntity extends TameableEntity {
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

	public ItemStack getHatStack() {
		return dataTracker.get(HAT_TRACKER);
	}

	public void setHatStack(ItemStack stack) {
		dataTracker.set(HAT_TRACKER, stack);
	}
}
