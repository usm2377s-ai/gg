package com.example.nohotbaranim;

import net.fabricmc.api.ClientModInitializer;

/**
 * All the actual work happens in the mixin (HeldItemRendererMixin),
 * which forces the item "equip progress" to stay at 1.0 so the
 * hotbar/held-item swap animation never plays. This class just needs
 * to exist as the declared entrypoint in fabric.mod.json.
 */
public class NoHotbarAnimMod implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Nothing to do on startup - the mixin handles everything.
	}
}
