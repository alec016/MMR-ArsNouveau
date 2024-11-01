package es.degrassi.mmreborn.ars.common.data.config;

import es.degrassi.mmreborn.ars.common.block.prop.SourceHatchSize;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "source_hatch")
public class MMRSourceHatchConfig implements ConfigData {
  @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
  public Tier TINY = new Tier(SourceHatchSize.TINY);

  @ConfigEntry.Gui.CollapsibleObject
  public Tier SMALL = new Tier(SourceHatchSize.SMALL);

  @ConfigEntry.Gui.CollapsibleObject
  public Tier NORMAL = new Tier(SourceHatchSize.NORMAL);

  @ConfigEntry.Gui.CollapsibleObject
  public Tier REINFORCED = new Tier(SourceHatchSize.REINFORCED);

  @ConfigEntry.Gui.CollapsibleObject
  public Tier BIG = new Tier(SourceHatchSize.BIG);

  @ConfigEntry.Gui.CollapsibleObject
  public Tier HUGE = new Tier(SourceHatchSize.HUGE);

  @ConfigEntry.Gui.CollapsibleObject
  public Tier LUDICROUS = new Tier(SourceHatchSize.LUDICROUS);

  @ConfigEntry.Gui.CollapsibleObject
  public Tier VACUUM = new Tier(SourceHatchSize.VACUUM);

  public static class Tier {
    @ConfigEntry.BoundedDiscrete(max = Integer.MAX_VALUE)
    @Comment("Defines the tank size of fluid hatch in mB")
    public int size;

    public Tier(SourceHatchSize tier) {
      this.size = tier.defaultConfigurationValue;
    }
  }

  public int sourceSize(SourceHatchSize size) {
    return switch(size) {
      case TINY -> TINY.size;
      case SMALL -> SMALL.size;
      case NORMAL -> NORMAL.size;
      case REINFORCED -> REINFORCED.size;
      case BIG -> BIG.size;
      case HUGE -> HUGE.size;
      case LUDICROUS -> LUDICROUS.size;
      case VACUUM -> VACUUM.size;
    };
  }
}
