package dev.cammiescorner.arcanus.api.staff;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import dev.cammiescorner.arcanus.api.mana.ManaType;
import dev.cammiescorner.arcanus.api.util.ManaModifiers;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;

public record StaffCore(ManaModifiers manaModifiers) {
	public static final Codec<StaffCore> CODEC = RecordCodecBuilder.create(coreInstance -> coreInstance.group(
		ManaModifiers.CODEC.optionalFieldOf("mana_modifiers", ManaModifiers.empty()).forGetter(StaffCore::manaModifiers)
	).apply(coreInstance, StaffCore::new));
	public static final Codec<Holder<StaffCore>> HOLDER_CODEC = RegistryFixedCodec.create(ArcanusRegistries.STAFF_CORE);
	public static final StreamCodec<RegistryFriendlyByteBuf, StaffCore> STREAM_CODEC = ByteBufCodecs.registry(ArcanusRegistries.STAFF_CORE);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<StaffCore>> STREAM_HOLDER_CODEC = ByteBufCodecs.holderRegistry(ArcanusRegistries.STAFF_CORE);

	public static ResourceLocation getItemModelLocation(Holder.Reference<StaffCore> holder) {
		return holder.key().location().withPrefix("item/arcanus/staff_core/");
	}

	public static ResourceLocation getStaffModelLocation(Holder.Reference<StaffCore> holder) {
		return holder.key().location().withPrefix("arcanus/staff_part/staff_core/");
	}

	public static String getDescriptionId(Holder.Reference<StaffCore> holder) {
		return Util.makeDescriptionId("item", holder.key().location());
	}

	public static String getStaffDescriptionId(Holder.Reference<StaffCore> holder) {
		return Util.makeDescriptionId("staff", holder.key().location()) + "_staff";
	}

	public double modifier(ManaType manaType) {
		return manaModifiers.modifier(manaType);
	}
}
