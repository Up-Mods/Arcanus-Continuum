package dev.cammiescorner.arcanus.common.component.entity;

import com.mojang.datafixers.util.Pair;
import dev.cammiescorner.arcanus.api.spell.Spell;
import dev.cammiescorner.arcanus.common.registry.ArcanusComponents;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

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
	public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		spellCoolDowns.clear();

		var list = tag.getList("SpellCoolDowns", Tag.TAG_COMPOUND);

		for(int i = 0; i < list.size(); i++) {
			var nbt = list.getCompound(i);
			var spell = Spell.CODEC.decode(NbtOps.INSTANCE, nbt.getCompound("Spell")).result().orElseGet(() -> Pair.of(new Spell(), nbt.getCompound("Spell"))).getFirst();
			var coolDown = nbt.getInt("CoolDown");

			spellCoolDowns.put(spell, coolDown);
		}
	}

	@Override
	public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
		var list = new ListTag();

		for(Spell spell : spellCoolDowns.keySet()) {
			var nbt = new CompoundTag();
			var spellTag = new CompoundTag();

			Spell.CODEC.encode(spell, NbtOps.INSTANCE, spellTag);

			nbt.put("Spell", spellTag);
			nbt.putInt("CoolDown", spellCoolDowns.getInt(spell));
			list.add(nbt);
		}

		tag.put("SpellCoolDowns", list);
	}
}
