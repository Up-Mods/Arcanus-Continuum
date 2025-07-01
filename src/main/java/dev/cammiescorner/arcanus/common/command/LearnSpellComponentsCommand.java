package dev.cammiescorner.arcanus.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.cammiescorner.arcanus.api.ArcanusRegistries;
import dev.cammiescorner.arcanus.api.spells.SpellComponent;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.common.util.TranslationKeys;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public class LearnSpellComponentsCommand {
	public static void register(LiteralArgumentBuilder<CommandSourceStack> builder, CommandBuildContext context) {
		builder.then(Commands.literal("spell_components")
			.then(Commands.literal("list")
				.requires(serverCommandSource -> serverCommandSource.hasPermission(Commands.LEVEL_GAMEMASTERS))
				.then(Commands.argument("player", EntityArgument.player())
					.executes(ctx -> getSpellComponents(ctx, EntityArgument.getPlayer(ctx, "player")))
				)
				.executes(ctx -> getSpellComponents(ctx, ctx.getSource().getPlayerOrException()))
			)
			.then(Commands.literal("grant")
				.requires(serverCommandSource -> serverCommandSource.hasPermission(Commands.LEVEL_GAMEMASTERS))
				.then(Commands.argument("spell_component", ResourceArgument.resource(context, ArcanusRegistries.SPELL_COMPONENTS))
					.then(Commands.argument("player", EntityArgument.player())
						.executes(ctx -> grantSpellComponent(ctx, EntityArgument.getPlayer(ctx, "player")))
					)
					.executes(ctx -> grantSpellComponent(ctx, ctx.getSource().getPlayerOrException()))
				)
			)
			.then(Commands.literal("revoke")
				.requires(serverCommandSource -> serverCommandSource.hasPermission(Commands.LEVEL_GAMEMASTERS))
				.then(Commands.argument("spell_component", ResourceArgument.resource(context, ArcanusRegistries.SPELL_COMPONENTS))
					.then(Commands.argument("player", EntityArgument.player())
						.executes(ctx -> revokeSpellComponent(ctx, EntityArgument.getPlayer(ctx, "player")))
					)
					.executes(ctx -> revokeSpellComponent(ctx, ctx.getSource().getPlayerOrException()))
				)
			)
		);
	}

	public static int getSpellComponents(CommandContext<CommandSourceStack> context, ServerPlayer player) throws CommandSyntaxException {
		List<SpellComponent> knownComponents = ArcanusComponents.getKnownSpellComponents(player);

		if(!ArcanusComponents.knowsAnySpellComponents(player)) {
			context.getSource().sendSuccess(() -> Component.translatable(TranslationKeys.COMMAND_SPELL_COMPONENT_LIST_FAIL, player.getScoreboardName()), true);
			return 0;
		}

		context.getSource().sendSuccess(() -> {
			MutableComponent text = Component.translatable(TranslationKeys.COMMAND_SPELL_COMPONENT_LIST_SUCCESS, player.getScoreboardName());

			for(SpellComponent spellComponent : knownComponents)
				text.append(Component.literal("\n  - ").append(Component.translatable(spellComponent.getTranslationKey())));

			return text;
		}, true);

		return Command.SINGLE_SUCCESS;
	}

	public static int grantSpellComponent(CommandContext<CommandSourceStack> context, ServerPlayer player) throws CommandSyntaxException {
		Holder.Reference<SpellComponent> spellComponent = getSpellComponent(context, "spell_component");

		if(ArcanusComponents.knowsSpellComponents(player, spellComponent.value())) {
			context.getSource().sendSuccess(() -> Component.translatable(TranslationKeys.COMMAND_SPELL_COMPONENT_LEARN_FAIL, player.getScoreboardName(), Component.translatable(spellComponent.value().getTranslationKey())), true);
			return 0;
		}

		ArcanusComponents.learnSpellComponents(player, spellComponent.value());
		context.getSource().sendSuccess(() -> Component.translatable(TranslationKeys.COMMAND_SPELL_COMPONENT_LEARN_SUCCESS, player.getScoreboardName(), Component.translatable(spellComponent.value().getTranslationKey())), true);

		return Command.SINGLE_SUCCESS;
	}

	public static int revokeSpellComponent(CommandContext<CommandSourceStack> context, ServerPlayer player) throws CommandSyntaxException {
		Holder.Reference<SpellComponent> spellComponent = getSpellComponent(context, "spell_component");

		if(!ArcanusComponents.knowsSpellComponents(player, spellComponent.value())) {
			context.getSource().sendSuccess(() -> Component.translatable(TranslationKeys.COMMAND_SPELL_COMPONENT_REVOKE_FAIL, player.getScoreboardName(), Component.translatable(spellComponent.value().getTranslationKey())), true);
			return 0;
		}

		ArcanusComponents.forgetSpellComponents(player, spellComponent.value());
		context.getSource().sendSuccess(() -> Component.translatable(TranslationKeys.COMMAND_SPELL_COMPONENT_REVOKE_SUCCESS, player.getScoreboardName(), Component.translatable(spellComponent.value().getTranslationKey())), true);

		return Command.SINGLE_SUCCESS;
	}

	public static Holder.Reference<SpellComponent> getSpellComponent(CommandContext<CommandSourceStack> context, String name) throws CommandSyntaxException {
		return ResourceArgument.getResource(context, name, ArcanusRegistries.SPELL_COMPONENTS);
	}
}
