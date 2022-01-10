package me.imamapletree.tools;

import org.bukkit.Location;
import org.bukkit.World;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.RegionFunction;
import com.sk89q.worldedit.function.biome.BiomeReplace;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.function.visitor.RegionVisitor;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.biome.BiomeType;

public class WEUtility {
	@SuppressWarnings("deprecation")
	public static EditSession createEditSession(Location loc) {
		try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(loc.getWorld()), -1)) {
			return editSession;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static EditSession createEditSession(World world) {
		try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(world), -1)) {
			return editSession;
		}
	}
	
	public static int setBiome(EditSession es, CuboidRegion cr, String biome) {
		SkyLog.log(cr.getVolume());
		BiomeType target = new BiomeType(Utility.getBiomeID(biome));
		RegionFunction replace = new BiomeReplace(es, target);
        RegionVisitor visitor = new RegionVisitor(cr, replace);
        try {
			Operations.completeLegacy(visitor);
		} catch (MaxChangedBlocksException e) {
			SkyLog.slog(e);
		}
        return visitor.getAffected();
	}
}
