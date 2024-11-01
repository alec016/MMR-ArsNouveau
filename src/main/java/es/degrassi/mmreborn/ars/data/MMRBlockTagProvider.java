package es.degrassi.mmreborn.ars.data;

import es.degrassi.mmreborn.data.MMRTags;
import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.common.registration.BlockRegistration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MMRBlockTagProvider extends BlockTagsProvider {
  public MMRBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, ModularMachineryRebornArs.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.@NotNull Provider provider) {
    tag(MMRArsTags.Blocks.SOURCE_INPUT).add(
        BlockRegistration.SOURCE_INPUT_HATCH_TINY.get(),
        BlockRegistration.SOURCE_INPUT_HATCH_SMALL.get(),
        BlockRegistration.SOURCE_INPUT_HATCH_NORMAL.get(),
        BlockRegistration.SOURCE_INPUT_HATCH_REINFORCED.get(),
        BlockRegistration.SOURCE_INPUT_HATCH_BIG.get(),
        BlockRegistration.SOURCE_INPUT_HATCH_HUGE.get(),
        BlockRegistration.SOURCE_INPUT_HATCH_LUDICROUS.get(),
        BlockRegistration.SOURCE_INPUT_HATCH_VACUUM.get()
    );
    tag(MMRArsTags.Blocks.SOURCE_OUTPUT).add(
        BlockRegistration.SOURCE_OUTPUT_HATCH_TINY.get(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_SMALL.get(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_NORMAL.get(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_REINFORCED.get(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_BIG.get(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_HUGE.get(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_LUDICROUS.get(),
        BlockRegistration.SOURCE_OUTPUT_HATCH_VACUUM.get()
    );

    tag(MMRTags.Blocks.ALL_CASINGS)
      .addTag(MMRArsTags.Blocks.SOURCE_INPUT)
      .addTag(MMRArsTags.Blocks.SOURCE_OUTPUT);
  }
}
