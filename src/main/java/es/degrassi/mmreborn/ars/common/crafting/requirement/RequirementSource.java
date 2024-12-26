package es.degrassi.mmreborn.ars.common.crafting.requirement;

import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.common.capability.SourceStorage;
import es.degrassi.mmreborn.ModularMachineryReborn;
import es.degrassi.mmreborn.api.codec.NamedCodec;
import es.degrassi.mmreborn.ars.common.crafting.helper.RestrictionSource;
import es.degrassi.mmreborn.ars.common.machine.SourceHatch;
import es.degrassi.mmreborn.ars.common.registration.ComponentRegistration;
import es.degrassi.mmreborn.ars.common.registration.RequirementTypeRegistration;
import es.degrassi.mmreborn.common.crafting.helper.ComponentOutputRestrictor;
import es.degrassi.mmreborn.common.crafting.helper.ComponentRequirement;
import es.degrassi.mmreborn.common.crafting.helper.CraftCheck;
import es.degrassi.mmreborn.common.crafting.helper.ProcessingComponent;
import es.degrassi.mmreborn.common.crafting.helper.RecipeCraftingContext;
import es.degrassi.mmreborn.common.crafting.requirement.PositionedRequirement;
import es.degrassi.mmreborn.common.crafting.requirement.RequirementType;
import es.degrassi.mmreborn.common.machine.IOType;
import es.degrassi.mmreborn.common.machine.MachineComponent;
import es.degrassi.mmreborn.common.crafting.modifier.RecipeModifier;
import es.degrassi.mmreborn.common.util.ResultChance;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class RequirementSource extends ComponentRequirement<Integer, RequirementSource> implements ComponentRequirement.ChancedRequirement {
  public static final NamedCodec<RequirementSource> CODEC = NamedCodec.record(instance -> instance.group(
      NamedCodec.intRange(0, Integer.MAX_VALUE).fieldOf("source").forGetter(req -> req.required),
      NamedCodec.enumCodec(IOType.class).fieldOf("mode").forGetter(ComponentRequirement::getActionType),
      NamedCodec.floatRange(0, 1).optionalFieldOf("chance", 1f).forGetter(req -> req.chance),
      PositionedRequirement.POSITION_CODEC.optionalFieldOf("position", new PositionedRequirement(0, 0)).forGetter(ComponentRequirement::getPosition)
  ).apply(instance, (source, mode, chance, position) -> {
    RequirementSource requirementSource = new RequirementSource(mode, source, position);
    requirementSource.setChance(chance);
    return requirementSource;
  }), "SourceRequirement");

  public static final int PRIORITY_WEIGHT_SOURCE = 10_000_000;
  public final Integer required;
  @Getter
  public float chance = 1F;

  private Integer requirementCheck;
  private boolean doesntConsumeInput;

  public RequirementSource(IOType mode, Integer source, PositionedRequirement position) {
    this(RequirementTypeRegistration.SOURCE.get(), mode, source, position);
  }

  public RequirementSource(RequirementType<RequirementSource> type, IOType mode, Integer source, PositionedRequirement position) {
    super(type, mode, position);
    this.required = source;
  }

  public int getSortingWeight() {
    return PRIORITY_WEIGHT_SOURCE;
  }

  @Override
  public RequirementSource deepCopy() {
    return new RequirementSource(getActionType(), required, getPosition());
  }

  @Override
  public RequirementSource deepCopyModified(List<RecipeModifier> modifiers) {
    int amount = Math.round(RecipeModifier.applyModifiers(modifiers, this, this.required, false));
    RequirementSource fluid = new RequirementSource(this.getActionType(), amount, getPosition());

    fluid.chance = RecipeModifier.applyModifiers(modifiers, this, this.chance, true);
    return fluid;
  }

  @Override
  public void setChance(float chance) {
    this.chance = chance;
  }

  @Override
  public JsonObject asJson() {
    JsonObject json = super.asJson();
    json.addProperty("type", ModularMachineryReborn.rl("source").toString());
    json.addProperty("source", required);
    json.addProperty("chance", chance);
    return json;
  }

  @Override
  public void startRequirementCheck(ResultChance contextChance, RecipeCraftingContext context) {
    this.requirementCheck = this.required;
    this.requirementCheck = Math.round(RecipeModifier.applyModifiers(context, this, this.required, false));
    this.doesntConsumeInput = contextChance.canProduce(RecipeModifier.applyModifiers(context, this, this.chance, true));
  }

  @Override
  public void endRequirementCheck() {
    this.requirementCheck = required;
    this.doesntConsumeInput = true;
  }

  @Override
  public @NotNull String getMissingComponentErrorMessage(IOType ioType) {
    return String.format("component.missing.source.%s", ioType.name().toLowerCase(Locale.ROOT));
  }

  @Override
  public boolean isValidComponent(ProcessingComponent<?> component, RecipeCraftingContext ctx) {
    MachineComponent<?> cmp = component.component();
    return (cmp.getComponentType().equals(ComponentRegistration.COMPONENT_SOURCE.get())) &&
        cmp instanceof SourceHatch &&
        cmp.getIOType() == getActionType();
  }

  @Override
  public @NotNull CraftCheck canStartCrafting(ProcessingComponent<?> component, RecipeCraftingContext context,
                                              List<ComponentOutputRestrictor<?>> restrictions) {
    SourceStorage handler = (SourceStorage) component.providedComponent();
    return switch (getActionType()) {
      case INPUT -> {
        if (handler.extractSource(this.requirementCheck, true) < this.requirementCheck)
          yield CraftCheck.failure("craftcheck.failure.source.input");
        int extracted = handler.extractSource(this.requirementCheck, true);
        this.requirementCheck -= extracted;
        if (this.requirementCheck <= 0)
          yield CraftCheck.success();
        yield CraftCheck.failure("craftcheck.failure.source.input");
      }
      case OUTPUT -> {
        for (ComponentOutputRestrictor<?> restrictor : restrictions) {
          if (restrictor instanceof RestrictionSource tank) {

            if (tank.exactComponent.equals(component)) {
              handler.receiveSource(Objects.requireNonNull(tank.inserted == null ? null : tank.inserted), true);
            }
          }
        }

        int filled = handler.receiveSource(requirementCheck, true);
        boolean didFill = filled >= this.requirementCheck;
        if (didFill) {
          context.addRestriction(new RestrictionSource(this.requirementCheck, component));
          yield CraftCheck.success();
        }
        yield CraftCheck.failure("craftcheck.failure.source.output.space");
      }
    };
  }

  @Override
  public boolean startCrafting(ProcessingComponent<?> component, RecipeCraftingContext context, ResultChance chance) {
    SourceStorage handler = (SourceStorage) component.providedComponent();
    if (Objects.requireNonNull(getActionType()).isInput()) {
      int drainedSimulated = handler.extractSource(requirementCheck, true);
      if (doesntConsumeInput) {
        this.requirementCheck = Math.max(requirementCheck - drainedSimulated, 0);
        return this.requirementCheck <= 0;
      }
      int actualDrained = handler.extractSource(requirementCheck, false);
      this.requirementCheck = Math.max(requirementCheck - actualDrained, 0);
      return this.requirementCheck <= 0;
    }
    return false;
  }

  @Override
  public @NotNull CraftCheck finishCrafting(ProcessingComponent<?> component, RecipeCraftingContext context, ResultChance chance) {
    SourceStorage handler = (SourceStorage) component.providedComponent();
    if (!Objects.requireNonNull(getActionType()).isInput()) {
      int fillableAmount = handler.receiveSource(requirementCheck, true);
      if (chance.canProduce(RecipeModifier.applyModifiers(context, this, this.chance, true))) {
        if (fillableAmount >= this.requirementCheck)
          return CraftCheck.success();
        return CraftCheck.failure("craftcheck.failure.source.output.space");
      }
      if (fillableAmount >= this.requirementCheck && handler.receiveSource(requirementCheck, false) >= requirementCheck)
        return CraftCheck.success();
      return CraftCheck.failure("craftcheck.failure.source.output.space");
    }
    return CraftCheck.skipComponent();
  }
}
