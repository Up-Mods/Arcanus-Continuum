package dev.cammiescorner.arcanus.api.staff;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import dev.cammiescorner.arcanus.common.registry.ArcanusStaffCaps;
import net.minecraft.Util;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class StaffCap implements StaffComponent {
	public static final Codec<StaffCap> CODEC = RecordCodecBuilder.create(capInstance -> capInstance.group(
		Codec.DOUBLE.fieldOf("potency").forGetter(StaffCap::potency),
		Codec.BOOL.optionalFieldOf("inert", false).forGetter(StaffCap::inert)
	).apply(capInstance, StaffCap::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, StaffCap> STREAM_CODEC = ByteBufCodecs.registry(ArcanusRegistries.STAFF_CAP);
	private ResourceLocation resourceLocation;
	private ResourceLocation itemModelLocation;
	private ResourceLocation staffModelLocation;
	private String descriptionId;
	private String staffId;
	private final double potency;
	private final boolean inert;

	public StaffCap(double potency, boolean inert) {
		this.potency = potency;
		this.inert = inert;
	}

	public StaffCap(double potency) {
		this(potency, false);
	}

	@Override
	public ResourceLocation getResourceLocation() {
		if(resourceLocation == null)
			resourceLocation = ArcanusStaffCaps.REGISTRY.getKey(this);

		return resourceLocation;
	}

	@Override
	public ResourceLocation getItemModelLocation() {
		if(itemModelLocation == null)
			itemModelLocation = getResourceLocation().withPrefix("item/arcanus/staff_cap/");

		return itemModelLocation;
	}

	@Override
	public ResourceLocation getStaffModelLocation() {
		if(staffModelLocation == null)
			staffModelLocation = getResourceLocation().withPrefix("arcanus/staff_part/staff_core/");

		return staffModelLocation;
	}

	@Override
	public String getDescriptionId() {
		if(descriptionId == null)
			descriptionId = Util.makeDescriptionId("item", ArcanusStaffCaps.REGISTRY.getKey(this));

		return descriptionId;
	}

	@Override
	public String getStaffId() {
		if(staffId == null)
			staffId = Util.makeDescriptionId("staff", ArcanusStaffCaps.REGISTRY.getKey(this)) + "ped";

		return staffId;
	}

	public double potency() {
		return potency;
	}

	public boolean inert() {
		return inert;
	}
}
