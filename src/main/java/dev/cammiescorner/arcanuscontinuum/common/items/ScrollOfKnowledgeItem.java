package dev.cammiescorner.arcanuscontinuum.common.items;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.components.entity.WizardLevelComponent;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class ScrollOfKnowledgeItem extends Item {
	public ScrollOfKnowledgeItem() {
		super(new QuiltItemSettings().maxCount(16));
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		WizardLevelComponent wizardLevel = ArcanusComponents.WIZARD_LEVEL_COMPONENT.get(user);
		ItemStack stack = user.getStackInHand(hand);

		if(wizardLevel.getLevel() < 10) {
			wizardLevel.setLevel(wizardLevel.getLevel() + 1);

			if(!user.isCreative())
				stack.decrement(1);

			user.sendMessage(Arcanus.translate("scroll_of_knowledge", "level_up").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC), true);

			return TypedActionResult.success(stack);
		}

		return super.use(world, user, hand);
	}
}
