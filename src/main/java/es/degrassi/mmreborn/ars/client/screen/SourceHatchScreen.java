package es.degrassi.mmreborn.ars.client.screen;

import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.client.container.SourceHatchContainer;
import es.degrassi.mmreborn.ars.client.util.SourceDisplayUtil;
import es.degrassi.mmreborn.ars.client.util.SourceRenderer;
import es.degrassi.mmreborn.ars.common.entity.base.SourceHatchEntity;
import es.degrassi.mmreborn.client.screen.BaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class SourceHatchScreen extends BaseScreen<SourceHatchContainer, SourceHatchEntity> {

  public SourceHatchScreen(SourceHatchContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
    super(pMenu, pPlayerInventory, pTitle);
  }

  @Override
  protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
  }

  @Override
  public ResourceLocation getTexture() {
    return ResourceLocation.fromNamespaceAndPath(ModularMachineryRebornArs.MODID, "textures/gui/guibar.png");
  }

  @Override
  protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
    // render image background:
    super.renderBg(guiGraphics, partialTick, mouseX, mouseY);
    guiGraphics.pose().pushPose();
    float percFilled = ((float) entity.getTank().getSource()) / ((float) entity.getTank().getMaxSource());
    int pxFilled = Mth.ceil(percFilled * 61);
    SourceRenderer.renderSource(
      guiGraphics.pose(),
      pxFilled,
      leftPos + 15,
      topPos + 10 + 61 - pxFilled,
      20,
      pxFilled
    );
//    guiGraphics.blit(getTexture(), leftPos + 15,  topPos + 10 + 61 - pxFilled, 196, 61 - pxFilled, 20, pxFilled);
    guiGraphics.pose().popPose();
  }

  @Override
  protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
    super.renderTooltip(guiGraphics, x, y);

    int offsetX = (this.width - this.getXSize()) / 2;
    int offsetZ = (this.height - this.getYSize()) / 2;

    if(x >= 15 + offsetX && x <= 15 + 18 + offsetX && y >= 10 + offsetZ && y <= 10 + 44 + offsetZ) {
      long currentSource = SourceDisplayUtil.formatSourceForDisplay(entity.getTank().getSource());
      long maxSource = SourceDisplayUtil.formatSourceForDisplay(entity.getTank().getMaxSource());

      Component text = Component.translatable("tooltip.sourcehatch.charge",
          String.valueOf(currentSource),
          String.valueOf(maxSource));

      Font font = Minecraft.getInstance().font;
      guiGraphics.renderTooltip(font, text, x, y);
    }
  }
}
