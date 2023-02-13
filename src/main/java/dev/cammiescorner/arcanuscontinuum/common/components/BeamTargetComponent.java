package dev.cammiescorner.arcanuscontinuum.common.components;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.hit.EntityHitResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class BeamTargetComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity entity;
	private UUID casterId = Util.NIL_UUID;
	private ItemStack stack = ItemStack.EMPTY;
	private List<SpellEffect> effects = new ArrayList<>();
	private List<SpellGroup> spellGroups = new ArrayList<>();
	private int groupIndex;
	private double potency;
	private int timer;

	public BeamTargetComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {
		if(entity.world instanceof ServerWorld world && timer > 0) {
			setTimer(getTimer() - 1);

			if(timer == 0) {
				for(SpellEffect effect : new HashSet<>(effects))
					effect.effect(getCaster(), world, new EntityHitResult(entity), effects, stack, potency + 0.25);

				SpellShape.castNext(getCaster(), entity.getPos(), entity, world, stack, spellGroups, groupIndex, potency);
				casterId = Util.NIL_UUID;
				stack = ItemStack.EMPTY;
				effects.clear();
				spellGroups.clear();
				groupIndex = 0;
				potency = 0;
			}
		}
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		effects.clear();
		spellGroups.clear();

		casterId = tag.getUuid("CasterId");
		stack = ItemStack.fromNbt(tag.getCompound("ItemStack"));
		potency = tag.getDouble("Potency");
		groupIndex = tag.getInt("GroupIndex");
		timer = tag.getInt("Timer");

		NbtList effectList = tag.getList("Effects", NbtElement.STRING_TYPE);
		NbtList groupsList = tag.getList("SpellGroups", NbtElement.COMPOUND_TYPE);

		for(int i = 0; i < effectList.size(); i++)
			effects.add((SpellEffect) Arcanus.SPELL_COMPONENTS.get(new Identifier(effectList.getString(i))));
		for(int i = 0; i < groupsList.size(); i++)
			spellGroups.add(SpellGroup.fromNbt(groupsList.getCompound(i)));
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		NbtList effectList = new NbtList();
		NbtList groupsList = new NbtList();

		tag.putUuid("CasterId", casterId);
		tag.put("ItemStack", stack.writeNbt(new NbtCompound()));
		tag.putDouble("Potency", potency);
		tag.putInt("GroupIndex", groupIndex);
		tag.putInt("Timer", timer);

		for(SpellEffect effect : effects)
			effectList.add(NbtString.of(Arcanus.SPELL_COMPONENTS.getId(effect).toString()));
		for(SpellGroup group : spellGroups)
			groupsList.add(group.toNbt());

		tag.put("Effects", effectList);
		tag.put("SpellGroups", groupsList);
	}

	private LivingEntity getCaster() {
		if(entity.world instanceof ServerWorld serverWorld && serverWorld.getEntity(casterId) instanceof LivingEntity caster)
			return caster;

		return null;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
		ArcanusComponents.BEAM_TARGET.sync(entity, (buf, recipient) -> buf.writeInt(timer));
	}

	public void setProperties(LivingEntity caster, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> groups, int groupIndex, double potency, int timer) {
		this.casterId = caster.getUuid();
		this.stack = stack;
		this.effects = effects;
		this.spellGroups = groups;
		this.groupIndex = groupIndex;
		this.potency = potency;
		setTimer(timer);
	}
}
