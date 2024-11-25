package es.degrassi.mmreborn.ars.common.crafting.requirement.jei;

import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.client.util.SourceRenderer;
import es.degrassi.mmreborn.ars.common.crafting.requirement.RequirementSource;
import es.degrassi.mmreborn.ars.client.requirement.SourceRendering;
import es.degrassi.mmreborn.common.crafting.MachineRecipe;
import es.degrassi.mmreborn.common.crafting.requirement.jei.JeiComponent;
import es.degrassi.mmreborn.common.integration.jei.category.MMRRecipeCategory;
import es.degrassi.mmreborn.common.integration.jei.ingredient.CustomIngredientTypes;
import es.degrassi.mmreborn.common.util.TextureSizeHelper;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.TooltipFlag;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JeiSourceComponent extends JeiComponent<Integer, RequirementSource> implements SourceRendering {
  private int width = 14;
  private int height = 14;

  public JeiSourceComponent(RequirementSource requirement) {
    super(requirement, 0, 0);
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public ResourceLocation texture() {
    return ModularMachineryRebornArs.rl("textures/gui/jeirecipeicons.png");
  }

  @Override
  public void render(GuiGraphics guiGraphics, Integer ingredient) {
    width += 4;
    height += 4;
    guiGraphics.blit(this.texture(), -2, -2, 0, (float) this.uOffset, (float) this.vOffset, this.getWidth(), this.getHeight(), TextureSizeHelper.getWidth(this.texture()), TextureSizeHelper.getHeight(this.texture()));
    width -= 4;
    height -= 4;
    renderSource(guiGraphics, width, height);
  }

  @Override
  public List<Integer> ingredients() {
    return Lists.newArrayList(List.of(requirement.required).iterator());
  }

  @Override
  @SuppressWarnings("removal")
  public @NotNull List<Component> getTooltip(@NotNull Integer ingredient, @NotNull TooltipFlag tooltipFlag) {
    List<Component> tooltip = super.getTooltip(ingredient, tooltipFlag);
    String mode = requirement.getActionType().isInput() ? "input" : "output";
    tooltip.add(Component.translatable("modular_machinery_reborn_ars.jei.ingredient.source." + mode, ingredient));
    return tooltip;
  }

  @Override
  public void setRecipe(MMRRecipeCategory category, IRecipeLayoutBuilder builder, MachineRecipe recipe, IFocusGroup focuses) {
    builder
        .addSlot(RecipeIngredientRole.RENDER_ONLY, getPosition().x() + 1, getPosition().y() + 1)
        .setCustomRenderer(CustomIngredientTypes.SOURCE, this)
        .addIngredients(CustomIngredientTypes.SOURCE, ingredients());
  }
}
