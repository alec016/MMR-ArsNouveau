package es.degrassi.mmreborn.ars.common.entity.base;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.source.AbstractSourceMachine;
import com.hollingsworth.arsnouveau.api.source.ISourceCap;
import com.hollingsworth.arsnouveau.api.source.ISourceTile;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.client.particle.ColorPos;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.tile.RelayTile;
import com.hollingsworth.arsnouveau.common.capability.SourceStorage;
import com.hollingsworth.arsnouveau.common.items.DominionWand;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import es.degrassi.mmreborn.ars.common.block.prop.SourceHatchSize;
import es.degrassi.mmreborn.ars.common.machine.component.SourceComponent;
import es.degrassi.mmreborn.ars.common.network.server.component.SUpdatePosComponentPacket;
import es.degrassi.mmreborn.common.entity.base.BlockEntityRestrictedTick;
import es.degrassi.mmreborn.common.entity.base.MachineComponentEntity;
import es.degrassi.mmreborn.common.machine.IOType;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public abstract class SourceHatchEntity extends BlockEntityRestrictedTick implements MachineComponentEntity<SourceComponent>,
    IWandable, ITooltipProvider, ISourceTile {
  private SourceStorage tank;
  private IOType ioType;
  private SourceHatchSize hatchSize;

  private BlockPos toPos;
  private BlockPos fromPos;
  private static final String TO = "to_";
  private static final String FROM = "from";

  public SourceHatchEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
  }

  public SourceHatchEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, SourceHatchSize size, IOType ioType) {
    super(type, pos, state);
    this.tank = size.buildTank(this, ioType == IOType.INPUT, ioType == IOType.OUTPUT);
    this.hatchSize = size;
  }

  public void setToPos(BlockPos toPos) {
    this.toPos = toPos;
    if (level instanceof ServerLevel l)
      PacketDistributor.sendToPlayersTrackingChunk(l, new ChunkPos(getBlockPos()), new SUpdatePosComponentPacket(true, toPos, getBlockPos()));
  }

  public void setFromPos(BlockPos fromPos) {
    this.fromPos = fromPos;
    if (level instanceof ServerLevel l)
      PacketDistributor.sendToPlayersTrackingChunk(l, new ChunkPos(getBlockPos()), new SUpdatePosComponentPacket(false, toPos, getBlockPos()));
  }

  @Override
  @SuppressWarnings("deprecation")
  protected void loadAdditional(CompoundTag compound, HolderLookup.Provider provider) {
    super.loadAdditional(compound, provider);
    this.ioType = compound.getBoolean("input") ? IOType.INPUT : IOType.OUTPUT;
    this.hatchSize = SourceHatchSize.value(compound.getString("size"));
    SourceStorage newTank = hatchSize.buildTank(this, ioType == IOType.INPUT, ioType == IOType.OUTPUT);
    Tag tankTag = compound.get("tank");
    if (tankTag != null)
      newTank.deserializeNBT(provider, tankTag);
    this.tank = newTank;
    this.toPos = null;
    this.fromPos = null;

    if (NBTUtil.hasBlockPos(compound, TO)) {
      this.toPos = NBTUtil.getBlockPos(compound, TO);
    }
    if (NBTUtil.hasBlockPos(compound, FROM)) {
      this.fromPos = NBTUtil.getBlockPos(compound, FROM);
    }
  }

  @Override
  protected void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
    super.saveAdditional(compound, provider);
    compound.putBoolean("input", ioType == IOType.INPUT);
    compound.putString("size", this.hatchSize.getSerializedName());
    Tag tankTag = this.tank.serializeNBT(provider);
    compound.put("tank", tankTag);
    if (toPos != null) {
      NBTUtil.storeBlockPos(compound, TO, toPos.immutable());
    } else {
      NBTUtil.removeBlockPos(compound, TO);
    }

    if (fromPos != null) {
      NBTUtil.storeBlockPos(compound, FROM, fromPos.immutable());
    } else {
      NBTUtil.removeBlockPos(compound, FROM);
    }
  }

  public SourceStorage getTank() {
    if (tank == null) {
      tank = hatchSize.buildTank(this, ioType == IOType.INPUT, ioType == IOType.OUTPUT);
      if (level != null) level.invalidateCapabilities(worldPosition);
    }
    return tank;
  }

  @Override
  public void onFinishedConnectionFirst(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
    if (
      level == null
        || storedPos == null
        || level.isClientSide
        || storedPos.equals(getBlockPos())
        || (!(level.getBlockEntity(storedPos) instanceof AbstractSourceMachine)
        && !(level.getBlockEntity(storedPos) instanceof SourceHatchEntity))
    ) {
      return;
    }
    // Let relays take from us, no action needed.
    if (this.setSendTo(storedPos.immutable())) {
      PortUtil.sendMessage(playerEntity, Component.translatable("modular_machinery_reborn_ars.connections.send", DominionWand.getPosString(storedPos)));
      ParticleUtil.beam(storedPos, worldPosition, level);
    } else {
      PortUtil.sendMessage(playerEntity, Component.translatable("modular_machinery_reborn_ars.connections.fail"));
    }
  }

  @Override
  public void onFinishedConnectionLast(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
    if (
      level == null
        || storedPos == null
        || storedPos.equals(getBlockPos())
        || level.getBlockEntity(storedPos) instanceof RelayTile
        || (!(level.getBlockEntity(storedPos) instanceof AbstractSourceMachine)
        && !(level.getBlockEntity(storedPos) instanceof SourceHatchEntity))
    ) {
      return;
    }

    if (this.setTakeFrom(storedPos.immutable())) {
      PortUtil.sendMessage(playerEntity, Component.translatable("modular_machinery_reborn_ars.connections.take", DominionWand.getPosString(storedPos)));
    } else {
      PortUtil.sendMessage(playerEntity, Component.translatable("modular_machinery_reborn_ars.connections.fail"));
    }
  }

  @Override
  public void onWanded(Player playerEntity) {
    this.clearPos();
    PortUtil.sendMessage(playerEntity, Component.translatable("ars_nouveau.connections.cleared"));
  }

  @Override
  public List<ColorPos> getWandHighlight(List<ColorPos> list) {
    if (toPos != null) {
      list.add(ColorPos.centered(toPos, ParticleColor.TO_HIGHLIGHT));
    }
    if(fromPos != null){
      list.add(ColorPos.centered(fromPos, ParticleColor.FROM_HIGHLIGHT));
    }
    return list;
  }

  public int getMaxDistance() {
    return 30;
  }

  public boolean setTakeFrom(BlockPos pos) {
    if (
      BlockUtil.distanceFrom(pos, this.worldPosition) > getMaxDistance()
        || pos.equals(getBlockPos())
    ) {
      return false;
    }
    setFromPos(pos);
    return true;
  }

  public boolean setSendTo(BlockPos pos) {
    if (
      BlockUtil.distanceFrom(pos, this.worldPosition) > getMaxDistance()
        || pos.equals(getBlockPos())
        || (!(Objects.requireNonNull(level).getBlockEntity(pos) instanceof AbstractSourceMachine)
        && !(level.getBlockEntity(pos) instanceof SourceHatchEntity))
    ) {
      return false;
    }
    setToPos(pos);
    return true;
  }

  public void clearPos() {
    setToPos(null);
    setFromPos(null);
  }

  public int transferSource(ISourceTile from, ISourceTile to) {
    int transferRate = getTransferRate(from, to);
    from.removeSource(transferRate);
    to.addSource(transferRate);
    markForUpdate();
    return transferRate;
  }

  public int transferSource(ISourceCap from, ISourceCap to) {
    int transfer = to.receiveSource(from.extractSource(from.getMaxExtract(), true), true);
    if (transfer == 0)
      return 0;
    from.extractSource(transfer, false);
    to.receiveSource(transfer, false);
    markForUpdate();
    return transfer;
  }

  public int getTransferRate(ISourceTile from, ISourceTile to) {
    return Math.min(Math.min(from.getTransferRate(), from.getSource()), to.getMaxSource() - to.getSource());
  }

  @Override
  public void getTooltip(List<Component> tooltip) {
    tooltip.clear();
    if (toPos == null) {
      tooltip.add(Component.translatable("modular_machinery_reborn_ars.relay.no_to"));
    } else {
      tooltip.add(Component.translatable("modular_machinery_reborn_ars.relay.one_to", 1));
    }
    if (fromPos == null) {
      tooltip.add(Component.translatable("modular_machinery_reborn_ars.relay.no_from"));
    } else {
      tooltip.add(Component.translatable("modular_machinery_reborn_ars.relay.one_from", 1));
    }
  }

  private ParticleColor getParticleColor() {
    return ParticleColor.defaultParticleColor();
  }

  @Override
  public void doRestrictedTick() {
    if(level == null || level.isClientSide)
      return;
    if (level.getGameTime() % 20 != 0)
      return;
    if (fromPos != null && level.isLoaded(fromPos)) {
      if (!(level.getBlockEntity(fromPos) instanceof AbstractSourceMachine) &&
          !(level.getBlockEntity(fromPos) instanceof SourceHatchEntity) &&
          level.getCapability(CapabilityRegistry.SOURCE_CAPABILITY, fromPos, null) == null
      ) {
        setFromPos(null);
        markForUpdate();
        return;
      }

      if (level.getCapability(CapabilityRegistry.SOURCE_CAPABILITY, fromPos, null) instanceof ISourceCap handler) {
        if (transferSource(handler, getTank()) > 0) {
          ParticleUtil.spawnFollowProjectile(level, fromPos, worldPosition, getParticleColor());
        }
        return;
      }

      if (level.getBlockEntity(fromPos) instanceof SourceHatchEntity from) {
        if (transferSource(from.tank, getTank()) > 0) {
          ParticleUtil.spawnFollowProjectile(level, fromPos, worldPosition, getParticleColor());
        }
        return;
      }

      if (level.getBlockEntity(fromPos) instanceof AbstractSourceMachine from) {
        if (transferSource(from.getSourceStorage(), getTank()) > 0) {
          ParticleUtil.spawnFollowProjectile(level, fromPos, worldPosition, getParticleColor());
        }
      }
    }

    if (toPos != null && level.isLoaded(toPos)) {
      if (!(level.getBlockEntity(toPos) instanceof AbstractSourceMachine) &&
          !(level.getBlockEntity(toPos) instanceof SourceHatchEntity) &&
          level.getCapability(CapabilityRegistry.SOURCE_CAPABILITY, toPos, null) == null
      ) {
        setToPos(null);
        markForUpdate();
        return;
      }

      if (level.getCapability(CapabilityRegistry.SOURCE_CAPABILITY, toPos, null) instanceof ISourceCap handler) {
        if (transferSource(handler, getTank()) > 0) {
          ParticleUtil.spawnFollowProjectile(level, worldPosition, toPos, getParticleColor());
        }
        return;
      }

      if (level.getBlockEntity(toPos) instanceof SourceHatchEntity from) {
        if (transferSource(from.tank, getTank()) > 0) {
          ParticleUtil.spawnFollowProjectile(level, worldPosition, toPos, getParticleColor());
        }
        return;
      }

      if (level.getBlockEntity(toPos) instanceof AbstractSourceMachine from) {
        if (transferSource(from.getSourceStorage(), getTank()) > 0) {
          ParticleUtil.spawnFollowProjectile(level, worldPosition, toPos, getParticleColor());
        }
      }
    }
  }

  @Override
  public int getTransferRate() {
    return getMaxSource();
  }

  @Override
  public boolean canAcceptSource() {
    return this.ioType.isInput() && this.tank.getSource() < this.tank.getMaxSource();
  }

  @Override
  public int getSource() {
    return tank.getSource();
  }

  @Override
  public int getMaxSource() {
    return tank.getMaxSource();
  }

  @Override
  public int setSource(int source) {
    tank.setSource(source);
    return getMaxSource();
  }

  @Override
  public int addSource(int source, boolean simulate) {
    return tank.receiveSource(source, simulate);
  }

  @Override
  public int addSource(int source) {
    return tank.receiveSource(source, false);
  }

  @Override
  public int removeSource(int source, boolean simulate) {
    return tank.extractSource(source, simulate);
  }

  @Override
  public int removeSource(int source) {
    return tank.receiveSource(source, false);
  }
}
