package es.degrassi.mmreborn.ars.common.crafting.requirement.emi;

import es.degrassi.mmreborn.api.crafting.requirement.RecipeRequirement;
import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.client.requirement.SourceRendering;
import es.degrassi.mmreborn.ars.common.crafting.requirement.RequirementSource;
import es.degrassi.mmreborn.ars.common.machine.component.SourceComponent;
import es.degrassi.mmreborn.client.requirement.ChanceRendering;
import es.degrassi.mmreborn.common.crafting.requirement.emi.EmiComponent;
import es.degrassi.mmreborn.common.machine.IOType;
import es.degrassi.mmreborn.common.util.Utils;
import lombok.Getter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public class EmiSourceComponent extends EmiComponent<Integer, RecipeRequirement<SourceComponent, RequirementSource>> implements SourceRendering, ChanceRendering {
  private int width = 14;
  private int height = 14;

  public EmiSourceComponent(RecipeRequirement<SourceComponent, RequirementSource> requirement) {
    super(requirement, 0, 0);
  }

  @Override
  public void render(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    width += 4;
    height += 4;
    super.render(guiGraphics, mouseX, mouseY);
    width -= 4;
    height -= 4;
    renderSource(guiGraphics, width, height, 2, 2);
    drawChance(guiGraphics, false);
  }

  @Override
  public int getXHighlight() {
    return 2;
  }

  @Override
  public int getYHighlight() {
    return 2;
  }

  @Override
  public List<Component> getTooltip() {
    List<Component> tooltip = super.getTooltip();
    String mode = requirement.requirement().getMode().isInput() ? "input" : "output";
    float chance = requirement.chance();
    String chanceS = Utils.decimalFormatWithPercentage(chance * 100);
    if (chance == 1)
      tooltip.add(Component.translatable("modular_machinery_reborn_ars.jei.ingredient.source." + mode, requirement.requirement().required));
    else {
      if (chance == 0)
        tooltip.add(Component.translatable("modular_machinery_reborn.ingredient.chance.not_consumed"));
      else if (chance > 0 && chance < 1)
        tooltip.add(Component.translatable("modular_machinery_reborn.ingredient.chance." + mode, chanceS, ""));
      tooltip.add(Component.translatable("modular_machinery_reborn_ars.jei.ingredient.source.amount", requirement.requirement().required));
    }
    return tooltip;
  }

  @Override
  public List<Integer> ingredients() {
    return Lists.newArrayList(List.of(requirement.requirement().required).iterator());
  }

  @Override
  @NotNull
  public ResourceLocation texture() {
    return ModularMachineryRebornArs.rl("textures/gui/jeirecipeicons.png");
  }

  @Override
  public float getChance() {
    return requirement.chance();
  }

  @Override
  public IOType getActionType() {
    return requirement.requirement().getMode();
  }
}
