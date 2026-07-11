package es.degrassi.mmreborn.ars.common.entity.base;

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
import com.hollingsworth.arsnouveau.common.items.data.BlockFillContents;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.hollingsworth.arsnouveau.setup.registry.DataComponentRegistry;
import com.hollingsworth.nuggets.client.overlay.IWorldTooltipProvider;
import es.degrassi.mmreborn.ModularMachineryReborn;
import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.common.block.prop.SourceHatchSize;
import es.degrassi.mmreborn.ars.common.machine.component.SourceComponent;
import es.degrassi.mmreborn.ars.common.network.server.component.SUpdatePosComponentPacket;
import es.degrassi.mmreborn.ars.common.registration.MachineHatchTypeRegistration;
import es.degrassi.mmreborn.ars.common.util.SourceHelper;
import es.degrassi.mmreborn.client.integration.athena.model.hatch.HatchTextureData;
import es.degrassi.mmreborn.common.entity.base.ColorableMachineComponentEntity;
import es.degrassi.mmreborn.common.entity.base.DataComponentInventoryEntity;
import es.degrassi.mmreborn.common.entity.base.IServerTickEntity;
import es.degrassi.mmreborn.common.entity.base.ITickEntity;
import es.degrassi.mmreborn.common.entity.base.MachineComponentEntity;
import es.degrassi.mmreborn.common.entity.base.TextureableMachineEntity;
import es.degrassi.mmreborn.common.machine.IOType;
import es.degrassi.mmreborn.common.machine.MachineHatchType;
import es.degrassi.mmreborn.common.manager.handler.ItemHandler;
import es.degrassi.mmreborn.common.network.server.SUpdateMachineTexturePacket;
import es.degrassi.mmreborn.common.util.Utils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class SourceHatchEntity extends ColorableMachineComponentEntity implements MachineComponentEntity<SourceComponent>,
    IWandable, IWorldTooltipProvider, ISourceTile, IServerTickEntity, TextureableMachineEntity, ITickEntity,
    DataComponentInventoryEntity<BlockFillContents> {
  @Getter
  private static final ResourceLocation defaultBaseTexture = ModularMachineryReborn.rl("block/casing_plain");
  private SourceStorage tank;
  private IOType ioType;
  private SourceHatchSize hatchSize;

  private BlockPos toPos;
  private BlockPos fromPos;
  private static final String TO = "to_";
  private static final String FROM = "from";

  @Getter
  @Setter
  private ResourceLocation baseTexture;
  @Getter
  @Setter
  private ResourceLocation overlayTexture;
  @Getter
  private ResourceLocation defaultOverlayTexture;

  @Getter
  private final ItemHandler dataComponentInventory;

  private final long tickOffset = Utils.RAND.nextIntBetweenInclusive(0, Integer.MAX_VALUE - 1);
  private long lastCheckTick;

  public SourceHatchEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, SourceHatchSize size, IOType ioType) {
    super(type, pos, state);
    this.ioType = ioType;
    this.tank = size.buildTank(this, ioType == IOType.INPUT, ioType == IOType.OUTPUT);
    this.hatchSize = size;
    this.defaultOverlayTexture = ModularMachineryRebornArs.rl("block/overlay_source" + ioType.getSerializedName() + "hatch_" + size.getSerializedName());
    this.overlayTexture = defaultOverlayTexture;
    this.dataComponentInventory = createDataComponentInventory();
  }

  @Nullable
  @Override
  public SourceComponent provideComponent() {
    return new SourceComponent(getTank(), getMode());
  }

  @Override
  public DataComponentType<BlockFillContents> getDataComponent() {
    return DataComponentRegistry.BLOCK_FILL_CONTENTS.get();
  }

  @Override
  public IOType getMode() {
    return ioType;
  }

  @Override
  public boolean shouldTick() {
    long gameTime = getLevel().getGameTime();
    if (!Utils.shouldRunPeriodicCheck(false, gameTime, lastCheckTick, tickOffset, 2))
      return false;
    lastCheckTick = gameTime;
    return true;
  }

  @Override
  public void tickInventory() {
    if (!shouldTick()) return;
    dataComponentInventory.getInventory().forEach(slot -> {
      Optional.ofNullable(slot.getItemStack().get(getDataComponent())).ifPresent(component -> {
        if (getMode() == IOType.NONE) return;
        if (getMode().isInput()) {
          SourceHelper.INSTANCE.fillBufferFromStack(getTank(), slot.getItemStack());
        } else if (getMode().isOutput()) {
          SourceHelper.INSTANCE.fillStackFromBuffer(slot.getItemStack(), getTank());
        }
      });
    });
  }

  public void setToPos(@Nullable BlockPos toPos) {
    this.toPos = toPos;
    if (level instanceof ServerLevel l)
      PacketDistributor.sendToPlayersTrackingChunk(l, new ChunkPos(getBlockPos()), new SUpdatePosComponentPacket(true, toPos, getBlockPos()));
  }

  public void setFromPos(@Nullable BlockPos fromPos) {
    this.fromPos = fromPos;
    if (level instanceof ServerLevel l)
      PacketDistributor.sendToPlayersTrackingChunk(l, new ChunkPos(getBlockPos()), new SUpdatePosComponentPacket(false, toPos, getBlockPos()));
  }

  @Override
  @SuppressWarnings("deprecation")
  protected void loadAdditional(CompoundTag compound, HolderLookup.Provider provider) {
    super.loadAdditional(compound, provider);
    this.ioType = IOType.getByString(compound.getString("mode"));
    this.hatchSize = SourceHatchSize.value(compound.getString("size"));
    SourceStorage newTank = hatchSize.buildTank(this, ioType == IOType.INPUT, ioType == IOType.OUTPUT);
    Tag tankTag = compound.get("tank");
    if (tankTag != null)
      newTank.deserializeNBT(provider, tankTag);
    this.tank = newTank;
    this.toPos = null;
    this.fromPos = null;
    dataComponentInventory.deserialize(compound.getCompound("dataInventory"), provider);
    this.defaultOverlayTexture = ModularMachineryRebornArs.rl("block/overlay_source" + ioType.getSerializedName() + "hatch_" + hatchSize.getSerializedName());

    this.baseTexture = compound.contains("baseTexture") ? ResourceLocation.parse(compound.getString("baseTexture")) : defaultBaseTexture;
    this.overlayTexture = compound.contains("overlayTexture") ? ResourceLocation.parse(compound.getString("overlayTexture")) : defaultOverlayTexture;

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
    compound.putString("mode", ioType.getSerializedName());
    compound.putString("size", this.hatchSize.getSerializedName());
    Tag tankTag = this.tank.serializeNBT(provider);
    compound.put("tank", tankTag);
    compound.put("dataInventory", dataComponentInventory.writeNBT(provider));
    if (baseTexture != null)
      compound.putString("baseTexture", baseTexture.toString());
    if (overlayTexture != null)
      compound.putString("overlayTexture", overlayTexture.toString());
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

  @Override
  public ModelData getModelData() {
    return getModelDataBuilder("all").build();
  }

  @Override
  public HatchTextureData getTextureData(String mode) {
    return MachineComponentEntity.super.getTextureData(mode).derive(
        "bg_all",
        baseTexture,
        defaultBaseTexture,
        "ov_all",
        overlayTexture,
        defaultOverlayTexture,
        false
    );
  }

  @Override
  public ResourceLocation getMachineBaseTexture() {
    return baseTexture;
  }

  @Override
  public ResourceLocation getMachineOverlayTexture() {
    return overlayTexture;
  }

  @Override
  public void setMachineBaseTexture(ResourceLocation newTexture) {
    setChanged();
    this.baseTexture = newTexture;
    setRequestModelUpdate(true);
    triggerEvent(1, 0);
    this.markForUpdate();
    if (getLevel() instanceof ServerLevel l) {
      PacketDistributor.sendToPlayersTrackingChunk(l, new ChunkPos(getBlockPos()),
          new SUpdateMachineTexturePacket(baseTexture, true, getBlockPos()));
    }
  }

  @Override
  public void setMachineOverlayTexture(ResourceLocation newTexture) {
    setChanged();
    this.overlayTexture = newTexture;
    setRequestModelUpdate(true);
    triggerEvent(1, 0);
    this.markForUpdate();
    if (getLevel() instanceof ServerLevel l) {
      PacketDistributor.sendToPlayersTrackingChunk(l, new ChunkPos(getBlockPos()),
          new SUpdateMachineTexturePacket(overlayTexture, false, getBlockPos()));
    }
  }

  public void resetTextures() {
    setMachineBaseTexture(defaultBaseTexture);
    setMachineOverlayTexture(defaultOverlayTexture);
  }

  public SourceStorage getTank() {
    if (tank == null) {
      tank = hatchSize.buildTank(this, ioType == IOType.INPUT, ioType == IOType.OUTPUT);
      if (level != null) level.invalidateCapabilities(worldPosition);
    }
    return tank;
  }

  @Override
  public IWandable.Result onFirstConnection(@Nullable GlobalPos globalPos, @Nullable Direction side, @Nullable LivingEntity storedEntity, Player playerEntity) {
    BlockPos storedPos = Optional.ofNullable(globalPos).map(GlobalPos::pos).orElse(null);
    if (
      level == null
        || storedPos == null
        || level.isClientSide
        || storedPos.equals(getBlockPos())
        || (!(level.getBlockEntity(storedPos) instanceof AbstractSourceMachine)
        && !(level.getBlockEntity(storedPos) instanceof SourceHatchEntity))
    ) {
      return IWandable.Result.NONE;
    }
    if (getMode().isInput()) return IWandable.Result.FAIL;
    // Let relays take from us, no action needed.
    if (this.setSendTo(storedPos.immutable())) {
      PortUtil.sendMessage(playerEntity, Component.translatable("modular_machinery_reborn_ars.connections.send", DominionWand.getPosString(storedPos)));
      ParticleUtil.beam(storedPos, worldPosition, level);
      return IWandable.Result.SUCCESS;
    } else {
      PortUtil.sendMessage(playerEntity, Component.translatable("modular_machinery_reborn_ars.connections.fail"));
      return IWandable.Result.FAIL;
    }
  }

  @Override
  public IWandable.Result onLastConnection(@Nullable GlobalPos globalPos, @Nullable Direction side, @Nullable LivingEntity storedEntity, Player playerEntity) {
    BlockPos storedPos = Optional.ofNullable(globalPos).map(GlobalPos::pos).orElse(null);
    if (
      level == null
        || storedPos == null
        || storedPos.equals(getBlockPos())
        || level.getBlockEntity(storedPos) instanceof RelayTile
        || (!(level.getBlockEntity(storedPos) instanceof AbstractSourceMachine)
        && !(level.getBlockEntity(storedPos) instanceof SourceHatchEntity))
    ) {
      return IWandable.Result.NONE;
    }
    if (getMode().isOutput()) return IWandable.Result.FAIL;
    if (this.setTakeFrom(storedPos.immutable())) {
      PortUtil.sendMessage(playerEntity, Component.translatable("modular_machinery_reborn_ars.connections.take", DominionWand.getPosString(storedPos)));
      return IWandable.Result.SUCCESS;
    } else {
      PortUtil.sendMessage(playerEntity, Component.translatable("modular_machinery_reborn_ars.connections.fail"));
      return IWandable.Result.FAIL;
    }
  }

  @Override
  public IWandable.Result onClearConnections(Player playerEntity) {
    this.clearPos();
    PortUtil.sendMessage(playerEntity, Component.translatable("ars_nouveau.connections.cleared"));
    return IWandable.Result.CLEAR;
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
    IServerTickEntity.super.doRestrictedTick();
    tickInventory();
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

  @Override
  public MachineHatchType getHatchType() {
    return switch(ioType) {
      case INPUT -> (switch (hatchSize) {
        case TINY -> MachineHatchTypeRegistration.SOURCE_INPUT_HATCH_TINY;
        case SMALL -> MachineHatchTypeRegistration.SOURCE_INPUT_HATCH_SMALL;
        case NORMAL -> MachineHatchTypeRegistration.SOURCE_INPUT_HATCH_NORMAL;
        case REINFORCED -> MachineHatchTypeRegistration.SOURCE_INPUT_HATCH_REINFORCED;
        case BIG -> MachineHatchTypeRegistration.SOURCE_INPUT_HATCH_BIG;
        case HUGE -> MachineHatchTypeRegistration.SOURCE_INPUT_HATCH_HUGE;
        case LUDICROUS -> MachineHatchTypeRegistration.SOURCE_INPUT_HATCH_LUDICROUS;
        case VACUUM -> MachineHatchTypeRegistration.SOURCE_INPUT_HATCH_VACUUM;
      }).get();
      case OUTPUT -> (switch(hatchSize) {
        case TINY -> MachineHatchTypeRegistration.SOURCE_OUTPUT_HATCH_TINY;
        case SMALL -> MachineHatchTypeRegistration.SOURCE_OUTPUT_HATCH_SMALL;
        case NORMAL -> MachineHatchTypeRegistration.SOURCE_OUTPUT_HATCH_NORMAL;
        case REINFORCED -> MachineHatchTypeRegistration.SOURCE_OUTPUT_HATCH_REINFORCED;
        case BIG -> MachineHatchTypeRegistration.SOURCE_OUTPUT_HATCH_BIG;
        case HUGE -> MachineHatchTypeRegistration.SOURCE_OUTPUT_HATCH_HUGE;
        case LUDICROUS -> MachineHatchTypeRegistration.SOURCE_OUTPUT_HATCH_LUDICROUS;
        case VACUUM -> MachineHatchTypeRegistration.SOURCE_OUTPUT_HATCH_VACUUM;
      }).get();
      default -> null;
    };
  }
}
