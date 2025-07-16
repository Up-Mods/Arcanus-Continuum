package dev.cammiescorner.arcanus.common.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.HumanoidArm;

public enum MirrorHudElement {
	NEVER, ALWAYS, IF_LEFT_HANDED;

	@Environment(EnvType.CLIENT)
	public boolean mirror() {
		if(this == NEVER)
			return false;
		if(this == ALWAYS)
			return true;

		return Minecraft.getInstance().options.mainHand().get() == HumanoidArm.LEFT;
	}
}
