package es.degrassi.mmreborn.ars.common.entity;

import com.hollingsworth.arsnouveau.common.capability.SourceStorage;
import es.degrassi.mmreborn.ars.common.registration.EntityRegistration;
import es.degrassi.mmreborn.common.entity.base.MachineComponentEntity;
import es.degrassi.mmreborn.common.machine.IOType;
import es.degrassi.mmreborn.common.machine.MachineComponent;
import es.degrassi.mmreborn.ars.common.block.prop.SourceHatchSize;
import es.degrassi.mmreborn.ars.common.entity.base.SourceHatchEntity;
import es.degrassi.mmreborn.ars.common.machine.SourceHatch;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class SourceInputHatchEntity extends SourceHatchEntity implements MachineComponentEntity {

  public SourceInputHatchEntity(BlockPos pos, BlockState state) {
    super(EntityRegistration.SOURCE_INPUT_HATCH.get(), pos, state);
  }

  public SourceInputHatchEntity(BlockPos pos, BlockState state, SourceHatchSize size) {
    super(EntityRegistration.SOURCE_INPUT_HATCH.get(), pos, state, size, IOType.INPUT);
  }

  @Nullable
  @Override
  public MachineComponent provideComponent() {
    return new SourceHatch(IOType.INPUT) {
      @Override
      public SourceStorage getContainerProvider() {
        return getTank();
      }
    };
  }
}
