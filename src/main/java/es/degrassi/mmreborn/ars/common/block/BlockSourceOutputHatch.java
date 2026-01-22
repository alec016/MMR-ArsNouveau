package es.degrassi.mmreborn.ars.common.block;

import es.degrassi.mmreborn.ars.common.block.prop.SourceHatchSize;
import es.degrassi.mmreborn.ars.common.entity.SourceOutputHatchEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockSourceOutputHatch extends BlockSourceHatch {
  public BlockSourceOutputHatch(SourceHatchSize size) {
    super(size);
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
    return new SourceOutputHatchEntity(blockPos, blockState, size);
  }
}
