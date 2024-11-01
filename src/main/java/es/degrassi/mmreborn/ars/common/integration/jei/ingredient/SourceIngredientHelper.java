package es.degrassi.mmreborn.ars.common.integration.jei.ingredient;

import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.common.integration.jei.ingredient.CustomIngredientTypes;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class SourceIngredientHelper implements IIngredientHelper<Integer> {

    @Override
    public IIngredientType<Integer> getIngredientType() {
        return CustomIngredientTypes.SOURCE;
    }

    @Override
    public String getDisplayName(Integer source) {
        return Component.translatable("modular_machinery_reborn_ars.jei.ingredient.source", source).getString();
    }

    //Safe to remove
    @SuppressWarnings("removal")
    @Override
    public String getUniqueId(Integer source, UidContext context) {
        return "" + source;
    }

    @Override
    public Object getUid(Integer source, UidContext context) {
        return "" + source;
    }

    @Override
    public Integer copyIngredient(Integer source) {
        return source.intValue();
    }

    @Override
    public String getErrorInfo(@Nullable Integer source) {
        return "";
    }

    @Override
    public ResourceLocation getResourceLocation(Integer ingredient) {
        return ModularMachineryRebornArs.rl("source");
    }
}
