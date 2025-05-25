package dev.cammiescorner.arcanus.common.registry;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spells.Spell;
import dev.cammiescorner.arcanus.api.util.XtraCodecs;
import dev.upcraft.sparkweave.api.color.Color;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.WeatheringCopper;

import java.util.UUID;

public class ArcanusDataComponents {
	public static final RegistryHandler<DataComponentType<?>> DATA_COMPONENTS = RegistryHandler.create(Registries.DATA_COMPONENT_TYPE, Arcanus.MOD_ID);

	// TODO add data component for attached spell books

	public static final RegistrySupplier<DataComponentType<Spell>> SPELL = DATA_COMPONENTS.register("spell", () -> DataComponentType.<Spell>builder()
		.persistent(Spell.CODEC)
		.networkSynchronized(Spell.STREAM_CODEC)
		.cacheEncoding()
		.build()
	);

	public static final RegistrySupplier<DataComponentType<UUID>> OWNER_ID = DATA_COMPONENTS.register("owner_id", () -> DataComponentType.<UUID>builder()
		.persistent(UUIDUtil.CODEC)
		.cacheEncoding()
		.build()
	);

	public static final RegistrySupplier<DataComponentType<Color>> PRIMARY_COLOR = DATA_COMPONENTS.register("primary_color", () -> DataComponentType.<Color>builder()
		.persistent(Color.CODEC)
		.networkSynchronized(XtraCodecs.COLOR_STREAM_CODEC)
		.cacheEncoding()
		.build()
	);

	public static final RegistrySupplier<DataComponentType<Color>> SECONDARY_COLOR = DATA_COMPONENTS.register("secondary_color", () -> DataComponentType.<Color>builder()
		.persistent(Color.CODEC)
		.networkSynchronized(XtraCodecs.COLOR_STREAM_CODEC)
		.cacheEncoding()
		.build()
	);

	public static final RegistrySupplier<DataComponentType<WeatheringCopper.WeatherState>> WEATHER_STATE = DATA_COMPONENTS.register("weather_state", () -> DataComponentType.<WeatheringCopper.WeatherState>builder()
		.persistent(WeatheringCopper.WeatherState.CODEC)
		.networkSynchronized(XtraCodecs.WEATHER_STATE_STREAM_CODEC)
		.cacheEncoding()
		.build()
	);

	public static final RegistrySupplier<DataComponentType<Boolean>> WAXED = DATA_COMPONENTS.register("waxed", () -> DataComponentType.<Boolean>builder()
		.persistent(Codec.BOOL)
		.cacheEncoding()
		.build()
	);
}
