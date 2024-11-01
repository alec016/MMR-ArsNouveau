package es.degrassi.mmreborn.ars.common.network.server.component;

import es.degrassi.mmreborn.ModularMachineryReborn;
import es.degrassi.mmreborn.ars.common.entity.base.SourceHatchEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SUpdateSourceComponentPacket (int source, BlockPos pos) implements CustomPacketPayload {
  public static final Type<SUpdateSourceComponentPacket> TYPE = new Type<>(ModularMachineryReborn.rl("update_source"));

  @Override
  public Type<SUpdateSourceComponentPacket> type() {
    return TYPE;
  }

  public static final StreamCodec<RegistryFriendlyByteBuf, SUpdateSourceComponentPacket> CODEC = StreamCodec.composite(
      ByteBufCodecs.INT,
      SUpdateSourceComponentPacket::source,
      BlockPos.STREAM_CODEC,
      SUpdateSourceComponentPacket::pos,
      SUpdateSourceComponentPacket::new
  );

  public static void handle(SUpdateSourceComponentPacket packet, IPayloadContext context) {
    if (context.flow().isClientbound())
      context.enqueueWork(() -> {
        if (context.player().level().getBlockEntity(packet.pos) instanceof SourceHatchEntity entity) {
          entity.getTank().setSource(packet.source);
        }
      });
  }
}
