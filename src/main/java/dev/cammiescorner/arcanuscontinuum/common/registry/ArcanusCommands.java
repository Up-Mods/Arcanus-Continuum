package dev.cammiescorner.arcanuscontinuum.common.registry;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandBuildContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ArcanusCommands {
	public static void register() {

	}

	public static void init(CommandDispatcher<ServerCommandSource> dispatcher, CommandBuildContext commandBuildContext, CommandManager.RegistrationEnvironment environment) {
		dispatcher.register(CommandManager.literal("wizard_level")
				.then(CommandManager.literal("set")
						.then(CommandManager.argument("level", IntegerArgumentType.integer())
								.executes(LevelCommand::setLevel)
						)
				)
				.then(CommandManager.literal("get")
						.executes(LevelCommand::getLevel)
				)
		);
	}

	private static class LevelCommand {
		public static int getLevel(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
			PlayerEntity player = context.getSource().getPlayer();
			int level = ArcanusComponents.getWizardLevel(player);
			context.getSource().sendFeedback(() -> Text.literal("Wizard Level: " + level), false);

			return Command.SINGLE_SUCCESS;
		}

		public static int setLevel(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
			PlayerEntity player = context.getSource().getPlayer();
			ArcanusComponents.setWizardLevel(player, IntegerArgumentType.getInteger(context, "level"));

			return Command.SINGLE_SUCCESS;
		}
	}
}
