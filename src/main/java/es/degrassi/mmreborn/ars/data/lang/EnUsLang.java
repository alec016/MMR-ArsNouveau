package es.degrassi.mmreborn.ars.data.lang;

import es.degrassi.mmreborn.ars.common.registration.BlockRegistration;
import es.degrassi.mmreborn.ars.data.MMRArsTags;

public final class EnUsLang extends Lang {

  @Override
  protected void addTags() {
    MMRArsTags.getAllTags().forEach(tag -> add(tag.getFirst(), tag.getSecond()));
  }

  @Override
  protected void addItemGroups() {
    add("itemGroup." + mma("group"), "Modular Machinery Reborn Ars");
  }

  @Override
  protected void addGuiTitles() {
    add(mm(gui("title.source_hatch")), "Source Hatch");
    add(mma("connections.send"), "Machine set to send to %s");
    add(mma("connections.take"), "Machine set to take form %s");
    add(mma("connections.fail"), "Too far away.");
    add(mma("relay.no_from"), "No take location set.");
    add(mma("relay.no_to"), "No send location set.");
    add(mma("relay.one_from"), "Taking from %d location(s).");
    add(mma("relay.one_to"), "Sending to %d location(s).");
  }

  @Override
  protected void addCraftcheck() {
    add(craftCheck("source.input"), "Not enough source!, %s needed but found %s");
    add(craftCheck("source.output"), "Not enough space for source output!, needed %s, but found %s space");
  }

  @Override
  protected void addTooltips() {
    add(tooltip("sourcehatch.empty"), "Empty");
    add(tooltip("sourcehatch.storage"), "Stores %s source");
    add(tooltip("sourcehatch.charge"), "%s / %s source");
  }

  @Override
  protected void addComponents() {
    add(missingComponent("source.output"), "No Source Output Hatch found!");
    add(missingComponent("source.input"), "No Source Input Hatch found!");
  }

  @Override
  protected void addIngredients() {
    add(jeiIngredient("source.input"), "Require %s source");
    add(jeiIngredient("source.output"), "Produce %s source");
    add(jeiIngredient("source.amount"), "%s source");
  }

  @Override
  protected void addBlocks() {
    addBlock(BlockRegistration.SOURCE_INPUT_HATCH_TINY, "Tiny Source Input Hatch");
    addBlock(BlockRegistration.SOURCE_INPUT_HATCH_SMALL, "Small Source Input Hatch");
    addBlock(BlockRegistration.SOURCE_INPUT_HATCH_NORMAL, "Normal Source Input Hatch");
    addBlock(BlockRegistration.SOURCE_INPUT_HATCH_REINFORCED, "Reinforced Source Input Hatch");
    addBlock(BlockRegistration.SOURCE_INPUT_HATCH_BIG, "Big Source Input Hatch");
    addBlock(BlockRegistration.SOURCE_INPUT_HATCH_HUGE, "Huge Source Input Hatch");
    addBlock(BlockRegistration.SOURCE_INPUT_HATCH_LUDICROUS, "Ludicrous Source Input Hatch");
    addBlock(BlockRegistration.SOURCE_INPUT_HATCH_VACUUM, "Vacuum Source Input Hatch");

    addBlock(BlockRegistration.SOURCE_OUTPUT_HATCH_TINY, "Tiny Source Output Hatch");
    addBlock(BlockRegistration.SOURCE_OUTPUT_HATCH_SMALL, "Small Source Output Hatch");
    addBlock(BlockRegistration.SOURCE_OUTPUT_HATCH_NORMAL, "Normal Source Output Hatch");
    addBlock(BlockRegistration.SOURCE_OUTPUT_HATCH_REINFORCED, "Reinforced Source Output Hatch");
    addBlock(BlockRegistration.SOURCE_OUTPUT_HATCH_BIG, "Big Source Output Hatch");
    addBlock(BlockRegistration.SOURCE_OUTPUT_HATCH_HUGE, "Huge Source Output Hatch");
    addBlock(BlockRegistration.SOURCE_OUTPUT_HATCH_LUDICROUS, "Ludicrous Source Output Hatch");
    addBlock(BlockRegistration.SOURCE_OUTPUT_HATCH_VACUUM, "Vacuum Source Output Hatch");
  }
}
