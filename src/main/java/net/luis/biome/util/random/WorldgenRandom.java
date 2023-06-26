package net.luis.biome.util.random;

import java.util.Random;

public class WorldgenRandom extends Random implements RandomSource {
	
	private static final long serialVersionUID = 3327246542639377990L;
	private final RandomSource randomSource;
	private int count;
	
	public WorldgenRandom(long seed) {
		this(new LegacyRandomSource(seed));
	}
	
	public WorldgenRandom(RandomSource source) {
		super(0L);
		this.randomSource = source;
	}

	public int getCount() {
		return this.count;
	}

	public RandomSource fork() {
		return this.randomSource.fork();
	}

	public PositionalRandomFactory forkPositional() {
		return this.randomSource.forkPositional();
	}

	public int next(int next) {
		++this.count;
		RandomSource randomsource = this.randomSource;
		if (randomsource instanceof LegacyRandomSource) {
			LegacyRandomSource legacyrandomsource = (LegacyRandomSource) randomsource;
			return legacyrandomsource.next(next);
		} else {
			return (int) (this.randomSource.nextLong() >>> 64 - next);
		}
	}

	public synchronized void setSeed(long seed) {
		if (this.randomSource != null) {
			this.randomSource.setSeed(seed);
		}
	}

	public long setDecorationSeed(long seed, int x, int z) {
		this.setSeed(seed);
		long i = this.nextLong() | 1L;
		long j = this.nextLong() | 1L;
		long k = (long) x * i + (long) z * j ^ seed;
		this.setSeed(k);
		return k;
	}

	public void setFeatureSeed(long seed, int x, int z) {
		long i = seed + (long) x + (long) (10000 * z);
		this.setSeed(i);
	}

	public void setLargeFeatureSeed(long seed, int x, int z) {
		this.setSeed(seed);
		long i = this.nextLong();
		long j = this.nextLong();
		long k = (long) x * i ^ (long) z * j ^ seed;
		this.setSeed(k);
	}

	public void setLargeFeatureWithSalt(long seed, int x, int y, int z) {
		long i = (long) x * 341873128712L + (long) y * 132897987541L + seed + (long) z;
		this.setSeed(i);
	}

	public static Random seedSlimeChunk(int x, int z, long seed0, long seed1) {
		return new Random(seed0 + (long) (x * x * 4987142) + (long) (x * 5947611) + (long) (z * z) * 4392871L + (long) (z * 389711) ^ seed1);
	}

}