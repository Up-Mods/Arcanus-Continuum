package dev.cammiescorner.arcanus.entrypoints;

import com.mojang.authlib.GameProfile;
import commonnetwork.api.Network;
import dev.cammiescorner.arcanus.ArcanusConfig;
import dev.cammiescorner.arcanus.common.block.MagicDoorBlock;
import dev.cammiescorner.arcanus.common.block.entities.MagicDoorBlockEntity;
import dev.cammiescorner.arcanus.common.entity.living.Arcanist;
import dev.cammiescorner.arcanus.common.entity.living.Cultist;
import dev.cammiescorner.arcanus.common.entity.living.NecroSkeleton;
import dev.cammiescorner.arcanus.common.entity.living.Opossum;
import dev.cammiescorner.arcanus.common.networking.clientbound.ClientboundEnforceConfigPacket;
import dev.cammiescorner.arcanus.common.networking.clientbound.ClientboundStatusEffectPacket;
import dev.cammiescorner.arcanus.common.registry.*;
import dev.cammiescorner.arcanus.common.util.TranslationKeys;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class FabricMain implements ModInitializer {
	@Override
	public void onInitialize() {
		FabricDefaultAttributeRegistry.register(ArcanusEntities.ARCANIST.get(), Arcanist.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ArcanusEntities.CULTIST_CLERIC.get(), Cultist.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ArcanusEntities.CULTIST_KNIGHT.get(), Cultist.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ArcanusEntities.OPOSSUM.get(), Opossum.createMobAttributes());
		FabricDefaultAttributeRegistry.register(ArcanusEntities.NECRO_SKELETON.get(), NecroSkeleton.createAttributes());

		CommandRegistrationCallback.EVENT.register(ArcanusCommands::init);

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			GameProfile hostProfile = server.getSingleplayerProfile();

			if(hostProfile == null || !hostProfile.getId().equals(handler.player.getGameProfile().getId()))
				Network.getNetworkHandler().sendToClient(new ClientboundEnforceConfigPacket(ArcanusConfig.castingSpeedHasCoolDown), handler.player);

			Network.getNetworkHandler().sendToClients(new ClientboundStatusEffectPacket(handler.player.getId(), ArcanusMobEffects.ANONYMITY.holder(), handler.player.hasEffect(ArcanusMobEffects.ANONYMITY.holder())), List.copyOf(PlayerLookup.tracking(handler.player)));
		});

		EntityTrackingEvents.START_TRACKING.register((trackedEntity, player) -> {
			if(trackedEntity instanceof ServerPlayer playerEntity)
				Network.getNetworkHandler().sendToClient(new ClientboundStatusEffectPacket(playerEntity.getId(), ArcanusMobEffects.ANONYMITY.holder(), playerEntity.hasEffect(ArcanusMobEffects.ANONYMITY.holder())), player);
		});

		EntitySleepEvents.STOP_SLEEPING.register((entity, sleepingPos) -> {
			if(!entity.level().isClientSide() && entity.level().getDayTime() == 24000)
				ArcanusArcana.primalArcana().forEach(primalArcana -> ArcanusComponents.setArcana(entity, primalArcana, primalArcana.getMaxArcana(entity)));
		});

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			ItemStack stack = player.getItemInHand(hand);
			BlockPos pos = hitResult.getBlockPos();
			BlockState state = world.getBlockState(pos);

			if(!world.isClientSide() && player.isShiftKeyDown() && stack.is(Items.NAME_TAG) && stack.has(DataComponents.CUSTOM_NAME)) {
				MagicDoorBlockEntity door = MagicDoorBlock.getBlockEntity(world, state, pos);

				if(door != null && door.getOwner() == player) {
					door.setPassword(stack.getHoverName().getString());

					if(!player.isCreative())
						stack.shrink(1);

					return InteractionResult.SUCCESS;
				}
			}

			if(ArcanusComponents.isBlockWarded(world, pos) && !ArcanusComponents.isOwnerOfBlock(player, pos)) {
				UseOnContext ctx = new BlockPlaceContext(world, player, hand, stack, hitResult);
				InteractionResult result = stack.useOn(ctx);

				if(!result.consumesAction()) {
					player.displayClientMessage(Component.translatable(TranslationKeys.BLOCK_IS_WARDED).withStyle(ChatFormatting.RED), true);
					player.swing(hand);

					return InteractionResult.FAIL;
				}

				return result;
			}

			return InteractionResult.PASS;
		});
	}
}
