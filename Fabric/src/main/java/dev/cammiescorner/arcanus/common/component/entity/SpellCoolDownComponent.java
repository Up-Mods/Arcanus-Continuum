package dev.cammiescorner.arcanus.common.component.entity;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.api.spell.Spell;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.ArrayList;
import java.util.List;

public class SpellCoolDownComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity entity;
	private final Object2IntMap<Spell> spellCoolDowns = new Object2IntArrayMap<>();

	public SpellCoolDownComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {
		Object2IntMap<Spell> temp = new Object2IntArrayMap<>();

		for(Spell spell : spellCoolDowns.keySet()) {
			var coolDown = spellCoolDowns.getInt(spell) - 1;

			if(coolDown > 0)
				temp.put(spell, coolDown);
		}

		spellCoolDowns.clear();
		spellCoolDowns.putAll(temp);
		ArcanusComponents.SPELL_COOL_DOWN_COMPONENT.sync(entity);
	}

	@Override
	public void readData(ValueInput readView) {
		spellCoolDowns.clear();

		for(Pair<Spell, Integer> pair : readView.read("SpellCoolDowns", Codec.pair(Spell.CODEC, Codec.INT).listOf()).orElse(List.of())) {
			spellCoolDowns.put(pair.getFirst(), (int) pair.getSecond());
		}
	}

	@Override
	public void writeData(ValueOutput writeView) {
		List<Pair<Spell, Integer>> list = new ArrayList<>();

		spellCoolDowns.forEach((spell, integer) -> {
			list.add(new Pair<>(spell, integer));
		});

		writeView.store("SpellCoolDowns", Codec.pair(Spell.CODEC, Codec.INT).listOf(), list);
	}
}
