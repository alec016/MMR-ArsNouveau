package es.degrassi.mmreborn.ars.common.integration.jei;

import es.degrassi.mmreborn.api.codec.NamedCodec;
import es.degrassi.mmreborn.ars.ModularMachineryRebornArs;
import es.degrassi.mmreborn.ars.common.integration.jei.ingredient.SourceIngredientHelper;
import es.degrassi.mmreborn.common.integration.jei.ingredient.CustomIngredientTypes;
import es.degrassi.mmreborn.common.integration.jei.ingredient.DummyIngredientRenderer;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IModIngredientRegistration;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

@JeiPlugin
public class MMRArsJeiPlugin implements IModPlugin {
  public static final ResourceLocation PLUGIN_ID = ModularMachineryRebornArs.rl("jei_plugin");

  @Override
  public ResourceLocation getPluginUid() {
    return PLUGIN_ID;
  }

  @Override
  public void registerIngredients(IModIngredientRegistration registration) {
    registration.register(CustomIngredientTypes.SOURCE, new ArrayList<>(), new SourceIngredientHelper(), new DummyIngredientRenderer<>(), NamedCodec.INT.codec());
  }
}
