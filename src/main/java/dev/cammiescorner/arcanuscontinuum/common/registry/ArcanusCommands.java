package dev.cammiescorner.arcanuscontinuum.common.registry;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellComponent;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.command.CommandBuildContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.SingletonArgumentInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ArcanusCommands {
	public static void register() {
		ArgumentTypeRegistry.registerArgumentType(Arcanus.id("spell"), SpellArgumentType.class, SingletonArgumentInfo.contextFree(SpellArgumentType::new));
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

		dispatcher.register(CommandManager.literal("spell_components")
				.then(CommandManager.literal("learn")
						.then(CommandManager.argument("spell_component", SpellArgumentType.spell())
								.executes(SpellsCommand::learnSpellComponent)
						)
						.then(CommandManager.argument("all", StringArgumentType.word())
								.executes(SpellsCommand::learnAllSpellComponents)
						)
				)
				.then(CommandManager.literal("list")
						.executes(SpellsCommand::getSpellComponents)
				)
		);
	}

	public static class SpellArgumentType implements ArgumentType<SpellComponent> {
		public static final DynamicCommandExceptionType INVALID_SPELL_EXCEPTION = new DynamicCommandExceptionType(object -> Text.translatable("commands." + Arcanus.MOD_ID + ".spell_components.not_found", object));

		public static SpellArgumentType spell() {
			return new SpellArgumentType();
		}

		public static SpellComponent getSpell(CommandContext<ServerCommandSource> commandContext, String string) {
			return commandContext.getArgument(string, SpellComponent.class);
		}

		@Override
		public SpellComponent parse(StringReader reader) throws CommandSyntaxException {
			Identifier identifier = Identifier.fromCommandInput(reader);
			return Arcanus.SPELL_COMPONENTS.getOrEmpty(identifier).orElseThrow(() -> INVALID_SPELL_EXCEPTION.create(identifier));
		}

		@Override
		public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
			return CommandSource.suggestIdentifiers(Arcanus.SPELL_COMPONENTS.getIds(), builder);
		}
	}

	private static class BurnoutCommand {
		public static int giveBurnout(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
			PlayerEntity player = context.getSource().getPlayer();
			int burnout = IntegerArgumentType.getInteger(context, "amount");

			ArcanusComponents.addBurnout(player, burnout, false);

			return Command.SINGLE_SUCCESS;
		}
	}

	private static class LevelCommand {
		public static int getLevel(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
			PlayerEntity player = context.getSource().getPlayer();
			int level = ArcanusComponents.getWizardLevel(player);
			context.getSource().sendFeedback(Text.literal("Wizard Level: " + level), false);

			return Command.SINGLE_SUCCESS;
		}

		public static int setLevel(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
			PlayerEntity player = context.getSource().getPlayer();
			ArcanusComponents.setWizardLevel(player, IntegerArgumentType.getInteger(context, "level"));

			return Command.SINGLE_SUCCESS;
		}
	}

	private static class SpellsCommand {
		public static int getSpellComponents(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
			PlayerEntity player = context.getSource().getPlayer();
			Set<SpellComponent> components = ArcanusComponents.getKnownComponents(player);

			context.getSource().sendFeedback(Text.literal("Known Spell Components:"), false);

			for(SpellComponent component : components)
				context.getSource().sendFeedback(Text.literal("  - ").append(Text.translatable(component.getTranslationKey(player))), false);

			return Command.SINGLE_SUCCESS;
		}

		public static int learnSpellComponent(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
			PlayerEntity player = context.getSource().getPlayer();
			SpellComponent component = SpellArgumentType.getSpell(context, "spell_component");
			ArcanusComponents.addComponent(player, component);

			return Command.SINGLE_SUCCESS;
		}

		public static int learnAllSpellComponents(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
			PlayerEntity player = context.getSource().getPlayer();

			for(SpellComponent component : Arcanus.SPELL_COMPONENTS)
				if(!ArcanusComponents.hasComponent(player, component))
					ArcanusComponents.addComponent(player, component);

			return Command.SINGLE_SUCCESS;
		}
	}
}
