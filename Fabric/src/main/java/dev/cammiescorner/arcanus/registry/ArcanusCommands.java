package dev.cammiescorner.arcanus.registry;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.command.LearnSpellComponentsCommand;
import dev.cammiescorner.arcanus.command.PocketDimensionCommand;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ArcanusCommands {
	public static void init(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext, Commands.CommandSelection environment) {
		LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal(Arcanus.MOD_ID);

		LearnSpellComponentsCommand.register(root, commandBuildContext);
		PocketDimensionCommand.register(root);
		dispatcher.register(root);
	}
}
