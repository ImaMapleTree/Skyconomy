package me.imamapletree.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.CustomColor;
import me.imamapletree.api.SmartBossBar;
import me.imamapletree.api.instances.BossDomain;
import me.imamapletree.api.instances.Dungeon;
import me.imamapletree.database.lists.BossList;
import me.imamapletree.database.lists.DungeonLocations;
import me.imamapletree.database.mobs.CustomBoss;
import me.imamapletree.database.mobs.DormantMob;
import me.imamapletree.tools.RMath;
import me.imamapletree.tools.SkyLog;
import me.imamapletree.tools.Utility;
import net.md_5.bungee.api.ChatColor;

public class DungeonManager {
	@SuppressWarnings("unused")
	private Skyconomy skyconomy;
	private DungeonLocations dungeonList;
	public ItemStack COMPASS;
	public CustomColor customColor;
	private BossList bossList;
	private RMath rm = new RMath(new Random());
	public World CurrentDomain = null;
	
	
	private int bossRequirements;
	private int mobsKilled = 0;
	private int tier0 = 0;
	private int tier1 = 0;
	private int tier2 = 0;
	private int tier3 = 0;
	private double tw0 = 0.25;
	private double tw1 = 0.25;
	private double tw2 = 0.25;
	private double tw3 = 0.25;
	
	private float additiveHealth = 0;
	
	public DungeonManager(Skyconomy skyconomy) {
		this.skyconomy = skyconomy;
		this.customColor = new CustomColor();
		this.bossList = new BossList();
		dungeonList = new DungeonLocations();
		ItemStack c = new ItemStack(Material.COMPASS);
		ItemStack cc = Utility.setItemName(c, customColor.ORANGE + "" + ChatColor.BOLD  + "Teleport");
		this.COMPASS = Utility.addEnchantTint(cc);
		ItemMeta IM = this.COMPASS.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(this.customColor.HUED_YELLOW + "Click to Teleport");
		IM.setLore(lore);
		this.COMPASS.setItemMeta(IM);
	}
	
	public Dungeon getDungeon(Integer id) {
		return dungeonList.getMappedDungeon(id);
	}
	
	public void setBossRequirements(int mobs) {
		this.bossRequirements = mobs;
	}
	
	public void addKilledMob(LivingEntity e, int tier) {
		this.mobsKilled += 1;
		if(tier == 0) this.tier0 += 1;
		else if(tier == 1) this.tier1 += 1;
		else if(tier == 2) this.tier2 += 1;
		else this.tier3 += 1;
		if(this.mobsKilled >= this.bossRequirements) this.startBossEvent();
		this.additiveHealth += e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()*0.2;
		SkyLog.log(this.mobsKilled);
	}
	
	public void startBossEvent() {
		this.bossRequirements = 9999999;
		this.mobsKilled = 0;
		this.additiveHealth = 0;
		List<Double> dl = new ArrayList<Double>();
		dl.add(this.tier0 * this.tw0);
		dl.add(this.tier1 * this.tw1);
		dl.add(this.tier2 * this.tw2);
		dl.add(this.tier3 * this.tw3);
		Double max = Collections.max(dl);
		int indx = dl.indexOf(max);
		if(indx == 0) {summonBoss(0);
			if(tw0 >= 0.25) {tw0 -= 0.12; tw1 += 0.4; tw2 += 0.4; tw3 += 0.4;}
			else {tw1 += tw0/3; tw2 += tw0/3; tw3 += tw0/3; tw0 = 0;}
		}
		
		else if(indx == 1) {summonBoss(1);
			if(tw1 >= 0.25) {tw1 -= 0.12; tw0 += 0.4; tw2 += 0.4; tw3 += 0.4;}
			else {tw0 += tw1/3; tw2 += tw1/3; tw3 += tw1/3; tw1 = 0;}
		}
		
		else if(indx == 2) {summonBoss(2);
			tw0 += tw2/3; tw1 += tw2/3; tw3 += tw2/3; tw2 = 0;
		}
	
		else {summonBoss(3);
			tw0 += tw3/3; tw1 += tw3/3; tw2 += tw3/3; tw3 = 0;
		}
	}
	
	private void summonBoss(int tier) {
		BossDomain domain = (BossDomain) rm.choice(this.bossList.domains);
		WorldCreator WC = new WorldCreator(domain.getName());
		WC.createWorld();
		World world = Bukkit.getServer().createWorld(WC);
		
		DormantMob dormant = (DormantMob) RMath.staticChoice(this.bossList.commonBoss);
		SmartBossBar bar = new SmartBossBar(dormant.getName(), BarStyle.SEGMENTED_10, dormant.getTier());
		CustomBoss boss = dormant.constructMob(world);
		boss.addHealth(additiveHealth);
		Location summonLocation = new Location(world, domain.getX(), domain.getY(), domain.getZ());
		boss.spawn(summonLocation);
		boss.attachBossBar(bar);
		for(int i=1; i<dormant.getAmount(); i++) {
			boss = dormant.constructMob(world);
			boss.spawn(summonLocation);
			boss.attachBossBar(bar);
		}
		List<Player> pl = new ArrayList<Player>(Bukkit.getOnlinePlayers());
		bar.showBar(pl);
		this.CurrentDomain = world;
	}
}
