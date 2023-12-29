package dev.cammiescorner.arcanuscontinuum.common.registry;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandBuildContext;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ArcanusCommands {
	public static void init(CommandDispatcher<ServerCommandSource> dispatcher, CommandBuildContext commandBuildContext, CommandManager.RegistrationEnvironment environment) {
		dispatcher.register(CommandManager.literal("wizard_level")
			.then(CommandManager.literal("set").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(CommandManager.ADMIN_PERMISSION_LEVEL))
				.then(CommandManager.argument("player", EntityArgumentType.player())
					.then(CommandManager.argument("level", IntegerArgumentType.integer(0, 10))
						.executes(context -> LevelCommand.setLevel(context, EntityArgumentType.getPlayer(context, "player")))
					)
				)
				.then(CommandManager.argument("level", IntegerArgumentType.integer(0, 10))
					.executes(context -> LevelCommand.setLevel(context, context.getSource().getPlayerOrThrow()))
				)
			)
			.then(CommandManager.literal("get")
				.then(CommandManager.argument("player", EntityArgumentType.player())
					.executes(context -> LevelCommand.getLevel(context, EntityArgumentType.getPlayer(context, "player")))
				).executes(context -> LevelCommand.getLevel(context, context.getSource().getPlayerOrThrow()))
			)
		);
		dispatcher.register(CommandManager.literal("regen_pocket").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(CommandManager.ADMIN_PERMISSION_LEVEL))
			.then(CommandManager.argument("player", EntityArgumentType.player())
				.executes(context -> RegenPocketCommand.regeneratePocket(context, EntityArgumentType.getPlayer(context, "player")))
			).executes(context -> RegenPocketCommand.regeneratePocket(context, context.getSource().getPlayerOrThrow()))
		);
	}

	private static class LevelCommand {
		public static int getLevel(CommandContext<ServerCommandSource> context, ServerPlayerEntity player) throws CommandSyntaxException {
			int level = ArcanusComponents.getWizardLevel(player);
			context.getSource().sendFeedback(() -> Text.literal(String.format("%s's wizard level is %s", player.getEntityName(), level)), false);

			return Command.SINGLE_SUCCESS;
		}

		public static int setLevel(CommandContext<ServerCommandSource> context, ServerPlayerEntity player) throws CommandSyntaxException {
			int level = IntegerArgumentType.getInteger(context, "level");
			ArcanusComponents.setWizardLevel(player, level);

			context.getSource().sendFeedback(() -> Text.literal(String.format("Set %s's level to %s", player.getEntityName(), level)), false);

			return Command.SINGLE_SUCCESS;
		}
	}

	private static class RegenPocketCommand {
		public static int regeneratePocket(CommandContext<ServerCommandSource> context, ServerPlayerEntity player) throws CommandSyntaxException {
			context.getSource().getWorld().getProperties().getComponent(ArcanusComponents.POCKET_DIMENSION_COMPONENT).generateNewPlot(player);
			context.getSource().sendFeedback(() -> Text.literal(String.format("Regenerated %s's pocket dimension", player.getEntityName())), false);

			return Command.SINGLE_SUCCESS;
		}
	}
}
