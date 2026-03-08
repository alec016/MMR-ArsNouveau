package es.degrassi.mmreborn.ars.client;

import es.degrassi.mmreborn.api.integration.emi.RegisterEmiComponentEvent;
import es.degrassi.mmreborn.api.integration.jei.RegisterJeiComponentEvent;
import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.client.screen.SourceHatchScreen;
import es.degrassi.mmreborn.ars.client.xei.EmiRegistration;
import es.degrassi.mmreborn.ars.client.xei.JeiRegistration;
import es.degrassi.mmreborn.ars.common.crafting.requirement.emi.EmiSourceComponent;
import es.degrassi.mmreborn.ars.common.crafting.requirement.jei.JeiSourceComponent;
import es.degrassi.mmreborn.ars.common.entity.base.SourceHatchEntity;
import es.degrassi.mmreborn.ars.common.registration.BlockRegistration;
import es.degrassi.mmreborn.ars.common.registration.ContainerRegistration;
import es.degrassi.mmreborn.ars.common.registration.ItemRegistration;
import es.degrassi.mmreborn.ars.common.registration.RequirementTypeRegistration;
import es.degrassi.mmreborn.client.ModularMachineryRebornClient;
import es.degrassi.mmreborn.common.util.Mods;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(value = ModularMachineryRebornArs.MODID, dist = Dist.CLIENT)
public class MMRArsClient {
  public MMRArsClient(IEventBus bus) {
    bus.register(this);
    if (Mods.isEMILoaded()) {
      bus.register(new EmiRegistration());
    } else if (Mods.isJEILoaded()) {
      bus.register(new JeiRegistration());
    }
  }

  @SubscribeEvent
  public void registerMenuScreens(final RegisterMenuScreensEvent event) {
    event.register(ContainerRegistration.SOURCE_HATCH.get(), SourceHatchScreen::new);
  }

  @SubscribeEvent
  public void registerBlockColors(final RegisterColorHandlersEvent.Block event) {
    BlockRegistration.BLOCKS.getEntries().forEach(block -> event.register(ModularMachineryRebornClient::blockColor, block.get()));
  }

  @SubscribeEvent
  public void registerItemColors(final RegisterColorHandlersEvent.Item event) {
    ItemRegistration.ITEMS.getEntries().forEach(item -> event.register(ModularMachineryRebornClient::itemColor, item.get()));
  }

  public static SourceHatchEntity getClientSideSourceHatchEntity(BlockPos pos) {
    if (Minecraft.getInstance().level != null) {
      BlockEntity tile = Minecraft.getInstance().level.getBlockEntity(pos);
      if (tile instanceof SourceHatchEntity controller)
        return controller;
    }
    throw new IllegalStateException("Trying to open a Source Hatch container without clicking on a Custom Machine block");
  }
}
