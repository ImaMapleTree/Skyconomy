package me.imamapletree.wrappers.panel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.imamapletree.api.ContainerKey;
import me.imamapletree.api.instances.Island;
import me.imamapletree.panels.CommandIcon;
import me.imamapletree.panels.Icon;
import me.imamapletree.tools.ItemUtility;
import net.md_5.bungee.api.ChatColor;

public class MemberSettingWrapper implements ClickCommandWrapper {
	public boolean onClick(Icon icon, Player player) {
		Map<ContainerKey, Object> data = icon.getPanel().getDataContainer();
		if(!(icon instanceof CommandIcon)) return true;
		CommandIcon ICON = (CommandIcon) icon;
		CommandSender sender = player;
		String uuid = (String) data.get(new ContainerKey("Player", null));
		Island island = (Island) data.get(new ContainerKey("Island", null));
		boolean success = Bukkit.dispatchCommand(sender, "island " + ICON.getExtraneous() + " " + uuid + " " + island.getUUID());
		if(ICON.getExtraneous().equals("kick")) {
			island.updateMemberPanel().openPanel(player);
			return false;
		}
		else if(success) {
			ItemStack IS = (ItemStack) ICON.getDataContainer().get("Head");
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GOLD + "> " + StringUtils.capitalize(island.getRole(uuid).toLowerCase()));
			IS = ItemUtility.setLore(IS, lore);
			ICON.getPanel().addIcon(4, 1, new CommandIcon(ICON.getPanel(), IS));
			island.updateMemberPanel();
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return this.getClass().toString();
	}
	
}
