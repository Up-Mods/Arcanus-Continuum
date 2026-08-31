package dev.cammiescorner.arcanus.data_component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.cammiescorner.arcanus.api.util.ArcanaModifiers;
import dev.cammiescorner.arcanus.registry.ArcanusArcana;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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

	public static StaffCoreComponent of(double ignisArcana, double terraArcana, double aquaArcana, double aerArcana, double aetherArcana) {
		Object2DoubleArrayMap<PrimalArcana> map = new Object2DoubleArrayMap<>();

		map.put(ArcanusArcana.IGNIS.get(), ignisArcana);
		map.put(ArcanusArcana.TERRA.get(), terraArcana);
		map.put(ArcanusArcana.AQUA.get(), aquaArcana);
		map.put(ArcanusArcana.AER.get(), aerArcana);
		map.put(ArcanusArcana.AETHER.get(), aetherArcana);

		return new StaffCoreComponent(new ArcanaModifiers(map));
	}

	public double modifier(PrimalArcana primalArcana) {
		return arcanaModifiers.modifier(primalArcana);
	}

	@Override
	public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag, DataComponentGetter components) {
		MutableComponent component = Component.empty();

		ArcanusArcana.primalArcana().forEach(primalArcana -> {
			var modStr = String.format("%.2f", modifier(primalArcana));

			if(!component.equals(Component.empty()))
				component.append(Component.literal(" | ").withStyle(ChatFormatting.GRAY));

			component.append(Component.literal("x" + modStr).withColor(primalArcana.color().asIntARGB()));
		});

		tooltipAdder.accept(component);
	}
}
