package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects.movement;

import dev.cammiescorner.arcanuscontinuum.ArcanusConfig;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TeleportSpellEffect extends SpellEffect {
	public TeleportSpellEffect(boolean isEnabled, SpellType type, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(isEnabled, type, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, World world, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() != HitResult.Type.MISS) {
			if(caster != null && caster.getPos().distanceTo(target.getPos()) <= ArcanusConfig.MovementEffects.TeleportEffectProperties.baseTeleportDistance * effects.stream().filter(ArcanusSpellComponents.TELEPORT::is).count() * potency) {
				Vec3d pos = target.getPos();

				if(target.getType() == HitResult.Type.BLOCK) {
					BlockHitResult blockHit = (BlockHitResult) target;
					pos = pos.add(blockHit.getSide().getOffsetX() * 0.5, blockHit.getSide() == Direction.DOWN ? -2 : 0, blockHit.getSide().getOffsetZ() * 0.5);
				}

				caster.requestTeleportAndDismount(pos.getX(), pos.getY(), pos.getZ());
				world.sendEntityStatus(caster, EntityStatuses.ADD_PORTAL_PARTICLES);
			}
		}
	}
}
