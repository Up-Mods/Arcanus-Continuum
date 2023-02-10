package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.entities.living.NecroSkeletonEntity;
import dev.cammiescorner.arcanuscontinuum.common.entities.living.OpossumEntity;
import dev.cammiescorner.arcanuscontinuum.common.entities.living.WizardEntity;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.MagicProjectileEntity;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.ManaShieldEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

import java.util.LinkedHashMap;

public class ArcanusEntities {
	//-----Entity Map-----//
	public static final LinkedHashMap<EntityType<?>, Identifier> ENTITIES = new LinkedHashMap<>();

	//-----Entities-----//
	public static final EntityType<WizardEntity> WIZARD = create("wizard", QuiltEntityTypeBuilder.createMob().entityFactory(WizardEntity::new).defaultAttributes(WizardEntity.createAttributes()).setDimensions(EntityDimensions.changing(0.7F, 1.8F)).build());
	public static final EntityType<OpossumEntity> OPOSSUM = create("opossum", QuiltEntityTypeBuilder.createMob().entityFactory(OpossumEntity::new).defaultAttributes(OpossumEntity.createAttributes()).setDimensions(EntityDimensions.changing(0.6F, 0.7F)).build());
	public static final EntityType<NecroSkeletonEntity> NECRO_SKELETON = create("necro_skeleton", QuiltEntityTypeBuilder.createMob().entityFactory(NecroSkeletonEntity::new).defaultAttributes(NecroSkeletonEntity.createAttributes()).setDimensions(EntityDimensions.changing(0.6F, 1.8F)).build());
	public static final EntityType<ManaShieldEntity> MANA_SHIELD = create("mana_shield", QuiltEntityTypeBuilder.create().entityFactory(ManaShieldEntity::new).setDimensions(EntityDimensions.fixed(4F, 4F)).build());
	public static final EntityType<MagicProjectileEntity> MAGIC_PROJECTILE = create("magic_projectile", QuiltEntityTypeBuilder.create().entityFactory(MagicProjectileEntity::new).setDimensions(EntityDimensions.fixed(0.6F, 0.6F)).build());

	//-----Registry-----//
	public static void register() {
		ENTITIES.keySet().forEach(entityType -> Registry.register(Registries.ENTITY_TYPE, ENTITIES.get(entityType), entityType));
	}

	private static <T extends Entity> EntityType<T> create(String name, EntityType<T> type) {
		ENTITIES.put(type, Arcanus.id(name));
		return type;
	}
}
