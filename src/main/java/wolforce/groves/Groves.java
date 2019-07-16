package wolforce.groves;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import groves.ct.RecipeGrove;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Groves.MODID, name = Groves.NAME, version = Groves.VERSION)
@Mod.EventBusSubscriber(modid = Groves.MODID)
public class Groves {

	@Instance(Groves.MODID)
	public static Groves instance;

	public static final String MODID = "groves";
	public static final String NAME = "Groves";
	public static final String VERSION = "0.0";
	public static final Logger logger = LogManager.getLogger(NAME);

	private CreativeTab creativeTab;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		creativeTab = new CreativeTab();
		for (RecipeGrove recipe : RecipeGrove.recipes.values())
			recipe.blockGrove.setCreativeTab(creativeTab);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}

	public static void logNormal(String string) {
		logger.log(Level.INFO, string);
	}

	public static void logError(String string) {
		logger.log(Level.ERROR, string);
	}

}