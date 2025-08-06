package dev.cammiescorner.arcanus.common.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.common.entity.living.*;
import dev.cammiescorner.arcanus.common.entity.magic.*;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ArcanusEntities {
	public static final RegistryHandler<EntityType<?>> ENTITY_TYPES = RegistryHandler.create(Registries.ENTITY_TYPE, Arcanus.MOD_ID);

	public static final RegistrySupplier<EntityType<Wizard>> WIZARD = ENTITY_TYPES.register("wizard", () -> EntityType.Builder.of(Wizard::new, MobCategory.MISC).sized(0.7f, 1.8f).build());
	public static final RegistrySupplier<EntityType<CultistCleric>> CULTIST_CLERIC = ENTITY_TYPES.register("cultist_cleric", () -> EntityType.Builder.of(CultistCleric::new, MobCategory.MONSTER).sized(0.6f, 1.8f).build());
	public static final RegistrySupplier<EntityType<CultistKnight>> CULTIST_KNIGHT = ENTITY_TYPES.register("cultist_knight", () -> EntityType.Builder.of(CultistKnight::new, MobCategory.MONSTER).sized(0.6f, 1.8f).build());
	public static final RegistrySupplier<EntityType<Opossum>> OPOSSUM = ENTITY_TYPES.register("opossum", () -> EntityType.Builder.of(Opossum::new, MobCategory.CREATURE).sized(0.6f, 0.7f).build());
	public static final RegistrySupplier<EntityType<NecroSkeleton>> NECRO_SKELETON = ENTITY_TYPES.register("necro_skeleton", () -> EntityType.Builder.of(NecroSkeleton::new, MobCategory.MONSTER).noSummon().sized(0.6f, 1.8f).build());
	public static final RegistrySupplier<EntityType<ManaShield>> MANA_SHIELD = ENTITY_TYPES.register("arcana_shield", () -> EntityType.Builder.of(ManaShield::new, MobCategory.MISC).fireImmune().noSummon().sized(4f, 4f).build());
	public static final RegistrySupplier<EntityType<Missile>> MISSILE = ENTITY_TYPES.register("missile", () -> EntityType.Builder.of(Missile::new, MobCategory.MISC).fireImmune().noSummon().sized(0.6f, 0.6f).build());
	public static final RegistrySupplier<EntityType<Lob>> LOB = ENTITY_TYPES.register("lob", () -> EntityType.Builder.of(Lob::new, MobCategory.MISC).fireImmune().noSummon().sized(0.6f, 0.6f).build());
	public static final RegistrySupplier<EntityType<Smite>> SMITE = ENTITY_TYPES.register("smite", () -> EntityType.Builder.of(Smite::new, MobCategory.MISC).fireImmune().noSummon().sized(4f, 4f).build());
	public static final RegistrySupplier<EntityType<MagicRune>> MAGIC_RUNE = ENTITY_TYPES.register("magic_rune", () -> EntityType.Builder.of(MagicRune::new, MobCategory.MISC).fireImmune().noSummon().sized(1f, 0.125f).build());
	public static final RegistrySupplier<EntityType<GuidedShot>> GUIDED_SHOT = ENTITY_TYPES.register("guided_shot", () -> EntityType.Builder.of(GuidedShot::new, MobCategory.MISC).fireImmune().noSummon().sized(0.6f, 0.6f).build());
	public static final RegistrySupplier<EntityType<AreaOfEffect>> AOE = ENTITY_TYPES.register("area_of_effect", () -> EntityType.Builder.of(AreaOfEffect::new, MobCategory.MISC).fireImmune().noSummon().sized(4f, 2.5f).build());
	public static final RegistrySupplier<EntityType<Beam>> BEAM = ENTITY_TYPES.register("beam", () -> EntityType.Builder.of(Beam::new, MobCategory.MISC).fireImmune().noSummon().sized(0.1f, 0.1f).build());
	public static final RegistrySupplier<EntityType<FollowingOrb>> FOLLOWING_ORB = ENTITY_TYPES.register("following_orb", () -> EntityType.Builder.of(FollowingOrb::new, MobCategory.MISC).clientTrackingRange(60).fireImmune().noSummon().sized(0.4f, 0.4f).build());
	public static final RegistrySupplier<EntityType<Aggressorb>> AGGRESSORB = ENTITY_TYPES.register("aggressorb", () -> EntityType.Builder.of(Aggressorb::new, MobCategory.MISC).clientTrackingRange(60).fireImmune().noSummon().sized(0.4f, 0.4f).build()); // TODO replace aggressorb's name with something better
	public static final RegistrySupplier<EntityType<PocketDimensionPortal>> PORTAL = ENTITY_TYPES.register("pocket_dimension_portal", () -> EntityType.Builder.of(PocketDimensionPortal::new, MobCategory.MISC).fireImmune().noSummon().sized(1.5f, 0.1f).build());
	public static final RegistrySupplier<EntityType<TemporalDilationField>> TEMPORAL_DILATION_FIELD = ENTITY_TYPES.register("temporal_dilation_field", () -> EntityType.Builder.of(TemporalDilationField::new, MobCategory.MISC).fireImmune().noSummon().sized(9f, 9f).build());
}
