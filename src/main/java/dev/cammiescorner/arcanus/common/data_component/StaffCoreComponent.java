package dev.cammiescorner.arcanus.common.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.mana.ManaType;
import dev.cammiescorner.arcanus.api.util.ManaModifiers;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record StaffCoreComponent(ManaModifiers manaModifiers) implements TooltipProvider {
	public static final Codec<StaffCoreComponent> CODEC = RecordCodecBuilder.create(coreInstance -> coreInstance.group(
		ManaModifiers.CODEC.optionalFieldOf("mana_modifiers", ManaModifiers.empty()).forGetter(StaffCoreComponent::manaModifiers)
	).apply(coreInstance, StaffCoreComponent::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, StaffCoreComponent> STREAM_CODEC = StreamCodec.composite(
		ManaModifiers.STREAM_CODEC,
		StaffCoreComponent::manaModifiers,

		StaffCoreComponent::new
	);

	public static StaffCoreComponent of(double red, double green, double blue, double white, double black) {
		return new StaffCoreComponent(new ManaModifiers(red, green, blue, white, black));
	}

	public double modifier(ManaType manaType) {
		return manaModifiers.modifier(manaType);
	}

	@Override
	public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag) {
		// TODO replace debug text with proper translatable values
		tooltipAdder.accept(Component.literal("[Staff Core]"));

		for (ManaType manaType : ManaType.values()) {
			// make sure to keep this bit when making translatable!
			var modStr = String.format("%.2f", modifier(manaType));
			tooltipAdder.accept(Component.literal("  %s: x%s".formatted(manaType.name(), modStr)).withColor(manaType.getColor().asIntARGB()));
		}
	}
}
