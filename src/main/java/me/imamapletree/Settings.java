package me.imamapletree;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import me.imamapletree.api.WorldCoordinate;
import me.imamapletree.tools.SkyLog;

public class Settings {
	private Skyconomy skyconomy;
	public boolean ONLY_REGISTER_IN_ENABLED_WORLDS = false;
	public int IslandSeperation = 500;
	public int SpawnOffset = 2000;
	public int island_y = 120;
	public boolean ChatErrorOutput = true;
	public boolean ChatOutput = true;
	public int BLOCKS_FROM_CENTER = 250;
	public WorldCoordinate SpawnCoords = new WorldCoordinate(0, 4, 0);
	public boolean IntenseDebug = false;
	public String ChatCharacter = ChatColor.DARK_GRAY + " »";
	public boolean GlobalChatPrefix = true;
	
	private Map<String, List<String>> flag_settings = new HashMap<String, List<String>>();
	
	
	//Flag Settings
	public List<String> FLAG_UseFly = Arrays.asList("OWNER", "ADMIN", "MEMBER", "GUEST");
	public List<String> FLAG_UseBed = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_BreakBlock = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_PlaceBlock = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_UseAnvil = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_BreedAnimals = Arrays.asList("OWNER", "ADMIN", "MEMBER", "GUEST");
	public List<String> FLAG_UseBrewingStand = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_UseBeacon = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_UseContainer = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_AllowCropTrample = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_UseDoor = Arrays.asList("OWNER", "ADMIN", "MEMBER", "GUEST"); //Should have guest
	public List<String> FLAG_UseFurnace = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_AccessAnimalInventory = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_UseGate = Arrays.asList("OWNER", "ADMIN", "MEMBER", "GUEST"); //Should have guest
	public List<String> FLAG_AllowAnimalRiding = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_AllowHurtingPassiveMobs = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_AllowHurtingHostileMobs = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_AffectHanging = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_AllowMobLeashing = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_UseSwitch = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_UsePressurePlate = Arrays.asList("OWNER", "ADMIN", "MEMBER", "GUEST");
	public List<String> FLAG_HarvestAnimalProduct = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_UseNoteblock = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_UseJukebox = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_AllowPotionThrow = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_AllowSpawnEgg = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_AllowItemDrop = Arrays.asList("OWNER", "ADMIN", "MEMBER", "GUEST");
	public List<String> FLAG_AllowItemPickup = Arrays.asList("OWNER", "ADMIN", "MEMBER", "GUEST");
	public List<String> FLAG_AllowEnderPearling = Arrays.asList("OWNER", "ADMIN", "MEMBER", "GUEST");
	public List<String> FLAG_UseTrapdoor = Arrays.asList("OWNER", "ADMIN", "MEMBER", "GUEST");
	public List<String> FLAG_PlaceFire = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_PlaceRedstone = Arrays.asList("OWNER", "ADMIN", "MEMBER");
	public List<String> FLAG_ChangeRole = Arrays.asList("OWNER", "NONE", "NONE", "NONE");
	public List<String> FLAG_KickMember = Arrays.asList("OWNER", "NONE", "NONE", "NONE");
	//Global Flags
	public List<String> FLAG_PVP = Arrays.asList("DISABLED");
	public List<String> FLAG_DamageByExplosion = Arrays.asList("DISABLED");
	public List<String> FLAG_PassiveMobSpawn = Arrays.asList("ENABLED");
	public List<String> FLAG_HostileMobSpawn = Arrays.asList("ENABLED");
	public List<String> FLAG_FireSpread = Arrays.asList("DISABLED");
	public List<String> FLAG_FallDamage = Arrays.asList("DISABLED");
	
 	public Settings(Skyconomy skyconomy) {
		this.skyconomy = skyconomy;
	}
	
	@SuppressWarnings("unchecked")
	public void Initialize() {
		Field[] fields = Settings.class.getDeclaredFields();
		for (Field field : fields) {
			if(Modifier.isPublic(field.getModifiers())) {
				if(field.getName().contains("FLAG_")) {
					try {
						List<String> value = (List<String>) field.get(this);
						flag_settings.put(field.getName().replace("FLAG_", "").toUpperCase(), value);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						SkyLog.log(ChatColor.RED + "Unable to set flag values, disabling.");
						SkyLog.log(e);
						Bukkit.getPluginManager().disablePlugin(skyconomy);
					}
				}
				
			}
		}
	}
	
	public Map<String, List<String>> getFlagSettings() {
		return this.flag_settings;
	}
	
	public Field[] getFields() {
		return Settings.class.getFields();
	}
	
}
