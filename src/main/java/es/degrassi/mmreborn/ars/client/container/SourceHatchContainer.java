package es.degrassi.mmreborn.ars.client.container;

import es.degrassi.mmreborn.ars.client.MMRArsClient;
import es.degrassi.mmreborn.ars.common.entity.base.SourceHatchEntity;
import es.degrassi.mmreborn.ars.common.registration.ContainerRegistration;
import es.degrassi.mmreborn.client.container.ContainerBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

public class SourceHatchContainer extends ContainerBase<SourceHatchEntity> {

  public static void open(ServerPlayer player, SourceHatchEntity machine) {
    player.openMenu(new MenuProvider() {
      @Override
      public @NotNull Component getDisplayName() {
        return Component.translatable("modular_machinery_reborn.gui.title.chemical_hatch");
      }

      @Override
      public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new SourceHatchContainer(id, inv, machine);
      }
    }, buf -> buf.writeBlockPos(machine.getBlockPos()));
  }

  public SourceHatchContainer(int id, Inventory playerInv, SourceHatchEntity entity) {
    super(entity, playerInv.player, ContainerRegistration.SOURCE_HATCH.get(), id);
  }

  public SourceHatchContainer(int id, Inventory inv, FriendlyByteBuf buffer) {
    this(id, inv, MMRArsClient.getClientSideSourceHatchEntity(buffer.readBlockPos()));
  }
}
