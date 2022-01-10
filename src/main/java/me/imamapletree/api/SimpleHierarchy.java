package me.imamapletree.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.imamapletree.panels.InterfacePanel;

public class SimpleHierarchy {
	private SimpleHierarchy parent = null;
	private final List<SimpleHierarchy> children = new ArrayList<SimpleHierarchy>();
	private final UUID indivID = UUID.randomUUID();
	private UUID familyID = UUID.randomUUID();
	private InterfacePanel bound_panel;
	
	public SimpleHierarchy(InterfacePanel creator) {
		this.bound_panel = creator;
	}
		
	public void addParent(SimpleHierarchy parent) {
		this.parent = parent;
		if(!parent.getChildren().contains(this)) parent.addChild(this);
		this.familyID = parent.getFamilyID();
	}
	
	public void addChild(SimpleHierarchy panel) {
		this.children.add(panel);
		if(panel.parent == null) panel.addParent(this);
		else if(!panel.parent.equals(this)) panel.addParent(this);
		HierarchyIterator iterator = new HierarchyIterator(panel);
		while(iterator.hasNext()) {
			iterator.next().setFamilyID(this.familyID);
		}
	}
	
	public void addChildren(List<SimpleHierarchy> panels) {
		this.children.addAll(panels);
		for(SimpleHierarchy child : panels) {
			HierarchyIterator iterator = new HierarchyIterator(child);
			while(iterator.hasNext()) {
				iterator.next().setFamilyID(this.familyID);
			}
		}
	}
	
	public SimpleHierarchy getParent() {
		return this.parent;
	}
	
	public InterfacePanel getParentAsInterface() {
		return getParent().getBound();
	}
	
	public List<SimpleHierarchy> getChildren() {
		return this.children;
	}
	
	public List<InterfacePanel> getChildrenAsInterface() {
		List<InterfacePanel> IPchildren = new ArrayList<InterfacePanel>();
		for(SimpleHierarchy child : getChildren()) IPchildren.add(child.getBound());
		return IPchildren;
	}
	
 	public InterfacePanel getBound() {
		return this.bound_panel;
	}
	
	public boolean hasChildren() {
		if(this.children.isEmpty()) return false;
		return true;
	}
	
	public boolean hasParent() {
		if(this.parent == null) return false;
		return true;
	}
	
	public UUID getUUID() {
		return this.indivID;
	}
	
	public UUID getFamilyID() {
		return this.familyID;
	}
	
	private void setFamilyID(UUID uuid) {
		this.familyID = uuid;
	}
	
	public class HierarchyIterator {
		List<SimpleHierarchy> subjects = new ArrayList<SimpleHierarchy>();
		List<SimpleHierarchy> layer = new ArrayList<SimpleHierarchy>();
		SimpleHierarchy start;
		Integer index = 0;
		SimpleHierarchy next;
		
		public HierarchyIterator(SimpleHierarchy iteratee) {
			this.start = iteratee;
			if(!iteratee.hasChildren()) this.next = null;
			else {this.subjects = iteratee.getChildren();
			this.next = this.subjects.get(index);}
		}
		public SimpleHierarchy next() {
			if(this.next == null) return null;
			layer.add(next);
			if (this.subjects.size()-1 > this.index) {this.index += 1; this.next = this.subjects.get(this.index);}
			else {
				this.index = 0;
				this.subjects.clear();
				for(SimpleHierarchy sub : this.layer) this.subjects.addAll(sub.getChildren());
				if(this.subjects.isEmpty()) this.next = null;
				else this.next = this.subjects.get(this.index);
				this.layer.clear();
			}
			return this.next;
		}
		
		public boolean hasNext() {
			if(this.next == null) return false;
			return true;
		}
	}
}
