package dev.cammiescorner.arcanuscontinuum.common.components.level;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.blocks.SpatialRiftExitEdgeBlock;
import dev.cammiescorner.arcanuscontinuum.common.blocks.entities.MagicBlockEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusBlocks;
import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import org.quiltmc.qsl.worldgen.dimension.api.QuiltDimensions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class PocketDimensionComponent implements Component {
	public static final RegistryKey<World> POCKET_DIM = RegistryKey.of(RegistryKeys.WORLD, Arcanus.id("pocket_dimension"));
	private final Map<UUID, Box> existingPlots = new HashMap<>();
	private final Random random = new Random();
	private final WorldProperties properties;

	public PocketDimensionComponent(WorldProperties properties) {
		this.properties = properties;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		NbtList nbtList = tag.getList("PlotMap", NbtElement.COMPOUND_TYPE);

		existingPlots.clear();

		for(int i = 0; i < nbtList.size(); i++) {
			NbtCompound entry = nbtList.getCompound(i);
			existingPlots.put(
				entry.getUuid("OwnerUuid"),
				new Box(entry.getInt("MinX"), entry.getInt("MinY"), entry.getInt("MinZ"), entry.getInt("MaxX"), entry.getInt("MaxY"), entry.getInt("MaxZ"))
			);
		}
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		NbtList nbtList = new NbtList();

		existingPlots.forEach((uuid, box) -> {
			NbtCompound entry = new NbtCompound();
			entry.putUuid("OwnerUuid", uuid);
			entry.putInt("MinX", (int) box.minX);
			entry.putInt("MinY", (int) box.minY);
			entry.putInt("MinZ", (int) box.minZ);
			entry.putInt("MaxX", (int) box.maxX);
			entry.putInt("MaxY", (int) box.maxY);
			entry.putInt("MaxZ", (int) box.maxZ);
			nbtList.add(entry);
		});

		tag.put("PlotMap", nbtList);
	}

	public void teleportToPocketDimension(PlayerEntity ownerOfPocket, Entity entity) {
		if(!entity.getWorld().isClient()) {
			if(!existingPlots.containsKey(ownerOfPocket.getUuid()))
				generateNewPlot(ownerOfPocket);

			ServerWorld pocketDim = entity.getServer().getWorld(POCKET_DIM);

			if(pocketDim != null)
				QuiltDimensions.teleport(entity, pocketDim, new TeleportTarget(existingPlots.get(ownerOfPocket.getUuid()).getCenter().subtract(-0.5, 11, -0.5), Vec3d.ZERO, entity.getYaw(), entity.getPitch()));
		}
	}

	private void generateNewPlot(PlayerEntity player) {
		var boxContainer = new Object() {
			Box box = new Box(-13, -13, -13, 12, 12, 12);
		};

		while(existingPlots.entrySet().stream().anyMatch(entry -> entry.getValue().intersects(boxContainer.box)))
			boxContainer.box = boxContainer.box.offset(random.nextInt(-468749, 468750) * 64, random.nextInt(-8, 9) * 64, random.nextInt(-468749, 468750) * 64);

		existingPlots.put(player.getUuid(), boxContainer.box);

		MinecraftServer server = player.getServer();

		if(server != null) {
			ServerWorld pocketDim = server.getWorld(POCKET_DIM);

			if(pocketDim != null) {
				for(BlockPos pos : BlockPos.iterate((int) boxContainer.box.minX, (int) boxContainer.box.minY, (int) boxContainer.box.minZ, (int) boxContainer.box.maxX, (int) boxContainer.box.maxY, (int) boxContainer.box.maxZ)) {
					if(pos.getX() > boxContainer.box.minX && pos.getX() < boxContainer.box.maxX && pos.getY() > boxContainer.box.minY && pos.getY() < boxContainer.box.maxY && pos.getZ() > boxContainer.box.minZ && pos.getZ() < boxContainer.box.maxZ)
						continue;

					pocketDim.setBlockState(pos, ArcanusBlocks.UNBREAKABLE_MAGIC_BLOCK.get().getDefaultState());

					if(pocketDim.getBlockEntity(pos) instanceof MagicBlockEntity magicBlock)
						magicBlock.setColour(Arcanus.getMagicColour(player.getGameProfile().getId()));
				}

				for(int x = 0; x < 4; x++) {
					for(int z = 0; z < 4; z++) {
						BlockPos pos = new BlockPos((int) boxContainer.box.getCenter().getX() + (x - 2), (int) boxContainer.box.minY, (int) boxContainer.box.getCenter().getZ() + (z - 2));

						if(x == 0) {
							switch(z) {
								case 0 -> pocketDim.setBlockState(pos, ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get().getDefaultState().with(SpatialRiftExitEdgeBlock.CORNER, true));
								case 1, 2 -> pocketDim.setBlockState(pos, ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get().getDefaultState().with(SpatialRiftExitEdgeBlock.FACING, Direction.WEST));
								case 3 -> pocketDim.setBlockState(pos, ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get().getDefaultState().with(SpatialRiftExitEdgeBlock.FACING, Direction.WEST).with(SpatialRiftExitEdgeBlock.CORNER, true));
							}
						}
						else if(x == 3) {
							switch(z) {
								case 0 -> pocketDim.setBlockState(pos, ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get().getDefaultState().with(SpatialRiftExitEdgeBlock.FACING, Direction.EAST).with(SpatialRiftExitEdgeBlock.CORNER, true));
								case 1, 2 -> pocketDim.setBlockState(pos, ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get().getDefaultState().with(SpatialRiftExitEdgeBlock.FACING, Direction.EAST));
								case 3 -> pocketDim.setBlockState(pos, ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get().getDefaultState().with(SpatialRiftExitEdgeBlock.FACING, Direction.SOUTH).with(SpatialRiftExitEdgeBlock.CORNER, true));
							}
						}
						else if(z == 0) {
							pocketDim.setBlockState(pos, ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get().getDefaultState().with(SpatialRiftExitEdgeBlock.FACING, Direction.NORTH));
						}
						else if(z == 3) {
							pocketDim.setBlockState(pos, ArcanusBlocks.SPATIAL_RIFT_EXIT_EDGE.get().getDefaultState().with(SpatialRiftExitEdgeBlock.FACING, Direction.SOUTH));
						}
						else {
							pocketDim.setBlockState(pos, Blocks.DEEPSLATE_TILES.getDefaultState());
						}

						if(pocketDim.getBlockEntity(pos) instanceof MagicBlockEntity magicBlock)
							magicBlock.setColour(Arcanus.getMagicColour(player.getGameProfile().getId()));
					}
				}
			}
		}
	}
}
