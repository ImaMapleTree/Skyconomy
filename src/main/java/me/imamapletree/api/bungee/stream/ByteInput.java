package me.imamapletree.api.bungee.stream;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.google.common.io.ByteArrayDataInput;

import me.imamapletree.Skyconomy;
import me.imamapletree.WorldLinker;
import me.imamapletree.api.bungee.bridge.IslandBridge;
import me.imamapletree.api.bungee.bridge.PlayerBridge;
import me.imamapletree.api.bungee.commands.DelayWarp;
import me.imamapletree.api.bungee.commands.SendGCMessage;
import me.imamapletree.tools.SkyLog;
import me.imamapletree.tools.Utility;

public class ByteInput {
	private Skyconomy skyconomy;
	
	public ByteInput() {
		this.skyconomy = Skyconomy.getInstance();
	}
	
	public void interpretInput(String subchannel, Player player, ByteArrayDataInput in) {
		if(subchannel.equals("PlayerList")) {
			@SuppressWarnings("unused") String uuid = null;
			String server = in.readUTF();
			if(player.hasMetadata("ByteTag")) {
				uuid = player.getMetadata("ByteTag").get(0).asString();
				if(server.equals(WorldLinker.getInstance().getLastWorld(server))) player.removeMetadata("ByteTag", Skyconomy.getInstance());
			}
			String[] playerlist = in.readUTF().split(", ");
			Skyconomy.getInstance().getCachedInput().recieveCachedList(playerlist);
			if(server.equals(WorldLinker.getInstance().getLastWorld(server))) Skyconomy.getInstance().getCachedInput().enableRewrite();
		} else if(subchannel.equals("UUIDOther")) {
			String name = in.readUTF();
			String playeruuid = Utility.hyphenateUUID(in.readUTF());
			SkyLog.log(name + " " + playeruuid);
			Skyconomy.getInstance().getCachedInput().recieveCachedUUID(name, playeruuid);
		}
		else {
			SkyLog.log("Went to default: " + subchannel);
		}
	}
	
	public DataInputStream readInput(ByteArrayDataInput in) {
		short len = in.readShort();
		byte[] msgbytes = new byte[len];
		in.readFully(msgbytes);
		return new DataInputStream(new ByteArrayInputStream(msgbytes));
	}
	
	public void interpretStream(String subchannel, DataInputStream in) throws IOException {
		SkyLog.slog("Incoming stream");
		SkyLog.slog(subchannel);
		if(subchannel.equals("GlobalMessage")) {
			String message = in.readUTF();
			SendGCMessage.send(message);
		}
		else if(subchannel.equals("NewInvite")) {
			String playeruuid = Utility.hyphenateUUID(in.readUTF());
			String islanduuid = Utility.hyphenateUUID(in.readUTF());
			String targetuuid = Utility.hyphenateUUID(in.readUTF());
			IslandBridge.createInvite(playeruuid, islanduuid, targetuuid);
		}
		else if(subchannel.equals("NewPlayer")) {
			String json = in.readUTF();
			PlayerBridge.addPlayer(json);
		}
		else if(subchannel.equals("NewIsland")) {
			String json = in.readUTF();
			IslandBridge.addIsland(json);
		}
		else if(subchannel.equals("ServerQuery")) {
			String source = in.readUTF();
			String player = in.readUTF();
			String server = Bukkit.getPlayerExact(player).getWorld().getName();
			if(server != null) server = Utility.stripWorldFolder(server);
			else server = "null";
			PlayerBridge.answerServerQuery(source, player, server);
		}
		else if(subchannel.equals("answeredServerQuery")) {
			String player = in.readUTF();
			String server = in.readUTF();
			SkyLog.log("This is the server I got: " + server);
			Skyconomy.getInstance().getCachedInput().recieveCachedServer(player, server);
		}
		else if(subchannel.equals("registerMember")) {
			String member = Utility.hyphenateUUID(in.readUTF());
			String island = Utility.hyphenateUUID(in.readUTF());
			String role = in.readUTF();
			IslandBridge.addMember(member, island, role);
		}
		else if(subchannel.equals("BungeeTP")) {
			String playerUUID = in.readUTF();
			String WCS = in.readUTF();
			Bukkit.getScheduler().scheduleSyncDelayedTask(skyconomy, new DelayWarp(playerUUID, WCS), 10L);
		}
		else {
			SkyLog.log(subchannel);
		}
	}
}
