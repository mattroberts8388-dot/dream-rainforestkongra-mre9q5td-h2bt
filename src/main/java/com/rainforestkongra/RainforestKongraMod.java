package com.rainforestkongra;

import com.rainforestkongra.entity.JaguarEntity;
import com.rainforestkongra.entity.KongraEntity;
import com.rainforestkongra.entity.PoisonDartFrogEntity;
import com.rainforestkongra.entity.ToucanEntity;
import com.rainforestkongra.item.ModItems;
import com.rainforestkongra.registry.ModEntities;
import com.rainforestkongra.event.RainDamageHandler;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.util.Identifier;

public class RainforestKongraMod implements ModInitializer {
    public static final String MOD_ID = "rainforestkongra";

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ModItems.register();
        ModEntities.register();

        FabricDefaultAttributeRegistry.register(ModEntities.JAGUAR, JaguarEntity.createJaguarAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.TOUCAN, ToucanEntity.createToucanAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.POISON_DART_FROG, PoisonDartFrogEntity.createFrogAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.KONGRA, KongraEntity.createKongraAttributes());

        RainDamageHandler.register();
    }
}