package com.fouristhenumber.utilitiesinexcess.compat.exu;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.common.StartupQuery;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class ExuCompat {

    private static boolean hasEXUItem = false;

    public static void onMissingMappings(FMLMissingMappingsEvent event) {
        for (FMLMissingMappingsEvent.MissingMapping mapping : event.getAll()) {
            if (mapping == null) continue;

            if (Remappings.skippedMappings.contains(mapping.name)) {
                mapping.ignore();
                hasEXUItem = true;
                continue;
            }

            if (mapping.type == GameRegistry.Type.ITEM) {
                Item newItem = Remappings.itemMappings.getOrDefault(mapping.name, null);
                if (newItem != null) {
                    mapping.remap(newItem);
                    hasEXUItem = true;
                }
            } else { // BLOCK
                Block newBlock = Remappings.blockMappings.getOrDefault(mapping.name, null);
                if (newBlock != null) {
                    mapping.remap(newBlock);
                    hasEXUItem = true;
                }
            }
        }
    }

    // For the extended confirmation gui we call the same startup query page that FML uses,
    // but replace the gui using a GuiOpenEvent.
    // Both showConfirmationGui and ExtendedConfirmationGui::onGuiOpen right after FMLMissingMappingsEvent events
    // and right before FML tries to show the missing mapping confirmation gui
    public static final String GUI_PREFIX = "UTILITIES IN EXCESS:\n";

    public static void showConfirmationGui() {
        if (!hasEXUItem) return;

        String txt = GUI_PREFIX + StatCollector.translateToLocal("uie.world_conversion.warning")
            .replace("\\n", "\n");
        boolean confirmed = StartupQuery.confirm(txt);
        if (!confirmed) StartupQuery.abort();
    }

}
