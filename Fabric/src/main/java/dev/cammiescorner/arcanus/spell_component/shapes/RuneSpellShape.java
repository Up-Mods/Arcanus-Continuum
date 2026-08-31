package dev.cammiescorner.arcanus.spell_component.shapes;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.entity.magic.MagicRune;
import dev.cammiescorner.arcanus.registry.ArcanusEntities;
import dev.cammiescorner.arcanus.util.ArcanusHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RuneSpellShape extends SpellShape {
	public RuneSpellShape() {
		super(
			() -> ArcanusConfig.SpellShapes.RuneShapeProperties.enabled,
			() -> ArcanusConfig.SpellShapes.RuneShapeProperties.weight,
			() -> ArcanusConfig.SpellShapes.RuneShapeProperties.arcanaCosts(),
			() -> ArcanusConfig.SpellShapes.RuneShapeProperties.arcanaModifier,
			() -> ArcanusConfig.SpellShapes.RuneShapeProperties.potencyModifier,
			() -> ArcanusConfig.SpellShapes.RuneShapeProperties.coolDownModifier,
			() -> ArcanusConfig.SpellShapes.RuneShapeProperties.activatesOnce);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		potency += getPotencyModifier();

		if(caster != null) {
			List<? extends MagicRune> list = level.getEntities(EntityTypeTest.forClass(MagicRune.class), entity -> caster.getUUID().equals(entity.getCasterId()));

			for(int i = 0; i < list.size() - 100; i++)
				list.get(i).kill();

			MagicRune magicRune = ArcanusEntities.MAGIC_RUNE.get().create(level);
			Entity sourceEntity = castSource != null ? castSource : caster;

			if(magicRune != null) {
				magicRune.setProperties(caster.getUUID(), sourceEntity, castFrom, stack, effects, potency, spellGroups, groupIndex);
				ArcanusHelper.copyMagicColor(magicRune, caster);
				level.addFreshEntity(magicRune);
			}
		}
	}
}
