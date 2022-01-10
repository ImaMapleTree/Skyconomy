package me.imamapletree.events.listeners.addons.bosses;

import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import me.imamapletree.database.mobs.CustomBoss;

public class BossBarUpdater implements Listener {
	@EventHandler(priority = EventPriority.LOW)
	public void onDamage(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof CraftEntity)) return;
		CraftEntity CE = (CraftEntity) e.getEntity();
		if(!CustomBoss.class.isInstance(CE.getHandle())) return;
		CustomBoss CB = (CustomBoss) CE.getHandle();
		Double damage = e.getFinalDamage();
		CB.getBar().removeProgress(damage.floatValue());
		if(e.getEntity().getLocation().getBlockY() <= 0) CB.getBar().removeEntityTracker((LivingEntity) CB);
	}
}
