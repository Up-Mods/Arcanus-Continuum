package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import com.google.common.collect.Sets;
import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class ExplosionSpellShape extends SpellShape {
	public ExplosionSpellShape(Weight weight, double manaCost, int coolDown, int minLevel) {
		super(weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void cast(LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, StaffItem staffItem, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex) {
		world.emitGameEvent(caster, GameEvent.EXPLODE, new Vec3d(castFrom.getX(), castFrom.getY(), castFrom.getZ()));
		Set<BlockPos> affectedBlocks = Sets.newHashSet();
		int i = 16;

		for(int j = 0; j < i; ++j) {
			for(int k = 0; k < i; ++k) {
				for(int l = 0; l < i; ++l) {
					if(j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
						double d = j / 15F * 2F - 1F;
						double e = k / 15F * 2F - 1F;
						double f = l / 15F * 2F - 1F;
						double g = Math.sqrt(d * d + e * e + f * f);
						d /= g;
						e /= g;
						f /= g;
						float h = 5 * (0.7F + world.random.nextFloat() * 0.6F);
						double m = castFrom.getX();
						double n = castFrom.getY();
						double o = castFrom.getZ();

						for(float p = 0.3F; h > 0F; h -= 0.225F) {
							BlockPos blockPos = new BlockPos(m, n, o);

							if(!world.isInBuildLimit(blockPos))
								break;

							if(!world.isAir(blockPos) && world.isTopSolid(blockPos, caster))
								affectedBlocks.add(blockPos);

							m += d * 0.3F;
							n += e * 0.3F;
							o += f * 0.3F;
						}
					}
				}
			}
		}

		int k = MathHelper.floor(castFrom.getX() - 10 - 1);
		int l = MathHelper.floor(castFrom.getX() + 10 + 1);
		int r = MathHelper.floor(castFrom.getY() - 10 - 1);
		int s = MathHelper.floor(castFrom.getY() + 10 + 1);
		int t = MathHelper.floor(castFrom.getZ() - 10 - 1);
		int u = MathHelper.floor(castFrom.getZ() + 10 + 1);
		List<Entity> list = world.getOtherEntities(caster, new Box(k, r, t, l, s, u));

		for(Entity entity : list)
			for(SpellEffect effect : effects)
				effect.effect(caster, world, new EntityHitResult(entity), effects, staffItem, stack, caster.getAttributeValue(ArcanusEntityAttributes.SPELL_POTENCY));

		for(BlockPos blockPos : affectedBlocks)
			for(SpellEffect effect : effects)
				effect.effect(caster, world, new BlockHitResult(Vec3d.ofCenter(blockPos), Direction.UP, blockPos, true), effects, staffItem, stack, caster.getAttributeValue(ArcanusEntityAttributes.SPELL_POTENCY));

		world.playSound(null, castFrom.getX(), castFrom.getY(), castFrom.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4F, (1F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F, 1L);
		world.spawnParticles(ParticleTypes.EXPLOSION_EMITTER, castFrom.getX(), castFrom.getY(), castFrom.getZ(), 1, 1, 0, 0, 1);
		castNext(caster, castFrom, castSource, world, staffItem, stack, effects, spellGroups, groupIndex);
	}
}
