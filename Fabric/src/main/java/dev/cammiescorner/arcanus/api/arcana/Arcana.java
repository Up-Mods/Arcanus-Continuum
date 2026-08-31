package dev.cammiescorner.arcanus.api.arcana;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import dev.cammiescorner.arcanus.registry.ArcanusArcana;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public interface Arcana {
	Codec<Arcana> CODEC = ArcanusArcana.REGISTRY.byNameCodec();
	StreamCodec<RegistryFriendlyByteBuf, Arcana> STREAM_CODEC = ByteBufCodecs.registry(ArcanusRegistries.ARCANA);

	Color color();

	default String getDescriptionId() {
		return "arcana." + ArcanusArcana.REGISTRY.getKey(this).toLanguageKey();
	}

	default Component getName() {
		return Component.translatable(getDescriptionId());
	}
}
