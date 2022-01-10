package me.imamapletree.tools;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.ChatMessage;
import net.minecraft.server.v1_16_R2.IChatBaseComponent;
import net.minecraft.server.v1_16_R2.TileEntityEnchantTable;

public class NMSUtil {
	public static void renameBlock(Block block, String title) {
		TileEntityEnchantTable TE = (TileEntityEnchantTable) ((CraftWorld) block.getWorld()).getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
		IChatBaseComponent base = new ChatMessage(title);
		TE.setCustomName(base);
		TE.update();
	}
}
