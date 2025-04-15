package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;

public class ArcanusEnchantments {
	public static final RegistryHandler<Enchantment> ENCHANTMENTS = RegistryHandler.create(Registries.ENCHANTMENT, Arcanus.MOD_ID);
}
