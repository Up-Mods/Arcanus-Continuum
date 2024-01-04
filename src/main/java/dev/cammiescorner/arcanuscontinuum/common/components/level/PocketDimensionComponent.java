package dev.cammiescorner.arcanuscontinuum.common.components.level;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.blocks.SpatialRiftExitBlock;
import dev.cammiescorner.arcanuscontinuum.common.blocks.SpatialRiftExitEdgeBlock;
import dev.cammiescorner.arcanuscontinuum.common.blocks.entities.MagicBlockEntity;
import dev.cammiescorner.arcanuscontinuum.common.blocks.entities.SpatialRiftExitBlockEntity;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusBlocks;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import org.quiltmc.qsl.worldgen.dimension.api.QuiltDimensions;

import java.util.*;

public class PocketDimensionComponent implements Component {
	public static final RegistryKey<World> POCKET_DIM = RegistryKey.of(RegistryKeys.WORLD, Arcanus.id("pocket_dimension"));
	private final Map<UUID, Box> existingPlots = new HashMap<>();
	private final Map<UUID, Pair<RegistryKey<World>, Vec3d>> exitSpot = new HashMap<>();
	private final Random random = new Random();
	private final WorldProperties properties;

	public PocketDimensionComponent(WorldProperties properties) {
		this.properties = properties;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		NbtList plotNbtList = tag.getList("PlotMap", NbtElement.COMPOUND_TYPE);
		NbtList exitNbtList = tag.getList("ExitSpots", NbtElement.COMPOUND_TYPE);

		existingPlots.clear();
		exitSpot.clear();

		for(int i = 0; i < plotNbtList.size(); i++) {
			NbtCompound entry = plotNbtList.getCompound(i);
			existingPlots.put(
				entry.getUuid("OwnerUuid"),
				new Box(entry.getInt("MinX"), entry.getInt("MinY"), entry.getInt("MinZ"), entry.getInt("MaxX"), entry.getInt("MaxY"), entry.getInt("MaxZ"))
			);
		}

		for(int i = 0; i < exitNbtList.size(); i++) {
			NbtCompound entry = exitNbtList.getCompound(i);
			exitSpot.put(
					entry.getUuid("EntityId"),
					new Pair<>(
							RegistryKey.of(RegistryKeys.WORLD, new Identifier(entry.getString("WorldKey"))),
					new Vec3d(entry.getDouble("X"), entry.getDouble("Y"), entry.getDouble("Z"))
					)
			);
		}
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		NbtList plotNbtList = new NbtList();
		NbtList exitNbtList = new NbtList();

		existingPlots.forEach((uuid, box) -> {
			NbtCompound entry = new NbtCompound();
			entry.putUuid("OwnerUuid", uuid);
			entry.putInt("MinX", (int) box.minX);
			entry.putInt("MinY", (int) box.minY);
			entry.putInt("MinZ", (int) box.minZ);
			entry.putInt("MaxX", (int) box.maxX);
			entry.putInt("MaxY", (int) box.maxY);
			entry.putInt("MaxZ", (int) box.maxZ);
			plotNbtList.add(entry);
		});

		exitSpot.forEach((uuid, pair) -> {
			NbtCompound entry = new NbtCompound();
			entry.putUuid("EntityId", uuid);
			entry.putString("WorldKey", pair.getLeft().getValue().toString());
			entry.putDouble("X", pair.getRight().getX());
			entry.putDouble("Y", pair.getRight().getY());
			entry.putDouble("Z", pair.getRight().getZ());
			exitNbtList.add(entry);
		});

		tag.put("PlotMap", plotNbtList);
		tag.put("ExitSpots", exitNbtList);
	}

	public void teleportToPocketDimension(PlayerEntity ownerOfPocket, Entity entity) {
		if(!entity.getWorld().isClient()) {
			if(!existingPlots.containsKey(ownerOfPocket.getUuid()))
				generateNewPlot(ownerOfPocket);

			ServerWorld pocketDim = entity.getServer().getWorld(POCKET_DIM);

			if(pocketDim != null)
				QuiltDimensions.teleport(entity, pocketDim, new TeleportTarget(existingPlots.get(ownerOfPocket.getUuid()).getCenter().subtract(0, 11, 0), Vec3d.ZERO, entity.getYaw(), entity.getPitch()));
		}
	}

	public void teleportOutOfPocketDimension(ServerPlayerEntity player) {
		if(!player.getWorld().isClient() && player.getWorld().getRegistryKey() == POCKET_DIM) {
			Optional<Map.Entry<UUID, Box>> entry = existingPlots.entrySet().stream().filter(entry1 -> entry1.getValue().intersects(player.getBoundingBox())).findFirst();

			if(entry.isPresent()) {
				UUID ownerId = entry.get().getKey();
				Pair<RegistryKey<World>, Vec3d> pair = exitSpot.get(ownerId);

				if(pair == null)
					pair = new Pair<>(World.OVERWORLD, Vec3d.ofBottomCenter(player.getServer().getOverworld().getSpawnPos()));

				ArcanusComponents.setPortalCoolDown(player, 200);
				QuiltDimensions.teleport(player, player.getServer().getWorld(pair.getLeft()), new TeleportTarget(pair.getRight(), Vec3d.ZERO, player.getYaw(), player.getPitch()));
			}
		}
	}

	public void generateNewPlot(PlayerEntity player) {
		var boxContainer = new Object() {
			Box box = new Box(-13, -13, -13, 13, 13, 13);
		};

		while(existingPlots.entrySet().stream().anyMatch(entry -> entry.getValue().intersects(boxContainer.box.expand(38))))
			boxContainer.box = boxContainer.box.offset(random.nextInt(-468748, 468749) * 64, random.nextInt(-3, 4) * 64, random.nextInt(-468748, 468749) * 64);

		existingPlots.put(player.getUuid(), boxContainer.box);

		MinecraftServer server = player.getServer();

		if(server != null) {
			ServerWorld pocketDim = server.getWorld(POCKET_DIM);

			if(pocketDim != null) {
				for(BlockPos pos : BlockPos.iterate((int) Math.round(boxContainer.box.minX), (int) Math.round(boxContainer.box.minY), (int) Math.round(boxContainer.box.minZ), (int) Math.round(boxContainer.box.maxX - 1), (int) Math.round(boxContainer.box.maxY - 1), (int) Math.round(boxContainer.box.maxZ - 1))) {
					if(pos.getX() > boxContainer.box.minX && pos.getX() < boxContainer.box.maxX - 1 && pos.getY() > boxContainer.box.minY && pos.getY() < boxContainer.box.maxY - 1 && pos.getZ() > boxContainer.box.minZ && pos.getZ() < boxContainer.box.maxZ - 1)
						continue;

					pocketDim.setBlockState(pos, ArcanusBlocks.UNBREAKABLE_MAGIC_BLOCK.get().getDefaultState());

					if(pocketDim.getBlockEntity(pos) instanceof MagicBlockEntity magicBlock)
						magicBlock.setColour(Arcanus.getMagicColour(player.getGameProfile().getId()));
				}

				for(int x = 0; x < 4; x++) {
					for(int z = 0; z < 4; z++) {
						BlockPos pos = new BlockPos((int) Math.round(boxContainer.box.getCenter().getX()) + (x - 2), (int) Math.round(boxContainer.box.minY), (int) Math.round(boxContainer.box.getCenter().getZ()) + (z - 2));

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
							pocketDim.setBlockState(pos, ArcanusBlocks.SPATIAL_RIFT_EXIT.get().getDefaultState().with(SpatialRiftExitBlock.ACTIVE, x == 1 && z == 1));

							if(pocketDim.getBlockEntity(pos) instanceof SpatialRiftExitBlockEntity exitBlockEntity)
								exitBlockEntity.setColour(Arcanus.getMagicColour(player.getGameProfile().getId()));
						}

						if(pocketDim.getBlockEntity(pos) instanceof MagicBlockEntity magicBlock)
							magicBlock.setColour(Arcanus.getMagicColour(player.getGameProfile().getId()));
					}
				}
			}
		}
	}

	public void setExit(PlayerEntity ownerOfPocket, World world, Vec3d pos) {
		exitSpot.put(ownerOfPocket.getUuid(), new Pair<>(world.getRegistryKey(), pos));
	}
}
