package dev.cammiescorner.arcanus.common.command.pocket_dim;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.cammiescorner.arcanus.common.command.PocketDimensionCommand;
import dev.cammiescorner.arcanus.common.component.level.PocketDimensionComponent;
import dev.cammiescorner.arcanus.common.data.ArcanusDimensions;
import dev.cammiescorner.arcanus.common.util.TranslationKeys;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;

public class RegeneratePocketDimensionCommand {

	public static void register(RequiredArgumentBuilder<CommandSourceStack, EntitySelector> builder) {
		builder.then(Commands.literal("regenerate")
			.requires(serverCommandSource -> Commands.LEVEL_ADMINS.check(serverCommandSource.permissions()))
			.executes(context -> RegeneratePocketDimensionCommand.regeneratePocket(context, PocketDimensionCommand.getPlayerProfile(context), PocketDimensionComponent.RegenerateType.FULL))
		);
		builder.then(Commands.literal("repair_walls")
			.requires(serverCommandSource -> Commands.LEVEL_ADMINS.check(serverCommandSource.permissions()))
			.executes(context -> RegeneratePocketDimensionCommand.regeneratePocket(context, PocketDimensionCommand.getPlayerProfile(context), PocketDimensionComponent.RegenerateType.WALLS_ONLY))
		);
	}

	public static int regeneratePocket(CommandContext<CommandSourceStack> context, GameProfile target, PocketDimensionComponent.RegenerateType regenerateType) throws CommandSyntaxException {
		var server = context.getSource().getServer();
		var pocketDimension = server.getLevel(ArcanusDimensions.POCKET_DIMENSION);
		var component = PocketDimensionComponent.get(server);

		if(!component.replacePlotSpace(target.id(), pocketDimension, regenerateType)) {
			context.getSource().sendFailure(Component.literal("Pocket dimension location not found for player %s (%s)".formatted(target.name(), target.id())));
			return 0;
		}

		context.getSource().sendSuccess(() -> switch(regenerateType) {
			case WALLS_ONLY ->
				Component.translatable(TranslationKeys.COMMAND_REGEN_POCKET_WALLS_ONLY, target.name());
			case FULL ->
				Component.translatable(TranslationKeys.COMMAND_REGEN_POCKET_SUCCESS, target.name());
			default -> throw new UnsupportedOperationException();
		}, true);
		return Command.SINGLE_SUCCESS;
	}
}
