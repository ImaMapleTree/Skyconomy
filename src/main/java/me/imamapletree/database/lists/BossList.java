package me.imamapletree.database.lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;

import me.imamapletree.api.instances.BossDomain;
import me.imamapletree.database.mobs.*;

public class BossList {
	public List<DormantMob> commonBoss = new ArrayList<DormantMob>();
	public List<BossDomain> domains = Arrays.asList(new BossDomain("SpiderDen", 42, 60, -36), new BossDomain("SpiderDen", 10, 105, 35));
	
	
	public BossList() {
		World world = Bukkit.getWorlds().get(0);
		//commonBoss.add(new DormantMob(SlimeGolem.class, Bukkit.getWorlds().get(0), "Slime Golem", 0));
		//commonBoss.add(new DormantMob(SpiderBoss.class, Bukkit.getWorlds().get(0), "Spider Queen", 0));
		commonBoss.add(new DormantMob(Horsemen.class, world, "Men of the Eclipse", 2, 3));
		commonBoss.add(new DormantMob(Horsemen.class, world, "Men of the Eclipse", 2, 3));
	}
}
