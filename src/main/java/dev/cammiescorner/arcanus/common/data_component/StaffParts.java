package dev.cammiescorner.arcanus.common.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.common.registry.ArcanusItems;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record StaffParts(ItemStack staffCore, ItemStack staffCap) {
	public static final Codec<StaffParts> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ItemStack.CODEC.fieldOf("staff_core").forGetter(StaffParts::staffCore),
		ItemStack.CODEC.fieldOf("staff_cap").forGetter(StaffParts::staffCap)
	).apply(instance, StaffParts::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, StaffParts> STREAM_CODEC = StreamCodec.composite(
		ItemStack.STREAM_CODEC, StaffParts::staffCore,
		ItemStack.STREAM_CODEC, StaffParts::staffCap,
		StaffParts::new
	);

	public static StaffParts defaultInstance() {
		return new StaffParts(ArcanusItems.WOODEN_STAFF_CORE.get().getDefaultInstance(), ArcanusItems.IRON_STAFF_CAP.get().getDefaultInstance());
	}
}
