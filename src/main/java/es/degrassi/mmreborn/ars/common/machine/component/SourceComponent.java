package es.degrassi.mmreborn.ars.common.machine.component;

import com.hollingsworth.arsnouveau.common.capability.SourceStorage;
import es.degrassi.mmreborn.ars.common.registration.ComponentRegistration;
import es.degrassi.mmreborn.common.crafting.ComponentType;
import es.degrassi.mmreborn.common.machine.IOType;
import es.degrassi.mmreborn.common.machine.MachineComponent;
import net.minecraft.MethodsReturnNonnullByDefault;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
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

  @Override
  @SuppressWarnings("unchecked")
  public <C extends MachineComponent<?>> C merge(C c) {
    SourceComponent comp = (SourceComponent) c;
    return (C) new SourceComponent(
        new SourceStorage(handler.getSourceCapacity() + comp.handler.getSourceCapacity()) {
          @Override
          public int getSource() {
            return handler.getSource() + comp.handler.getSource();
          }

          @Override
          public int receiveSource(int toReceive, boolean simulate) {
            int received1 = handler.receiveSource(toReceive, simulate);
            toReceive -= received1;
            int received2 = comp.handler.receiveSource(toReceive, simulate);
            return received1 + received2;
          }

          @Override
          public int extractSource(int toExtract, boolean simulate) {
            int extracted1 = handler.extractSource(toExtract, simulate);
            toExtract -= extracted1;
            int extracted2 = comp.handler.extractSource(toExtract, simulate);
            return extracted1 + extracted2;
          }
        },
        getIOType()
    );
  }
}
