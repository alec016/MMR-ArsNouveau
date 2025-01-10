package es.degrassi.mmreborn.ars.common.machine.component;

import com.hollingsworth.arsnouveau.common.capability.SourceStorage;
import es.degrassi.mmreborn.ars.common.registration.ComponentRegistration;
import es.degrassi.mmreborn.common.crafting.ComponentType;
import es.degrassi.mmreborn.common.machine.IOType;
import es.degrassi.mmreborn.common.machine.MachineComponent;
import org.jetbrains.annotations.Nullable;

public class SourceComponent extends MachineComponent<SourceStorage> {
  private final SourceStorage handler;
  public SourceComponent(SourceStorage handler, IOType ioType) {
    super(ioType);
    this.handler = handler;
  }

  @Override
  public ComponentType getComponentType() {
    return ComponentRegistration.COMPONENT_SOURCE.get();
  }

  @Override
  public @Nullable SourceStorage getContainerProvider() {
    return handler;
  }
}
