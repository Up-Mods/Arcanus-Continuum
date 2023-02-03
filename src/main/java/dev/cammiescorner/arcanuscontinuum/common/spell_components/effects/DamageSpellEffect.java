package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DamageSpellEffect extends SpellEffect {
	public DamageSpellEffect(SpellType type, ParticleEffect particle, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(type, particle, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, World world, HitResult target, StaffItem staffItem, ItemStack stack) {
		if(target.getType() == HitResult.Type.ENTITY){
			EntityHitResult entityHit = (EntityHitResult) target;
			if(entityHit.getEntity() instanceof LivingEntity livingEntity) {
				//livingEntity.damage();
			}
		}
		else if(target.getType() == HitResult.Type.BLOCK) {
			BlockHitResult blockHit = (BlockHitResult) target;

			if(!(caster instanceof PlayerEntity player) || world.canPlayerModifyAt(player, blockHit.getBlockPos())) {
				world.breakBlock(blockHit.getBlockPos(), true);
			}
		}

	}

}
