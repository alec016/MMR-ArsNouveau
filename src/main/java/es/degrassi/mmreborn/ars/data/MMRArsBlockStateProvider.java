package es.degrassi.mmreborn.ars.data;

import es.degrassi.mmreborn.ModularMachineryReborn;
import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.common.block.prop.SourceHatchSize;
import es.degrassi.mmreborn.ars.common.registration.BlockRegistration;
import es.degrassi.mmreborn.data.BaseMMRBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MMRArsBlockStateProvider extends BaseMMRBlockStateProvider {
  protected MMRArsBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, ModularMachineryRebornArs.MODID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    addHatch(BlockRegistration.SOURCE_INPUT_HATCH_TINY.get(), false, source(true, SourceHatchSize.TINY), false);
    addHatch(BlockRegistration.SOURCE_INPUT_HATCH_SMALL.get(), false, source(true, SourceHatchSize.SMALL), false);
    addHatch(BlockRegistration.SOURCE_INPUT_HATCH_NORMAL.get(), false, source(true, SourceHatchSize.NORMAL), false);
    addHatch(BlockRegistration.SOURCE_INPUT_HATCH_REINFORCED.get(), true, source(true, SourceHatchSize.REINFORCED), false);
    addHatch(BlockRegistration.SOURCE_INPUT_HATCH_BIG.get(), true, source(true, SourceHatchSize.BIG), false);
    addHatch(BlockRegistration.SOURCE_INPUT_HATCH_HUGE.get(), true, source(true, SourceHatchSize.HUGE), false);
    addHatch(BlockRegistration.SOURCE_INPUT_HATCH_LUDICROUS.get(), true, source(true, SourceHatchSize.LUDICROUS), false);
    addHatch(BlockRegistration.SOURCE_INPUT_HATCH_VACUUM.get(), true, source(true, SourceHatchSize.VACUUM), false);

    addHatch(BlockRegistration.SOURCE_OUTPUT_HATCH_TINY.get(), false, source(false, SourceHatchSize.TINY), false);
    addHatch(BlockRegistration.SOURCE_OUTPUT_HATCH_SMALL.get(), false, source(false, SourceHatchSize.SMALL), false);
    addHatch(BlockRegistration.SOURCE_OUTPUT_HATCH_NORMAL.get(), false, source(false, SourceHatchSize.NORMAL), false);
    addHatch(BlockRegistration.SOURCE_OUTPUT_HATCH_REINFORCED.get(), true, source(false, SourceHatchSize.REINFORCED), false);
    addHatch(BlockRegistration.SOURCE_OUTPUT_HATCH_BIG.get(), true, source(false, SourceHatchSize.BIG), false);
    addHatch(BlockRegistration.SOURCE_OUTPUT_HATCH_HUGE.get(), true, source(false, SourceHatchSize.HUGE), false);
    addHatch(BlockRegistration.SOURCE_OUTPUT_HATCH_LUDICROUS.get(), true, source(false, SourceHatchSize.LUDICROUS), false);
    addHatch(BlockRegistration.SOURCE_OUTPUT_HATCH_VACUUM.get(), true, source(false, SourceHatchSize.VACUUM), false);
  }

  @Override
  public ResourceLocation modLoc(String name) {
    return ModularMachineryReborn.rl(name);
  }

  public ResourceLocation ml(String name) {
    return ModularMachineryRebornArs.rl(name);
  }

  private ResourceLocation source(boolean input, SourceHatchSize size) {
    return ml("block/overlay_source" + (input ? "in" : "out") + "puthatch_" + size.getSerializedName());
  }
}
