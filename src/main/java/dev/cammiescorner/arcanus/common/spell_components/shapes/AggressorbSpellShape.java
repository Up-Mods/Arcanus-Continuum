package dev.cammiescorner.arcanus.common.spell_components.shapes;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spells.SpellEffect;
import dev.cammiescorner.arcanus.api.spells.SpellGroup;
import dev.cammiescorner.arcanus.api.spells.SpellShape;
import dev.cammiescorner.arcanus.common.entities.magic.Aggressorb;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AggressorbSpellShape extends SpellShape {
	public AggressorbSpellShape() {
		super(
			() -> ArcanusConfig.SpellShapes.AggressorbShapeProperties.enabled,
			() -> ArcanusConfig.SpellShapes.AggressorbShapeProperties.weight,
			() -> ArcanusConfig.SpellShapes.AggressorbShapeProperties.manaCosts(),
			() -> ArcanusConfig.SpellShapes.AggressorbShapeProperties.manaMultiplier,
			() -> ArcanusConfig.SpellShapes.AggressorbShapeProperties.coolDown,
			() -> ArcanusConfig.SpellShapes.AggressorbShapeProperties.potencyModifier,
			() -> ArcanusConfig.SpellShapes.AggressorbShapeProperties.procsOnce
		);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		potency += getPotencyModifier();
		Entity sourceEntity = castSource != null ? castSource : caster;

		if(sourceEntity instanceof LivingEntity target) {
			for(int i = 0; i < ArcanusConfig.SpellShapes.AggressorbShapeProperties.aggressorbsPerCast; i++) {
				if(ArcanusComponents.aggressorbCount(target) >= ArcanusConfig.SpellShapes.AggressorbShapeProperties.maximumAggressorbs) {
					if(caster instanceof Player player)
						player.sendSystemMessage(Component.translatable("text.arcanus.too_many_orbs").withStyle(ChatFormatting.RED));

					break;
				}

				Aggressorb aggressorb = ArcanusEntities.AGGRESSORB.get().create(level);

				if(aggressorb != null) {
					aggressorb.setProperties(caster, target, stack, effects, spellGroups, groupIndex, potency);
					aggressorb.setPos(castFrom);
					level.addFreshEntity(aggressorb);
				}
			}
		}
	}
}
