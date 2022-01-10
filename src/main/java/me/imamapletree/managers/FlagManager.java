package me.imamapletree.managers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;

import me.imamapletree.database.lists.Flags;
import me.imamapletree.Skyconomy;
import me.imamapletree.api.instances.Flag;
import me.imamapletree.tools.JsonHelper;

public class FlagManager {
	@SuppressWarnings("unused")
	private Skyconomy skyconomy;
	private Flags FlagHelper = new Flags();
	private List<Flag> flags;
	private Map<Flag, List<String>> flag_values = new HashMap<Flag, List<String>>();
	private JSONObject FlagDescription = JsonHelper.loadJsonFromResources("FlagDescription.json");
	private JSONObject FlagNames = JsonHelper.loadJsonFromResources("PublicFlagNames.json");
	private List<String> flag_order = Arrays.asList("ISBan", "ISManageSettings", "ISInvite", "IslandChatAccess", "BankBalanceAccess", "BankDepositAccess",
			"BankWithdrawAccess", "IslandVaultAccess", "BreakBlock", "PlaceBlock", "ChangeRole", "KickMember", "UseContainer", "ChestShopAccess", "BreakValuables", "UseFly", "AllowEnderPearling",
			"AllowHurtingPassiveMobs", "AllowHurtingHostileMobs", "AllowCropTrample", "UseAnvil", "UseBeacon", "UseBed", "UseBrewingStand", "UseFurnace", 
			"UseDoor", "UseGate", "UseTrapdoor", "AllowMobLeashing", "AffectHanging", "AccessAnimalInventory", 
			"BreedAnimals", "AllowAnimalRiding", "HarvestAnimalProduct", "PlaceFire", "PlaceRedstone", "UseSwitch", "UsePressurePlate", "UseNoteblock", "UseJukebox", "AllowPotionThrow",
			"AllowSpawnEgg", "AllowItemDrop", "AllowItemPickup");
	private List<String> global_order = Arrays.asList("PVP", "DamageByExplosion", "PassiveMobSpawn", "HostileMobSpawn", "FireSpread", "FallDamage");
	
	public FlagManager(Skyconomy skyconomy) {
		this.skyconomy = skyconomy;
		this.flags = FlagHelper.defaultFlags();
		this.flag_values = FlagHelper.attachValue(this.flag_values);
	}
	
	public JSONObject getFlagTitles() {
		return this.FlagNames;
	}
	
	public JSONObject getFlagDescriptions() {
		return this.FlagDescription;
	}
	
	public Optional<Flag> getFlag(String FlagName) {
		return this.flags.stream().filter(flag -> flag.getName().equals(FlagName)).findAny();
	}
	
	public Map<Flag, List<String>> getFlagValues() {
		return this.flag_values;
	}
	
	public List<String> getFlagOrder() {
		return this.flag_order;
	}
	
	public List<String> getGlobalOrder() {
		return this.global_order;
	}
	
	public List<Flag> getFlags() {
		return this.flags;
	}
}
