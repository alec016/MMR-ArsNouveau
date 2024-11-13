package es.degrassi.mmreborn.ars.common.crafting.helper;

import es.degrassi.mmreborn.common.crafting.helper.ComponentOutputRestrictor;
import es.degrassi.mmreborn.common.crafting.helper.ProcessingComponent;

public class RestrictionSource extends ComponentOutputRestrictor<Integer>{

  public RestrictionSource(Integer inserted, ProcessingComponent<?> exactComponent) {
    super(inserted, exactComponent);
  }
}
