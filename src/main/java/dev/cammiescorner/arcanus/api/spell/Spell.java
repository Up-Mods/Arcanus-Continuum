package dev.cammiescorner.arcanus.api.spell;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.api.spell.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.api.mana.ManaType;
import dev.cammiescorner.arcanus.common.registry.ArcanusAttributes;
import dev.cammiescorner.arcanus.common.util.TranslationKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Spell {
	public static final Codec<Spell> CODEC = RecordCodecBuilder.create(spellInstance -> spellInstance.group(
		Codec.list(SpellGroup.CODEC).optionalFieldOf("ComponentGroups", List.of(new SpellGroup(SpellShape.empty(), List.of(), List.of()))).forGetter(Spell::getComponentGroups),
		Codec.STRING.optionalFieldOf("Name", "Blank").forGetter(Spell::getName),
		SpellAspects.CODEC.optionalFieldOf("Aspects", new SpellAspects(1, 1, 1)).forGetter(Spell::getAspects)
	).apply(spellInstance, Spell::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, Spell> STREAM_CODEC = StreamCodec.composite(
		SpellGroup.STREAM_CODEC.apply(ByteBufCodecs.list()), Spell::getComponentGroups,
		ByteBufCodecs.STRING_UTF8, Spell::getName,
		SpellAspects.STREAM_CODEC, Spell::getAspects,
		Spell::new
	);
	private final List<SpellGroup> groups;
	private final String name;
	private final SpellAspects aspects;

	public Spell(List<SpellGroup> groups, String name, SpellAspects aspects) {
		this.groups = groups;
		this.name = name;
		this.aspects = aspects;
	}

	public Spell() {
		this(List.of(new SpellGroup(SpellShape.empty(), List.of(), List.of())), "Blank", new SpellAspects(1, 1, 1));
	}

	public List<SpellGroup> getComponentGroups() {
		return groups;
	}

	public String getName() {
		return name;
	}

	public SpellAspects getAspects() {
		return aspects;
	}

	public boolean isEmpty() {
		return groups.isEmpty() || groups.get(0).isEmpty();
	}

	public Weight getWeight() {
		int averageWeightIndex = 0;

		if(!groups.isEmpty()) {
			int i = 0;

			for(SpellGroup group : groups) {
				if(group.isEmpty())
					continue;

				averageWeightIndex += group.getWeight().ordinal();
				i++;
			}

			averageWeightIndex = Math.round(averageWeightIndex / (float) i);
		}

		return Weight.values()[averageWeightIndex];
	}

	public Map<ManaType, Double> getManaCost() {
		Map<ManaType, Double> cumulativeManaCost = Arcanus.constructManaMap(0, 0, 0, 0, 0);

		for(ManaType manaType : cumulativeManaCost.keySet()) {
			for(SpellGroup group : groups)
				cumulativeManaCost.put(manaType, cumulativeManaCost.get(manaType) + group.getManaCost().get(manaType));

			cumulativeManaCost.put(manaType, cumulativeManaCost.get(manaType) * getManaMultiplier());
		}

		return cumulativeManaCost;
	}

	public double getManaMultiplier() {
		double manaMultiplier = 1;

		for(SpellGroup group : groups)
			manaMultiplier += group.shape().getManaModifier();

		return manaMultiplier;
	}

	public int getCoolDown() {
		int coolDown = Math.max(Math.toIntExact(components().count()) * ArcanusConfig.coolDownPerComponent, ArcanusConfig.minimumCoolDown);
		double coolDownModifier = 1d;

		if(!groups.isEmpty())
			for(SpellGroup group : groups)
				coolDownModifier *= group.shape().getCoolDownModifier();

		return (int) (coolDown * coolDownModifier);
	}

	public String getManaCostAsString(ManaType manaType) {
		return Arcanus.format(getManaCost().get(manaType));
	}

	public String getCoolDownAsString() {
		return Arcanus.format(getCoolDown() / 20d) + "s";
	}

	public Stream<SpellComponent> components() {
		return groups.stream().flatMap(SpellGroup::getAllComponents);
	}

	public void cast(LivingEntity caster, ServerLevel world, ItemStack stack) {
		List<SpellGroup> groups = getComponentGroups();

		if(groups.isEmpty())
			return;

		if(groups.stream().flatMap(SpellGroup::getAllComponents).anyMatch(Predicate.not(SpellComponent::isEnabled))) {
			caster.sendSystemMessage(Component.translatable(TranslationKeys.SPELL_HAS_DISABLED_COMPONENT).withStyle(ChatFormatting.RED));
			return;
		}

		// start casting the spell
		SpellGroup firstGroup = groups.getFirst();
		firstGroup.shape().cast(caster, caster.position(), null, world, stack, firstGroup.effects(), groups, 0, caster.getAttributeValue(ArcanusAttributes.SPELL_POTENCY.holder()));
	}
}
