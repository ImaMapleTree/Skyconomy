package me.imamapletree.tools;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.imamapletree.Skyconomy;
import me.imamapletree.WorldLinker;
import me.imamapletree.api.WorldCoordinate;
import me.imamapletree.api.instances.Flag;
import me.imamapletree.api.instances.Island;
import me.imamapletree.tools.builders.SentenceBuilder;

public class Utility {
	private static Skyconomy skyconomy = Skyconomy.getInstance();
	private static List<String> bungeechannels = Arrays.asList("Connect", "ConnectOther", "IP", "PlayerCount", "PlayerList", "GetServers", "Message", "GetServer",
			"Forward", "ForwardToPlayer", "UUID", "UUIDOther", "ServerIP", "Kickplayer");
	private static Pattern pattern = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");
	
	@SuppressWarnings("unused")
	private static double getRelativeCoord(int i) {
		double d = i;
	    d = d < 0 ? d - .5 : d + .5;
	    return d;
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        @SuppressWarnings("unused")
			int i = Integer.valueOf(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public static World getWorld(CommandSender sender) {
		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage("You must be in game to run this command!");
			return null;
		} else if(sender instanceof BlockCommandSender) {
			return (World) ((BlockCommandSender) sender).getBlock().getWorld();
		} else if(sender instanceof ProxiedCommandSender) {
			return (World) ((Entity) ((ProxiedCommandSender) sender).getCallee()).getWorld();
		} else if(sender instanceof Entity) {
			return (World) ((Entity) sender).getWorld();
		}
		return null;
	}
	
	public static Location LocationFromWC(World world, WorldCoordinate WC) {
		return new Location(world, WC.getX(), WC.getY(), WC.getZ());
	}
	
	public static Location getLocationCenter(Location loc) {
		double x = 0.5;
		double z = 0.5;
		//if(loc.getBlockX() >= 0) x = 0.5;
		//if(loc.getBlockX() < 0) x = -0.5;
		//if(loc.getBlockZ() >= 0) z = 0.5;
		//if(loc.getBlockZ() < 0) z = -0.5;
		return loc.clone().add(x, 0, z);
	}
	
	public static net.minecraft.server.v1_16_R2.World getNMSWorld(CommandSender sender) {
		if(sender instanceof ConsoleCommandSender) {
			sender.sendMessage("You must be in game to run this command!");
			return null;
		} else if(sender instanceof BlockCommandSender) {
			return ((CraftWorld) ((BlockCommandSender) sender).getBlock().getWorld()).getHandle();
		} else if(sender instanceof ProxiedCommandSender) {
			return ((CraftWorld) ((Entity) ((ProxiedCommandSender) sender).getCallee()).getWorld()).getHandle();
		} else if(sender instanceof Entity) {
			return ((CraftWorld) ((Entity) sender).getWorld()).getHandle();
		}
		return null;
	}
	
	public static String type(Object obj){
	    return obj.getClass().getSimpleName();
	}
	
	public static int makeNegative(int number) {
		if (number > 0) {
			return number * -1;
		}
		return number;
	}
	
	public static int makePositive(int number) {
		if (number < 0) {
			return number * -1;
		}
		return number;
	}
	
	public static boolean equalsType(Object obj1, Object obj2) {
		if (Utility.type(obj1) == Utility.type(obj2)) {
			return true;
		}
		return false;
	}

	public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }
	
	public static boolean SimpleACheck(String FlagName, Player player, Location loc) {
		Island island = skyconomy.getIslandManager().locateIsland(loc);
		if(island == null) return true; //perhaps add global settings
		String role = island.getRole(player.getUniqueId().toString());
		return island.isAllowed(FlagName, role);
	}

	public static boolean SimpleGCheck(String FlagName, Location loc) {
		Island island = skyconomy.getIslandManager().locateIsland(loc);
		if(island == null) return true;
		Flag flag = skyconomy.getFlagManager().getFlag(FlagName).orElse(null);
		if(flag == null) return true;
		List<String> value = island.getFlagValue(flag);
		if(value == null) return true;
		if(value.isEmpty()) return true;
		String real_value = value.get(0);
		if(real_value.equals("ENABLED")) return true;
		return false;
	}
	
	public static String getVersion(int ProtocolVersion) {
		switch (ProtocolVersion) {
			case 736: return "1.16.1";
			case 735: return "1.16";
			case 578: return "1.15.2";
			case 575: return "1.15.1";
			case 573: return "1.15";
			case 498: return "1.14.4";
			case 490: return "1.14.3";
			case 485: return "1.14.2";
			case 480: return "1.14.1";
			case 477: return "1.14";
			case 404: return "1.13.2";
			case 401: return "1.13.1";
			case 393: return "1.13";
			case 340: return "1.12.2";
			case 338: return "1.12.1";
			case 335: return "1.12";
			case 316: return "1.11.2";
			case 315: return "1.11";
			case 210: return "1.10.X";
			case 110: return "1.9.4";
			case 109: return "1.9.2";
			case 108: return "1.9.1";
			case 107: return "1.9";
			case 47: return "1.8.X";
			case 5: return "1.7.10 (1.7.X)";
			case 4: return "1.7.2 (1.7.X)";
		}
		if(ProtocolVersion >= 701) return "20w06a+";
		if(ProtocolVersion >= 550) return "19w34a+";
		if(ProtocolVersion >= 441) return "18w43a+";
		if(ProtocolVersion >= 341) return "17w43a+";
		if(ProtocolVersion >= 317) return "17w06+";
		if(ProtocolVersion >= 301) return "16w32a+";
		if(ProtocolVersion >= 201) return "16w20+";
		if(ProtocolVersion >= 48) return "15w14+";
		if(ProtocolVersion >= 6) return "14w03a+";
		if(ProtocolVersion >= 0) return "13w41a+";
		return "Unknown";
	}

	public static String readableTime(long time, boolean shortened) {
		String minute_end = "minutes";
		String second_end = "seconds";
		if(shortened) {minute_end = "min"; second_end = "sec";}
		Integer minutes = (int) Math.floor(time/60);
		Integer seconds = (int) (time%60);
		if(minutes == 0 && seconds == 0) return new SentenceBuilder("0").add(minute_end).add("and").add(second_end).create();
		if(minutes == 0) return seconds.toString() + " " + second_end;
		if(seconds == 0) return minutes.toString() + " " + minute_end;
		else return minutes.toString() + " " + minute_end + " " + seconds.toString() + " " + second_end;
	}

	public static String getOfflineName(String uuid) {
		return Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
	}

	public static int getBiomeInt(String string) {
		switch (string.toLowerCase()) {
			case "snowy tundra": return 12;
			case "ice spikes": return 140;
			case "snowy taiga": return 30;
			case "snowy taiga mountains": return 158;
			case "frozen river": return 11;
			case "snowy beach": return 26;
			case "mountains": return 3;
			case "gravelly mountains": return 131;
			case "wooded mountains": return 34;
			case "gravelly mountains+": return 162;
			case "taiga": return 5;
			case "taiga mountains": return 133;
			case "giant tree taiga": return 32;
			case "giant spruce taiga": return 160;
			case "stone shore": return 25;
			case "plains": return 1;
			case "sunflower plains": return 129;
			case "forest": return 4;
			case "flower forest": return 132;
			case "birch forest": return 27;
			case "tall birch forest": return 155;
			case "dark forest": return 29;
			case "dark forest hills": return 157;
			case "swamp": return 6;
			case "swamp hills": return 134;
			case "jungle": return 21;
			case "modified jungled": return 149;
			case "jungle edge": return 23;
			case "modified jungle edge": return 151;
			case "bamboo jungle": return 168;
			case "river": return 7;
			case "beach": return 16;
			case "mushroom fields": return 14;
			case "mushroom field shore": return 15;
			case "desert": return 2;
			case "desert lakes": return 130;
			case "savanna": return 35;
			case "shattered savanna": return 163;
			case "badlands": return 37;
			case "eroded badlands": return 165;
			case "wooded badlands plateau": return 38;
			case "modified wooded badlands plateau": return 166;
			case "badlands plateau": return 36;
			case "savanna plateau": return 39;
			case "modified badlands plateau": return 164;
			case "shattered savanna plateau": return 167;
			case "warm ocean": return 44;
			case "lukewarm ocean": return 45;
			case "deep lukewarm ocean": return 48;
			case "ocean": return 0;
			case "deep ocean": return 24;
			case "cold ocean": return 46;
			case "deep cold ocean": return 49;
			case "frozen ocean": return 10;
			case "deep frozen ocean": return 50;
			case "void": return 127;
			case "wooded hills": return 13;
			case "taiga hills": return 17;
			case "snowy taiga hills": return 18;
			case "jungle hills": return 19;
			case "desert hills": return 22;
			case "birch forest hills": return 28;
			case "tall birch hills": return 31;
			case "giant tree taiga hills": return 33;
			case "giant spruce taiga hills": return 156;
			case "snowy mountains": return 161;
			case "nether wastes": return 8;
			case "soul sand valley": return 170;
			case "crimson forest": return 171;
			case "warped forest": return 172;
			case "basalt deltas": return 173;
			case "end": return 9;
			case "small end islands": return 40;
			case "end midlands": return 41;
			case "end highlands": return 42;
			case "end barrens": return 43;
			default: return 1;
		}
	}

	public static String getBiomeID(String string) {
		switch (string.toLowerCase().replace(" ", "_")) {
			case "snowy_tundra": return "minecraft:snowy_tundra";
			case "ice_spikes": return "minecraft:ice_spikes";
			case "snowy_taiga": return "minecraft:snowy_taiga";
			case "snowy_taiga_mountains": return "minecraft:taiga_mountains";
			case "frozen_river": return "minecraft:frozen_river";
			case "snowy_beach": return "minecraft:snowy_beach";
			case "mountains": return "minecraft:mountains";
			case "gravelly_mountains": return "minecraft:gravelly_mountains";
			case "wooded_mountains": return "minecraft:wooded_mountains";
			case "taiga": return "minecraft:taiga";
			case "taiga_mountains": return "minecraft:taiga_mountains";
			case "giant_tree_taiga": return "minecraft:giant_tree_taiga";
			case "giant_spruce_taiga": return "minecraft:giant_spruce_taiga";
			case "stone_shore": return "minecraft:stone_shore";
			case "plains": return "minecraft:plains";
			case "sunflower_plains": return "minecraft:sunflower_plains";
			case "forest": return "minecraft:forest";
			case "flower_forest": return "minecraft:flower_forest";
			case "birch_forest": return "minecraft:birch_forest";
			case "tall_birch_forest": return "minecraft:tall_birch_forest";
			case "dark_forest": return "minecraft:dark_forest";
			case "dark_forest_hills": return "minecraft:dark_forest_hills";
			case "swamp": return "minecraft:swamp";
			case "swamp_hills": return "minecraft:swamp_hills";
			case "jungle": return "minecraft:jungle";
			case "modified_jungle": return "minecraft:modified_jungle";
			case "jungle_edge": return "minecraft:jungle_edge";
			case "modified_jungle_edge": return "minecraft:modified_jungle_edge";
			case "bamboo_jungle": return "minecraft:bamboo_jungle";
			case "river": return "minecraft:river";
			case "beach": return "minecraft:beach";
			case "mushroom_fields": return "minecraft:mushroom_fields";
			case "mushroom_field_shore": return "minecraft:mushroom_field_shore";
			case "desert": return "minecraft:desert";
			case "desert_lakes": return "minecraft:desert_lakes";
			case "savanna": return "minecraft:savanna";
			case "shattered_savanna": return "minecraft:shattered_savanna";
			case "badlands": return "minecraft:badlands";
			case "eroded_badlands": return "minecraft:eroded_badlands";
			case "wooded_badlands_plateau": return "minecraft:wooded_badlands_plateau";
			case "modified_wooded_badlands_plateau": return "minecraft:modified_wooded_badlands_plateau";
			case "badlands_plateau": return "minecraft:badlands_plateay";
			case "savanna_plateau": return "minecraft:savanna_plateau";
			case "modified_badlands_plateau": return "minecraft:modified_badlands_plateau";
			case "shattered_savanna_plateau": return "minecraft:shattered_savanna_plateau";
			case "warm_ocean": return "minecraft:warm_ocean";
			case "lukewarm_ocean": return "minecraft:lukewarm_ocean";
			case "deep_lukewarm_ocean": return "minecraft:deep_lukewarm_ocean";
			case "ocean": return "minecraft:ocean";
			case "deep_ocean": return "minecraft:deep_ocean";
			case "cold_ocean": return "minecraft:cold_ocean";
			case "deep_cold_ocean": return "minecraft:deep_cold_ocean";
			case "frozen_ocean": return "minecraft:frozen_ocean";
			case "deep frozen ocean": return "minecraft:deep_frozen_ocean";
			case "void": return "minecraft:the_void";
			case "wooded_hills": return "minecraft:wooded_hills";
			case "taiga_hills": return "minecraft:taiga_hills";
			case "snowy_taiga_hills": return "minecraft:snowy_taiga_hills";
			case "jungle_hills": return "minecraft:jungle_hills";
			case "desert_hills": return "minecraft:desert_hills";
			case "birch_forest_hills": return "minecraft:birch_forest_hills";
			case "tall_birch_hills": return "minecraft:tall_birch_hills";
			case "giant_tree_taiga_hills": return "minecraft:giant_tree_taiga_hills";
			case "giant_spruce_taiga_hills": return "minecraft:giant_spruce_taiga_hills";
			case "snowy_mountains": return "minecraft:snowy_mountains";
			case "nether_wastes": return "minecraft:nether_wastes";
			case "soul_sand_valley": return "minecraft:soul_sand_valley";
			case "crimson_forest": return "minecraft:crimson_forest";
			case "warped_forest": return "minecraft:warped_forest";
			case "basalt_deltas": return "minecraft:basalt_deltas";
			case "end": return "minecraft:the_end";
			case "small_end_islands": return "minecraft:small_end_islands";
			case "end_midlands": return "minecraft:end_midlands";
			case "end_highlands": return "minecraft:end_highlands";
			case "end_barrens": return "minecraft:end_barrens";
			default: return "minecraft:plains";
		}
	}

	public static boolean isBungeeSubchannel(String string) {
		return bungeechannels.contains(string);
	}

	public static String stripWorldFolder(String world) {
		if(!world.contains("/")) return world;
		String[] parts = world.split("/");
		return parts[parts.length-1];
	}

	public static String hyphenateUUID(String uuid) {
		return pattern.matcher(uuid).replaceAll("$1-$2-$3-$4-$5");
	}
	
	public static List<String> cleanAssociatedWorlds(String world) {
		String world_name;
		if(world != null) world_name = world;
		else world_name = "null";
		List<String> worlds = WorldLinker.getInstance().getAssociatedWorlds(WorldLinker.getInstance().getSuperWorld(Utility.stripWorldFolder(world_name)));
		if(worlds.size() == 0) worlds.add("ALL");
		worlds.remove(Utility.stripWorldFolder(world_name));
		return worlds;
	}

	public static Player adaptPlayer(HumanEntity HE) {
		return Bukkit.getPlayer(HE.getUniqueId());
	}

	public static ItemStack addEnchantTint(ItemStack itemstack) {
		itemstack.addUnsafeEnchantment(Enchantment.CHANNELING, 1);
		ItemMeta IM = itemstack.getItemMeta();
		IM.addEnchant(Enchantment.CHANNELING, 1, true);
		IM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemstack.setItemMeta(IM);
		return itemstack;
	}
	
	public static ItemStack setItemName(ItemStack itemstack, String name) {
		ItemMeta IM = itemstack.getItemMeta();
		IM.setDisplayName(name);
		itemstack.setItemMeta(IM);
		return itemstack;
	}

	public static boolean isEven(int num) {
		if(num % 2 == 0) return true;
		else return false;
	}
	
	public static boolean isOdd(int num) {
		if(num % 2 == 0) return false;
		else return true;
	}
}
