package net.luis.biome.util.random;

import java.util.concurrent.atomic.AtomicLong;

import net.luis.biome.util.Mth;

public class LegacyRandomSource implements BitRandomSource {

	private final AtomicLong seed = new AtomicLong();
	private final MarsagliaPolarGaussian gaussianSource = new MarsagliaPolarGaussian(this);

	public LegacyRandomSource(long seed) {
		this.setSeed(seed);
	}

	public RandomSource fork() {
		return new LegacyRandomSource(this.nextLong());
	}

	public PositionalRandomFactory forkPositional() {
		return new LegacyRandomSource.LegacyPositionalRandomFactory(this.nextLong());
	}

	public void setSeed(long seed) {
		if (!this.seed.compareAndSet(this.seed.get(), (seed ^ 25214903917L) & 281474976710655L)) {
			throw new RuntimeException();
		} else {
			this.gaussianSource.reset();
		}
	}

	public int next(int p_188581_) {
		long i = this.seed.get();
		long j = i * 25214903917L + 11L & 281474976710655L;
		if (!this.seed.compareAndSet(i, j)) {
			throw new RuntimeException();
		} else {
			return (int) (j >> 48 - p_188581_);
		}
	}

	public double nextGaussian() {
		return this.gaussianSource.nextGaussian();
	}

	public static class LegacyPositionalRandomFactory implements PositionalRandomFactory {
		private final long seed;

		public LegacyPositionalRandomFactory(long seed) {
			this.seed = seed;
		}

		public RandomSource at(int x, int y, int z) {
			long i = Mth.getSeed(x, y, z);
			long j = i ^ this.seed;
			return new LegacyRandomSource(j);
		}

		public RandomSource fromHashOf(String string) {
			int i = string.hashCode();
			return new LegacyRandomSource((long) i ^ this.seed);
		}
		
	}
	
}