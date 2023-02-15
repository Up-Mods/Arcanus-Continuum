package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.blocks.entities.MagicBlockEntity;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusBlocks;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BuildSpellEffect extends SpellEffect {
	public BuildSpellEffect(SpellType type, ParticleEffect particle, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(type, particle, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, World world, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.BLOCK) {
			BlockHitResult blockHit = (BlockHitResult) target;
			BlockPos pos = blockHit.getBlockPos().offset(blockHit.getSide());

			if(world.getBlockState(pos).getMaterial().isReplaceable() && (!(caster instanceof PlayerEntity player) || world.canPlayerModifyAt(player, pos))) {
				world.setBlockState(pos, ArcanusBlocks.MAGIC_BLOCK.getDefaultState(), Block.NOTIFY_LISTENERS);
				world.scheduleBlockTick(pos, world.getBlockState(pos).getBlock(), (int) (220 * effects.stream().filter(effect -> effect == ArcanusSpellComponents.BUILD).count() * potency));

				if(world.getBlockEntity(pos) instanceof MagicBlockEntity magicBlock && caster != null)
					magicBlock.setColour(StaffItem.getMagicColour(caster.getUuidAsString()));
			}
		}
	}
}
