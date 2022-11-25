package dev.cammiescorner.arcanuscontinuum.common.items;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Spell;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.List;
import java.util.Locale;

public class SpellBookItem extends Item {
	public SpellBookItem() {
		super(new QuiltItemSettings().group(Arcanus.ITEM_GROUP).maxCount(1));
	}

	@Override
	public Text getName(ItemStack stack) {
		Spell spell = getSpell(stack);

		return ((MutableText) super.getName(stack)).append(" (" + spell.getName() + ")");
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		Spell spell = getSpell(stack);

		tooltip.add(Text.literal(spell.getName()).formatted(Formatting.GOLD));
		tooltip.add(Text.translatable("spell_book." + Arcanus.MOD_ID + ".weight").append(": ").formatted(Formatting.GREEN)
				.append(Text.translatable("spell_book." + Arcanus.MOD_ID + ".weight." + spell.getWeight().toString().toLowerCase(Locale.ROOT)).formatted(Formatting.GRAY)));
		tooltip.add(Text.translatable("spell_book." + Arcanus.MOD_ID + ".mana_cost").append(": ").formatted(Formatting.BLUE)
				.append(Text.literal(String.valueOf(spell.getManaCost())).formatted(Formatting.GRAY)));
		tooltip.add(Text.translatable("spell_book." + Arcanus.MOD_ID + ".cooldown").append(": ").formatted(Formatting.RED)
				.append(Text.literal(String.valueOf((double) spell.getCoolDown() / 20D)).append(Text.translatable("spell_book." + Arcanus.MOD_ID + ".seconds")).formatted(Formatting.GRAY)));

		super.appendTooltip(stack, world, tooltip, context);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);

		if(state.isOf(Blocks.LECTERN))
			return LecternBlock.putBookIfAbsent(context.getPlayer(), world, pos, state, context.getStack()) ? ActionResult.success(world.isClient) : ActionResult.PASS;

		return ActionResult.PASS;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return super.use(world, user, hand);
	}

	public static Spell getSpell(ItemStack stack) {
		return stack.hasNbt() ? Spell.fromNbt(stack.getNbt().getCompound("Spell")) : new Spell();
	}
}
