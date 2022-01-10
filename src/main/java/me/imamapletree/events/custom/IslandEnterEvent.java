package me.imamapletree.events.custom;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import me.imamapletree.api.instances.Island;

public class IslandEnterEvent extends Event implements Cancellable {
	private final Player player;
	private final Location location;
	private final Island island;
	private boolean cancelled = false;
	private static final HandlerList HANDLERS = new HandlerList();
	
	public IslandEnterEvent(Player player, Location location, Island island) {
		this.player = player;
		this.location = location;
		this.island = island;
	}

	public Player getPlayer() {
		return this.player;
	}

	public Location getLocation() {
		return this.location;
	}
	
	public Island getIsland() {
		return this.island;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
	public void Cancel() {
		//this.cancelled = true;
		//this.initiatior.addItem(this.source_item);
		//this.destination.removeItem(this.source_item);
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.cancelled = arg0;
	}
}
