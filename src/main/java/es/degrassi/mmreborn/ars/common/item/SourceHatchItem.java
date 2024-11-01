package es.degrassi.mmreborn.ars.common.item;

import es.degrassi.mmreborn.ars.common.block.prop.SourceHatchSize;
import es.degrassi.mmreborn.ars.common.block.BlockSourceHatch;
import es.degrassi.mmreborn.common.item.ItemBlockMachineComponent;
import lombok.Getter;

@Getter
public class SourceHatchItem extends ItemBlockMachineComponent {
  private final SourceHatchSize type;

  public SourceHatchItem(BlockSourceHatch block, SourceHatchSize type) {
    super(block, new Properties());
    this.type = type;
  }
}
