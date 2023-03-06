package dev.cammiescorner.arcanuscontinuum.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Spell;
import dev.cammiescorner.arcanuscontinuum.common.util.StaffType;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class StaffItem extends Item {
	public static final UUID ATTACK_RANGE_MODIFIER_ID = UUID.fromString("05869d86-c861-4954-9079-68c380ad063c");
	private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
	public final StaffType staffType;
	public final int defaultPrimaryColour;
	public final int defaultSecondaryColour;
	public final boolean isDonorOnly;

	public StaffItem(StaffType staffType, int defaultPrimaryColour, int defaultSecondaryColour) {
		this(staffType, defaultPrimaryColour, defaultSecondaryColour, false);
	}

	public StaffItem(StaffType staffType, int defaultPrimaryColour, int defaultSecondaryColour, boolean isDonorOnly) {
		super(new QuiltItemSettings().maxCount(1));
		ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

		builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -1, EntityAttributeModifier.Operation.ADDITION));
		builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(ATTACK_RANGE_MODIFIER_ID, "Weapon modifier", 0.5, EntityAttributeModifier.Operation.ADDITION));

		attributeModifiers = builder.build();
		this.staffType = staffType;
		this.defaultPrimaryColour = defaultPrimaryColour;
		this.defaultSecondaryColour = defaultSecondaryColour;
		this.isDonorOnly = isDonorOnly;
	}

	@Override
	public void onCraft(ItemStack stack, World world, PlayerEntity player) {
		if(!world.isClient()) {
			NbtCompound tag = stack.getOrCreateSubNbt(Arcanus.MOD_ID);

			if(tag.isEmpty()) {
				NbtList list = new NbtList();

				for(int i = 0; i < 8; i++)
					list.add(i, new Spell().toNbt());

				tag.put("Spells", list);
			}
		}

		super.onCraft(stack, world, player);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if(!world.isClient()) {
			NbtCompound tag = stack.getOrCreateSubNbt(Arcanus.MOD_ID);

			if(tag.isEmpty()) {
				NbtList list = new NbtList();

				for(int i = 0; i < 8; i++)
					list.add(i, new Spell().toNbt());

				tag.put("Spells", list);
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		NbtCompound tag = stack.getSubNbt(Arcanus.MOD_ID);
		int primaryColour = getPrimaryColour(stack);
		int secondaryColour = getSecondaryColour(stack);

		tooltip.add(Arcanus.translate("staff", "primary_color").styled(style -> style.withColor(primaryColour)).append(Text.literal(": " + String.format(Locale.ROOT, "#%06X", primaryColour)).formatted(Formatting.GRAY)));
		tooltip.add(Arcanus.translate("staff", "secondary_color").styled(style -> style.withColor(secondaryColour)).append(Text.literal(": " + String.format(Locale.ROOT, "#%06X", secondaryColour)).formatted(Formatting.GRAY)));
		tooltip.add(Text.empty());

		if(tag != null && !tag.isEmpty()) {
			NbtList list = tag.getList("Spells", NbtElement.COMPOUND_TYPE);

			for(int i = 0; i < list.size(); i++) {
				Spell spell = Spell.fromNbt(list.getCompound(i));

				if(spell.getComponentGroups().isEmpty()) {
					tooltip.add(Arcanus.translate("staff", "invalid_data").formatted(Formatting.DARK_RED));
					return;
				}

				MutableText text = Text.literal(spell.getName()).formatted(spell.getComponentGroups().get(0).isEmpty() ? Formatting.GRAY : Formatting.GREEN);
				tooltip.add(text.append(Text.literal(" (").formatted(Formatting.DARK_GRAY)).append(Arcanus.getSpellPatternAsText(i).formatted(Formatting.GRAY)).append(Text.literal(")").formatted(Formatting.DARK_GRAY)));
			}
		}
	}

	@Override
	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return false;
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		return slot == EquipmentSlot.MAINHAND ? attributeModifiers : super.getAttributeModifiers(slot);
	}

	public static void setPrimaryColour(ItemStack stack, int colour) {
		stack.getOrCreateSubNbt(Arcanus.MOD_ID).putInt("PrimaryColour", colour);
	}

	public static int getPrimaryColour(ItemStack stack) {
		NbtCompound tag = stack.getSubNbt(Arcanus.MOD_ID);
		int colour = ((StaffItem) stack.getItem()).defaultPrimaryColour;

		if(tag != null && tag.contains("PrimaryColour", NbtElement.INT_TYPE))
			colour = tag.getInt("PrimaryColour");

		return colour;
	}

	public static void setSecondaryColour(ItemStack stack, int colour) {
		stack.getOrCreateSubNbt(Arcanus.MOD_ID).putInt("SecondaryColour", colour);
	}

	public static int getSecondaryColour(ItemStack stack) {
		NbtCompound tag = stack.getSubNbt(Arcanus.MOD_ID);
		int colour = ((StaffItem) stack.getItem()).defaultSecondaryColour;

		if(tag != null && tag.contains("SecondaryColour", NbtElement.INT_TYPE))
			colour = tag.getInt("SecondaryColour");

		return colour;
	}
}
