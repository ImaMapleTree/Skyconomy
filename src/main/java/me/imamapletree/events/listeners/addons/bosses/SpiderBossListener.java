package me.imamapletree.events.listeners.addons.bosses;

import org.bukkit.craftbukkit.v1_16_R2.entity.CraftSpider;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.imamapletree.Skyconomy;
import me.imamapletree.database.mobs.SpiderBoss;

public class SpiderBossListener implements Listener {	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerDamage(final EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		if(!(e.getDamager() instanceof Spider)) return;
		CraftSpider cs = (CraftSpider) e.getDamager();
		if(!(cs.getHandle() instanceof SpiderBoss)) return;
		Player p = (Player) e.getEntity();
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 160, 1));
	}
	
	@EventHandler
	public void onEntityDeath(final EntityDeathEvent e) {
		Skyconomy.getInstance().getDungeonManager().addKilledMob(e.getEntity(), 0);
	}
}
