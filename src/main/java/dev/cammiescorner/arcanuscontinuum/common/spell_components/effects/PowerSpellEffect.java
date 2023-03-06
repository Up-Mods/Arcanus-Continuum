package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PowerSpellEffect extends SpellEffect {
	public PowerSpellEffect(SpellType type, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(type, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, World world, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.BLOCK) {
			BlockHitResult blockHit = (BlockHitResult) target;
			BlockPos pos = blockHit.getBlockPos();
			BlockPos pos1 = pos.offset(blockHit.getSide());
			BlockState state = world.getBlockState(pos);
			BlockState state1 = world.getBlockState(pos1);
			int maxPower = (int) (4 + effects.stream().filter(effect -> effect == ArcanusSpellComponents.POWER).count() * potency);

			if(state.getProperties().contains(Properties.POWER)) {
				world.emitGameEvent(caster, GameEvent.BLOCK_ACTIVATE, pos);
				world.setBlockState(pos, state.with(Properties.POWER, maxPower), Block.NOTIFY_ALL);
				world.updateNeighborsAlways(pos, state.getBlock());
				world.scheduleBlockTick(pos, state.getBlock(), 2);
			}
			if(state.getProperties().contains(Properties.POWERED)) {
				world.emitGameEvent(caster, GameEvent.BLOCK_ACTIVATE, pos);
				world.setBlockState(pos, state.with(Properties.POWERED, true), Block.NOTIFY_ALL);
				world.updateNeighborsAlways(pos, state.getBlock());
				world.scheduleBlockTick(pos, state.getBlock(), 2);
			}
			if(state1.getProperties().contains(Properties.POWER)) {
				world.emitGameEvent(caster, GameEvent.BLOCK_ACTIVATE, pos1);
				world.setBlockState(pos1, state1.with(Properties.POWER, maxPower), Block.NOTIFY_ALL);
				world.updateNeighborsAlways(pos1, state1.getBlock());
				world.scheduleBlockTick(pos1, state1.getBlock(), 2);
			}
			if(state1.getProperties().contains(Properties.POWERED)) {
				world.emitGameEvent(caster, GameEvent.BLOCK_ACTIVATE, pos1);
				world.setBlockState(pos1, state1.with(Properties.POWERED, true), Block.NOTIFY_ALL);
				world.updateNeighborsAlways(pos1, state1.getBlock());
				world.scheduleBlockTick(pos1, state1.getBlock(), 2);
			}
		}
	}
}
