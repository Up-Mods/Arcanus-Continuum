package dev.cammiescorner.arcanus.common.spell_component.shapes;

import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.components.SpellEffect;
import dev.cammiescorner.arcanus.api.spell.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.common.entity.magic.StockpileOrb;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanus.common.util.TranslationKeys;
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

public class StockpileSpellShape extends SpellShape {
	public StockpileSpellShape() {
		super(
			() -> ArcanusConfig.SpellShapes.StockpileShapeProperties.enabled,
			() -> ArcanusConfig.SpellShapes.StockpileShapeProperties.weight,
			() -> ArcanusConfig.SpellShapes.StockpileShapeProperties.arcanaCosts(),
			() -> ArcanusConfig.SpellShapes.StockpileShapeProperties.arcanaModifier,
			() -> ArcanusConfig.SpellShapes.StockpileShapeProperties.potencyModifier,
			() -> ArcanusConfig.SpellShapes.StockpileShapeProperties.coolDownModifier,
			() -> ArcanusConfig.SpellShapes.StockpileShapeProperties.procsOnce);
	}

	@Override
	public void cast(@Nullable LivingEntity caster, Vec3 castFrom, @Nullable Entity castSource, ServerLevel level, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex, double potency) {
		potency += getPotencyModifier();
		Entity sourceEntity = castSource != null ? castSource : caster;

		if(sourceEntity instanceof LivingEntity target) {
			for(int i = 0; i < ArcanusConfig.SpellShapes.StockpileShapeProperties.stockpileOrbsPerCast; i++) {
				if(ArcanusComponents.stockpileOrbCount(target) >= ArcanusConfig.SpellShapes.StockpileShapeProperties.maximumStockpileOrbs) {
					if(caster instanceof Player player)
						player.sendSystemMessage(Component.translatable(TranslationKeys.TOO_MANY_ORBS).withStyle(ChatFormatting.RED));

					break;
				}

				StockpileOrb stockpileOrb = ArcanusEntities.STOCKPILE_ORB.get().create(level);

				if(stockpileOrb != null) {
					stockpileOrb.setProperties(caster, target, stack, effects, spellGroups, groupIndex, potency);
					stockpileOrb.setPos(castFrom);
					level.addFreshEntity(stockpileOrb);
				}
			}
		}
	}
}
