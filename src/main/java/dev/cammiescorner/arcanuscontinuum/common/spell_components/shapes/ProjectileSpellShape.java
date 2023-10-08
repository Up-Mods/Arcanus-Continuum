package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.MagicProjectileEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ProjectileSpellShape extends SpellShape {
	public ProjectileSpellShape(Weight weight, double manaCost, int coolDown, int minLevel) {
		super(weight, manaCost, coolDown, minLevel);
	}

	public ProjectileSpellShape(Weight weight, double manaCost, double manaMultiplier, int coolDown, int minLevel) {
		super(weight, manaCost, manaMultiplier, coolDown, minLevel);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		if(caster != null) {
			List<? extends MagicProjectileEntity> list = world.getEntitiesByType(TypeFilter.instanceOf(MagicProjectileEntity.class), entity -> caster.equals(entity.getOwner()));

			for(int i = 0; i < list.size() - 20; i++)
				list.get(i).kill();

			MagicProjectileEntity projectile = ArcanusEntities.MAGIC_PROJECTILE.get().create(world);

			if(projectile != null) {
				projectile.setProperties(caster, castSource, this, stack, effects, spellGroups, groupIndex, potency, ArcanusSpellComponents.LOB.is(this) ? 2F : 4F, !ArcanusSpellComponents.LOB.is(this), Arcanus.DEFAULT_MAGIC_COLOUR);

				if(caster instanceof PlayerEntity player)
					projectile.setColour(Arcanus.getMagicColour(player.getGameProfile().getId()));

				world.spawnEntity(projectile);
			}
		}
	}
}
