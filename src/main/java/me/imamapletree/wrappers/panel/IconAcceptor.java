package me.imamapletree.wrappers.panel;

import org.bukkit.entity.Player;

import me.imamapletree.Skyconomy;
import me.imamapletree.api.Invite;
import me.imamapletree.panels.Icon;

public class IconAcceptor implements ClickCommandWrapper {
	@SuppressWarnings("unused")
	private Skyconomy skyconomy = Skyconomy.getInstance();
	public boolean onClick(Icon icon, Player player) {
		boolean accepted = (boolean) icon.getDataContainer().get("value");
		Invite invite = (Invite) icon.getDataContainer().get("invite");
		if(accepted) invite.Accept(player);
		else invite.Deny(player);
		return false;
	}
	
	@Override
	public String toString() {
		return this.getClass().toString();
	}
	
}
