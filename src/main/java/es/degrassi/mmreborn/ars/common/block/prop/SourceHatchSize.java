package es.degrassi.mmreborn.ars.common.block.prop;

import com.hollingsworth.arsnouveau.common.capability.SourceStorage;
import es.degrassi.mmreborn.ars.common.data.MMRConfig;
import es.degrassi.mmreborn.ars.common.entity.base.SourceHatchEntity;
import es.degrassi.mmreborn.ars.common.network.server.component.SUpdateSourceComponentPacket;
import es.degrassi.mmreborn.common.block.prop.ConfigLoaded;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Locale;

public enum SourceHatchSize implements StringRepresentable, ConfigLoaded {
  TINY(100),
  SMALL(400),
  NORMAL(1000),
  REINFORCED(2000),
  BIG(4500),
  HUGE(8000),
  LUDICROUS(16000),
  VACUUM(32000);

  @Getter
  @Setter
  private int size;

  public final int defaultConfigurationValue;

  SourceHatchSize(int defaultConfigurationValue) {
    this.defaultConfigurationValue = defaultConfigurationValue;
  }

  public static SourceHatchSize value(String value) {
    return switch(value.toUpperCase(Locale.ROOT)) {
      case "SMALL" -> SMALL;
      case "NORMAL" -> NORMAL;
      case "REINFORCED" -> REINFORCED;
      case "BIG" -> BIG;
      case "HUGE" -> HUGE;
      case "LUDICROUS" -> LUDICROUS;
      case "VACUUM" -> VACUUM;
      default -> TINY;
    };
  }

  @Override
  public String getSerializedName() {
    return name().toLowerCase();
  }

  public SourceStorage buildTank(SourceHatchEntity sourceHatchEntity, boolean canFill, boolean canDrain) {
    return buildDefaultTank(sourceHatchEntity, canFill, canDrain);
  }

  public SourceStorage buildDefaultTank(SourceHatchEntity sourceHatchEntity, boolean canFill, boolean canDrain) {
    return new SourceStorage(getSize(), getSize(), getSize()) {
      @Override
      public void onContentsChanged() {
        if(sourceHatchEntity.getLevel() instanceof ServerLevel l)
          PacketDistributor.sendToPlayersTrackingChunk(
              l,
              new ChunkPos(sourceHatchEntity.getBlockPos()),
              new SUpdateSourceComponentPacket(sourceHatchEntity.getTank().getSource(), sourceHatchEntity.getBlockPos())
          );
      }
    };
  }
}
