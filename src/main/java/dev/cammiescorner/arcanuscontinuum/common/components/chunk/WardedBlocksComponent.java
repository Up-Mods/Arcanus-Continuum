package dev.cammiescorner.arcanuscontinuum.common.components.chunk;

import dev.cammiescorner.arcanuscontinuum.common.compat.ArcanusConfig;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class WardedBlocksComponent implements AutoSyncedComponent {
	private static final Supplier<Boolean> canOtherPlayersRemoveBlock = ArcanusConfig.getConfigOption(ArcanusConfig.wardingEffectProperties, "canBeRemovedByOthers");
	private final Map<BlockPos, UUID> wardedBlocks = new HashMap<>();
	private final Chunk chunk;

	public WardedBlocksComponent(Chunk chunk) {
		this.chunk = chunk;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		NbtList nbtList = tag.getList("WardedBlocksMap", NbtElement.COMPOUND_TYPE);
		wardedBlocks.clear();

		for(int i = 0; i < nbtList.size(); i++) {
			NbtCompound compound = nbtList.getCompound(i);
			NbtList blockPosList = compound.getList("BlockPosList", NbtElement.COMPOUND_TYPE);
			UUID ownerUuid = compound.getUuid("OwnerUuid");

			for(int j = 0; j < blockPosList.size(); j++)
				wardedBlocks.put(NbtHelper.toBlockPos(blockPosList.getCompound(j)), ownerUuid);
		}
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		NbtList nbtList = new NbtList();
		Map<UUID, NbtList> map = new HashMap<>();

		wardedBlocks.forEach((blockPos, uuid) -> map.computeIfAbsent(uuid, uuid1 -> new NbtList()).add(NbtHelper.fromBlockPos(blockPos)));

		map.forEach((uuid, nbt) -> {
			NbtCompound compound = new NbtCompound();
			compound.putUuid("OwnerUuid", uuid);
			compound.put("BlockPosList", nbt);

			nbtList.add(compound);
		});

		tag.put("WardedBlocksMap", nbtList);
	}

	public void addWardedBlock(PlayerEntity player, BlockPos pos) {
		if(!isBlockWarded(pos)) {
			wardedBlocks.put(pos, player.getUuid());
			chunk.syncComponent(ArcanusComponents.WARDED_BLOCKS_COMPONENT);
			chunk.setNeedsSaving(true);
		}
	}

	public void removeWardedBlock(PlayerEntity player, BlockPos pos) {
		if(canOtherPlayersRemoveBlock.get() || isOwnerOfBlock(player, pos)) {
			wardedBlocks.remove(pos);
			chunk.syncComponent(ArcanusComponents.WARDED_BLOCKS_COMPONENT);
			chunk.setNeedsSaving(true);
		}
	}

	public boolean isOwnerOfBlock(PlayerEntity player, BlockPos pos) {
		return wardedBlocks.getOrDefault(pos, Util.NIL_UUID).equals(player.getUuid());
	}

	public boolean isBlockWarded(BlockPos pos) {
		return wardedBlocks.containsKey(pos);
	}

	public Map<BlockPos, UUID> getWardedBlocks() {
		return Collections.unmodifiableMap(wardedBlocks);
	}
}
