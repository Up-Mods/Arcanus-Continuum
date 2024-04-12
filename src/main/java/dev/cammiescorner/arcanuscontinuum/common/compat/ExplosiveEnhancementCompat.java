package dev.cammiescorner.arcanuscontinuum.common.compat;

import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.superkat.explosiveenhancement.ExplosiveEnhancement;
import net.superkat.explosiveenhancement.ExplosiveEnhancementClient;
import net.superkat.explosiveenhancement.api.ExplosiveApi;

public class ExplosiveEnhancementCompat {
	public static void spawnEnhancedBooms(World world, double x, double y, double z, float power, boolean didDestroyBlocks) {
		boolean isUnderWater = false;
		BlockPos pos = BlockPos.create(x, y, z);

		if(ExplosiveEnhancementClient.config.underwaterExplosions && world.getFluidState(pos).isIn(FluidTags.WATER)) {
			isUnderWater = true;

			if(ExplosiveEnhancementClient.config.debugLogs)
				ExplosiveEnhancement.LOGGER.info("particle is underwater!");
		}

		ExplosiveApi.spawnParticles(world, x, y, z, power, isUnderWater, didDestroyBlocks);
	}
}
