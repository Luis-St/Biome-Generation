package net.luis.util.random;

import java.util.concurrent.atomic.AtomicLong;

public class LegacyRandomSource implements BitRandomSource {
	
	private final AtomicLong seed = new AtomicLong();
	private final MarsagliaPolarGaussian gaussian = new MarsagliaPolarGaussian(this);
	
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
			this.gaussian.reset();
		}
	}
	
	public int next(int value) {
		long i = this.seed.get();
		long j = i * 25214903917L + 11L & 281474976710655L;
		if (!this.seed.compareAndSet(i, j)) {
			throw new RuntimeException();
		} else {
			return (int) (j >> 48 - value);
		}
	}
	
	public static class LegacyPositionalRandomFactory implements PositionalRandomFactory {
		
		private final long seed;
		
		public LegacyPositionalRandomFactory(long seed) {
			this.seed = seed;
		}
		
		public RandomSource fromHashOf(String string) {
			return new LegacyRandomSource((long) string.hashCode() ^ this.seed);
		}
		
		private long getSeed(int x, int y, int z) {
			long result = x * 3129871L ^ z * 116129781L ^ y;
			result = result * result * 42317861L + result * 11L;
			return result >> 16;
		}
	}
}