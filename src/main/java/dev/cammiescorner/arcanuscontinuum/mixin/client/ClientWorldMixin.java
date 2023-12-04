package dev.cammiescorner.arcanuscontinuum.mixin.client;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Holder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends World {
	protected ClientWorldMixin(MutableWorldProperties worldProperties, RegistryKey<World> registryKey, DynamicRegistryManager registryManager, Holder<DimensionType> dimension, Supplier<Profiler> profiler, boolean client, boolean debug, long seed, int maxChainedNeighborUpdates) { super(worldProperties, registryKey, registryManager, dimension, profiler, client, debug, seed, maxChainedNeighborUpdates); }

	@WrapWithCondition(method = "syncWorldEvent", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/render/WorldRenderer;processWorldEvent(ILnet/minecraft/util/math/BlockPos;I)V"
	))
	private boolean arcanuscontinuum$noBreakingSoundsOrParticles(WorldRenderer target, int eventId, BlockPos pos, int data) {
		return eventId != 2001 || !ArcanusComponents.isBlockWarded(this, pos);
	}

	@WrapWithCondition(method = "tickEntity", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/entity/Entity;tick()V"
	))
	private boolean arcanuscontinuum$blockEntityTick(Entity entity) {
		return !ArcanusComponents.areUpdatesBlocked(entity);
	}
}
