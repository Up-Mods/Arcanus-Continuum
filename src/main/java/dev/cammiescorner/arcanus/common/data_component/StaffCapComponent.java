package dev.cammiescorner.arcanus.common.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record StaffCapComponent(double potency, boolean isInert) implements TooltipProvider {
	public static final Codec<StaffCapComponent> CODEC = RecordCodecBuilder.create(capInstance -> capInstance.group(
		Codec.DOUBLE.fieldOf("potency").forGetter(StaffCapComponent::potency),
		Codec.BOOL.optionalFieldOf("inert", false).forGetter(StaffCapComponent::isInert)
	).apply(capInstance, StaffCapComponent::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, StaffCapComponent> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.DOUBLE,
		StaffCapComponent::potency,

		ByteBufCodecs.BOOL,
		StaffCapComponent::isInert,

		StaffCapComponent::new
	);

	public StaffCapComponent(double potency) {
		this(potency, false);
	}

	public StaffCapComponent withInert(boolean isInert) {
		return new StaffCapComponent(this.potency(), isInert);
	}

	public StaffCapComponent withPotency(double potency) {
		return new StaffCapComponent(potency, this.isInert());
	}

	@Override
	public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag) {
		// TODO replace debug text with proper translatable values
		String potencyStr = String.format("%.0f", (1 + potency()) * 100) + "%";
		tooltipAdder.accept(Component.literal("Potency: %s".formatted(potencyStr)));
	}
}
