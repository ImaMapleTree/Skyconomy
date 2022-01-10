package me.imamapletree.api;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import me.imamapletree.panels.InterfacePanel;

public class SkyconomyInventoryHolder implements InventoryHolder
{
	private InterfacePanel InterfacePanel;
	
	public SkyconomyInventoryHolder(final InterfacePanel InterfacePanel) {
		this.InterfacePanel = InterfacePanel;
	}

	public InterfacePanel getPanel() {
		return this.InterfacePanel;
	}
	
	public Inventory getInventory() {
		return Bukkit.createInventory((InventoryHolder)null, this.InterfacePanel.getSize(), this.InterfacePanel.getName());
	}
	
}
