package me.imamapletree.tools;

import java.util.List;
import java.util.Random;

public class RMath {
	private Random r = new Random();
	
	public RMath(Random random) {
		this.r = random;
	}
	
	public int randomInt(int iMin, int iMax) {
		if(iMin < 0) {
			int aMin = Math.abs(iMin);
			return this.randomInt(iMin+aMin, iMax+aMin)-aMin;
		}
		return r.nextInt((iMax - iMin) + 1) + iMin;
	}
	
	public Double randomDouble(double dMin, double dMax) {
		if(dMax == dMin) return dMax;
		if(dMax < dMin) return dMax + ((dMin - dMax) * r.nextDouble());
		if(dMin < 0) {
			double aMin = Math.abs(dMin);
			return this.randomDouble(dMin+aMin, dMax+aMin)-aMin;
		}
		return dMin + ((dMax - dMin) * r.nextDouble());
	}
	
	public Float randomFloat(float fMin, float fMax) {
		if(fMin == fMax) return fMax;
		if(fMax < fMin) return fMax + ((fMin - fMax) * r.nextFloat());
		if(fMin < 0) {
			float aMin = Math.abs(fMin);
			return this.randomFloat(fMin+aMin, fMax+aMin)-aMin;
		}
		return fMin + ((fMax - fMin) * r.nextFloat());
	}
	
	public Object choice(List<?> list) {
		if(list.size() == 1) return list.get(0);
		return list.get(r.nextInt(list.size()-1));
	}
	
	public static Float doubleToFloat(Double nD) {
		return nD.floatValue();
	}
	
	public static Float intToFloat(Integer nI) {
		return nI.floatValue();
	}
	
	public static Double floatToDouble(Float nF) {
		return nF.doubleValue();
	}
	
	public static Double intToDouble(Integer nI) {
		return nI.doubleValue();
	}
	
	public static Integer doubleToInt(Double nD) {
		return nD.intValue();
	}
	
	public static Integer floatToInt(Float nF) {
		return nF.intValue();
	}
	
	public static int staticRInt(int iMin, int iMax) {
		Random SR = new Random();
		if(iMin < 0) {
			int aMin = Math.abs(iMin);
			return staticRInt(iMin+aMin, iMax+aMin)-aMin;
		}
		return SR.nextInt((iMax - iMin) + 1) + iMin;
	}
	
	public static Double staticRDouble(double dMin, double dMax) {
		Random SR = new Random();
		if(dMax == dMin) return dMax;
		if(dMax < dMin) return dMax + ((dMin - dMax) * SR.nextDouble());
		if(dMin < 0) {
			double aMin = Math.abs(dMin);
			return staticRDouble(dMin+aMin, dMax+aMin)-aMin;
		}
		return dMin + ((dMax - dMin) * SR.nextDouble());
	}
	
	public static Float staticRFloat(float fMin, float fMax) {
		Random SR = new Random();
		if(fMin == fMax) return fMax;
		if(fMax < fMin) return fMax + ((fMin - fMax) * SR.nextFloat());
		if(fMin < 0) {
			float aMin = Math.abs(fMin);
			return staticRFloat(fMin+aMin, fMax+aMin)-aMin;
		}
		return fMin + ((fMax - fMin) * SR.nextFloat());
	}
	
	public static Object staticChoice(List<?> list) {
		Random SR = new Random();
		if(list.size() == 1) return list.get(0);
		return list.get(SR.nextInt(list.size()-1));
	}
	
	public void setSeed(Long seed) {
		this.r.setSeed(seed);
	}
	
	public static Float wrapNumber(Float num, Float max) {
		int times = (int) Math.floor(num/max);
		return num - (max*times);
	}
	
	public static int wrapNumber(Float num, int max) {
		int times = (int) Math.floor(num/max);
		return (int) (num - (max*times));
	}
}
