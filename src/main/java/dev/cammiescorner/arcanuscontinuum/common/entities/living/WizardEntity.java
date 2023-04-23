package dev.cammiescorner.arcanuscontinuum.common.entities.living;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusItems;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusTradeOffers;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRetaliateTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class WizardEntity extends MerchantEntity implements SmartBrainOwner<WizardEntity>, Angerable {
	private static final TrackedData<Integer> ROBES_COLOUR = DataTracker.registerData(WizardEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public WizardEntity(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
		Arrays.fill(armorDropChances, 0.1F);
		Arrays.fill(handDropChances, 0.05F);
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		initEquipment(world.getRandom(), difficulty);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	@Override
	protected void initEquipment(RandomGenerator random, LocalDifficulty difficulty) {
		dataTracker.set(ROBES_COLOUR, newRandomRobeColour(random));
		equipStack(EquipmentSlot.HEAD, getColouredRobes(ArcanusItems.WIZARD_HAT));
		equipStack(EquipmentSlot.CHEST, getColouredRobes(ArcanusItems.WIZARD_ROBES));
		equipStack(EquipmentSlot.LEGS, getColouredRobes(ArcanusItems.WIZARD_PANTS));
		equipStack(EquipmentSlot.FEET, getColouredRobes(ArcanusItems.WIZARD_BOOTS));
		equipStack(EquipmentSlot.MAINHAND, getRandomStaff(random));
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		dataTracker.startTracking(ROBES_COLOUR, 0);
	}

	@Override
	public void trade(TradeOffer offer) {
		ambientSoundChance = -getMinAmbientSoundDelay();
		afterUsing(offer);

		if(getCurrentCustomer() instanceof ServerPlayerEntity player)
			Criteria.VILLAGER_TRADE.trigger(player, this, offer.getSellItem());
	}

	@Override
	protected void afterUsing(TradeOffer offer) {
		if(offer.shouldRewardPlayerExperience())
			world.spawnEntity(new ExperienceOrbEntity(world, getX(), getY() + 0.5, getZ(), 3 + random.nextInt(4)));
	}

	@Override
	protected void fillRecipes() {
		TradeOffers.Factory[] factories = ArcanusTradeOffers.WIZARD_TRADES.get(1);
		TradeOffers.Factory[] factories1 = ArcanusTradeOffers.WIZARD_TRADES.get(2);

		if(factories != null && factories1 != null) {
			TradeOfferList tradeOfferList = getOffers();
			fillRecipesFromPool(tradeOfferList, factories, 6);

			int i = random.nextInt(factories1.length);
			TradeOffers.Factory factory = factories1[i];
			TradeOffer tradeOffer = factory.create(this, random);

			if(tradeOffer != null)
				tradeOfferList.add(tradeOffer);
		}
	}

	@Override
	public boolean isLeveledMerchant() {
		return false;
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return TameableEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15);
	}

	@Override
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		if(!world.isClient() && !getOffers().isEmpty()) {
			setCurrentCustomer(player);
			sendOffers(player, getDisplayName(), 1);
		}

		return ActionResult.SUCCESS;
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		if(nbt.contains("RobesColour", NbtElement.NUMBER_TYPE))
			dataTracker.set(ROBES_COLOUR, nbt.getInt("RobesColour"));
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("RobesColour", getRobesColour());
	}

	@Override
	public boolean cannotDespawn() {
		return true;
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return null;
	}

	@Override
	public boolean canBeLeashedBy(PlayerEntity player) {
		return false;
	}

	@Override
	protected void mobTick() {
		super.mobTick();
		tickBrain(this);
	}

	@Override
	protected Brain.Profile<?> createBrainProfile() {
		return new SmartBrainProvider<>(this);
	}

	@Override
	public List<ExtendedSensor<WizardEntity>> getSensors() {
		return ObjectArrayList.of(
				new NearbyLivingEntitySensor<>(),
				new HurtBySensor<>()
		);
	}

	@Override
	public BrainActivityGroup<WizardEntity> getCoreTasks() {
		return BrainActivityGroup.coreTasks(
				new FloatToSurfaceOfFluid<>(),
				new LookAtTarget<>(),
				new MoveToWalkTarget<>().stopIf(pathAwareEntity -> getCurrentCustomer() != null)
		);
	}

	@Override
	public BrainActivityGroup<WizardEntity> getIdleTasks() {
		return BrainActivityGroup.idleTasks(
				new FirstApplicableBehaviour<WizardEntity>(
						new SetRetaliateTarget<>(),
						new SetPlayerLookTarget<>(),
						new SetRandomLookTarget<>()
				), new OneRandomBehaviour<>(
						new SetRandomWalkTarget<>().startCondition(pathAwareEntity -> getCurrentCustomer() != null),
						new Idle<>().runFor(entity -> entity.getRandom().nextInt(30) + 30)
				)
		);
	}

	private ItemStack getRandomStaff(RandomGenerator random) {
		List<Item> staves = List.of(
				ArcanusItems.WOODEN_STAFF, ArcanusItems.CRYSTAL_STAFF, ArcanusItems.DIVINATION_STAFF,
				ArcanusItems.CRESCENT_STAFF, ArcanusItems.ANCIENT_STAFF
		);

		return new ItemStack(staves.get(random.nextInt(staves.size())));
	}

	public int getRobesColour() {
		return dataTracker.get(ROBES_COLOUR);
	}

	public ItemStack getColouredRobes(Item robes) {
		ItemStack stack = new ItemStack(robes);
		stack.getOrCreateSubNbt("display").putInt("color", getRobesColour());
		return stack;
	}

	private int newRandomRobeColour(RandomGenerator random) {
		// Rare Colours
		if(random.nextDouble() <= 0.1) {
			int chance = random.nextInt(2);

			return switch(chance) {
				case 0 -> 0xff005a; // Folly Red
				case 1 -> 0xf2dd50; // Lotus Gold
				default -> throw new IllegalStateException("Unexpected value: " + chance);
			};
		}

		// Normal Colours
		int chance = random.nextInt(17);

		return switch(chance) {
			case 0 -> 0xffffff;
			case 1 -> 0xf9801d;
			case 2 -> 0xc74ebd;
			case 3 -> 0x3ab3da;
			case 4 -> 0xfed83d;
			case 5 -> 0x80c71f;
			case 6 -> 0xf38baa;
			case 7 -> 0x474f52;
			case 8 -> 0x9d9d97;
			case 9 -> 0x169c9c;
			case 10 -> 0x8932b8;
			case 11 -> 0x52392a;
			case 12 -> 0x3c44aa;
			case 13 -> 0x5e7c16;
			case 14 -> 0xb02e26;
			case 15 -> 0x1d1d21;
			case 16 -> 0xfcc973;
			default -> throw new IllegalStateException("Unexpected value: " + chance);
		};
	}

	@Override
	public int getAngerTime() {
		return 0;
	}

	@Override
	public void setAngerTime(int ticks) {

	}

	@Nullable
	@Override
	public UUID getAngryAt() {
		return null;
	}

	@Override
	public void setAngryAt(@Nullable UUID uuid) {

	}

	@Override
	public void chooseRandomAngerTime() {

	}
}
