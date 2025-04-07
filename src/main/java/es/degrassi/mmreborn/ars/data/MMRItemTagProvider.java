package es.degrassi.mmreborn.ars.data;

import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.common.registration.BlockRegistration;
import es.degrassi.mmreborn.data.MMRTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MMRItemTagProvider extends ItemTagsProvider {
  public MMRItemTagProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, CompletableFuture<TagLookup<Block>> completableFuture2, @Nullable ExistingFileHelper existingFileHelper) {
    super(arg, completableFuture, completableFuture2, ModularMachineryRebornArs.MODID, existingFileHelper);
  }

  @Override
  public void addTags(HolderLookup.@NotNull Provider provider) {
    tag(MMRArsTags.Items.SOURCE_INPUT).add(
        BlockRegistration.SOURCE_INPUT_HATCH_TINY.get().asItem(),
        BlockRegistration.SOURCE_INPUT_HATCH_SMALL.get().asItem(),
        BlockRegistration.SOURCE_INPUT_HATCH_NORMAL.get().asItem(),
        BlockRegistration.SOURCE_INPUT_HATCH_REINFORCED.get().asItem(),
        BlockRegistration.SOURCE_INPUT_HATCH_BIG.get().asItem(),
        BlockRegistration.SOURCE_INPUT_HATCH_HUGE.get().asItem(),
        BlockRegistration.SOURCE_INPUT_HATCH_LUDICROUS.get().asItem(),
        BlockRegistration.SOURCE_INPUT_HATCH_VACUUM.get().asItem()
    );
    tag(MMRArsTags.Items.SOURCE_OUTPUT).add(
        BlockRegistration.SOURCE_OUTPUT_HATCH_TINY.get().asItem(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_SMALL.get().asItem(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_NORMAL.get().asItem(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_REINFORCED.get().asItem(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_BIG.get().asItem(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_HUGE.get().asItem(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_LUDICROUS.get().asItem(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_VACUUM.get().asItem()
    );

    tag(MMRArsTags.Items.SOURCE)
        .addTag(MMRArsTags.Items.SOURCE_INPUT)
        .addTag(MMRArsTags.Items.SOURCE_OUTPUT);

    tag(MMRTags.Items.ALL_CASINGS)
        .addTag(MMRArsTags.Items.SOURCE_INPUT)
        .addTag(MMRArsTags.Items.SOURCE_OUTPUT);
  }
}
