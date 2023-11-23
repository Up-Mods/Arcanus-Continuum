package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.entities.living.NecroSkeletonEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusEntities;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusItems;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NecromancySpellEffect extends SpellEffect {
	public NecromancySpellEffect(boolean isEnabled, SpellType type, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(isEnabled, type, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, @Nullable Entity sourceEntity, World world, HitResult target, List<SpellEffect> effects, ItemStack stack, double potency) {
		if(target.getType() != HitResult.Type.MISS && caster != null) {
			int effectCount = (int) effects.stream().filter(ArcanusSpellComponents.NECROMANCY::is).count();
			double healthModifier = (effectCount - 1) / 10F;
			List<? extends NecroSkeletonEntity> list = ((ServerWorld) world).getEntitiesByType(TypeFilter.instanceOf(NecroSkeletonEntity.class), necroSkeletonEntity -> necroSkeletonEntity.getOwnerId().equals(caster.getUuid()));

			for(int i = 0; i < effectCount; i++) {
				if(list.size() + i >= 20)
					return;

				NecroSkeletonEntity skeleton = ArcanusEntities.NECRO_SKELETON.get().create(world);

				if(skeleton != null) {
					EntityAttributeInstance damage = skeleton.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);

					skeleton.setPosition(target.getPos());
					skeleton.setMaxHealth((11 - healthModifier) * potency);
					skeleton.setOwner(caster);
					skeleton.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_AXE));
					skeleton.equipStack(EquipmentSlot.HEAD, new ItemStack(ArcanusItems.WIZARD_HAT.get()));

					if(damage != null)
						damage.addPersistentModifier(new EntityAttributeModifier("Attack Damage", -(effectCount / 2D) / potency, EntityAttributeModifier.Operation.ADDITION));

					world.spawnEntity(skeleton);
				}
			}
		}
	}

	@Override
	public boolean shouldTriggerOnceOnExplosion() {
		return true;
	}
}
