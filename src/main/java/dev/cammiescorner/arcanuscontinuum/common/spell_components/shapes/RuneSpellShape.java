package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.MagicRuneEntity;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
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
	public void cast(LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		MagicRuneEntity magicRune = ArcanusEntities.MAGIC_RUNE.create(world);
		Entity sourceEntity = castSource != null ? castSource : caster;

		if(magicRune != null) {
			magicRune.setProperties(caster, sourceEntity, castFrom, stack, effects, potency, spellGroups, groupIndex, StaffItem.getMagicColour(caster.getUuidAsString()));
			world.spawnEntity(magicRune);
		}
	}
}
