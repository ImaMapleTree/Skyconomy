package me.imamapletree.api;

/*
public class PanelHierarchy implements Hierarchy {
	@SuppressWarnings("unused")
	private Skyconomy skyconomy;
	@SuppressWarnings("rawtypes")
	private Map<String, Map> member_tree = new HashMap<String, Map>();
	private InterfacePanel apex;
	private String ID;
	private String apex_parent = null;
	private Map<String, InterfacePanel> real_members = new HashMap<String, InterfacePanel>();
	
	public PanelHierarchy(Skyconomy skyconomy, InterfacePanel origin) {
		skyconomy.getHierarchyManager().addHierarchy(this);
		this.ID = UUID.randomUUID().toString();
		this.apex = origin;
		this.real_members.put(origin.getUUID(), origin);
		@SuppressWarnings("rawtypes")
		Map<String, Map> children = new HashMap<String, Map>();
		member_tree.put(origin.getUUID(), children);
	}
	
	//From here on all methods will use UUID to pull from the Hash rather than InterfacePanel
	
	public void setArtificialParent(InterfacePanel parent) {
		this.apex_parent = parent.getUUID();
		this.real_members.put(parent.getUUID(), parent);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addChild(String UUID, InterfacePanel child) throws ParentExistanceException {
		if(!member_tree.containsKey(UUID)) throw new ParentExistanceException();
		Map<String, Map> next_gen = createMapFromChildren(child.getChildren());
		this.real_members.put(child.getUUID(), child);
		
		Map<String, Map> children = this.member_tree.get(UUID);
		children.put(child.getUUID(), next_gen);
		this.member_tree.put(UUID, children);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addParent(InterfacePanel child, InterfacePanel parent) {
		if(!member_tree.containsKey(parent.getUUID())) {
			Map<String, Map> parent_children = new HashMap<String, Map>();
			member_tree.put(parent.getUUID(), parent_children);
		}
		Map<String, Map> next_gen = createMapFromChildren(child.getChildren());
		this.real_members.put(child.getUUID(), child);
		
		Map<String, Map> children = this.member_tree.get(parent.getUUID());
		children.put(child.getUUID(), next_gen);
		this.member_tree.put(parent.getUUID(), children);
		this.member_tree.put(child.getUUID(), next_gen);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Map> getChildren(String UUID) {
		List<Map<String, Map>> dumb_list = new ArrayList<Map<String, Map>>();
		for(Entry<String, Map> entry : this.member_tree.entrySet()) {
			if(entry.getKey().equals(UUID)) {return entry.getValue();}
			dumb_list.add(entry.getValue());
			for(int i=0; i<dumb_list.size(); i++) {
				for(Entry<String, Map> entry2 : dumb_list.get(i).entrySet()) {
					if(entry2.getKey().equals(UUID)) {return entry2.getValue();}
					dumb_list.add(entry2.getValue());
				}
			}	
		} return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getParent(String UUID) {
		Map<Map, String> temp_map = new HashMap<Map, String>();
		List<Map<String, Map>> dumb_list = new ArrayList<Map<String, Map>>();
		for(Entry<String, Map> entry : this.member_tree.entrySet()) {
			if(entry.getKey().equals(UUID)) {
				if(apex_parent != null) return apex_parent;
				return entry.getKey();
			}
			dumb_list.add(entry.getValue());
			temp_map.put(entry.getValue(), entry.getKey());
			for(int i=0; i<dumb_list.size(); i++) {
				for(Entry<String, Map> entry2 : dumb_list.get(i).entrySet()) {
					if(entry2.getKey().equals(UUID)) {return temp_map.get(dumb_list.get(i));}
					dumb_list.add(entry2.getValue());
				}
			}	
		} return null;
	}
	
	public InterfacePanel getInterfacePanel(String UUID) {
		if(!this.real_members.containsKey(UUID)) return null;
		return this.real_members.get(UUID);
	}
	
	public InterfacePanel getApex() {
		return this.apex;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Map> createMapFromChildren(List<InterfacePanel> list) {
		Map<String, Map> next_gen = new HashMap<String, Map>();
		Map<String, Map> new_map = new HashMap<String, Map>();
		if(list != null) {
			for (InterfacePanel ip : list) {
				new_map.put(ip.getUUID(), next_gen);
				this.real_members.put(ip.getUUID(), ip);
			}
		}
		return new_map;
	}
	
	public String getUUID() {
		return this.ID;
	}
}
*/