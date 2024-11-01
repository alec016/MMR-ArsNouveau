package es.degrassi.mmreborn.ars.common.util;

import com.hollingsworth.arsnouveau.common.capability.SourceStorage;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.Tag;

public class CopyHandlerHelper {
  public static SourceStorage copyTank(SourceStorage handler, RegistryAccess registryAccess) {
    Tag cmp = handler.serializeNBT(registryAccess);
    SourceStorage newTank = new SourceStorage(handler.getSourceCapacity());
    newTank.deserializeNBT(registryAccess, cmp);
    return newTank;
  }
}
