package dev.cammiescorner.arcanuscontinuum.client.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class CollapseParticle extends SpriteBillboardParticle {
	private final SpriteProvider spriteProvider;

	public CollapseParticle(ClientWorld world, double posX, double posY, double posZ, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
		super(world, posX, posY, posZ, 0, 0, 0);
		this.maxAge = 60;
		this.spriteProvider = spriteProvider;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.velocityZ = velocityZ;
	}

	@Override
	public void tick() {
		setSpriteForAge(spriteProvider);
		super.tick();
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
	}

	@ClientOnly
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		@Override
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double posX, double posY, double posZ, double velocityX, double velocityY, double velocityZ) {
			CollapseParticle particle = new CollapseParticle(clientWorld, posX, posY, posZ, velocityX, velocityY, velocityZ, spriteProvider);
			particle.setSpriteForAge(spriteProvider);
			return particle;
		}
	}
}
