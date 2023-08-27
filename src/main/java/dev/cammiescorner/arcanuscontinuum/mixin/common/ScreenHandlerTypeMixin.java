package dev.cammiescorner.arcanuscontinuum.mixin.common;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.LecternScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScreenHandlerType.class)
public class ScreenHandlerTypeMixin {
	@Inject(method = "method_17435", at = @At("HEAD"), cancellable = true)
	private static void arcanuscontinuum$register(String id, ScreenHandlerType.Factory<ScreenHandler> factory, CallbackInfoReturnable<ScreenHandlerType<ScreenHandler>> info) {
		if(id.equals("lectern"))
			info.setReturnValue(Registry.register(Registries.SCREEN_HANDLER_TYPE, id, new ExtendedScreenHandlerType<>((syncId, inventory, buf) -> new LecternScreenHandler(syncId))));
	}
}
