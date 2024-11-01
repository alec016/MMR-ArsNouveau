package es.degrassi.mmreborn.ars.common.machine;

import com.hollingsworth.arsnouveau.common.capability.SourceStorage;
import es.degrassi.mmreborn.ars.common.registration.ComponentRegistration;
import es.degrassi.mmreborn.common.crafting.ComponentType;
import es.degrassi.mmreborn.common.machine.IOType;
import es.degrassi.mmreborn.common.machine.MachineComponent;

public abstract class SourceHatch extends MachineComponent<SourceStorage> {
  public SourceHatch(IOType ioType) {
    super(ioType);
  }

  @Override
  public ComponentType getComponentType() {
    return ComponentRegistration.COMPONENT_SOURCE.get();
  }
}
