package me.imamapletree.api.instances;

import java.io.Serializable;

import org.bukkit.Material;
import me.imamapletree.Skyconomy;

public class Flag implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3720453084743007155L;
	private Skyconomy skyconomy = (Skyconomy) Skyconomy.getInstance();
	private String FlagName;
	private Material material;
	
	public Flag(String name, Material material) {
		this.FlagName = name;
		this.material = material;
	}

	public Flag() {
	}

	public Material getMaterial() {
		return this.material;
	}

	public String getName() {
		return this.FlagName;
	}

	public String getTitle() {
		return (String) skyconomy.getFlagManager().getFlagTitles().get(this.FlagName);
	}
	
	public void openFlagPanel(Island island) {
		
	}
}
