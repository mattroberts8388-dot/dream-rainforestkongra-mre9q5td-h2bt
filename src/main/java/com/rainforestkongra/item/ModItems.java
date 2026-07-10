package com.rainforestkongra.item;

import com.rainforestkongra.RainforestKongraMod;
import com.rainforestkongra.registry.ModEntities;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {
    public static Item KONGRA_SCALE;
    public static Item RAINFOREST_ESSENCE;

    public static Item KONGRA_HELMET;
    public static Item KONGRA_CHESTPLATE;
    public static Item KONGRA_LEGGINGS;
    public static Item KONGRA_BOOTS;

    public static Item JAGUAR_SPAWN_EGG;
    public static Item TOUCAN_SPAWN_EGG;
    public static Item POISON_DART_FROG_SPAWN_EGG;
    public static Item KONGRA_SPAWN_EGG;

    public static ItemGroup GENERAL_GROUP;

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(RainforestKongraMod.MOD_ID, name), item);
    }

    public static void register() {
        KONGRA_SCALE = register("kongra_scale", new Item(new FabricItemSettings().maxCount(64)));
        RAINFOREST_ESSENCE = register("rainforest_essence", new Item(new FabricItemSettings().maxCount(64)));

        KONGRA_HELMET = register("kongra_helmet",
                new KongraArmorItem(KongraArmorMaterial.INSTANCE, ArmorItem.Type.HELMET, new FabricItemSettings()));
        KONGRA_CHESTPLATE = register("kongra_chestplate",
                new KongraArmorItem(KongraArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
        KONGRA_LEGGINGS = register("kongra_leggings",
                new KongraArmorItem(KongraArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
        KONGRA_BOOTS = register("kongra_boots",
                new KongraArmorItem(KongraArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS, new FabricItemSettings()));

        JAGUAR_SPAWN_EGG = register("jaguar_spawn_egg",
                new SpawnEggItem(ModEntities.JAGUAR, 0x3a2a10, 0xe0b040, new FabricItemSettings()));
        TOUCAN_SPAWN_EGG = register("toucan_spawn_egg",
                new SpawnEggItem(ModEntities.TOUCAN, 0x101010, 0xff8000, new FabricItemSettings()));
        POISON_DART_FROG_SPAWN_EGG = register("poison_dart_frog_spawn_egg",
                new SpawnEggItem(ModEntities.POISON_DART_FROG, 0x1050ff, 0x20ff20, new FabricItemSettings()));
        KONGRA_SPAWN_EGG = register("kongra_spawn_egg",
                new SpawnEggItem(ModEntities.KONGRA, 0x203020, 0x50a020, new FabricItemSettings()));

        GENERAL_GROUP = Registry.register(Registries.ITEM_GROUP,
                new Identifier(RainforestKongraMod.MOD_ID, "general"),
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(KONGRA_SCALE))
                        .displayName(Text.translatable("itemGroup.rainforestkongra.general"))
                        .entries((displayContext, entries) -> {
                            entries.add(KONGRA_SCALE);
                            entries.add(RAINFOREST_ESSENCE);
                            entries.add(KONGRA_HELMET);
                            entries.add(KONGRA_CHESTPLATE);
                            entries.add(KONGRA_LEGGINGS);
                            entries.add(KONGRA_BOOTS);
                            entries.add(JAGUAR_SPAWN_EGG);
                            entries.add(TOUCAN_SPAWN_EGG);
                            entries.add(POISON_DART_FROG_SPAWN_EGG);
                            entries.add(KONGRA_SPAWN_EGG);
                        })
                        .build());
    }
}