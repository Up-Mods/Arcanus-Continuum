package dev.cammiescorner.arcanus.api.staff;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;

public class StaffCap {
	public static final Codec<StaffCap> CODEC = RecordCodecBuilder.create(capInstance -> capInstance.group(
		Codec.DOUBLE.fieldOf("potency").forGetter(StaffCap::potency),
		Codec.BOOL.optionalFieldOf("inert", false).forGetter(StaffCap::inert)
	).apply(capInstance, StaffCap::new));
	public static final Codec<Holder<StaffCap>> HOLDER_CODEC = RegistryFixedCodec.create(ArcanusRegistries.STAFF_CAP);
	public static final StreamCodec<RegistryFriendlyByteBuf, StaffCap> STREAM_CODEC = ByteBufCodecs.registry(ArcanusRegistries.STAFF_CAP);
	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<StaffCap>> STREAM_HOLDER_CODEC = ByteBufCodecs.holderRegistry(ArcanusRegistries.STAFF_CAP);
	private String staffDescriptionId;
	private final double potency;
	private final boolean inert;

	public StaffCap(double potency, boolean inert) {
		this.potency = potency;
		this.inert = inert;
	}

	public static ResourceLocation getItemModelLocation(Holder.Reference<StaffCap> holder) {
		return holder.key().location().withPrefix("item/arcanus/staff_cap/");
	}

	public static ResourceLocation getStaffModelLocation(Holder.Reference<StaffCap> holder) {
		return holder.key().location().withPrefix("arcanus/staff_part/staff_core/");
	}

	public static String getDescriptionId(Holder.Reference<StaffCap> holder) {
		return Util.makeDescriptionId("item", holder.key().location());
	}

	public String getStaffDescriptionId(Holder.Reference<StaffCap> holder) {
		if(staffDescriptionId == null)
			staffDescriptionId = Util.makeDescriptionId("staff", holder.key().location()) + "ped";

		return staffDescriptionId;
	}

	public double potency() {
		return potency;
	}

	public boolean inert() {
		return inert;
	}
}
