package dev.cammiescorner.arcanus.api.spell;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.api.spell.components.SpellGroup;
import dev.cammiescorner.arcanus.api.spell.components.SpellShape;
import dev.cammiescorner.arcanus.api.spell.mana.ManaType;
import dev.cammiescorner.arcanus.common.registry.ArcanusAttributes;
import dev.cammiescorner.arcanus.common.util.TranslationKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;


// TODO implement mana reduction & potency increases directly on the individual spell
public class Spell {
	public static final Codec<Spell> CODEC = RecordCodecBuilder.create(spellInstance -> spellInstance.group(
		Codec.list(SpellGroup.CODEC).optionalFieldOf("ComponentGroups", List.of(new SpellGroup(SpellShape.empty(), List.of(), List.of()))).forGetter(Spell::getComponentGroups),
		Codec.STRING.optionalFieldOf("Name", "Blank").forGetter(Spell::getName)
	).apply(spellInstance, Spell::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, Spell> STREAM_CODEC = StreamCodec.of((buffer, spell) -> buffer.writeNbt(spell.toNbt()), buffer -> buffer.readNbt() instanceof CompoundTag tag ? Spell.fromNbt(tag) : new Spell());
	private final List<SpellGroup> groups;
	private final String name;

	public Spell(List<SpellGroup> groups, String name) {
		this.groups = groups;
		this.name = name;
	}

	public Spell() {
		this(List.of(new SpellGroup(SpellShape.empty(), List.of(), List.of())), "Blank");
	}

	public static Spell fromNbt(CompoundTag nbt) {
		List<SpellGroup> groups = new ArrayList<>();
		ListTag nbtList = nbt.getList("ComponentGroups", Tag.TAG_COMPOUND);

		for(int i = 0; i < nbtList.size(); i++) {
			SpellGroup group = SpellGroup.fromNbt(nbtList.getCompound(i));

			if(group.isEmpty())
				return new Spell();

			groups.add(group);
		}

		return new Spell(groups, nbt.getString("Name"));
	}

	public CompoundTag toNbt() {
		CompoundTag nbt = new CompoundTag();
		ListTag nbtList = new ListTag();

		for(SpellGroup group : groups)
			nbtList.add(group.toNbt());

		nbt.put("ComponentGroups", nbtList);
		nbt.putString("Name", name != null ? name : "Empty");

		return nbt;
	}

	public List<SpellGroup> getComponentGroups() {
		return groups;
	}

	public String getName() {
		return name;
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
		int coolDown = Math.max(Math.toIntExact(components().count()) + 8, 10);
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
