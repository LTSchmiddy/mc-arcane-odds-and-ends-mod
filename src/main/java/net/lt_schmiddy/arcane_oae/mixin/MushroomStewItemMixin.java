package net.lt_schmiddy.arcane_oae.mixin;

import net.minecraft.item.Items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.item.MushroomStewItem;

import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MushroomStewItem.class)
public class MushroomStewItemMixin extends Item {

    public MushroomStewItemMixin(Settings settings) {
        super(settings);
    }

    @Overwrite
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);

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
