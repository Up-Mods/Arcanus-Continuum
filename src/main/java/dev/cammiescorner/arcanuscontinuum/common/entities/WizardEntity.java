package dev.cammiescorner.arcanuscontinuum.common.entities;

import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.entity.effect.api.StatusEffectRemovalReason;

import java.util.Arrays;
import java.util.List;

public class WizardEntity extends PassiveEntity implements SmartBrainOwner<WizardEntity> {
	public WizardEntity(EntityType<? extends PassiveEntity> entityType, World world) {
		super(entityType, world);
		Arrays.fill(armorDropChances, 0F);
		Arrays.fill(handDropChances, 0F);
		equipStack(EquipmentSlot.HEAD, new ItemStack(ArcanusItems.WIZARD_HAT));
		equipStack(EquipmentSlot.CHEST, new ItemStack(ArcanusItems.WIZARD_ROBES));
		equipStack(EquipmentSlot.LEGS, new ItemStack(ArcanusItems.WIZARD_PANTS));
		equipStack(EquipmentSlot.FEET, new ItemStack(ArcanusItems.WIZARD_BOOTS));
		equipStack(EquipmentSlot.MAINHAND, getRandomStaff());
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return TameableEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1);
	}

	@Override
	public boolean cannotDespawn() {
		return true;
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return null;
	}

	@Override
	public List<ExtendedSensor<WizardEntity>> getSensors() {
		return null;
	}

	@Override
	public boolean removeStatusEffect(@NotNull StatusEffect type, @NotNull StatusEffectRemovalReason reason) {
		return false;
	}

	@Override
	public int clearStatusEffects(@NotNull StatusEffectRemovalReason reason) {
		return 0;
	}

	@Override
	public void onStatusEffectRemoved(@NotNull StatusEffectInstance effect, @NotNull StatusEffectRemovalReason reason) {

	}

	private ItemStack getRandomStaff() {
		List<Item> staves = Registry.ITEM.stream().filter(item -> item instanceof StaffItem).toList();

		return new ItemStack(staves.get(world.getRandom().nextInt(staves.size())));
	}
}
