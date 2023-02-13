package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.SmiteEntity;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SmiteSpellShape extends SpellShape {
	private static final double MAX_RANGE = 100D;

	public SmiteSpellShape(Weight weight, double manaCost, int coolDown, int minLevel) {
		super(weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void cast(LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		HitResult ray;
		boolean hitDidConnect = true;

		if(caster == castSource) {
			ray = caster.raycast(MAX_RANGE, 1F, false);

			if(ray.getType() == HitResult.Type.MISS) {
				if(caster instanceof PlayerEntity player)
					player.sendMessage(Arcanus.translate("spell", "smite_miss"), true); // TODO format

				hitDidConnect = false;
			}
		}
		else {
			ray = BlockHitResult.createMissed(castFrom, Direction.UP, new BlockPos(castFrom));
		}

		SmiteEntity smite = null;

		if(hitDidConnect) {
			smite = ArcanusEntities.SMITE.create(world);

			if(smite != null) {
				smite.setProperties(caster, ray.getPos(), stack, effects, potency, StaffItem.getMagicColour(caster.getUuidAsString()));
				world.spawnEntity(smite);
			}
		}

		castNext(caster, hitDidConnect ? ray.getPos() : castFrom, smite, world, stack, spellGroups, groupIndex, potency);
	}
}
