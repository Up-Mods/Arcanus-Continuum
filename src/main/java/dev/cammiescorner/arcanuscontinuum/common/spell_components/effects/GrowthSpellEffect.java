package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GrowthSpellEffect extends SpellEffect {
	public GrowthSpellEffect(SpellType type, ParticleEffect particle, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(type, particle, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, World world, HitResult target, List<SpellEffect> effects, StaffItem staffItem, ItemStack stack, double potency) {
		int growthCount = (int) (effects.stream().filter(effect -> effect == ArcanusSpellComponents.GROWTH).count() * potency);

		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;

			if(entityHit.getEntity() instanceof AnimalEntity animal && animal.isBaby())
				animal.growUp(PassiveEntity.m_eyazmlyb(-animal.getBreedingAge()) * growthCount, true);
		}
		else if(target.getType() == HitResult.Type.BLOCK) {
			BlockHitResult blockHit = (BlockHitResult) target;
			BlockPos pos = blockHit.getBlockPos().offset(blockHit.getSide());

			for(int i = 0; i < growthCount; i++) {
				BoneMealItem.useOnFertilizable(ItemStack.EMPTY, world, pos);
				BoneMealItem.useOnGround(ItemStack.EMPTY, world, pos, blockHit.getSide());
			}
		}
	}
}
