package es.degrassi.mmreborn.ars.common.entity;

import es.degrassi.mmreborn.ars.common.block.prop.SourceHatchSize;
import es.degrassi.mmreborn.ars.common.entity.base.SourceHatchEntity;
import es.degrassi.mmreborn.ars.common.machine.component.SourceComponent;
import es.degrassi.mmreborn.ars.common.registration.EntityRegistration;
import es.degrassi.mmreborn.common.machine.IOType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class SourceInputHatchEntity extends SourceHatchEntity {

  public SourceInputHatchEntity(BlockPos pos, BlockState state) {
    super(EntityRegistration.SOURCE_INPUT_HATCH.get(), pos, state);
  }

  public SourceInputHatchEntity(BlockPos pos, BlockState state, SourceHatchSize size) {
    super(EntityRegistration.SOURCE_INPUT_HATCH.get(), pos, state, size, IOType.INPUT);
  }

  @Nullable
  @Override
  public SourceComponent provideComponent() {
    return new SourceComponent(getTank(), IOType.INPUT);
  }
}
