package me.imamapletree.wrappers.panel;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.channels.IslandChannel;
import me.imamapletree.api.instances.SkyPlayer;
import me.imamapletree.panels.Icon;
import net.md_5.bungee.api.ChatColor;

public class DebugWrapper implements ClickCommandWrapper {
	public boolean onClick(Icon icon, Player player) {
		ItemStack base_paper = new ItemStack(Material.PAPER);
		NBTItem nbti = new NBTItem(base_paper);
		if(icon.getExtraneous().contains("s-")) {
			String what = icon.getExtraneous().split("-")[1];
			if(what.equals("i")) {
				SkyPlayer SP = Skyconomy.getInstance().getPlayerManager().getSkyPlayer(player.getUniqueId()).orElse(null);
				if(SP == null) return false;
				SP.setChannel(new IslandChannel(player, Skyconomy.getInstance().getIslandManager().getIsland(player.getUniqueId().toString(), player.getWorld())));
				return false;
			}
		}
		
		if(icon.getExtraneous().contains("giv")) {
			String what = icon.getExtraneous().split("-")[1];
			if(what.equals("1")) {
				nbti.setInteger("dungeonID", 0);
				base_paper = nbti.getItem();
				base_paper.addUnsafeEnchantment(Enchantment.CHANNELING, 1);
				ItemMeta IM = base_paper.getItemMeta();
				IM.addEnchant(Enchantment.CHANNELING, 1, true);
				IM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				IM.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Dungeon Calibration Point");
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GREEN + "Common Location");
				lore.add(ChatColor.DARK_GREEN + "Plains of Beginning");
				lore.add(" ");
				lore.add(ChatColor.RED + "This item can only be used once.");
				IM.setLore(lore);
				base_paper.setItemMeta(IM);
				player.getInventory().addItem(base_paper);
			} else if(what.equals("2")) {
				nbti.setInteger("dungeonID", 1);
				base_paper = nbti.getItem();
				base_paper.addUnsafeEnchantment(Enchantment.CHANNELING, 1);
				ItemMeta IM = base_paper.getItemMeta();
				IM.addEnchant(Enchantment.CHANNELING, 1, true);
				IM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				IM.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Dungeon Calibration Point");
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GOLD + "Legendary Location");
				lore.add(ChatColor.YELLOW + "Dawning Horizons");
				lore.add(" ");
				lore.add(ChatColor.RED + "This item can only be used once.");
				IM.setLore(lore);
				base_paper.setItemMeta(IM);
				player.getInventory().addItem(base_paper);
			}
		}
		return false;
	}
}
