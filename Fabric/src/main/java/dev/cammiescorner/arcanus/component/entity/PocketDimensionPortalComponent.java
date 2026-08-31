package dev.cammiescorner.arcanus.component.entity;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import dev.cammiescorner.arcanus.Arcanus;
import dev.cammiescorner.arcanus.component.level.PocketDimensionComponent;
import dev.cammiescorner.arcanus.entity.magic.PocketDimensionPortal;
import dev.cammiescorner.arcanus.registry.ArcanusEntities;
import dev.cammiescorner.arcanus.util.ArcanusHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.ladysnake.cca.api.v8.component.CardinalComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PocketDimensionPortalComponent implements CardinalComponent {
	public static final ResourceKey<Level> POCKET_DIMENSION_WORLD_KEY = ResourceKey.create(Registries.DIMENSION, Arcanus.id("pocket_dimension"));
	private final Player player;
	private final Map<ResourceKey<Level>, Pair<UUID, Vec3>> portalIds = new HashMap<>();

	public PocketDimensionPortalComponent(Player player) {
		this.player = player;
	}

	@Override
	public void readData(ValueInput readView) {
		portalIds.clear();

		readView.read("PortalIds", Codec.unboundedMap(Codec.STRING, Codec.pair(UUIDUtil.CODEC, Vec3.CODEC))).orElse(Map.of()).forEach((s, pair) -> {
			portalIds.put(ResourceKey.create(Registries.DIMENSION, Identifier.parse(s)), pair);
		});
	}

	@Override
	public void writeData(ValueOutput writeView) {
		Map<String, Pair<UUID, Vec3>> map = new HashMap<>();

		portalIds.forEach((resourceKey, pair) -> {
			map.put(resourceKey.identifier().toString(), pair);
		});

		writeView.store("PortalIds", Codec.unboundedMap(Codec.STRING, Codec.pair(UUIDUtil.CODEC, Vec3.CODEC)), map);
	}

	public void createPortal(ServerLevel level, Vec3 pos, double pullStrength) {
		MinecraftServer server = level.getServer();

		for(ResourceKey<Level> levelKey : portalIds.keySet()) {
			Pair<UUID, Vec3> pair = portalIds.get(levelKey);
			UUID portalId = pair.getFirst();

			if(portalId != Util.NIL_UUID) {
				ServerLevel otherWorld = server.getLevel(levelKey);

				if(otherWorld != null) {
					BlockPos blockPos = BlockPos.containing(pair.getSecond());

					otherWorld.getChunkSource().addTicketWithRadius(TicketType.PORTAL, SectionPos.of(blockPos).chunk(), 1);

					Entity oldPortal = otherWorld.getEntity(portalId);

					if(oldPortal != null)
						oldPortal.remove(Entity.RemovalReason.DISCARDED);
				}
			}
		}

		PocketDimensionPortal portal = ArcanusEntities.PORTAL.get().create(level, EntitySpawnReason.SPAWN_ITEM_USE);

		if(portal != null) {
			portalIds.put(level.dimension(), new Pair<>(portal.getUUID(), pos));

			if(level.dimension() != POCKET_DIMENSION_WORLD_KEY)
				PocketDimensionComponent.get(level).setExit(player.getGameProfile().id(), level, pos);

			portal.setProperties(player.getUUID(), pos, pullStrength);
			ArcanusHelper.copyMagicColor(portal, player);
			level.addFreshEntity(portal);
		}
	}

	public Vec3 getPortalPos(Level level) {
		return portalIds.get(level.dimension()).getSecond();
	}
}
