package me.imamapletree.api;

public class ContainerKey {
	private String target;
	private String uuid;
	private Integer key_length;
	
	public ContainerKey(String target, String uuid) {
		this.target = target;
		this.uuid = uuid;
		this.key_length = (target != null ? target.length() : 0) + (uuid != null ? uuid.length() : 0);
	}
	public String getTarget() {
		return this.target;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	@Override
	public int hashCode() {
		int result = this.key_length.hashCode();
		result = 31 * result + (target != null ? target.hashCode() : 0);
		result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (this.getClass() != obj.getClass()) return false;
		ContainerKey key = (ContainerKey) obj;
		if (key.key_length != this.key_length) return false;
		if (key.target != this.target) return false;
		if (key.uuid != this.uuid) return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ContainerKey [target=" + target  +", UUID=" + uuid + "]";
	}
}
