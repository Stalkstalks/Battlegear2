package mods.battlegear2.mixins.early;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mods.battlegear2.client.utils.BattlegearRenderHelper;

@Mixin(ModelBiped.class)
public class MixinModelBiped {

    @Inject(
            method = "setRotationAngles",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/model/ModelBiped;isSneak:Z",
                    shift = At.Shift.BEFORE))
    private void battlegear2$moveOffHandArm(float f1, float f2, float f3, float f4, float f5, float f6, Entity entity,
            CallbackInfo ci) {
        BattlegearRenderHelper.moveOffHandArm(entity, (ModelBiped) (Object) this, f6);
    }

}
