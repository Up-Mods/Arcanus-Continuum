package dev.cammiescorner.arcanuscontinuum.client.renderer.entity.magic;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.MagicProjectileEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class MagicProjectileEntityRenderer extends ProjectileEntityRenderer<MagicProjectileEntity> {
	public MagicProjectileEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
	}

	@Override
	public Identifier getTexture(MagicProjectileEntity entity) {
		return Arcanus.id("textures/entity/projectile/magic_projectile.png");
	}
}
