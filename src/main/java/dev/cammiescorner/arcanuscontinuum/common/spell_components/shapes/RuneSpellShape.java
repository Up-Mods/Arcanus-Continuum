package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.MagicRuneEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RuneSpellShape extends SpellShape {
	public RuneSpellShape(Weight weight, double manaCost, int coolDown, int minLevel) {
		super(weight, manaCost, coolDown, minLevel);
	}

	public RuneSpellShape(Weight weight, double manaCost, double manaMultiplier, int coolDown, int minLevel) {
		super(weight, manaCost, manaMultiplier, coolDown, minLevel);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		if(caster != null) {
			List<? extends MagicRuneEntity> list = world.getEntitiesByType(TypeFilter.instanceOf(MagicRuneEntity.class), entity -> caster.getUuid().equals(entity.getCasterId()));

			for(int i = 0; i < list.size() - 100; i++)
				list.get(i).kill();

			MagicRuneEntity magicRune = ArcanusEntities.MAGIC_RUNE.get().create(world);
			Entity sourceEntity = castSource != null ? castSource : caster;

			if(magicRune != null) {
				magicRune.setProperties(caster.getUuid(), sourceEntity, castFrom, stack, effects, potency, spellGroups, groupIndex, Arcanus.DEFAULT_MAGIC_COLOUR);

				if(caster instanceof PlayerEntity player)
					magicRune.setColour(Arcanus.getMagicColour(player.getGameProfile().getId()));

				world.spawnEntity(magicRune);
			}
		}
	}
}
