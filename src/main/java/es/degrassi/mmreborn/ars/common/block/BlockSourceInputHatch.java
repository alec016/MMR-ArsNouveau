package es.degrassi.mmreborn.ars.common.block;

import es.degrassi.mmreborn.ars.common.block.prop.SourceHatchSize;
import es.degrassi.mmreborn.ars.common.entity.SourceInputHatchEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockSourceInputHatch extends BlockSourceHatch {
  public BlockSourceInputHatch(SourceHatchSize size) {
    super(size);
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
    return new SourceInputHatchEntity(blockPos, blockState, size);
  }
}
