package wolforce.groves.jei;

import groves.ct.RecipeGrove;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;

public class GroveRecipeWrapper implements IRecipeWrapper {

	private RecipeGrove recipe;

	public GroveRecipeWrapper(RecipeGrove recipe) {
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		// ingredients.setInputs(VanillaTypes.ITEM, recipe.getIns());
		// ingredients.setOutput(VanillaTypes.ITEM, recipe.getOuts());
	}
}