package me.imamapletree.tools.builders;

public class SentenceBuilder {
	private String start = "";
	private boolean autospace = true;
	
	public SentenceBuilder(String part1) {
		this.start = part1;
	}
	
	public SentenceBuilder (String part1, boolean autospace) {
		this.start = part1;
		this.autospace = autospace;
	}
	
	public SentenceBuilder add(Object obj) {
		if(autospace) this.start = this.start + " " + String.valueOf(obj);
		else this.start = this.start + String.valueOf(obj);
		return this;
	}
	
	public SentenceBuilder add(int obj) {
		if(autospace) this.start = this.start + " " + String.valueOf(obj);
		else this.start = this.start + String.valueOf(obj);
		return this;
	}
	
	public SentenceBuilder add(double obj) {
		if(autospace) this.start = this.start + " " + String.valueOf(obj);
		else this.start = this.start + String.valueOf(obj);
		return this;
	}
	
	public SentenceBuilder add(long obj) {
		if(autospace) this.start = this.start + " " + String.valueOf(obj);
		else this.start = this.start + String.valueOf(obj);
		return this;
	}
	
	public SentenceBuilder add(float obj) {
		if(autospace) this.start = this.start + " " + String.valueOf(obj);
		else this.start = this.start + String.valueOf(obj);
		return this;
	}
	
	public SentenceBuilder add(String obj) {
		if(autospace) this.start = this.start + " " + String.valueOf(obj);
		else this.start = this.start + String.valueOf(obj);
		return this;
	}	

	public String create() {
		return this.start;
	}
}
