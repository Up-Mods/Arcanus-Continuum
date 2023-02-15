package dev.cammiescorner.arcanuscontinuum.common.screens;

import dev.cammiescorner.arcanuscontinuum.common.entities.living.WizardEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusScreenHandlers;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.tslat.smartbrainlib.util.BrainUtils;

public class DialogueScreenHandler extends ScreenHandler {
	public WizardEntity wizard;
	public PlayerInventory inventory;

	public DialogueScreenHandler(int syncId) {
		super(ArcanusScreenHandlers.DIALOGUE_SCREEN_HANDLER, syncId);
	}

	public DialogueScreenHandler(int syncId, WizardEntity wizard, PlayerInventory inventory) {
		this(syncId);
		this.wizard = wizard;
		this.inventory = inventory;
		this.wizard.talkingPlayers.add(this.inventory.player);
		BrainUtils.setMemory(wizard, MemoryModuleType.LOOK_TARGET, new EntityLookTarget(inventory.player, true));
	}

	@Override
	public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}

	@Override
	public void close(PlayerEntity player) {
		if(wizard != null) {
			wizard.talkingPlayers.remove(inventory.player);
			BrainUtils.clearMemory(wizard, MemoryModuleType.LOOK_TARGET);
		}

		super.close(player);
	}
}
