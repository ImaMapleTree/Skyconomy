package me.imamapletree.executors;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.data.Directional;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import me.imamapletree.api.Recipe;

public class CraftExecutor implements Runnable {
	private Recipe recipe;
	private Inventory disp_inv;
	
	public CraftExecutor(Recipe recipe, Inventory disp_inv) {
		this.recipe = recipe;
		this.disp_inv = disp_inv;
	}

	@Override
	public void run() {
		Block dispblock = disp_inv.getLocation().getBlock();
		
		if(!recipe.isCraftable(disp_inv)) return;
		disp_inv.addItem(recipe.Craft(disp_inv));
		
		BlockState output = dispblock.getRelative(((Directional)dispblock.getBlockData()).getFacing()).getState();
		
		if(output instanceof Container) {
			((Container) output).getSnapshotInventory().addItem(recipe.getResult());
			output.update();
			disp_inv.remove(recipe.getResult());
		} 	Bukkit.getPluginManager().callEvent(new InventoryMoveItemEvent(disp_inv, recipe.getResult(), ((Container) output).getInventory(), true));
		dispblock.getState().update();
	}
}
