package dev.cammiescorner.arcanuscontinuum.mixin.client;

import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
	@Shadow @Final private ItemModels models;

	@Inject(method = "getHeldItemModel", at = @At("HEAD"), cancellable = true)
	private void arcanuscontinuum$getHeldItemModel(ItemStack stack, World world, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
		if(stack.getItem() instanceof StaffItem) {
			BakedModel bakedModel = models.getModelManager().getModel(new ModelIdentifier("minecraft", "trident_in_hand", "inventory"));
			ClientWorld clientWorld = world instanceof ClientWorld cWorld ? cWorld : null;
			BakedModel bakedModel2 = bakedModel.getOverrides().apply(bakedModel, stack, clientWorld, entity, seed);
			cir.setReturnValue(bakedModel2 == null ? models.getModelManager().getMissingModel() : bakedModel2);
		}
	}
}
