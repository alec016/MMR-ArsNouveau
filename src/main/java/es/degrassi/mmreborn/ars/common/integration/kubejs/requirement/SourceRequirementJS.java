package es.degrassi.mmreborn.ars.common.integration.kubejs.requirement;

import es.degrassi.mmreborn.ars.common.crafting.requirement.RequirementSource;
import es.degrassi.mmreborn.common.integration.kubejs.MachineRecipeBuilderJS;
import es.degrassi.mmreborn.common.integration.kubejs.RecipeJSBuilder;
import es.degrassi.mmreborn.common.machine.IOType;

public interface SourceRequirementJS extends RecipeJSBuilder {

  default MachineRecipeBuilderJS requireSource(Integer stack) {
    return addRequirement(new RequirementSource(IOType.INPUT, stack));
  }

  default MachineRecipeBuilderJS produceSource(Integer stack) {
    return addRequirement(new RequirementSource(IOType.OUTPUT, stack));
  }
}
