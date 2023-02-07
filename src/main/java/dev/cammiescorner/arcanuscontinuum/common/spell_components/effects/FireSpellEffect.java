package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FireSpellEffect extends SpellEffect {
	public FireSpellEffect(SpellType type, ParticleEffect particle, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(type, particle, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, World world, HitResult target, List<SpellEffect> effects, StaffItem staffItem, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;

			if(entityHit.getEntity() instanceof LivingEntity livingEntity)
				livingEntity.setOnFireFor((int) (3 * effects.stream().filter(effect -> effect == ArcanusSpellComponents.FIRE).count() * potency));
		}
		else if(target.getType() == HitResult.Type.BLOCK) {
			BlockHitResult blockHit = (BlockHitResult) target;

			world.setBlockState(blockHit.getBlockPos().offset(blockHit.getSide()), Blocks.FIRE.getDefaultState().with(switch(blockHit.getSide()) {
				case UP, DOWN -> FireBlock.UP;
				case NORTH -> FireBlock.SOUTH;
				case SOUTH -> FireBlock.NORTH;
				case WEST -> FireBlock.EAST;
				case EAST -> FireBlock.WEST;
			}, blockHit.getSide() != Direction.UP));
		}
	}
}
