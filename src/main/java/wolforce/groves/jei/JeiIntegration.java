package wolforce.groves.jei;

import java.util.LinkedList;

import groves.ct.RecipeGrove;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import wolforce.groves.Groves;

@JEIPlugin
public class JeiIntegration implements IModPlugin {

	public static IJeiHelpers jeiHelpers;
	public static ICraftingGridHelper craftingGridHelper;
	public static IRecipeRegistry recipeRegistry;

	public static final String GROVES = Groves.MODID + ".groves";

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		recipeRegistry = jeiRuntime.getRecipeRegistry();
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		if (registry != null) {
			IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
			registry.addRecipeCategories(new GroveRecipeCategory(guiHelper));
		}
	}

	@Override
	public void register(IModRegistry registry) {
		jeiHelpers = registry.getJeiHelpers();

		registry.handleRecipes(RecipeGrove.class, (GroveRecipeWrapper::new), GROVES);

		for (RecipeGrove recipe : RecipeGrove.recipes.values()) {
			GroveRecipeWrapper wrapper = new GroveRecipeWrapper(recipe);
			LinkedList<GroveRecipeWrapper> recipeList = new LinkedList<>();
			recipeList.add(wrapper);
			registry.addRecipes(recipeList, GROVES);
			registry.addRecipeCatalyst(new ItemStack(recipe.blockGrove), GROVES);
		}

	}
}