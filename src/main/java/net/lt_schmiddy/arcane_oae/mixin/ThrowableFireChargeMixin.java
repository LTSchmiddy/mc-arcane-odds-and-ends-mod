package net.lt_schmiddy.arcane_oae.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(FireChargeItem.class)
public class ThrowableFireChargeMixin extends Item{

    public ThrowableFireChargeMixin(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack itemStack = user.getStackInHand(hand);
        // user.setCurrentHand(hand);
        // double e = 4.0D;
        Vec3d vec3d = user.getRotationVec(1.0F);
        


        SmallFireballEntity fireballEntity = new SmallFireballEntity(world, user, vec3d.x, vec3d.y, vec3d.z);
        fireballEntity.setProperties(user, user.getPitch(), user.getYaw(), 0, 3, 1);
        fireballEntity.setPosition(user.getX() + vec3d.x * 1.0D, user.getBodyY(0.25D) + 0.25D, user.getZ() + vec3d.z * 1.0D);
        world.spawnEntity(fireballEntity);

        itemStack.decrement(1);
        return TypedActionResult.success(itemStack);


     
    }
}
