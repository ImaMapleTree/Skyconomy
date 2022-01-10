package me.imamapletree.managers;

import java.util.HashMap;
import java.util.Map;

import me.imamapletree.Skyconomy;
import me.imamapletree.events.AsyncPlayerMessage;
import me.imamapletree.events.PlayerInventoryInteractor;
import me.imamapletree.events.PlayerJoin;
import me.imamapletree.events.PlayerLeave;
import me.imamapletree.events.PlayerMove;
import me.imamapletree.events.listeners.*;
import me.imamapletree.events.listeners.addons.*;
import me.imamapletree.events.listeners.addons.bosses.*;
import me.imamapletree.events.listeners.blocks.*;
import me.imamapletree.events.listeners.blocks.addons.*;

public class EventManager {
	private Skyconomy skyconomy;
	public Map<Object, Object> EventCache = new HashMap<Object, Object>();
	
	
	public EventManager(Skyconomy skyconomy) {
		this.skyconomy = skyconomy;
		LoadEvents();
	}
	
	private void LoadEvents() {
		skyconomy.getServer().getPluginManager().registerEvents(new BreakBlockListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new PlayerFlyListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new UseBed(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new PlaceBlockListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new UseAnvil(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new FeedAnimalListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new CropTrampleListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new AccessAnimalListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new AnimalRideListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new HurtPassiveMobListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new HurtHostileMobListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new TouchHangingListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new ChangeItemFrameListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new MobLeashListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new PlaceRedstoneListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new HarvestAnimalListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new PotionThrowListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new SpawnEggListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new DropItemListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new PickupItemListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new PlaceFireListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new EnderpearlThrowListener(), skyconomy);
  		
  		skyconomy.getServer().getPluginManager().registerEvents(new OpenEnchantingTableListener(skyconomy, this), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new PlaceAutoCraftListener(skyconomy), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new PlaceDAPListener(skyconomy), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new PreEnchantListener(skyconomy), skyconomy);
  		//Global flags
  		skyconomy.getServer().getPluginManager().registerEvents(new PVPListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new PlayerExplosionDamageListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new PassiveSpawnListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new HostileSpawnListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new FireSpreadListener(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new FallDamageListener(), skyconomy);
  		
  		if(skyconomy.getSettings().IntenseDebug) skyconomy.getServer().getPluginManager().registerEvents(new DebugListener(), skyconomy);
  		
  		skyconomy.getServer().getPluginManager().registerEvents(new UseBrewingStand(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new UseContainer(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new UseBeacon(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new UseDoor(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new UseFurnace(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new UseGate(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new UseSwitch(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new UsePressurePlate(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new UseNoteblock(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new UseJukebox(), skyconomy);
  		skyconomy.getServer().getPluginManager().registerEvents(new UseTrapdoor(), skyconomy);
  		
  		skyconomy.getServer().getPluginManager().registerEvents(new CalibrationInputListener(skyconomy, this), skyconomy);
		skyconomy.getServer().getPluginManager().registerEvents(new PlayerInventoryInteractor(skyconomy), skyconomy);
		skyconomy.getServer().getPluginManager().registerEvents(new PlayerMove(skyconomy), skyconomy);
		skyconomy.getServer().getPluginManager().registerEvents(new PlayerJoin(skyconomy), skyconomy);
		skyconomy.getServer().getPluginManager().registerEvents(new PlayerLeave(skyconomy), skyconomy);
		skyconomy.getServer().getPluginManager().registerEvents(new AsyncPlayerMessage(skyconomy), skyconomy);
		
		skyconomy.getServer().getPluginManager().registerEvents(new IslandEnterListener(skyconomy), skyconomy);
		skyconomy.getServer().getPluginManager().registerEvents(new IslandLeaveListener(skyconomy), skyconomy);
		
		skyconomy.getServer().getPluginManager().registerEvents(new BossBarUpdater(), skyconomy);
		skyconomy.getServer().getPluginManager().registerEvents(new SpiderBossListener(), skyconomy);
	}

	public boolean hasCachedObject(Object key) {
		return this.EventCache.containsKey(key);
	}
	
	public Object getCachedObject(Object key) {
		return this.EventCache.get(key);
	}
	
	public void addCachedObject(Object key, Object object) {
		this.EventCache.put(key, object);
	}
	
	public void removeCachedObject(Object key) {
		this.EventCache.remove(key);
	}
}
