package me.imamapletree.api;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.json.simple.JSONObject;

import me.imamapletree.tools.Utility;
import me.imamapletree.extensions.exceptions.InvalidParentException;

public class ChildJSON {
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -7103877785624116721L;
	JSONObject Jparent;
	JSONObject realJson;
	Map<Object, Object> Mparent;
	List<Object> Lparent;
	String type;
	Object key;
	Object Okey;
	Object value;
	boolean jIsKey = true;
	public ChildJSON(JSONObject parent, JSONObject key, Object value) {
		this.Jparent = parent;
		this.type = Utility.type(parent);
		this.key = key;
		this.value = value;
		this.Okey = key.clone();
		this.realJson = (JSONObject) key.clone();
	}
	@SuppressWarnings("unchecked")
	public ChildJSON(Map<?, ?> parent, JSONObject key, Object value) {
		this.Mparent = (Map<Object, Object>) parent;
		this.type = Utility.type(parent);
		this.key = value;
		this.value = value;
		this.Okey = key.clone();
		this.realJson = (JSONObject) key.clone();
	}
	public ChildJSON(JSONObject parent, Object key, JSONObject value) {
		this.Jparent = parent;
		this.type = Utility.type(parent);
		this.key = key;
		this.value = value;
		this.jIsKey = false;
		this.realJson = (JSONObject) value.clone();
	}
	@SuppressWarnings("unchecked")
	public ChildJSON(Map<?, ?> parent, Object key, JSONObject value) {
		this.Mparent = (Map<Object, Object>) parent;
		this.type = Utility.type(parent);
		this.key = key;
		this.value = value;
		this.jIsKey = false;
		this.realJson = (JSONObject) value.clone();
	}
	@SuppressWarnings("unchecked")
	public ChildJSON(List<?> parent, JSONObject key) {
		this.Lparent = (List<Object>) parent;
		this.type = Utility.type(parent);
		this.key = key;
		this.Okey = key.clone();
		this.realJson = (JSONObject) key.clone();
	}
	
	@SuppressWarnings("unchecked")
	public Object put(Object key, Object value) {
		this.realJson.put(key, value);
		if(this.type == "JSONObject" && jIsKey) {
			Jparent.put(this.realJson.clone(), this.value); return Jparent.remove(this.Okey);
		}
		if(this.type == "HashMap" && jIsKey) {
			Mparent.put(this.realJson.clone(), this.value); return Mparent.remove(this.Okey);
		}
		if(this.type == "JSONObject") return Jparent.put(this.key, this.realJson.clone());
		if(this.type == "HashMap") return Mparent.put(this.key, this.realJson.clone());
		if(this.type == "ArrayList") {
			Lparent.set(Lparent.indexOf(this.Okey), this.realJson.clone()); return null;
		}
		return null;
	}
	
	public Object get(Object key) {
		return this.realJson.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public Object putSilent(Object Key, Object value) {
		return this.realJson.put(Key, value);
	}
	
	@SuppressWarnings("unchecked")
	public void parentPut(Object key, Object value) throws InvalidParentException {
		if(this.type == "JSONObject") Jparent.put(key, value);
		if(this.type == "HashMap") Mparent.put(key, value);
		if(this.type == "ArrayList") throw new InvalidParentException("Parent is type ArrayList.");
	}
	
	public void parentAdd(Object e) throws InvalidParentException {
		if(this.type == "ArrayList") Lparent.add(e);
		if(this.type == "HashMap") throw new InvalidParentException("Parent is type HashMap not ArrayList.");
		if(this.type == "JSONObject") throw new InvalidParentException("Parent is type JSONObject not ArrayList.");
	}
	
	public boolean parentConatainsKey(Object key) {
		if(this.type == "JSONObject")  return Jparent.containsKey(key);
		else if (this.type == "HashMap") return Mparent.containsKey(key);
		else return false;
	}
	
	public boolean parentContainsValue(Object value) {
		if(this.type == "JSONObject") return Jparent.containsValue(value);
		else if (this.type == "HashMap") return Mparent.containsValue(value);
		else return false;
	}

	public boolean parentContains(Object o) {
		if(this.type == "ArrayList") return Lparent.contains(o);
		else return false;
	}
	
	public JSONObject getChild() {
		return this.realJson;
	}
	
	public <K, V> Stream<K> keys(Map<K, V> map, V value) {
	    return map
	      .entrySet()
	      .stream()
	      .filter(entry -> value.equals(entry.getValue()))
	      .map(Map.Entry::getKey);
	}
}
