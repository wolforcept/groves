package wolforce.groves;

import groves.ct.RecipeGrove;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs {

	public CreativeTab() {
		super("Groves");
	}

	@Override
	public ItemStack getTabIconItem() {
		if (!RecipeGrove.recipes.isEmpty()) {
			Block block = ((RecipeGrove) RecipeGrove.recipes.values().toArray()[0]).blockGrove;
			return new ItemStack(block);
		}
		return new ItemStack(Blocks.AIR);
	}
}