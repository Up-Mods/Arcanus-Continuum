package dev.cammiescorner.arcanus.mixin.common;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	// TODO mess with armor/tool attribute name colors i guess
//	@Inject(method = "getTooltipLines", at = @At(
//		value = "INVOKE",
//		target = "Ljava/util/Map$Entry;getValue()Ljava/lang/Object;"
//	), locals = LocalCapture.CAPTURE_FAILSOFT)
//	private void captureEntry(Player player, TooltipFlag context, CallbackInfoReturnable<List<Component>> cir, List list, MutableComponent mutableText, int i, EquipmentSlot[] var6, int var7, int var8, EquipmentSlot equipmentSlot, Multimap multimap, Iterator var11, Map.Entry<Attribute, AttributeModifier> entry, @Share("entry") LocalRef<Map.Entry<Attribute, AttributeModifier>> ref) {
//		ref.set(entry);
//	}
//
//	@ModifyArg(method = "getTooltipLines", slice = @Slice(from = @At(
//		value = "FIELD",
//		target = "Lnet/minecraft/ChatFormatting;BLUE:Lnet/minecraft/ChatFormatting;"
//	)), at = @At(
//		value = "INVOKE",
//		target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;"
//	))
//	private ChatFormatting switchColour(ChatFormatting formatting, @Share("entry") LocalRef<Map.Entry<Attribute, AttributeModifier>> ref) {
//		return ref.get() != null && (ref.get().getKey() == ArcanusEntityAttributes.MANA_COST.get() || ref.get().getKey() == ArcanusEntityAttributes.SPELL_COOL_DOWN.get()) ? (formatting == ChatFormatting.BLUE ? ChatFormatting.RED : ChatFormatting.BLUE) : formatting;
//	}
}
