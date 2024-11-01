package es.degrassi.mmreborn.ars.client.util;

import net.minecraft.util.Mth;

public class SourceDisplayUtil {

  public static long formatSourceForDisplay(long source) {
    return Mth.lfloor(source);
  }
}
