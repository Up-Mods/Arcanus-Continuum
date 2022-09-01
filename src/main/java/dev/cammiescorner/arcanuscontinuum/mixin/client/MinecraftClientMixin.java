package dev.cammiescorner.arcanuscontinuum.mixin.client;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Pattern;
import dev.cammiescorner.arcanuscontinuum.client.utils.ClientUtils;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.packets.c2s.SetCastingPacket;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBind;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin implements ClientUtils {
	@Unique private boolean isCasting = false;
	@Unique private int timer = 0;
	@Unique private KeyBind lastMouseDown = null;
	@Unique private final List<Pattern> pattern = new ArrayList<>(3);

	@Shadow @Nullable public ClientPlayerEntity player;
	@Shadow @Final public GameOptions options;

	@Shadow public abstract float getTickDelta();
	@Shadow protected abstract boolean doAttack();

	@Inject(method = "tick", at = @At("HEAD"))
	public void tick(CallbackInfo info) {
		if(player == null)
			return;

		ItemStack stack = player.getMainHandStack();

		if(timer == 0 && (pattern.size() < 3 || !(stack.getItem() instanceof StaffItem) || (lastMouseDown != null && !lastMouseDown.isPressed()))) {
			if(!pattern.isEmpty())
				pattern.clear();

			lastMouseDown = null;
			isCasting = false;
		}

		if(stack.getItem() instanceof StaffItem) {
			isCasting = lastMouseDown != null && lastMouseDown.isPressed();

			if(timer > 0 && pattern.size() == 3) {
				if(isCasting) {
					// TODO handle the spell shenanigans
					if(stack.getCooldown() == 0) {
						int index = Arcanus.getSpellIndex(pattern);
					}
				}
				else {
					timer = 0;
				}
			}
		}
		else {
			timer = 0;
		}

		if(isCasting && !ArcanusComponents.isCasting(player))
			SetCastingPacket.send(true);
		if(!isCasting && ArcanusComponents.isCasting(player))
			SetCastingPacket.send(false);

		if(timer > 0 && player.getAttackCooldownProgress(getTickDelta()) == 1F)
			timer--;
	}

	@Inject(method = "handleInputEvents", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/MinecraftClient;handleBlockBreaking(Z)V"
	), cancellable = true)
	public void arcanuscontinuum$onBlockBreak(CallbackInfo info) {
		if(isCasting)
			info.cancel();
	}

	@Inject(method = "handleInputEvents", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/MinecraftClient;doAttack()Z",
			ordinal = 0
	), cancellable = true)
	public void arcanuscontinuum$onLeftClick(CallbackInfo info) {
		if(isCasting)
			info.cancel();

		if(player != null && !player.isSpectator() && player.getMainHandStack().getItem() instanceof StaffItem && player.getMainHandStack().getCooldown() == 0 && !isCasting) {
			if(player.getAttackCooldownProgress(getTickDelta()) == 1F) {
				doAttack();
				timer = 20;
				pattern.add(Pattern.LEFT);
				player.swingHand(Hand.MAIN_HAND);
				player.world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 1F, 1.3F);

				if(pattern.size() == 3)
					lastMouseDown = options.attackKey;

				player.sendMessage(Arcanus.getSpellInputs(pattern), true);
			}

			info.cancel();
		}
	}

	@Inject(method = "handleInputEvents", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/MinecraftClient;doItemUse()V"
	), cancellable = true)
	public void arcanuscontinuum$onRightClick(CallbackInfo info) {
		if(isCasting)
			info.cancel();

		if(player != null && !player.isSpectator() && player.getMainHandStack().getItem() instanceof StaffItem && player.getMainHandStack().getCooldown() == 0 && !isCasting) {
			if(player.getAttackCooldownProgress(getTickDelta()) == 1F) {
				timer = 20;
				pattern.add(Pattern.RIGHT);
				player.swingHand(Hand.MAIN_HAND);
				player.resetLastAttackedTicks();
				player.world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 1F, 1.1F);

				if(pattern.size() == 3)
					lastMouseDown = options.useKey;

				player.sendMessage(Arcanus.getSpellInputs(pattern), true);
			}

			info.cancel();
		}
	}

	@Override
	public boolean isCasting() {
		return isCasting;
	}
}
