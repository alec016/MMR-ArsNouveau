package es.degrassi.mmreborn.ars.common.registration;

import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.common.block.prop.SourceHatchSize;
import es.degrassi.mmreborn.ars.common.item.SourceHatchItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistration {
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(ModularMachineryRebornArs.MODID);

  public static final DeferredHolder<Item, SourceHatchItem> SOURCE_INPUT_HATCH_TINY = ITEMS.register("sourceinputhatch_" + SourceHatchSize.TINY.getSerializedName(),
      () -> new SourceHatchItem(BlockRegistration.SOURCE_INPUT_HATCH_TINY.get(), SourceHatchSize.TINY));
  public static final DeferredHolder<Item, SourceHatchItem> SOURCE_INPUT_HATCH_SMALL = ITEMS.register("sourceinputhatch_" + SourceHatchSize.SMALL.getSerializedName(),
      () -> new SourceHatchItem(BlockRegistration.SOURCE_INPUT_HATCH_SMALL.get(), SourceHatchSize.SMALL));
  public static final DeferredHolder<Item, SourceHatchItem> SOURCE_INPUT_HATCH_NORMAL = ITEMS.register("sourceinputhatch_" + SourceHatchSize.NORMAL.getSerializedName(),
      () -> new SourceHatchItem(BlockRegistration.SOURCE_INPUT_HATCH_NORMAL.get(), SourceHatchSize.NORMAL));
  public static final DeferredHolder<Item, SourceHatchItem> SOURCE_INPUT_HATCH_REINFORCED = ITEMS.register("sourceinputhatch_" + SourceHatchSize.REINFORCED.getSerializedName(),
      () -> new SourceHatchItem(BlockRegistration.SOURCE_INPUT_HATCH_REINFORCED.get(), SourceHatchSize.REINFORCED));
  public static final DeferredHolder<Item, SourceHatchItem> SOURCE_INPUT_HATCH_BIG = ITEMS.register("sourceinputhatch_" + SourceHatchSize.BIG.getSerializedName(),
      () -> new SourceHatchItem(BlockRegistration.SOURCE_INPUT_HATCH_BIG.get(), SourceHatchSize.BIG));
  public static final DeferredHolder<Item, SourceHatchItem> SOURCE_INPUT_HATCH_HUGE = ITEMS.register("sourceinputhatch_" + SourceHatchSize.HUGE.getSerializedName(),
      () -> new SourceHatchItem(BlockRegistration.SOURCE_INPUT_HATCH_HUGE.get(), SourceHatchSize.HUGE));
  public static final DeferredHolder<Item, SourceHatchItem> SOURCE_INPUT_HATCH_LUDICROUS = ITEMS.register("sourceinputhatch_" + SourceHatchSize.LUDICROUS.getSerializedName(),
      () -> new SourceHatchItem(BlockRegistration.SOURCE_INPUT_HATCH_LUDICROUS.get(), SourceHatchSize.LUDICROUS));
  public static final DeferredHolder<Item, SourceHatchItem> SOURCE_INPUT_HATCH_VACUUM = ITEMS.register("sourceinputhatch_" + SourceHatchSize.VACUUM.getSerializedName(),
      () -> new SourceHatchItem(BlockRegistration.SOURCE_INPUT_HATCH_VACUUM.get(), SourceHatchSize.VACUUM));

  public static final DeferredHolder<Item, SourceHatchItem> SOURCE_OUTPUT_HATCH_TINY = ITEMS.register("sourceoutputhatch_" + SourceHatchSize.TINY.getSerializedName(),
      () -> new SourceHatchItem(BlockRegistration.SOURCE_OUTPUT_HATCH_TINY.get(), SourceHatchSize.TINY));
  public static final DeferredHolder<Item, SourceHatchItem> SOURCE_OUTPUT_HATCH_SMALL = ITEMS.register("sourceoutputhatch_" + SourceHatchSize.SMALL.getSerializedName(),
      () -> new SourceHatchItem(BlockRegistration.SOURCE_OUTPUT_HATCH_SMALL.get(), SourceHatchSize.SMALL));
  public static final DeferredHolder<Item, SourceHatchItem> SOURCE_OUTPUT_HATCH_NORMAL = ITEMS.register("sourceoutputhatch_" + SourceHatchSize.NORMAL.getSerializedName(),
      () -> new SourceHatchItem(BlockRegistration.SOURCE_OUTPUT_HATCH_NORMAL.get(), SourceHatchSize.NORMAL));
  public static final DeferredHolder<Item, SourceHatchItem> SOURCE_OUTPUT_HATCH_REINFORCED = ITEMS.register("sourceoutputhatch_" + SourceHatchSize.REINFORCED.getSerializedName(),
      () -> new SourceHatchItem(BlockRegistration.SOURCE_OUTPUT_HATCH_REINFORCED.get(), SourceHatchSize.REINFORCED));
  public static final DeferredHolder<Item, SourceHatchItem> SOURCE_OUTPUT_HATCH_BIG = ITEMS.register("sourceoutputhatch_" + SourceHatchSize.BIG.getSerializedName(),
      () -> new SourceHatchItem(BlockRegistration.SOURCE_OUTPUT_HATCH_BIG.get(), SourceHatchSize.BIG));
  public static final DeferredHolder<Item, SourceHatchItem> SOURCE_OUTPUT_HATCH_HUGE = ITEMS.register("sourceoutputhatch_" + SourceHatchSize.HUGE.getSerializedName(),
      () -> new SourceHatchItem(BlockRegistration.SOURCE_OUTPUT_HATCH_HUGE.get(), SourceHatchSize.HUGE));
  public static final DeferredHolder<Item, SourceHatchItem> SOURCE_OUTPUT_HATCH_LUDICROUS = ITEMS.register("sourceoutputhatch_" + SourceHatchSize.LUDICROUS.getSerializedName(),
      () -> new SourceHatchItem(BlockRegistration.SOURCE_OUTPUT_HATCH_LUDICROUS.get(), SourceHatchSize.LUDICROUS));
  public static final DeferredHolder<Item, SourceHatchItem> SOURCE_OUTPUT_HATCH_VACUUM = ITEMS.register("sourceoutputhatch_" + SourceHatchSize.VACUUM.getSerializedName(),
      () -> new SourceHatchItem(BlockRegistration.SOURCE_OUTPUT_HATCH_VACUUM.get(), SourceHatchSize.VACUUM));
  
  public static void register(final IEventBus bus) {
    ITEMS.register(bus);
  }
}
