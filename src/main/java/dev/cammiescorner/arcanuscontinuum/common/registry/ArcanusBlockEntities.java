package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.blocks.entities.ArcaneWorkbenchBlockEntity;
import dev.cammiescorner.arcanuscontinuum.common.blocks.entities.MagicBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public class ArcanusBlockEntities {
	//-----Block Entity Type Map-----//
	private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();

	//-----Block Entity Types-----//
	public static final BlockEntityType<ArcaneWorkbenchBlockEntity> ARCANE_WORKBENCH = create("arcane_workbench", QuiltBlockEntityTypeBuilder.create(ArcaneWorkbenchBlockEntity::new, ArcanusBlocks.ARCANE_WORKBENCH).build());
	public static final BlockEntityType<MagicBlockEntity> MAGIC_BLOCK = create("magic_block", QuiltBlockEntityTypeBuilder.create(MagicBlockEntity::new, ArcanusBlocks.MAGIC_BLOCK).build());

	//-----Registry-----//
	public static void register() {
		BLOCK_ENTITY_TYPES.keySet().forEach(blockEntityType -> Registry.register(Registry.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(blockEntityType), blockEntityType));
	}

	private static <T extends BlockEntity> BlockEntityType<T> create(String name, BlockEntityType<T> type) {
		BLOCK_ENTITY_TYPES.put(type, Arcanus.id(name));
		return type;
	}
}
