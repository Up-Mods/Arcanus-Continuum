package dev.cammiescorner.arcanus.api.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import dev.upcraft.sparkweave.api.registry.RegistryHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public abstract class RiteResult {

	public static final Codec<RiteResult> CODEC = Codec.lazyInitialized(() -> RegistryHelper.getBuiltinRegistry(ArcanusRegistries.RITE_RESULT_TYPE).byNameCodec().dispatch(RiteResult::getType, Type::codec));
	@SuppressWarnings({"unchecked"})
	public static final StreamCodec<RegistryFriendlyByteBuf, RiteResult> STREAM_CODEC = StreamCodec.ofMember((riteResult, buf) -> {
		Type<RiteResult> type = (Type<RiteResult>) riteResult.getType();
		Type.STREAM_CODEC.encode(buf, type);
		type.streamCodec().encode(buf, riteResult);
	}, buf -> {
		Type<? extends RiteResult> type = Type.STREAM_CODEC.decode(buf);
		return type.streamCodec().decode(buf);
	});

	public abstract Type<?> getType();

	public record Type<T extends RiteResult>(MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
		public static final StreamCodec<RegistryFriendlyByteBuf, RiteResult.Type<?>> STREAM_CODEC = ByteBufCodecs.registry(ArcanusRegistries.RITE_RESULT_TYPE);
	}
}
