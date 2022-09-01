package dev.cammiescorner.arcanuscontinuum.mixin;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.api.spells.Pattern;
import dev.cammiescorner.arcanuscontinuum.api.spells.Spell;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Shadow public abstract @Nullable EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);
	@Shadow public abstract ItemStack getMainHandStack();

	@Unique private static final UUID uUID = UUID.fromString("e348efa3-7987-4912-b82a-03c5c75eccb1");
	@Unique private final LivingEntity self = (LivingEntity) (Object) this;

	@Inject(method = "tick", at = @At("HEAD"))
	private void arcanuscontinuum$tick(CallbackInfo info) {
		if(!self.world.isClient()) {
			EntityAttributeInstance speedAttr = getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
			List<Pattern> pattern = ArcanusComponents.getPattern(self);
			ItemStack stack = getMainHandStack();

			if(speedAttr != null) {
				if(stack.getItem() instanceof StaffItem && ArcanusComponents.isCasting(self) && pattern.size() == 3) {
					int index = Arcanus.getSpellIndex(pattern);
					NbtCompound tag = stack.getOrCreateSubNbt(Arcanus.MOD_ID);
					NbtList list = tag.getList("Spells", NbtElement.STRING_TYPE);

					if(!list.isEmpty() && index < list.size()) {
						Spell spell = Arcanus.SPELLS.get(new Identifier(list.getString(index)));
						EntityAttributeModifier speedMod = new EntityAttributeModifier(uUID, "Spell Speed Modifier", spell.getSlowdown(), EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

						if(!speedAttr.hasModifier(speedMod))
							speedAttr.addTemporaryModifier(speedMod);
					}
				}
				else if(speedAttr.getModifier(uUID) != null)
					speedAttr.removeModifier(uUID);
			}
		}
	}

	@Inject(method = "createLivingAttributes", at = @At("RETURN"))
	private static void arcanuscontinuum$createPlayerAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
		info.getReturnValue().add(ArcanusEntityAttributes.MAX_MANA).add(ArcanusEntityAttributes.MANA_REGEN).add(ArcanusEntityAttributes.BURNOUT_REGEN).add(ArcanusEntityAttributes.MANA_LOCK).add(ArcanusEntityAttributes.SPELL_POTENCY);
	}
}
