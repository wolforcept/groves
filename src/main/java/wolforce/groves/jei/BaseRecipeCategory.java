package wolforce.groves.jei;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import wolforce.groves.Groves;

public abstract class BaseRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {

	public IDrawable background;
	public String unlocalizedName;

	public BaseRecipeCategory(IDrawable background, String unlocalizedName) {
		this.background = background;
		this.unlocalizedName = unlocalizedName;
	}

	@Override
	public String getModName() {
		return Groves.MODID;
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

}