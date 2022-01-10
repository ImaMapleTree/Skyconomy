package me.imamapletree.world.generators;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class Generator1 extends ChunkGenerator {
	int currentHeight = 100;
	Double blockChance = 5.0;
	//List<Material> MATLIST = Arrays.asList(Material.CLAY, Material.QUARTZ_BLOCK, Material.OBSIDIAN, Material.DIORITE, Material.PRISMARINE, Material.WARPED_WART_BLOCK, Material.LIGHT_BLUE_TERRACOTTA);
	//List<Material> MATLIST = Arrays.asList(Material.BLUE_CONCRETE, Material.ORANGE_CONCRETE, Material.YELLOW_CONCRETE, Material.WHITE_CONCRETE, Material.RED_CONCRETE, Material.LIME_CONCRETE, Material.PINK_CONCRETE);
	//List<Material> MATLIST = Arrays.asList(Material.BLACKSTONE, Material.COBBLESTONE, Material.RED_SANDSTONE, Material.BROWN_TERRACOTTA);
	//List<Material> MATLIST = Arrays.asList(Material.GREEN_TERRACOTTA, Material.BROWN_TERRACOTTA, Material.LIME_CONCRETE, Material.BROWN_WOOL, Material.GRAY_CONCRETE, Material.DIRT, Material.GRAVEL,
			//Material.OAK_LOG, Material.SOUL_SOIL, Material.BROWN_CONCRETE_POWDER);
	//List<Material> MATLIST = Arrays.asList(Material.SAND, Material.BLACKSTONE, Material.DIORITE, Material.ORANGE_TERRACOTTA, Material.TERRACOTTA);
	List<Material> MATLIST = Arrays.asList(Material.DIAMOND_BLOCK, Material.HAY_BLOCK, Material.NETHER_BRICKS, Material.RED_MUSHROOM, Material.ACACIA_WOOD, Material.PURPUR_BLOCK, Material.COAL_BLOCK);
	
	BlockGenerator blockgen = new BlockGenerator(MATLIST, new Random());
	Material currentBlock;
	
	@Override
	public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
		SimplexOctaveGenerator generator = new SimplexOctaveGenerator(new Random(world.getSeed()), 32);
		//generator.setScale(0.01D);
		generator.setScale(0.05D);

		
		ChunkData chunk = createChunkData(world);
		for(int x = 0; x < 16; x++) {
			for(int z = 0; z < 16; z++) {
				//currentHeight = ((int) (generator.noise(chunkX*16+x, chunkZ*16+z, 0.09D, 0.82D)*47D+90D))+40;
				currentHeight = ((int) (generator.noise(chunkX*16+x, chunkZ*16+z, 0.09D, 0.5D)*20D+90D))+40;
				currentBlock = blockgen.getBlock(chunkX*16+x, chunkZ*16+z);
				chunk.setBlock(x, currentHeight, z, currentBlock);
				for (int i = currentHeight-1; i > 0; i--) {
					//currentBlock = blockgen.getBlock();
					chunk.setBlock(x, i, z, currentBlock);
				}
				chunk.setBlock(x, 0, z, Material.BEDROCK);
			}
		}
		return chunk;
	}
}
