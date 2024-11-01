package es.degrassi.mmreborn.ars.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import org.joml.Matrix4f;

import java.util.Objects;

public class SourceRenderer {
  private static final int TEXTURE_SIZE = 16;

  public static void renderSource(PoseStack poseStack, int sourceHeight, int x, int y, int width, int height) {
    RenderSystem.enableBlend();

    poseStack.pushPose();
    poseStack.translate(x, y, 0);

    TextureAtlasSprite sprite = Objects.requireNonNull(
        Minecraft
            .getInstance()
            .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
            .apply(ResourceLocation.fromNamespaceAndPath("ars_nouveau", "block/source_still"))
    );

    drawTiledSprite(poseStack, width, height, sourceHeight, sprite);

    poseStack.popPose();

    RenderSystem.disableBlend();
  }

  private static void drawTiledSprite(PoseStack poseStack, final int tiledWidth, final int tiledHeight, int scaledAmount, TextureAtlasSprite sprite) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
    Matrix4f matrix = poseStack.last().pose();

    float r = ((0xFFFFFF >> 16) & 0xFF) / 255.0F;
    float g = ((0xFFFFFF >> 8) & 0xFF) / 255.0F;
    float b = ((0xFFFFFF) & 0xFF) / 255.0F;

    RenderSystem.setShaderColor(r, g, b, 1F);

    final int xTileCount = tiledWidth / TEXTURE_SIZE;
    final int xRemainder = tiledWidth - (xTileCount * TEXTURE_SIZE);
    final int yTileCount = scaledAmount / TEXTURE_SIZE;
    final int yRemainder = scaledAmount - (yTileCount * TEXTURE_SIZE);

    for (int xTile = 0; xTile <= xTileCount; xTile++) {
      for (int yTile = 0; yTile <= yTileCount; yTile++) {
        int width = (xTile == xTileCount) ? xRemainder : TEXTURE_SIZE;
        int height = (yTile == yTileCount) ? yRemainder : TEXTURE_SIZE;
        int x = (xTile * TEXTURE_SIZE);
        int y = tiledHeight - ((yTile + 1) * TEXTURE_SIZE);
        if (width > 0 && height > 0) {
          int maskTop = TEXTURE_SIZE - height;
          int maskRight = TEXTURE_SIZE - width;

          drawTextureWithMasking(matrix, x, y, sprite, maskTop, maskRight);
        }
      }
    }

    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
  }

  private static void drawTextureWithMasking(Matrix4f matrix, float xCoord, float yCoord, TextureAtlasSprite textureSprite, int maskTop, int maskRight) {
    float uMin = textureSprite.getU0();
    float uMax = textureSprite.getU1();
    float vMin = textureSprite.getV0();
    float vMax = textureSprite.getV1();
    uMax = uMax - (maskRight / 16F * (uMax - uMin));
    vMax = vMax - (maskTop / 16F * (vMax - vMin));

    float zLevel = 100;

    Tesselator tesselator = Tesselator.getInstance();
    BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
    bufferBuilder.addVertex(matrix, xCoord, yCoord + 16, zLevel).setUv(uMin, vMax);
    bufferBuilder.addVertex(matrix, xCoord + 16 - maskRight, yCoord + 16, zLevel).setUv(uMax, vMax);
    bufferBuilder.addVertex(matrix, xCoord + 16 - maskRight, yCoord + maskTop, zLevel).setUv(uMax, vMin);
    bufferBuilder.addVertex(matrix, xCoord, yCoord + maskTop,zLevel).setUv(uMin, vMin);
    BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
  }
}
