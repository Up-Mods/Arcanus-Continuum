package dev.cammiescorner.arcanuscontinuum.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Spell;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableItem;
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
import java.util.UUID;

public class StaffItem extends Item implements DyeableItem {
	public static final UUID ATTACK_RANGE_MODIFIER_ID = UUID.fromString("05869d86-c861-4954-9079-68c380ad063c");
	private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

	public StaffItem() {
		this(2);
	}

	public StaffItem(double attackDamage) {
		super(new QuiltItemSettings().maxCount(1));
		ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

		builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", attackDamage, EntityAttributeModifier.Operation.ADDITION));
		builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -1, EntityAttributeModifier.Operation.ADDITION));
		builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(ATTACK_RANGE_MODIFIER_ID, "Weapon modifier", 0.5, EntityAttributeModifier.Operation.ADDITION));

		attributeModifiers = builder.build();
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

		int magicColour = getMagicColour(stack, switch(MinecraftClient.getInstance().player.getUuidAsString()) {
			case "1b44461a-f605-4b29-a7a9-04e649d1981c" -> 0xff005a; // folly red
			case "6147825f-5493-4154-87c5-5c03c6b0a7c2" -> 0xf2dd50; // lotus gold
			case "63a8c63b-9179-4427-849a-55212e6008bf" -> 0x7cff7c; // moriya green
			case "d5034857-9e8a-44cb-a6da-931caff5b838" -> 0xbd78ff; // upcraft pourble
			default -> 0x68e1ff;
		});
		tooltip.add(Text.translatable("staff." + Arcanus.MOD_ID + ".magic_color", Text.literal(String.format("#%06X", magicColour)).styled(style -> style.withColor(magicColour))).formatted(Formatting.GRAY));
		tooltip.add(Text.empty());

		if(tag != null && !tag.isEmpty()) {
			NbtList list = tag.getList("Spells", NbtElement.COMPOUND_TYPE);

			for(int i = 0; i < list.size(); i++) {
				Spell spell = Spell.fromNbt(list.getCompound(i));

				if(spell.getComponentGroups().isEmpty()) {
					tooltip.add(Text.translatable("staff." + Arcanus.MOD_ID + ".invalid_data").formatted(Formatting.DARK_RED));
					return;
				}

				MutableText text = Text.literal(spell.getName()).formatted(spell.getComponentGroups().get(0).shape() == SpellShape.EMPTY ? Formatting.GRAY : Formatting.GREEN);
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

	public static int getMagicColour(ItemStack stack, int defaultColour) {
		NbtCompound nbt = stack.getNbt();
		return nbt != null && nbt.contains("MagicColor", NbtElement.NUMBER_TYPE) ? nbt.getInt("MagicColor") : defaultColour;
	}
}
