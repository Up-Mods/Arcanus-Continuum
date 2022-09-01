package dev.cammiescorner.arcanuscontinuum;

import dev.cammiescorner.arcanuscontinuum.api.entities.ArcanusEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.api.spells.Pattern;
import dev.cammiescorner.arcanuscontinuum.api.spells.Spell;
import dev.cammiescorner.arcanuscontinuum.common.packets.c2s.CastSpellPacket;
import dev.cammiescorner.arcanuscontinuum.common.packets.c2s.SetCastingPacket;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusItems;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpells;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Arcanus implements ModInitializer {
	public static final String MOD_ID = "arcanuscontinuum";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ItemGroup ITEM_GROUP = QuiltItemGroup.createWithIcon(id("general"), () -> new ItemStack(ArcanusItems.WOODEN_STAFF));
	public static final DefaultedRegistry<Spell> SPELLS = FabricRegistryBuilder.createDefaulted(Spell.class, id("spells"), id("empty")).buildAndRegister();

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Magic Mod 2: Electric Boogaloo");

		Registry.register(Registry.ATTRIBUTE, id("max_mana"), ArcanusEntityAttributes.MAX_MANA);
		Registry.register(Registry.ATTRIBUTE, id("mana_regen"), ArcanusEntityAttributes.MANA_REGEN);
		Registry.register(Registry.ATTRIBUTE, id("burnout_regen"), ArcanusEntityAttributes.BURNOUT_REGEN);
		Registry.register(Registry.ATTRIBUTE, id("mana_lock"), ArcanusEntityAttributes.MANA_LOCK);
		Registry.register(Registry.ATTRIBUTE, id("spell_potency"), ArcanusEntityAttributes.SPELL_POTENCY);

		ArcanusSpells.register();
		ArcanusItems.register();

		ServerPlayNetworking.registerGlobalReceiver(CastSpellPacket.ID, CastSpellPacket::handler);
		ServerPlayNetworking.registerGlobalReceiver(SetCastingPacket.ID, SetCastingPacket::handler);
	}

	public static Identifier id(String name) {
		return new Identifier(MOD_ID, name);
	}

	public static int getSpellIndex(List<Pattern> patternList) {
		String pattern = patternList.get(0).getSymbol() + patternList.get(1).getSymbol() + patternList.get(2).getSymbol();

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
			case 0 -> "L-L-L";
			case 1 -> "L-L-R";
			case 2 -> "L-R-L";
			case 3 -> "L-R-R";
			case 4 -> "R-R-R";
			case 5 -> "R-R-L";
			case 6 -> "R-L-R";
			case 7 -> "R-L-L";
			default -> "ERROR";
		};

		return Text.literal(text).formatted(Formatting.GRAY);
	}

	public static MutableText getSpellInputs(List<Pattern> pattern) {
		MutableText hyphen = Text.literal("-").formatted(Formatting.GRAY);
		return getSpellInputs(pattern, 0).append(hyphen).append(getSpellInputs(pattern, 1)).append(hyphen).append(getSpellInputs(pattern, 2));
	}

	public static MutableText getSpellInputs(List<Pattern> pattern, int index) {
		return index >= pattern.size() || pattern.get(index) == null ? Text.literal("?").formatted(Formatting.GRAY, Formatting.UNDERLINE) : Text.literal(pattern.get(index).getSymbol()).formatted(Formatting.GREEN);
	}
}
