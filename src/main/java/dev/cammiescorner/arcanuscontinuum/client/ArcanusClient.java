package dev.cammiescorner.arcanuscontinuum.client;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusItems;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.DyeableItem;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class ArcanusClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableItem) stack.getItem()).getColor(stack) : 0xffffff,
				ArcanusItems.WOODEN_STAFF, ArcanusItems.AMETHYST_SHARD_STAFF, ArcanusItems.QUARTZ_SHARD_STAFF,
				ArcanusItems.ENDER_SHARD_STAFF, ArcanusItems.ECHO_SHARD_STAFF
		);
	}
}
