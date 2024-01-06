package dev.cammiescorner.arcanuscontinuum.common.enchantments;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class EffectTypeProficiencyEnchantment extends Enchantment {
	public EffectTypeProficiencyEnchantment(Rarity weight, EquipmentSlot[] slotTypes) {
		super(weight, EnchantmentTarget.TRIDENT, slotTypes);
	}

	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		return stack.isIn(ArcanusTags.STAVES);
	}
}
