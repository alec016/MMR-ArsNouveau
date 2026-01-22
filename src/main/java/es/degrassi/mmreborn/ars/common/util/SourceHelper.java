package es.degrassi.mmreborn.ars.common.util;

import com.hollingsworth.arsnouveau.common.capability.SourceStorage;
import com.hollingsworth.arsnouveau.common.items.data.BlockFillContents;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.registry.DataComponentRegistry;
import net.minecraft.world.item.ItemStack;

public class SourceHelper {
  public static final SourceHelper INSTANCE = new SourceHelper();

  private SourceHelper() {}

  public void fillBufferFromStack(SourceStorage buffer, ItemStack stack) {

    BlockFillContents tag = stack.getComponents().get(DataComponentRegistry.BLOCK_FILL_CONTENTS.get());
    int itemCount = stack.getCount();
    if (tag == null) return;
    int source = tag.amount() * itemCount;
    int received = buffer.receiveSource(source, true);
    BlockFillContents newContent;
    if (received == source){
      newContent = new BlockFillContents(0);
      buffer.receiveSource(source, false);
    } else {
      buffer.receiveSource(received, false);
      newContent = new BlockFillContents((source - received) / itemCount);
    }
    stack.set(DataComponentRegistry.BLOCK_FILL_CONTENTS.get(), newContent);
  }

  public void fillStackFromBuffer(ItemStack stack, SourceStorage buffer) {

    BlockFillContents nbt = stack.getComponents().get(DataComponentRegistry.BLOCK_FILL_CONTENTS.get());
    if (nbt == null) return;
    int stackSource = nbt.amount();
    int stackCapacity = stack.is(BlockRegistry.SOURCE_JAR.asItem()) ? 10000 : 0;
    if (stackSource == stackCapacity || stackCapacity == 0) return;
    int itemCount = stack.getCount();
    int possibleReceive = (stackCapacity - stackSource) * itemCount;
    int extract;
    BlockFillContents newContent;
    if (buffer.extractSource(possibleReceive, true) == possibleReceive) {
      buffer.extractSource(possibleReceive, false);
      newContent = new BlockFillContents(stackCapacity);
    } else if ((extract = buffer.extractSource(possibleReceive, true)) < possibleReceive) {
      buffer.extractSource(extract, false);
      newContent = new BlockFillContents(stackSource + (extract / itemCount));
    } else {
      newContent = nbt;
    }
    stack.set(DataComponentRegistry.BLOCK_FILL_CONTENTS.get(), newContent);
  }
}
