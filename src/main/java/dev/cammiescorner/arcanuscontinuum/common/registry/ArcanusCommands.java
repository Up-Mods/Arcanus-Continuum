package dev.cammiescorner.arcanuscontinuum.common.registry;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.cammiescorner.arcanuscontinuum.common.components.level.PocketDimensionComponent;
import net.minecraft.command.CommandBuildContext;
import net.minecraft.command.argument.EntityArgumentType;
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
			var pocketDimension = context.getSource().getServer().getWorld(PocketDimensionComponent.POCKET_DIM);
			var component = context.getSource().getWorld().getProperties().getComponent(ArcanusComponents.POCKET_DIMENSION_COMPONENT);

			if(!component.generatePlotSpace(player, pocketDimension)) {
				context.getSource().sendError(Text.literal("Pocket dimension location not found for player %s".formatted(player.getEntityName())));
				return 0;
			}

			context.getSource().sendFeedback(() -> Text.literal("Regenerated %s's pocket dimension".formatted(player.getEntityName())), false);
			return Command.SINGLE_SUCCESS;
		}
	}
}
