package me.imamapletree.executors;

import org.bukkit.Bukkit;

import me.imamapletree.Skyconomy;

public class MobRequirementCalculation implements Runnable{
	private Skyconomy skyconomy;
	
	public MobRequirementCalculation(Skyconomy skyconomy) {
		this.skyconomy = skyconomy;
	}
	
	@Override
	public void run() {
		skyconomy.getDungeonManager().setBossRequirements(Bukkit.getOnlinePlayers().size()*40);
	}
}
