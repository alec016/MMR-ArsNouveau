package es.degrassi.mmreborn.ars.common.crafting.helper;

import es.degrassi.mmreborn.common.crafting.helper.ComponentOutputRestrictor;
import es.degrassi.mmreborn.common.crafting.helper.ProcessingComponent;

public class RestrictionSource extends ComponentOutputRestrictor {
  public final Integer inserted;
  public final ProcessingComponent<?> exactComponent;

  public RestrictionSource(Integer inserted, ProcessingComponent<?> exactComponent) {
    this.inserted = inserted;
    this.exactComponent = exactComponent;
  }
}
