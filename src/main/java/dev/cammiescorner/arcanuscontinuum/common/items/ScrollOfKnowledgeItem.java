package dev.cammiescorner.arcanuscontinuum.common.items;

import dev.cammiescorner.arcanuscontinuum.common.components.WizardLevelComponent;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

		if(wizardLevel.getLevel() < 10) {
			wizardLevel.setLevel(wizardLevel.getLevel() + 1);
			return TypedActionResult.success(user.getStackInHand(hand));
		}

		return super.use(world, user, hand);
	}
}
