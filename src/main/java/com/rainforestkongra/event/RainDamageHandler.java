package com.rainforestkongra.event;

import com.rainforestkongra.item.KongraArmorMaterial;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RainDamageHandler {
    // Time each player has been continuously exposed to rain (in ticks)
    private static final Map<UUID, Integer> exposure = new HashMap<>();

    // After this many ticks in rain, damage begins (30 seconds).
    private static final int EXPOSURE_THRESHOLD = 600;
    // How often to apply damage once threshold is reached (every 2 seconds).
    private static final int DAMAGE_INTERVAL = 40;

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                tickPlayer(player);
            }
        });
    }

    private static boolean isExposedToRain(ServerPlayerEntity player) {
        ServerWorld world = player.getServerWorld();
        if (!world.isRaining()) {
            return false;
        }
        BlockPos pos = player.getBlockPos();
        // Must be able to see the sky and not be submerged (still gets wet by rain)
        boolean canSeeSky = world.isSkyVisible(pos);
        boolean warmEnough = world.getBiome(pos).value().doesNotSnow(pos);
        return canSeeSky && warmEnough;
    }

    private static int countKongraArmor(ServerPlayerEntity player) {
        int count = 0;
        for (ItemStack stack : player.getInventory().armor) {
            if (stack.getItem() instanceof ArmorItem armor
                    && armor.getMaterial() == KongraArmorMaterial.INSTANCE) {
                count++;
            }
        }
        return count;
    }

    private static void tickPlayer(ServerPlayerEntity player) {
        if (player.isCreative() || player.isSpectator()) {
            exposure.put(player.getUuid(), 0);
            return;
        }

        // Full KONGRA armor grants immunity to the acid rain of the rainforest.
        if (countKongraArmor(player) >= 4) {
            exposure.put(player.getUuid(), 0);
            return;
        }

        if (isExposedToRain(player)) {
            int time = exposure.getOrDefault(player.getUuid(), 0) + 1;
            exposure.put(player.getUuid(), time);

            if (time >= EXPOSURE_THRESHOLD && (time - EXPOSURE_THRESHOLD) % DAMAGE_INTERVAL == 0) {
                player.damage(player.getServerWorld().getDamageSources().magic(), 2.0f);
            }
        } else {
            // Dry off gradually when out of the rain.
            int time = exposure.getOrDefault(player.getUuid(), 0);
            if (time > 0) {
                exposure.put(player.getUuid(), Math.max(0, time - 3));
            }
        }
    }
}