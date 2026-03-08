package es.degrassi.mmreborn.ars.client.xei;

import es.degrassi.mmreborn.api.integration.emi.RegisterEmiComponentEvent;
import es.degrassi.mmreborn.ars.common.crafting.requirement.emi.EmiSourceComponent;
import es.degrassi.mmreborn.ars.common.registration.RequirementTypeRegistration;
import net.neoforged.bus.api.SubscribeEvent;

public class EmiRegistration {

  @SubscribeEvent
  public void registerEmiComponents(final RegisterEmiComponentEvent event) {
    event.register(RequirementTypeRegistration.SOURCE.get(), EmiSourceComponent::new);
  }
}
