package com.example.nohotbaranim.mixin;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public abstract class HotbarSelectionMixin {

	@Shadow
	@Final
	private static Identifier HOTBAR_SELECTION_TEXTURE;

	@Redirect(
		method = "renderHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V"
		)
	)
	private void noHotbarSwapAnim$hideSelectionBox(DrawContext context, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height) {
		if (sprite.equals(HOTBAR_SELECTION_TEXTURE)) {
			return;
		}
		context.drawGuiTexture(pipeline, sprite, x, y, width, height);
	}
}
