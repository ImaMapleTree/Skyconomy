package me.imamapletree.tools;

import java.time.Instant;

public class Stopwatch {
	private int type;
	private long start_time;
	
	public Stopwatch() {
		this.type = 0;
	}
	
	public Stopwatch(int type) {
		this.type = type;
	}
	
	public void start() {
		Instant instant = Instant.now();
		if (type == 0) start_time = instant.getEpochSecond();
		if (type == 1) start_time = instant.toEpochMilli();
		start_time = instant.getEpochSecond();
	}
	
	public long timeSince() {
		Instant instant = Instant.now();
		if (type == 0) return instant.getEpochSecond() - start_time;
		if (type == 1) return instant.toEpochMilli() - start_time;
		return instant.getEpochSecond() - start_time;
	}
}
