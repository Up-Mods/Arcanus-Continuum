package dev.cammiescorner.arcanus.api.crafting;

import com.mojang.serialization.MapCodec;
import dev.cammiescorner.arcanus.common.registry.ArcanusRiteResultTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public final class ItemRiteResult extends RiteResult {

	public static final MapCodec<ItemRiteResult> CODEC = MapCodec.assumeMapUnsafe(ItemStack.CODEC).xmap(ItemRiteResult::new, ItemRiteResult::getItemStack);
	public static final StreamCodec<RegistryFriendlyByteBuf, ItemRiteResult> STREAM_CODEC = ItemStack.STREAM_CODEC.map(ItemRiteResult::new, ItemRiteResult::getItemStack);
	private final ItemStack itemStack;

	public ItemRiteResult(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	@Override
	public Type<?> getType() {
		return ArcanusRiteResultTypes.ITEM.get();
	}
}
