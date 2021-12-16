package net.lt_schmiddy.arcane_oae.mixin;

import net.minecraft.item.Items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SuspiciousStewItem.class)
public class SuspiciousStewItemMixin extends Item {

    public SuspiciousStewItemMixin(Settings settings) {
        super(settings);
    }

    @Overwrite
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound != null && nbtCompound.contains("Effects", 9)) {
            NbtList nbtList = nbtCompound.getList("Effects", 10);

            for (int i = 0; i < nbtList.size(); ++i) {
                NbtCompound nbtCompound2 = nbtList.getCompound(i);

                int duration = 160;
                if (nbtCompound2.contains("EffectDuration", 3)) {
                    duration = nbtCompound2.getInt("EffectDuration");
                }

                int amplifier = 160;
                if (nbtCompound2.contains("EffectAmplifier", 3)) {
                    amplifier = nbtCompound2.getInt("EffectAmplifier");
                }

                StatusEffect statusEffect = StatusEffect.byRawId(nbtCompound2.getByte("EffectId"));
                if (statusEffect != null) {
                    user.addStatusEffect(new StatusEffectInstance(statusEffect, duration, amplifier));
                }
            }
        }

        if (user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;

            if (!player.getAbilities().creativeMode){
                itemStack.decrement(1);
                player.getInventory().insertStack(new ItemStack(Items.BOWL));
            }
        } else {
            itemStack.decrement(1);
        }
        
        return itemStack;

        // return user instanceof PlayerEntity && ((PlayerEntity) user).getAbilities().creativeMode ? itemStack
        //         : new ItemStack(Items.BOWL);
    }
}
