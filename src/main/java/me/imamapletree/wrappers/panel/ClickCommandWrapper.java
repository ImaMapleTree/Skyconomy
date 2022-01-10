package me.imamapletree.wrappers.panel;

import org.bukkit.entity.Player;

import me.imamapletree.panels.Icon;

public interface ClickCommandWrapper {
	boolean onClick(Icon icon, Player player);
}
