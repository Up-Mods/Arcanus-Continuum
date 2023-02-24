package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;

public class SizeChangeSpellEffect extends SpellEffect {
	public SizeChangeSpellEffect(SpellType type, ParticleEffect particle, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(type, particle, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, World world, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;
			Entity entity = entityHit.getEntity();
			ScaleData heightData = ScaleTypes.HEIGHT.getScaleData(entity);
			ScaleData widthData = ScaleTypes.WIDTH.getScaleData(entity);
			ScaleData speedData = ScaleTypes.MOTION.getScaleData(entity);
			ScaleData reachData = ScaleTypes.REACH.getScaleData(entity);

			if(this == ArcanusSpellComponents.SHRINK) {
				float scale = (float) (effects.stream().filter(effect -> effect == ArcanusSpellComponents.SHRINK).count() * 0.05F * potency);

				heightData.setScale(Math.max(1 - scale, heightData.getBaseScale() - scale));
				widthData.setScale(Math.max(1 - scale, widthData.getBaseScale() - scale));
				reachData.setScale(Math.max(1 - scale, reachData.getBaseScale() - scale));
				speedData.setScale(Math.max(1 - scale, speedData.getBaseScale() - scale));
			}
			if(this == ArcanusSpellComponents.ENLARGE) {
				float scale = (float) (effects.stream().filter(effect -> effect == ArcanusSpellComponents.ENLARGE).count() * 0.05F * potency);

				heightData.setScale(Math.min(1 + scale, heightData.getBaseScale() + scale));
				widthData.setScale(Math.min(1 + scale, widthData.getBaseScale() + scale));
				reachData.setScale(Math.min(1 + scale, reachData.getBaseScale() + scale));
				speedData.setScale(Math.min(1 + scale, speedData.getBaseScale() + scale));
			}
		}
	}
}
