package dev.cammiescorner.arcanus.client.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.HumanoidArm;

@Environment(EnvType.CLIENT)
public enum MirrorHudElement {
	NEVER, ALWAYS, IF_LEFT_HANDED;

	public boolean mirror() {
		if(this == NEVER)
			return false;
		if(this == ALWAYS)
			return true;

		return Minecraft.getInstance().options.mainHand().get() == HumanoidArm.LEFT;
	}
}
