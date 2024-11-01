package es.degrassi.mmreborn.ars.common.data;

import es.degrassi.mmreborn.ModularMachineryReborn;
import es.degrassi.mmreborn.ars.common.data.config.MMRSourceHatchConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = ModularMachineryReborn.MODID)
public class MMRConfig extends PartitioningSerializer.GlobalData {
  @ConfigEntry.Category("source_hatch")
  @ConfigEntry.Gui.TransitiveObject
  public MMRSourceHatchConfig sourceHatch = new MMRSourceHatchConfig();

  public static MMRConfig get() {
    return AutoConfig.getConfigHolder(MMRConfig.class).getConfig();
  }
}
