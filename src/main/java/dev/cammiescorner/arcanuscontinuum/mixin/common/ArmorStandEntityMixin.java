package dev.cammiescorner.arcanuscontinuum.mixin.common;

import dev.cammiescorner.arcanuscontinuum.common.items.BattleMageArmorItem;
import net.minecraft.block.Oxidizable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStandEntity.class)
public abstract class ArmorStandEntityMixin extends LivingEntity {
	@Shadow protected abstract EquipmentSlot slotFromPosition(Vec3d hitPos);
	@Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

	protected ArmorStandEntityMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

	@Inject(method = "interactAt", at = @At("HEAD"), cancellable = true)
	private void arcanuscontinuum$waxOnWaxOff(PlayerEntity player, Vec3d hitPos, Hand hand, CallbackInfoReturnable<ActionResult> info) {
		EquipmentSlot slot = slotFromPosition(hitPos);
		ItemStack stack = getEquippedStack(slot);
		ItemStack heldStack = player.getStackInHand(hand);

		if(stack.getItem() instanceof BattleMageArmorItem) {
			if(heldStack.isIn(ItemTags.AXES)) {
				if(BattleMageArmorItem.isWaxed(stack)) {
					getWorld().playSound(player, getBlockPos(), SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1f, 1f);
					BattleMageArmorItem.setWaxed(stack, false);
					heldStack.damage(1, player, player1 -> player1.sendToolBreakStatus(hand));
					info.setReturnValue(ActionResult.SUCCESS);
				}

				if(BattleMageArmorItem.getOxidation(stack) != Oxidizable.OxidizationLevel.UNAFFECTED) {
					getWorld().playSound(player, getBlockPos(), SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1f, 1f);
					BattleMageArmorItem.setOxidation(stack, Oxidizable.OxidizationLevel.values()[BattleMageArmorItem.getOxidation(stack).ordinal() - 1]);
					heldStack.damage(1, player, player1 -> player1.sendToolBreakStatus(hand));
					info.setReturnValue(ActionResult.SUCCESS);
				}
			}

			if(heldStack.getItem() == Items.HONEYCOMB && !BattleMageArmorItem.isWaxed(stack)) {
				getWorld().playSound(player, getBlockPos(), SoundEvents.ITEM_HONEYCOMB_WAX_ON, SoundCategory.BLOCKS, 1f, 1f);
				BattleMageArmorItem.setWaxed(stack, true);
				heldStack.decrement(1);
				info.setReturnValue(ActionResult.SUCCESS);
			}
		}
	}
}
