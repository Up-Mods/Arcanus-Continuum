package dev.cammiescorner.arcanuscontinuum.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.common.base.PatchouliSounds;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

public class ArcanusCompendiumItem extends Item {
	public ArcanusCompendiumItem() {
		super(new QuiltItemSettings().maxCount(1));
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
}
