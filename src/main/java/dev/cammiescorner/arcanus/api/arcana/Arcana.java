package dev.cammiescorner.arcanus.api.arcana;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import dev.cammiescorner.arcanus.common.registry.ArcanusArcana;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public interface Arcana {
	Codec<Arcana> CODEC = ArcanusArcana.REGISTRY.byNameCodec();
	StreamCodec<RegistryFriendlyByteBuf, Arcana> STREAM_CODEC = ByteBufCodecs.registry(ArcanusRegistries.ARCANA);

	ChatFormatting formatting();
	Color color();

	default String translationKey() {
		return "arcana." + ArcanusArcana.REGISTRY.getKey(this).toLanguageKey();
	}
}
