package wolforce.groves.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.util.ResourceLocation;
import wolforce.groves.Groves;

public class GroveRecipeCategory extends BaseRecipeCategory<GroveRecipeWrapper> {

	static ResourceLocation guiTexture = new ResourceLocation(Groves.MODID, "textures/gui/container/jei_grove.png");

	public GroveRecipeCategory(IGuiHelper iGuiHelper) {
		super(iGuiHelper.createDrawable(guiTexture, 0, 0, 116, 50), "tile.groves.grove.name");
	}

	@Override
	public String getUid() {
		return JeiIntegration.GROVES;
	}

	@Override
	public String getTitle() {
		return "Grove Recipes"; // MUtil.translateToLocal("mechanics.titles.groves.name");
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, GroveRecipeWrapper recipeWrapper, IIngredients ingredients) {
		// TODO
		// IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		// guiItemStacks.init(0, true, 16, 16);
		// guiItemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		//
		// guiItemStacks.init(1, false, 83, 16);
		// guiItemStacks.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}
}