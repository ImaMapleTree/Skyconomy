package me.imamapletree.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.instances.Island;
import me.imamapletree.panels.PremadePanels;
import me.imamapletree.tools.ItemUtility;

public class MiscManager {
	@SuppressWarnings("unused")
	private Skyconomy skyconomy;
	private FlagManager flagmanager;
	private PremadePanels premade_panels;
	public ItemStack Energy_Core;
	public ItemStack Villager_Head;
	
	private Map<String, Island> IAP = new HashMap<String, Island>();
	
	@SuppressWarnings("deprecation")
	public MiscManager(Skyconomy skyconomy, FlagManager flagmanager) {
		this.skyconomy = skyconomy;
		this.flagmanager = flagmanager;
		this.premade_panels = new PremadePanels(skyconomy, this);
		
		this.Energy_Core = this.premade_panels.getHead("Energy Crystal", "27639099-b43d-4fd2-aecc-49b6b098ddd9", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGYxNGYzMTc5Yjg2ZjY5YjNlZmE3NDcyZGFjYWViMjMzOWY2MjkwZDJkODE3MzYyNzkzMzQ4YWJkOThlMDIxIn19fQ==");
		this.Villager_Head = ItemUtility.createHead("Villager");
	}
	
	public PremadePanels getPremades() {
		return this.premade_panels;
	}

	public FlagManager getFlagManger() {
	/*
	 * Normally don't use this method
	 */
	return this.flagmanager;
	}

	public void addIAP(String uuid, Island island) {
		this.IAP.put(uuid, island);
	}
	
	public Island getIAP(String uuid) {
		return this.IAP.get(uuid);
	}
	
	public void removeIAP(String uuid) {
		if(IAP.containsKey(uuid)) this.IAP.remove(uuid);
	}
}
