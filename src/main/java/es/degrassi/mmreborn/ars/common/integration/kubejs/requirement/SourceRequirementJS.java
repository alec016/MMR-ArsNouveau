package es.degrassi.mmreborn.ars.common.integration.kubejs.requirement;

import es.degrassi.mmreborn.ars.common.crafting.requirement.RequirementSource;
import es.degrassi.mmreborn.common.crafting.requirement.jei.IJeiRequirement;
import es.degrassi.mmreborn.common.integration.kubejs.MachineRecipeBuilderJS;
import es.degrassi.mmreborn.common.integration.kubejs.RecipeJSBuilder;
import es.degrassi.mmreborn.common.machine.IOType;

public interface SourceRequirementJS extends RecipeJSBuilder {

  default MachineRecipeBuilderJS requireSource(Integer stack, int x, int y) {
    return addRequirement(new RequirementSource(IOType.INPUT, stack, new IJeiRequirement.JeiPositionedRequirement(x, y)));
  }

  default MachineRecipeBuilderJS produceSource(Integer stack, int x, int y) {
    return addRequirement(new RequirementSource(IOType.OUTPUT, stack, new IJeiRequirement.JeiPositionedRequirement(x, y)));
  }
}
