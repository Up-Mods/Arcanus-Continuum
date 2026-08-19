package dev.cammiescorner.arcanus.common.item;

import com.teamresourceful.resourcefulconfig.client.components.options.types.color.HsbColor;
import dev.cammiescorner.arcanus.api.arcana.Arcana;
import dev.cammiescorner.arcanus.common.data_component.ArcanaStack;
import dev.cammiescorner.arcanus.common.registry.ArcanusArcana;
import dev.cammiescorner.arcanus.common.registry.ArcanusDataComponents;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class WardedJarItem extends BlockItem {
	public WardedJarItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
		ArcanaStack data = itemStack.get(ArcanusDataComponents.ARCANA_STACK.get());

		if(data != null) {
			MutableComponent component = Component.empty();
			Arcana arcana = data.arcana();

			if(arcana != null && arcana != ArcanusArcana.NIL.get()) {
				component.append(String.format(data.amount() % 1 == 0 ? "%.0f" : "%.1f", data.amount()));
				component.append(" ");
				component.append(Component.translatable(arcana.translationKey()));

				Color color = arcana.color();
				HsbColor hsb = HsbColor.fromRgb(color.asIntARGB());

				if(hsb.brightness() < 0.4f)
					hsb = HsbColor.of(hsb.hue(), hsb.saturation(), 0.4f, hsb.alpha());

				builder.accept(component.withColor(hsb.toRgba()));
			}
		}
	}
}
