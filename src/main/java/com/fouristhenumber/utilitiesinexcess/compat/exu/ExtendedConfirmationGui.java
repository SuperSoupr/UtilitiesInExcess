package com.fouristhenumber.utilitiesinexcess.compat.exu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiOpenEvent;

import com.fouristhenumber.utilitiesinexcess.mixins.early.minecraft.accessors.AccessorGuiNotification;
import com.gtnewhorizon.gtnhlib.eventbus.EventBusSubscriber;

import cpw.mods.fml.client.GuiConfirmation;
import cpw.mods.fml.common.StartupQuery;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
@EventBusSubscriber()
public class ExtendedConfirmationGui extends GuiConfirmation {

    @SubscribeEvent
    public static void onGuiOpen(GuiOpenEvent event) {
        if (!(event.gui instanceof GuiConfirmation confirmationGui)) return;
        StartupQuery query = ((AccessorGuiNotification) confirmationGui).postea$getQuery();
        if (!query.getText()
            .startsWith(ExuCompat.GUI_PREFIX)) return;
        event.gui = new ExtendedConfirmationGui(query);
    }

    private final List<String> lines;
    private final String warning;

    public ExtendedConfirmationGui(StartupQuery query) {
        super(query);
        lines = new ArrayList<>(
            Arrays.asList(
                query.getText()
                    .substring(ExuCompat.GUI_PREFIX.length())
                    .split("\n")));
        warning = lines.get(0);
    }

    public void initGui() {
        this.buttonList.add(new GuiOptionButton(0, this.width / 2 - 155, this.height - 58, I18n.format("gui.yes")));
        this.buttonList
            .add(new GuiOptionButton(1, this.width / 2 - 155 + 160, this.height - 58, I18n.format("gui.no")));
    }

    private static byte colorTick = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        colorTick++;

        String color = Math.abs(colorTick % 6) >= 3 ? "§4§l" : "§c§l";
        lines.set(0, "§kABC§r " + color + warning + " §r§kCBA");

        int spaceAvailable = this.height - 58 - 20;
        int spaceRequired = Math.min(spaceAvailable, 10 + 10 * lines.size());

        int offset = 10 + (spaceAvailable - spaceRequired) / 2; // vertically centered
        for (String line : lines) {
            if (offset >= spaceAvailable) {
                this.drawCenteredString(this.fontRendererObj, "...", this.width / 2, offset, 0xFFFFFF);
                break;
            } else {
                if (!line.isEmpty())
                    this.drawCenteredString(this.fontRendererObj, line, this.width / 2, offset, 0xFFFFFF);
                offset += 10;
            }
        }

        for (GuiButton button : this.buttonList) {
            button.drawButton(this.mc, mouseX, mouseY);
        }

        for (GuiLabel label : this.labelList) {
            label.func_146159_a(this.mc, mouseX, mouseY);
        }
    }
}
