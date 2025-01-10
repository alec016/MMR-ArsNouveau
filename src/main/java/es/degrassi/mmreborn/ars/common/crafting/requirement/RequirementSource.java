package es.degrassi.mmreborn.ars.common.crafting.requirement;

import com.hollingsworth.arsnouveau.common.capability.SourceStorage;
import es.degrassi.mmreborn.api.codec.NamedCodec;
import es.degrassi.mmreborn.api.crafting.CraftingResult;
import es.degrassi.mmreborn.api.crafting.ICraftingContext;
import es.degrassi.mmreborn.api.crafting.requirement.IRequirement;
import es.degrassi.mmreborn.api.crafting.requirement.IRequirementList;
import es.degrassi.mmreborn.ars.common.machine.component.SourceComponent;
import es.degrassi.mmreborn.ars.common.registration.ComponentRegistration;
import es.degrassi.mmreborn.ars.common.registration.RequirementTypeRegistration;
import es.degrassi.mmreborn.common.crafting.ComponentType;
import es.degrassi.mmreborn.common.crafting.modifier.RecipeModifier;
import es.degrassi.mmreborn.common.crafting.requirement.PositionedRequirement;
import es.degrassi.mmreborn.common.crafting.requirement.RequirementType;
import es.degrassi.mmreborn.common.machine.IOType;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class RequirementSource implements IRequirement<SourceComponent> {
  public static final NamedCodec<RequirementSource> CODEC = NamedCodec.record(instance -> instance.group(
      NamedCodec.intRange(0, Integer.MAX_VALUE).fieldOf("source").forGetter(req -> req.required),
      NamedCodec.enumCodec(IOType.class).fieldOf("mode").forGetter(IRequirement::getMode),
      PositionedRequirement.POSITION_CODEC.optionalFieldOf("position", new PositionedRequirement(0, 0)).forGetter(IRequirement::getPosition)
  ).apply(instance, (source, mode, position) -> new RequirementSource(mode, source, position)), "SourceRequirement");

  public final Integer required;
  @Getter
  private final IOType mode;
  @Getter
  private final PositionedRequirement position;

  public RequirementSource(IOType mode, Integer source, PositionedRequirement position) {
    this.mode = mode;
    this.required = source;
    this.position = position;
  }

  @Override
  public RequirementType<RequirementSource> getType() {
    return RequirementTypeRegistration.SOURCE.get();
  }

  @Override
  public ComponentType getComponentType() {
    return ComponentRegistration.COMPONENT_SOURCE.get();
  }

  @Override
  public boolean test(SourceComponent component, ICraftingContext context) {
    SourceStorage handler = component.getContainerProvider();
    if (handler == null) return false;
    int amount = (int) context.getModifiedValue(required, this);
    return switch (mode) {
      case INPUT -> handler.extractSource(amount, true) >= amount;
      case OUTPUT -> handler.receiveSource(amount, true) >= amount;
    };
  }

  @Override
  public void gatherRequirements(IRequirementList<SourceComponent> list) {
    switch (getMode()) {
      case INPUT -> list.processOnStart(this::processInput);
      case OUTPUT -> list.processOnEnd(this::processOutput);
    }
  }

  private CraftingResult processOutput(SourceComponent component, ICraftingContext context) {
    SourceStorage handler = component.getContainerProvider();
    int amount = (int) context.getModifiedValue(required, this);
    int remaining = handler.receiveSource(amount, true);
    if (remaining >= amount) {
      handler.receiveSource(amount, false);
      return CraftingResult.success();
    }
    return CraftingResult.error(Component.translatable(
        "craftcheck.failure.source.output", amount,
        component.getContainerProvider().getSourceCapacity() - component.getContainerProvider().getSource()
    ));
  }

  private CraftingResult processInput(SourceComponent component, ICraftingContext context) {
    int amount = (int) context.getModifiedValue(required, this);
    int canExtract = component.getContainerProvider().extractSource(amount, true);
    if (canExtract >= amount) {
      component.getContainerProvider().extractSource(amount, false);
      return CraftingResult.success();
    }
    return CraftingResult.error(Component.translatable(
        "craftcheck.failure.source.input", amount, component.getContainerProvider().getSource()
    ));
  }

  @Override
  public RequirementSource deepCopyModified(List<RecipeModifier> modifiers) {
    int amount = Math.round(RecipeModifier.applyModifiers(modifiers, this.getType(), getMode(), this.required, false));
    return new RequirementSource(this.getMode(), amount, getPosition());
  }

  @Override
  public RequirementSource deepCopy() {
    return new RequirementSource(getMode(), required, getPosition());
  }

  @Override
  public @NotNull Component getMissingComponentErrorMessage(IOType ioType) {
    return Component.translatable(String.format("component.missing.source.%s", ioType.name().toLowerCase(Locale.ROOT)));
  }

  @Override
  public boolean isComponentValid(SourceComponent sourceComponent, ICraftingContext iCraftingContext) {
    return getMode().equals(sourceComponent.getIOType());
  }
}
