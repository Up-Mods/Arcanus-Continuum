package dev.cammiescorner.arcanuscontinuum.mixin.client;

import com.mojang.blaze3d.framebuffer.Framebuffer;
import dev.cammiescorner.arcanuscontinuum.client.utils.StencilBuffer;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.opengl.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Objects;

@Mixin(Framebuffer.class)
public abstract class FramebufferMixin implements StencilBuffer {
	@Unique private boolean isStencilBufferEnabled;
	@Shadow public int textureWidth;
	@Shadow public int textureHeight;
	@Shadow public abstract void resize(int width, int height, boolean clearError);

	@Inject(method = "<init>", at = @At("RETURN"))
	private void onInit(boolean useDepth, CallbackInfo info) {
		isStencilBufferEnabled = false;
	}

	@ModifyArgs(method = "create", at = @At(value = "INVOKE",
			target = "Lcom/mojang/blaze3d/platform/GlStateManager;_texImage2D(IIIIIIIILjava/nio/IntBuffer;)V",
			remap = false
	))
	private void modifyTexImage2D(Args args) {
		if(Objects.equals(args.get(2), GL11.GL_DEPTH_COMPONENT) && isStencilBufferEnabled) {
			args.set(2, GL31.GL_DEPTH24_STENCIL8);
			args.set(6, ARBFramebufferObject.GL_DEPTH_STENCIL);
			args.set(7, GL31.GL_UNSIGNED_INT_24_8);
		}
	}

	@ModifyArgs(method = "create", at = @At(value = "INVOKE",
			target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glFramebufferTexture2D(IIIII)V",
			remap = false
	))
	private void modifyFrameBufferTexture2D(Args args) {
		if(Objects.equals(args.get(1), GL30C.GL_DEPTH_ATTACHMENT))
			if(isStencilBufferEnabled)
				args.set(1, GL31.GL_DEPTH_STENCIL_ATTACHMENT);
	}

	@Override
	public boolean arcanuscontinuum$isStencilBufferEnabled() {
		return isStencilBufferEnabled;
	}

	@Override
	public void arcanuscontinuum$enableStencilBufferAndReload(boolean cond) {
		if(isStencilBufferEnabled != cond) {
			isStencilBufferEnabled = cond;
			resize(textureWidth, textureHeight, MinecraftClient.IS_SYSTEM_MAC);
		}
	}
}
