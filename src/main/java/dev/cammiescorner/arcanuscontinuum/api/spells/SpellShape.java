package dev.cammiescorner.arcanuscontinuum.api.spells;

import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class SpellShape extends SpellComponent {
	public static final SpellShape EMPTY = new SpellShape(Weight.NONE, 0, 20, 0) {
		@Override public void cast(LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, StaffItem staffItem, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex) {
			castNext(caster, castFrom, castSource, world, staffItem, stack, spellGroups, groupIndex);
		}
	};

	public SpellShape(Weight weight, double manaCost, int coolDown, int minLevel) {
		super(weight, manaCost, coolDown, minLevel);
	}

	public abstract void cast(LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, StaffItem staffItem, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex);

	public final void castNext(LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, ServerWorld world, StaffItem staffItem, ItemStack stack, List<SpellGroup> spellGroups, int groupIndex) {
		if(spellGroups.size() <= groupIndex + 1)
			return;

		SpellGroup group = spellGroups.get(groupIndex + 1);
		group.shape().cast(caster, castFrom, castSource, world, staffItem, stack, group.effects(), spellGroups, groupIndex + 1);
	}
}
