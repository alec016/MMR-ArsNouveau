package es.degrassi.mmreborn.ars.client;

import es.degrassi.mmreborn.api.integration.emi.RegisterEmiComponentEvent;
import es.degrassi.mmreborn.api.integration.jei.RegisterJeiComponentEvent;
import es.degrassi.mmreborn.ars.client.screen.SourceHatchScreen;
import es.degrassi.mmreborn.ars.common.crafting.requirement.emi.EmiSourceComponent;
import es.degrassi.mmreborn.ars.common.crafting.requirement.jei.JeiSourceComponent;
import es.degrassi.mmreborn.ars.common.entity.base.SourceHatchEntity;
import es.degrassi.mmreborn.ars.common.registration.BlockRegistration;
import es.degrassi.mmreborn.ars.common.registration.ContainerRegistration;
import es.degrassi.mmreborn.ars.common.registration.ItemRegistration;
import es.degrassi.mmreborn.ars.common.registration.RequirementTypeRegistration;
import es.degrassi.mmreborn.client.ModularMachineryRebornClient;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class MMRArsClient {
  public static SourceHatchEntity getClientSideSourceHatchEntity(BlockPos pos) {
    if (Minecraft.getInstance().level != null) {
      BlockEntity tile = Minecraft.getInstance().level.getBlockEntity(pos);
      if (tile instanceof SourceHatchEntity controller)
        return controller;
    }
    throw new IllegalStateException("Trying to open a Source Hatch container without clicking on a Custom Machine block");
  }

  @SubscribeEvent
  public void registerJeiComponents(final RegisterJeiComponentEvent event) {
    event.register(RequirementTypeRegistration.SOURCE.get(), JeiSourceComponent::new);
  }

  @SubscribeEvent
  public void registerEmiComponents(final RegisterEmiComponentEvent event) {
    event.register(RequirementTypeRegistration.SOURCE.get(), EmiSourceComponent::new);
  }

  @SubscribeEvent
  public void registerMenuScreens(final RegisterMenuScreensEvent event) {
    event.register(ContainerRegistration.SOURCE_HATCH.get(), SourceHatchScreen::new);
  }

  @SubscribeEvent
  public void registerBlockColors(final RegisterColorHandlersEvent.Block event) {
    event.register(
        ModularMachineryRebornClient::blockColor,

        BlockRegistration.SOURCE_INPUT_HATCH_TINY.get(),
        BlockRegistration.SOURCE_INPUT_HATCH_SMALL.get(),
        BlockRegistration.SOURCE_INPUT_HATCH_NORMAL.get(),
        BlockRegistration.SOURCE_INPUT_HATCH_REINFORCED.get(),
        BlockRegistration.SOURCE_INPUT_HATCH_BIG.get(),
        BlockRegistration.SOURCE_INPUT_HATCH_HUGE.get(),
        BlockRegistration.SOURCE_INPUT_HATCH_LUDICROUS.get(),
        BlockRegistration.SOURCE_INPUT_HATCH_VACUUM.get(),

        BlockRegistration.SOURCE_OUTPUT_HATCH_TINY.get(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_SMALL.get(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_NORMAL.get(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_REINFORCED.get(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_BIG.get(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_HUGE.get(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_LUDICROUS.get(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_VACUUM.get()
    );
  }

  @SubscribeEvent
  public void registerItemColors(final RegisterColorHandlersEvent.Item event) {
    event.register(
        ModularMachineryRebornClient::itemColor,

        ItemRegistration.SOURCE_INPUT_HATCH_TINY.get(),
        ItemRegistration.SOURCE_INPUT_HATCH_SMALL.get(),
        ItemRegistration.SOURCE_INPUT_HATCH_NORMAL.get(),
        ItemRegistration.SOURCE_INPUT_HATCH_REINFORCED.get(),
        ItemRegistration.SOURCE_INPUT_HATCH_BIG.get(),
        ItemRegistration.SOURCE_INPUT_HATCH_HUGE.get(),
        ItemRegistration.SOURCE_INPUT_HATCH_LUDICROUS.get(),
        ItemRegistration.SOURCE_INPUT_HATCH_VACUUM.get(),

        ItemRegistration.SOURCE_OUTPUT_HATCH_TINY.get(),
        ItemRegistration.SOURCE_OUTPUT_HATCH_SMALL.get(),
        ItemRegistration.SOURCE_OUTPUT_HATCH_NORMAL.get(),
        ItemRegistration.SOURCE_OUTPUT_HATCH_REINFORCED.get(),
        ItemRegistration.SOURCE_OUTPUT_HATCH_BIG.get(),
        ItemRegistration.SOURCE_OUTPUT_HATCH_HUGE.get(),
        ItemRegistration.SOURCE_OUTPUT_HATCH_LUDICROUS.get(),
        ItemRegistration.SOURCE_OUTPUT_HATCH_VACUUM.get()
    );
  }
}
