package es.degrassi.mmreborn.ars.data;

import es.degrassi.mmreborn.ars.common.registration.BlockRegistration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MMRArsLootTableProvider extends LootTableProvider {
  public MMRArsLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
    super(output, Set.of(), List.of(
        new SubProviderEntry(
            MMRArsLootTableProvider.LootSubProvider::new,
            LootTable.DEFAULT_PARAM_SET
        )
    ), provider);
  }

  static class LootSubProvider extends BlockLootSubProvider {
    public LootSubProvider(HolderLookup.Provider provider) {
      super(Set.of(), FeatureFlags.DEFAULT_FLAGS, provider);

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
      return BlockRegistration.BLOCKS.getEntries()
          .stream()
          .map(DeferredHolder::value)
          .map(e -> (Block) e)
          .toList();
    }

    @Override
    protected void generate() {
      getKnownBlocks().forEach(this::dropSelf);
    }
  }
}
