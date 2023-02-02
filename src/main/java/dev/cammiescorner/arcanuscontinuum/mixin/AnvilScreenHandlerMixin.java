package dev.cammiescorner.arcanuscontinuum.mixin;

import dev.cammiescorner.arcanuscontinuum.client.ArcanusClient;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {
	@ModifyArg(method = "updateResult", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/inventory/CraftingResultInventory;setStack(ILnet/minecraft/item/ItemStack;)V",
			ordinal = 4
	))
	private ItemStack arcanuscontinuum$updateResult(ItemStack stack) {
		String itemName = stack.getName().getString();

		if(stack.getItem() instanceof StaffItem && stack.hasCustomName() && itemName.matches(".+#[a-fA-F\\d]{6}$")) {
			Text newName = Text.literal(itemName.substring(0, itemName.lastIndexOf('#')).trim());
			int magicColour = Integer.decode(itemName.substring(itemName.lastIndexOf('#')));
			int r = magicColour >> 16 & 255;
			int g = magicColour >> 8 & 255;
			int b = magicColour & 255;
			Vector3f hsb = ArcanusClient.RGBtoHSB(r, g, b);

			if(hsb.z < 0.33333334F)
				magicColour = MathHelper.hsvToRgb(hsb.x, hsb.y, 0.33333334F);

			if(newName.getString().equals(stack.getItem().getName().getString()))
				stack.removeCustomName();
			else
				stack.setCustomName(newName);

			stack.getOrCreateNbt().putInt("MagicColor", magicColour);
		}

		return stack;
	}
}
