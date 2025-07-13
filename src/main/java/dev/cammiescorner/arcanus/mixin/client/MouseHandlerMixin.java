package dev.cammiescorner.arcanus.mixin.client;

import com.mojang.blaze3d.platform.InputConstants;
import commonnetwork.api.Network;
import dev.cammiescorner.arcanus.common.item.BookPouchItem;
import dev.cammiescorner.arcanus.common.item.StaffItem;
import dev.cammiescorner.arcanus.common.networking.serverbound.ServerboundCycleBookPouchPacket;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import dev.cammiescorner.arcanus.common.registry.ArcanusMobEffects;
import dev.cammiescorner.arcanus.common.util.ArcanusHelper;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
	@Shadow @Final private Minecraft minecraft;

	@Inject(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;swapPaint(D)V"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
	private void cycleSpellBookPouch(long windowPointer, double xOffset, double yOffset, CallbackInfo ci, boolean bl, double d, double e, double f, int i, int j, int k) {
		if(minecraft.player.getMainHandItem().getItem() instanceof StaffItem && minecraft.player.isCrouching() && TrinketsApi.getTrinketComponent(minecraft.player).get() instanceof TrinketComponent component && component.isEquipped(ArcanusItems.BOOK_POUCH.get())) {
			ItemStack stack = component.getEquipped(ArcanusItems.BOOK_POUCH.get()).getFirst().getB();
			int index = stack.getOrDefault(ArcanusDataComponents.BOOK_POUCH_INDEX.get(), 0) + k;

			if(index < 0)
				index = BookPouchItem.SLOT_COUNT - 1;
			else if(index >= BookPouchItem.SLOT_COUNT)
				index = 0;

			Network.getNetworkHandler().sendToServer(new ServerboundCycleBookPouchPacket(index));

			ci.cancel();
		}
	}

	@ModifyArgs(method = "turnPlayer", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"
	))
	private void slowMouse(Args args) {
		if(minecraft.player != null && ArcanusHelper.shouldTimeDilate(minecraft.player, minecraft.player.level())) {
			double x = args.get(0);
			double y = args.get(1);
			args.setAll(x * 0.5, y * 0.5);
		}
	}

	@ModifyArgs(method = "turnPlayer", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"
	))
	public void invertMouseMovements(Args args) {
		if(minecraft.player != null && minecraft.player.hasEffect(ArcanusMobEffects.DISCOMBOBULATE.holder())) {
			double x = args.get(0);
			double y = args.get(1);
			args.setAll(-x, -y);
		}
	}

	@ModifyArg(method = "onPress", at = @At(
		value = "INVOKE",
		target = "Lcom/mojang/blaze3d/platform/InputConstants$Type;getOrCreate(I)Lcom/mojang/blaze3d/platform/InputConstants$Key;"
	), index = 0)
	public int invertMouseButtons(int i) {
		if(minecraft.player != null && minecraft.player.hasEffect(ArcanusMobEffects.DISCOMBOBULATE.holder())) {
			return switch(i) {
				case 0 -> {
					KeyMapping.set(InputConstants.Type.MOUSE.getOrCreate(0), false);
					KeyMapping.set(InputConstants.Type.MOUSE.getOrCreate(1), true);
					yield 1;
				}
				case 1 -> {
					KeyMapping.set(InputConstants.Type.MOUSE.getOrCreate(1), false);
					KeyMapping.set(InputConstants.Type.MOUSE.getOrCreate(0), true);
					yield 0;
				}
				default -> i;
			};
		}

		return i;
	}
}
