package es.degrassi.mmreborn.ars.client.requirement;

import es.degrassi.mmreborn.ars.client.util.SourceRenderer;
import net.minecraft.client.gui.GuiGraphics;

public interface SourceRendering {
  default void renderSource(GuiGraphics guiGraphics, int width, int height, int xOffset, int yOffset) {
    SourceRenderer.renderSource(guiGraphics.pose(), height, xOffset, yOffset, width, height);
  }

  default void renderSource(GuiGraphics guiGraphics, int width, int height) {
    renderSource(guiGraphics, width, height, 0, 0);
  }
}
