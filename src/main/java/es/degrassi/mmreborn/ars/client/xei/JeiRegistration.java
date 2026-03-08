package es.degrassi.mmreborn.ars.client.xei;

import es.degrassi.mmreborn.api.integration.jei.RegisterJeiComponentEvent;
import es.degrassi.mmreborn.ars.common.crafting.requirement.jei.JeiSourceComponent;
import es.degrassi.mmreborn.ars.common.registration.RequirementTypeRegistration;
import net.neoforged.bus.api.SubscribeEvent;

public class JeiRegistration {

  @SubscribeEvent
  public void registerJeiComponents(final RegisterJeiComponentEvent event) {
    event.register(RequirementTypeRegistration.SOURCE.get(), JeiSourceComponent::new);
  }
}
