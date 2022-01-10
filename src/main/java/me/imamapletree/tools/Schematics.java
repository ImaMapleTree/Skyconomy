package me.imamapletree.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.bukkit.Location;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;

import me.imamapletree.Skyconomy;

public class Schematics {
	private static Skyconomy skyconomy = Skyconomy.getInstance();
	
	public static Clipboard createFileClipboard(File file) throws FileNotFoundException, IOException {
		Clipboard clipboard;
		ClipboardFormat format = ClipboardFormats.findByFile(file);
		try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
		    clipboard = reader.read();
		}
		return clipboard;
	}
	
	public static Clipboard createFileClipboard(String string) throws FileNotFoundException, IOException {
		if (!string.contains(".schem")) string = string + ".schem";
		File newfile = new File(skyconomy.getResourceManager().SCHEMATICS_FOLDER.getPath() + "\\" + string);
		Clipboard clipboard;
		ClipboardFormat format = ClipboardFormats.findByFile(newfile);
		try (ClipboardReader reader = format.getReader(new FileInputStream(newfile))) {
		    clipboard = reader.read();
		}
		return clipboard;
	}
	
	public static void save(Clipboard clipboard, File file) throws IOException {
		try (ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file))) {
		    writer.write(clipboard);
		}
	}
	
	public static boolean load(Location loc, Clipboard clipboard) throws FileNotFoundException, IOException, WorldEditException {
		try (@SuppressWarnings("deprecation")
		EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession((World) BukkitAdapter.adapt(loc.getWorld()), -1)) {
			Operation operation = new ClipboardHolder(clipboard)
		            .createPaste(editSession)
		            .to(BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()))
		        .build();
			Operations.complete(operation);
		}
		return true;
	}
}
