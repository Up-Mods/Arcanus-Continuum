package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.entities.living.NecroSkeletonEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusItems;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NecromancySpellEffect extends SpellEffect {
	public NecromancySpellEffect(SpellType type, ParticleEffect particle, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(type, particle, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, World world, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() != HitResult.Type.MISS) {
			int effectCount = (int) effects.stream().filter(effect -> effect == ArcanusSpellComponents.NECROMANCY).count();
			double healthMultiplier = 1 + (effectCount - 1) * (-0.075);

			for(int i = 0; i < effectCount; i++) {
				NecroSkeletonEntity skeleton = ArcanusEntities.NECRO_SKELETON.create(world);

				if(skeleton != null) {
					skeleton.setPosition(target.getPos());
					skeleton.setMaxHealth(20 * healthMultiplier * potency);

					if(caster != null)
						skeleton.setOwner(caster);

					skeleton.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_AXE));
					skeleton.equipStack(EquipmentSlot.HEAD, new ItemStack(ArcanusItems.WIZARD_HAT));

					world.spawnEntity(skeleton);
				}
			}
		}
	}

	@Override
	public boolean shouldTriggerOnceOnExplosion() {
		return true;
	}
}
