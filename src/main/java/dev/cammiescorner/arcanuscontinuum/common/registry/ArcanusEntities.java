package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.entities.OpossumEntity;
import dev.cammiescorner.arcanuscontinuum.common.entities.WizardEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

import java.util.LinkedHashMap;

public class ArcanusEntities {
	//-----Entity Map-----//
	public static final LinkedHashMap<EntityType<?>, Identifier> ENTITIES = new LinkedHashMap<>();

	//-----Entities-----//
	public static final EntityType<WizardEntity> WIZARD = create("wizard", QuiltEntityTypeBuilder.createMob().entityFactory(WizardEntity::new).defaultAttributes(WizardEntity.createAttributes()).setDimensions(EntityDimensions.changing(0.7F, 1.8F)).build());
	public static final EntityType<OpossumEntity> OPOSSUM = create("opossum", QuiltEntityTypeBuilder.createMob().entityFactory(OpossumEntity::new).defaultAttributes(OpossumEntity.createAttributes()).setDimensions(EntityDimensions.changing(0.6F, 0.7F)).build());

	//-----Registry-----//
	public static void register() {
		ENTITIES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITIES.get(entityType), entityType));
	}

	private static <T extends Entity> EntityType<T> create(String name, EntityType<T> type) {
		ENTITIES.put(type, Arcanus.id(name));
		return type;
	}
}
