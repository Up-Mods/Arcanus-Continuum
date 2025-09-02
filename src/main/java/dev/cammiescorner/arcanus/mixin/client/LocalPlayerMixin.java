package dev.cammiescorner.arcanus.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.GameProfile;
import dev.cammiescorner.arcanus.common.block.WardedJarBlock;
import dev.cammiescorner.arcanus.common.registry.ArcanusBlocks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
	public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) { super(clientLevel, gameProfile); }

	@ModifyReturnValue(method = "isUnderWater", at = @At("RETURN"))
	private boolean drownInAJar(boolean original) {
		BlockState state = level().getBlockState(blockPosition());
		AABB insideJar = WardedJarBlock.INSIDE.bounds().inflate(0.001).move(blockPosition());

		if(state.is(ArcanusBlocks.WARDED_JAR.get()) && insideJar.contains(position()) && insideJar.contains(getEyePosition()))
			return getBlockY() + (0.078125 * state.getValue(WardedJarBlock.LEVEL)) + 0.0625 >= getEyeY();

		return original;
	}
}
