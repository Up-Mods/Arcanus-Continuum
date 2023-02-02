package dev.cammiescorner.arcanuscontinuum.mixin.client;

import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingScreen.class)
public abstract class CraftingScreenMixin extends HandledScreen<CraftingScreenHandler> {
	public CraftingScreenMixin(CraftingScreenHandler handler, PlayerInventory inventory, Text title) { super(handler, inventory, title); }

	@Inject(method = "render", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screen/ingame/CraftingScreen;drawMouseoverTooltip(Lnet/minecraft/client/util/math/MatrixStack;II)V"
	))
	private void arcanuscontinuum$render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
		if(getScreenHandler().getSlot(5).getStack().getItem() instanceof StaffItem) {
			float scale = 0.5F;
			matrices.push();
			matrices.scale(scale, scale, 1F);

			if(getScreenHandler().getSlot(2).getStack().isEmpty())
				drawCenteredText(matrices, textRenderer, "L-L-L", (int) ((x + 56) / scale), (int) ((y + 23) / scale), 0xffffff);
			if(getScreenHandler().getSlot(3).getStack().isEmpty())
				drawCenteredText(matrices, textRenderer, "L-L-R", (int) ((x + 74) / scale), (int) ((y + 23) / scale), 0xffffff);
			if(getScreenHandler().getSlot(6).getStack().isEmpty())
				drawCenteredText(matrices, textRenderer, "L-R-L", (int) ((x + 74) / scale), (int) ((y + 41) / scale), 0xffffff);
			if(getScreenHandler().getSlot(9).getStack().isEmpty())
				drawCenteredText(matrices, textRenderer, "L-R-R", (int) ((x + 74) / scale), (int) ((y + 59) / scale), 0xffffff);
			if(getScreenHandler().getSlot(8).getStack().isEmpty())
				drawCenteredText(matrices, textRenderer, "R-R-R", (int) ((x + 56) / scale), (int) ((y + 59) / scale), 0xffffff);
			if(getScreenHandler().getSlot(7).getStack().isEmpty())
				drawCenteredText(matrices, textRenderer, "R-R-L", (int) ((x + 38) / scale), (int) ((y + 59) / scale), 0xffffff);
			if(getScreenHandler().getSlot(4).getStack().isEmpty())
				drawCenteredText(matrices, textRenderer, "R-L-R", (int) ((x + 38) / scale), (int) ((y + 41) / scale), 0xffffff);
			if(getScreenHandler().getSlot(1).getStack().isEmpty())
				drawCenteredText(matrices, textRenderer, "R-L-L", (int) ((x + 38) / scale), (int) ((y + 23) / scale), 0xffffff);

			matrices.pop();
		}
	}
}
