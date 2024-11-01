package es.degrassi.mmreborn.ars.common.registration;

import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.common.entity.SourceInputHatchEntity;
import es.degrassi.mmreborn.ars.common.entity.SourceOutputHatchEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Supplier;

public class EntityRegistration {
  public static final DeferredRegister<BlockEntityType<?>> ENTITY_TYPE = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ModularMachineryRebornArs.MODID);

  public static final Supplier<BlockEntityType<SourceInputHatchEntity>> SOURCE_INPUT_HATCH = ENTITY_TYPE.register(
      "source_hatch_input",
      () -> new BlockEntityType<>(
          SourceInputHatchEntity::new,
          Set.of(
              BlockRegistration.SOURCE_INPUT_HATCH_TINY.get(),
              BlockRegistration.SOURCE_INPUT_HATCH_SMALL.get(),
              BlockRegistration.SOURCE_INPUT_HATCH_NORMAL.get(),
              BlockRegistration.SOURCE_INPUT_HATCH_REINFORCED.get(),
              BlockRegistration.SOURCE_INPUT_HATCH_BIG.get(),
              BlockRegistration.SOURCE_INPUT_HATCH_HUGE.get(),
              BlockRegistration.SOURCE_INPUT_HATCH_LUDICROUS.get(),
              BlockRegistration.SOURCE_INPUT_HATCH_VACUUM.get()
          ),
          null)
  );
  public static final Supplier<BlockEntityType<SourceOutputHatchEntity>> SOURCE_OUTPUT_HATCH = ENTITY_TYPE.register(
      "source_hatch_output",
      () -> new BlockEntityType<>(
          SourceOutputHatchEntity::new,
          Set.of(
              BlockRegistration.SOURCE_OUTPUT_HATCH_TINY.get(),
              BlockRegistration.SOURCE_OUTPUT_HATCH_SMALL.get(),
              BlockRegistration.SOURCE_OUTPUT_HATCH_NORMAL.get(),
              BlockRegistration.SOURCE_OUTPUT_HATCH_REINFORCED.get(),
              BlockRegistration.SOURCE_OUTPUT_HATCH_BIG.get(),
              BlockRegistration.SOURCE_OUTPUT_HATCH_HUGE.get(),
              BlockRegistration.SOURCE_OUTPUT_HATCH_LUDICROUS.get(),
              BlockRegistration.SOURCE_OUTPUT_HATCH_VACUUM.get()
          ),
          null)
  );
  public static void register(final IEventBus bus) {
    ENTITY_TYPE.register(bus);
  }
}
