package dev.cammiescorner.arcanuscontinuum.mixin;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.screen.LecternScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScreenHandlerType.class)
public class ScreenHandlerTypeMixin {
	@Inject(method = "register", at = @At("HEAD"), cancellable = true)
	private static void arcanuscontinuum$register(String id, ScreenHandlerType.Factory<ScreenHandler> factory, CallbackInfoReturnable<ScreenHandlerType<ScreenHandler>> info) {
		if(id.equals("lectern"))
			info.setReturnValue(Registry.register(Registry.SCREEN_HANDLER, id, new ExtendedScreenHandlerType<>((syncId, inventory, buf) -> new LecternScreenHandler(syncId))));
	}
}
