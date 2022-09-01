package dev.cammiescorner.arcanuscontinuum.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpells;
import net.minecraft.block.BlockState;
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
import net.minecraft.nbt.NbtString;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.List;

public class StaffItem extends Item implements DyeableItem {
	private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

	public StaffItem() {
		this(2);
	}

	public StaffItem(double attackDamage) {
		super(new QuiltItemSettings().maxCount(1).group(Arcanus.ITEM_GROUP));
		ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

		builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", attackDamage, EntityAttributeModifier.Operation.ADDITION));
		builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -1, EntityAttributeModifier.Operation.ADDITION));

		attributeModifiers = builder.build();
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		NbtCompound tag = stack.getOrCreateSubNbt(Arcanus.MOD_ID);

		if(tag.isEmpty()) {
			NbtList list = new NbtList();

			for(int i = 0; i < 8; i++)
				list.add(0, NbtString.of(Arcanus.SPELLS.getId(ArcanusSpells.EMPTY).toString()));

			tag.put("Spells", list);
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		NbtCompound tag = stack.getOrCreateSubNbt(Arcanus.MOD_ID);

		if(!tag.isEmpty()) {
			NbtList list = tag.getList("Spells", NbtElement.STRING_TYPE);

			for(int i = 0; i < list.size(); i++) {
				MutableText text = Text.translatable(Arcanus.SPELLS.get(new Identifier(list.getString(i))).getTranslationKey()).formatted(Formatting.GRAY);
				tooltip.add(text.append(Text.literal(" (").formatted(Formatting.DARK_GRAY)).append(Arcanus.getSpellPatternAsText(i)).append(Text.literal(")").formatted(Formatting.DARK_GRAY)));
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
}
