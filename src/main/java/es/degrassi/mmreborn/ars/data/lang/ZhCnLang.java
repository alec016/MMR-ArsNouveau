package es.degrassi.mmreborn.ars.data.lang;

import es.degrassi.mmreborn.ars.common.registration.BlockRegistration;
import es.degrassi.mmreborn.ars.data.MMRArsTags;

public final class ZhCnLang extends Lang {

  @Override
  protected void addTags() {
    MMRArsTags.getAllTags().forEach(tag -> add(tag.getFirst(), tag.getSecond()));
  }

  @Override
  protected void addItemGroups() {
    add("itemGroup." + mma("group"), "模块化机械:重制版 新生魔艺附属");
  }

  @Override
  protected void addGuiTitles() {
    add(mm(gui("title.source_hatch")), "魔源仓");
    add(mma("connections.send"), "已设定机器将魔源传输到 %s");
    add(mma("connections.take"), "已设定机器从 %s 抽取魔源");
    add(mma("connections.fail"), "距离过远.");
    add(mma("relay.no_from"), "未设定魔源传输目标位置.");
    add(mma("relay.no_to"), "未设定魔源抽取位置.");
    add(mma("relay.one_from"), "正在从 %d 个位置抽取魔源.");
    add(mma("relay.one_to"), "正在传输魔源至 %d 个位置");
  }

  @Override
  protected void addCraftcheck() {
    add(craftCheck("source.input"), "缺少魔源输入, 需要 %s 魔源,但找到 %s 魔源！");
    add(craftCheck("source.output"), "没有足够的空间用于魔源输出, 需要: %s 魔源,但找到 %s 魔源空余！");
  }

  @Override
  protected void addTooltips() {
    add(tooltip("sourcehatch.empty"), "空");
    add(tooltip("sourcehatch.storage"), "最大存储 %s 魔源");
    add(tooltip("sourcehatch.charge"), "%s / %s 魔源");
  }

  @Override
  protected void addComponents() {
    add(missingComponent("source.output"), "未找到魔源输出仓！");
    add(missingComponent("source.input"), "未找到魔源输出仓");
  }

  @Override
  protected void addIngredients() {
    add(jeiIngredient("source.input"), "需要: %s 魔源");
    add(jeiIngredient("source.output"), "产出: %s 魔源");
    add(jeiIngredient("source.amount"), "%s 魔源");
  }

  @Override
  protected void addBlocks() {
    addBlock(BlockRegistration.SOURCE_INPUT_HATCH_TINY, "微型魔源输入仓");
    addBlock(BlockRegistration.SOURCE_INPUT_HATCH_SMALL, "小型魔源输入仓");
    addBlock(BlockRegistration.SOURCE_INPUT_HATCH_NORMAL, "中型魔源输入仓");
    addBlock(BlockRegistration.SOURCE_INPUT_HATCH_REINFORCED, "强化魔源输入仓");
    addBlock(BlockRegistration.SOURCE_INPUT_HATCH_BIG, "大型魔源输入仓");
    addBlock(BlockRegistration.SOURCE_INPUT_HATCH_HUGE, "巨型魔源输入仓");
    addBlock(BlockRegistration.SOURCE_INPUT_HATCH_LUDICROUS, "超级魔源输入仓");
    addBlock(BlockRegistration.SOURCE_INPUT_HATCH_VACUUM, "真空魔源输入仓");

    addBlock(BlockRegistration.SOURCE_OUTPUT_HATCH_TINY, "微型魔源输出仓");
    addBlock(BlockRegistration.SOURCE_OUTPUT_HATCH_SMALL, "小型魔源输出仓");
    addBlock(BlockRegistration.SOURCE_OUTPUT_HATCH_NORMAL, "中型魔源输出仓");
    addBlock(BlockRegistration.SOURCE_OUTPUT_HATCH_REINFORCED, "强化魔源输出仓");
    addBlock(BlockRegistration.SOURCE_OUTPUT_HATCH_BIG, "大型魔源输出仓");
    addBlock(BlockRegistration.SOURCE_OUTPUT_HATCH_HUGE, "巨型魔源输出仓");
    addBlock(BlockRegistration.SOURCE_OUTPUT_HATCH_LUDICROUS, "超级魔源输出仓");
    addBlock(BlockRegistration.SOURCE_OUTPUT_HATCH_VACUUM, "真空魔源输出仓");
  }
}
