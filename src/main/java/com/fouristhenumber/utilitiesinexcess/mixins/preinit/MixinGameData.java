package com.fouristhenumber.utilitiesinexcess.mixins.preinit;

import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.fouristhenumber.utilitiesinexcess.compat.exu.ExuCompat;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;

import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.registry.GameData;

// See ExuCompat.java for more
@Mixin(GameData.class)
public class MixinGameData {

    @Definition(id = "defaulted", local = @Local(type = List.class, name = "defaulted"))
    @Definition(id = "isEmpty", method = "Ljava/util/List;isEmpty()Z")
    @Expression("defaulted.isEmpty()")
    @Inject(
        method = "processIdRematches",
        at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.BY, by = -2),
        remap = false)
    private static void uie$processIdRematches(Iterable<FMLMissingMappingsEvent.MissingMapping> missedMappings,
        boolean isLocalWorld, GameData gameData, Map<String, Integer[]> remaps,
        CallbackInfoReturnable<List<String>> cir) {
        ExuCompat.showConfirmationGui();
    }
}
