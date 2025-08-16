package dev.cammiescorner.arcanus.api.arcana;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import dev.cammiescorner.arcanus.common.registry.ArcanusCompoundArcana;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

public interface Arcana extends StringRepresentable {
	Codec<Arcana> CODEC = ArcanusCompoundArcana.REGISTRY.byNameCodec();
	StreamCodec<RegistryFriendlyByteBuf, Arcana> STREAM_CODEC = ByteBufCodecs.registry(ArcanusRegistries.ARCANA);

	ChatFormatting formatting();
	Color color();
}
