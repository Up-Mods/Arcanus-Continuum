package dev.cammiescorner.arcanuscontinuum.common.entities.magic;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSoundEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class SmiteEntity extends Entity {
	private final List<UUID> hasHit = new ArrayList<>();
	private UUID casterId = Util.NIL_UUID;
	private ItemStack stack = ItemStack.EMPTY;
	private List<SpellEffect> effects = new ArrayList<>();
	private double potency;

	public SmiteEntity(EntityType<? extends Entity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
		if(!world.isClient()) {
			if(age <= 9) {
				Box box = new Box(getX() - 4, getY() - 1, getZ() - 4, getX() + 4, (world.getHeight() + 2048) - getY(), getZ() + 4);

				world.getEntitiesByClass(LivingEntity.class, box, livingEntity -> livingEntity.isAlive() && !livingEntity.isSpectator()).forEach(entity -> {
					if(!hasHit.contains(entity.getUuid())) {
						for(SpellEffect effect : new HashSet<>(effects))
							effect.effect(getCaster(), this, world, new EntityHitResult(entity), effects, stack, potency + 0.5);

						hasHit.add(entity.getUuid());
					}
				});
			}

			if(age > 23)
				kill();
		}
		else {
			clientTick();
		}

		super.tick();
	}

	@ClientOnly
	public void clientTick() {
		if(age == 1)
			world.playSound(getX(), getY(), getZ(), ArcanusSoundEvents.SMITE, SoundCategory.NEUTRAL, MathHelper.clamp(1 - (distanceTo(MinecraftClient.getInstance().player) / 100F), 0, 1), (1F + (random.nextFloat() - random.nextFloat()) * 0.2F) * 0.7F, false);
	}

	@Override
	protected void initDataTracker() {

	}

	@Override
	public boolean doesRenderOnFire() {
		return false;
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound tag) {
		effects.clear();
		hasHit.clear();

		casterId = tag.getUuid("CasterId");
		stack = ItemStack.fromNbt(tag.getCompound("ItemStack"));
		potency = tag.getDouble("Potency");

		NbtList effectList = tag.getList("Effects", NbtElement.STRING_TYPE);
		NbtList entityList = tag.getList("HasHit", NbtElement.INT_ARRAY_TYPE);

		for(int i = 0; i < effectList.size(); i++)
			effects.add((SpellEffect) Arcanus.SPELL_COMPONENTS.get(new Identifier(effectList.getString(i))));
		for(NbtElement nbtElement : entityList)
			hasHit.add(NbtHelper.toUuid(nbtElement));
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound tag) {
		NbtList effectList = new NbtList();
		NbtList entityList = new NbtList();

		tag.putUuid("CasterId", casterId);
		tag.put("ItemStack", stack.writeNbt(new NbtCompound()));
		tag.putDouble("Potency", potency);

		for(SpellEffect effect : effects)
			effectList.add(NbtString.of(Arcanus.SPELL_COMPONENTS.getId(effect).toString()));
		for(UUID uuid1 : hasHit)
			entityList.add(NbtHelper.fromUuid(uuid1));

		tag.put("Effects", effectList);
		tag.put("HasHit", entityList);
	}

	private LivingEntity getCaster() {
		if(world instanceof ServerWorld serverWorld && serverWorld.getEntity(casterId) instanceof LivingEntity caster)
			return caster;

		return null;
	}

	public int getColour() {
		return ArcanusComponents.getColour(this);
	}

	public void setProperties(LivingEntity caster, Entity sourceEntity, Vec3d pos, ItemStack stack, List<SpellEffect> effects, double potency, int colour) {
		setPos(pos.getX(), pos.getY(), pos.getZ());
		setNoGravity(true);
		setYaw(sourceEntity.getYaw());
		setPitch(sourceEntity.getPitch());
		this.casterId = caster.getUuid();
		this.stack = stack;
		this.effects = effects;
		this.potency = potency;
		ArcanusComponents.setColour(this, colour);
	}
}
