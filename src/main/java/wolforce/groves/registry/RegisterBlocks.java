package wolforce.groves.registry;

import java.util.Map.Entry;

import org.apache.logging.log4j.Level;

import crafttweaker.CraftTweakerAPI;
import groves.ct.RecipeGrove;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wolforce.groves.Groves;
import wolforce.groves.MUtil;
import wolforce.groves.blocks.BlockGrove;

@Mod.EventBusSubscriber(modid = Groves.MODID)
public class RegisterBlocks {

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {

		CraftTweakerAPI.tweaker.loadScript(false, "groves");

		for (Entry<String, RecipeGrove> entry : RecipeGrove.recipes.entrySet()) {

			String regname = entry.getKey();
			RecipeGrove recipe = entry.getValue();

			BlockGrove blockGrove = new BlockGrove(regname);
			MUtil.setReg(blockGrove, regname);
			Groves.logger.log(Level.INFO, "Added new custom Grove Block: " + blockGrove);

			recipe.blockGrove = blockGrove;
			event.getRegistry().register(blockGrove);
			if (blockGrove instanceof ITileEntityProvider) {
				Class<? extends TileEntity> clazz = ((ITileEntityProvider) blockGrove).createNewTileEntity(null, 0)
						.getClass();
				GameRegistry.registerTileEntity(clazz,
						new ResourceLocation(Groves.MODID + ":tile_" + blockGrove.getUnlocalizedName()));
			}
		}
	}

	@SubscribeEvent
	public static void registerItemsBlocks(RegistryEvent.Register<Item> event) {
		for (RecipeGrove recipe : RecipeGrove.recipes.values()) {
			Block block = recipe.blockGrove;
			ItemBlock itemblock = new ItemBlock(block) {
				@Override
				public String getItemStackDisplayName(ItemStack stack) {
					return recipe.blockGrove.getLocalizedName();
				}
			};
			MUtil.setReg(itemblock, block.getRegistryName().getResourcePath());
			event.getRegistry().register(itemblock);
		}
	}
}
