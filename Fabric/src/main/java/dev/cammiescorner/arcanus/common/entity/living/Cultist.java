package dev.cammiescorner.arcanus.common.entity.living;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class Cultist extends Mob {
	@Environment(EnvType.CLIENT) private PlayerInfo playerInfo;

	public Cultist(EntityType<? extends Mob> entityType, Level level) {
		super(entityType, level);
		setLeftHanded(getRandom().nextInt() == 0);
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.ATTACK_DAMAGE, 4).add(Attributes.MOVEMENT_SPEED, 0.5);
	}

	@Environment(EnvType.CLIENT)
	public PlayerInfo getPlayerInfo() {
		if(playerInfo == null)
			playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(getUUID());

		return playerInfo;
	}

	@Environment(EnvType.CLIENT)
	public PlayerSkin getSkin() {
		PlayerInfo playerInfo = getPlayerInfo();
		return playerInfo == null ? DefaultPlayerSkin.get(getUUID()) : playerInfo.getSkin();
	}
}
