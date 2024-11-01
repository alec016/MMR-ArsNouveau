package es.degrassi.mmreborn.ars.common.registration;

import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.common.crafting.ComponentType;
import es.degrassi.mmreborn.ars.common.crafting.component.ComponentSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ComponentRegistration {

  public static final DeferredRegister<ComponentType> MACHINE_COMPONENTS = DeferredRegister.create(ComponentType.REGISTRY_KEY, ModularMachineryRebornArs.MODID);

  public static final Supplier<ComponentType> COMPONENT_SOURCE = MACHINE_COMPONENTS.register("source", ComponentSource::new);

  public static void register(final IEventBus bus) {
    MACHINE_COMPONENTS.register(bus);
  }
}
