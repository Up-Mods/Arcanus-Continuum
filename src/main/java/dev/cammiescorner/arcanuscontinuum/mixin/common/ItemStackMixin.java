package dev.cammiescorner.arcanuscontinuum.mixin.common;

import com.google.common.collect.Multimap;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Inject(method = "getTooltip", at = @At(value = "INVOKE",
		target = "Ljava/util/Map$Entry;getValue()Ljava/lang/Object;"
	), locals = LocalCapture.CAPTURE_FAILSOFT)
	private void arcanuscontinuum$captureEntry(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir, List list, MutableText mutableText, int i, EquipmentSlot[] var6, int var7, int var8, EquipmentSlot equipmentSlot, Multimap multimap, Iterator var11, Map.Entry<EntityAttribute, EntityAttributeModifier> entry, @Share("entry") LocalRef<Map.Entry<EntityAttribute, EntityAttributeModifier>> ref) {
		ref.set(entry);
	}

	@ModifyArg(method = "getTooltip", slice = @Slice(from = @At(value = "FIELD",
		target = "Lnet/minecraft/util/Formatting;BLUE:Lnet/minecraft/util/Formatting;"
	)), at = @At(value = "INVOKE",
		target = "Lnet/minecraft/text/MutableText;formatted(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/MutableText;"
	))
	private Formatting arcanuscontinuum$switchColour(Formatting formatting, @Share("entry") LocalRef<Map.Entry<EntityAttribute, EntityAttributeModifier>> ref) {
		return ref.get() != null && (ref.get().getKey() == ArcanusEntityAttributes.MANA_COST.get() || ref.get().getKey() == ArcanusEntityAttributes.SPELL_COOL_DOWN.get()) ? (formatting == Formatting.BLUE ? Formatting.RED : Formatting.BLUE) : formatting;
	}
}
