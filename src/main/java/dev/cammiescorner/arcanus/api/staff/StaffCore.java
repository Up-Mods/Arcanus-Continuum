package dev.cammiescorner.arcanus.api.staff;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import dev.cammiescorner.arcanus.api.util.ManaDiscount;
import dev.upcraft.sparkweave.api.registry.RegistryHelper;
import net.minecraft.Util;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaffCore implements StaffComponent {
	public static final Codec<StaffCore> CODEC = RecordCodecBuilder.create(coreInstance -> coreInstance.group(
		ManaDiscount.CODEC.listOf().optionalFieldOf("mana_discounts", StaffCore.noDiscounts()).forGetter(StaffCore::manaDiscounts)
	).apply(coreInstance, StaffCore::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, StaffCore> STREAM_CODEC = ByteBufCodecs.registry(ArcanusRegistries.STAFF_CORE);
	private ResourceLocation resourceLocation;
	private ResourceLocation itemModelLocation;
	private ResourceLocation staffModelLocation;
	private String descriptionId;
	private String staffId;
	private final List<ManaDiscount> manaDiscounts;

	public StaffCore(ManaDiscount... manaDiscounts) {
		this(Arrays.asList(manaDiscounts));
	}

	private StaffCore(List<ManaDiscount> manaDiscounts) {
		this.manaDiscounts = manaDiscounts;
	}

	@Override
	public ResourceLocation getResourceLocation() {
		if(resourceLocation == null)
			resourceLocation = RegistryHelper.getBuiltinRegistry(ArcanusRegistries.STAFF_CORE).getKey(this);

		return resourceLocation;
	}

	@Override
	public ResourceLocation getItemModelLocation() {
		if(itemModelLocation == null)
			itemModelLocation = getResourceLocation().withPrefix("item/arcanus/staff_core/");

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
			descriptionId = Util.makeDescriptionId("item", getResourceLocation());

		return descriptionId;
	}

	@Override
	public String getStaffId() {
		if(staffId == null)
			staffId = Util.makeDescriptionId("staff", getResourceLocation()) + "_staff";

		return staffId;
	}

	public double discount(ManaType manaType) {
		double discount = 0;

		for(ManaDiscount manaDiscount : manaDiscounts) {
			if(manaDiscount.manaType() == manaType)
				discount = manaDiscount.discount();
		}

		return discount;
	}

	public List<ManaDiscount> manaDiscounts() {
		return List.copyOf(manaDiscounts);
	}

	private static List<ManaDiscount> noDiscounts() {
		ArrayList<ManaDiscount> list = new ArrayList<>();

		for(ManaType value : ManaType.values())
			list.add(new ManaDiscount(value, 0));

		return List.copyOf(list);
	}
}
