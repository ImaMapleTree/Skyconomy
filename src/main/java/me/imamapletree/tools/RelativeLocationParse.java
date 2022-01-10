package me.imamapletree.tools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.entity.Entity;

public class RelativeLocationParse {
	public static Location tildeParse(CommandSender sender, String[] args) {
		Location loc = null;
		if(sender instanceof Entity || sender instanceof ProxiedCommandSender) {
			if (sender instanceof ProxiedCommandSender) {
				sender = ((ProxiedCommandSender) sender).getCallee();
			}
			loc = ((Entity) sender).getLocation();
		} else if (sender instanceof BlockCommandSender) {
			loc = ((BlockCommandSender) sender).getBlock().getLocation();
		} else if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage("The console cannot use relative coordinates!");
			throw new IllegalArgumentException();
		}
		
		List<String> strings = new ArrayList<String>();
		int start_index = -1;
		for (String arg : args) {
			String t_arg;
			arg = arg.replace(" ", "");
			if(arg == "~" || arg.equalsIgnoreCase("~") || arg.matches("~")) {
				t_arg = "0";}
			else {
				t_arg = arg.replace("~", "").replace("^", "").replace(" ", "");}
			if (start_index <= -1) {
				if (Utility.isNumeric(t_arg)) {
					start_index = 0;
					strings.add(t_arg);
				}
			} else if (start_index < 2 && start_index >= 0) {
				strings.add(t_arg);
				start_index += 1;
			} else {
				break;
			}
		}
		
		String strX = strings.get(0).replace("~", "");
		String strY = strings.get(1).replace("~", "");
		String strZ = strings.get(2).replace("~", "");
		if (strX.length() == 0) { strX = "0";}
		if (strY.length() == 0) { strY = "0";}
		if (strZ.length() == 0) { strZ = "0";}
		double X = Double.valueOf(strX);
		double Y = Double.valueOf(strY);
		double Z = Double.valueOf(strZ);
		if (args[0].contains("~")) {loc.add(X, 0, 0);} else {loc.setX(X);}
		if (args[1].contains("~")) {loc.add(0, Y, 0);} else {loc.setY(Y);}
		if (args[2].contains("~")) {loc.add(0, 0, Z);} else {loc.setZ(Z);}
		return loc;
	}
	
	public static boolean relcoordcheck(String[] args) {
		boolean occurence = false;
		for (String arg : args) {
			if(arg.contains("~")||arg.contains("^")) {
				occurence = true;
			}
		}
		return occurence;
	}
}
