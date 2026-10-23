package com.theecho.client;

import com.theecho.entity.EchoEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.texture.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.UUID;

/** Renderiza al Eco como un jugador con la skin del jugador al que imita. */
public class EchoEntityRenderer extends LivingEntityRenderer<EchoEntity, PlayerEntityModel<EchoEntity>> {

    public EchoEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PlayerEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER), false), 0.5f);
        this.addFeature(new ArmorFeatureRenderer<>(this,
                new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)),
                new BipedEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)),
                ctx.getModelManager()));
        this.addFeature(new HeldItemFeatureRenderer<>(this, ctx.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(EchoEntity entity) {
        UUID id = entity.getOwnerUuid();
        if (id == null) return DefaultSkinHelper.getSkinTextures(entity.getUuid()).texture();
        if (MinecraftClient.getInstance().getNetworkHandler() != null) {
            PlayerListEntry e = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(id);
            if (e != null) return e.getSkinTextures().texture();
        }
        return DefaultSkinHelper.getSkinTextures(id).texture();
    }

    @Override
    protected void scale(EchoEntity entity, MatrixStack matrices, float amount) {
        matrices.scale(0.9375f, 0.9375f, 0.9375f);
    }

    /** El nombre solo se ve de cerca: de lejos es solo "otro jugador". */
    @Override
    protected boolean hasLabel(EchoEntity entity) {
        return super.hasLabel(entity) && this.dispatcher.getSquaredDistanceToCamera(entity) < 18 * 18;
    }
}
