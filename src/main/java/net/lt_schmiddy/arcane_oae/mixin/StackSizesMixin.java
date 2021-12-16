package net.lt_schmiddy.arcane_oae.mixin;

import net.lt_schmiddy.arcane_oae.config.ConfigHandler;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public abstract class StackSizesMixin {
	@ModifyArg(
		method = "<clinit>",
		at = @At(
			value = "INVOKE", 
			target = "Lnet/minecraft/item/Item$Settings;maxCount(I)Lnet/minecraft/item/Item$Settings;", 
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "NEW", 
				target = "Lnet/minecraft/item/EnderPearlItem;"
			)
		)
	)
	private static int onEnderPearl(int old) {
		return ConfigHandler.getConfig().stackSizes.ender_pearl;
	}

	@ModifyArg(
		method = "<clinit>",
		at = @At(
			value = "INVOKE", 
			target = "Lnet/minecraft/item/Item$Settings;maxCount(I)Lnet/minecraft/item/Item$Settings;", 
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "NEW", 
				target = "Lnet/minecraft/item/SuspiciousStewItem;"
			)
		)
	)
	private static int onSuspiciousStew(int old) {
		return ConfigHandler.getConfig().stackSizes.suspicious_stew;
	}

	@ModifyArg(
		method = "<clinit>",
		at = @At(
			value = "INVOKE", 
			target = "Lnet/minecraft/item/Item$Settings;maxCount(I)Lnet/minecraft/item/Item$Settings;", 
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "NEW", 
				target = "Lnet/minecraft/item/MushroomStewItem;"
			)
		)
	)
	private static int onMushroomStew(int old) {
		return ConfigHandler.getConfig().stackSizes.food_stews;
	}
}