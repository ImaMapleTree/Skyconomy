package me.imamapletree.api.bungee.bridge;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import com.google.common.io.ByteArrayDataOutput;

import me.imamapletree.Skyconomy;
import me.imamapletree.WorldLinker;
import me.imamapletree.api.bungee.stream.ByteSender;
import me.imamapletree.tools.Utility;

public class MessageBridge {
	@SuppressWarnings("unused")
	private static Skyconomy skyconomy = Skyconomy.getInstance();
	
	public static void requestPlayerStringList(String uuid) {
		requestPlayerStringList(uuid, null);
	}
	
	public static void requestPlayerStringList(String uuid, String world) {
		if(world == null) {
			ByteArrayDataOutput out = ByteSender.out();
			out.writeUTF("PlayerList");
			out.writeUTF("ALL");
			ByteSender.sendAndAttach(out, uuid);
		} else {
			List<String> worlds = WorldLinker.getInstance().getAssociatedWorlds(WorldLinker.getInstance().getSuperWorld(world));
			if(worlds.size() == 0) requestPlayerStringList(uuid, null);
			for(String world_string : worlds) {
				ByteArrayDataOutput out = ByteSender.out();
				out.writeUTF("PlayerList");
				out.writeUTF(world_string);
				ByteSender.sendAndAttach(out, uuid);
			}
		}
	}

	public static void sendGlobalMessage(String message, String world) {
		if(world == null) {
			ByteArrayDataOutput out = ByteSender.out();
			out.writeUTF("Forward");
			out.writeUTF("ALL");
			out.writeUTF("GlobalMessage");
			ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
			DataOutputStream msgout = new DataOutputStream(msgbytes);
			try {
				msgout.writeUTF(message); // You can do anything you want with msgout
			} catch (IOException exception){
				exception.printStackTrace();
			}
			out.writeShort(msgbytes.toByteArray().length);
			out.write(msgbytes.toByteArray());
			ByteSender.send(out);
		} else {
			List<String> worlds = WorldLinker.getInstance().getAssociatedWorlds(WorldLinker.getInstance().getSuperWorld(Utility.stripWorldFolder(world)));
			if(worlds.size() == 0) sendGlobalMessage(message, null);
			worlds.remove(Utility.stripWorldFolder(world));
			for(String world_string : worlds) {
				ByteArrayDataOutput out = ByteSender.out();
				out.writeUTF("Forward");
				out.writeUTF(world_string);
				out.writeUTF("GlobalMessage");
				ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
				DataOutputStream msgout = new DataOutputStream(msgbytes);
				try {
					msgout.writeUTF(message); // You can do anything you want with msgout
				} catch (IOException exception){
					exception.printStackTrace();
				}
				out.writeShort(msgbytes.toByteArray().length);
				out.write(msgbytes.toByteArray());
				ByteSender.send(out);
			}
		}
	}
}

