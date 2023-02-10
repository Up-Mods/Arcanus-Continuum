package dev.cammiescorner.arcanuscontinuum;

import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.api.spells.Pattern;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellComponent;
import dev.cammiescorner.arcanuscontinuum.common.blocks.MagicDoorBlock;
import dev.cammiescorner.arcanuscontinuum.common.blocks.entities.MagicDoorBlockEntity;
import dev.cammiescorner.arcanuscontinuum.common.packets.c2s.CastSpellPacket;
import dev.cammiescorner.arcanuscontinuum.common.packets.c2s.SaveBookDataPacket;
import dev.cammiescorner.arcanuscontinuum.common.packets.c2s.SetCastingPacket;
import dev.cammiescorner.arcanuscontinuum.common.packets.c2s.SyncPatternPacket;
import dev.cammiescorner.arcanuscontinuum.common.packets.s2c.SyncStatusEffectPacket;
import dev.cammiescorner.arcanuscontinuum.common.registry.*;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.chat.api.QuiltChatEvents;
import org.quiltmc.qsl.chat.api.QuiltMessageType;
import org.quiltmc.qsl.chat.api.types.ChatC2SMessage;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.quiltmc.qsl.networking.api.ServerPlayConnectionEvents;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Arcanus implements ModInitializer {
	public static final String MOD_ID = "arcanuscontinuum";
	public static final DefaultedRegistry<SpellComponent> SPELL_COMPONENTS = FabricRegistryBuilder.createDefaulted(SpellComponent.class, id("spell_components"), id("empty")).buildAndRegister();

	@Override
	public void onInitialize(ModContainer mod) {
		Registry.register(Registries.ENTITY_ATTRIBUTE, id("max_mana"), ArcanusEntityAttributes.MAX_MANA);
		Registry.register(Registries.ENTITY_ATTRIBUTE, id("mana_regen"), ArcanusEntityAttributes.MANA_REGEN);
		Registry.register(Registries.ENTITY_ATTRIBUTE, id("burnout_regen"), ArcanusEntityAttributes.BURNOUT_REGEN);
		Registry.register(Registries.ENTITY_ATTRIBUTE, id("mana_lock"), ArcanusEntityAttributes.MANA_LOCK);
		Registry.register(Registries.ENTITY_ATTRIBUTE, id("spell_potency"), ArcanusEntityAttributes.SPELL_POTENCY);
		Registry.register(Registries.ENTITY_ATTRIBUTE, id("magic_resistance"), ArcanusEntityAttributes.MAGIC_RESISTANCE);

		ArcanusEntities.register();
		ArcanusBlocks.register();
		ArcanusBlockEntities.register();
		ArcanusItems.register();
		ArcanusStatusEffects.register();
		ArcanusSpellComponents.register();
		ArcanusRecipes.register();
		ArcanusScreenHandlers.register();
		ArcanusCommands.register();
		ArcanusPointsOfInterest.register();

		ServerPlayNetworking.registerGlobalReceiver(CastSpellPacket.ID, CastSpellPacket::handler);
		ServerPlayNetworking.registerGlobalReceiver(SetCastingPacket.ID, SetCastingPacket::handler);
		ServerPlayNetworking.registerGlobalReceiver(SaveBookDataPacket.ID, SaveBookDataPacket::handler);
		ServerPlayNetworking.registerGlobalReceiver(SyncPatternPacket.ID, SyncPatternPacket::handler);

		CommandRegistrationCallback.EVENT.register(ArcanusCommands::init);

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> SyncStatusEffectPacket.send(handler.player, ArcanusStatusEffects.ANONYMITY, handler.player.hasStatusEffect(ArcanusStatusEffects.ANONYMITY)));

		QuiltChatEvents.CANCEL.register(EnumSet.of(QuiltMessageType.CHAT), abstractMessage -> {
			PlayerEntity player = abstractMessage.getPlayer();
			World world = player.getWorld();

			if(world instanceof ServerWorld server && abstractMessage instanceof ChatC2SMessage packet) {
				String message = packet.getMessage();

				CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
					PointOfInterestStorage poiStorage = server.getChunkManager().getPointOfInterestStorage();
					Stream<BlockPos> pointOfInterest = poiStorage.getInSquare(poiTypeHolder -> poiTypeHolder.isRegistryKey(ArcanusPointsOfInterest.MAGIC_DOOR), player.getBlockPos(), 8, PointOfInterestStorage.OccupationStatus.ANY).map(PointOfInterest::getPos);
					boolean beep = false;

					for(BlockPos pos : pointOfInterest.collect(Collectors.toSet())) {
						BlockState state = world.getBlockState(pos);

						if(state.getBlock() instanceof MagicDoorBlock doorBlock && world.getBlockEntity(pos) instanceof MagicDoorBlockEntity door && message.equals(door.getPassword())) {
							doorBlock.setOpen(null, world, state, pos, true);
							player.sendMessage(translate("door", "access_granted").formatted(Formatting.GRAY, Formatting.ITALIC), true);

							beep = true;
						}
					}

					return beep;
				}, world.getServer());

				return future.join();
			}

			return false;
		});

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			ItemStack stack = player.getStackInHand(hand);

			if(!world.isClient() && player.isSneaking() && stack.isOf(Items.NAME_TAG) && stack.hasCustomName()) {
				BlockPos pos = hitResult.getBlockPos();
				BlockState state = world.getBlockState(pos);
				MagicDoorBlockEntity door = MagicDoorBlock.getBlockEntity(world, state, pos);

				if(door != null && door.getOwner() == player) {
					door.setPassword(stack.getName().getString());

					if(!player.isCreative())
						stack.decrement(1);

					return ActionResult.SUCCESS;
				}
			}

			return ActionResult.PASS;
		});
	}

	public static Identifier id(String name) {
		return new Identifier(MOD_ID, name);
	}

	public static MutableText translate(@Nullable String prefix, String... value) {
		String translationKey = Arcanus.MOD_ID + "." + String.join(".", value);
		return Text.translatable(prefix != null ? (prefix + "." + translationKey) : translationKey);
	}

	public static int getSpellIndex(List<Pattern> patternList) {
		String pattern = patternList.get(0).getLetter() + patternList.get(1).getLetter() + patternList.get(2).getLetter();

		return switch(pattern) {
			case "LLL" -> 0;
			case "LLR" -> 1;
			case "LRL" -> 2;
			case "LRR" -> 3;
			case "RRR" -> 4;
			case "RRL" -> 5;
			case "RLR" -> 6;
			case "RLL" -> 7;
			default -> 0;
		};
	}

	public static List<Pattern> getSpellPattern(int index) {
		return switch(index) {
			case 0 -> List.of(Pattern.LEFT, Pattern.LEFT, Pattern.LEFT);
			case 1 -> List.of(Pattern.LEFT, Pattern.LEFT, Pattern.RIGHT);
			case 2 -> List.of(Pattern.LEFT, Pattern.RIGHT, Pattern.LEFT);
			case 3 -> List.of(Pattern.LEFT, Pattern.RIGHT, Pattern.RIGHT);
			case 4 -> List.of(Pattern.RIGHT, Pattern.RIGHT, Pattern.RIGHT);
			case 5 -> List.of(Pattern.RIGHT, Pattern.RIGHT, Pattern.LEFT);
			case 6 -> List.of(Pattern.RIGHT, Pattern.LEFT, Pattern.RIGHT);
			case 7 -> List.of(Pattern.RIGHT, Pattern.LEFT, Pattern.LEFT);
			default -> List.of(Pattern.LEFT, Pattern.LEFT, Pattern.LEFT);
		};
	}

	public static MutableText getSpellPatternAsText(int index) {
		String text = switch(index) {
			case 0 -> Pattern.LEFT.getSymbol() + "-" + Pattern.LEFT.getSymbol() + "-" + Pattern.LEFT.getSymbol();
			case 1 -> Pattern.LEFT.getSymbol() + "-" + Pattern.LEFT.getSymbol() + "-" + Pattern.RIGHT.getSymbol();
			case 2 -> Pattern.LEFT.getSymbol() + "-" + Pattern.RIGHT.getSymbol() + "-" + Pattern.LEFT.getSymbol();
			case 3 -> Pattern.LEFT.getSymbol() + "-" + Pattern.RIGHT.getSymbol() + "-" + Pattern.RIGHT.getSymbol();
			case 4 -> Pattern.RIGHT.getSymbol() + "-" + Pattern.RIGHT.getSymbol() + "-" + Pattern.RIGHT.getSymbol();
			case 5 -> Pattern.RIGHT.getSymbol() + "-" + Pattern.RIGHT.getSymbol() + "-" + Pattern.LEFT.getSymbol();
			case 6 -> Pattern.RIGHT.getSymbol() + "-" + Pattern.LEFT.getSymbol() + "-" + Pattern.RIGHT.getSymbol();
			case 7 -> Pattern.RIGHT.getSymbol() + "-" + Pattern.LEFT.getSymbol() + "-" + Pattern.LEFT.getSymbol();
			default -> "ERROR";
		};

		return Text.literal(text);
	}

	public static MutableText getSpellInputs(List<Pattern> pattern) {
		MutableText hyphen = Text.literal("-").formatted(Formatting.GRAY);
		return getSpellInputs(pattern, 0).append(hyphen).append(getSpellInputs(pattern, 1)).append(hyphen).append(getSpellInputs(pattern, 2));
	}

	public static MutableText getSpellInputs(List<Pattern> pattern, int index) {
		return index >= pattern.size() || pattern.get(index) == null ? Text.literal("?").formatted(Formatting.GRAY, Formatting.UNDERLINE) : Text.literal(pattern.get(index).getSymbol()).formatted(Formatting.GREEN);
	}
}
