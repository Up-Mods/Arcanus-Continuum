package dev.cammiescorner.arcanus.registry;

import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.entity.living.*;
import dev.cammiescorner.arcanus.entity.magic.*;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import dev.upcraft.sparkweave.api.registry.entity.EntityRegistryHandler;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ArcanusEntities {
	public static final EntityRegistryHandler ENTITY_TYPES = RegistryHandler.entities(Arcanus.MOD_ID);

	public static final RegistrySupplier<EntityType<Arcanist>> ARCANIST = ENTITY_TYPES.register("arcanist", Arcanist::new, MobCategory.MISC, builder -> builder.sized(0.7f, 1.8f));
	public static final RegistrySupplier<EntityType<CultistCleric>> CULTIST_CLERIC = ENTITY_TYPES.register("cultist_cleric", CultistCleric::new, MobCategory.MONSTER, builder -> builder.sized(0.6f, 1.8f));
	public static final RegistrySupplier<EntityType<CultistKnight>> CULTIST_KNIGHT = ENTITY_TYPES.register("cultist_knight", CultistKnight::new, MobCategory.MONSTER, builder -> builder.sized(0.6f, 1.8f));
	public static final RegistrySupplier<EntityType<Opossum>> OPOSSUM = ENTITY_TYPES.register("opossum", Opossum::new, MobCategory.CREATURE, builder -> builder.sized(0.6f, 0.7f));
	public static final RegistrySupplier<EntityType<NecroSkeleton>> NECRO_SKELETON = ENTITY_TYPES.register("necro_skeleton", NecroSkeleton::new, MobCategory.MONSTER, builder -> builder.noSummon().sized(0.6f, 1.8f));
	public static final RegistrySupplier<EntityType<ManaShield>> MANA_SHIELD = ENTITY_TYPES.register("mana_shield", ManaShield::new, MobCategory.MISC, builder -> builder.fireImmune().noSummon().sized(4f, 4f));
	public static final RegistrySupplier<EntityType<Missile>> MISSILE = ENTITY_TYPES.register("missile", Missile::new, MobCategory.MISC, builder -> builder.fireImmune().noSummon().sized(0.6f, 0.6f));
	public static final RegistrySupplier<EntityType<Lob>> LOB = ENTITY_TYPES.register("lob", Lob::new, MobCategory.MISC, builder -> builder.fireImmune().noSummon().sized(0.6f, 0.6f));
	public static final RegistrySupplier<EntityType<Smite>> SMITE = ENTITY_TYPES.register("smite", Smite::new, MobCategory.MISC, builder -> builder.fireImmune().noSummon().sized(4f, 4f));
	public static final RegistrySupplier<EntityType<MagicRune>> MAGIC_RUNE = ENTITY_TYPES.register("magic_rune", MagicRune::new, MobCategory.MISC, builder -> builder.fireImmune().noSummon().sized(1f, 0.125f));
	public static final RegistrySupplier<EntityType<GuidedShot>> GUIDED_SHOT = ENTITY_TYPES.register("guided_shot", GuidedShot::new, MobCategory.MISC, builder -> builder.fireImmune().noSummon().sized(0.6f, 0.6f));
	public static final RegistrySupplier<EntityType<AreaOfEffect>> AOE = ENTITY_TYPES.register("area_of_effect", AreaOfEffect::new, MobCategory.MISC, builder -> builder.fireImmune().noSummon().sized(4f, 2.5f));
	public static final RegistrySupplier<EntityType<Beam>> BEAM = ENTITY_TYPES.register("beam", Beam::new, MobCategory.MISC, builder -> builder.fireImmune().noSummon().sized(0.1f, 0.1f));
	public static final RegistrySupplier<EntityType<MagicOrb>> MAGIC_ORB = ENTITY_TYPES.register("magic_orb", MagicOrb::new, MobCategory.MISC, builder -> builder.clientTrackingRange(60).fireImmune().noSummon().sized(0.4f, 0.4f));
	public static final RegistrySupplier<EntityType<StockpileOrb>> STOCKPILE_ORB = ENTITY_TYPES.register("stockpile_orb", StockpileOrb::new, MobCategory.MISC, builder -> builder.clientTrackingRange(60).fireImmune().noSummon().sized(0.4f, 0.4f));
	public static final RegistrySupplier<EntityType<PocketDimensionPortal>> PORTAL = ENTITY_TYPES.register("pocket_dimension_portal", PocketDimensionPortal::new, MobCategory.MISC, builder -> builder.fireImmune().noSummon().sized(1.5f, 0.1f));
	public static final RegistrySupplier<EntityType<TemporalDilationField>> TEMPORAL_DILATION_FIELD = ENTITY_TYPES.register("temporal_dilation_field", TemporalDilationField::new, MobCategory.MISC, builder -> builder.fireImmune().noSummon().sized(9f, 9f));
}
