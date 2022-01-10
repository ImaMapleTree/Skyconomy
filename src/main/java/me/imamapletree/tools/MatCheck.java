package me.imamapletree.tools;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

public class MatCheck {
	//Lists won't be updated in future versions unless a big update happens to Skyconomy.
	private static List<Material> containers = Arrays.asList(Material.LIGHT_GRAY_SHULKER_BOX, Material.BLACK_SHULKER_BOX, Material.BLUE_SHULKER_BOX, Material.BROWN_SHULKER_BOX,
			Material.CYAN_SHULKER_BOX, Material.GRAY_SHULKER_BOX, Material.GREEN_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX, Material.LIME_SHULKER_BOX,
			Material.MAGENTA_SHULKER_BOX, Material.ORANGE_SHULKER_BOX, Material.PINK_SHULKER_BOX, Material.PURPLE_SHULKER_BOX, Material.RED_SHULKER_BOX,
			Material.WHITE_SHULKER_BOX, Material.YELLOW_SHULKER_BOX, Material.BARREL, Material.CHEST, Material.DROPPER, Material.DISPENSER, Material.TRAPPED_CHEST);
	
	private static List<Material> breedables = Arrays.asList(Material.GOLDEN_APPLE, Material.GOLDEN_CARROT, Material.WHEAT, Material.CARROT, Material.POTATO, Material.BEETROOT,
			Material.WHEAT_SEEDS, Material.PUMPKIN_SEEDS, Material.MELON_SEEDS, Material.BEETROOT_SEEDS, Material.PORKCHOP, Material.BEEF, Material.CHICKEN,
			Material.RABBIT, Material.MUTTON, Material.ROTTEN_FLESH, Material.COOKED_BEEF, Material.COOKED_CHICKEN, Material.COOKED_PORKCHOP, Material.COOKED_RABBIT,
			Material.COOKED_MUTTON, Material.COD, Material.SALMON, Material.DANDELION, Material.HAY_BLOCK, Material.SEAGRASS, Material.BAMBOO, Material.SWEET_BERRIES,
			Material.CRIMSON_FUNGUS, Material.WARPED_FUNGUS);
	
	private static List<Material> beds = Arrays.asList(Material.WHITE_BED, Material.ORANGE_BED, Material.MAGENTA_BED, Material.LIGHT_BLUE_BED, Material.YELLOW_BED,
			Material.LIME_BED, Material.PINK_BED, Material.GRAY_BED, Material.LIGHT_GRAY_BED, Material.CYAN_BED, Material.PURPLE_BED, Material.BLUE_BED,
			Material.BROWN_BED, Material.GREEN_BED, Material.RED_BED, Material.BLACK_BED);
	
	private static List<Material> doors = Arrays.asList(Material.ACACIA_DOOR, Material.BIRCH_DOOR, Material.CRIMSON_DOOR, Material.DARK_OAK_DOOR, Material.JUNGLE_DOOR,
			Material.OAK_DOOR, Material.SPRUCE_DOOR, Material.WARPED_DOOR);
	
	private static List<Material> furnaces = Arrays.asList(Material.BLAST_FURNACE, Material.FURNACE, Material.SMOKER);
	
	private static List<Material> gates = Arrays.asList(Material.ACACIA_FENCE_GATE, Material.BIRCH_FENCE_GATE, Material.CRIMSON_FENCE_GATE,
			Material.DARK_OAK_FENCE_GATE, Material.JUNGLE_FENCE_GATE, Material.OAK_FENCE_GATE, Material.SPRUCE_FENCE_GATE, Material.WARPED_FENCE_GATE);
	
	private static List<Material> redstone_switches = Arrays.asList(Material.ACACIA_BUTTON, Material.BIRCH_BUTTON, Material.CRIMSON_BUTTON, Material.DARK_OAK_BUTTON,
			Material.JUNGLE_BUTTON, Material.OAK_BUTTON, Material.POLISHED_BLACKSTONE_BUTTON, Material.SPRUCE_BUTTON, Material.SPRUCE_BUTTON, Material.STONE_BUTTON, Material.WARPED_BUTTON,
			Material.LEVER);
	
	private static List<Material> pressure_plates = Arrays.asList(Material.ACACIA_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.CRIMSON_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE,
			Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE, Material.OAK_PRESSURE_PLATE, Material.POLISHED_BLACKSTONE_PRESSURE_PLATE,
			Material.SPRUCE_PRESSURE_PLATE, Material.STONE_PRESSURE_PLATE, Material.WARPED_PRESSURE_PLATE);
	
	private static List<Material> redstone = Arrays.asList(Material.ACACIA_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.CRIMSON_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE,
			Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE, Material.OAK_PRESSURE_PLATE, Material.POLISHED_BLACKSTONE_PRESSURE_PLATE,
			Material.SPRUCE_PRESSURE_PLATE, Material.STONE_PRESSURE_PLATE, Material.WARPED_PRESSURE_PLATE, Material.ACACIA_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.CRIMSON_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE,
			Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE, Material.OAK_PRESSURE_PLATE, Material.POLISHED_BLACKSTONE_PRESSURE_PLATE,
			Material.SPRUCE_PRESSURE_PLATE, Material.STONE_PRESSURE_PLATE, Material.WARPED_PRESSURE_PLATE, Material.REDSTONE, Material.REDSTONE_WIRE, Material.REDSTONE_WALL_TORCH,
			Material.REDSTONE_TORCH, Material.REPEATER, Material.DROPPER, Material.DISPENSER, Material.TRAPPED_CHEST, Material.TNT, Material.OBSERVER, Material.ACTIVATOR_RAIL,
			Material.DAYLIGHT_DETECTOR, Material.TARGET, Material.LECTERN, Material.TRIPWIRE_HOOK, Material.COMPARATOR, Material.NOTE_BLOCK);
	
	private static List<Material> spawn_eggs = Arrays.asList(Material.BAT_SPAWN_EGG, Material.BEE_SPAWN_EGG, Material.BLAZE_SPAWN_EGG, Material.CAT_SPAWN_EGG, Material.CAVE_SPIDER_SPAWN_EGG,
			Material.CHICKEN_SPAWN_EGG, Material.COD_SPAWN_EGG, Material.COW_SPAWN_EGG, Material.CREEPER_SPAWN_EGG, Material.DOLPHIN_SPAWN_EGG, Material.DONKEY_SPAWN_EGG, Material.DROWNED_SPAWN_EGG,
			Material.ELDER_GUARDIAN_SPAWN_EGG, Material.ENDERMAN_SPAWN_EGG, Material.ENDERMITE_SPAWN_EGG, Material.EVOKER_SPAWN_EGG, Material.FOX_SPAWN_EGG, Material.GHAST_SPAWN_EGG, Material.GUARDIAN_SPAWN_EGG,
			Material.HOGLIN_SPAWN_EGG, Material.HUSK_SPAWN_EGG, Material.LLAMA_SPAWN_EGG, Material.MAGMA_CUBE_SPAWN_EGG, Material.PANDA_SPAWN_EGG, Material.PHANTOM_SPAWN_EGG,
			Material.PIGLIN_SPAWN_EGG, Material.PILLAGER_SPAWN_EGG, Material.POLAR_BEAR_SPAWN_EGG, Material.RAVAGER_SPAWN_EGG, Material.SHULKER_SPAWN_EGG, Material.SILVERFISH_SPAWN_EGG,
			Material.SKELETON_SPAWN_EGG, Material.SLIME_SPAWN_EGG, Material.SPIDER_SPAWN_EGG, Material.STRAY_SPAWN_EGG, Material.STRIDER_SPAWN_EGG, Material.VEX_SPAWN_EGG, Material.VINDICATOR_SPAWN_EGG,
			Material.WITCH_SPAWN_EGG, Material.WITHER_SKELETON_SPAWN_EGG, Material.WOLF_SPAWN_EGG, Material.ZOGLIN_SPAWN_EGG, Material.ZOMBIE_SPAWN_EGG,Material.ZOMBIE_VILLAGER_SPAWN_EGG,
			Material.ZOMBIFIED_PIGLIN_SPAWN_EGG, Material.HORSE_SPAWN_EGG, Material.MOOSHROOM_SPAWN_EGG, Material.MULE_SPAWN_EGG, Material.OCELOT_SPAWN_EGG, Material.PARROT_SPAWN_EGG,
			Material.PIG_SPAWN_EGG, Material.PUFFERFISH_SPAWN_EGG, Material.RABBIT_SPAWN_EGG, Material.SALMON_SPAWN_EGG, Material.SHEEP_SPAWN_EGG, Material.SKELETON_HORSE_SPAWN_EGG,
			Material.SQUID_SPAWN_EGG, Material.TROPICAL_FISH_SPAWN_EGG, Material.TURTLE_SPAWN_EGG, Material.VILLAGER_SPAWN_EGG, Material.WANDERING_TRADER_SPAWN_EGG, Material.ZOMBIE_HORSE_SPAWN_EGG);
	
	private static List<Material> animaltools = Arrays.asList(Material.SHEARS, Material.BUCKET, Material.BOWL);
	
	private static List<Material> fireitems = Arrays.asList(Material.FLINT_AND_STEEL, Material.FIRE_CHARGE);
	
	private static List<Material> trapdoors = Arrays.asList(Material.ACACIA_TRAPDOOR, Material.BIRCH_TRAPDOOR, Material.CRIMSON_TRAPDOOR, Material.DARK_OAK_TRAPDOOR, Material.JUNGLE_TRAPDOOR,
			Material.IRON_TRAPDOOR, Material.OAK_TRAPDOOR, Material.SPRUCE_TRAPDOOR, Material.WARPED_TRAPDOOR);
	
	public static boolean isContainer(Material m) {
		return containers.contains(m);
	}
	
	public static boolean isBreedable(Material m) {
		return breedables.contains(m);
	}

	public static boolean isBed(Material m) {
		return beds.contains(m);
	}

	public static boolean isDoor(Material m) {
		return doors.contains(m);
	}
	
	public static boolean isFurnace(Material m) {
		return furnaces.contains(m);
	}

	public static boolean isGate(Material m) {
		return gates.contains(m);
	}

	public static boolean isSwitch(Material m) {
		return redstone_switches.contains(m);
	}
	
	public static boolean isPressurePlate(Material m) {
		return pressure_plates.contains(m);
	}

	public static boolean isRedstone(Material m) {
		return redstone.contains(m);
	}
	
	public static boolean isAnimalTool(Material m) {
		return animaltools.contains(m);
	}
	
	public static boolean isSpawnEgg(Material m) {
		return spawn_eggs.contains(m);
	}
	
	public static boolean isFireStarter(Material m) {
		return fireitems.contains(m);
	}

	public static boolean isTrapdoor(Material m) {
		return trapdoors.contains(m);
	}
}
