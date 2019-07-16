package wolforce.groves.blocks;

import java.util.HashMap;
import java.util.List;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.VanillaIngredient;
import groves.ct.RecipeGrove;
import groves.ct.RecipeGrove.BlockValue;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import wolforce.groves.MConfig;
import wolforce.groves.MUtil;

public class TileGrove extends TileBase implements ITickable {

	private Integer spawned = 0;
	private Integer cooldown = -1;

	@Override
	public void update() {

		if (world.isRemote)
			return;

		if (cooldown > 0) {
			cooldown--;
			return;
		}

		BlockGrove block = (BlockGrove) world.getBlockState(pos).getBlock();
		RecipeGrove recipe = RecipeGrove.recipes.get(block.name);

		if (cooldown == -1) {
			cooldown = recipe.cooldown;
			return;
		}

		System.out.println("cooldown:" + cooldown);

		cooldown = recipe.cooldown;

		// --

		// HashMap<IIngredient, Integer> ns = new HashMap<>();
		// for (BlockValue bv : recipe.blockValues) {
		// ns.put(bv.block, 0);
		// }
		int sum = 0;
		for (int dx = -recipe.radius; dx < recipe.radius; dx++) {
			for (int dy = -recipe.radius; dy < recipe.radius; dy++) {
				for (int dz = -recipe.radius; dz < recipe.radius; dz++) {
					IBlockState state = world.getBlockState(pos.add(dx, dy, dz));
					for (BlockValue bv : recipe.blockValues) {
						if (bv.block.matches(toIItemStack(state)))
							sum += bv.value;
					}
				}
			}
		}

		System.out.println("sum:" + sum);
		if (sum < recipe.totalvalue)
			return;

		// --

		float x = (float) (pos.getX() - recipe.radius * (Math.random() * 2 - 1));
		float y = pos.getY();
		float z = (float) (pos.getZ() - recipe.radius * (Math.random() * 2 - 1));

		while (!world.isAirBlock(new BlockPos(x, y, z)))
			y++;
		while (world.isAirBlock(new BlockPos(x, y - 1, z)))
			y--;

		if (recipe.stackSpawns.size() == 0 || recipe.testSpawnEntity())
			spawnEntity(recipe, x, y, z);
		else if (recipe.stackSpawns.size() != 0)
			spawnStack(recipe, x, y, z);

		// --

		spawned++;
		if (spawned >= recipe.uses) {
			world.setBlockToAir(pos);
		}

	}

	private IItemStack toIItemStack(IBlockState state) {
		return CraftTweakerMC
				.getIItemStack(new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)));
	}

	private void spawnStack(RecipeGrove recipe, float x, float y, float z) {
		MUtil.spawnItem(world, new BlockPos(x, y, z), recipe.getRandomStack());
	}

	private void spawnEntity(RecipeGrove recipe, float x, float y, float z) {
		String entityLoc = recipe.getRandomEntity();

		if (entityLoc == null)
			return;
		Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation(entityLoc), world);

		if (canSpawn(recipe, entity, recipe.radius)) {
			entity.setLocationAndAngles(x, y, z, (float) Math.random(), 0);
			world.spawnEntity(entity);
		}
	}

	private boolean canSpawn(RecipeGrove recipe, Entity entity, int range) {

		AxisAlignedBB aabb = new AxisAlignedBB(pos.add(-range, -range, -range), pos.add(range, range, range));
		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(entity, aabb);
		return entities.size() < MConfig.max_entities;
	}

	//

	//

	// HAS DATA TO SAVE

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("cooldown", cooldown);
		compound.setInteger("spawned", spawned);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		cooldown = compound.getInteger("cooldown");
		spawned = compound.getInteger("spawned");
	}

}
