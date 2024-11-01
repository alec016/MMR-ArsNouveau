package es.degrassi.mmreborn.ars.common.entity;

import com.hollingsworth.arsnouveau.common.capability.SourceStorage;
import es.degrassi.mmreborn.common.entity.base.MachineComponentEntity;
import es.degrassi.mmreborn.common.machine.IOType;
import es.degrassi.mmreborn.common.machine.MachineComponent;
import es.degrassi.mmreborn.ars.common.block.prop.SourceHatchSize;
import es.degrassi.mmreborn.ars.common.entity.base.SourceHatchEntity;
import es.degrassi.mmreborn.ars.common.machine.SourceHatch;
import es.degrassi.mmreborn.ars.common.registration.EntityRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class SourceOutputHatchEntity extends SourceHatchEntity implements MachineComponentEntity {

  public SourceOutputHatchEntity(BlockPos pos, BlockState state) {
    super(EntityRegistration.SOURCE_OUTPUT_HATCH.get(), pos, state);
  }

  public SourceOutputHatchEntity(BlockPos pos, BlockState state, SourceHatchSize size) {
    super(EntityRegistration.SOURCE_OUTPUT_HATCH.get(), pos, state, size, IOType.OUTPUT);
  }

  @Nullable
  @Override
  public MachineComponent provideComponent() {
    return new SourceHatch(IOType.OUTPUT) {
      @Override
      public SourceStorage getContainerProvider() {
        return getTank();
      }
    };
  }
}
