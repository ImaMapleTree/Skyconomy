package me.imamapletree.intensives;

import java.util.Iterator;

import org.bukkit.World;
import org.bukkit.block.Biome;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.biome.BiomeType;

import me.imamapletree.tools.SkyLog;
import me.imamapletree.tools.Stopwatch;

public class ChangeBiome implements Runnable {
	private CuboidRegion CR;
	private BiomeType biometype;
	
	public ChangeBiome(CuboidRegion CR, BiomeType bt) {
		this.CR = CR;
		this.biometype = bt;
	}
	@Override
	public void run() {
		Stopwatch sw = new Stopwatch();
		sw.start();
		Iterator<BlockVector3> iterator = CR.iterator();
		World world = BukkitAdapter.adapt(CR.getWorld());
		Biome biome = BukkitAdapter.adapt(biometype);
		while (iterator.hasNext()) {
			BlockVector3 b = (BlockVector3) iterator.next();
			BukkitAdapter.adapt(world, b).getBlock().setBiome(biome);
		}
		SkyLog.slog("Biome change took: (seconds)");
		SkyLog.slog(sw.timeSince());
	}
}
