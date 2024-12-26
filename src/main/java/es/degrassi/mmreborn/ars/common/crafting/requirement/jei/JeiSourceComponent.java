package es.degrassi.mmreborn.ars.common.crafting.requirement.jei;

import com.mojang.datafixers.util.Pair;
import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.client.requirement.SourceRendering;
import es.degrassi.mmreborn.ars.common.crafting.requirement.RequirementSource;
import es.degrassi.mmreborn.common.crafting.MachineRecipe;
import es.degrassi.mmreborn.common.crafting.requirement.PositionedSizedRequirement;
import es.degrassi.mmreborn.common.crafting.requirement.jei.JeiComponent;
import es.degrassi.mmreborn.common.data.Config;
import es.degrassi.mmreborn.common.integration.jei.category.MMRRecipeCategory;
import es.degrassi.mmreborn.common.integration.jei.category.drawable.DrawableWrappedText;
import es.degrassi.mmreborn.common.integration.jei.ingredient.CustomIngredientTypes;
import es.degrassi.mmreborn.common.util.TextureSizeHelper;
import es.degrassi.mmreborn.common.util.Utils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
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
  public void setRecipe(MMRRecipeCategory category, IRecipeLayoutBuilder builder, MachineRecipe recipe, IFocusGroup focuses) {
    Component component = Component.empty();
    String chance = Utils.decimalFormat(requirement.chance * 100);
    if (requirement.chance > 0 && requirement.chance < 1)
      component = Component.translatable("modular_machinery_reborn.ingredient.chance", chance, "%").withColor(Config.chanceColor);
    else if (requirement.chance == 0)
      component = Component.translatable("modular_machinery_reborn.ingredient.chance.nc").withColor(Config.chanceColor);
    Font font = Minecraft.getInstance().font;
    recipe.chanceTexts.add(
        Pair.of(
            new PositionedSizedRequirement(
                getPosition().x(),
                getPosition().y(),
                getWidth(),
                font.wordWrapHeight(component, getWidth())
            ),
            new DrawableWrappedText(List.of(component), getWidth() + 2, true)
                .transform(DrawableWrappedText.Operation.SET, DrawableWrappedText.State.TRANSLATEX, getPosition().x())
                .transform(DrawableWrappedText.Operation.SET, DrawableWrappedText.State.TRANSLATEY, getPosition().y())
                .transform(DrawableWrappedText.Operation.SET, DrawableWrappedText.State.SCALE, 0.75)
                .transform(DrawableWrappedText.Operation.SET, DrawableWrappedText.State.TRANSLATEZ, 500)
                .transform(DrawableWrappedText.Operation.SET, DrawableWrappedText.State.TRANSLATEX, (double) (getWidth() - 16) / 2)
                .transform(DrawableWrappedText.Operation.ADD, DrawableWrappedText.State.TRANSLATEX, 17)
                .transform(DrawableWrappedText.Operation.REMOVE, DrawableWrappedText.State.TRANSLATEX, Math.min(14, font.width(component)))
                .transform(DrawableWrappedText.Operation.SET, DrawableWrappedText.State.TRANSLATEY, (double) (getHeight() - 16) / 2)
                .transform(DrawableWrappedText.Operation.REMOVE, DrawableWrappedText.State.TRANSLATEY, 1)
                .transform(DrawableWrappedText.Operation.ADD, DrawableWrappedText.State.TRANSLATEY, (double) font.lineHeight * 3/2)
        )
    );
    builder
        .addSlot(RecipeIngredientRole.RENDER_ONLY, getPosition().x() + 1, getPosition().y() + 1)
        .setCustomRenderer(CustomIngredientTypes.SOURCE, this)
        .addIngredients(CustomIngredientTypes.SOURCE, ingredients());
  }
}
