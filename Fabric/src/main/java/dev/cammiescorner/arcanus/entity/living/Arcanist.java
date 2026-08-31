package dev.cammiescorner.arcanus.entity.living;

import dev.cammiescorner.arcanus.data.ArcanusTags;
import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import dev.cammiescorner.arcanus.registry.ArcanusItems;
import dev.cammiescorner.arcanus.util.ArcanusHelper;
import dev.cammiescorner.arcanus.util.TranslationKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.illager.Evoker;
import net.minecraft.world.entity.monster.illager.Illusioner;
import net.minecraft.world.entity.monster.illager.Pillager;
import net.minecraft.world.entity.monster.illager.Vindicator;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Arcanist extends AbstractVillager implements NeutralMob {
	private static final EntityDataAccessor<Integer> ROBE_COLOR = SynchedEntityData.defineId(Arcanist.class, EntityDataSerializers.INT);

	public Arcanist(EntityType<? extends AbstractVillager> entityType, Level world) {
		super(entityType, world);
//		Arrays.fill(armorDropChances, 0.1f);
//		Arrays.fill(handDropChances, 0.05f);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new FloatGoal(this));
		goalSelector.addGoal(2, new TradeWithPlayerGoal(this));
		goalSelector.addGoal(2, new LookAtTradingPlayerGoal(this));
		goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Zombie.class, 8f, 1, 2));
		goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Evoker.class, 12f, 1, 2));
		goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Vindicator.class, 8f, 1, 2));
		goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Vex.class, 8f, 1, 2));
		goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Pillager.class, 15f, 1, 2));
		goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Illusioner.class, 12f, 1, 2));
		goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Zoglin.class, 10f, 1, 2));
		goalSelector.addGoal(3, new MoveTowardsRestrictionGoal(this, 1));
		goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1));
		goalSelector.addGoal(5, new InteractGoal(this, Player.class, 3f, 1f));
		goalSelector.addGoal(6, new LookAtPlayerGoal(this, Mob.class, 8f));
	}

	@Override
	public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @org.jspecify.annotations.Nullable SpawnGroupData groupData) {
		populateDefaultEquipmentSlots(level.getRandom(), difficulty);
		return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		var robeColor = newRandomRobeColor(random);
		setRobeColor(robeColor);
		setItemSlot(EquipmentSlot.HEAD, ArcanusHelper.applyColorToItem(new ItemStack(ArcanusItems.ARCANIST_HAT.get()), robeColor));
		setItemSlot(EquipmentSlot.CHEST, ArcanusHelper.applyColorToItem(new ItemStack(ArcanusItems.ARCANIST_ROBES.get()), robeColor));
		setItemSlot(EquipmentSlot.LEGS, ArcanusHelper.applyColorToItem(new ItemStack(ArcanusItems.ARCANIST_PANTS.get()), robeColor));
		setItemSlot(EquipmentSlot.FEET, ArcanusHelper.applyColorToItem(new ItemStack(ArcanusItems.ARCANIST_BOOTS.get()), robeColor));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ROBE_COLOR, 0xffffffff);
	}

	@Override
	public void notifyTrade(MerchantOffer offer) {
		ambientSoundTime = -getAmbientSoundInterval();
		rewardTradeXp(offer);

		if(getTradingPlayer() instanceof ServerPlayer player)
			CriteriaTriggers.TRADE.trigger(player, this, offer.getResult());
	}

	@Override
	protected void rewardTradeXp(MerchantOffer offer) {
		if(offer.shouldRewardExp())
			level().addFreshEntity(new ExperienceOrb(level(), getX(), getY() + 0.5, getZ(), 3 + random.nextInt(4)));
	}

	@Override
	protected void updateTrades(ServerLevel level) {
		// TODO figure this shit out
//		VillagerTrades.ItemListing[] factories = ArcanusTradeOffers.WIZARD_TRADES.get(1);
//		VillagerTrades.ItemListing[] factories1 = ArcanusTradeOffers.WIZARD_TRADES.get(2);
//
//		if(factories != null && factories1 != null) {
//			MerchantOffers tradeOfferList = getOffers();
//			addOffersFromItemListings(tradeOfferList, factories, 6);
//
//			int i = random.nextInt(factories1.length);
//			VillagerTrades.ItemListing factory = factories1[i];
//			MerchantOffer tradeOffer = factory.getOffer(this, random);
//
//			if(tradeOffer != null)
//				tradeOfferList.add(tradeOffer);
//		}
	}

	@Override
	public boolean showProgressBar() {
		return false;
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return TamableAnimal.createMobAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.ATTACK_DAMAGE, 4).add(Attributes.MOVEMENT_SPEED, 0.15);
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand hand) {
		if(!level().isClientSide()) {
			if(ArcanusComponents.knowsAnySpellComponents(player) || player.getItemBySlot(EquipmentSlot.HEAD).is(ArcanusTags.Items.ARCANIST_ARMOR) || player.getItemBySlot(EquipmentSlot.CHEST).is(ArcanusTags.Items.ARCANIST_ARMOR) || player.getItemBySlot(EquipmentSlot.LEGS).is(ArcanusTags.Items.ARCANIST_ARMOR) || player.getItemBySlot(EquipmentSlot.FEET).is(ArcanusTags.Items.ARCANIST_ARMOR)) {
				if(!getOffers().isEmpty()) {
					setTradingPlayer(player);
					openTradingScreen(player, getDisplayName(), 1);
				}
			}
			else {
				player.sendSystemMessage(Component.translatable(TranslationKeys.ARCANIST_NO_ARCANIST_ARMOR).withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC).withStyle(style -> style.withHoverEvent(new HoverEvent.ShowText(Component.translatable(TranslationKeys.ARCANIST_NO_ARCANIST_ARMOR).withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC)))));
			}
		}

		return InteractionResult.SUCCESS;
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		entityData.set(ROBE_COLOR, input.getIntOr("RobeColor", 0xFF52392A));
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putInt("RobeColor", entityData.get(ROBE_COLOR));
	}

	@Override
	public boolean requiresCustomPersistence() {
		return true;
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
		return null;
	}

	public void setRobeColor(int color) {
		entityData.set(ROBE_COLOR, color);
	}

	public int getRobeColor() {
		return entityData.get(ROBE_COLOR);
	}

	private int newRandomRobeColor(RandomSource random) {
		// Rare Colors
		if(random.nextDouble() <= 0.1) {
			var list = List.of(
				0xffff005a, // Folly Red
				0xfff2dd50 // Lotus Gold
			);
			return list.get(random.nextInt(list.size()));
		}

		// Normal Colors
		var list = List.of(
			0xffffffff,
			0xfff9801d,
			0xffc74ebd,
			0xff3ab3da,
			0xfffed83d,
			0xff80c71f,
			0xfff38baa,
			0xff474f52,
			0xff9d9d97,
			0xff169c9c,
			0xff8932b8,
			0xff52392a,
			0xff3c44aa,
			0xff5e7c16,
			0xffb02e26,
			0xff1d1d21,
			0xfffcc973
		);

		return list.get(random.nextInt(list.size()));
	}

	@Override
	public long getPersistentAngerEndTime() {
		return 0;
	}

	@Override
	public void setPersistentAngerEndTime(long endTime) {

	}

	@Override
	public @Nullable EntityReference<LivingEntity> getPersistentAngerTarget() {
		return null;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable EntityReference<LivingEntity> persistentAngerTarget) {

	}

	@Override
	public void startPersistentAngerTimer() {

	}
}
