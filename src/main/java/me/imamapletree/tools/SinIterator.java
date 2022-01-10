package me.imamapletree.tools;

public class SinIterator {
	private Float amp = 1f;
	private Float freq = 1f;
	private Float xShift = 0f;
	private Float yShift = 0f;
	private Float iterNum = 0f;
	
	public SinIterator(Float amp) {
		this.amp = amp;
	}
	
	public SinIterator(Float amp, Float freq) {
		this.amp = amp;
		this.freq = freq;
	}
	
	public SinIterator(Float amp, Float freq, boolean MM) {
		if(MM) {
			this.bound(amp, freq);
		} else {
			this.amp = amp;
			this.freq = freq;
		}
	}
	
	public SinIterator(Float amp, Float freq, Float xShift) {
		this.amp = amp;
		this.freq = freq;
		this.xShift = xShift;
	}
	
	public SinIterator(Float amp, Float freq, Float xShift, Float yShift) {
		this.amp = amp;
		this.freq = freq;
		this.xShift = xShift;
		this.yShift = yShift;
	}
	
	public Float step(Float x) {
		float num = (float) (amp*Math.sin((freq*iterNum)+xShift))+yShift;
		this.iterNum += x;
		return num;
	}
	
	public void setAmplitude(Float amp) {
		this.amp = amp;
	}
	
	public void setFrequency(Float freq) {
		this.freq = freq;
	}
	
	public void bound(Float min, Float max) {
		this.amp = (max-min)/2;
		this.yShift = (max+min)/2;
	}
}
