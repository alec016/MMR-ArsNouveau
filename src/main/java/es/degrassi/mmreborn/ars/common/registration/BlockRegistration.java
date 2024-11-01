package es.degrassi.mmreborn.ars.common.registration;

import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.common.block.BlockSourceInputHatch;
import es.degrassi.mmreborn.ars.common.block.BlockSourceOutputHatch;
import es.degrassi.mmreborn.ars.common.block.prop.SourceHatchSize;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistration {
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(ModularMachineryRebornArs.MODID);

  public static final DeferredHolder<Block, BlockSourceInputHatch> SOURCE_INPUT_HATCH_TINY = BLOCKS.register("sourceinputhatch_" + SourceHatchSize.TINY.getSerializedName(),
      () -> new BlockSourceInputHatch(SourceHatchSize.TINY));
  public static final DeferredHolder<Block, BlockSourceInputHatch> SOURCE_INPUT_HATCH_SMALL = BLOCKS.register("sourceinputhatch_" + SourceHatchSize.SMALL.getSerializedName(),
      () -> new BlockSourceInputHatch(SourceHatchSize.SMALL));
  public static final DeferredHolder<Block, BlockSourceInputHatch> SOURCE_INPUT_HATCH_NORMAL = BLOCKS.register("sourceinputhatch_" + SourceHatchSize.NORMAL.getSerializedName(),
      () -> new BlockSourceInputHatch(SourceHatchSize.NORMAL));
  public static final DeferredHolder<Block, BlockSourceInputHatch> SOURCE_INPUT_HATCH_REINFORCED = BLOCKS.register("sourceinputhatch_" + SourceHatchSize.REINFORCED.getSerializedName(),
      () -> new BlockSourceInputHatch(SourceHatchSize.REINFORCED));
  public static final DeferredHolder<Block, BlockSourceInputHatch> SOURCE_INPUT_HATCH_BIG = BLOCKS.register("sourceinputhatch_" + SourceHatchSize.BIG.getSerializedName(),
      () -> new BlockSourceInputHatch(SourceHatchSize.BIG));
  public static final DeferredHolder<Block, BlockSourceInputHatch> SOURCE_INPUT_HATCH_HUGE = BLOCKS.register("sourceinputhatch_" + SourceHatchSize.HUGE.getSerializedName(),
      () -> new BlockSourceInputHatch(SourceHatchSize.HUGE));
  public static final DeferredHolder<Block, BlockSourceInputHatch> SOURCE_INPUT_HATCH_LUDICROUS = BLOCKS.register("sourceinputhatch_" + SourceHatchSize.LUDICROUS.getSerializedName(),
      () -> new BlockSourceInputHatch(SourceHatchSize.LUDICROUS));
  public static final DeferredHolder<Block, BlockSourceInputHatch> SOURCE_INPUT_HATCH_VACUUM = BLOCKS.register("sourceinputhatch_" + SourceHatchSize.VACUUM.getSerializedName(),
      () -> new BlockSourceInputHatch(SourceHatchSize.VACUUM));

  public static final DeferredHolder<Block, BlockSourceOutputHatch> SOURCE_OUTPUT_HATCH_TINY = BLOCKS.register("sourceoutputhatch_" + SourceHatchSize.TINY.getSerializedName(),
      () -> new BlockSourceOutputHatch(SourceHatchSize.TINY));
  public static final DeferredHolder<Block, BlockSourceOutputHatch> SOURCE_OUTPUT_HATCH_SMALL = BLOCKS.register("sourceoutputhatch_" + SourceHatchSize.SMALL.getSerializedName(),
      () -> new BlockSourceOutputHatch(SourceHatchSize.SMALL));
  public static final DeferredHolder<Block, BlockSourceOutputHatch> SOURCE_OUTPUT_HATCH_NORMAL = BLOCKS.register("sourceoutputhatch_" + SourceHatchSize.NORMAL.getSerializedName(),
      () -> new BlockSourceOutputHatch(SourceHatchSize.NORMAL));
  public static final DeferredHolder<Block, BlockSourceOutputHatch> SOURCE_OUTPUT_HATCH_REINFORCED = BLOCKS.register("sourceoutputhatch_" + SourceHatchSize.REINFORCED.getSerializedName(),
      () -> new BlockSourceOutputHatch(SourceHatchSize.REINFORCED));
  public static final DeferredHolder<Block, BlockSourceOutputHatch> SOURCE_OUTPUT_HATCH_BIG = BLOCKS.register("sourceoutputhatch_" + SourceHatchSize.BIG.getSerializedName(),
      () -> new BlockSourceOutputHatch(SourceHatchSize.BIG));
  public static final DeferredHolder<Block, BlockSourceOutputHatch> SOURCE_OUTPUT_HATCH_HUGE = BLOCKS.register("sourceoutputhatch_" + SourceHatchSize.HUGE.getSerializedName(),
      () -> new BlockSourceOutputHatch(SourceHatchSize.HUGE));
  public static final DeferredHolder<Block, BlockSourceOutputHatch> SOURCE_OUTPUT_HATCH_LUDICROUS = BLOCKS.register("sourceoutputhatch_" + SourceHatchSize.LUDICROUS.getSerializedName(),
      () -> new BlockSourceOutputHatch(SourceHatchSize.LUDICROUS));
  public static final DeferredHolder<Block, BlockSourceOutputHatch> SOURCE_OUTPUT_HATCH_VACUUM = BLOCKS.register("sourceoutputhatch_" + SourceHatchSize.VACUUM.getSerializedName(),
      () -> new BlockSourceOutputHatch(SourceHatchSize.VACUUM));

  public static void register(final IEventBus bus) {
    BLOCKS.register(bus);
  }
}
