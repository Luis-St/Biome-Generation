package net.luis.util.random;

import java.util.Random;

public class WorldgenRandom extends Random implements RandomSource {
	
	private final RandomSource rng;
	private int count;
	
	public WorldgenRandom(long seed) {
		this(new LegacyRandomSource(seed));
	}
	
	public WorldgenRandom(RandomSource rng) {
		super(0L);
		this.rng = rng;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public RandomSource fork() {
		return this.rng.fork();
	}
	
	public PositionalRandomFactory forkPositional() {
		return this.rng.forkPositional();
	}
	
	public int next(int next) {
		++this.count;
		if (this.rng instanceof LegacyRandomSource rng) {
			return rng.next(next);
		} else {
			return (int) (this.rng.nextLong() >>> 64 - next);
		}
	}
	
	public synchronized void setSeed(long seed) {
		if (this.rng != null) {
			this.rng.setSeed(seed);
		}
	}
}