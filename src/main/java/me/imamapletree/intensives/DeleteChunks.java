package me.imamapletree.intensives;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;

import me.imamapletree.api.ChunkCoordinate;
import me.imamapletree.api.NMS.NMSDelete;
import me.imamapletree.tools.MatCheck;

public class DeleteChunks implements Runnable {
	private ChunkCoordinate[] ccoords;
	private World world;
	private byte zeroasbyte = new Integer(0).byteValue();
	
	public DeleteChunks(World world, ChunkCoordinate[] ChunkCoords) {
		this.ccoords = ChunkCoords;
		this.world = world;
	}
	
	@SuppressWarnings("deprecation")
	public void run() {
		Integer X;
		Integer Z;
		X = ccoords[0].getRealX();
		Z = ccoords[0].getRealZ();
		X = ccoords[ccoords.length-1].getRealX();
		Z = ccoords[ccoords.length-1].getRealZ();
		for (ChunkCoordinate coord : this.ccoords) {
			X = coord.getRealX();
			Z = coord.getRealZ();
			for (int x = 0; x < 16; x++) {
				for(int z = 0; z < 16; z++) {
					for(int y = 0; y < 256; y++) {
						Material mat = world.getBlockAt(X+x, y, Z+z).getType();
						if(mat == Material.LEGACY_AIR || mat == Material.AIR) {}
						else {
							//SkyLog.log(mat);
							if(MatCheck.isContainer(mat)) {
								BlockState bs = world.getBlockAt(X+x, y, Z+z).getState();
								if (bs instanceof Container) {
									((Container) bs).getInventory().clear();
								}
							} NMSDelete.lessIntensiveBlockReplace(world, X+x, y, Z+z, 0, zeroasbyte, false);
						}
					}
				}
			}
			
		}
	}
	
}
