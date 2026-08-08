package com.example.nohotbaranim.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Cancels the little action-bar popup text whenever it mentions "light
 * update" (the message Sodium Extra shows when its "Light Updates" debug
 * option is toggled on/off). This hooks Minecraft's own general-purpose
 * overlay-message system rather than depending on Sodium Extra's internal
 * classes directly, so it keeps working even if Sodium Extra changes
 * internally, and it also catches any other mod that reuses this same
 * vanilla message system to say the same thing.
 */
@Mixin(InGameHud.class)
public abstract class HideLightUpdatesMessageMixin {

	@Inject(method = "setOverlayMessage", at = @At("HEAD"), cancellable = true)
	private void noHotbarSwapAnim$hideLightUpdatesMessage(Text message, boolean tinted, CallbackInfo ci) {
		String text = message.getString();
		if (text != null && text.toLowerCase().contains("light update")) {
			ci.cancel();
		}
	}
}
