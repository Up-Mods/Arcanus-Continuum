package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.entities.OpossumEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;

public class ArcanusEntities {
	//-----Entity Map-----//
	public static final LinkedHashMap<EntityType<?>, Identifier> ENTITIES = new LinkedHashMap<>();

	//-----Entities-----//
	public static final EntityType<OpossumEntity> OPOSSUM = create("opossum", OpossumEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, OpossumEntity::new).dimensions(EntityDimensions.changing(0.6F, 0.7F)).build());

	//-----Registry-----//
	public static void register() {
		ENTITIES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITIES.get(entityType), entityType));
	}

	private static <T extends LivingEntity> EntityType<T> create(String name, DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
		FabricDefaultAttributeRegistry.register(type, attributes);
		return create(name, type);
	}

	private static <T extends Entity> EntityType<T> create(String name, EntityType<T> type) {
		ENTITIES.put(type, Arcanus.id(name));
		return type;
	}
}
