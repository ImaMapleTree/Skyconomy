package me.imamapletree.database;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONObject;

import me.imamapletree.Skyconomy;
import me.imamapletree.tools.JsonHelper;

public class DataFolder {
	private Skyconomy skyconomy = (Skyconomy) Skyconomy.getInstance();
	public File data_folder = skyconomy.getDataFolder();
	public DataFolder() {
	}
	
	public DataFolder(String deeper) {
		String file_string = this.data_folder.getPath();
		this.data_folder = new File(file_string + "\\" + deeper);
		if (!this.data_folder.exists()) {
			this.data_folder.mkdirs();
		}
	}
	
	public DataFolder(String deeper, String extensions) {
		try {
			String file_string = this.data_folder.getPath();
			this.data_folder = new File(file_string + "\\" + deeper);
			if (!this.data_folder.exists()) {
				this.data_folder.mkdirs();
			}
			String FS = this.data_folder.getPath() + "\\" + extensions;
			this.data_folder = new File(FS);
			if (!this.data_folder.exists()) {
				this.data_folder.createNewFile();
			}
		} catch (IOException e) {
			Bukkit.getLogger().info(e.toString());
			Bukkit.getLogger().info("[Critical Error] Folder could not be created, disabling plugin.");
			Bukkit.getPluginManager().disablePlugin((Plugin) Bukkit.getPluginManager().getPlugin("Skyconomy"));
		}
	}
	
	public DataFolder(String deeper, String extensions, JSONObject json) {
		try {
			String file_string = this.data_folder.getPath();
			this.data_folder = new File(file_string + "\\" + deeper);
			if (!this.data_folder.exists()) {
				this.data_folder.mkdirs();
			}
			String FS = this.data_folder.getPath() + "\\" + extensions;
			this.data_folder = new File(FS);
			if (!this.data_folder.exists()) {
				this.data_folder.createNewFile();
				JsonHelper.saveJson(this.data_folder, json);
			}
		} catch (IOException e) {
			Bukkit.getLogger().info(e.toString());
			Bukkit.getLogger().info("[Critical Error] Folder could not be created, disabling plugin.");
			Bukkit.getPluginManager().disablePlugin((Plugin) Bukkit.getPluginManager().getPlugin("Skyconomy"));
		}
	}
	
	public File getFile() {
		return this.data_folder;
	}
	
	public static File addBranch(File file, String branch) {
		String FS = file.getPath() + "\\" + branch;
		final File FF = new File(FS);
		if (!FF.exists()) {
			FF.mkdirs();
		}
		return FF;
	}
	
	public static File addBranch(DataFolder DF, String branch) {
		File file = DF.getFile();
		String FS = file.getPath() + "\\" + branch;
		final File FF = new File(FS);
		if (!FF.exists()) {
			FF.mkdirs();
		}
		return FF;
	}
	
	public static File addExtension(File file, String ext) throws IOException {
		String FS = file.getPath() + "\\" + ext;
		final File FF = new File(FS);
		if (!FF.exists()) {
			FF.createNewFile();
		}
		return FF;
	}
	
	public static File addExtension(DataFolder DF, String ext) throws IOException {
		File file = DF.getFile();
		String FS = file.getPath() + "\\" + ext;
		final File FF = new File(FS);
		if (!FF.exists()) {
			FF.createNewFile();
		}
		return FF;
	}
}
