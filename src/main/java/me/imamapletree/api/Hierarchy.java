package me.imamapletree.api;

import java.util.HashMap;
import java.util.Map;

import me.imamapletree.panels.InterfacePanel;

public interface Hierarchy {
	@SuppressWarnings("rawtypes")
	public Map<String, Map> member_tree = new HashMap<String, Map>();
	public static final InterfacePanel apex = null;
	public static final String ID = "";
	public Map<String, InterfacePanel> real_members = new HashMap<String, InterfacePanel>();
	
	public InterfacePanel getApex();
	
	public String getUUID();

	public void setArtificialParent(InterfacePanel parent_panel);
	
}