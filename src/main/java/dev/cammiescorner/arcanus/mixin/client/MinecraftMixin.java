package dev.cammiescorner.arcanus.mixin.client;

import commonnetwork.api.Network;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.Pattern;
import dev.cammiescorner.arcanus.client.ArcanusClient;
import dev.cammiescorner.arcanus.client.util.ClientUtils;
import dev.cammiescorner.arcanus.common.entity.magic.Aggressorb;
import dev.cammiescorner.arcanus.common.item.StaffItem;
import dev.cammiescorner.arcanus.common.networking.serverbound.ServerboundIsCastingPacket;
import dev.cammiescorner.arcanus.common.networking.serverbound.ServerboundShootOrbsPacket;
import dev.cammiescorner.arcanus.common.networking.serverbound.ServerboundSyncPatternPacket;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
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
import java.util.UUID;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements ClientUtils {
	@Unique private boolean isCasting = false;
	@Unique private int timer = 0;
	@Unique private int mouseDownTimer = 0;
	@Unique private KeyMapping lastMouseDown = null;
	@Unique private final List<Pattern> patterns = new ArrayList<>(3);

	@Shadow @Nullable public LocalPlayer player;
	@Shadow @Final public Options options;
	@Shadow @Nullable public ClientLevel level;

	@Shadow public abstract boolean isLocalServer();
	@Shadow public abstract long getFrameTimeNs();

	@Inject(method = "tick", at = @At("HEAD"))
	public void tick(CallbackInfo info) {
		if(player == null)
			return;

		ItemStack stack = player.getMainHandItem();

		if(timer == 0 || (lastMouseDown != null && !lastMouseDown.isDown()) || ArcanusComponents.getStunTimer(player) > 0) {
			patterns.clear();
			Network.getNetworkHandler().sendToServer(new ServerboundSyncPatternPacket(patterns));
			lastMouseDown = null;
			isCasting = false;
			timer = 0;

			if(ArcanusComponents.getStunTimer(player) > 0)
				player.resetAttackStrengthTicker();
		}

		if(stack.getItem() instanceof StaffItem staff) {
			if(timer > 0 && patterns.size() >= 3) {
				isCasting = lastMouseDown != null && lastMouseDown.isDown();

				if(isCasting) {
					mouseDownTimer++;

					if(player.getCooldowns().getCooldownPercent(staff, getFrameTimeNs()) == 0) {
						Network.getNetworkHandler().sendToServer(new ServerboundSyncPatternPacket(patterns));
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
			Network.getNetworkHandler().sendToServer(new ServerboundIsCastingPacket(true));
		if(!isCasting() && ArcanusComponents.isCasting(player))
			Network.getNetworkHandler().sendToServer(new ServerboundIsCastingPacket(false));

		if(timer > 0 && player.getAttackStrengthScale(getFrameTimeNs()) == 1f && player.getCooldowns().getCooldownPercent(stack.getItem(), getFrameTimeNs()) == 0)
			timer--;
	}

	@Inject(method = "handleKeybinds", at = @At("HEAD"), cancellable = true)
	private void handleInputEvents(CallbackInfo info) {
		if(ArcanusComponents.getStunTimer(player) > 0)
			info.cancel();
	}

	@Inject(method = "handleKeybinds", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/client/Minecraft;continueAttack(Z)V"
	), cancellable = true)
	public void onBlockBreak(CallbackInfo info) {
		if(isCasting)
			info.cancel();
	}

	@Inject(method = "handleKeybinds", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/client/Minecraft;startAttack()Z",
		ordinal = 0
	), cancellable = true)
	public void onLeftClick(CallbackInfo info) {
		if(isCasting)
			info.cancel();

		if(player != null && !player.isSpectator() && level != null) {
			if(player.getMainHandItem().getItem() instanceof StaffItem staff) {
				if(player.getAttackStrengthScale(getFrameTimeNs()) >= ((isLocalServer() ? ArcanusConfig.castingSpeedHasCoolDown : ArcanusClient.castingSpeedHasCoolDown) ? 1f : 0.15f) && player.getCooldowns().getCooldownPercent(staff, getFrameTimeNs()) == 0 && !isCasting) {
					timer = 20;
					patterns.add(Pattern.LEFT);
					Network.getNetworkHandler().sendToServer(new ServerboundSyncPatternPacket(patterns));
					player.swing(InteractionHand.MAIN_HAND);
					player.resetAttackStrengthTicker();
					player.level().playSeededSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_BUTTON_CLICK, SoundSource.PLAYERS, 1f, 1.3f, 1L);

					if(patterns.size() >= 3)
						lastMouseDown = options.keyAttack;
				}

				info.cancel();
			}
			else {
				List<UUID> orbIds = player.getComponent(ArcanusComponents.AGGRESSORB_COMPONENT).getOrbs();

				if(!orbIds.isEmpty()) {
					Network.getNetworkHandler().sendToServer(new ServerboundShootOrbsPacket(player.getUUID(), orbIds));
					shootOrbs(orbIds);
				}
			}
		}
	}

	@Inject(method = "handleKeybinds", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/client/Minecraft;startUseItem()V",
		ordinal = 0
	), cancellable = true)
	public void onRightClick(CallbackInfo info) {
		if(isCasting)
			info.cancel();

		if(player != null && !player.isSpectator() && level != null && player.getMainHandItem().getItem() instanceof StaffItem staff) {
			if(player.getAttackStrengthScale(getFrameTimeNs()) >= ((isLocalServer() ? ArcanusConfig.castingSpeedHasCoolDown : ArcanusClient.castingSpeedHasCoolDown) ? 1 : 0.15f) && player.getCooldowns().getCooldownPercent(staff, getFrameTimeNs()) == 0 && !isCasting) {
				timer = 20;
				patterns.add(Pattern.RIGHT);
				Network.getNetworkHandler().sendToServer(new ServerboundSyncPatternPacket(patterns));
				player.swing(InteractionHand.MAIN_HAND);
				player.resetAttackStrengthTicker();
				player.level().playSeededSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_BUTTON_CLICK, SoundSource.PLAYERS, 1f, 1.1f, 1L);

				if(patterns.size() >= 3)
					lastMouseDown = options.keyUse;
			}

			info.cancel();
		}
	}

	@Inject(method = "handleKeybinds", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/client/Minecraft;startUseItem()V",
		ordinal = 1
	), cancellable = true)
	public void onRightClickBlock(CallbackInfo info) {
		if(isCasting || (player != null && !player.isSpectator() && player.getMainHandItem().getItem() instanceof StaffItem))
			info.cancel();
	}

	@Override
	public boolean isCasting() {
		return isCasting && timer > 0;
	}

	@Unique
	private void shootOrbs(List<UUID> orbIds) {
		for(Entity entity : level.entitiesForRendering()) {
			if(entity instanceof Aggressorb orb && orbIds.get(0).equals(entity.getUUID()) && orb.isBoundToTarget()) {
				orb.setBoundToTarget(false);
				orb.setPos(orb.getTarget().getEyePosition());
				orb.shootFromRotation(orb.getTarget(), orb.getTarget().getXRot(), orb.getTarget().getYRot(), 0f, ArcanusConfig.SpellShapes.AggressorbShapeProperties.projectileSpeed, 1f);

				break;
			}
		}
	}
}
