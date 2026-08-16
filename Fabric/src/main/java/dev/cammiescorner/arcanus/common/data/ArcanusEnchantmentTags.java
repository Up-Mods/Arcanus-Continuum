package dev.cammiescorner.arcanus.common.data;

import dev.cammiescorner.arcanus.Arcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class ArcanusEnchantmentTags {
	public static final TagKey<Enchantment> MANA_POOL_COMPATIBLE_WITH = TagKey.create(Registries.ENCHANTMENT, Arcanus.id("arcana_pool_compatible_with"));
}
