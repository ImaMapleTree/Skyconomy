package me.imamapletree.events.custom;

import org.bukkit.block.Block;
import org.bukkit.block.EnchantingTable;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;

public class CalibrationInputEvent extends Event implements Cancellable {
	private boolean cancelled = false;
	private static final HandlerList HANDLERS = new HandlerList();
	private EnchantingInventory tableInventory;
	private Player enchanter;
	private EnchantingTable enchantTable;
	private ItemStack enchantItem;
	private Block block;
	
	public CalibrationInputEvent(EnchantingInventory source_inv, Player enchanter, EnchantingTable table, ItemStack item) {
		this.tableInventory = source_inv;
		this.enchanter = enchanter;
		this.enchantTable = table;
		this.enchantItem = item;
		this.block = this.enchantTable.getBlock();
	}
	
	public EnchantingInventory getEnchantInventory() {
		return this.tableInventory;
	}
	
	public Player getEnchanter() {
		return this.enchanter;
	}
	
	public Block getBlock() {
		return this.block;
	}
	
	public EnchantingTable getEnchantingTable() {
		return this.enchantTable;
	}
	
	public ItemStack getItem() {
		return this.enchantItem;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
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
