package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;

public class SizeChangeSpellEffect extends SpellEffect {
	public SizeChangeSpellEffect(SpellType type, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(type, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, World world, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;
			Entity entity = entityHit.getEntity();
			ScaleData heightData = ScaleTypes.HEIGHT.getScaleData(entity);
			ScaleData widthData = ScaleTypes.WIDTH.getScaleData(entity);
			ScaleData reachData = ScaleTypes.REACH.getScaleData(entity);
			ScaleData speedData = ScaleTypes.MOTION.getScaleData(entity);

			heightData.setScaleTickDelay(10);
			widthData.setScaleTickDelay(10);
			reachData.setScaleTickDelay(10);
			speedData.setScaleTickDelay(10);

			if(this == ArcanusSpellComponents.SHRINK) {
				float scale = (float) (effects.stream().filter(effect -> effect == ArcanusSpellComponents.SHRINK).count() / Math.max(12F, 20F / potency));

				heightData.setTargetScale(Math.max(Math.max(0.001F, 1 - scale), heightData.getBaseScale() - scale));
				widthData.setTargetScale(Math.max(Math.max(0.001F, 1 - scale), widthData.getBaseScale() - scale));
				reachData.setTargetScale(Math.max(Math.max(0.001F, 1 - scale), reachData.getBaseScale() - scale));
				speedData.setTargetScale(Math.max(Math.max(0.001F, 1 - scale), speedData.getBaseScale() - scale));
			}
			if(this == ArcanusSpellComponents.ENLARGE) {
				float scale = (float) (effects.stream().filter(effect -> effect == ArcanusSpellComponents.ENLARGE).count() / Math.max(12F, 20F / potency));

				heightData.setTargetScale(Math.min(1 + scale, heightData.getBaseScale() + scale));
				widthData.setTargetScale(Math.min(1 + scale, widthData.getBaseScale() + scale));
				reachData.setTargetScale(Math.min(1 + scale, reachData.getBaseScale() + scale));
				speedData.setTargetScale(Math.min(1 + scale, speedData.getBaseScale() + scale));
			}
		}
	}
}
