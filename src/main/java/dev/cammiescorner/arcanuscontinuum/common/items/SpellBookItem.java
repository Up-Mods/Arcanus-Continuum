package dev.cammiescorner.arcanuscontinuum.common.items;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Spell;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.common.base.PatchouliSounds;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

import java.util.List;
import java.util.Locale;

public class SpellBookItem extends Item {
	private final Spell spell;

	public SpellBookItem(Spell spell) {
		super(new QuiltItemSettings().group(Arcanus.ITEM_GROUP).maxCount(1));
		this.spell = spell;
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("spell_book." + Arcanus.MOD_ID + ".weight").append(": ").formatted(Formatting.GREEN)
				.append(Text.translatable("spell_book." + Arcanus.MOD_ID + ".weight." + spell.getWeight().toString().toLowerCase(Locale.ROOT)).formatted(Formatting.GRAY)));
		tooltip.add(Text.translatable("spell_book." + Arcanus.MOD_ID + ".mana_cost").append(": ").formatted(Formatting.BLUE)
				.append(Text.literal(String.valueOf(spell.getManaCost())).formatted(Formatting.GRAY)));
		tooltip.add(Text.translatable("spell_book." + Arcanus.MOD_ID + ".cooldown").append(": ").formatted(Formatting.RED)
				.append(Text.literal(String.valueOf((double) spell.getCooldown() / 20D)).append(Text.translatable("spell_book." + Arcanus.MOD_ID + ".seconds")).formatted(Formatting.GRAY)));

		super.appendTooltip(stack, world, tooltip, context);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if(user instanceof ServerPlayerEntity player) {
			Book book = BookRegistry.INSTANCE.books.get(Registry.ITEM.getId(this));
			PatchouliAPI.get().openBookGUI(player, book.id);
			user.playSound(PatchouliSounds.getSound(book.openSound, PatchouliSounds.BOOK_OPEN), 1, (float) (0.7 + Math.random() * 0.4));
		}

		return TypedActionResult.success(user.getStackInHand(hand));
	}

	public Spell getSpell() {
		return spell;
	}
}
