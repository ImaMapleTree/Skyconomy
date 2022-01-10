package me.imamapletree.wrappers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.ContainerKey;
import me.imamapletree.panels.CommandIcon;
import me.imamapletree.panels.HierarchyIcon;
import me.imamapletree.panels.Icon;
import me.imamapletree.panels.InterfacePanel;
import me.imamapletree.tools.Utility;
import me.imamapletree.wrappers.panel.ClickCommandWrapper;
import me.imamapletree.wrappers.panel.MemberSettingWrapper;
import net.md_5.bungee.api.ChatColor;

public class MPWrapper implements ClickCommandWrapper {
	@SuppressWarnings("unused")
	private Skyconomy skyconomy = Skyconomy.getInstance();
	private MemberSettingWrapper MSW = new MemberSettingWrapper();
	
	public boolean onClick(Icon icon, Player player) {
		InterfacePanel msett = new InterfacePanel(4, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + Utility.getOfflineName((String) icon.getDataContainer().get("Player")));
		msett.addParent(icon.getPanel());
		msett.getDataContainer().put(new ContainerKey("Island", null), icon.getPanel().getDataContainer().get(new ContainerKey("Island", null)));
		msett.getDataContainer().put(new ContainerKey("Player", null), icon.getDataContainer().get("Player"));
		msett.addBorder(Material.BLUE_STAINED_GLASS_PANE, Material.LIGHT_BLUE_STAINED_GLASS_PANE);
		
		CommandIcon transfer_icon = new CommandIcon(msett, new ItemStack(Material.NETHER_STAR), ChatColor.GOLD + "Transfer Ownership");
		transfer_icon.setExtraneous("transfer");
		transfer_icon.setCommand(MSW);
		transfer_icon.replaceDataContainer(icon.getDataContainer());
		msett.addIcon(1, 1, transfer_icon);
		
		CommandIcon promote_icon = new CommandIcon(msett, new ItemStack(Material.GOLD_INGOT), ChatColor.DARK_GREEN + "Promote Player");
		promote_icon.setExtraneous("promote");
		promote_icon.setCommand(MSW);
		promote_icon.replaceDataContainer(icon.getDataContainer());
		msett.addIcon(3, 2, promote_icon);
		
		CommandIcon head_icon = new CommandIcon(msett, (ItemStack) icon.getDataContainer().get("Head"), ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + Utility.getOfflineName((String) icon.getDataContainer().get("Player")));
		msett.addIcon(4, 1, head_icon);
		
		CommandIcon demote_icon = new CommandIcon(msett, new ItemStack(Material.NETHER_BRICK), ChatColor.YELLOW + "Demote Player");
		demote_icon.setExtraneous("demote");
		demote_icon.setCommand(MSW);
		demote_icon.replaceDataContainer(icon.getDataContainer());
		msett.addIcon(5, 2, demote_icon);
		
		CommandIcon kick_icon = new CommandIcon(msett, new ItemStack(Material.COBWEB), ChatColor.RED + "Kick Player");
		kick_icon.setExtraneous("kick");
		kick_icon.setCommand(MSW);
		kick_icon.replaceDataContainer(icon.getDataContainer());
		msett.addIcon(7, 1, kick_icon);
		
		msett.addParent(icon.getPanel());
		HierarchyIcon back_icon = new HierarchyIcon(msett, new ItemStack(Material.BARRIER), ChatColor.YELLOW + "" + ChatColor.BOLD + "Back");
		back_icon.setHandler("back");
		msett.addIcon(4, 3, back_icon);
		
		msett.openPanel(player);
		return false;
	}
	
	@Override
	public String toString() {
		return this.getClass().toString();
	}
	
}
