package wolforce.groves.client;

import groves.ct.RecipeGrove;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import wolforce.groves.Groves;
import wolforce.groves.blocks.BlockGrove;
import wolforce.groves.blocks.TileGrove;

@Mod.EventBusSubscriber(modid = Groves.MODID, value = Side.CLIENT)
public class RegisterModels {

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {

		// ModelLoader.setCustomModelResourceLocation(item, 0,
		// new ModelResourceLocation(item.getRegistryName(), "inventory"));

		for (RecipeGrove recipe : RecipeGrove.recipes.values()) {
			BlockGrove block = recipe.blockGrove;
			Item itemBlock = Item.getItemFromBlock(block);

			ModelLoader.setCustomStateMapper(block, new StateMapperBase() {

				@Override
				protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
					return new ModelResourceLocation(Groves.MODID + ":grove");
				}
			});

			ModelLoader.setCustomModelResourceLocation(itemBlock, 0,
					new ModelResourceLocation(Groves.MODID + ":grove", "inventory"));
		}

		// for (Item item : Main.items) {
		// ModelLoader.setCustomModelResourceLocation(item, 0,
		// new ModelResourceLocation(item.getRegistryName(), "inventory"));
		// }

		ClientRegistry.bindTileEntitySpecialRenderer(TileGrove.class, new TesrDryingTable());

	}

}
