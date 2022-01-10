package me.imamapletree.api.bungee.bridge;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.bukkit.World;
import org.json.simple.JSONObject;

import com.google.common.io.ByteArrayDataOutput;

import me.imamapletree.Skyconomy;
import me.imamapletree.WorldLinker;
import me.imamapletree.api.bungee.stream.ByteSender;
import me.imamapletree.api.instances.Island;
import me.imamapletree.api.instances.SkyPlayer;
import me.imamapletree.tools.JsonHelper;
import me.imamapletree.tools.SkyLog;
import me.imamapletree.tools.Utility;

public class PlayerBridge {
	public static void sendPlayer(String json, String world) {
		String world_name;
		if(world != null) world_name = world;
		else world_name = "null";
		List<String> worlds = WorldLinker.getInstance().getAssociatedWorlds(WorldLinker.getInstance().getSuperWorld(Utility.stripWorldFolder(world_name)));
		if(worlds.size() == 0) worlds.add("ALL");
		worlds.remove(Utility.stripWorldFolder(world_name));
		for(String sworld : worlds) {
			ByteArrayDataOutput out = ByteSender.out();
			out.writeUTF("Forward");
			out.writeUTF(sworld);
			out.writeUTF("NewPlayer");
			ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
			DataOutputStream msgout = new DataOutputStream(msgbytes);
			try {
				msgout.writeUTF(json); // You can do anything you want with msgout
			} catch (IOException exception){
				exception.printStackTrace();
			}
			out.writeShort(msgbytes.toByteArray().length);
			out.write(msgbytes.toByteArray());
			ByteSender.send(out);
		}
		
	}
	
	public static void sendPlayer(String json) {
		sendPlayer(json, null);
	}
	
	public static void sendPlayer(JSONObject json) {
		sendPlayer(json.toJSONString(), null);
	}
	
	public static void sendPlayer(SkyPlayer player) {
		sendPlayer(player.getJSON().toJSONString(), null);
	}
	
	public static void sendPlayer(JSONObject json, String world) {
		sendPlayer(json.toJSONString(), world);
	}
	
	public static void sendPlayer(SkyPlayer player, String world) {
		sendPlayer(player.getJSON().toJSONString(), world);
	}
	
	public static void registerIsland(String playerUUID, String islandUUID, String world) {
		List<String> worlds = Utility.cleanAssociatedWorlds(world);
		for(String sworld : worlds) {
			ByteArrayDataOutput out = ByteSender.out();
			out.writeUTF("Forward");
			out.writeUTF(sworld);
			out.writeUTF("registerIsland");
			ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
			DataOutputStream msgout = new DataOutputStream(msgbytes);
			try {
				msgout.writeUTF(playerUUID); // You can do anything you want with msgout
				msgout.writeUTF(islandUUID);
			} catch (IOException exception){
				SkyLog.log(exception);
			}
			out.writeShort(msgbytes.toByteArray().length);
			out.write(msgbytes.toByteArray());
			ByteSender.send(out);
		}
	}
	
	public static void registerIsland(String playerUUID, String islandUUID) {
		registerIsland(playerUUID, islandUUID, null);
	}
	
	public static void askServerQuery(String playername) {
		ByteArrayDataOutput out = ByteSender.out();
		out.writeUTF("Forward");
		out.writeUTF("ALL");
		out.writeUTF("ServerQuery");
		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(Utility.stripWorldFolder(ByteSender.getRandomPlayer().getWorld().getName()));
			msgout.writeUTF(playername); // You can do anything you want with msgout
		} catch (IOException exception){
			SkyLog.log(exception);
		}
		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());
		ByteSender.send(out);
	}
	
	public static void answerServerQuery(String source, String player, String server) {
		ByteArrayDataOutput out = ByteSender.out();
		out.writeUTF("Forward");
		out.writeUTF(source);
		out.writeUTF("answeredServerQuery");
		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(player);
			msgout.writeUTF(server); // You can do anything you want with msgout
		} catch (IOException exception){
			SkyLog.log(exception);
		}
		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());
		ByteSender.send(out);
	}
	
	public static void updatePlayerlist(String world) {
		List<String> worlds = Utility.cleanAssociatedWorlds(world);
		for(String sworld : worlds) {
			ByteArrayDataOutput out = ByteSender.out();
			out.writeUTF("PlayerList");
			out.writeUTF(sworld);
			ByteSender.send(out);
		}
	}
	
	public static void updatePlayerlist() {
		updatePlayerlist(null);
	}
	
	public static void cachePlayerUUID(String name) {
		ByteArrayDataOutput out = ByteSender.out();
		out.writeUTF("UUIDOther");
		out.writeUTF(name);
		ByteSender.send(out);
	}

	public static void addPlayer(String jstring) {
		JSONObject SJO = JsonHelper.parseString(jstring);
		SkyPlayer skyplayer = new SkyPlayer(SJO);
		Skyconomy.getInstance().getPlayerManager().add(skyplayer);
	}
	
	public static void updatePlayerIslandRegistry(String playerUUID, String islandUUID) {
		Island island = Skyconomy.getInstance().getIslandManager().getIsland(islandUUID);
		if (island == null) return;
		World world = island.getWorld();
		SkyPlayer skyplayer = Skyconomy.getInstance().getPlayerManager().getSkyPlayer(UUID.fromString(playerUUID)).orElse(null);
		if(skyplayer == null) return;
		skyplayer.addIsland(world, island);
	}

	public static void sendTP(String uuid, String WCS, String server) {
		ByteArrayDataOutput out = ByteSender.out();
		out.writeUTF("Forward");
		out.writeUTF(server);
		out.writeUTF("BungeeTP");
		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(uuid);
			msgout.writeUTF(WCS); // You can do anything you want with msgout
		} catch (IOException exception){
			SkyLog.log(exception);
		}
		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());
		ByteSender.send(out);
	}
}
