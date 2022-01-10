package me.imamapletree.api;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import me.imamapletree.tools.Utility;

public class Recipe implements org.bukkit.inventory.Recipe {
	private ItemStack result;
	private Map<Material, Integer> ingredient_list = new HashMap<Material, Integer>();
	
	
	public Recipe(Inventory inv, org.bukkit.inventory.Recipe r) {
		this.result = r.getResult();
		this.sortIngredients(inv);
	}
	
	public Recipe(String recipe_string) {
		if(recipe_string == null) return;
		String[] split1 = recipe_string.split(">");
		if(split1 == null) return;
		String[] ingredients = split1[0].split(",");
		if(ingredients == null) return;
		for(String ingredient : ingredients) {
			String[] KV = ingredient.split("!");
			if(ingredient != null) {this.ingredient_list.put(Material.getMaterial(KV[0]), Integer.valueOf(KV[1]));}
		} 
		String[] resultKV = split1[1].split("!");
		this.result = new ItemStack(Material.getMaterial(resultKV[0]), Integer.valueOf(resultKV[1]));	
	}
	
	private void sortIngredients(Inventory inv) {
		for(int i = 1; i < 10; i++) {
			ItemStack IS = inv.getItem(i);
			if (IS != null) {
				Material mat = IS.getType();
				if(!ingredient_list.containsKey(mat)) {ingredient_list.put(mat, 1);} 
				else {ingredient_list.put(mat, ingredient_list.get(mat)+1);}
			}
		}
	}
	
	public boolean isCraftable(Inventory inv) {
		Map<Material, Integer> copied_list = new HashMap<Material, Integer>(this.ingredient_list);
		for(ItemStack IS : inv.getContents()) {
			if(IS != null) {
				Material mat = IS.getType();
				if(!copied_list.containsKey(mat)) {
				} else {copied_list.put(mat, copied_list.get(mat)-IS.getAmount());}
			}
		}
		boolean craftable = true;
		for (Map.Entry<Material, Integer> entry : copied_list.entrySet()) {
		    if(entry.getValue() > 0) craftable = false;
		}
		return craftable;
	}
	
	public ItemStack Craft(Inventory inv) { //Add forced call to isCraftable later
		for (Map.Entry<Material, Integer> entry : this.ingredient_list.entrySet()) {
			ItemStack IS = new ItemStack(entry.getKey(), entry.getValue());
			inv.removeItem(IS);
		}
		return this.result;
	}
	
	public String toString() {
		String recipe_string = "";
		for (Map.Entry<Material, Integer> entry : this.ingredient_list.entrySet()) {
			recipe_string = recipe_string + entry.getKey().toString() + "!" + entry.getValue().toString() + ",";
		}
		return Utility.replaceLast(recipe_string, ",", ">") + result.getType().toString() + "!" + result.getAmount();
	}
	
	
	@Override
	public ItemStack getResult() {
		return this.result;
	}
}
