package me.imamapletree.api.bungee.bridge;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import com.google.common.io.ByteArrayDataOutput;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.FlatCoordinate;
import me.imamapletree.api.Invite;
import me.imamapletree.api.bungee.stream.ByteSender;
import me.imamapletree.api.instances.Island;
import me.imamapletree.api.instances.SkyPlayer;
import me.imamapletree.tools.CoordinateParse;
import me.imamapletree.tools.JsonHelper;
import me.imamapletree.tools.SkyLog;
import me.imamapletree.tools.Utility;

public class IslandBridge {
	public static void sendIsland(String json, String world) {
		List<String> worlds = Utility.cleanAssociatedWorlds(world);
		for(String sworld : worlds) {
			ByteArrayDataOutput out = ByteSender.out();
			out.writeUTF("Forward");
			out.writeUTF(sworld);
			out.writeUTF("NewIsland");
			ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
			DataOutputStream msgout = new DataOutputStream(msgbytes);
			try {
				msgout.writeUTF(json); // You can do anything you want with msgout
			} catch (IOException exception){
				SkyLog.log(exception);
			}
			out.writeShort(msgbytes.toByteArray().length);
			out.write(msgbytes.toByteArray());
			ByteSender.send(out);
		}
		
	}
	
	public static void sendIsland(String json) {
		sendIsland(json, null);
	}
	
	public static void sendIsland(JSONObject json) {
		sendIsland(json.toJSONString(), null);
	}
	
	public static void sendIsland(Island island) {
		sendIsland(island.toJSON().toJSONString(), null);
	}
	
	public static void sendIsland(JSONObject json, String world) {
		sendIsland(json.toJSONString(), world);
	}
	
	public static void sendIsland(Island island, String world) {
		sendIsland(island.toJSON().toJSONString(), world);
	}

	public static void sendInvite(String playerUUID, String islandUUID, String targetUUID, String server) {
		ByteArrayDataOutput out = ByteSender.out();
		out.writeUTF("Forward");
		out.writeUTF(server);
		out.writeUTF("NewInvite");
		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(playerUUID); // You can do anything you want with msgout
			msgout.writeUTF(islandUUID);
			msgout.writeUTF(targetUUID);
		} catch (IOException exception){
			SkyLog.log(exception);
		}
		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());
		ByteSender.send(out);
	}
	
	public static void registerMember(String playerUUID, String islandUUID, String role, String world) {
		List<String> worlds = Utility.cleanAssociatedWorlds(world);
		for(String sworld : worlds) {
			ByteArrayDataOutput out = ByteSender.out();
			out.writeUTF("Forward");
			out.writeUTF(sworld);
			out.writeUTF("registerMember");
			ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
			DataOutputStream msgout = new DataOutputStream(msgbytes);
			try {
				msgout.writeUTF(playerUUID); // You can do anything you want with msgout
				msgout.writeUTF(islandUUID);
				msgout.writeUTF(role);
			} catch (IOException exception){
				SkyLog.log(exception);
			}
			out.writeShort(msgbytes.toByteArray().length);
			out.write(msgbytes.toByteArray());
			ByteSender.send(out);
		}
	}
	
	public static void registerMember(String playerUUID, String islandUUID, String role) {
		registerMember(playerUUID, islandUUID, role, null);
	}
	
	public static void createInvite(String playerUUID, String islandUUID, String targetUUID) {
		OfflinePlayer off_invitee = Bukkit.getOfflinePlayer(UUID.fromString(targetUUID));
		if(!off_invitee.isOnline()) return;
		Player invitee = off_invitee.getPlayer();
		OfflinePlayer inviter = Bukkit.getOfflinePlayer(UUID.fromString(playerUUID));
		Island island = Skyconomy.getInstance().getIslandManager().getIsland(islandUUID);
		if(island == null) return;
		Invite invite = new Invite(island, inviter, invitee);
		SkyPlayer spi = Skyconomy.getInstance().getPlayerManager().getSkyPlayer(invitee.getUniqueId()).orElse(null);
		if(spi == null) return;
		spi.addInvite(invite);
		return;
	}
	
	public static void addMember(String playerUUID, String islandUUID, String role) {
		Island island = Skyconomy.getInstance().getIslandManager().getIsland(islandUUID);
		if(island == null) return;
		island.addMember(playerUUID, role);
	}
	
	public static void addIsland(String jstring) {
		JSONObject IJO = JsonHelper.parseString(jstring);
		Island island = new Island(IJO);
		Skyconomy.getInstance().getIslandManager().add(island);
	}
	
	public static void removeIsland(String flatcoord) {
		FlatCoordinate FC = CoordinateParse.FlatCoordParse(flatcoord);
		Skyconomy.getInstance().getIslandManager().deleteFromCache(FC);
	}
}
