package me.imamapletree.tools;


import java.util.logging.Logger;

import org.bukkit.Bukkit;

import me.imamapletree.Settings;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.WorldCoordinate;
import net.md_5.bungee.api.ChatColor;

public class SkyLog {
	private static final Skyconomy skyconomy = (Skyconomy) Skyconomy.getInstance();
	private static final Logger log = skyconomy.getLogger();
	private static Settings settings = skyconomy.getSettings();
	
	public static void log (String string){
		log.info(string);
		if (settings.ChatOutput == true) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "[Skyconomy] " + ChatColor.RESET + string);
		}
		return;
	}
	
	public static void log (Throwable error){
		SkyLog.log(error.getClass().toString(), error.getStackTrace());
		log.info(ChatColor.RED + "[ERROR] " + error.toString());
		if (settings.ChatErrorOutput == true) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "[Skyconomy] " + ChatColor.RED + "[ERROR] "  + error);
		}
		return;
	}
	
	public static void log (String cause, StackTraceElement[] stes) {
		String result = cause + "\n";
		String nextTrace = "";
		String CnextTrace = "";
		if (settings.ChatErrorOutput == true) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "[Skyconomy] " + ChatColor.RED + "[TRACE] " + cause.replace("class ", ""));
		}
		for (int i = 0; i < stes.length; i++) {
			CnextTrace = stes[i].getMethodName() + "(" + stes[i].getFileName() + ":" + stes[i].getLineNumber() +")";
			nextTrace = "at " + stes[i].getClassName() + "." + stes[i].getMethodName() + "(" + stes[i].getFileName() + ":" + stes[i].getLineNumber() +")";
			if (settings.ChatErrorOutput == true) {
				Bukkit.broadcastMessage(ChatColor.RED + CnextTrace);
			}
			
		    result = result + nextTrace + "\n";
		  }

		log.info(ChatColor.RED + "[TRACE] " + result);
		
	}
	
	public static void log (int number) {
		log.info(String.valueOf(number));
		if (settings.ChatOutput == true) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "[Skyconomy] " + ChatColor.RESET + String.valueOf(number));
		}
	}
	
	public static void log (long number) {
		log.info(String.valueOf(number));
		if (settings.ChatOutput == true) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "[Skyconomy] " + ChatColor.RESET + String.valueOf(number));
		}
	}
	
	public static void log (double number) {
		log.info(String.valueOf(number));
		if (settings.ChatOutput == true) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "[Skyconomy] " + ChatColor.RESET + String.valueOf(number));
		}
	}
	
	public static void log (float number) {
		log.info(String.valueOf(number));
		if (settings.ChatOutput == true) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "[Skyconomy] " + ChatColor.RESET + String.valueOf(number));
		}
	}
	
	public static void log (boolean truth) {
		log.info(String.valueOf(truth));
		if (settings.ChatOutput == true) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "[Skyconomy] " + ChatColor.RESET + String.valueOf(truth));
		}
	}
	
	public static void log (WorldCoordinate WC) {
		log.info(WC.toString());
		if (settings.ChatOutput == true) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "[Skyconomy] " + ChatColor.RESET + WC.toString());
		}
	}

	public static void log (Object obj) {
		try {
			if(obj == null) {
				log.info("Null");
				if (settings.ChatOutput == true) {
					Bukkit.broadcastMessage(ChatColor.GREEN + "[Skyconomy] " + ChatColor.RESET + "Null");
				}
				return;
			}
			log.info(ChatColor.YELLOW + "[" + Utility.type(obj) + "] " + ChatColor.RESET + String.valueOf(obj));
			if (settings.ChatOutput == true) {
				Bukkit.broadcastMessage(ChatColor.GREEN + "[Skyconomy] " + ChatColor.YELLOW + "[" + Utility.type(obj) + "] " + ChatColor.RESET + String.valueOf(obj));
			}
			return;
		} catch (Error e) {
			return;
		}		
	}

	public static void slog (String string){
		log.info(string);
		return;
	}
	
	public static void slog (Throwable error){
		SkyLog.log(error.getClass().toString(), error.getStackTrace());
		log.info(ChatColor.RED + "[ERROR] " + error.toString());
		return;
	}
	
	public static void slog (String cause, StackTraceElement[] stes) {
		String result = cause + "\n";
		String nextTrace = "";
		for (int i = 0; i < stes.length; i++) {
			nextTrace = "at " + stes[i].getClassName() + "." + stes[i].getMethodName() + "(" + stes[i].getFileName() + ":" + stes[i].getLineNumber() +")";	
		    result = result + nextTrace + "\n";
		  }

		log.info(ChatColor.RED + "[TRACE] " + result);
		
	}
	
	public static void slog (int number) {
		log.info(String.valueOf(number));
	}
	
	public static void slog (long number) {
		log.info(String.valueOf(number));
	}
	
	public static void slog (double number) {
		log.info(String.valueOf(number));
	}
	
	public static void slog (float number) {
		log.info(String.valueOf(number));
	}
	
	public static void slog (boolean truth) {
		log.info(String.valueOf(truth));
	}
	
	public static void slog (WorldCoordinate WC) {
		log.info(WC.toString());
	}

	public static void slog (Object obj) {
		try {
			if(obj == null) {
				log.info("Null");
				return;
			}
			log.info(ChatColor.YELLOW + "[" + Utility.type(obj) + "] " + ChatColor.RESET + String.valueOf(obj));
			return;
		} catch (Error e) {
			return;
		}		
	}

}
