package me.imamapletree.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.Clipboard;

import me.imamapletree.database.DataFolder;
import me.imamapletree.tools.RelativeLocationParse;
import me.imamapletree.tools.Schematics;

public class StructureCommands {
	public static boolean loadSchematic(CommandSender sender, Command command, String label2, String[] args) throws FileNotFoundException, IOException, WorldEditException{
		if (args.length == 4) {
			Location loc = RelativeLocationParse.tildeParse(sender, args);
			File file = new DataFolder("schematics\\skyblock").getFile();
			File schematic = new File(file.getPath() + "\\" + args[3] + ".schem");
			Clipboard clipboard = Schematics.createFileClipboard(schematic);
			Schematics.load(loc, clipboard);
			return true;
		}
		return false;
	}
	
	
}
