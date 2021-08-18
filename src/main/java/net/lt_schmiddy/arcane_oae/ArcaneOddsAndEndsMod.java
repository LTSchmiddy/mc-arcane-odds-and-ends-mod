package net.lt_schmiddy.arcane_oae;

import net.fabricmc.api.ModInitializer;
import net.lt_schmiddy.arcane_oae.config.ConfigHandler;

public class ArcaneOddsAndEndsMod implements ModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ConfigHandler.load();
		ConfigHandler.save();

	}
}
