package dev.cammiescorner.arcanuscontinuum.mixin.client;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Pattern;
import dev.cammiescorner.arcanuscontinuum.client.utils.ClientUtils;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.packets.c2s.CastSpellPacket;
import dev.cammiescorner.arcanuscontinuum.common.packets.c2s.SetCastingPacket;
import dev.cammiescorner.arcanuscontinuum.common.packets.c2s.SyncPatternPacket;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBind;
import net.minecraft.client.world.ClientWorld;
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
	@Unique private int mouseDownTimer = 0;
	@Unique private KeyBind lastMouseDown = null;
	@Unique private final List<Pattern> pattern = new ArrayList<>(3);

	@Shadow @Nullable public ClientPlayerEntity player;
	@Shadow @Final public GameOptions options;
	@Shadow @Nullable public ClientWorld world;

	@Shadow public abstract float getTickDelta();
	@Shadow protected abstract boolean doAttack();

	@Inject(method = "tick", at = @At("HEAD"))
	public void tick(CallbackInfo info) {
		if(player == null)
			return;

		ItemStack stack = player.getMainHandStack();

		if(timer == 0 || (lastMouseDown != null && !lastMouseDown.isPressed()) || ArcanusComponents.getStunTimer(player) > 0) {
			pattern.clear();
			SyncPatternPacket.send(pattern);
			lastMouseDown = null;
			isCasting = false;
			timer = 0;

			if(ArcanusComponents.getStunTimer(player) > 0)
				player.resetLastAttackedTicks();
		}

		if(stack.getItem() instanceof StaffItem staff && (ArcanusComponents.getMana(player) > 0 || player.isCreative())) {
			if(timer > 0 && pattern.size() >= 3) {
				isCasting = lastMouseDown != null && lastMouseDown.isPressed();

				if(isCasting) {
					mouseDownTimer++;

					if(player.getItemCooldownManager().getCooldownProgress(staff, getTickDelta()) == 0) {
						int index = Arcanus.getSpellIndex(pattern);
						CastSpellPacket.send(index);
						timer = 20;
					}
				}
				else {
					timer = 0;
					mouseDownTimer = 0;
				}
			}
		}
		else {
			timer = 0;
		}

		if(isCasting() && !ArcanusComponents.isCasting(player) && mouseDownTimer > 5)
			SetCastingPacket.send(true);
		if((!isCasting() || ArcanusComponents.getMana(player) <= 0) && ArcanusComponents.isCasting(player))
			SetCastingPacket.send(false);

		if(timer > 0 && player.getAttackCooldownProgress(getTickDelta()) == 1F && player.getItemCooldownManager().getCooldownProgress(stack.getItem(), getTickDelta()) == 0)
			timer--;
	}

	@Inject(method = "handleInputEvents", at = @At("HEAD"), cancellable = true)
	private void arcanuscontinuum$handleInputEvents(CallbackInfo info) {
		if(ArcanusComponents.getStunTimer(player) > 0)
			info.cancel();
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

		if(player != null && !player.isSpectator() && player.getMainHandStack().getItem() instanceof StaffItem staff) {
			if(player.getAttackCooldownProgress(getTickDelta()) == 1F && player.getItemCooldownManager().getCooldownProgress(staff, getTickDelta()) == 0 && ArcanusComponents.getMana(player) > 0 && !isCasting) {
				timer = 20;
				pattern.add(Pattern.LEFT);
				SyncPatternPacket.send(pattern);
				player.swingHand(Hand.MAIN_HAND);
				player.resetLastAttackedTicks();
				player.world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 1F, 1.3F, 1L);

				if(pattern.size() >= 3)
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

		if(player != null && !player.isSpectator() && player.getMainHandStack().getItem() instanceof StaffItem staff) {
			if(player.getAttackCooldownProgress(getTickDelta()) == 1F && player.getItemCooldownManager().getCooldownProgress(staff, getTickDelta()) == 0 && ArcanusComponents.getMana(player) > 0 && !isCasting) {
				timer = 20;
				pattern.add(Pattern.RIGHT);
				SyncPatternPacket.send(pattern);
				player.swingHand(Hand.MAIN_HAND);
				player.resetLastAttackedTicks();
				player.world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 1F, 1.1F, 1L);

				if(pattern.size() >= 3)
					lastMouseDown = options.useKey;

				player.sendMessage(Arcanus.getSpellInputs(pattern), true);
			}

			info.cancel();
		}
	}

	@Override
	public boolean isCasting() {
		return isCasting && timer > 0;
	}
}
