package me.imamapletree.database.lists;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.imamapletree.api.instances.Dungeon;
import me.imamapletree.tools.SkyLog;

public class DungeonLocations {
	private Map<Integer, Dungeon> DungeonMap = new HashMap<Integer, Dungeon>();
	private List<Dungeon> DungeonList = new ArrayList<Dungeon>();
	
	protected Dungeon D0 = new Dungeon("Plains of Beginning", 1);
	protected Dungeon D1 = new Dungeon("Dawning Horizon", 4);
	
	public DungeonLocations() {
		this.registerMap();
		this.registerList();
	}
	
	private void registerMap() {
		DungeonMap.put(0, D0);
		DungeonMap.put(1, D1);
	}
	
	public Dungeon getMappedDungeon(Integer id) {
		return DungeonMap.get(id);
	}
	
	private void registerList() {
		Field[] fields = DungeonLocations.class.getDeclaredFields();
		for (Field field : fields) {
			if (Modifier.isProtected(field.getModifiers())) {
				try {
					Dungeon dungeon = (Dungeon) field.get(this);
					this.DungeonList.add(dungeon);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					SkyLog.log("Unable to register flags, disabling.");
				}
			}
		}
	}
}
