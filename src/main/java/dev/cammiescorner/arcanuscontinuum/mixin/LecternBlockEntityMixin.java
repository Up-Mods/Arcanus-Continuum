package dev.cammiescorner.arcanuscontinuum.mixin;

import dev.cammiescorner.arcanuscontinuum.common.items.SpellBookItem;
import dev.cammiescorner.arcanuscontinuum.common.screens.SpellcraftScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LecternBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LecternBlockEntity.class)
public abstract class LecternBlockEntityMixin extends BlockEntity implements Clearable, NamedScreenHandlerFactory, ExtendedScreenHandlerFactory {
	@Shadow @Final private Inventory inventory;
	@Shadow public abstract ItemStack getBook();

	public LecternBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	@Inject(method = "createMenu", at = @At("HEAD"), cancellable = true)
	private void devotion$createResearchScreen(int i, PlayerInventory playerInventory, PlayerEntity playerEntity, CallbackInfoReturnable<ScreenHandler> info) {
		if(getBook().getItem() instanceof SpellBookItem)
			info.setReturnValue(new SpellcraftScreenHandler(i, inventory, ScreenHandlerContext.create(world, getPos()), getBook()));
	}

//	@Inject(method = "setBook(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At("TAIL"))
//	private void devotion$syncContents(ItemStack book, PlayerEntity player, CallbackInfo info) {
//		if(world != null && !world.isClient())
//			world.updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_ALL);
//	}
//
//	@Override
//	public NbtCompound toInitialChunkDataNbt() {
//		NbtCompound tag = super.toInitialChunkDataNbt();
//		writeNbt(tag);
//		return tag;
//	}
//
//	@Nullable
//	@Override
//	public Packet<ClientPlayPacketListener> toUpdatePacket() {
//		return BlockEntityUpdateS2CPacket.of(this);
//	}

	@Override
	public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
		buf.writeItemStack(getBook());
	}
}
