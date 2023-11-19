package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.ManaShieldEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaShieldSpellEffect extends SpellEffect {
	public ManaShieldSpellEffect(boolean isEnabled, SpellType type, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(isEnabled, type, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, World world, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() != HitResult.Type.MISS && caster != null) {
			List<? extends ManaShieldEntity> list = ((ServerWorld) world).getEntitiesByType(TypeFilter.instanceOf(ManaShieldEntity.class), entity -> entity.getOwnerId().equals(caster.getUuid()));

			for(int i = 0; i < list.size() - 10; i++)
				list.get(i).kill();

			ManaShieldEntity manaShield = ArcanusEntities.MANA_SHIELD.get().create(world);

			if(manaShield != null) {
				manaShield.setProperties(caster.getUuid(), target.getPos().add(0, -0.7, 0), Arcanus.DEFAULT_MAGIC_COLOUR, (int) ((100 + 40 * (effects.stream().filter(ArcanusSpellComponents.MANA_SHIELD::is).count() - 1)) * potency));

				if(caster instanceof PlayerEntity player)
					manaShield.setColour(Arcanus.getMagicColour(player.getGameProfile().getId()));

				world.spawnEntity(manaShield);
			}
		}
	}

	@Override
	public boolean shouldTriggerOnceOnExplosion() {
		return true;
	}
}
