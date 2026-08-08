package com.example.nohotbaranim.mixin;

import net.minecraft.client.render.item.HeldItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * HeldItemRenderer#updateHeldItems() runs every client tick and lerps
 * equipProgressMainHand / equipProgressOffHand from 0 -> 1 whenever the
 * held item changes (that lerp is what produces the "dip down, come back
 * up" animation you see when switching hotbar slots).
 *
 * By forcing both the current and previous progress values to 1.0F right
 * after that method runs, the renderer always thinks the item is fully
 * "equipped" already, so no animation ever plays - switching slots is instant.
 */
@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

	@Shadow private float equipProgressMainHand;
	@Shadow private float lastEquipProgressMainHand;
	@Shadow private float equipProgressOffHand;
	@Shadow private float lastEquipProgressOffHand;

	@Inject(method = "updateHeldItems", at = @At("TAIL"))
	private void noHotbarSwapAnim$killAnimation(CallbackInfo ci) {
		this.equipProgressMainHand = 1.0F;
		this.lastEquipProgressMainHand = 1.0F;
		this.equipProgressOffHand = 1.0F;
		this.lastEquipProgressOffHand = 1.0F;
	}
}
