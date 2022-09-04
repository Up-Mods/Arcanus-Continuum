package dev.cammiescorner.arcanuscontinuum.common.spells.healing_touch;

import dev.cammiescorner.arcanuscontinuum.api.spells.Spell;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class InvigoratingAuraSpell extends Spell {
	public InvigoratingAuraSpell(@Nullable Spell parent, Weight weight, double manaCost, int cooldown) {
		super(parent, weight, manaCost, cooldown);
	}

	@Override
	public void cast(LivingEntity caster, World world, StaffItem staff) {

	}
}
