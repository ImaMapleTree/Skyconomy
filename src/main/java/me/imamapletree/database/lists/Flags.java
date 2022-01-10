package me.imamapletree.database.lists;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import me.imamapletree.Settings;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.instances.Flag;
import me.imamapletree.tools.SkyLog;

public class Flags {
	private List<Flag> flags = new ArrayList<Flag>();
	protected Flag UseFly = new Flag("UseFly", Material.FEATHER);
	protected Flag UseBed = new Flag("UseBed", Material.RED_BED);
	protected Flag BreakBlock = new Flag("BreakBlock", Material.IRON_PICKAXE);
	protected Flag PlaceBlock = new Flag("PlaceBlock", Material.GRASS_BLOCK);
	protected Flag UseAnvil = new Flag("UseAnvil", Material.ANVIL);
	protected Flag BreedAnimals = new Flag("BreedAnimals", Material.WHEAT);
	protected Flag UseBeacon = new Flag("UseBeacon", Material.BEACON);
	protected Flag UseContainer = new Flag("UseContainer", Material.CHEST);
	protected Flag UseBrewingStand = new Flag("UseBrewingStand", Material.BREWING_STAND);
	protected Flag AllowCropTrample = new Flag("AllowCropTrample", Material.FARMLAND);
	protected Flag UseDoor = new Flag("UseDoor", Material.OAK_DOOR);
	protected Flag UseFurnace = new Flag("UseFurnace", Material.FURNACE);
	protected Flag AccessAnimalInventory = new Flag("AccessAnimalInventory", Material.LEATHER_HORSE_ARMOR);
	protected Flag UseGate = new Flag("UseGate", Material.OAK_FENCE_GATE);
	protected Flag AllowAnimalRiding = new Flag("AllowAnimalRiding", Material.SADDLE);
	protected Flag AllowHurtingPassiveMobs = new Flag("AllowHurtingPassiveMobs", Material.WOODEN_SWORD);
	protected Flag AllowHurtingHostileMobs = new Flag("AllowHurtingHostileMobs", Material.NETHERITE_SWORD);
	protected Flag AffectHanging = new Flag("AffectHanging", Material.ITEM_FRAME);
	protected Flag AllowMobLeashing = new Flag("AllowMobLeashing", Material.LEAD);
	protected Flag UseSwitch = new Flag("UseSwitch", Material.LEVER);
	protected Flag UsePressurePlate = new Flag("UsePressurePlate", Material.STONE_PRESSURE_PLATE);
	protected Flag PlaceRedstone = new Flag("PlaceRedstone", Material.REDSTONE);
	protected Flag HarvestAnimalProduct = new Flag("HarvestAnimalProduct", Material.MILK_BUCKET);
	protected Flag UseNoteblock = new Flag("UseNoteblock", Material.NOTE_BLOCK);
	protected Flag UseJukebox = new Flag("UseJukebox", Material.JUKEBOX);
	protected Flag AllowPotionThrow = new Flag("AllowPotionThrow", Material.SPLASH_POTION);
	protected Flag AllowSpawnEgg = new Flag("AllowSpawnEgg", Material.ZOMBIE_SPAWN_EGG);
	protected Flag AllowItemDrop = new Flag("AllowItemDrop", Material.DROPPER);
	protected Flag AllowItemPickup = new Flag("AllowItemPickup", Material.HOPPER);
	protected Flag PlaceFire = new Flag("PlaceFire", Material.FLINT_AND_STEEL);
	protected Flag AllowEnderPearling = new Flag("AllowEnderPearling", Material.ENDER_PEARL);
	protected Flag UseTrapdoor = new Flag("UseTrapdoor", Material.OAK_TRAPDOOR);
	protected Flag ChangeRole = new Flag("ChangeRole", Material.GOLD_INGOT);
	protected Flag KickMember = new Flag("KickMember", Material.COBWEB);
	//Global flags
	protected Flag PVP = new Flag("PVP", Material.IRON_SWORD);
	protected Flag DamageByExplosion = new Flag("DamageByExplosion", Material.TNT);
	protected Flag PassiveMobSpawn = new Flag("PassiveMobSpawn", Material.PLAYER_HEAD);
	protected Flag HostileMobSpawn = new Flag("HostileMobSpawn", Material.ZOMBIE_HEAD);
	protected Flag FireSpread = new Flag("FireSpread", Material.FLINT_AND_STEEL);
	protected Flag FallDamage = new Flag("FallDamage", Material.FEATHER);
	
	
	private Skyconomy skyconomy = Skyconomy.getInstance();	
	private Settings settings = skyconomy.getSettings();
	
	public Flags() {
	}
	
	public void UpdateFlags() {
		Field[] fields = Flags.class.getDeclaredFields();
		for (Field field : fields) {
			if (Modifier.isProtected(field.getModifiers())) {
				try {
					Flag flag = (Flag) field.get(this);
					this.flags.add(flag);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					SkyLog.log("Unable to register flags, disabling.");
					Bukkit.getPluginManager().disablePlugin(skyconomy);
				}
			}
		}
	}
	
	public List<Flag> defaultFlags() {
		UpdateFlags();
		return flags;
	}
	
	public Map<Flag, List<String>> attachValue(Map<Flag, List<String>> map) {
		Field[] fields = Flags.class.getDeclaredFields();
		Map<String, List<String>> flag_settings = settings.getFlagSettings();
		Map<Flag, List<String>> result = new HashMap<Flag, List<String>>();
		for (Field field : fields) {
			if (Modifier.isProtected(field.getModifiers())) {
				try {
					Flag flag = (Flag) field.get(this);
					List<String> DefValue = flag_settings.get(field.getName().toUpperCase());
					result.put(flag, DefValue);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					SkyLog.log(ChatColor.RED + "Unable to set flag values, disabling.");
					SkyLog.log(e);
					Bukkit.getPluginManager().disablePlugin(skyconomy);
				}
			}
		}
		return result;
	}
}
