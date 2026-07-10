package com.rainforestkongra.client;

import com.rainforestkongra.registry.ModEntities;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.OcelotEntityModel;
import net.minecraft.client.render.entity.model.ParrotEntityModel;
import net.minecraft.client.render.entity.model.FrogEntityModel;
import net.minecraft.client.render.entity.model.PiglinEntityModel;
import net.minecraft.util.Identifier;

public class RainforestKongraClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.JAGUAR,
                context -> new MobEntityRenderer<>(context,
                        new OcelotEntityModel<>(context.getPart(EntityModelLayers.OCELOT)), 0.5f) {
                    @Override
                    public Identifier getTexture(net.minecraft.entity.mob.MobEntity entity) {
                        return new Identifier("minecraft", "textures/entity/cat/ocelot.png");
                    }
                });

        EntityRendererRegistry.register(ModEntities.TOUCAN,
                context -> new MobEntityRenderer<>(context,
                        new ParrotEntityModel(context.getPart(EntityModelLayers.PARROT)), 0.3f) {
                    @Override
                    public Identifier getTexture(net.minecraft.entity.mob.MobEntity entity) {
                        return new Identifier("minecraft", "textures/entity/parrot/parrot_black.png");
                    }
                });

        EntityRendererRegistry.register(ModEntities.POISON_DART_FROG,
                context -> new MobEntityRenderer<>(context,
                        new FrogEntityModel(context.getPart(EntityModelLayers.FROG)), 0.3f) {
                    @Override
                    public Identifier getTexture(net.minecraft.entity.mob.MobEntity entity) {
                        return new Identifier("minecraft", "textures/entity/frog/green_frog.png");
                    }
                });

        EntityRendererRegistry.register(ModEntities.KONGRA,
                context -> new MobEntityRenderer<>(context,
                        new PiglinEntityModel<>(context.getPart(EntityModelLayers.PIGLIN)), 1.0f) {
                    @Override
                    public Identifier getTexture(net.minecraft.entity.mob.MobEntity entity) {
                        return new Identifier("minecraft", "textures/entity/illager/vindicator.png");
                    }

                    @Override
                    protected void scale(net.minecraft.entity.mob.MobEntity entity,
                                         net.minecraft.client.util.math.MatrixStack matrices, float amount) {
                        matrices.scale(1.7f, 1.7f, 1.7f);
                    }
                });
    }
}