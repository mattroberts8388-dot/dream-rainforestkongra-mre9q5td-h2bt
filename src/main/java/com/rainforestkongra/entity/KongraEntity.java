package com.rainforestkongra.entity;

import com.rainforestkongra.item.ModItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class KongraEntity extends HostileEntity {
    private final ServerBossBar bossBar =
            new ServerBossBar(Text.literal("KONGRA"), BossBar.Color.RED, BossBar.Style.PROGRESS);

    public KongraEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 100;
    }

    public static DefaultAttributeContainer.Builder createKongraAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 300.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 18.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.8)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_ARMOR, 12.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.15, true));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.9));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 16.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public boolean tryAttack(net.minecraft.entity.Entity target) {
        boolean hit = super.tryAttack(target);
        if (hit && target instanceof LivingEntity living) {
            // Cobra venom bite
            living.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200, 2));
            living.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, 1));
        }
        return hit;
    }

    private int countKongraArmor(PlayerEntity player) {
        int count = 0;
        for (ItemStack stack : player.getInventory().armor) {
            if (stack.getItem() instanceof ArmorItem armor
                    && armor.getMaterial() == com.rainforestkongra.item.KongraArmorMaterial.INSTANCE) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void tick() {
        super.tick();
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());

        if (!this.getWorld().isClient) {
            // Anyone without a full KONGRA set nearby takes extra punishment
            Box area = this.getBoundingBox().expand(6.0);
            for (PlayerEntity player : this.getWorld().getEntitiesByClass(PlayerEntity.class, area, p -> true)) {
                if (countKongraArmor(player) < 4 && this.age % 40 == 0) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 60, 0));
                }
            }
        }
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        super.dropLoot(source, causedByPlayer);
        this.dropStack(new ItemStack(ModItems.KONGRA_SCALE, 4 + this.random.nextInt(4)));
        this.dropStack(new ItemStack(ModItems.RAINFOREST_ESSENCE, 2 + this.random.nextInt(3)));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_RAVAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_RAVAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_RAVAGER_DEATH;
    }

    @Override
    public boolean isPushable() {
        return false;
    }
}