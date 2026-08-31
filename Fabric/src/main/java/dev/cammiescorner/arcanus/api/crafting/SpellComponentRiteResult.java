package dev.cammiescorner.arcanus.api.crafting;

import com.mojang.serialization.MapCodec;
import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.registry.ArcanusRiteResultTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public final class SpellComponentRiteResult extends RiteResult {
	public static final MapCodec<SpellComponentRiteResult> CODEC = MapCodec.assumeMapUnsafe(SpellComponent.CODEC).xmap(SpellComponentRiteResult::new, SpellComponentRiteResult::getSpellComponent);
	public static final StreamCodec<RegistryFriendlyByteBuf, SpellComponentRiteResult> STREAM_CODEC = SpellComponent.STREAM_CODEC.map(SpellComponentRiteResult::new, SpellComponentRiteResult::getSpellComponent);
	private final SpellComponent spellComponent;

	public SpellComponentRiteResult(SpellComponent spellComponent) {
		this.spellComponent = spellComponent;
	}

	public SpellComponent getSpellComponent() {
		return spellComponent;
	}

	@Override
	public Type<?> getType() {
		return ArcanusRiteResultTypes.SPELL_COMPONENT.get();
	}
}
