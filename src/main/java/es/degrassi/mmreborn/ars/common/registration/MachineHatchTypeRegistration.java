package es.degrassi.mmreborn.ars.common.registration;

import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.common.machine.MachineHatchType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Locale;
import java.util.function.Supplier;

import static es.degrassi.mmreborn.ModularMachineryReborn.rootLC;

public class MachineHatchTypeRegistration {
  private MachineHatchTypeRegistration() {}

  public static final DeferredRegister<MachineHatchType> MACHINE_COMPONENTS =
      DeferredRegister.create(MachineHatchType.REGISTRY_KEY, ModularMachineryRebornArs.MODID);

  public static final Supplier<MachineHatchType> SOURCE_INPUT_HATCH_TINY =
      MACHINE_COMPONENTS.register(rootLC("SOURCE_INPUT_HATCH_TINY".toLowerCase(Locale.ENGLISH)),
          MachineHatchType::create);
  public static final Supplier<MachineHatchType> SOURCE_INPUT_HATCH_SMALL =
      MACHINE_COMPONENTS.register(rootLC("SOURCE_INPUT_HATCH_SMALL".toLowerCase(Locale.ENGLISH)),
          MachineHatchType::create);
  public static final Supplier<MachineHatchType> SOURCE_INPUT_HATCH_NORMAL =
      MACHINE_COMPONENTS.register(rootLC("SOURCE_INPUT_HATCH_NORMAL".toLowerCase(Locale.ENGLISH)),
          MachineHatchType::create);
  public static final Supplier<MachineHatchType> SOURCE_INPUT_HATCH_REINFORCED =
      MACHINE_COMPONENTS.register(rootLC("SOURCE_INPUT_HATCH_REINFORCED".toLowerCase(Locale.ENGLISH)),
          MachineHatchType::create);
  public static final Supplier<MachineHatchType> SOURCE_INPUT_HATCH_BIG =
      MACHINE_COMPONENTS.register(rootLC("SOURCE_INPUT_HATCH_BIG".toLowerCase(Locale.ENGLISH)),
          MachineHatchType::create);
  public static final Supplier<MachineHatchType> SOURCE_INPUT_HATCH_HUGE =
      MACHINE_COMPONENTS.register(rootLC("SOURCE_INPUT_HATCH_HUGE".toLowerCase(Locale.ENGLISH)),
          MachineHatchType::create);
  public static final Supplier<MachineHatchType> SOURCE_INPUT_HATCH_LUDICROUS =
      MACHINE_COMPONENTS.register(rootLC("SOURCE_INPUT_HATCH_LUDICROUS".toLowerCase(Locale.ENGLISH)),
          MachineHatchType::create);
  public static final Supplier<MachineHatchType> SOURCE_INPUT_HATCH_VACUUM =
      MACHINE_COMPONENTS.register(rootLC("SOURCE_INPUT_HATCH_VACUUM".toLowerCase(Locale.ENGLISH)),
          MachineHatchType::create);

  public static final Supplier<MachineHatchType> SOURCE_OUTPUT_HATCH_TINY =
      MACHINE_COMPONENTS.register(rootLC("SOURCE_OUTPUT_HATCH_TINY".toLowerCase(Locale.ENGLISH)),
          MachineHatchType::create);
  public static final Supplier<MachineHatchType> SOURCE_OUTPUT_HATCH_SMALL =
      MACHINE_COMPONENTS.register(rootLC("SOURCE_OUTPUT_HATCH_SMALL".toLowerCase(Locale.ENGLISH)),
          MachineHatchType::create);
  public static final Supplier<MachineHatchType> SOURCE_OUTPUT_HATCH_NORMAL =
      MACHINE_COMPONENTS.register(rootLC("SOURCE_OUTPUT_HATCH_NORMAL".toLowerCase(Locale.ENGLISH)),
          MachineHatchType::create);
  public static final Supplier<MachineHatchType> SOURCE_OUTPUT_HATCH_REINFORCED =
      MACHINE_COMPONENTS.register(rootLC("SOURCE_OUTPUT_HATCH_REINFORCED".toLowerCase(Locale.ENGLISH)),
          MachineHatchType::create);
  public static final Supplier<MachineHatchType> SOURCE_OUTPUT_HATCH_BIG =
      MACHINE_COMPONENTS.register(rootLC("SOURCE_OUTPUT_HATCH_BIG".toLowerCase(Locale.ENGLISH)),
          MachineHatchType::create);
  public static final Supplier<MachineHatchType> SOURCE_OUTPUT_HATCH_HUGE =
      MACHINE_COMPONENTS.register(rootLC("SOURCE_OUTPUT_HATCH_HUGE".toLowerCase(Locale.ENGLISH)),
          MachineHatchType::create);
  public static final Supplier<MachineHatchType> SOURCE_OUTPUT_HATCH_LUDICROUS =
      MACHINE_COMPONENTS.register(rootLC("SOURCE_OUTPUT_HATCH_LUDICROUS".toLowerCase(Locale.ENGLISH)),
          MachineHatchType::create);
  public static final Supplier<MachineHatchType> SOURCE_OUTPUT_HATCH_VACUUM =
      MACHINE_COMPONENTS.register(rootLC("SOURCE_OUTPUT_HATCH_VACUUM".toLowerCase(Locale.ENGLISH)),
          MachineHatchType::create);

  public static void register(final IEventBus bus) {
    MACHINE_COMPONENTS.register(bus);
  }
}
