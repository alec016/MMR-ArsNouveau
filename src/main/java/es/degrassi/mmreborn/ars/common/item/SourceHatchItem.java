package es.degrassi.mmreborn.ars.common.item;

import es.degrassi.mmreborn.ModularMachineryReborn;
import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.common.block.prop.SourceHatchSize;
import es.degrassi.mmreborn.ars.common.block.BlockSourceHatch;
import es.degrassi.mmreborn.common.item.ItemBlockMachineComponent;
import es.degrassi.mmreborn.common.item.ItemHatch;
import es.degrassi.mmreborn.common.registration.DataComponentRegistration;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;

@Getter
public class SourceHatchItem extends ItemBlockMachineComponent implements ItemHatch {
  private static final ResourceLocation BASE_TEXTURE = ModularMachineryReborn.rl("block/casing_plain");
  private final SourceHatchSize type;

  public SourceHatchItem(BlockSourceHatch block, SourceHatchSize type, boolean input) {
    super(
        block,
        new Properties()
            .component(DataComponentRegistration.BASE_TEXTURE, BASE_TEXTURE)
            .component(DataComponentRegistration.OVERLAY_TEXTURE, ModularMachineryRebornArs.rl("block/overlay_source" + (input ? "in" : "out") + "puthatch_" + type.getSerializedName()))
            .component(DataComponentRegistration.DEFAULT_MODEL, ModularMachineryRebornArs.rl("default/hatches/source" + (input ? "in" : "out") + "puthatch_" + type.getSerializedName()))
    );
    this.type = type;
  }

  @Override
  public ResourceLocation getDefaultBaseTexture() {
    return BASE_TEXTURE;
  }

  @Override
  public ResourceLocation getDefaultOverlayTexture() {
    return ModularMachineryRebornArs.rl("block/overlay_sourceinputhatch_" + type.getSerializedName());
  }
}
