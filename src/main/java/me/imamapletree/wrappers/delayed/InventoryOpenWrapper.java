package me.imamapletree.wrappers.delayed;

import org.bukkit.entity.Player;

import me.imamapletree.panels.InterfacePanel;

public class InventoryOpenWrapper implements DelayedWrapper {
	public InventoryOpenWrapper() {
	}
	
	@Override
	public void execute(Object obj1, Object obj2, Object obj3) {
		if (!(obj1 instanceof InterfacePanel)) return;
		if (!(obj2 instanceof Player)) return;
		InterfacePanel IP = (InterfacePanel) obj1;
		Player player = (Player) obj2;
		IP.openPanel(player, false);
	}
}
