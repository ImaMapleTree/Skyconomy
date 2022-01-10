package me.imamapletree.executors;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.ArmorStand;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.EulerAngle;

import me.imamapletree.Skyconomy;
import me.imamapletree.tools.Utility;

public class WorkbenchSummonExecutor implements Runnable {
	private Skyconomy skyconomy = Skyconomy.getInstance();
	private Block block;
	
	public WorkbenchSummonExecutor(Skyconomy skyconomy, Block block) {
		this.skyconomy = skyconomy;
		this.block = block;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		ArmorStand AS = block.getWorld().spawn(Utility.getLocationCenter(block.getLocation()).clone().add(0, 0.58, 0), ArmorStand.class);
		AS.setInvulnerable(true);
		AS.setVisible(false);
		AS.setCustomName("AutocraftingTable");
		AS.setGravity(false);
		AS.setSmall(true);
		AS.setHeadPose(new EulerAngle(0, 0, Math.PI));
		AS.setHelmet(skyconomy.getMiscManager().Energy_Core);
		Dispenser dispenser = (Dispenser) block.getState();
		NamespacedKey key = new NamespacedKey(skyconomy, "CUSTOM_DATA");
		dispenser.getPersistentDataContainer().set(key, PersistentDataType.STRING, "AUTOCRAFTINGTABLE");
		dispenser.update();
	}
}
