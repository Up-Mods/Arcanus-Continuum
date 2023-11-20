package dev.cammiescorner.arcanuscontinuum.mixin.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements Ownable {
	public ItemEntityMixin(EntityType<?> variant, World world) { super(variant, world); }

	@WrapOperation(method = "onPlayerCollision", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z"
	))
	private boolean arcanuscontinuum$copperCurse(PlayerInventory instance, ItemStack stack, Operation<Boolean> original, PlayerEntity player) {
		if(player.hasStatusEffect(ArcanusStatusEffects.COPPER_CURSE.get()) && stack.getItem() != Items.RAW_COPPER && (instance.getOccupiedSlotWithRoomForStack(stack) >= 0 || instance.getEmptySlot() >= 0)) {
			int originalStackCount = stack.getCount();

			for(int i = 0; i < originalStackCount; i++)
				if(random.nextFloat() < 0.0625f)
					stack.decrement(1);

			if(originalStackCount - stack.getCount() > 0)
				instance.offerOrDrop(new ItemStack(Items.RAW_COPPER, originalStackCount - stack.getCount()));
		}

		return original.call(instance, stack);
	}
}
