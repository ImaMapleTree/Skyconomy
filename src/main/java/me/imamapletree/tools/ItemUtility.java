package me.imamapletree.tools;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtility {
	public static ItemStack createHead(UUID uuid) {
		ItemStack head_stack = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta head_meta = (SkullMeta) head_stack.getItemMeta();
		OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
		head_meta.setOwningPlayer(op);
		head_stack.setItemMeta(head_meta);
		return head_stack;
	}
	
	@Deprecated
	public static ItemStack createHead(String name) {
		ItemStack head_stack = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta head_meta = (SkullMeta) head_stack.getItemMeta();
		head_meta.setOwner(name);
		head_stack.setItemMeta(head_meta);
		return head_stack;
	}
	
	public static ItemStack createHead(UUID uuid, int amount) {
		ItemStack head_stack = new ItemStack(Material.PLAYER_HEAD, amount);
		SkullMeta head_meta = (SkullMeta) head_stack.getItemMeta();
		OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
		head_meta.setOwningPlayer(op);
		head_stack.setItemMeta(head_meta);
		return head_stack;
	}
	
	public static ItemStack setName(ItemStack item, String name) {
		ItemMeta IM = item.getItemMeta();
		IM.setDisplayName(name);
		item.setItemMeta(IM);
		return item;
	}
	
	public static ItemStack setLore(ItemStack item, List<String> lore) {
		ItemMeta IM = item.getItemMeta();
		IM.setLore(lore);
		item.setItemMeta(IM);
		return item;
	}
}
