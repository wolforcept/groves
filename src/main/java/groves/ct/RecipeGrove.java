package groves.ct;

import java.util.HashMap;
import java.util.LinkedList;

import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wolforce.groves.blocks.BlockGrove;

@ZenClass("mods.groves")
@ZenRegister
public class RecipeGrove {

	public static HashMap<String, RecipeGrove> recipes = new HashMap<>();

	@ZenDoc("Add a new grove block.")
	@ZenMethod
	public static void createGrove(String regname, int radius, int uses, int cooldown, int total) {
		if (recipes.containsKey(regname))
			return;
		RecipeGrove recipe = new RecipeGrove(radius, uses, cooldown, total);
		recipes.put(regname, recipe);
	}

	@ZenDoc("Add a block value to an existing grove.")
	@ZenMethod
	public static void addBlock(String grove, IIngredient block, int value) {
		if (!recipes.containsKey(grove))
			return;
		BlockValue blockValue = new BlockValue(block, value);
		recipes.get(grove).blockValues.add(blockValue);
	}

	@ZenDoc("Add an item spawn to an existing grove.")
	@ZenMethod
	public static void addStackSpawn(String grove, IItemStack item, double probability) {
		if (!recipes.containsKey(grove))
			return;
		StackSpawn itemSpawn = new StackSpawn(item, probability);
		recipes.get(grove).stackSpawns.add(itemSpawn);
	}

	@ZenDoc("Add an entity spawn to an existing grove.")
	@ZenMethod
	public static void addEntitySpawn(String grove, String entity, double probability) {
		if (!recipes.containsKey(grove))
			return;
		EntitySpawn entitySpawn = new EntitySpawn(entity, probability);
		recipes.get(grove).entitySpawns.add(entitySpawn);
	}

	//

	//

	//

	public BlockGrove blockGrove;

	public final int radius;
	public final int uses;
	public final int cooldown;
	public final int totalvalue;

	public final LinkedList<BlockValue> blockValues;
	public final LinkedList<StackSpawn> stackSpawns;
	public final LinkedList<EntitySpawn> entitySpawns;

	public RecipeGrove(int radius, int uses, int cooldown, int total) {
		this.radius = radius;
		this.uses = uses;
		this.cooldown = cooldown;
		this.totalvalue = total;

		// this.blockGrove = blockGrove;

		blockValues = new LinkedList<>();
		stackSpawns = new LinkedList<>();
		entitySpawns = new LinkedList<>();
	}

	//

	// public boolean testSpawnStack() {
	// return Math.random() < (stackSpawns.size() / getTotal());
	// }

	public boolean testSpawnEntity() {
		return Math.random() < (entitySpawns.size() / (float) (entitySpawns.size() + stackSpawns.size()));
	}

	// private int getTotal() {
	// return entitySpawns.size() + stackSpawns.size();
	// }

	//

	public static class BlockValue {
		public IIngredient block;
		public double value;

		public BlockValue(IIngredient block, double value) {
			this.block = block;
			this.value = value;
		}
	}

	public static class StackSpawn {
		public IItemStack stack;
		public double probability;

		public StackSpawn(IItemStack stack, double probability) {
			this.stack = stack;
			this.probability = probability;
		}
	}

	public static class EntitySpawn {
		public String entity;
		public double probability;

		public EntitySpawn(String entity, double probability) {
			this.entity = entity;
			this.probability = probability;
		}
	}

	public ItemStack getRandomStack() {
		double rand = Math.random();
		double sum = 0;
		for (StackSpawn ss : stackSpawns) {
			sum += ss.probability;
			if (rand < sum)
				return CraftTweakerMC.getItemStack(ss.stack);
		}
		return ItemStack.EMPTY;
	}

	public String getRandomEntity() {
		double rand = Math.random();
		double sum = 0;
		for (EntitySpawn es : entitySpawns) {
			sum += es.probability;
			if (rand < sum)
				return es.entity;
		}
		return null;
	}
}
