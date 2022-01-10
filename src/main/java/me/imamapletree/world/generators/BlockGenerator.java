package me.imamapletree.world.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Material;

import me.imamapletree.tools.RMath;
import me.imamapletree.tools.SinIterator;

public class BlockGenerator {
	private List<Material> MAT_LIST = new ArrayList<Material>();
	private Double frequency = 0.015;
	private Double frequencyX = 1.0;
	private Double frequencyY = 1.0;
	private int choice = 0;
	private int listSize = MAT_LIST.size();
	private Float shuffleValue;
	private Float step;
	private Random rand = new Random();
	private RMath rm = new RMath(rand);
	
	private Float rVal = 0f;
	private Float rFreq;
	private Float rExp;
	
	private SinIterator SI1 = new SinIterator(0.8f, 2.5f, true);
	private SinIterator SI2 = new SinIterator(0.1f, 1.5f, true);
	
	public BlockGenerator() {
	}
	
	public BlockGenerator(Double freq, Double steplifier) {
		this.frequency = freq;
	}
	
	public BlockGenerator(Double freq, Double steplifier, List<Material> matlist) {
		this.MAT_LIST.addAll(matlist);
		this.frequency = freq;
		this.listSize = this.MAT_LIST.size();
	}
	
	public BlockGenerator(List<Material> matlist, Random rand) {
		this.rand = rand;
		this.rm = new RMath(this.rand);
		this.MAT_LIST.addAll(matlist);
		this.listSize = matlist.size();
		this.rFreq = this.rm.randomFloat(-3, 3);
		this.SI1.setFrequency(rFreq);
		this.SI2.setFrequency(rFreq);
		this.shuffleValue = refreshShuffleValue();
	}
	
	public void registerBlock(Material mat) {
		this.MAT_LIST.add(mat);
		this.listSize = MAT_LIST.size();
	}
	
	public Material getBlock(int x, int y) {
		if(rand.nextInt(1) == 1) rExp = (float) (1+(rand.nextDouble()/9.8));
		else rExp = (float) (1-(rand.nextDouble()/9.8));
		//Float xRN = this.SI1.step(0.1f);
		//Float yRN = this.SI2.step(0.1f);
		
		//this.step = (float) (xRN*(Math.sin(x) + (yRN*Math.abs(Math.cos(0.5*(y/10)))))*Math.cos(4*y)+xRN);
		//step = (float) (((xRN*Math.sin(0.05*x) + (yRN*Math.abs(Math.cos(0.05*(y/10))))))*Math.cos(y*4))*listSize;
		//step = (float) ((((xRN*Math.sin(0.001*y))+(yRN*Math.cos(0.001*x)))/(Math.abs(Math.cos(0.001*y)+2)))*listSize) + this.plugR();
		//step = Math.abs(step);
		
		//this.step = (float) Math.pow(((Math.sin(frequency*x*frequencyX*this.plugR())+(Math.cos(Math.abs(frequency*y*frequencyY*this.plugR())))*Math.cos(y)))
		this.step = (float) Math.pow(((Math.sin(frequency*x*frequencyX)*Math.sin(frequency*y*frequencyY)*(this.listSize/2))+(this.listSize/2)+this.plugR()), rExp);
		//this.step = (float) Math.pow(((((Math.sin(frequency*x*frequencyX))*(Math.cos(frequency*y*frequencyY))))*this.listSize/2)+this.listSize/2 + this.plugR(), 1+Math.pow(this.plugR(), 4));
		this.choice = RMath.wrapNumber(step,listSize-1);
		if(this.choice >= this.listSize) this.choice = 0;
		if(this.rVal > this.shuffleValue) {
			//Collections.shuffle(this.MAT_LIST);
			//this.shuffleValue = refreshShuffleValue();
		}
		return this.MAT_LIST.get(this.choice);
	}
	
	private Float plugR() {
		Float ret = (float) ((0.5*Math.sin(this.rVal*this.rFreq))+0.5);
		this.rVal += this.rm.randomFloat(-1, 1);
		return ret;
	}
	
	private Float refreshShuffleValue() {
		int randInt = this.rm.randomInt(30000, 50000);
		Float max = (float) (this.rVal + (rand.nextFloat() * randInt));
		return this.rm.randomFloat(this.rVal, max);
	}
}
