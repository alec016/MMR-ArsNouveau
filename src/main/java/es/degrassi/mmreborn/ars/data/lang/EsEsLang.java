package es.degrassi.mmreborn.ars.data.lang;

import es.degrassi.mmreborn.ars.common.registration.BlockRegistration;
import es.degrassi.mmreborn.ars.data.MMRArsTags;

public final class EsEsLang extends Lang {

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
    add(mma("connections.send"), "Máquina puesta a enviar a %s");
    add(mma("connections.take"), "Máquina puesta a extraer de %s");
    add(mma("connections.fail"), "Demasiado lejos.");
    add(mma("relay.no_from"), "Sin posición de extracción.");
    add(mma("relay.no_to"), "Sin posición de envío.");
    add(mma("relay.one_from"), "Extrayendo de %d localizacion(es).");
    add(mma("relay.one_to"), "Enviando a %d localizacion(es).");
  }

  @Override
  protected void addCraftcheck() {
    add(craftCheck("source.input"), "No hay suficiente source!, %s necesario pero se encontró %s source");
    add(craftCheck("source.output"), "No hay suficiente espacio para la salida de source!, necesario: %s, pero se encontró %s");
  }

  @Override
  protected void addTooltips() {
    add(tooltip("sourcehatch.empty"), "Vacío");
    add(tooltip("sourcehatch.storage"), "Almacena %s source");
    add(tooltip("sourcehatch.charge"), "%s / %s source");
  }

  @Override
  protected void addComponents() {
    add(missingComponent("source.output"), "No se ha encontrado Source Output Hatch!");
    add(missingComponent("source.input"), "No se ha encontrado Source Input Hatch!");
  }

  @Override
  protected void addIngredients() {
    add(jeiIngredient("source.input"), "Requiere %s source");
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
