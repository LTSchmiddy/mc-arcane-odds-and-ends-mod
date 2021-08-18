package net.lt_schmiddy.arcane_oae.mixin;


import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public abstract class StackSizesMixin {
	@ModifyArg(method = "<clinit>",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item$Settings;maxCount(I)Lnet/minecraft/item/Item$Settings;", ordinal = 0),
			slice = @Slice( from = @At(value = "NEW", target = "Lnet/minecraft/item/EnderPearlItem;")))
	private static int onEnderPearl(int old) {
		return 64;
	}
}