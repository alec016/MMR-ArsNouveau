package es.degrassi.mmreborn.ars.common.registration;

import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.client.container.SourceHatchContainer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ContainerRegistration {
  public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(BuiltInRegistries.MENU, ModularMachineryRebornArs.MODID);
  public static final DeferredHolder<MenuType<?>, MenuType<SourceHatchContainer>> SOURCE_HATCH = CONTAINERS.register("source_hatch", () -> IMenuTypeExtension.create(SourceHatchContainer::new));
  public static void register(IEventBus bus) {
    CONTAINERS.register(bus);
  }
}
