package es.degrassi.mmreborn.ars.common.network;

import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.common.network.server.component.SUpdatePosComponentPacket;
import es.degrassi.mmreborn.ars.common.network.server.component.SUpdateSourceComponentPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = ModularMachineryRebornArs.MODID, bus = EventBusSubscriber.Bus.MOD)
public class PacketManager {
  @SubscribeEvent
  public static void register(final RegisterPayloadHandlersEvent event) {
    final PayloadRegistrar registrar = event.registrar(ModularMachineryRebornArs.MODID);

    registrar.playToClient(SUpdateSourceComponentPacket.TYPE, SUpdateSourceComponentPacket.CODEC, SUpdateSourceComponentPacket::handle);
    registrar.playToClient(SUpdatePosComponentPacket.TYPE, SUpdatePosComponentPacket.CODEC, SUpdatePosComponentPacket::handle);
  }
}
