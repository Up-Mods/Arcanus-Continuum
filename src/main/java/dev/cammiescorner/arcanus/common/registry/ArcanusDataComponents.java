package dev.cammiescorner.arcanus.common.registry;

import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spell.Spell;
import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.api.staff.StaffCap;
import dev.cammiescorner.arcanus.api.staff.StaffCore;
import dev.cammiescorner.arcanus.common.data_component.BookPouchComponent;
import dev.cammiescorner.arcanus.common.data_component.SpellBookComponent;
import dev.cammiescorner.arcanus.common.item.BookPouchItem;
import dev.cammiescorner.arcanus.common.util.XtraCodecs;
import dev.upcraft.sparkweave.api.color.Color;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.block.WeatheringCopper;

import java.util.UUID;

public class ArcanusDataComponents {
	public static final RegistryHandler<DataComponentType<?>> DATA_COMPONENTS = RegistryHandler.create(Registries.DATA_COMPONENT_TYPE, Arcanus.MOD_ID);

	public static final RegistrySupplier<DataComponentType<Boolean>> HOOD_DOWN = DATA_COMPONENTS.register("hood_down", () -> DataComponentType.<Boolean>builder()
		.persistent(Codec.BOOL)
		.networkSynchronized(ByteBufCodecs.BOOL)
		.cacheEncoding()
		.build()
	);

	public static final RegistrySupplier<DataComponentType<Holder<StaffCore>>> STAFF_CORE = DATA_COMPONENTS.register("staff_core", () -> DataComponentType.<Holder<StaffCore>>builder()
		.persistent(StaffCore.HOLDER_CODEC)
		.networkSynchronized(StaffCore.STREAM_HOLDER_CODEC)
		.cacheEncoding()
		.build()
	);

	public static final RegistrySupplier<DataComponentType<Holder<StaffCap>>> STAFF_CAP = DATA_COMPONENTS.register("staff_cap", () -> DataComponentType.<Holder<StaffCap>>builder()
		.persistent(StaffCap.HOLDER_CODEC)
		.networkSynchronized(StaffCap.STREAM_HOLDER_CODEC)
		.cacheEncoding()
		.build()
	);

	public static final RegistrySupplier<DataComponentType<SpellComponent>> SPELL_COMPONENT = DATA_COMPONENTS.register("spell_component", () -> DataComponentType.<SpellComponent>builder()
		.persistent(SpellComponent.CODEC)
		.networkSynchronized(SpellComponent.STREAM_CODEC)
		.cacheEncoding()
		.build()
	);

	public static final RegistrySupplier<DataComponentType<Spell>> SPELL = DATA_COMPONENTS.register("spell", () -> DataComponentType.<Spell>builder()
		.persistent(Spell.CODEC)
		.networkSynchronized(Spell.STREAM_CODEC)
		.cacheEncoding()
		.build()
	);

	public static final RegistrySupplier<DataComponentType<SpellBookComponent>> SPELL_BOOK = DATA_COMPONENTS.register("spell_list", () -> DataComponentType.<SpellBookComponent>builder()
		.persistent(SpellBookComponent.CODEC)
		.networkSynchronized(SpellBookComponent.STREAM_CODEC)
		.cacheEncoding()
		.build()
	);

	public static final RegistrySupplier<DataComponentType<BookPouchComponent>> BOOK_POUCH = DATA_COMPONENTS.register("book_pouch", () -> DataComponentType.<BookPouchComponent>builder()
		.persistent(BookPouchComponent.CODEC)
		.networkSynchronized(BookPouchComponent.STREAM_CODEC)
		.cacheEncoding()
		.build()
	);

	public static final RegistrySupplier<DataComponentType<Integer>> BOOK_POUCH_INDEX = DATA_COMPONENTS.register("book_pouch_index", () -> DataComponentType.<Integer>builder()
		.persistent(Codec.intRange(0, BookPouchItem.SLOT_COUNT - 1))
		.networkSynchronized(StreamCodec.of(FriendlyByteBuf::writeVarInt, FriendlyByteBuf::readVarInt))
		.cacheEncoding()
		.build()
	);

	public static final RegistrySupplier<DataComponentType<UUID>> OWNER_ID = DATA_COMPONENTS.register("owner_id", () -> DataComponentType.<UUID>builder()
		.persistent(UUIDUtil.CODEC)
		.build()
	);

	public static final RegistrySupplier<DataComponentType<Color>> PRIMARY_COLOR = DATA_COMPONENTS.register("primary_color", () -> DataComponentType.<Color>builder()
		.persistent(Color.CODEC)
		.networkSynchronized(Color.STREAM_CODEC)
		.cacheEncoding()
		.build()
	);

	public static final RegistrySupplier<DataComponentType<Color>> SECONDARY_COLOR = DATA_COMPONENTS.register("secondary_color", () -> DataComponentType.<Color>builder()
		.persistent(Color.CODEC)
		.networkSynchronized(Color.STREAM_CODEC)
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
