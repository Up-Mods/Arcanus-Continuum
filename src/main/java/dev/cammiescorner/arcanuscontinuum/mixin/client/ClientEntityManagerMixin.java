package dev.cammiescorner.arcanuscontinuum.mixin.client;

import dev.cammiescorner.arcanuscontinuum.client.utils.ArcanusEntityIndex;
import net.minecraft.client.world.ClientEntityManager;
import net.minecraft.world.entity.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientEntityManager.class)
public class ClientEntityManagerMixin<T extends EntityLike> {
	@Shadow @Mutable @Final EntityIndex<T> index;
	@Shadow @Mutable @Final private EntityLookup<T> lookup;
	@Shadow @Final SectionedEntityCache<T> cache;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void arcanuscontinuum$replaceEntityIndex(Class<T> entityClass, EntityHandler<T> handler, CallbackInfo info) {
		index = new ArcanusEntityIndex<>(index);
		lookup = new SimpleEntityLookup<>(index, cache);
	}
}
