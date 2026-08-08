package com.example.nohotbaranim.mixin;

import java.util.function.Function;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Skips drawing the small white outline (HOTBAR_SELECTION_TEXTURE) that
 * highlights whichever hotbar slot is currently selected. Everything else
 * about the hotbar (items, background, offhand slot) still renders normally.
 */
@Mixin(InGameHud.class)
public abstract class HotbarSelectionMixin {

	@Shadow
	@Final
	private static Identifier HOTBAR_SELECTION_TEXTURE;

	@Redirect(
		method = "renderHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIII)V"
		)
	)
	private void noHotbarSwapAnim$hideSelectionBox(DrawContext context, Function<Identifier, RenderLayer> renderLayers, Identifier sprite, int x, int y, int width, int height) {
		if (sprite.equals(HOTBAR_SELECTION_TEXTURE)) {
			return;
		}
		context.drawGuiTexture(renderLayers, sprite, x, y, width, height);
	}
}
