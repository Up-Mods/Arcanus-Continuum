package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.api.arcana.PrimalArcana;
import dev.cammiescorner.arcanus.api.spell.Pattern;
import dev.cammiescorner.arcanus.api.spell.components.SpellComponent;
import dev.cammiescorner.arcanus.common.block.entities.AbstractMagicBlockEntity;
import dev.cammiescorner.arcanus.common.component.MagicColorComponent;
import dev.cammiescorner.arcanus.common.component.chunk.WardedBlocksComponent;
import dev.cammiescorner.arcanus.common.component.color.GenericMagicColorComponent;
import dev.cammiescorner.arcanus.common.component.color.PlayerMagicColorComponent;
import dev.cammiescorner.arcanus.common.component.entity.*;
import dev.cammiescorner.arcanus.common.component.level.PocketDimensionComponent;
import dev.cammiescorner.arcanus.common.entity.magic.*;
import dev.upcraft.sparkweave.api.color.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.EmptyLevelChunk;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.block.BlockComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.block.BlockComponentInitializer;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentInitializer;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.ladysnake.cca.api.v3.scoreboard.ScoreboardComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.scoreboard.ScoreboardComponentInitializer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ArcanusComponents implements BlockComponentInitializer, ChunkComponentInitializer, EntityComponentInitializer, ScoreboardComponentInitializer {
	public static final ComponentKey<MagicColorComponent> MAGIC_COLOR = createComponent("magic_color", MagicColorComponent.class);

	// ----- Scoreboard Components ----- \\
	public static final ComponentKey<PocketDimensionComponent> POCKET_DIMENSION_COMPONENT = createComponent("pocket_dimension", PocketDimensionComponent.class);

	// ----- Chunk Components ----- \\
	public static final ComponentKey<WardedBlocksComponent> WARDED_BLOCKS_COMPONENT = createComponent("warded_blocks", WardedBlocksComponent.class);

	// ----- Entity Components ----- \\
	public static final ComponentKey<KnownSpellComponentsComponent> KNOWN_SPELL_COMPONENTS_COMPONENT = createComponent("known_spell_components", KnownSpellComponentsComponent.class);
	public static final ComponentKey<ArcanaComponent> ARCANA_COMPONENT = createComponent("arcana", ArcanaComponent.class);
	public static final ComponentKey<CastingComponent> CASTING_COMPONENT = createComponent("casting", CastingComponent.class);
	public static final ComponentKey<PatternComponent> PATTERN_COMPONENT = createComponent("casting_pattern", PatternComponent.class);
	public static final ComponentKey<LastCastTimeComponent> LAST_CAST_TIME_COMPONENT = createComponent("last_cast_time", LastCastTimeComponent.class);
	public static final ComponentKey<StunComponent> STUN_COMPONENT = createComponent("stun", StunComponent.class);
	public static final ComponentKey<BoltTargetComponent> BOLT_TARGET = createComponent("bolt_target", BoltTargetComponent.class);
	public static final ComponentKey<PocketDimensionPortalComponent> POCKET_DIMENSION_PORTAL_COMPONENT = createComponent("pocket_dimension_portal", PocketDimensionPortalComponent.class);
	public static final ComponentKey<StockpileOrbsComponent> STOCKPILE_ORB_COMPONENT = createComponent("stockpile_orbs", StockpileOrbsComponent.class);
	public static final ComponentKey<MagicOrbComponent> MAGIC_ORB_COMPONENT = createComponent("magic_orb", MagicOrbComponent.class);
	public static final ComponentKey<PortalCoolDownComponent> PORTAL_COOL_DOWN_COMPONENT = createComponent("portal_cool_down", PortalCoolDownComponent.class);

	@Override
	public void registerBlockComponentFactories(BlockComponentFactoryRegistry registry) {
		registry.beginRegistration(AbstractMagicBlockEntity.class, MAGIC_COLOR)
			.impl(GenericMagicColorComponent.class)
			.end(GenericMagicColorComponent::new);
	}

	@Override
	public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
		registry.register(WARDED_BLOCKS_COMPONENT, WardedBlocksComponent::new);
	}

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(Player.class, KNOWN_SPELL_COMPONENTS_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(KnownSpellComponentsComponent::new);
		registry.beginRegistration(LivingEntity.class, ARCANA_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(ArcanaComponent::new);
		registry.beginRegistration(Player.class, CASTING_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(CastingComponent::new);
		registry.beginRegistration(Player.class, PATTERN_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(PatternComponent::new);
		registry.beginRegistration(Player.class, POCKET_DIMENSION_PORTAL_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(PocketDimensionPortalComponent::new);
		registry.beginRegistration(LivingEntity.class, LAST_CAST_TIME_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(LastCastTimeComponent::new);
		registry.beginRegistration(LivingEntity.class, STUN_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(StunComponent::new);
		registry.beginRegistration(LivingEntity.class, BOLT_TARGET).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(BoltTargetComponent::new);
		registry.beginRegistration(LivingEntity.class, STOCKPILE_ORB_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(StockpileOrbsComponent::new);
		registry.beginRegistration(LivingEntity.class, MAGIC_ORB_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(MagicOrbComponent::new);
		registry.beginRegistration(Player.class, PORTAL_COOL_DOWN_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(PortalCoolDownComponent::new);

		List.of(
			StockpileOrb.class,
			AreaOfEffect.class,
			Beam.class,
			MagicOrb.class,
			Missile.class,
			MagicRune.class,
			ManaShield.class,
			PocketDimensionPortal.class,
			Smite.class,
			TemporalDilationField.class
		).forEach(type ->
			registry.beginRegistration(type, MAGIC_COLOR)
				.impl(GenericMagicColorComponent.class)
				.end(GenericMagicColorComponent::new)
		);
		registry.registerForPlayers(MAGIC_COLOR, PlayerMagicColorComponent::new, RespawnCopyStrategy.NEVER_COPY);
	}

	private static <T extends Component> ComponentKey<T> createComponent(String name, Class<T> component) {
		return ComponentRegistry.getOrCreate(Arcanus.id(name), component);
	}

	@Nullable
	private static WardedBlocksComponent getWardedBlocksComponent(Level world, BlockPos pos) {
		ChunkAccess chunk = world.getChunk(pos);
		return !(chunk instanceof EmptyLevelChunk) ? chunk.getComponent(WARDED_BLOCKS_COMPONENT) : null;
	}

	// ----- Helper Methods ----- //
	public static void addWardedBlock(Player player, BlockPos pos) {
		WardedBlocksComponent component = getWardedBlocksComponent(player.level(), pos);
		if(component != null)
			component.addWardedBlock(player, pos);
	}

	public static void removeWardedBlock(Player player, BlockPos pos) {
		WardedBlocksComponent component = getWardedBlocksComponent(player.level(), pos);
		if(component != null)
			component.removeWardedBlock(player, pos);
	}

	public static boolean isOwnerOfBlock(Player player, BlockPos pos) {
		WardedBlocksComponent component = getWardedBlocksComponent(player.level(), pos);
		return component != null && component.isOwnerOfBlock(player, pos);
	}

	public static boolean isBlockWarded(Level world, BlockPos pos) {
		WardedBlocksComponent component = getWardedBlocksComponent(world, pos);
		return component != null && component.isBlockWarded(pos);
	}

	public static Map<BlockPos, java.util.UUID> getWardedBlocks(ChunkAccess chunk) {
		if(chunk instanceof EmptyLevelChunk)
			return Map.of();

		return chunk.getComponent(WARDED_BLOCKS_COMPONENT).getWardedBlocks();
	}

	public static double getMaxArcana(LivingEntity entity, PrimalArcana primalArcana) {
		return primalArcana.getMaxArcana(entity);
	}

	public static double getArcanaLock(LivingEntity entity) {
		return entity.getComponent(ARCANA_COMPONENT).getArcanaLock();
	}

	public static double getTrueMaxArcana(LivingEntity entity, PrimalArcana primalArcana) {
		return entity.getComponent(ARCANA_COMPONENT).getTrueMaxArcana(primalArcana);
	}

	public static double getArcana(LivingEntity entity, PrimalArcana primalArcana) {
		return entity.getComponent(ARCANA_COMPONENT).getArcana(primalArcana);
	}

	public static void setArcana(LivingEntity entity, PrimalArcana primalArcana, double amount) {
		entity.getComponent(ARCANA_COMPONENT).setArcana(primalArcana, amount);
	}

	public static boolean addArcana(LivingEntity entity, PrimalArcana primalArcana, double amount, boolean simulate) {
		return entity.getComponent(ARCANA_COMPONENT).addArcana(primalArcana, amount, simulate);
	}

	public static boolean drainArcana(LivingEntity entity, PrimalArcana primalArcana, double amount, boolean simulate) {
		return entity.getComponent(ARCANA_COMPONENT).drainArcana(primalArcana, amount, simulate);
	}

	public static List<SpellComponent> getKnownSpellComponents(Player player) {
		return player.getComponent(KNOWN_SPELL_COMPONENTS_COMPONENT).getKnownComponents();
	}

	public static boolean knowsAnySpellComponents(Player player) {
		List<SpellComponent> knownSpellComponents = getKnownSpellComponents(player);

		return knownSpellComponents.contains(ArcanusSpellComponents.EMPTY.get()) && knownSpellComponents.size() > 1;
	}

	public static boolean knowsSpellComponents(Player player, SpellComponent... components) {
		return getKnownSpellComponents(player).containsAll(Arrays.asList(components));
	}

	public static void learnSpellComponents(Player player, SpellComponent... components) {
		for(SpellComponent spellComponent : components)
			player.getComponent(KNOWN_SPELL_COMPONENTS_COMPONENT).learnSpellComponent(spellComponent);
	}

	public static void forgetSpellComponents(Player player, SpellComponent... components) {
		for(SpellComponent spellComponent : components)
			player.getComponent(KNOWN_SPELL_COMPONENTS_COMPONENT).forgetSpellComponent(spellComponent);
	}

	public static int maxSpellSize() {
		return 8;
	}

	public static boolean isCasting(LivingEntity entity) {
		return CASTING_COMPONENT.get(entity).isCasting();
	}

	public static void setCasting(LivingEntity entity, boolean casting) {
		CASTING_COMPONENT.get(entity).setCasting(casting);
	}

	public static List<Pattern> getPattern(LivingEntity entity) {
		return PATTERN_COMPONENT.get(entity).getPattern();
	}

	public static void setPattern(LivingEntity entity, List<Pattern> pattern) {
		PATTERN_COMPONENT.get(entity).setPattern(pattern);
	}

	public static void clearPattern(LivingEntity entity) {
		PATTERN_COMPONENT.get(entity).clearPattern();
	}

	public static long getLastCastTime(LivingEntity entity) {
		return LAST_CAST_TIME_COMPONENT.get(entity).getLastCastTime();
	}

	public static void setLastCastTime(LivingEntity entity, long time) {
		LAST_CAST_TIME_COMPONENT.get(entity).setLastCastTime(time);
	}

	public static boolean isStunned(Entity entity) {
		return entity instanceof LivingEntity livingEntity && getStunTimer(livingEntity) > 0;
	}

	public static int getStunTimer(LivingEntity entity) {
		return STUN_COMPONENT.get(entity).getStunTimer();
	}

	public static void setStunTimer(LivingEntity entity, int timer) {
		STUN_COMPONENT.get(entity).setStunTimer(timer);
	}

	public static Color getColor(Entity entity) {
		return MAGIC_COLOR.get(entity).getColor();
	}

	public static Vec3 getBoltPos(LivingEntity entity) {
		return BOLT_TARGET.get(entity).getPos();
	}

	public static void setBoltPos(LivingEntity entity, Vec3 pos) {
		BOLT_TARGET.get(entity).setPos(pos);
	}

	public static boolean shouldRenderBolt(LivingEntity entity) {
		return BOLT_TARGET.get(entity).shouldRender();
	}

	public static void setShouldRenderBolt(LivingEntity entity, boolean shouldRender) {
		BOLT_TARGET.get(entity).setShouldRender(shouldRender);
	}

	public static void setBoltAge(LivingEntity entity, int timer) {
		BOLT_TARGET.get(entity).setAge(timer);
	}

	public static void createPortal(Player player, ServerLevel world, Vec3 pos, double pullStrength) {
		POCKET_DIMENSION_PORTAL_COMPONENT.get(player).createPortal(world, pos, pullStrength);
	}

	public static Vec3 getPortalPos(Player player, Level level) {
		return POCKET_DIMENSION_PORTAL_COMPONENT.get(player).getPortalPos(level);
	}

	public static int stockpileOrbCount(LivingEntity entity) {
		return entity.getComponent(STOCKPILE_ORB_COMPONENT).orbCount();
	}

	public static int stockpileOrbIndex(LivingEntity entity, StockpileOrb orb) {
		return entity.getComponent(STOCKPILE_ORB_COMPONENT).orbIndex(orb);
	}

	public static void addStockpileOrbToEntity(LivingEntity entity, UUID orbId) {
		entity.getComponent(STOCKPILE_ORB_COMPONENT).addOrbToEntity(orbId);
	}

	public static void removeStockpileOrbFromEntity(LivingEntity entity, UUID orbId) {
		entity.getComponent(STOCKPILE_ORB_COMPONENT).removeOrbFromEntity(orbId);
	}

	public static UUID getMagicOrbId(LivingEntity entity) {
		return entity.getComponent(MAGIC_ORB_COMPONENT).getOrbId();
	}

	public static void setMagicOrb(LivingEntity entity, UUID orbId) {
		entity.getComponent(MAGIC_ORB_COMPONENT).setOrb(orbId);
	}

	public static void setPortalCoolDown(Entity entity, int cooldownTicks) {
		PORTAL_COOL_DOWN_COMPONENT.maybeGet(entity).ifPresent(component -> component.setCoolDown(cooldownTicks));
	}

	public static boolean hasPortalCoolDown(Entity entity) {
		return PORTAL_COOL_DOWN_COMPONENT.maybeGet(entity).map(PortalCoolDownComponent::hasCoolDown).orElse(false);
	}

	@Override
	public void registerScoreboardComponentFactories(ScoreboardComponentFactoryRegistry registry) {
		registry.registerScoreboardComponent(POCKET_DIMENSION_COMPONENT, PocketDimensionComponent::new);
	}
}
