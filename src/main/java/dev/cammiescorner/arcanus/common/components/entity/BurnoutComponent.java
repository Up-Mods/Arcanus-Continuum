package dev.cammiescorner.arcanus.common.components.entity;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.registry.ArcanusEntityAttributes;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class BurnoutComponent implements AutoSyncedComponent, ServerTickingComponent {
	public static final ResourceLocation ATTACK_SPEED_MODIFIER = Arcanus.id("burnout_attack_speed_modifier");
	public static final ResourceLocation MOVE_SPEED_MODIFIER = Arcanus.id("burnout_move_speed_modifier");
	private final LivingEntity entity;
	private double burnout;

	public BurnoutComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {
		AttributeInstance burnoutRegenAttr = entity.getAttribute(ArcanusEntityAttributes.BURNOUT_REGEN.holder());
		AttributeInstance attackSpeedAttr = entity.getAttribute(Attributes.ATTACK_SPEED);
		AttributeInstance moveSpeedAttr = entity.getAttribute(Attributes.MOVEMENT_SPEED);

		if(burnoutRegenAttr != null && drainBurnout(burnoutRegenAttr.getValue(), true)) {
			drainBurnout(burnoutRegenAttr.getValue() / (entity instanceof Player player && player.isCreative() ? 1 : 30), false);

			if(entity instanceof Player player)
				player.causeFoodExhaustion(0.01F);
		}

		if(attackSpeedAttr != null) {
			if(burnout > 0 && attackSpeedAttr.getModifier(ATTACK_SPEED_MODIFIER) == null)
				attackSpeedAttr.addPermanentModifier(new AttributeModifier(ATTACK_SPEED_MODIFIER, -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
			if(burnout <= 0 && attackSpeedAttr.getModifier(ATTACK_SPEED_MODIFIER) != null)
				attackSpeedAttr.removeModifier(ATTACK_SPEED_MODIFIER);
		}

		if(moveSpeedAttr != null) {
			if(burnout > 0 && moveSpeedAttr.getModifier(MOVE_SPEED_MODIFIER) == null)
				moveSpeedAttr.addPermanentModifier(new AttributeModifier(MOVE_SPEED_MODIFIER, -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
			if(burnout <= 0 && moveSpeedAttr.getModifier(MOVE_SPEED_MODIFIER) != null)
				moveSpeedAttr.removeModifier(MOVE_SPEED_MODIFIER);
		}
	}

	@Override
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		burnout = tag.getDouble("Burnout");
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		tag.putDouble("Burnout", burnout);
	}

	public double getBurnout() {
		return burnout;
	}

	public void setBurnout(double burnout) {
		this.burnout = burnout;

		if(entity instanceof Player)
			ArcanusComponents.BURNOUT_COMPONENT.sync(entity);
	}

	public boolean addBurnout(double amount, boolean simulate) {
		double maxMana = ArcanusComponents.getMaxMana(entity) - ArcanusComponents.getManaLock(entity);

		if(getBurnout() < maxMana) {
			if(!simulate)
				setBurnout(Math.min(maxMana, getBurnout() + amount));

			return true;
		}

		return false;
	}

	public boolean drainBurnout(double amount, boolean simulate) {
		double maxMana = ArcanusComponents.getMaxMana(entity) - ArcanusComponents.getManaLock(entity);

		if(getBurnout() > 0) {
			if(!simulate) {
				if(burnout > maxMana)
					setBurnout(Math.max(0, maxMana - amount));
				else
					setBurnout(Math.max(0, getBurnout() - amount));
			}

			return true;
		}

		return false;
	}
}
