package com.example.nohotbaranim.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
