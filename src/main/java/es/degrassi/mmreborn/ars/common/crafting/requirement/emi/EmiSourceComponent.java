package es.degrassi.mmreborn.ars.common.crafting.requirement.emi;

import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.client.requirement.SourceRendering;
import es.degrassi.mmreborn.ars.common.crafting.requirement.RequirementSource;
import es.degrassi.mmreborn.common.crafting.requirement.emi.EmiComponent;
import es.degrassi.mmreborn.common.util.Utils;
import lombok.Getter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public class EmiSourceComponent extends EmiComponent<Integer, RequirementSource> implements SourceRendering {
  private int width = 14;
  private int height = 14;

  public EmiSourceComponent(RequirementSource requirement) {
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
    String mode = requirement.getActionType().isInput() ? "input" : "output";
    float chance = requirement.chance;
    String chanceS = Utils.decimalFormatWithPercentage(chance * 100);
    if (chance == 1)
      tooltip.add(Component.translatable("modular_machinery_reborn_ars.jei.ingredient.source." + mode, requirement.required));
    else {
      if (chance == 0)
        tooltip.add(Component.translatable("modular_machinery_reborn.ingredient.chance.not_consumed"));
      else if (chance > 0 && chance < 1)
        tooltip.add(Component.translatable("modular_machinery_reborn.ingredient.chance." + mode, chanceS, ""));
      tooltip.add(Component.translatable("modular_machinery_reborn_ars.jei.ingredient.source.amount", requirement.required));
    }
    return tooltip;
  }

  @Override
  public List<Integer> ingredients() {
    return Lists.newArrayList(List.of(requirement.required).iterator());
  }

  @Override
  @NotNull
  public ResourceLocation texture() {
    return ModularMachineryRebornArs.rl("textures/gui/jeirecipeicons.png");
  }
}
