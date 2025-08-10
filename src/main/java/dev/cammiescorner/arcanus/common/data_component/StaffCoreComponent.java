package dev.cammiescorner.arcanus.common.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.arcana.ArcanaType;
import dev.cammiescorner.arcanus.api.util.ArcanaModifiers;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record StaffCoreComponent(ArcanaModifiers arcanaModifiers) implements TooltipProvider {
	public static final Codec<StaffCoreComponent> CODEC = RecordCodecBuilder.create(coreInstance -> coreInstance.group(
		ArcanaModifiers.CODEC.optionalFieldOf("arcana_modifiers", ArcanaModifiers.empty()).forGetter(StaffCoreComponent::arcanaModifiers)
	).apply(coreInstance, StaffCoreComponent::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, StaffCoreComponent> STREAM_CODEC = StreamCodec.composite(
		ArcanaModifiers.STREAM_CODEC,
		StaffCoreComponent::arcanaModifiers,

		StaffCoreComponent::new
	);

	public static StaffCoreComponent of(double red, double green, double blue, double white, double black) {
		return new StaffCoreComponent(new ArcanaModifiers(red, green, blue, white, black));
	}

	public double modifier(ArcanaType arcanaType) {
		return arcanaModifiers.modifier(arcanaType);
	}

	@Override
	public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag) {
		// TODO replace debug text with proper translatable values
		tooltipAdder.accept(Component.literal("[Staff Core]"));

		for (ArcanaType arcanaType : ArcanaType.values()) {
			// make sure to keep this bit when making translatable!
			var modStr = String.format("%.2f", modifier(arcanaType));
			tooltipAdder.accept(Component.literal("  %s: x%s".formatted(arcanaType.name(), modStr)).withColor(arcanaType.getColor().asIntARGB()));
		}
	}
}
