package es.degrassi.mmreborn.ars.mixin;

import es.degrassi.mmreborn.ars.common.integration.kubejs.requirement.SourceRequirementJS;
import es.degrassi.mmreborn.common.integration.kubejs.MachineRecipeBuilderJS;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ MachineRecipeBuilderJS.class })
public abstract class KubeJSIntegrationMixin implements SourceRequirementJS {
}
