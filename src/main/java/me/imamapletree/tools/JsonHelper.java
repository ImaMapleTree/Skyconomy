package me.imamapletree.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.imamapletree.Skyconomy;

public class JsonHelper {
	private final static Skyconomy skyconomy = (Skyconomy) Skyconomy.getInstance();
	public final static JSONArray EMPTY_ARRAY = new JSONArray();
	private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private final static JSONParser parser = new JSONParser();
	
	public static File appendExtension(File file, String extension) {
		if(!extension.toLowerCase().contains("json")) {
			extension = extension + ".json";
		}
		return new File(file.getPath() + "\\" + extension);
	}
	
	public static JSONObject loadJson(File file) {
		JSONObject object = new JSONObject();
		try {
			FileReader infile = new FileReader(file);
			object = (JSONObject) parser.parse(infile);
			infile.close();
		} catch (FileNotFoundException e) {
			SkyLog.log(e);
		} catch (IOException e) {
			SkyLog.log(e);
		} catch (ParseException e) {
			SkyLog.log(e);
		}
		return object;
	}
	
	public static JSONObject loadJson(File file, String extension) {
		File newfile = appendExtension(file, extension);
		if(!newfile.exists()) {
			return null;
		}
		return loadJson(newfile);
	}
	
	public static JSONObject loadJsonFromResources(String resource) {
		InputStream IS = (InputStream) skyconomy.getClass().getClassLoader().getResourceAsStream(resource);
		return InputStreamToJSON(IS);
	}	
	
	public static void saveJson(File file, JSONObject object) {
		try {
			FileWriter openfile = new FileWriter(file);
			openfile.write(gson.toJson(parser.parse(object.toJSONString())));
			openfile.close();
		} catch (IOException | ParseException e) {
			SkyLog.log(e);
			SkyLog.log(object);
		}
	}
	
	public static File saveJson(File file, JSONObject object, String extension) {
		File newfile = appendExtension(file, extension);
		saveJson(newfile, object);
		return newfile;
	}
	
	public static void saveNewJson(File file, JSONObject object) {
		if(file.exists()) {
			return;
		}
		saveJson(file, object);
	}
	
	public static File saveNewJson(File file, JSONObject object, String extension) {
		File newfile = appendExtension(file, extension);
		if(newfile.exists()) {
			return null;
		}
		saveJson(newfile, object);
		return newfile;
	}
	
	public static JSONObject loadNonExistingJson(File file, JSONObject object) {
		if(!file.exists()) {
			saveNewJson(file, object);
			return object;
		}
		return loadJson(file);
	}
	
	public static JSONObject loadNonExistingJson(File file, JSONObject object, String extension) {
		File newfile = appendExtension(file, extension);
		if (!newfile.exists()) {
			saveNewJson(newfile, object);
			return object;
		}
		return loadJson(newfile);
	}
	
	public static JSONObject InputStreamToJSON(InputStream IS) {
		JSONParser JP = new JSONParser();
		try {
			return (JSONObject)JP.parse(new InputStreamReader(IS, "UTF-8"));
		} catch (IOException | ParseException e) {
			SkyLog.log(e);
			return null;
		}
	}
	
	public static String makeJSON(Map<?, ?> map) {
		SkyLog.log(map);
		String ret = gson.toJson(map);
		SkyLog.log(ret);
		JSONObject test = new JSONObject(map);
		SkyLog.log(test);
		return ret;
	}
	
	public static Map<?, ?> makeMap(JSONObject JO) {
		return (Map<?, ?>) gson.fromJson(JO.toJSONString(), HashMap.class);
	}
	
	public static Integer readAsInt(JSONObject JO, Object key) {
		return Integer.valueOf(String.valueOf(JO.get(key)));
	}
	
	@SuppressWarnings("unchecked")
	public static JSONArray parseLocation(Location location) {
		JSONArray JA = new JSONArray();
		JA.add(location.getWorld().getName());
		JA.add(location.getX());
		JA.add(location.getY());
		JA.add(location.getZ());
		JA.add(location.getYaw());
		JA.add(location.getPitch());
		return JA;
	}

	@SuppressWarnings("unchecked")
	public static Object parseLocationNoFail(Location location, String server) {
		JSONArray JA = new JSONArray();
		JA.add(server);
		JA.add(location.getX());
		JA.add(location.getY());
		JA.add(location.getZ());
		JA.add(location.getYaw());
		JA.add(location.getPitch());
		return JA;
	}
	
	public static Location jsonifyLocation(JSONArray JA) {
		World world = skyconomy.getServer().getWorld((String) JA.get(0));
		double X = (double) JA.get(1);
		double Y = (double) JA.get(2);
		double Z = (double) JA.get(3);
		float yaw = (float) Float.valueOf(Double.toString((double) JA.get(4)));
		float pitch = (float) Float.valueOf(Double.toString((double) JA.get(5)));
		return new Location(world, X, Y, Z, yaw, pitch);
	}

	public static JSONObject parseString(String string) {
		try {
			return (JSONObject) parser.parse(string);
		} catch (ParseException e) {
			SkyLog.log(e);
			return null;
		}
	}


}
