package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.entities.living.NecroSkeletonEntity;
import dev.cammiescorner.arcanuscontinuum.common.entities.living.OpossumEntity;
import dev.cammiescorner.arcanuscontinuum.common.entities.living.WizardEntity;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.*;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

public class ArcanusEntities {
	public static final RegistryHandler<EntityType<?>> ENTITY_TYPES = RegistryHandler.create(RegistryKeys.ENTITY_TYPE, Arcanus.MOD_ID);

	public static final RegistrySupplier<EntityType<WizardEntity>> WIZARD = ENTITY_TYPES.register("wizard", () -> QuiltEntityTypeBuilder.createMob().entityFactory(WizardEntity::new).defaultAttributes(WizardEntity.createAttributes()).setDimensions(EntityDimensions.changing(0.7F, 1.8F)).build());
	public static final RegistrySupplier<EntityType<OpossumEntity>> OPOSSUM = ENTITY_TYPES.register("opossum", () -> QuiltEntityTypeBuilder.createMob().entityFactory(OpossumEntity::new).defaultAttributes(OpossumEntity.createAttributes()).setDimensions(EntityDimensions.changing(0.6F, 0.7F)).build());
	public static final RegistrySupplier<EntityType<NecroSkeletonEntity>> NECRO_SKELETON = ENTITY_TYPES.register("necro_skeleton", () -> QuiltEntityTypeBuilder.createMob().entityFactory(NecroSkeletonEntity::new).disableSummon().defaultAttributes(NecroSkeletonEntity.createAttributes()).setDimensions(EntityDimensions.changing(0.6F, 1.8F)).build());
	public static final RegistrySupplier<EntityType<ManaShieldEntity>> MANA_SHIELD = ENTITY_TYPES.register("mana_shield", () -> QuiltEntityTypeBuilder.create().entityFactory(ManaShieldEntity::new).disableSummon().setDimensions(EntityDimensions.fixed(4F, 4F)).build());
	public static final RegistrySupplier<EntityType<MagicProjectileEntity>> MAGIC_PROJECTILE = ENTITY_TYPES.register("magic_projectile", () -> QuiltEntityTypeBuilder.create().entityFactory(MagicProjectileEntity::new).disableSummon().setDimensions(EntityDimensions.fixed(0.6F, 0.6F)).build());
	public static final RegistrySupplier<EntityType<SmiteEntity>> SMITE = ENTITY_TYPES.register("smite", () -> QuiltEntityTypeBuilder.create().entityFactory(SmiteEntity::new).disableSummon().setDimensions(EntityDimensions.fixed(4F, 4F)).build());
	public static final RegistrySupplier<EntityType<MagicRuneEntity>> MAGIC_RUNE = ENTITY_TYPES.register("magic_rune", () -> QuiltEntityTypeBuilder.create().entityFactory(MagicRuneEntity::new).disableSummon().setDimensions(EntityDimensions.fixed(1F, 0.125F)).build());
	public static final RegistrySupplier<EntityType<AreaOfEffectEntity>> AOE = ENTITY_TYPES.register("area_of_effect", () -> QuiltEntityTypeBuilder.create().entityFactory(AreaOfEffectEntity::new).disableSummon().setDimensions(EntityDimensions.fixed(4F, 2.5F)).build());
	public static final RegistrySupplier<EntityType<BeamEntity>> BEAM = ENTITY_TYPES.register("beam", () -> QuiltEntityTypeBuilder.create().entityFactory(BeamEntity::new).disableSummon().setDimensions(EntityDimensions.fixed(0.1F, 0.1F)).build());
	public static final RegistrySupplier<EntityType<GuardianOrbEntity>> GUARDIAN_ORB = ENTITY_TYPES.register("guardian_orb", () -> QuiltEntityTypeBuilder.create().entityFactory(GuardianOrbEntity::new).trackingTickInterval(60).disableSummon().setDimensions(EntityDimensions.fixed(0.4F, 0.4F)).build());
	public static final RegistrySupplier<EntityType<PocketDimensionPortalEntity>> PORTAL = ENTITY_TYPES.register("pocket_dimension_portal", () -> QuiltEntityTypeBuilder.create().entityFactory(PocketDimensionPortalEntity::new).disableSummon().setDimensions(EntityDimensions.fixed(2f, 0.1f)).build());
}
