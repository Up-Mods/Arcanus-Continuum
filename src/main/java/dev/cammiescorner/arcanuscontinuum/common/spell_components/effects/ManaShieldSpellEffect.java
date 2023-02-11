package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.ManaShieldEntity;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaShieldSpellEffect extends SpellEffect {
	public ManaShieldSpellEffect(SpellType type, ParticleEffect particle, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(type, particle, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, World world, HitResult target, List<SpellEffect> effects, StaffItem staffItem, ItemStack stack, double potency) {
		if(target.getType() != HitResult.Type.MISS) {
			ManaShieldEntity manaShield = new ManaShieldEntity(ArcanusEntities.MANA_SHIELD, world);

			if(caster != null) {
				manaShield.setProperties(target.getPos().add(0, -0.7, 0), StaffItem.getMagicColour(stack,  switch(caster.getUuidAsString()) {
					case "1b44461a-f605-4b29-a7a9-04e649d1981c" -> 0xff005a; // folly red
					case "6147825f-5493-4154-87c5-5c03c6b0a7c2" -> 0xf2dd50; // lotus gold
					case "63a8c63b-9179-4427-849a-55212e6008bf" -> 0x7cff7c; // moriya green
					case "d5034857-9e8a-44cb-a6da-931caff5b838" -> 0xbd78ff; // upcraft pourble
					default -> 0x68e1ff;
				}), (int) ((100 + 40 * (effects.stream().filter(effect -> effect == ArcanusSpellComponents.MANA_SHIELD).count() - 1)) * potency));
			}

			world.spawnEntity(manaShield);
		}
	}

	@Override
	public boolean shouldTriggerOnceOnExplosion() {
		return true;
	}
}
