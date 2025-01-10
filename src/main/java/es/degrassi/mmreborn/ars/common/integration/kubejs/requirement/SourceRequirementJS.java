package es.degrassi.mmreborn.ars.common.integration.kubejs.requirement;

import es.degrassi.mmreborn.api.crafting.requirement.RecipeRequirement;
import es.degrassi.mmreborn.ars.common.crafting.requirement.RequirementSource;
import es.degrassi.mmreborn.common.crafting.requirement.PositionedRequirement;
import es.degrassi.mmreborn.common.integration.kubejs.MachineRecipeBuilderJS;
import es.degrassi.mmreborn.common.integration.kubejs.RecipeJSBuilder;
import es.degrassi.mmreborn.common.machine.IOType;

public interface SourceRequirementJS extends RecipeJSBuilder {

  default MachineRecipeBuilderJS requireSource(Integer stack, float chance, int x, int y) {
    RequirementSource requirement = new RequirementSource(IOType.INPUT, stack, new PositionedRequirement(x, y));
    return addRequirement(new RecipeRequirement<>(requirement, chance));
  }

  default MachineRecipeBuilderJS produceSource(Integer stack, float chance, int x, int y) {
    RequirementSource requirement = new RequirementSource(IOType.OUTPUT, stack, new PositionedRequirement(x, y));
    return addRequirement(new RecipeRequirement<>(requirement, chance));
  }

  default MachineRecipeBuilderJS requireSource(Integer stack, float chance) {
    return requireSource(stack, chance, 0, 0);
  }

  default MachineRecipeBuilderJS produceSource(Integer stack, float chance) {
    return produceSource(stack, chance, 0, 0);
  }

  default MachineRecipeBuilderJS requireSource(Integer stack, int x, int y) {
    return requireSource(stack, 1, x, y);
  }

  default MachineRecipeBuilderJS produceSource(Integer stack, int x, int y) {
    return produceSource(stack, 1, x, y);
  }

  default MachineRecipeBuilderJS requireSource(Integer stack) {
    return requireSource(stack, 1, 0, 0);
  }

  default MachineRecipeBuilderJS produceSource(Integer stack) {
    return produceSource(stack, 1, 0, 0);
  }
}
