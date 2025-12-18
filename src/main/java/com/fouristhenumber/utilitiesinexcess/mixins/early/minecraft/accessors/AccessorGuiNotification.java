package com.fouristhenumber.utilitiesinexcess.mixins.early.minecraft.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import cpw.mods.fml.client.GuiNotification;
import cpw.mods.fml.common.StartupQuery;

@Mixin(GuiNotification.class)
public interface AccessorGuiNotification {

    @Accessor(value = "query", remap = false)
    StartupQuery postea$getQuery();
}
