package me.imamapletree.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.bungee.GlobalChannel;
import me.imamapletree.api.instances.SkyPlayer;
import me.imamapletree.database.mobs.SpiderBoss;
import me.imamapletree.tools.SkyLog;
import me.imamapletree.world.generators.Generator1;
import net.md_5.bungee.api.ChatColor;

public class Test {
	private static Skyconomy skyconomy = (Skyconomy) Skyconomy.getInstance();
	public static boolean run(CommandSender sender, Command command, String label2, String[] args) {
		if (sender instanceof Player) {
			SkyLog.log(Bukkit.getWorlds());
			/*
			WorldCreator WC = new WorldCreator("SpiderDen");
			WC.generator(new Generator1());
			WC.createWorld();
			World world = Bukkit.getServer().createWorld(WC);
			*/
			SkyLog.log(Bukkit.getWorlds());
			Player player = ((Player) sender).getPlayer();
			SkyPlayer skyplayer = skyconomy.getPlayerManager().getSkyPlayer(player.getUniqueId()).orElse(null);
			if(args.length >= 1) {
				if(skyplayer != null) skyplayer.setChannel(null);
			} else {
				GlobalChannel channel = new GlobalChannel(player);
				if(skyplayer != null) skyplayer.setChannel(channel);
				SkyLog.log(skyplayer.hasChannel());
			}
			ItemStack QI = new ItemStack(Material.ENCHANTING_TABLE);
			NBTItem nbtiq = new NBTItem(QI);
			nbtiq.setString("CUSTOM_DATA", "DUNGEONACCESSPOINT");
			QI = nbtiq.getItem();
			ItemMeta QIM = QI.getItemMeta();
			QIM.setDisplayName(ChatColor.YELLOW + "Dungeon Access Point");
			QI.setItemMeta(QIM);
			player.getInventory().addItem(QI);
			
			ItemStack ACB = new ItemStack(Material.DISPENSER);
			NBTItem nbti = new NBTItem(ACB);
			nbti.setString("CUSTOM_DATA", "AUTOCRAFTINGTABLE");
			ACB = nbti.getItem();
			ItemMeta IM = ACB.getItemMeta();
			IM.setDisplayName(ChatColor.YELLOW + "Autocrafting Table");
			ACB.addUnsafeEnchantment(Enchantment.LUCK, 1);
			ACB.setItemMeta(IM);
			player.getInventory().addItem(ACB);
			
			/*
			if(world != null) {
				Location loc = new Location(world, 0, 100, 0);
				player.teleport(loc);
			}
			*/
			
			//SpiderBoss SB = new SpiderBoss(player.getWorld(), "Spider Queen Ilygo", 3);
			/*
			SpiderBoss SB = new SpiderBoss(player.getWorld(), "Venenatis", 2);
			SB.setHP(50);
			SB.setArmor(20);
			SB.setArmorToughness(5);
			SB.setMovementSpeed(0.53f);
			SB.setAttackDamage(3);
			SB.spawn(player.getLocation());
			List<Player> pl = new ArrayList<Player>(Bukkit.getOnlinePlayers());
			SB.showBossBar(pl);
			*/
			skyconomy.getDungeonManager().startBossEvent();
			Location loc = new Location(skyconomy.getDungeonManager().CurrentDomain, 0, 100, 0);
			player.teleport(loc);
			
			return true;
		}
		return false;
		
	}
}
