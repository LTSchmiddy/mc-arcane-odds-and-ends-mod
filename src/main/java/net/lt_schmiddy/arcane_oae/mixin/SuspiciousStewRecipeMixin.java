package net.lt_schmiddy.arcane_oae.mixin;


import net.lt_schmiddy.arcane_oae.stews.StewEffect;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.recipe.SuspiciousStewRecipe;
import net.minecraft.tag.ItemTags;

import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SuspiciousStewRecipe.class)
public class SuspiciousStewRecipeMixin {

    // public static class StewEffect {
    //     public StatusEffect effect = null;
    //     public int duration = 0;
    //     public int amplifier = 0;

    //     public StewEffect() {}
    //     public StewEffect(StatusEffect p_effect, int p_duration, int p_amplifier) {
    //         effect = p_effect;
    //         duration = p_duration;
    //         amplifier = p_amplifier;
    //     }

    //     public NbtCompound toNbt() {
    //         NbtCompound retVal = new NbtCompound();
    //         retVal.putByte("EffectId", (byte)StatusEffect.getRawId(effect));
    //         retVal.putInt("EffectDuration", duration);
    //         retVal.putInt("EffectAmplifier", amplifier);

    //         return retVal;
    //     }
    // }

    @Overwrite
    public boolean matches(CraftingInventory craftingInventory, World world) {
        int bowls = 0;
        int brown_mushrooms = 0;
        int red_mushrooms = 0;
        int flowers = 0;

        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStack(i);
            if (!itemStack.isEmpty()) {
                if (itemStack.isOf(Items.BOWL)) {
                    bowls++;
                } else if (itemStack.isOf(Blocks.BROWN_MUSHROOM.asItem())) {
                    brown_mushrooms++;
                } else if (itemStack.isOf(Blocks.RED_MUSHROOM.asItem())) {
                    red_mushrooms++;
                } else if (itemStack.isIn(ItemTags.SMALL_FLOWERS)) {
                    flowers++;
                } else {
                    return false;
                }
            }
        }

        return (bowls == 1 && brown_mushrooms == 1 && red_mushrooms == 1 && flowers >= 1);
    }
    
    @Overwrite
    public ItemStack craft(CraftingInventory craftingInventory) {
        ItemStack retVal = new ItemStack(Items.SUSPICIOUS_STEW, 1);

        List<StewEffect> effects = new ArrayList<StewEffect>();

        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStack(i);
            if (!itemStack2.isEmpty() && itemStack2.isIn(ItemTags.SMALL_FLOWERS)) {
                ItemStack flower = itemStack2;

                if (flower.getItem() instanceof BlockItem && ((BlockItem) flower.getItem()).getBlock() instanceof FlowerBlock) {
                    FlowerBlock flowerBlock = (FlowerBlock) ((BlockItem) flower.getItem()).getBlock();
                    StatusEffect statusEffect = flowerBlock.getEffectInStew();
                    // SuspiciousStewItem.addEffectToStew(retVal, statusEffect, flowerBlock.getEffectInStewDuration());
                    // effects.add(new StewEffect(statusEffect, flowerBlock.getEffectInStewDuration(), 1));
                    addStewEffect(effects, new StewEffect(statusEffect, flowerBlock.getEffectInStewDuration(), 1));
                }
            }
        }
        addAllEffectsToStew(retVal, effects);
        return retVal;
    }

    private static void addStewEffect(List<StewEffect> effects, StewEffect newEffect) {
        for (StewEffect i : effects) {
            if (StatusEffect.getRawId(i.effect) == StatusEffect.getRawId(newEffect.effect)) {
                i.duration += newEffect.duration;
                i.amplifier += newEffect.amplifier;
                return;
            }
        }

        effects.add(newEffect);

    }

    private static void addAllEffectsToStew(ItemStack stew, List<StewEffect> effects) {
        NbtCompound nbtCompound = stew.getOrCreateNbt();
        NbtList nbtList = nbtCompound.getList("Effects", 9);
        
        for (StewEffect i : effects) {
            nbtList.add(i.toNbt());
        }

        nbtCompound.put("Effects", nbtList);

        System.out.println(nbtList.size());
    }
}
