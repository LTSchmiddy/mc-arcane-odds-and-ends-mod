package net.lt_schmiddy.arcane_oae.stews;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.nbt.NbtCompound;


public class StewEffect {
    public StatusEffect effect = null;
    public int duration = 0;
    public int amplifier = 0;

    public StewEffect() {}
    public StewEffect(StatusEffect p_effect, int p_duration, int p_amplifier) {
        effect = p_effect;
        duration = p_duration;
        amplifier = p_amplifier;
    }

    public NbtCompound toNbt() {
        NbtCompound retVal = new NbtCompound();
        retVal.putByte("EffectId", (byte)StatusEffect.getRawId(effect));
        retVal.putInt("EffectDuration", duration);
        retVal.putInt("EffectAmplifier", amplifier);

        return retVal;
    }
}
