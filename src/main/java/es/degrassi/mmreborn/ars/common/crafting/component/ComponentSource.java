package es.degrassi.mmreborn.ars.common.crafting.component;

import es.degrassi.mmreborn.common.crafting.ComponentType;

import javax.annotation.Nullable;

public class ComponentSource extends ComponentType {

  @Nullable
  @Override
  public String requiresModid() {
    return "ars_nouveau";
  }

}