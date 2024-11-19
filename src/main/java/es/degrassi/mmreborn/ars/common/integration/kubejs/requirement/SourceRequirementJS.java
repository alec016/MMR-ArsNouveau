package es.degrassi.mmreborn.ars.common.integration.kubejs.requirement;

import es.degrassi.mmreborn.ars.common.crafting.requirement.RequirementSource;
import es.degrassi.mmreborn.common.crafting.requirement.jei.JeiPositionedRequirement;
import es.degrassi.mmreborn.common.integration.kubejs.MachineRecipeBuilderJS;
import es.degrassi.mmreborn.common.integration.kubejs.RecipeJSBuilder;
import es.degrassi.mmreborn.common.machine.IOType;

public interface SourceRequirementJS extends RecipeJSBuilder {

  default MachineRecipeBuilderJS requireSource(Integer stack, int x, int y) {
    return addRequirement(new RequirementSource(IOType.INPUT, stack, new JeiPositionedRequirement(x, y)));
  }

  default MachineRecipeBuilderJS produceSource(Integer stack, int x, int y) {
    return addRequirement(new RequirementSource(IOType.OUTPUT, stack, new JeiPositionedRequirement(x, y)));
  }

  default MachineRecipeBuilderJS requireSource(Integer stack) {
    return requireSource(stack, 0, 0);
  }

  default MachineRecipeBuilderJS produceSource(Integer stack) {
    return produceSource(stack, 0, 0);
  }
}
