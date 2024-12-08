package es.degrassi.mmreborn.ars;

import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.common.items.DominionWand;
import com.hollingsworth.arsnouveau.common.items.data.DominionWandData;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.hollingsworth.arsnouveau.setup.registry.DataComponentRegistry;
import es.degrassi.mmreborn.ars.client.MMRArsClient;
import es.degrassi.mmreborn.ars.common.block.prop.SourceHatchSize;
import es.degrassi.mmreborn.ars.common.data.MMRConfig;
import es.degrassi.mmreborn.ars.common.entity.base.SourceHatchEntity;
import es.degrassi.mmreborn.ars.common.registration.EntityRegistration;
import es.degrassi.mmreborn.ars.common.registration.Registration;
import es.degrassi.mmreborn.client.util.EnergyDisplayUtil;
import es.degrassi.mmreborn.common.block.prop.EnergyHatchSize;
import es.degrassi.mmreborn.common.block.prop.FluidHatchSize;
import es.degrassi.mmreborn.common.block.prop.ItemBusSize;
import es.degrassi.mmreborn.common.data.Config;
import es.degrassi.mmreborn.common.util.MMRLogger;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.CommandEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Mod(ModularMachineryRebornArs.MODID)
public class ModularMachineryRebornArs {
  public static final String MODID = "modular_machinery_reborn_ars";
  public static final Logger LOGGER = LogManager.getLogger("Modular Machinery Reborn ArsNouveau");

  public ModularMachineryRebornArs(final ModContainer CONTAINER, final IEventBus MOD_BUS) {
    CONTAINER.registerConfig(ModConfig.Type.COMMON, MMRConfig.getSpec());
    Registration.register(MOD_BUS);

    MOD_BUS.addListener(this::commonSetup);

    NeoForge.EVENT_BUS.addListener(this::handleWandClick);

    MOD_BUS.register(new MMRArsClient());
    MOD_BUS.addListener(this::registerCapabilities);
    MOD_BUS.addListener(this::reloadConfig);

    final IEventBus GAME_BUS = NeoForge.EVENT_BUS;
    GAME_BUS.addListener(this::onReloadStart);
  }

  private void commonSetup(final FMLCommonSetupEvent event) {
    SourceHatchSize.loadFromConfig();
  }

  private void reloadConfig(final ModConfigEvent.Reloading event) {
    if(event.getConfig().getSpec() == MMRConfig.getSpec()) {
      SourceHatchSize.loadFromConfig();
    }
  }

  private void handleWandClick(final PlayerInteractEvent.RightClickBlock event) {
    if (event.getEntity() instanceof ServerPlayer player && !player.isShiftKeyDown()) {
      if (player.getItemInHand(event.getHand()).getItem() instanceof DominionWand wand
          && player.level().getBlockEntity(event.getPos()) instanceof SourceHatchEntity tile) {
        if (tile.getTank() == null) return;
        ItemStack stack = player.getItemInHand(event.getHand());
        DominionWandData data = stack.getOrDefault(DataComponentRegistry.DOMINION_WAND.get(), new DominionWandData());
        if (!data.hasStoredData()) {
          data = data.storePos(new GlobalPos(event.getLevel().dimension(), event.getPos().immutable()));
          if (data.strict()) data = data.setFace(event.getFace());
          stack.set(DataComponentRegistry.DOMINION_WAND.get(), data);
          PortUtil.sendMessage(player, Component.translatable("ars_nouveau.dominion_wand.position_set"));
          event.setCancellationResult(InteractionResult.CONSUME);
          event.setCanceled(true);
          return;
        }
        if (data.storedPos().isPresent() && player.getCommandSenderWorld().getBlockEntity(data.storedPos().get().pos()) instanceof IWandable wandable) {
          wandable.onFinishedConnectionFirst(data.storedPos().get(), (LivingEntity) player.level().getEntity(data.storedEntityId()), player);
        }
        tile.onFinishedConnectionLast(data.storedPos().get(), (LivingEntity) player.level().getEntity(data.storedEntityId()), player);
        tile.getTank().onContentsChanged();
        if (data.storedEntityId() != -1 && player.level().getEntity(data.storedEntityId()) instanceof IWandable wandable) {
          wandable.onFinishedConnectionFirst(event.getPos(), null, player);
        }
        wand.clear(stack, player);
        event.setCancellationResult(InteractionResult.CONSUME);
      }
    }
  }

  private void registerCapabilities(final RegisterCapabilitiesEvent event) {
    event.registerBlockEntity(
        CapabilityRegistry.SOURCE_CAPABILITY,
        EntityRegistration.SOURCE_INPUT_HATCH.get(),
        (be, side) -> be.getTank()
    );
    event.registerBlockEntity(
        CapabilityRegistry.SOURCE_CAPABILITY,
        EntityRegistration.SOURCE_OUTPUT_HATCH.get(),
        (be, side) -> be.getTank()
    );
  }

  @Contract("_ -> new")
  public static @NotNull ResourceLocation rl(String path) {
    return ResourceLocation.fromNamespaceAndPath(MODID, path);
  }

  private void onReloadStart(final CommandEvent event) {
    if (event.getParseResults().getReader().getString().equals("reload") && event.getParseResults().getContext().getSource().hasPermission(2)) {
      SourceHatchSize.loadFromConfig();
    }
  }
}
