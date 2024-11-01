package es.degrassi.mmreborn.ars.common.network.server.component;

import es.degrassi.mmreborn.ModularMachineryReborn;
import es.degrassi.mmreborn.ars.common.entity.base.SourceHatchEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public record SUpdatePosComponentPacket(boolean to, BlockPos pos, BlockPos entityPos) implements CustomPacketPayload {
  public static final Type<SUpdatePosComponentPacket> TYPE = new Type<>(ModularMachineryReborn.rl("update_pos"));

  @Override
  public Type<SUpdatePosComponentPacket> type() {
    return TYPE;
  }
  public static final StreamCodec<RegistryFriendlyByteBuf, BlockPos> BLOCK_POS_CODEC = new StreamCodec<>() {
    public @Nullable BlockPos decode(RegistryFriendlyByteBuf buffer) {
      return buffer.readBoolean() ? buffer.readBlockPos() : null;
    }

    public void encode(@NotNull RegistryFriendlyByteBuf buffer, @Nullable BlockPos pos) {
      if (pos == null) {
        buffer.writeBoolean(false);
      } else {
        buffer.writeBoolean(true);
        buffer.writeBlockPos(pos);
      }
    }
  };

  public static final StreamCodec<RegistryFriendlyByteBuf, SUpdatePosComponentPacket> CODEC = StreamCodec.composite(
      ByteBufCodecs.BOOL,
      SUpdatePosComponentPacket::to,
      BLOCK_POS_CODEC,
      SUpdatePosComponentPacket::pos,
      BlockPos.STREAM_CODEC,
      SUpdatePosComponentPacket::entityPos,
      SUpdatePosComponentPacket::new
  );

  public static void handle(SUpdatePosComponentPacket packet, IPayloadContext context) {
    if (context.flow().isClientbound())
      context.enqueueWork(() -> {
        if (context.player().level().getBlockEntity(packet.entityPos) instanceof SourceHatchEntity entity) {
          if (packet.to)
            entity.setToPos(packet.pos);
          else
            entity.setFromPos(packet.pos);
        }
      });
  }
}
