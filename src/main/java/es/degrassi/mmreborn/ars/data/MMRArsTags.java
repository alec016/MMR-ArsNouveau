package es.degrassi.mmreborn.ars.data;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Locale;

public class MMRArsTags {
  private static TagKey<Block> blockTag(String name, boolean isNeoForge) {
    return BlockTags.create(isNeoForge ? ResourceLocation.fromNamespaceAndPath("c", name) : ModularMachineryRebornArs.rl(name));
  }

  private static TagKey<Item> itemTag(String name, boolean isNeoForge) {
    return ItemTags.create(isNeoForge ? ResourceLocation.fromNamespaceAndPath("c", name) : ModularMachineryRebornArs.rl(name));
  }

  public static List<Pair<TagKey<?>, String>> getAllTags() {
    return MMRArsTags.Tag.tags.stream().filter(pair -> pair.getFirst().location().getNamespace().equals(ModularMachineryRebornArs.MODID)).toList();
  }

  private static class Tag<T> {
    private static final List<Pair<TagKey<?>, String>> tags = Lists.newArrayList();
    private final TagKey<T> tag;
    protected Tag(TagKey<T> tag, String engTranslation) {
      this.tag = tag;
      tags.add(Pair.of(tag, engTranslation));
    }

    public TagKey<T> get() {
      return tag;
    }
  }

  public static class Blocks extends Tag<Block> {
    public static final TagKey<Block> SOURCE = new Blocks(false, "sourcehatch", "Source Hatches").get();
    public static final TagKey<Block> SOURCE_INPUT = new Blocks(false, "sourceinputhatch", "Source Input Hatches").get();
    public static final TagKey<Block> SOURCE_OUTPUT = new Blocks(false, "sourceoutputhatch", "Source Output Hatches").get();

    private Blocks(boolean isNeoForge, String name) {
      this(isNeoForge, name, capitalize(name));
    }

    private Blocks(boolean isNeoForge, String name, String enTranslation) {
      super(blockTag(name, isNeoForge), enTranslation);
    }
  }

  public static class Items extends Tag<Item> {
    public static final TagKey<Item> SOURCE = new Items(false, "sourcehatch", "Source Hatches").get();
    public static final TagKey<Item> SOURCE_INPUT = new Items(false, "sourceinputhatch", "Source Input Hatches").get();
    public static final TagKey<Item> SOURCE_OUTPUT = new Items(false, "sourceoutputhatch", "Source Output Hatches").get();

    private Items(boolean isNeoForge, String name) {
      this(isNeoForge, name, capitalize(name));
    }
    private Items(boolean isNeoForge, String name, String enTranslation) {
      super(itemTag(name, isNeoForge), enTranslation);
    }
  }

  private static String capitalize(String toCapitalize) {
    if (toCapitalize.trim().isEmpty()) return toCapitalize;
    String[] splitted = toCapitalize.split("_");
    StringBuilder builder = new StringBuilder();
    for (var part : splitted) {
      if (part.trim().isEmpty()) continue;
      if (part.trim().length() == 1) {
        builder.append(part.trim().toUpperCase(Locale.ENGLISH))
            .append(" ");
        continue;
      }
      String first = (part.trim().charAt(0) + "").toUpperCase(Locale.ENGLISH);
      builder.append(first)
          .append(part.substring(1))
          .append(" ");
    }
    return builder.toString().trim();
  }
}
