package me.imamapletree.regions;

import org.bukkit.World;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;

import me.imamapletree.api.FlatCoordinate;
import me.imamapletree.tools.SkyLog;

public class WEUtil {
	public static CuboidRegion createRegion(World world, FlatCoordinate IC1, FlatCoordinate IC2) {
		BlockVector3 BV1 = BlockVector3.at(IC1.getX(), 0, IC1.getZ());
		BlockVector3 BV2 = BlockVector3.at(IC2.getX(), 256, IC2.getZ());
		return new CuboidRegion(new BukkitWorld(world), BV1, BV2);
	}
	
	@SuppressWarnings("deprecation")
	public static EditSession createSession(World world) {
		return WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(world), -1);
	}

    @SuppressWarnings("deprecation")
	public static boolean regenerateChunk(org.bukkit.Chunk chunk) {
        int bx = chunk.getX() << 4;
        int bz = chunk.getZ() << 4;
        try {
            com.sk89q.worldedit.math.BlockVector3 pt1 =  com.sk89q.worldedit.math.BlockVector3.at(bx, 0, bz);
            com.sk89q.worldedit.math.BlockVector3 pt2 = com.sk89q.worldedit.math.BlockVector3.at(bx + 15, 256, bz + 15);
            com.sk89q.worldedit.bukkit.BukkitWorld world = new com.sk89q.worldedit.bukkit.BukkitWorld(chunk.getWorld());
            com.sk89q.worldedit.regions.CuboidRegion region = new com.sk89q.worldedit.regions.CuboidRegion(world, pt1, pt2);   
            com.sk89q.worldedit.EditSession session = com.sk89q.worldedit.WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, 65536);
            boolean result = world.regenerate(region, session);
            session.flushSession();
            return result;
        } catch (Exception e) {
        	SkyLog.log(e);
            return false;
        }
    }
}
