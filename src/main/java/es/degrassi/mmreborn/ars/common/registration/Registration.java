package es.degrassi.mmreborn.ars.common.registration;

import net.neoforged.bus.api.IEventBus;

public class Registration {
  public static void register(final IEventBus bus) {
    ComponentRegistration.register(bus);
    CreativeTabsRegistration.register(bus);
    RequirementTypeRegistration.register(bus);
    BlockRegistration.register(bus);
    ItemRegistration.register(bus);
    EntityRegistration.register(bus);
    ContainerRegistration.register(bus);
  }
}
