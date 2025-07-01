package dev.cammiescorner.arcanus.common.command.pocket_dim;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.command.PocketDimensionCommand;
import dev.cammiescorner.arcanus.common.components.level.PocketDimensionComponent;
import dev.cammiescorner.arcanus.common.components.level.PocketDimensionPlot;
import dev.cammiescorner.arcanus.common.data.ArcanusDimensions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static dev.cammiescorner.arcanus.common.util.TranslationKeys.*;

public class ExportPocketDimensionCommand {

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	public static void register(RequiredArgumentBuilder<CommandSourceStack, EntitySelector> builder) {
		builder.then(Commands.literal("export")
			.requires(source -> source.hasPermission(Commands.LEVEL_GAMEMASTERS))
			.executes(context -> ExportPocketDimensionCommand.export(context, PocketDimensionCommand.getPlayerProfile(context)))
		);
	}

	private static int export(CommandContext<CommandSourceStack> context, GameProfile targetProfile) {
		String date = LocalDateTime.now().format(FORMAT);
		MinecraftServer server = context.getSource().getServer();
		ServerLevel pocketDim = server.getLevel(ArcanusDimensions.POCKET_DIMENSION);

		if(pocketDim == null) {
			context.getSource().sendFailure(Component.translatable(COMMAND_EXPORT_POCKET_DIM_NOT_FOUND, ArcanusDimensions.POCKET_DIMENSION));
			return 0;
		}

		PocketDimensionPlot plot = PocketDimensionComponent.get(server).getAssignedPlotSpace(targetProfile.getId());

		if(plot == null) {
			context.getSource().sendFailure(Component.translatable(COMMAND_EXPORT_POCKET_POCKET_NOT_FOUND, targetProfile.getName()));
			return 0;
		}

		ResourceLocation structureId = Arcanus.id("pocket_dimensions/%s/pocket_dimensions_%s".formatted(targetProfile.getId(), date));
		Arcanus.LOGGER.info("Saving pocket dimension for player {} ({}) as '{}'", targetProfile.getName(), targetProfile.getId(), structureId);

		StructureTemplateManager templateManager = pocketDim.getStructureManager();
		StructureTemplate structure = templateManager.getOrCreate(structureId);

		structure.setAuthor("%s_pocket_dimension_%s_%s".formatted(Arcanus.MOD_ID, targetProfile.getId(), date));
		structure.fillFromWorld(pocketDim, plot.min(), plot.max().offset(1, 1, 1).subtract(plot.min()), true, null);
		templateManager.save(structureId);

		context.getSource().sendSuccess(() -> Component.translatable(COMMAND_EXPORT_POCKET_SUCCESS, targetProfile.getName()), true);


		return Command.SINGLE_SUCCESS;
	}
}
