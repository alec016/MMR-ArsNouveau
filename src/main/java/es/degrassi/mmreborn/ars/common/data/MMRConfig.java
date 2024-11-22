package es.degrassi.mmreborn.ars.common.data;

import es.degrassi.mmreborn.ars.common.block.prop.SourceHatchSize;
import lombok.Getter;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

public class MMRConfig {
  private static final MMRConfig INSTANCE;
  @Getter
  private static final ModConfigSpec spec;

  static {
    Pair<MMRConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(MMRConfig::new);
    INSTANCE = pair.getLeft();
    spec = pair.getRight();
  }

  public final ConfigValue<Integer> TINY_size;

  public final ConfigValue<Integer> SMALL_size;

  public final ConfigValue<Integer> NORMAL_size;

  public final ConfigValue<Integer> REINFORCED_size;

  public final ConfigValue<Integer> BIG_size;

  public final ConfigValue<Integer> HUGE_size;

  public final ConfigValue<Integer> LUDICROUS_size;

  public final ConfigValue<Integer> VACUUM_size;

  public MMRConfig(ModConfigSpec.Builder builder) {
    builder.push(SourceHatchSize.TINY.getSerializedName());
    this.TINY_size = builder
        .comment("Defines the source tank size")
        .defineInRange("size", SourceHatchSize.TINY.defaultConfigurationValue, 1, Integer.MAX_VALUE);
    builder.pop();
    builder.push(SourceHatchSize.SMALL.getSerializedName());
    this.SMALL_size = builder
        .comment("Defines the source tank size")
        .defineInRange("size", SourceHatchSize.SMALL.defaultConfigurationValue, 1, Integer.MAX_VALUE);
    builder.pop();
    builder.push(SourceHatchSize.NORMAL.getSerializedName());
    this.NORMAL_size = builder
        .comment("Defines the source tank size")
        .defineInRange("size", SourceHatchSize.NORMAL.defaultConfigurationValue, 1, Integer.MAX_VALUE);
    builder.pop();
    builder.push(SourceHatchSize.REINFORCED.getSerializedName());
    this.REINFORCED_size = builder
        .comment("Defines the source tank size")
        .defineInRange("size", SourceHatchSize.REINFORCED.defaultConfigurationValue, 1, Integer.MAX_VALUE);
    builder.pop();
    builder.push(SourceHatchSize.BIG.getSerializedName());
    this.BIG_size = builder
        .comment("Defines the source tank size")
        .defineInRange("size", SourceHatchSize.BIG.defaultConfigurationValue, 1, Integer.MAX_VALUE);
    builder.pop();
    builder.push(SourceHatchSize.HUGE.getSerializedName());
    this.HUGE_size = builder
        .comment("Defines the source tank size")
        .defineInRange("size", SourceHatchSize.HUGE.defaultConfigurationValue, 1, Integer.MAX_VALUE);
    builder.pop();
    builder.push(SourceHatchSize.LUDICROUS.getSerializedName());
    this.LUDICROUS_size = builder
        .comment("Defines the source tank size")
        .defineInRange("size", SourceHatchSize.LUDICROUS.defaultConfigurationValue, 1, Integer.MAX_VALUE);
    builder.pop();
    builder.push(SourceHatchSize.VACUUM.getSerializedName());
    this.VACUUM_size = builder
        .comment("Defines the source tank size")
        .defineInRange("size", SourceHatchSize.VACUUM.defaultConfigurationValue, 1, Integer.MAX_VALUE);
    builder.pop();
  }


  public int sourceSize(SourceHatchSize size) {
    return switch(size) {
      case TINY -> TINY_size.get();
      case SMALL -> SMALL_size.get();
      case NORMAL -> NORMAL_size.get();
      case REINFORCED -> REINFORCED_size.get();
      case BIG -> BIG_size.get();
      case HUGE -> HUGE_size.get();
      case LUDICROUS -> LUDICROUS_size.get();
      case VACUUM -> VACUUM_size.get();
    };
  }

  public static MMRConfig get() {
    return INSTANCE;
  }
}
