package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.blocks.entities.MagicBlockEntity;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusBlocks;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.block.Block;
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
	public void effect(@Nullable LivingEntity caster, World world, HitResult target, List<SpellEffect> effects, StaffItem staffItem, ItemStack stack) {
		if(target.getType() == HitResult.Type.BLOCK) {
			BlockHitResult blockHit = (BlockHitResult) target;
			BlockPos pos = blockHit.getBlockPos().offset(blockHit.getSide());

			if(world.getBlockState(pos).getMaterial().isReplaceable() && (!(caster instanceof PlayerEntity player) || world.canPlayerModifyAt(player, pos))) {
				world.setBlockState(pos, ArcanusBlocks.MAGIC_BLOCK.getDefaultState(), Block.NOTIFY_LISTENERS);
				world.scheduleBlockTick(pos, world.getBlockState(pos).getBlock(), (int) (220 * effects.stream().filter(effect -> effect == ArcanusSpellComponents.BUILD).count()));

				if(world.getBlockEntity(pos) instanceof MagicBlockEntity magicBlock && caster != null) {
					magicBlock.setColour(StaffItem.getMagicColour(stack, switch(caster.getUuidAsString()) {
						case "1b44461a-f605-4b29-a7a9-04e649d1981c" -> 0xff005a; // folly red
						case "6147825f-5493-4154-87c5-5c03c6b0a7c2" -> 0xf2dd50; // lotus gold
						case "63a8c63b-9179-4427-849a-55212e6008bf" -> 0x7cff7c; // moriya green
						case "d5034857-9e8a-44cb-a6da-931caff5b838" -> 0xbd78ff; // upcraft pourble
						default -> 0x68e1ff;
					}));
				}
			}
		}
	}
}
