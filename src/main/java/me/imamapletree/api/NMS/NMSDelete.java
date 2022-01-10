package me.imamapletree.api.NMS;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;

import me.imamapletree.tools.SkyLog;
import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.ChunkSection;
import net.minecraft.server.v1_16_R2.IBlockData;

public class NMSDelete {
	
	public static void lessIntensiveBlockReplace(World world, int x, int y, int z, int blockId, byte data, boolean applyPhysics) {
	    net.minecraft.server.v1_16_R2.World nmsWorld = ((CraftWorld) world).getHandle();
	    net.minecraft.server.v1_16_R2.Chunk nmsChunk = nmsWorld.getChunkAt(x >> 4, z >> 4);
	    BlockPosition bp = new BlockPosition(x, y, z);
	    IBlockData ibd = net.minecraft.server.v1_16_R2.Block.getByCombinedId(blockId + (data << 12));
	    nmsChunk.setType(bp, ibd, applyPhysics);
	}
	
	public static void superIntensiveBlockReplace(World world, int x, int y, int z, int blockId, byte data, boolean applyPhysics) {
		if(y == 0) {
			y = 1;
		}
		
	    net.minecraft.server.v1_16_R2.World nmsWorld = ((CraftWorld) world).getHandle();
	    net.minecraft.server.v1_16_R2.Chunk nmsChunk = nmsWorld.getChunkAt(x >> 4, z >> 4);
	    IBlockData ibd = net.minecraft.server.v1_16_R2.Block.getByCombinedId(blockId + (data << 12));

	    ChunkSection cs = nmsChunk.getSections()[y >> 4];
	    if (cs == nmsChunk.a()) {
	        cs = new ChunkSection(y >> 4 << 4);
	        nmsChunk.getSections()[y >> 4] = cs;
	    }

	    if(cs == null) {
	    	SkyLog.log("Nullify!");
	    	SkyLog.log(y);
	    }
	    if(ibd == null) {
	    	SkyLog.log("Null!");
	    }
	    
	    
	    if (applyPhysics)
	       cs.getBlocks().setBlock(x & 15, y & 15, z & 15, ibd);
	    else
	       cs.getBlocks().b(x & 15, y & 15, z & 15, ibd);
	}
}
