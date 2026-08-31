package dev.cammiescorner.arcanus.component.chunk;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.registry.ArcanusComponents;
import dev.upcraft.sparkweave.api.SparkweaveApi;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.util.Util;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import java.util.*;

public class WardedBlocksComponent implements AutoSyncedComponent {
	private final Map<BlockPos, UUID> wardedBlocks = new HashMap<>();
	private final ChunkAccess chunk;

	public WardedBlocksComponent(ChunkAccess chunk) {
		this.chunk = chunk;
	}

	@Override
	public void readData(ValueInput readView) {
		wardedBlocks.clear();

		for(Pair<BlockPos, UUID> pair : readView.read("WardedBlocksMap", Codec.pair(BlockPos.CODEC, UUIDUtil.CODEC).listOf()).orElse(List.of())) {
			// make sure we have the data cached when we need it
			if(SparkweaveApi.CLIENTSIDE_ENVIRONMENT)
				Arcanus.WIZARD_DATA.get(pair.getSecond());

			wardedBlocks.put(pair.getFirst(), pair.getSecond());
		}
	}

	@Override
	public void writeData(ValueOutput writeView) {
		List<Pair<BlockPos, UUID>> pairs = new ArrayList<>();

		wardedBlocks.forEach((blockPos, uuid) -> pairs.add(new Pair<>(blockPos, uuid)));

		writeView.store("WardedBlocksMap", Codec.pair(BlockPos.CODEC, UUIDUtil.CODEC).listOf(), pairs);
	}

	public void addWardedBlock(Player player, BlockPos pos) {
		if(!isBlockWarded(pos)) {
			wardedBlocks.put(pos, player.getUUID());
			chunk.syncComponent(ArcanusComponents.WARDED_BLOCKS_COMPONENT);
			chunk.markUnsaved();
		}
	}

	public void removeWardedBlock(Player player, BlockPos pos) {
		boolean canOtherPlayersRemoveBlock = ArcanusConfig.UtilityEffects.WardingEffectProperties.canBeRemovedByOthers;

		if(canOtherPlayersRemoveBlock || isOwnerOfBlock(player, pos)) {
			wardedBlocks.remove(pos);
			chunk.syncComponent(ArcanusComponents.WARDED_BLOCKS_COMPONENT);
			chunk.markUnsaved();
		}
	}

	public boolean isOwnerOfBlock(Player player, BlockPos pos) {
		return wardedBlocks.getOrDefault(pos, Util.NIL_UUID).equals(player.getUUID());
	}

	public boolean isBlockWarded(BlockPos pos) {
		return wardedBlocks.containsKey(pos);
	}

	public Map<BlockPos, UUID> getWardedBlocks() {
		return Collections.unmodifiableMap(wardedBlocks);
	}
}
