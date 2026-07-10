package com.rainforestkongra.registry;

import com.rainforestkongra.RainforestKongraMod;
import com.rainforestkongra.entity.JaguarEntity;
import com.rainforestkongra.entity.KongraEntity;
import com.rainforestkongra.entity.PoisonDartFrogEntity;
import com.rainforestkongra.entity.ToucanEntity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static EntityType<JaguarEntity> JAGUAR;
    public static EntityType<ToucanEntity> TOUCAN;
    public static EntityType<PoisonDartFrogEntity> POISON_DART_FROG;
    public static EntityType<KongraEntity> KONGRA;

    public static void register() {
        JAGUAR = Registry.register(Registries.ENTITY_TYPE, new Identifier(RainforestKongraMod.MOD_ID, "jaguar"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, JaguarEntity::new)
                        .dimensions(EntityDimensions.fixed(0.9f, 0.9f)).build());

        TOUCAN = Registry.register(Registries.ENTITY_TYPE, new Identifier(RainforestKongraMod.MOD_ID, "toucan"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ToucanEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5f, 0.6f)).build());

        POISON_DART_FROG = Registry.register(Registries.ENTITY_TYPE, new Identifier(RainforestKongraMod.MOD_ID, "poison_dart_frog"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PoisonDartFrogEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());

        KONGRA = Registry.register(Registries.ENTITY_TYPE, new Identifier(RainforestKongraMod.MOD_ID, "kongra"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, KongraEntity::new)
                        .dimensions(EntityDimensions.fixed(1.4f, 3.2f)).build());
    }
}