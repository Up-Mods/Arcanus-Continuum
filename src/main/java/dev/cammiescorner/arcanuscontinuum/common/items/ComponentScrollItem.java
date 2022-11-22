package dev.cammiescorner.arcanuscontinuum.common.items;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellComponent;
import dev.cammiescorner.arcanuscontinuum.common.components.KnownComponentsComponent;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class ComponentScrollItem extends Item {
	public final SpellComponent component;

	public ComponentScrollItem(SpellComponent component) {
		super(new QuiltItemSettings().group(Arcanus.ITEM_GROUP).maxCount(16));
		this.component = component;
	}

	@Override
	public String getTranslationKey() {
		Identifier id = Registry.ITEM.getId(this);
		MinecraftClient client = MinecraftClient.getInstance();

		if(client.player != null && ArcanusComponents.KNOWN_COMPONENTS_COMPONENT.get(client.player).hasComponent(component))
			return "item." + id.getNamespace() + "." + id.getPath() + "_known";

		return "item." + id.getNamespace() + "." + id.getPath() + "_unknown";
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		KnownComponentsComponent knownComponentsComponent = ArcanusComponents.KNOWN_COMPONENTS_COMPONENT.get(user);

		if(!knownComponentsComponent.hasComponent(component)) {
			knownComponentsComponent.addComponent(component);
			return TypedActionResult.success(user.getStackInHand(hand));
		}

		return super.use(world, user, hand);
	}
}
