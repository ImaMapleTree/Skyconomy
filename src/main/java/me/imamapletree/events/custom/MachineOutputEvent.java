package me.imamapletree.events.custom;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MachineOutputEvent extends Event implements Cancellable {
	private final Inventory destination;
	private final Inventory initiatior;
	private ItemStack source_item;
	private final Inventory source;
	private boolean cancelled = false;
	private static final HandlerList HANDLERS = new HandlerList();
	
	public MachineOutputEvent(Inventory initiator, Inventory destination, ItemStack item) {
		this.initiatior = initiator;
		this.destination = destination;
		this.source_item = item;
		this.source = initiator;
	}

	public Inventory getSource() {
		return this.source;
	}

	public ItemStack getItem() {
		return this.source_item;
	}
	
	public Inventory getDestination() {
		return this.destination;
	}
	
	public Inventory getInitiator() {
		return this.initiatior;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
	public void Cancel() {
		this.cancelled = true;
		this.initiatior.addItem(this.source_item);
		this.destination.removeItem(this.source_item);
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
